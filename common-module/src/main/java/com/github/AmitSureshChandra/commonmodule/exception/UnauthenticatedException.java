package com.github.AmitSureshChandra.commonmodule.exception;

public class UnauthenticatedException extends RuntimeException {
    public UnauthenticatedException() {
        super();
    }

    public UnauthenticatedException(String msg) {
        super(msg);
    }
}
