package com.proyectoMaximo.proyectoMaximoSpringBoot.unidades;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadesService {

    @Autowired UnidadesRepository unidadesRepository;

    public void registrarUnidades(DatosRegistroUnidades datos) {
        unidadesRepository.save(new Unidades(datos));
    }

    public Page<DatosListadoUnidades> listarUnidades(Pageable paginacion) {
        return unidadesRepository
                .findAll(paginacion)
                .map(DatosListadoUnidades::new);
    }

    @Transactional
    public void actualizarUnidades(DatosActualizarUnidades datos) {
        Unidades unidades = unidadesRepository.getReferenceById(datos.id());

        if (datos.uc() != null) unidades.setUc(datos.uc());
        if (datos.descripcion() != null) unidades.setDescripcion(datos.descripcion());
        if (datos.unidadMedida() != null) unidades.setUnidadMedida(datos.unidadMedida());
        if (datos.version() != null) unidades.setVersion(datos.version());
        if (datos.regulacionVersion() != null) unidades.setRegulacionVersion(datos.regulacionVersion());
        if (datos.nivelUc() != null) unidades.setNivelUc(datos.nivelUc());
        if (datos.equivale() != null) unidades.setEquivale(datos.equivale());
        if (datos.planta() != null) unidades.setPlanta(datos.planta());
        if (datos.estado() != null) unidades.setEstado(datos.estado());
    }

    public void eliminarUnidades(Long id) {

        if (!unidadesRepository.existsById(id)) {
            throw new EntityNotFoundException("Unidad no encontrada con el id: " + id);
        }
        unidadesRepository.deleteById(id);
    }

}
