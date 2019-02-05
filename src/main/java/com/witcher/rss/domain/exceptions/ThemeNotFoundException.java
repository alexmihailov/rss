package com.witcher.rss.domain.exceptions;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
public class ThemeNotFoundException extends ThemeException {

    private static final String DEFAULT_MSG = "Theme with id=%s not found.";

    public ThemeNotFoundException(long themeId) {
        super(String.format(DEFAULT_MSG, String.valueOf(themeId)));
    }
}
