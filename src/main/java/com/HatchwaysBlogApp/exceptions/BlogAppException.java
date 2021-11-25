package com.HatchwaysBlogApp.exceptions;

public class BlogAppException extends RuntimeException {

    public BlogAppException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public BlogAppException(String exMessage) {
        super(exMessage);
    }
}
