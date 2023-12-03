package br.com.adote.application.common;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ResourceBundle {
    private final MessageSource messageSource;

    public ResourceBundle(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String message, String... args) {
        return messageSource.getMessage(message, args, LocaleContextHolder.getLocale());
    }
}
