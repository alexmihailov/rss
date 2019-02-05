package com.witcher.rss.domain.exceptions;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
public class UserNotFoundException extends UserException {

    private static final String DEFAULT_MSG = "User with id=%s not found.";

    public UserNotFoundException(long userId) {
        super(String.format(DEFAULT_MSG, String.valueOf(userId)));
    }
}
