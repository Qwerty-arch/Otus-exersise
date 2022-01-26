package com.oshovskii.otus.utils;

import lombok.*;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class LocaleManager {
    private String local = "en-EN";
    private final MessageSource messageSource;

    public String translate(String fromString) {
        return messageSource.getMessage(
                fromString,
                null,
                Locale.forLanguageTag(local)
        );
    }
}
