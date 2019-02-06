package com.witcher.rss.api.data;

import lombok.Getter;

import java.io.Serializable;

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */

@Getter
public class ResultResponse<T> implements Serializable {

    private T data;
    private Meta meta;

    public ResultResponse(T data, Meta meta) {
        this.data = data;
        this.meta = meta;
    }

    public ResultResponse(Status status, String message) {
        this.meta = new Meta(message, status);
    }

    public ResultResponse(T data) {
        this(Status.OK, null);
        this.data = data;
    }

    @Getter
    public class Meta {

        private String message;
        private Status status;

        public Meta(String message, Status status) {
            this.message = message;
            this.status = status;
        }
    }

    public enum Status {
        OK, ERROR
    }
}
