package se.omik.geoip.config;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import se.omik.geoip.impl.GeoIPDAO;
import se.omik.geoip.impl.GeoIPLocator;
import se.omik.geoip.model.ILocator;
import se.omik.geoip.model.ILocatorDAO;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.jdbi.DBIFactory;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jeremy Comte
 */
public class GeoIPModule extends AbstractModule {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private final GeoIPConfiguration configuration;
    private final Environment environment;

    public GeoIPModule(GeoIPConfiguration configuration, Environment environment) {
        this.configuration = configuration;
        this.environment = environment;
    }

    @Override
    protected void configure() {
        try {
            // Here we configure guice to inject our implementations and singletons in the application
            DBIFactory factory = new DBIFactory();
            DBI jdbi = factory.build(environment, configuration.getDatabaseConfiguration(), "mysql");
            GeoIPDAO geoIPDAO = jdbi.onDemand(GeoIPDAO.class);
            bind(ILocatorDAO.class).toInstance(geoIPDAO);
            bind(ILocator.class).to(GeoIPLocator.class).in(Scopes.SINGLETON);
        } catch (ClassNotFoundException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}