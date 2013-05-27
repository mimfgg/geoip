package se.omik.geoip.resource;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import se.omik.geoip.exception.MaintenanceException;
import se.omik.geoip.model.ILocator;
import se.omik.geoip.model.Location;
import com.yammer.metrics.annotation.Metered;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.codehaus.jettison.json.JSONException;

/**
 * @author Jeremy Comte
 */
@Path("/")
public class GeoIPResource {

    @Inject
    private ILocator locator;

    @Metered
    @GET
    @Path("{ip}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLocation(@PathParam("ip") String ip) throws JSONException, MaintenanceException {
        ResponseBuilder response;
        if (ip.matches("\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b")) {
            Optional<Location> location = locator.locate(ip);
            if (location.isPresent()) {
                response = Response.ok(location.get().toJSON().toString());
            } else {
                response = Response.status(Response.Status.NOT_FOUND);
            }
        }else{
            response = Response.noContent();
        }
        return response.build();
    }

    @Metered
    @GET
    @Path("{ip}/small")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLocationSmall(@PathParam("ip") String ip) throws JSONException, MaintenanceException {
        ResponseBuilder response;
        Optional<Location> location = locator.locate(ip);
        if (location.isPresent()) {
            response = Response.ok(location.get().toSmallJSON().toString());
        } else {
            response = Response.status(Response.Status.NOT_FOUND);
        }
        return response.build();
    }

    @GET
    @Path("rebuild")
    @Produces(MediaType.TEXT_PLAIN)
    public Response rebuild() throws JSONException, MaintenanceException {
        locator.rebuild();
        return Response.ok("database is being rebuilt, service will resume as soon as possible").build();
    }
}