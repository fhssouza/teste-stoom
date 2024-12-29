package br.com.stoom.store.exception.handler;

import br.com.stoom.store.dto.error.StandardError;
import br.com.stoom.store.dto.error.ValidationError;
import br.com.stoom.store.exception.HandlerBadRequestException;
import br.com.stoom.store.exception.HandlerDataIntegrityViolationException;
import br.com.stoom.store.exception.HandlerIllegalArgumentException;
import br.com.stoom.store.exception.HandlerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(br.com.stoom.store.exception.IllegalArgumentException.class)
//    public ResponseEntity<ErrorResponse> handleInvalidArgumentException(br.com.stoom.store.exception.IllegalArgumentException ex) {
//        return new ResponseEntity<>(new ErrorResponse("Invalid input", ex.getMessage()), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(NotFoundException ex) {
//        return new ResponseEntity<>(new ErrorResponse("Not found", ex.getMessage()), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(br.com.stoom.store.exception.Exception.class)
//    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
//        return new ResponseEntity<>(new ErrorResponse("Internal Server Error", "An error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(HandlerNotFoundException.class)
    public ResponseEntity<StandardError> notFoundException(HandlerNotFoundException n,
                                                           HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setHttpStatus(HttpStatus.NOT_FOUND);
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setError("Resource not found");
        error.setMessage(n.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(HandlerBadRequestException.class)
    public ResponseEntity<StandardError> badRequestException(HandlerBadRequestException b, HttpServletRequest request) {

        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setHttpStatus(HttpStatus.BAD_REQUEST);
        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        error.setError("Database Exception");
        error.setMessage(b.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HandlerDataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrityViolationException(HandlerDataIntegrityViolationException d,
                                                                         HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setHttpStatus(HttpStatus.CONFLICT);
        error.setStatusCode(HttpStatus.CONFLICT.value());
        error.setError("Data Integrity Violation Exception");
        error.setMessage(d.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(HandlerIllegalArgumentException.class)
    public ResponseEntity<StandardError> handleIllegalArgumentException(HandlerIllegalArgumentException ex, HttpServletRequest request) {
        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setHttpStatus(HttpStatus.BAD_REQUEST);
        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        error.setError("Invalid Argument");
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> methodArgumentNotValidException(MethodArgumentNotValidException v,
                                                                           HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        ValidationError err = new ValidationError();
        err.setTimestamp(Instant.now());
        err.setHttpStatus(status);
        err.setStatusCode(status.value());
        err.setError("Validation Exception");
        err.setMessage("Campos com erros de Validação");
        err.setPath(request.getRequestURI());

        for (FieldError f : v.getBindingResult().getFieldErrors()) {
            err.addError(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(err);

    }
}
