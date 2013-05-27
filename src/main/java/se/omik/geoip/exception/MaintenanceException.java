package se.omik.geoip.exception;

import javax.naming.ServiceUnavailableException;

/**
 * @author Jeremy Comte
 */
public class MaintenanceException extends ServiceUnavailableException{

    public MaintenanceException(String message) {
        super(message);
    }
}