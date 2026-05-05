package com.proyectoMaximo.proyectoMaximoSpringBoot.unidades;

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

}
