package com.itau.maquinacartao;

import com.itau.maquinacartao.exception.TransacaoException;
import com.itau.maquinacartao.model.Transacao;
import com.itau.maquinacartao.service.MaquinaCartaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MaquinaCartaoServiceTest {
    @Autowired
    private MaquinaCartaoService service;

    @Test
    public void testCriarTransacaoCreditoValida() throws TransacaoException {
        Transacao transacao = service.processarTransacao("CREDITO", 100.0);
        assertNotNull(transacao);
        assertEquals("CREDITO", transacao.getTipo());
        assertEquals(100.0, transacao.getValorBruto());
        assertEquals(5.0, transacao.getTaxa());
        assertEquals(95.0, transacao.getValorLiquido());
        assertEquals("Concluída", transacao.getStatus());
    }

    @Test
    public void testCriarTransacaoDebitoValida() throws TransacaoException {
        Transacao transacao = service.processarTransacao("DEBITO", 100.0);
        assertNotNull(transacao);
        assertEquals("DEBITO", transacao.getTipo());
        assertEquals(100.0, transacao.getValorBruto());
        assertEquals(2.0, transacao.getTaxa());
        assertEquals(98.0, transacao.getValorLiquido());
        assertEquals("Concluída", transacao.getStatus());
    }

    @Test
    public void testCalculoTaxaCredito() throws TransacaoException {
        Transacao transacao = service.processarTransacao("CREDITO", 200.0);
        assertEquals(10.0, transacao.getTaxa());
        assertEquals(190.0, transacao.getValorLiquido());
    }

    @Test
    public void testCalculoTaxaDebito() throws TransacaoException {
        Transacao transacao = service.processarTransacao("DEBITO", 500.0);
        assertEquals(10.0, transacao.getTaxa());
        assertEquals(490.0, transacao.getValorLiquido());
    }

    @Test
    public void testCancelamentoTransacao() throws TransacaoException {
        Transacao transacao = service.processarTransacao("CREDITO", 100.0);
        assertFalse(transacao.isCancelada());
        assertEquals(95.0, transacao.getValorLiquido());

        Transacao transacaoCancelada = service.cancelarTransacao(transacao.getId());
        assertTrue(transacaoCancelada.isCancelada());
        assertEquals(0.0, transacaoCancelada.getValorLiquido());
    }

    @Test
    public void testExcecaoValorInvalido() {
        assertThrows(TransacaoException.class, () -> {
            service.processarTransacao("CREDITO", 0);
        });

        assertThrows(TransacaoException.class, () -> {
            service.processarTransacao("DEBITO", -50.0);
        });
    }

    @Test
    public void testExcecaoTipoInvalido() {
        assertThrows(TransacaoException.class, () -> {
            service.processarTransacao("PIX", 100.0);
        });

        assertThrows(TransacaoException.class, () -> {
            service.processarTransacao("", 100.0);
        });
    }

    @Test
    public void testListagemTransacoes() throws TransacaoException {
        service.processarTransacao("CREDITO", 100.0);
        service.processarTransacao("DEBITO", 200.0);
        service.processarTransacao("CREDITO", 300.0);

        var transacoes = service.listarTransacoes();
        assertEquals(3, transacoes.size());
    }

    @Test
    public void testCancelamentoTransacaoJaCancelada() throws TransacaoException {
        Transacao transacao = service.processarTransacao("CREDITO", 100.0);
        service.cancelarTransacao(transacao.getId());

        assertThrows(TransacaoException.class, () -> {
            service.cancelarTransacao(transacao.getId());
        });
    }

    @Test
    public void testCancelamentoTransacaoInexistente() {
        assertThrows(TransacaoException.class, () -> {
            service.cancelarTransacao("ID_INEXISTENTE");
        });
    }

    @Test
    public void testBuscaTransacao() throws TransacaoException {
        Transacao transacao = service.processarTransacao("CREDITO", 100.0);
        Transacao encontrada = service.buscarTransacao(transacao.getId());
        assertNotNull(encontrada);
        assertEquals(transacao.getId(), encontrada.getId());
    }

    @Test
    public void testBuscaTransacaoInexistente() {
        assertThrows(TransacaoException.class, () -> {
            service.buscarTransacao("ID_INEXISTENTE");
        });
    }
}
