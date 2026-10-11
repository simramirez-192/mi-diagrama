package com.aerolinea.flota.repository;

import com.aerolinea.flota.model.Aerolinea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AerolineaRepository extends JpaRepository<Aerolinea, Long> {

    boolean existsByNombre(String nombre);

    boolean existsByCodigoIata(String codigoIata);
}
