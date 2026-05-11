package com.proyectoMaximo.proyectoMaximoSpringBoot.controller;

import com.proyectoMaximo.proyectoMaximoSpringBoot.unidades.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/unidades-compatibles")
@CrossOrigin(origins = "http://localhost:3000")
public class UnidadesController {

    private final UnidadesService unidadesService;

    public UnidadesController(UnidadesService unidadesService) {
        this.unidadesService = unidadesService;
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaUnidades> registrarUnidades(
            @RequestBody @Valid DatosRegistroUnidades datos,
            UriComponentsBuilder uriComponentsBuilder) {

        return unidadesService.registrarUnidades(datos, uriComponentsBuilder);
    }

    @GetMapping
    public Page<DatosListadoUnidades> listadoUnidades(
            @PageableDefault(size = 10, sort = "uc")Pageable paginacion) {
        return unidadesService.listarUnidades(paginacion);
    }

    @PutMapping
    public ResponseEntity<DatosRespuestaUnidades> actualizarUnidades(
            @RequestBody @Valid DatosActualizarUnidades datos) {
        return unidadesService.actualizarUnidades(datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUnidad(@PathVariable Long id) {
        unidadesService.eliminarUnidades(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cargar-excel")
    public ResponseEntity<String> cargarExcel(
            @RequestParam("archivo")MultipartFile archivo) {
        unidadesService.cargarExcel(archivo);
        return ResponseEntity.ok("Archivo procesado correctamente");
    }
}
