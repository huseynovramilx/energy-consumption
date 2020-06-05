package com.villages.energyconsumption.Village;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class VillageNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(VillageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String villageNotFoundHandler(VillageNotFoundException ex){
        return ex.getMessage();
    }
}
