package se.omik.geoip.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Jeremy Comte
 */
public class GeoIPConfiguration extends Configuration{
    
    @Valid
    @NotNull
    @JsonProperty
    private DatabaseConfiguration database = new DatabaseConfiguration();

    public DatabaseConfiguration getDatabaseConfiguration() {
        return database;
    }
    
}