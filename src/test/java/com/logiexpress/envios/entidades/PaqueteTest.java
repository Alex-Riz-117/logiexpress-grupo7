package com.logiexpress.envios.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaqueteTest {

    private Paquete paquete;

    @BeforeEach
    void setUp() {
        paquete = new Paquete(456, 2.5, "30x20x15", true);
    }

    @Test
    @DisplayName("CP-01: Estado inicial debe ser CREADO")
    void testEstadoInicial() {
        assertEquals(EstadoPaquete.CREADO, paquete.getEstado());
    }

    @Test
    @DisplayName("CP-02: Flujo exitoso: CREADO -> ASIGNADO -> EN_CAMINO -> ENTREGADO")
    void testCicloDeVidaExitoso() {
        paquete.asignarRepartidor(true); // Condición de guarda: True
        assertEquals(EstadoPaquete.ASIGNADO, paquete.getEstado());

        paquete.recolectarPaquete();
        assertEquals(EstadoPaquete.EN_CAMINO, paquete.getEstado());

        paquete.confirmarEntrega();
        assertEquals(EstadoPaquete.ENTREGADO, paquete.getEstado());
    }

    @Test
    @DisplayName("CP-03: Manejo de INCIDENCIA y vuelta a ruta usando resolverIncidencia()")
    void testFlujoIncidencia() {
        paquete.asignarRepartidor(true);
        paquete.recolectarPaquete();

        paquete.reportarIncidencia();
        assertEquals(EstadoPaquete.INCIDENCIA, paquete.getEstado());

        paquete.resolverIncidencia(); // Corregido según el evento del diagrama
        assertEquals(EstadoPaquete.EN_CAMINO, paquete.getEstado());
    }

    @Test
    @DisplayName("CP-04: Transicion invalida - Entrega directa no permitida")
    void testTransicionInvalida() {
        assertThrows(IllegalStateException.class, () -> {
            paquete.confirmarEntrega();
        });
    }

    @Test
    @DisplayName("CP-05: Condición de Guarda - No cambia a ASIGNADO si no hay repartidor disponible")
    void testGuardaRepartidorNoDisponible() {
        paquete.asignarRepartidor(false); // Condición de guarda: False
        assertEquals(EstadoPaquete.CREADO, paquete.getEstado(), 
            "Si no hay repartidor disponible, debe permanecer en CREADO.");
    }
}
