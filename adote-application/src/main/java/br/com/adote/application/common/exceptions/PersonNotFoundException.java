package br.com.adote.application.common.exceptions;

import br.com.adote.application.enums.PersonType;
import lombok.Getter;

@Getter
public class PersonNotFoundException extends ApplicationException {
    private final PersonType type;
    public PersonNotFoundException(PersonType type, String message) {
        super(message);
        this.type = type;
    }
}
