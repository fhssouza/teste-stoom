package br.com.stoom.store.exception;

public class HandlerBadRequestException extends RuntimeException{

    public HandlerBadRequestException(String message){
        super(message);
    }
}
