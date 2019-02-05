package com.witcher.rss.domain.exceptions;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
public abstract class UserException extends Exception {

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(Throwable cause) {
        super(cause);
    }
}
