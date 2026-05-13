package com.proyectoMaximo.proyectoMaximoSpringBoot.manoObra;

import com.proyectoMaximo.proyectoMaximoSpringBoot.unidades.CoincidenciaUnidadDTO;

import java.util.List;

public record MoRegistroDTO(

        String estructura,
        String detalle,
        Integer cant,
        Integer nivelTension,
        List<CoincidenciaUnidadDTO> coincidencias
) {
}
