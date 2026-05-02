package com.proyectoMaximo.proyectoMaximoSpringBoot.servicios;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServicioService {

    @Autowired ServicioRepository servicioRepository;

    public ResponseEntity<DatosRespuestaServicio> registrarServicio(
            DatosRegistroServicios datos, UriComponentsBuilder uriComponentsBuilder) {

        if (servicioRepository.existsByCodigoServicio(datos.codigoServicio())) {
            throw new RuntimeException("Código de servicio duplicado");
        }
        Servicio servicio = new Servicio(datos);//Creamos el objeto
        servicioRepository.save(new Servicio(datos));

        //Construímos la uri del recurso creado
        var uri = uriComponentsBuilder.path(
                "/servicios/{id}").buildAndExpand(servicio.getId()).toUri();

        //Creamos la respuesta
        DatosRespuestaServicio datosRespuestaServicio = new DatosRespuestaServicio(
                servicio.getId(),
                servicio.getCodigoServicio(),
                servicio.getItem(),
                servicio.getAgrupacion(),
                servicio.getEstructura(),
                servicio.getDetalle(),
                servicio.getUnidad(),
                servicio.getValorUnitario(),
                servicio.getNivelTension(),
                servicio.getDescripcion()
        );
        return ResponseEntity.created(uri).body(datosRespuestaServicio);
    }

    public Page<DatosListadoServicios> listarServicios(Pageable paginacion) {
        return servicioRepository
                .findAll(paginacion)
                .map(DatosListadoServicios::new);
    }

    @Transactional
    public ResponseEntity actualizarServicio(DatosActualizarServicios datos) {
        Servicio servicio = servicioRepository.getReferenceById(datos.id());
        servicio.actualizarDatos(datos);

        if (datos.codigoServicio() != null) servicio.setCodigoServicio(datos.codigoServicio());
        if (datos.item() != null) servicio.setItem(datos.item());
        if (datos.agrupacion() != null) servicio.setAgrupacion(datos.agrupacion());
        if (datos.estructura() != null) servicio.setEstructura(datos.estructura());
        if (datos.detalle() != null) servicio.setDetalle(datos.detalle());
        if (datos.unidad() != null) servicio.setUnidad(datos.unidad());
        if (datos.valorUnitario() != null) servicio.setValorUnitario(datos.valorUnitario());
        if (datos.nivelTension() != null) servicio.setNivelTension(datos.nivelTension());
        if (datos.descripcion() != null) servicio.setDescripcion(datos.descripcion());

        return ResponseEntity.ok(new DatosRespuestaServicio(
                servicio.getId(),
                servicio.getCodigoServicio(),
                servicio.getItem(),
                servicio.getAgrupacion(),
                servicio.getEstructura(),
                servicio.getDetalle(),
                servicio.getUnidad(),
                servicio.getValorUnitario(),
                servicio.getNivelTension(),
                servicio.getDescripcion()
        ));
    }

    public void eliminarServicio(Long id) {

        if (!servicioRepository.existsById(id)) {
            throw new EntityNotFoundException("Servicio no encontrado con el id: " + id);
        }
        servicioRepository.deleteById(id);

    }

    public void cargarExcel(MultipartFile archivo) {
        try (Workbook workbook = new XSSFWorkbook(archivo.getInputStream())) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // saltar encabezado

                //Validamos cada celda si vienen vacías que continue
                if (row.getCell(7) == null) continue;
                if (row.getCell(0) == null) continue;
                if (row.getCell(1) == null) continue;
                if (row.getCell(2) == null) continue;
                if (row.getCell(3) == null) continue;
                if (row.getCell(4) == null) continue;
                if (row.getCell(5) == null) continue;
                if (row.getCell(6) == null) continue;
                if (row.getCell(8) == null) continue;

                DatosRegistroServicios datos = new DatosRegistroServicios(
                        (long) row.getCell(7).getNumericCellValue(),
                        (int) row.getCell(0).getNumericCellValue(),
                        (int) row.getCell(1).getNumericCellValue(),
                        row.getCell(2).getStringCellValue(),
                        row.getCell(3).getStringCellValue(),
                        row.getCell(4).getStringCellValue(),
                        BigDecimal.valueOf(row.getCell(5).getNumericCellValue()),
                        (int) row.getCell(6).getNumericCellValue(),
                        row.getCell(8).getStringCellValue()
                );

                if (!servicioRepository.existsByCodigoServicio(datos.codigoServicio())) {
                    servicioRepository.save(new Servicio(datos));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al procesar el Excel: " + e.getMessage());
        }
    }
}
