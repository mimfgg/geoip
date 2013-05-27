package se.omik.geoip;

import com.fiestacabin.dropwizard.guice.AutoConfigService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import se.omik.geoip.config.GeoIPConfiguration;
import se.omik.geoip.config.GeoIPModule;
import com.yammer.dropwizard.config.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jeremy Comte
 */
public class GeoIPService extends AutoConfigService<GeoIPConfiguration> {

    private static Logger logger = LoggerFactory.getLogger(GeoIPService.class);

    @Override
    public void run(GeoIPConfiguration configuration, Environment environment) throws Exception {
        Injector injector = Guice.createInjector(new GeoIPModule(configuration, environment));
        injector.injectMembers(this);
        runWithInjector(configuration, environment, injector);
    }

    public static void main(final String[] args) {
        try {
            new GeoIPService().run(args);
        } catch (Exception ex) {
            logger.error("cannot start", ex);
        }
    }
}
