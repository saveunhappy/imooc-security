package com.imooc.dto;

public class FileInformation {
    private String path;

    public FileInformation(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
