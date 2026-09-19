package com.Arjun.rag.KnowSphere.exception;

import javax.swing.text.Document;

public class DocumentProcessingException extends RuntimeException{

    public DocumentProcessingException(String message){
        super(message);
    }

    public DocumentProcessingException(){
        super("Error in processing the document");
    }

    public DocumentProcessingException(String message, Throwable ex){
        super(message, ex);
    }
}
