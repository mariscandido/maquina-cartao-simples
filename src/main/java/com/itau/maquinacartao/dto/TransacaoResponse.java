package com.itau.maquinacartao.dto;

import java.time.LocalDateTime;

public class TransacaoResponse {
    private String id;
    private String tipo;
    private double valorBruto;
    private double taxa;
    private double valorLiquido;
    private LocalDateTime dataHora;
    private String status;

    public TransacaoResponse() {
    }

    public TransacaoResponse(String id, String tipo, double valorBruto, double taxa, 
                            double valorLiquido, LocalDateTime dataHora, String status) {
        this.id = id;
        this.tipo = tipo;
        this.valorBruto = valorBruto;
        this.taxa = taxa;
        this.valorLiquido = valorLiquido;
        this.dataHora = dataHora;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValorBruto() {
        return valorBruto;
    }

    public void setValorBruto(double valorBruto) {
        this.valorBruto = valorBruto;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }

    public double getValorLiquido() {
        return valorLiquido;
    }

    public void setValorLiquido(double valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
