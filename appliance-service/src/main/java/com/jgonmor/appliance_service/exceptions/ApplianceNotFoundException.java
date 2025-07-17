package com.jgonmor.appliance_service.exceptions;

public class ApplianceNotFoundException extends RuntimeException {

    public ApplianceNotFoundException(String message) {
        super(message);
    }
}
