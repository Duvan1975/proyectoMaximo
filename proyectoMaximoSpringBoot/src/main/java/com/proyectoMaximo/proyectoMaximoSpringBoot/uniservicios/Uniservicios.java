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

    public Uniservicios() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNivelTension() {
        return nivelTension;
    }

    public void setNivelTension(Integer nivelTension) {
        this.nivelTension = nivelTension;
    }

    public Integer getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(Integer codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public String getEstructura() {
        return estructura;
    }

    public void setEstructura(String estructura) {
        this.estructura = estructura;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void actualizarDatos(DatosActualizarUniservicios datos) {
    }
}
