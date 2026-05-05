package com.proyectoMaximo.proyectoMaximoSpringBoot.unidades;

import jakarta.persistence.*;

@Entity
@Table(name = "unidades")
public class Unidades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uc;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String unidadMedida;
    private Integer version;
    private String regulacionVersion;
    private Integer nivelUc;
    private String equivale;
    private String planta;
    private String estado;

    public Unidades(DatosRegistroUnidades datos) {
        this.uc = datos.uc();
        this.descripcion = datos.descripcion();
        this.unidadMedida = datos.unidadMedida();
        this.version = datos.version();
        this.regulacionVersion = datos.regulacionVersion();
        this.nivelUc = datos.nivelUc();
        this.equivale = datos.equivale();
        this.planta = datos.planta();
        this.estado = datos.estado();
    }

    //Constructor vacío
    public Unidades() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUc() {
        return uc;
    }

    public void setUc(String uc) {
        this.uc = uc;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getRegulacionVersion() {
        return regulacionVersion;
    }

    public void setRegulacionVersion(String regulacionVersion) {
        this.regulacionVersion = regulacionVersion;
    }

    public Integer getNivelUc() {
        return nivelUc;
    }

    public void setNivelUc(Integer nivelUc) {
        this.nivelUc = nivelUc;
    }

    public String getEquivale() {
        return equivale;
    }

    public void setEquivale(String equivale) {
        this.equivale = equivale;
    }

    public String getPlanta() {
        return planta;
    }

    public void setPlanta(String planta) {
        this.planta = planta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void actualizarDatos(DatosActualizarUnidades datos) {

    }
}
