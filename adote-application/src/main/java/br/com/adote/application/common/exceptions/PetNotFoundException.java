package br.com.adote.application.common.exceptions;

public class PetNotFoundException extends ApplicationException {
    public PetNotFoundException(String message) {
        super(message);
    }
}
