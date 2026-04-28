package com.proyectoMaximo.proyectoMaximoSpringBoot.sercivios;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "servicios")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_servicio", unique = true, nullable = false)
    private Long codigoServicio;

    private Integer item;
    private Integer agrupacion;
    private String estructura;

    @Column(columnDefinition = "TEXT")
    private String detalle;

    private String unidad;

    @Column(name = "valor_Unitario", precision = 15, scale = 2)
    private BigDecimal valorUnitario;

    @Column(name = "nivel_tension")
    private Integer nivelTension;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    public Servicio(DatosRegistroServicios datos) {

        this.codigoServicio = datos.codigoServicio();
        this.item = datos.item();
        this.agrupacion = datos.agrupacion();
        this.estructura = datos.estructura();
        this.detalle = datos.detalle();
        this.unidad = datos.unidad();
        this.valorUnitario = datos.valorUnitario();
        this.nivelTension = datos.nivelTension();
        this.descripcion = datos.descripcion();
    }
}
