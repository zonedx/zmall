package com.zmall.controller.common;

public class ControllerException extends RuntimeException {
    private int code;

    public ControllerException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ControllerException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ControllerException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    protected ControllerException(int code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
