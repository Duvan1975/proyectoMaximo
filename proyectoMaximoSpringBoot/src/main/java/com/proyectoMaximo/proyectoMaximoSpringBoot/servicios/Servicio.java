package com.proyectoMaximo.proyectoMaximoSpringBoot.servicios;

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
    public Servicio() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(Long codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public Integer getAgrupacion() {
        return agrupacion;
    }

    public void setAgrupacion(Integer agrupacion) {
        this.agrupacion = agrupacion;
    }

    public String getEstructura() {
        return estructura;
    }

    public void setEstructura(String estructura) {
        this.estructura = estructura;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Integer getNivelTension() {
        return nivelTension;
    }

    public void setNivelTension(Integer nivelTension) {
        this.nivelTension = nivelTension;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void actualizarDatos(DatosActualizarServicios datos) {
    }
}
