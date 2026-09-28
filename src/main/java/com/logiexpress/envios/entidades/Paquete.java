package com.logiexpress.envios.entidades;

public class Paquete {
    private int idPaquete;
    private double peso;
    private String dimensiones;
    private boolean fragil;
    private EstadoPaquete estado;

    public Paquete(int idPaquete, double peso, String dimensiones, boolean fragil) {
        this.idPaquete = idPaquete;
        this.peso = peso;
        this.dimensiones = dimensiones;
        this.fragil = fragil;
        this.estado = EstadoPaquete.CREADO;
    }

    public EstadoPaquete getEstado() {
        return this.estado;
    }

    public void asignarRepartidor() {
        if (this.estado != EstadoPaquete.CREADO) {
            throw new IllegalStateException("Solo se puede asignar un paquete en estado CREADO.");
        }
        this.estado = EstadoPaquete.ASIGNADO;
    }

    public void recolectarPaquete() {
        if (this.estado != EstadoPaquete.ASIGNADO && this.estado != EstadoPaquete.INCIDENCIA) {
            throw new IllegalStateException("Solo se puede recolectar desde ASIGNADO o resolviendo INCIDENCIA.");
        }
        this.estado = EstadoPaquete.EN_CAMINO;
    }

    public void reportarIncidencia() {
        if (this.estado != EstadoPaquete.EN_CAMINO) {
            throw new IllegalStateException("Solo se pueden reportar incidencias si está EN_CAMINO.");
        }
        this.estado = EstadoPaquete.INCIDENCIA;
    }

    public void confirmarEntrega() {
        if (this.estado != EstadoPaquete.EN_CAMINO) {
            throw new IllegalStateException("Solo se puede entregar un paquete que esté EN_CAMINO.");
        }
        this.estado = EstadoPaquete.ENTREGADO;
    }
}
