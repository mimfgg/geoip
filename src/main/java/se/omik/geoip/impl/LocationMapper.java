package se.omik.geoip.impl;

import se.omik.geoip.model.Location;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 *
 * @author Jeremy Comte
 */
public class LocationMapper implements ResultSetMapper<Location> {

    @Override
    public Location map(int i, ResultSet rs, StatementContext sc) throws SQLException {
        return new Location(
                rs.getInt("locId"),
                rs.getString("country"),
                rs.getString("region"),
                rs.getString("city"),
                rs.getString("postalCode"),
                rs.getDouble("latitude"),
                rs.getDouble("longitude"),
                rs.getString("metroCode"),
                rs.getString("areaCode"));
    }
}
