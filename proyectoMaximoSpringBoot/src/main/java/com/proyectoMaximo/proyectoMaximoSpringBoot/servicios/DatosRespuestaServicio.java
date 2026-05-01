package com.proyectoMaximo.proyectoMaximoSpringBoot.servicios;

import java.math.BigDecimal;

public record DatosRespuestaServicio(
        Long id,
        Long codigoServicio,
        Integer item,
        Integer agrupacion,
        String estructura,
        String detalle,
        String unidad,
        BigDecimal valorUnitario,
        Integer nivelTension,
        String descripcion
) {
}
