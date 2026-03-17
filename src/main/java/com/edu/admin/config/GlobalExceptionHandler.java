package com.edu.admin.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import javax.naming.SizeLimitExceededException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = FileNotFoundException.class)
    public void handle(FileNotFoundException ex, HttpServletResponse response) throws IOException {

        ex.printStackTrace();

        System.out.println("handling file not found exception");
        response.sendError(404, ex.getMessage());

    }

    @ExceptionHandler(value = IOException.class)
    public void handle(IOException ex, HttpServletResponse response) throws IOException {

        ex.printStackTrace();

        System.out.println("handling io exception");
        response.sendError(500, ex.getMessage());

    }

    @ExceptionHandler(value = Exception.class)
    public void handle(Exception ex, HttpServletResponse response) throws IOException {

        ex.printStackTrace();

        System.out.println("handling io exception");
        response.sendError(500, ex.getMessage());

    }

    @ExceptionHandler({ SizeLimitExceededException.class, MultipartException.class,
            java.lang.IllegalStateException.class })
    public void handleError(HttpServletRequest req, Exception e) {
        // error("Request: " + req.getRequestURL() + " raised " + ex);

        System.out.println("exception" + e);
        System.out.println("url" +  req.getRequestURL());
        System.out.println("timestamp" +  new Date());
        System.out.println("error" +  e.getClass());
        System.out.println("message" +  e.getMessage());
        System.out.println("status" + HttpStatus.INTERNAL_SERVER_ERROR);

    }

}