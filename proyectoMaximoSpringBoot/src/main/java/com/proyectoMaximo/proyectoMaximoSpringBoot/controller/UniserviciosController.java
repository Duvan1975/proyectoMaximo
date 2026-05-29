package com.proyectoMaximo.proyectoMaximoSpringBoot.controller;

import com.proyectoMaximo.proyectoMaximoSpringBoot.uniservicios.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/uni-servicios")
@CrossOrigin(origins = "http://localhost:3000")
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

    @GetMapping
    public Page<DatosListadoUniservicios> listadoUniservicios(
            @PageableDefault(size = 20, sort = "codigoServicio")Pageable paginacion) {
        return uniserviciosService.listadoUniservicios(paginacion);
    }

    @GetMapping("/buscar")
    public Page<DatosListadoUniservicios> buscarPorCodigoServicio(
            @RequestParam String codigoServicio,
            @PageableDefault(size = 10, sort = "estructura")
            Pageable paginacion) {

        return uniserviciosService.buscarPorCodigoServicio(
                codigoServicio, paginacion);
    }

    @PutMapping
    public ResponseEntity<DatosRespuestaUniservicios> actualizarUniservicios(
            @RequestBody @Valid DatosActualizarUniservicios datos) {
        return uniserviciosService.actualizarUniservicios(datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUniservicios(@PathVariable Long id) {
        uniserviciosService.eliminarUniservicios(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cargar-excel")
    public ResponseEntity<String> cargarExcel(
            @RequestParam("archivo")MultipartFile archivo) {
        uniserviciosService.cargarExcel(archivo);
        return ResponseEntity.ok("Archivo cargado correctamente");
    }

    @PostMapping("/buscar-acta")
    public ResponseEntity<List<ResultadoBusquedaServicio>>
    buscarActa(

            @RequestParam("archivo")
            MultipartFile archivo
    ) {

        return ResponseEntity.ok(
                uniserviciosService
                        .buscarServiciosActa(archivo)
        );
    }
}
