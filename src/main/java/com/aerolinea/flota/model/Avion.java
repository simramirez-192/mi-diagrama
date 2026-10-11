package com.aerolinea.flota.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Avión de la flota. Pertenece a una Aerolinea (N:1).
 */
@Entity
@Table(name = "aviones")
public class Avion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La matrícula es obligatoria")
    @Column(nullable = false, unique = true, length = 20)
    private String matricula;

    @NotBlank(message = "El modelo es obligatorio")
    @Column(nullable = false, length = 60)
    private String modelo;

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    @Column(nullable = false)
    private Integer capacidad;

    @Column(name = "en_mantenimiento", nullable = false)
    private boolean enMantenimiento;

    @Column(name = "combustible_listo", nullable = false)
    private boolean combustibleListo;

    @Column(name = "limpieza_lista", nullable = false)
    private boolean limpiezaLista;

    @NotNull(message = "La ubicación es obligatoria (VOLANDO, EN_TIERRA o FUERA_DE_SERVICIO)")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private UbicacionAvion ubicacion;

    // Lado dueño de la relación: columna FK "aerolinea_id".
    @NotNull(message = "La aerolínea es obligatoria (enviar {\"id\": n})")
    @ManyToOne
    @JoinColumn(name = "aerolinea_id", nullable = false)
    private Aerolinea aerolinea;

    public Avion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isEnMantenimiento() {
        return enMantenimiento;
    }

    public void setEnMantenimiento(boolean enMantenimiento) {
        this.enMantenimiento = enMantenimiento;
    }

    public boolean isCombustibleListo() {
        return combustibleListo;
    }

    public void setCombustibleListo(boolean combustibleListo) {
        this.combustibleListo = combustibleListo;
    }

    public boolean isLimpiezaLista() {
        return limpiezaLista;
    }

    public void setLimpiezaLista(boolean limpiezaLista) {
        this.limpiezaLista = limpiezaLista;
    }

    public UbicacionAvion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionAvion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Aerolinea getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(Aerolinea aerolinea) {
        this.aerolinea = aerolinea;
    }
}
