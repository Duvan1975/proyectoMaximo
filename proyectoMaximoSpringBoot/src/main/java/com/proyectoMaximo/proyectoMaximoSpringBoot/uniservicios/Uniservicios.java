package com.proyectoMaximo.proyectoMaximoSpringBoot.uniservicios;

import jakarta.persistence.*;

@Entity
@Table(name = "uniservicios")
public class Uniservicios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer nivelTension;
    private Integer codigoServicio;
    private String estructura;
    private String observacion;

    public Uniservicios(DatosRegistroUniservicios datos) {

        this.nivelTension = datos.nivelTension();
        this.codigoServicio = datos.codigoServicio();
        this.estructura = datos.estructura();
        this.observacion = datos.observacion();
    }
}
