package com.cashonline.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserNotFoundException extends RuntimeException {

    private final static String ID_NOT_FOUND = "Id %s is not found";

    public UserNotFoundException(String id){
        super(String.format(ID_NOT_FOUND, id));
    }
}



