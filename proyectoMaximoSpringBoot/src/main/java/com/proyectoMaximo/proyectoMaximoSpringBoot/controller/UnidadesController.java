package com.proyectoMaximo.proyectoMaximoSpringBoot.controller;

import com.proyectoMaximo.proyectoMaximoSpringBoot.unidades.DatosListadoUnidades;
import com.proyectoMaximo.proyectoMaximoSpringBoot.unidades.DatosRegistroUnidades;
import com.proyectoMaximo.proyectoMaximoSpringBoot.unidades.Unidades;
import com.proyectoMaximo.proyectoMaximoSpringBoot.unidades.UnidadesService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidades-compatibles")
@CrossOrigin(origins = "http://localhost:3000")
public class UnidadesController {

    private final UnidadesService unidadesService;

    public UnidadesController(UnidadesService unidadesService) {
        this.unidadesService = unidadesService;
    }

    @PostMapping
    public void registrarUnidades(@RequestBody @Valid DatosRegistroUnidades datos) {
        System.out.println("El request llego correctamente");
        unidadesService.registrarUnidades(datos);
    }

    @GetMapping
    public Page<DatosListadoUnidades> listadoUnidades(
            @PageableDefault(size = 10, sort = "uc")Pageable paginacion) {
        return unidadesService.listarUnidades(paginacion);
    }
}
