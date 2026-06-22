package com.itau.maquinacartao.service;

import com.itau.maquinacartao.dto.TransacaoResponse;
import com.itau.maquinacartao.exception.TransacaoException;
import com.itau.maquinacartao.model.Transacao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MaquinaCartaoService {
    private static final double TAXA_CREDITO = 0.05;
    private static final double TAXA_DEBITO = 0.02;
    private static final String TIPO_CREDITO = "CREDITO";
    private static final String TIPO_DEBITO = "DEBITO";

    private final List<Transacao> transacoes;

    public MaquinaCartaoService() {
        this.transacoes = new ArrayList<>();
    }

    public Transacao processarTransacao(String tipo, double valor) throws TransacaoException {
        if (valor <= 0) {
            throw new TransacaoException("Valor da transação deve ser maior que zero");
        }

        if (!tipo.equalsIgnoreCase(TIPO_CREDITO) && !tipo.equalsIgnoreCase(TIPO_DEBITO)) {
            throw new TransacaoException("Tipo de transação inválido. Use CREDITO ou DEBITO");
        }

        String tipoNormalizado = tipo.toUpperCase();
        double taxa = calcularTaxa(tipoNormalizado, valor);
        String id = gerarId();

        Transacao transacao = new Transacao(id, tipoNormalizado, valor, taxa);
        transacoes.add(transacao);

        return transacao;
    }

    public Transacao cancelarTransacao(String id) throws TransacaoException {
        Transacao transacao = buscarTransacaoPorId(id);
        if (transacao == null) {
            throw new TransacaoException("Transação não encontrada com ID: " + id);
        }

        if (transacao.isCancelada()) {
            throw new TransacaoException("Transação já está cancelada");
        }

        transacao.cancelar();
        return transacao;
    }

    public List<Transacao> listarTransacoes() {
        return new ArrayList<>(transacoes);
    }

    public Transacao buscarTransacao(String id) throws TransacaoException {
        Transacao transacao = buscarTransacaoPorId(id);
        if (transacao == null) {
            throw new TransacaoException("Transação não encontrada com ID: " + id);
        }
        return transacao;
    }

    public TransacaoResponse toResponse(Transacao transacao) {
        return new TransacaoResponse(
            transacao.getId(),
            transacao.getTipo(),
            transacao.getValorBruto(),
            transacao.getTaxa(),
            transacao.getValorLiquido(),
            transacao.getDataHora(),
            transacao.getStatus()
        );
    }

    public List<TransacaoResponse> toResponseList(List<Transacao> transacoes) {
        List<TransacaoResponse> responses = new ArrayList<>();
        for (Transacao t : transacoes) {
            responses.add(toResponse(t));
        }
        return responses;
    }

    private double calcularTaxa(String tipo, double valor) {
        if (tipo.equals(TIPO_CREDITO)) {
            return valor * TAXA_CREDITO;
        } else {
            return valor * TAXA_DEBITO;
        }
    }

    private String gerarId() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private Transacao buscarTransacaoPorId(String id) {
        return transacoes.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
