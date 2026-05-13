package com.proyectoMaximo.proyectoMaximoSpringBoot.uniservicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniserviciosService {

    @Autowired UniserviciosRepository uniserviciosRepository;

    public void registrarUniservicios(DatosRegistroUniservicios datos) {
        uniserviciosRepository.save(new Uniservicios(datos));
    }
}
