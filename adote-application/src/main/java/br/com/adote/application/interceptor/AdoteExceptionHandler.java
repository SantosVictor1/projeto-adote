package br.com.adote.application.interceptor;

import br.com.adote.application.api.response.ErrorResponse;
import br.com.adote.application.common.AdoteErrorCode;
import br.com.adote.application.common.ResourceBundle;
import br.com.adote.application.common.exceptions.LegalPersonTypeException;
import br.com.adote.application.common.exceptions.PersonAlreadyExistsException;
import br.com.adote.application.common.exceptions.PersonNotFoundException;
import br.com.adote.application.common.exceptions.PetNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AdoteExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(AdoteExceptionHandler.class);
    private final ResourceBundle resourceBundle;

    public AdoteExceptionHandler(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleGenericException(Exception ex) {
        String key = AdoteErrorCode.GENERAL_ERROR.key();
        String code = AdoteErrorCode.GENERAL_ERROR.code();
        String originalError = ex.getClass().getName() + "-" + ex.getMessage();
        String message = resourceBundle.getMessage(key);

        logger.error("Exception: {}", originalError, ex);

        return new ErrorResponse(code, message, null, originalError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logger.error("MethodArgumentNotValidException ", ex);

        HashMap<String, List<String>> fields = new LinkedHashMap<>();
        String mustNotBeNullMessage = resourceBundle.getMessage(AdoteErrorCode.MUST_NOT_BE_NULL.key());
        String mustNotBeEmptyMessage = resourceBundle.getMessage(AdoteErrorCode.MUST_NOT_BE_EMPTY.key());
        String invalidEmail = resourceBundle.getMessage(AdoteErrorCode.INVALID_EMAIL.key());
        String invalidCpf = resourceBundle.getMessage(AdoteErrorCode.INVALID_CPF.key());
        String methodArgumentNotValid = resourceBundle.getMessage(AdoteErrorCode.METHOD_ARGUMENT_INVALID.key());

        for (FieldError field : ex.getBindingResult().getFieldErrors()) {
            fields.computeIfAbsent(field.getField(), f -> {
                LinkedList<String> list = new LinkedList<>();
                switch (Objects.requireNonNull(field.getCode())) {
                    case "NotNull" -> list.add(mustNotBeNullMessage);
                    case "NotEmpty" -> list.add(mustNotBeEmptyMessage);
                    case "Email" -> list.add(invalidEmail);
                    case "CPF" -> list.add(invalidCpf);
                    default -> list.add(methodArgumentNotValid);
                }
                return list;
            });
        }

        return new ErrorResponse(
            AdoteErrorCode.METHOD_ARGUMENT_INVALID.code(), methodArgumentNotValid, fields, null
        );
    }

    @ExceptionHandler(PersonAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ErrorResponse handlePersonAlreadyExistsException(PersonAlreadyExistsException ex) {
        logger.error("PersonAlreadyExistsException ", ex);

        String key = AdoteErrorCode.PERSON_ALREADY_EXISTS.key();
        String code = AdoteErrorCode.PERSON_ALREADY_EXISTS.code();
        String personType = resourceBundle.getMessage(ex.getType().name().toLowerCase());
        String message = resourceBundle.getMessage(key, personType, ex.getMessage());

        return new ErrorResponse(code, message, null, null);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handlePersonNotFoundException(PersonNotFoundException ex) {
        logger.error("PersonNotFoundException ", ex);

        String key = AdoteErrorCode.PERSON_NOT_FOUND.key();
        String code = AdoteErrorCode.PERSON_NOT_FOUND.code();
        String personType = resourceBundle.getMessage(ex.getType().name().toLowerCase());
        String message = resourceBundle.getMessage(key, personType, ex.getMessage());

        return new ErrorResponse(code, message, null, null);
    }

    @ExceptionHandler(LegalPersonTypeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleLegalPersonTypeException(LegalPersonTypeException ex) {
        logger.error("LegalPersonTypeException ", ex);

        String key = AdoteErrorCode.LEGAL_PERSON_TYPE.key();
        String code = AdoteErrorCode.LEGAL_PERSON_TYPE.code();
        String message = resourceBundle.getMessage(key);

        return new ErrorResponse(code, message, null, null);
    }

    @ExceptionHandler(PetNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handlePetNotFoundException(PetNotFoundException ex) {
        logger.error("PetNotFoundException ", ex);

        String key = AdoteErrorCode.PET_NOT_FOUND.key();
        String code = AdoteErrorCode.PET_NOT_FOUND.code();
        String message = resourceBundle.getMessage(key);

        return new ErrorResponse(code, message, null, null);
    }
}
