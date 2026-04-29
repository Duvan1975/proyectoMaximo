package com.proyectoMaximo.proyectoMaximoSpringBoot.servicios;

import java.math.BigDecimal;

public record DatosListadoServicios(

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
    public DatosListadoServicios(Servicio servicio) {
        this(
                servicio.getId(),
                servicio.getCodigoServicio(),
                servicio.getItem(),
                servicio.getAgrupacion(),
                servicio.getEstructura(),
                servicio.getDetalle(),
                servicio.getUnidad(),
                servicio.getValorUnitario(),
                servicio.getNivelTension(),
                servicio.getDescripcion()
        );
    }
}
