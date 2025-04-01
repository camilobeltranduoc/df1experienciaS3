package com.duoc.veterinaria.model;

import java.util.List;

public class Factura {
    private int numero;
    private String cliente;
    private List<String> servicios;
    private double total;
    private boolean pagada;

    public Factura(int numero, String cliente, List<String> servicios, double total, boolean pagada) {
        this.numero = numero;
        this.cliente = cliente;
        this.servicios = servicios;
        this.total = total;
        this.pagada = pagada;
    }

    public int getNumero() { return numero; }
    public String getCliente() { return cliente; }
    public List<String> getServicios() { return servicios; }
    public double getTotal() { return total; }
    public boolean isPagada() { return pagada; }

    public void setPagada(boolean pagada) { this.pagada = pagada; }
}