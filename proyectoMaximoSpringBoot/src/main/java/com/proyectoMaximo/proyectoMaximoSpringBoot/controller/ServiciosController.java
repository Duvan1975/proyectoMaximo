package com.proyectoMaximo.proyectoMaximoSpringBoot.controller;

import com.proyectoMaximo.proyectoMaximoSpringBoot.servicios.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicios")
@CrossOrigin(origins = "http://localhost:3000")
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

    @GetMapping
    public Page<DatosListadoServicios> listadoServicios(
            @PageableDefault(size = 10, sort = "codigoServicio")Pageable paginacion) {
        return servicioService.listarServicios(paginacion);
    }

    @PutMapping
    public void actualizarServicio(
            @RequestBody @Valid DatosActualizarServicios datos) {
            servicioService.actualizarServicio(datos);
    }
}
