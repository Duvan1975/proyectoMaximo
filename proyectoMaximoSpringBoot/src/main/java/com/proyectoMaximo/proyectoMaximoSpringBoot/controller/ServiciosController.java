package com.proyectoMaximo.proyectoMaximoSpringBoot.controller;

import com.proyectoMaximo.proyectoMaximoSpringBoot.servicios.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity<DatosRespuestaServicio> registrarServicio(
            @RequestBody @Valid DatosRegistroServicios datos,
            UriComponentsBuilder uriComponentsBuilder) {

        return servicioService.registrarServicio(datos, uriComponentsBuilder);
    }

    @GetMapping
    public Page<DatosListadoServicios> listadoServicios(
            @PageableDefault(size = 10, sort = "codigoServicio")Pageable paginacion) {
        return servicioService.listarServicios(paginacion);
    }

    @PutMapping
    public ResponseEntity<DatosRespuestaServicio> actualizarServicio(
            @RequestBody @Valid DatosActualizarServicios datos) {
            return servicioService.actualizarServicio(datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarServicio(@PathVariable Long id) {
        servicioService.eliminarServicio(id);

        return ResponseEntity.noContent().build(); //Nos devuelve un 204 No Content
    }
}
