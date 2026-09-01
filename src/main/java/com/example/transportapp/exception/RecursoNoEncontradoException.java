package com.example.transportapp.exception;

/** Excepción de negocio que señala que un registro solicitado no existe. */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
