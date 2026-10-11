package com.aerolinea.flota.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Una aerolínea opera muchos aviones (relación 1:N con Avion).
 */
@Entity
@Table(name = "aerolineas")
public class Aerolinea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la aerolínea es obligatorio")
    @Column(nullable = false, unique = true, length = 60)
    private String nombre;

    @NotBlank(message = "El código IATA es obligatorio")
    @Column(name = "codigo_iata", nullable = false, unique = true, length = 3)
    private String codigoIata;

    // Lado inverso de la relación: la FK vive en la tabla "aviones".
    @JsonIgnore
    @OneToMany(mappedBy = "aerolinea", cascade = CascadeType.PERSIST)
    private List<Avion> aviones = new ArrayList<>();

    public Aerolinea() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoIata() {
        return codigoIata;
    }

    public void setCodigoIata(String codigoIata) {
        this.codigoIata = codigoIata;
    }

    public List<Avion> getAviones() {
        return aviones;
    }

    public void setAviones(List<Avion> aviones) {
        this.aviones = aviones;
    }
}
