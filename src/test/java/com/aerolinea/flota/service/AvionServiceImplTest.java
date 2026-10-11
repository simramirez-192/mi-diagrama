package com.aerolinea.flota.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.aerolinea.flota.exception.DuplicateResourceException;
import com.aerolinea.flota.exception.ResourceNotFoundException;
import com.aerolinea.flota.model.Aerolinea;
import com.aerolinea.flota.model.Avion;
import com.aerolinea.flota.model.UbicacionAvion;
import com.aerolinea.flota.repository.AerolineaRepository;
import com.aerolinea.flota.repository.AvionRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AvionServiceImplTest {

    @Mock
    private AvionRepository avionRepository;

    @Mock
    private AerolineaRepository aerolineaRepository;

    @InjectMocks
    private AvionServiceImpl avionService;

    private Avion nuevoAvion(String matricula) {
        Aerolinea referencia = new Aerolinea();
        referencia.setId(1L);

        Avion avion = new Avion();
        avion.setMatricula(matricula);
        avion.setModelo("Airbus A320");
        avion.setCapacidad(180);
        avion.setUbicacion(UbicacionAvion.EN_TIERRA);
        avion.setAerolinea(referencia);
        return avion;
    }

    @Test
    void buscarPorIdInexistenteLanzaNotFound() {
        when(avionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> avionService.buscarPorId(99L));
    }

    @Test
    void crearConMatriculaDuplicadaLanzaConflicto() {
        when(avionRepository.existsByMatricula("CC-BAA")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> avionService.crear(nuevoAvion("CC-BAA")));
        verify(avionRepository, never()).save(any());
    }

    @Test
    void crearAvionValidoLoGuardaConSuAerolinea() {
        Aerolinea aerolinea = new Aerolinea();
        aerolinea.setId(1L);
        aerolinea.setNombre("LATAM");

        when(avionRepository.existsByMatricula("CC-BAA")).thenReturn(false);
        when(aerolineaRepository.findById(1L)).thenReturn(Optional.of(aerolinea));
        when(avionRepository.save(any(Avion.class))).thenAnswer(inv -> inv.getArgument(0));

        Avion guardado = avionService.crear(nuevoAvion("CC-BAA"));

        assertEquals("CC-BAA", guardado.getMatricula());
        assertEquals("LATAM", guardado.getAerolinea().getNombre());
    }

    @Test
    void crearConAerolineaInexistenteLanzaNotFound() {
        when(avionRepository.existsByMatricula("CC-BAA")).thenReturn(false);
        when(aerolineaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> avionService.crear(nuevoAvion("CC-BAA")));
    }
}
