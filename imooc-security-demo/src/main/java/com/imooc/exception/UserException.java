package com.imooc.exception;

public class UserException extends RuntimeException{
    private String id;

    public UserException(String id) {
        super("user not exist");
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
