package com.proyectoMaximo.proyectoMaximoSpringBoot.controller;

import com.proyectoMaximo.proyectoMaximoSpringBoot.sercivios.DatosRegistroServicios;
import com.proyectoMaximo.proyectoMaximoSpringBoot.sercivios.ServicioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servicios")
public class ServiciosController {

    private final ServicioService servicioService;

    public ServiciosController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @PostMapping
    public void registrarServicio(@RequestBody @Valid DatosRegistroServicios datos) {
        System.out.println(datos);
        servicioService.registrarServicio(datos);
    }
}
