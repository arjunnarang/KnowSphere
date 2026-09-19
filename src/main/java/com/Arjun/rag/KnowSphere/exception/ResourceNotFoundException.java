package com.Arjun.rag.KnowSphere.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(){
        super("Resource is not found!!");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable ex){
        super(message, ex);
    }
}
