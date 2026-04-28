package com.proyectoMaximo.proyectoMaximoSpringBoot.sercivios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioService {

    @Autowired ServicioRepository servicioRepository;

    public void registrarServicio(DatosRegistroServicios datos) {

        if (servicioRepository.existsByCodigoServicio(datos.codigoServicio())) {
            throw new RuntimeException("Código de servicio duplicado");
        }
        servicioRepository.save(new Servicio(datos));
    }
}
