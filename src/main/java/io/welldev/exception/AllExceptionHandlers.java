package io.welldev.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class AllExceptionHandlers {


    @ExceptionHandler(InvalidRequestBodyException.class)
    public ResponseEntity<ErrorMessage> handleInvalidRequestBodyException(InvalidRequestBodyException exception){
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(exception.getBindingResult().getFieldError().getField() + " "
                + exception.getBindingResult().getFieldError().getDefaultMessage());
        
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorMessage);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleDataIntegrityViolationException(DataIntegrityViolationException exception){

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage("Data integrity violation");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }


    @ExceptionHandler(CustomerLoginException.class)
    public ResponseEntity<ErrorMessage> handleCustomerLoginException(CustomerLoginException exception){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getErrorMessage());
    }


}
