package com.edu.admin.exception;

public class ApiPermissionDeniedException extends RuntimeException {
    public ApiPermissionDeniedException(String msg) {
        super(msg);
    }
}
