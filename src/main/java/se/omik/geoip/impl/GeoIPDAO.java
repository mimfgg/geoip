package se.omik.geoip.impl;

import se.omik.geoip.model.Block;
import se.omik.geoip.model.ILocatorDAO;
import se.omik.geoip.model.Location;
import java.util.Iterator;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * @author Jeremy Comte
 */
@RegisterMapper(LocationMapper.class)
public interface GeoIPDAO extends ILocatorDAO {

    @SqlQuery("select * from locations l JOIN blocks b ON l.locId = b.locId where b.startIpNum <= :ip order by startIpNum desc")
    @Override
    Location findByIP(@Bind("ip") long ip);

    @SqlUpdate("delete from blocks")
    @Override
    void deleteAllBlocks();

    @SqlBatch("insert into blocks (startIpNum, endIpNum, locId) values (:startIpNum, :endIpNum, :locId)")
    @Override
    void insertBlocks(@BindBean Iterator<Block> blocks);

    @SqlUpdate("delete from locations")
    @Override
    void deleteAllLocations();

    @SqlBatch("insert into locations (locId, country, region, city, postalCode, latitude, longitude, metroCode, areaCode) "
            + "values (:locId, :country, :region, :city, :postalCode, :latitude, :longitude, :metroCode, :areaCode)")
    @Override
    void insertLocations(@BindBean Iterator<Location> locations);
}