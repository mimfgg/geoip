package se.omik.geoip.model;

import com.google.common.base.Optional;
import se.omik.geoip.exception.MaintenanceException;

/**
 * @author Jeremy Comte
 */
public interface ILocator {

    Optional<Location> locate(String ip) throws MaintenanceException;
    
    void rebuild() throws MaintenanceException;
    
}