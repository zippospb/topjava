package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class TimeFormatter implements Formatter<LocalTime> {
    private final String pattern;

    public TimeFormatter(String pattern){
        this.pattern = pattern;
    }

    @Override
    public LocalTime parse(String text, Locale locale) {
        return LocalTime.parse(text, getDateFormat(locale));
    }

    @Override
    public String print(LocalTime date, Locale locale) {
        return date.format(getDateFormat(locale));
    }

    private DateTimeFormatter getDateFormat(Locale locale){
        return DateTimeFormatter.ofPattern(pattern).withLocale(locale);
    }
}
