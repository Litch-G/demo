package com.example.demo.exception;

public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;
    public CustomizeException(ICustomizErrorCode errorCode){
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public CustomizeException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
