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

    // Se agrega el parámetro para cumplir la condición de guarda [hay Repartidor Disponible]
    public void asignarRepartidor(boolean hayRepartidorDisponible) {
        if (this.estado != EstadoPaquete.CREADO) {
            throw new IllegalStateException("Solo se puede asignar un paquete en estado CREADO.");
        }
        // Si no hay repartidor, el estado no cambia (se queda en CREADO)
        if (hayRepartidorDisponible) {
            this.estado = EstadoPaquete.ASIGNADO;
            notificarRepartidor();
        }
    }

    public void recolectarPaquete() {
        if (this.estado != EstadoPaquete.ASIGNADO) {
            throw new IllegalStateException("Solo se puede recolectar desde el estado ASIGNADO.");
        }
        this.estado = EstadoPaquete.EN_CAMINO;
    }

    public void reportarIncidencia() {
        if (this.estado != EstadoPaquete.EN_CAMINO) {
            throw new IllegalStateException("Solo se pueden reportar incidencias si está EN_CAMINO.");
        }
        this.estado = EstadoPaquete.INCIDENCIA;
        notificarAdministrador();
    }

    // Se crea el método propio para la transición según el diagrama
    public void resolverIncidencia() {
        if (this.estado != EstadoPaquete.INCIDENCIA) {
            throw new IllegalStateException("Solo se puede resolver una incidencia si el paquete está en estado INCIDENCIA.");
        }
        this.estado = EstadoPaquete.EN_CAMINO;
    }

    public void confirmarEntrega() {
        if (this.estado != EstadoPaquete.EN_CAMINO) {
            throw new IllegalStateException("Solo se puede entregar un paquete que esté EN_CAMINO.");
        }
        this.estado = EstadoPaquete.ENTREGADO;
        notificarCliente();
    }

    // Métodos para cumplir con las acciones de salida del diagrama ( / notificar... )
    private void notificarRepartidor() { /* Lógica de notificación */ }
    private void notificarAdministrador() { /* Lógica de notificación */ }
    private void notificarCliente() { /* Lógica de notificación */ }
}
