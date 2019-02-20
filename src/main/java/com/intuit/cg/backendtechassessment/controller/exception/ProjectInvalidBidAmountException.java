package com.intuit.cg.backendtechassessment.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by pkumawat on 9/18/2018.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectInvalidBidAmountException extends RuntimeException {
    public ProjectInvalidBidAmountException() {
        super("Invalid Project Bid amount.");
    }
}
