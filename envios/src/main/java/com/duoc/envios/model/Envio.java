package com.duoc.envios.model;

public class Envio {
    private int id;
    private String destinatario;
    private String paisDestino;
    private String ubicacionActual;
    private String estado; 

    public Envio(int id, String destinatario, String paisDestino, String ubicacionActual, String estado) {
        this.id = id;
        this.destinatario = destinatario;
        this.paisDestino = paisDestino;
        this.ubicacionActual = ubicacionActual;
        this.estado = estado;
    }

    public int getId() { return id; }
    public String getDestinatario() { return destinatario; }
    public String getPaisDestino() { return paisDestino; }
    public String getUbicacionActual() { return ubicacionActual; }
    public String getEstado() { return estado; }

    public void setUbicacionActual(String ubicacionActual) { this.ubicacionActual = ubicacionActual; }
    public void setEstado(String estado) { this.estado = estado; }
}
