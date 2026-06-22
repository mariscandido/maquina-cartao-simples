package com.itau.maquinacartao.dto;

public class TransacaoRequest {
    private String tipo;
    private double valor;

    public TransacaoRequest() {
    }

    public TransacaoRequest(String tipo, double valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
