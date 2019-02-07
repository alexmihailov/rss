package com.witcher.rss.domain.util;

import com.witcher.rss.api.data.ResultResponse;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import java.util.Objects;
import java.util.function.Predicate;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
public final class Preconditions {

    private Preconditions() {
    }

    /**
     * Создает исключение WebApplicationException.
     * @param httpStatus статус код ответа
     * @param status код ответа
     * @param message сообщение об ошибке
     * @return WebApplicationException
     */
    public static WebApplicationException webApplicationException(Response.Status httpStatus, ResultResponse.Status status,
                                                                  String message) {
        ResultResponse<?> response = new ResultResponse<>(status, message);
        return new WebApplicationException(message, Response.status(httpStatus).type(APPLICATION_JSON).entity(response).build());
    }

    /**
     * Создает исключение WebApplicationException с ошибкой.
     * @param httpStatus статус код ответа
     * @param message сообщение об ошибке
     * @return WebApplicationException
     */
    public static WebApplicationException webApplicationException(Response.Status httpStatus, String message) {
        ResultResponse<?> response = new ResultResponse<>(ResultResponse.Status.ERROR, message);
        return new WebApplicationException(message, Response.status(httpStatus).type(APPLICATION_JSON).entity(response).build());
    }

    /**
     * Проверить условие, если условие ложно, то выкинуть исключение.
     * @param data данные для проверки
     * @param predicate предикат с условием
     * @param message сообщение при ошибке
     * @param <T> тип данных
     */
    public static <T> void check(T data, Predicate<T> predicate, String message) {
        if (!predicate.test(data)) {
            throw webApplicationException(Response.Status.BAD_REQUEST, message);
        }
    }

    /**
     * Проверить, что данные не null, если null то выкинуть исключение.
     * @param data данные для проверки
     * @param message сообщение при ошибке
     * @param <T> тип данных
     */
    public static <T> void checkNotNull(T data, String message) {
        check(data, Objects::nonNull, message);
    }

    /**
     * Проверить, что строка не null и не пустая.
     * @param data строка для проверки
     * @param message сообщение при ошибке
     */
    public static void checkNotEmpty(String data, String message) {
        checkNotNull(data, message);
        check(data, s -> !s.isEmpty(), message);
    }
}
