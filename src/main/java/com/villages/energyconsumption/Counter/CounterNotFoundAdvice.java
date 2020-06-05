package com.villages.energyconsumption.Counter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CounterNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(CounterNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String counterNotFoundHandler(CounterNotFoundException ex){
        return ex.getMessage();
    }
}
