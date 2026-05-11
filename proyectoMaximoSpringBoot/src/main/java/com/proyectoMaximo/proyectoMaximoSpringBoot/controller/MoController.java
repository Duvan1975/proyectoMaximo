package com.proyectoMaximo.proyectoMaximoSpringBoot.controller;

import com.proyectoMaximo.proyectoMaximoSpringBoot.manoObra.MoRegistroDTO;
import com.proyectoMaximo.proyectoMaximoSpringBoot.manoObra.MoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/excel")
public class MoController {

    private final MoService moService;

    public MoController(MoService moService) {
        this.moService = moService;
    }

    @PostMapping("/mo")
    public List<MoRegistroDTO> cargarArchivoMo(
            @RequestParam("file")MultipartFile file) {
        try {
            return moService.leerArchivoMO(file.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el archivo", e);
        }
    }

}
