package com.proyectoMaximo.proyectoMaximoSpringBoot.uniservicios;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UniserviciosService {

    @Autowired UniserviciosRepository uniserviciosRepository;

    public void registrarUniservicios(DatosRegistroUniservicios datos) {
        uniserviciosRepository.save(new Uniservicios(datos));
    }

    public Page<DatosListadoUniservicios> listadoUniservicios(Pageable paginacion) {
        return uniserviciosRepository
                .findAll(paginacion)
                .map(DatosListadoUniservicios::new);
    }

    @Transactional
    public ResponseEntity actualizarUniservicios(DatosActualizarUniservicios datos) {
        Uniservicios uniservicios = uniserviciosRepository.getReferenceById(datos.id());
        uniservicios.actualizarDatos(datos);

        if (datos.nivelTension() != null) uniservicios.setNivelTension(datos.nivelTension());
        if (datos.codigoServicio() != null) uniservicios.setCodigoServicio(datos.codigoServicio());
        if (datos.estructura() != null) uniservicios.setEstructura(datos.estructura());
        if (datos.observacion() != null) uniservicios.setObservacion(datos.observacion());

        return ResponseEntity.ok(new DatosRespuestaUniservicios(
                uniservicios.getId(),
                uniservicios.getNivelTension(),
                uniservicios.getCodigoServicio(),
                uniservicios.getEstructura(),
                uniservicios.getObservacion()
        ));
    }

    public void eliminarUniservicios(Long id) {

        if (!uniserviciosRepository.existsById(id)) {
            throw new EntityNotFoundException("Servicios no encontrado con el id: " + id);
        }
        uniserviciosRepository.deleteById(id);
    }

    public void cargarExcel(MultipartFile archivo) {
        try (Workbook workbook = new XSSFWorkbook(archivo.getInputStream())) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // saltar encabezado

                if (row.getCell(0) == null) continue;
                if (row.getCell(1) == null) continue;
                if (row.getCell(2) == null) continue;
                if (row.getCell(3) == null) continue;

                DatosRegistroUniservicios datos = new DatosRegistroUniservicios(
                        (int)row.getCell(0).getNumericCellValue(),
                        (int)row.getCell(1).getNumericCellValue(),
                        row.getCell(2).getStringCellValue(),
                        row.getCell(3).getStringCellValue()
                );
                uniserviciosRepository.save(new Uniservicios(datos));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al procesar el Excel: " + e.getMessage());
        }
    }

    public List<ResultadoBusquedaServicio>
    buscarServiciosActa(MultipartFile archivo) {

        try (Workbook workbook =
                     new XSSFWorkbook(archivo.getInputStream())) {

            Sheet sheet = workbook.getSheet("TRABAJOS");

            if (sheet == null) {

                throw new RuntimeException(
                        "No se encontró la hoja TRABAJOS"
                );
            }

            Integer columnaServicio = null;
            Integer filaEncabezado = null;

            for (int i = 0; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                if (row == null) continue;

                for (Cell cell : row) {

                    try {

                        if (cell.getCellType() == CellType.STRING) {

                            String valor = cell
                                    .getStringCellValue()
                                    .trim();

                            if (valor.equalsIgnoreCase("Servicio")) {

                                columnaServicio =
                                        cell.getColumnIndex();

                                filaEncabezado = i;

                                break;
                            }
                        }

                    } catch (Exception e) {

                        System.out.println(
                                "Error leyendo encabezado fila: "
                                        + i
                        );
                    }
                }

                if (columnaServicio != null) {
                    break;
                }
            }

            if (columnaServicio == null) {

                throw new RuntimeException(
                        "No se encontró la columna Servicio"
                );
            }

            FormulaEvaluator evaluator =
                    workbook
                            .getCreationHelper()
                            .createFormulaEvaluator();

            Set<Integer> codigosUnicos =
                    new HashSet<>();

            for (int i = 1;
                 i <= sheet.getLastRowNum();
                 i++) {

                Row row = sheet.getRow(i);

                if (row == null) continue;

                Cell celda =
                        row.getCell(columnaServicio);

                if (celda == null) continue;

                try {

                    Integer codigo = null;

                    if (celda.getCellType()
                            == CellType.NUMERIC) {

                        codigo =
                                (int) celda
                                        .getNumericCellValue();
                    }

                    else if (celda.getCellType()
                            == CellType.FORMULA) {

                        CellValue valorFormula =
                                evaluator.evaluate(celda);

                        if (valorFormula.getCellType()
                                == CellType.NUMERIC) {

                            codigo =
                                    (int) valorFormula
                                            .getNumberValue();
                        }
                    }

                    if (codigo != null && codigo > 0) {

                        codigosUnicos.add(codigo);
                    }

                } catch (Exception e) {

                    System.out.println(
                            "Error leyendo fila: "
                                    + i
                    );
                }
            }

            List<Uniservicios> coincidencias =
                    uniserviciosRepository
                            .findByCodigoServicioIn(
                                    new ArrayList<>(
                                            codigosUnicos
                                    )
                            );

            Map<Integer,
                    List<DatosCoincidenciaUniservicio>>
                    agrupados = coincidencias.stream()

                    .collect(Collectors.groupingBy(

                            Uniservicios::getCodigoServicio,

                            Collectors.mapping(
                                    DatosCoincidenciaUniservicio::new,
                                    Collectors.toList()
                            )
                    ));

            return agrupados.entrySet()
                    .stream()

                    .map(entry ->
                            new ResultadoBusquedaServicio(
                                    entry.getKey(),
                                    entry.getValue()
                            )
                    )

                    .toList();

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Error procesando archivo: "
                            + e.getMessage()
            );
        }
    }
}

