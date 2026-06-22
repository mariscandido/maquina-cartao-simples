package com.itau.maquinacartao.controller;

import com.itau.maquinacartao.dto.TransacaoRequest;
import com.itau.maquinacartao.dto.TransacaoResponse;
import com.itau.maquinacartao.exception.TransacaoException;
import com.itau.maquinacartao.model.Transacao;
import com.itau.maquinacartao.service.MaquinaCartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    @Autowired
    private MaquinaCartaoService service;

    @PostMapping
    public ResponseEntity<?> processar(@RequestBody TransacaoRequest request) {
        try {
            Transacao transacao = service.processarTransacao(request.getTipo(), request.getValor());
            TransacaoResponse response = service.toResponse(transacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (TransacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable String id) {
        try {
            Transacao transacao = service.cancelarTransacao(id);
            TransacaoResponse response = service.toResponse(transacao);
            return ResponseEntity.ok(response);
        } catch (TransacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<TransacaoResponse>> listar() {
        List<Transacao> transacoes = service.listarTransacoes();
        List<TransacaoResponse> responses = service.toResponseList(transacoes);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable String id) {
        try {
            Transacao transacao = service.buscarTransacao(id);
            TransacaoResponse response = service.toResponse(transacao);
            return ResponseEntity.ok(response);
        } catch (TransacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/comprovante")
    public ResponseEntity<?> comprovante(@PathVariable String id) {
        try {
            Transacao transacao = service.buscarTransacao(id);
            StringBuilder sb = new StringBuilder();
            sb.append("========================================\n");
            sb.append("        COMPROVANTE DE TRANSAÇÃO       \n");
            sb.append("========================================\n");
            sb.append(transacao.toString()).append("\n");
            sb.append("========================================\n");
            return ResponseEntity.ok(sb.toString());
        } catch (TransacaoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
