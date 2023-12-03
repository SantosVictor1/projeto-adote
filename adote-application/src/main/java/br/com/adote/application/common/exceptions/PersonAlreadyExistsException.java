package br.com.adote.application.common.exceptions;

import br.com.adote.application.enums.PersonType;
import lombok.Getter;

@Getter
public class PersonAlreadyExistsException extends ApplicationException {
    private final PersonType type;
    public PersonAlreadyExistsException(PersonType type, String message) {
        super(message);
        this.type = type;
    }
}
