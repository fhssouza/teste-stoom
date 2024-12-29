package br.com.stoom.store.exception;

public class HandlerDataIntegrityViolationException extends RuntimeException{

    public HandlerDataIntegrityViolationException(String message){
        super(message);
    }
}
