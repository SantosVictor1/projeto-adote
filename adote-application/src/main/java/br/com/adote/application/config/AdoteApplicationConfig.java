package br.com.adote.application.config;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@SpringBootApplication(scanBasePackages = {"br.com.adote.application"})
@EnableJpaRepositories(basePackages = {"br.com.adote.application.repository"})
@EntityScan(value = {"br.com.adote.application.domain"})
@Configuration
public class AdoteApplicationConfig {
    @Bean
    public LocaleResolver localeResolver() {
        return new SmartLocaleResolver();
    }

    static class SmartLocaleResolver extends AcceptHeaderLocaleResolver {
        @Override
        public Locale resolveLocale(HttpServletRequest request) {
            String language = request.getHeader("Accept-Language");

            language = language != null ? language : "en_US";

            if (!language.isEmpty()) {
                String[] locale = language.split("_");

                if(locale.length == 2){
                    return new Locale(locale[0], locale[1].toUpperCase());
                }
            }

            return Locale.getDefault();
        }
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages");
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true);

        return source;
    }
}
