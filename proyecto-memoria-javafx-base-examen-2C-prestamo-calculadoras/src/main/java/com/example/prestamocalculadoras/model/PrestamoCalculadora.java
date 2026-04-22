package com.example.prestamocalculadoras.model;

public class PrestamoCalculadora {
    private String nombreSolicitante;
    private String cantidad;
    private String tipoCalculadora;

    public PrestamoCalculadora(String nombreSolicitante, String cantidad, String tipoCalculadora) {
        this.nombreSolicitante = nombreSolicitante;
        this.cantidad = cantidad;
        this.tipoCalculadora = tipoCalculadora;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipoCalculadora() {
        return tipoCalculadora;
    }

    public void setTipoCalculadora(String tipoCalculadora) {
        this.tipoCalculadora = tipoCalculadora;
    }

    @Override
    public String toString() {
        return nombreSolicitante + " | " + cantidad + " | " + tipoCalculadora;
    }
}
