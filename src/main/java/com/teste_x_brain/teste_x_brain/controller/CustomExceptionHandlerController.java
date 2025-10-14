package com.teste_x_brain.teste_x_brain.controller;

import com.teste_x_brain.teste_x_brain.exceptions.ApiErrorMessage;
import com.teste_x_brain.teste_x_brain.exceptions.DataInvalidaException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandlerController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorMessage> handleValidationErrors(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatusCode.valueOf(400), errors);
        return ResponseEntity.status(apiErrorMessage.getStatus()).body(apiErrorMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorMessage> handleExceptionDateError(){
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatusCode.valueOf(400), "Favor inserir uma data valida dia-mes-ano(dd-MM-yyyy)");
        return ResponseEntity.status(apiErrorMessage.getStatus()).body(apiErrorMessage);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorMessage> handleExceptionRequestParamError(){
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatusCode.valueOf(400), "Favor inserir uma url valida exemplo: http://localhost:8080/api/vendas?inicio=dd-MM-yyyy&fim=dd-MM-yyyy)");
        return ResponseEntity.status(apiErrorMessage.getStatus()).body(apiErrorMessage);
    }

    @ExceptionHandler(DataInvalidaException.class)
    public ResponseEntity<ApiErrorMessage> handleDataInvalidaError(DataInvalidaException ex){
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatusCode.valueOf(400), ex.getMessage());
        return ResponseEntity.status(apiErrorMessage.getStatus()).body(apiErrorMessage);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorMessage> handleDataNullError(MissingServletRequestParameterException ex){
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(HttpStatusCode.valueOf(400), ex.getParameterName() + " Não pode ser nulo");
        return ResponseEntity.status(apiErrorMessage.getStatus()).body(apiErrorMessage);
    }
}
