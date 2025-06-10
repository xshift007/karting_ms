package cl.kartingrm.client_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> badInput(MethodArgumentNotValidException ex){
        var errs = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(f -> Map.of(
                        "field", f.getField(),
                        "msg",   f.getDefaultMessage()))
                .toList();
        return ResponseEntity.badRequest().body(errs);
    }
}
