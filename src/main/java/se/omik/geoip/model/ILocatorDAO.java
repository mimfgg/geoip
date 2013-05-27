package se.omik.geoip.model;

import java.util.Iterator;

/**
 * @author Jeremy Comte
 */
public interface ILocatorDAO {

    Location findByIP(long ip);

    void deleteAllBlocks();

    void insertBlocks(Iterator<Block> blocks);

    void deleteAllLocations();

    void insertLocations(Iterator<Location> locations);
}