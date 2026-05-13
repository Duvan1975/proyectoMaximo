package com.proyectoMaximo.proyectoMaximoSpringBoot.controller;

import com.proyectoMaximo.proyectoMaximoSpringBoot.uniservicios.DatosRegistroUniservicios;
import com.proyectoMaximo.proyectoMaximoSpringBoot.uniservicios.UniserviciosService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uni-servicios")
public class UniserviciosController {

    private final UniserviciosService uniserviciosService;

    public UniserviciosController(UniserviciosService uniserviciosService) {
        this.uniserviciosService = uniserviciosService;
    }

    @PostMapping
    public void registrarUniservicios(@RequestBody DatosRegistroUniservicios datos) {
        System.out.println("La capa de servicio llegó correctamente");
        uniserviciosService.registrarUniservicios(datos);
    }
}
