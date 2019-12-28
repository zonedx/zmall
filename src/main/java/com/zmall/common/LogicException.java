package com.zmall.common;

public class LogicException extends RuntimeException {
    private int code;

    public LogicException(int code, String message) {
        super(message);
        this.code = code;
    }

    public LogicException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public LogicException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    protected LogicException(int code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
