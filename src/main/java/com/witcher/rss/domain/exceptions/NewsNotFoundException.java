package com.witcher.rss.domain.exceptions;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
public class NewsNotFoundException extends NewsException {

    private static final String DEFAULT_MSG = "News with id=%s not found.";

    public NewsNotFoundException(long id) {
        super(String.format(DEFAULT_MSG, String.valueOf(id)));
    }
}
