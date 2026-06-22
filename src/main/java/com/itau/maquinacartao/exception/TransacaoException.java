package com.itau.maquinacartao.exception;

public class TransacaoException extends Exception {
    public TransacaoException(String message) {
        super(message);
    }

    public TransacaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
