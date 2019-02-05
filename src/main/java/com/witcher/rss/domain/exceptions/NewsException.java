package com.witcher.rss.domain.exceptions;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
public abstract class NewsException extends Exception {

    public NewsException(String message) {
        super(message);
    }

    public NewsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NewsException(Throwable cause) {
        super(cause);
    }
}
