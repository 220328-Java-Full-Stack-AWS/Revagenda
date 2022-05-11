package com.revature.revagenda.beans.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller is for health checking and metrics
 */

@RestController
public class HealthController {

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String ping() {
        return "pong!";
    }
}
