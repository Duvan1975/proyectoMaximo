package com.proyectoMaximo.proyectoMaximoSpringBoot.unidades;

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
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class UnidadesService {

    @Autowired UnidadesRepository unidadesRepository;

    public ResponseEntity<DatosRespuestaUnidades> registrarUnidades(
            DatosRegistroUnidades datos, UriComponentsBuilder uriComponentsBuilder) {
        Unidades unidades = new Unidades(datos); //Creamos el objeto
        unidadesRepository.save(new Unidades(datos));

        //Construímos la uri
        var uri = uriComponentsBuilder.path(
                "/unidades-compatibles/{id}").buildAndExpand(unidades.getId()).toUri();

        //Creamos la respuesta
        DatosRespuestaUnidades datosRespuestaUnidades = new DatosRespuestaUnidades(
                unidades.getUc(),
                unidades.getDescripcion(),
                unidades.getUnidadMedida(),
                unidades.getVersion(),
                unidades.getRegulacionVersion(),
                unidades.getNivelUc(),
                unidades.getEquivale(),
                unidades.getPlanta(),
                unidades.getEstado()
        );
        return ResponseEntity.created(uri).body(datosRespuestaUnidades);
    }

    public Page<DatosListadoUnidades> listarUnidades(Pageable paginacion) {
        return unidadesRepository
                .findAll(paginacion)
                .map(DatosListadoUnidades::new);
    }

    @Transactional
    public ResponseEntity actualizarUnidades(DatosActualizarUnidades datos) {
        Unidades unidades = unidadesRepository.getReferenceById(datos.id());
        unidades.actualizarDatos(datos);

        if (datos.uc() != null) unidades.setUc(datos.uc());
        if (datos.descripcion() != null) unidades.setDescripcion(datos.descripcion());
        if (datos.unidadMedida() != null) unidades.setUnidadMedida(datos.unidadMedida());
        if (datos.version() != null) unidades.setVersion(datos.version());
        if (datos.regulacionVersion() != null) unidades.setRegulacionVersion(datos.regulacionVersion());
        if (datos.nivelUc() != null) unidades.setNivelUc(datos.nivelUc());
        if (datos.equivale() != null) unidades.setEquivale(datos.equivale());
        if (datos.planta() != null) unidades.setPlanta(datos.planta());
        if (datos.estado() != null) unidades.setEstado(datos.estado());

        return ResponseEntity.ok(new DatosRespuestaUnidades(
                unidades.getUc(),
                unidades.getDescripcion(),
                unidades.getUnidadMedida(),
                unidades.getVersion(),
                unidades.getRegulacionVersion(),
                unidades.getNivelUc(),
                unidades.getEquivale(),
                unidades.getPlanta(),
                unidades.getEstado()
        ));
    }

    public void eliminarUnidades(Long id) {

        if (!unidadesRepository.existsById(id)) {
            throw new EntityNotFoundException("Unidad no encontrada con el id: " + id);
        }
        unidadesRepository.deleteById(id);
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
                if (row.getCell(4) == null) continue;
                if (row.getCell(5) == null) continue;
                if (row.getCell(6) == null) continue;
                if (row.getCell(7) == null) continue;
                if (row.getCell(8) == null) continue;

                DatosRegistroUnidades datos = new DatosRegistroUnidades(
                        row.getCell(0).getStringCellValue(),
                        row.getCell(1).getStringCellValue(),
                        row.getCell(2).getStringCellValue(),
                        (int) row.getCell(3).getNumericCellValue(),
                        row.getCell(4).getStringCellValue(),
                        (int) row.getCell(5).getNumericCellValue(),
                        row.getCell(6).getStringCellValue(),
                        row.getCell(7).getStringCellValue(),
                        row.getCell(8).getStringCellValue()
                );
                unidadesRepository.save(new Unidades(datos));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al procesar el Excel: " + e.getMessage());
        }
    }

}
