package com.itau.maquinacartao.model;

import java.time.LocalDateTime;

public class Transacao {
    private String id;
    private String tipo;
    private double valorBruto;
    private double taxa;
    private LocalDateTime dataHora;
    private boolean cancelada;

    public Transacao(String id, String tipo, double valorBruto, double taxa) {
        this.id = id;
        this.tipo = tipo;
        this.valorBruto = valorBruto;
        this.taxa = taxa;
        this.dataHora = LocalDateTime.now();
        this.cancelada = false;
    }

    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public double getValorBruto() {
        return valorBruto;
    }

    public double getTaxa() {
        return taxa;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public double getValorLiquido() {
        return cancelada ? 0.0 : valorBruto - taxa;
    }

    public String getStatus() {
        return cancelada ? "Cancelada" : "Concluída";
    }

    public void cancelar() {
        this.cancelada = true;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    @Override
    public String toString() {
        return "Transação " + id + " [" + tipo + "] - Valor: R$" + valorBruto +
               " | Taxa: R$" + taxa + " | Líquido: R$" + getValorLiquido() +
               " | Status: " + getStatus() + " | Data: " + dataHora;
    }
}
