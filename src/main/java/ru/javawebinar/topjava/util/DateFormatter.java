package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class DateFormatter implements Formatter<LocalDate> {
    private final String pattern;

    public DateFormatter(String pattern){
        this.pattern = pattern;
    }

    @Override
    public LocalDate parse(String text, Locale locale) {
        return LocalDate.parse(text, getDateFormat(locale));
    }

    @Override
    public String print(LocalDate date, Locale locale) {
        return date.format(getDateFormat(locale));
    }

    private DateTimeFormatter getDateFormat(Locale locale){
        return DateTimeFormatter.ofPattern(pattern).withLocale(locale);
    }
}
