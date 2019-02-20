package com.intuit.cg.backendtechassessment.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by pkumawat on 9/18/2018.
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException() {
        super("Invalid LowestBid/Increment amount.");
    }
}