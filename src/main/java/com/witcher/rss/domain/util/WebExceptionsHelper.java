package com.witcher.rss.domain.util;

import com.witcher.rss.api.data.ResultResponse;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
public final class WebExceptionsHelper {

    private WebExceptionsHelper() {
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
}
