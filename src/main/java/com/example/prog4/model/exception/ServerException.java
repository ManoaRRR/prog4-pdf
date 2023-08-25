package com.example.prog4.model.exception;

import lombok.Getter;

public class ServerException extends RuntimeException {

    @Getter
    private final Integer code;

    public ServerException(String message, Integer code){
        super(message);
        this.code = code;
    }
}
