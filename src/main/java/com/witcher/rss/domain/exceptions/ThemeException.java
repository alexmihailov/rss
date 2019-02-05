package com.witcher.rss.domain.exceptions;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
public abstract class ThemeException extends Exception {

    public ThemeException(String message) {
        super(message);
    }

    public ThemeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThemeException(Throwable cause) {
        super(cause);
    }
}
