package com.proyectoMaximo.proyectoMaximoSpringBoot.uniservicios;

public record DatosRespuestaUniservicios(

        Long id,
        Integer nivelTension,
        Integer codigoServicio,
        String estructura,
        String observacion
) {
}
