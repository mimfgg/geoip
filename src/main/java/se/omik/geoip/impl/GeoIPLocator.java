package se.omik.geoip.impl;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import se.omik.geoip.exception.MaintenanceException;
import se.omik.geoip.model.Block;
import se.omik.geoip.model.ILocator;
import se.omik.geoip.model.ILocatorDAO;
import se.omik.geoip.model.Location;
import se.omik.geoip.utils.IPUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jeremy Comte
 */
public class GeoIPLocator implements ILocator {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Inject
    private ILocatorDAO dao;
    private boolean rebuilding = false;

    @Override
    public Optional<Location> locate(String ip) throws MaintenanceException {
        if (rebuilding) {
            throw new MaintenanceException("Database is rebuilding, please try again later");
        }
        long ipNum = IPUtils.ipToLong(ip);
        return Optional.fromNullable(dao.findByIP(ipNum));
    }

    @Override
    public void rebuild() throws MaintenanceException {
        if (rebuilding) {
            throw new MaintenanceException("Database is rebuilding, please try again later");
        }
        rebuilding = true;
        new Importer(new File("GeoLiteCity-Blocks.csv"), new File("GeoLiteCity-Location.csv")).start();
    }

    private class Importer extends Thread {

        private static final int BATCH_SIZE = 10000;
        private final File blocksFile;
        private final File locationsFile;

        public Importer(File blocksFile, File locationsFile) {
            this.blocksFile = blocksFile;
            this.locationsFile = locationsFile;
        }

        @Override
        public void run() {
            try {
                rebuildBlocks();
                rebuildLocations();
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            } finally {
                rebuilding = false;
            }
        }

        private void rebuildBlocks() throws IOException {
            BufferedReader reader = null;
            try {
                if (blocksFile.exists()) {
                    logger.info("deleting all ip blocks");
                    dao.deleteAllBlocks();
                    int blocksCount = countLines(blocksFile);
                    int insertedBlocks = 0;
                    logger.info("inserting " + blocksCount + " blocks");
                    reader = new BufferedReader(new FileReader(blocksFile));
                    ArrayList<Block> blocks = new ArrayList<Block>();

                    //Skip the first 2 lines of the file, copyrights and column names
                    if (reader.ready()) {
                        reader.readLine();
                        reader.readLine();
                    }

                    while (reader.ready()) {
                        String line = reader.readLine();
                        if (line != null) {
                            blocks.add(new Block(line));
                            if (blocks.size() >= BATCH_SIZE) {
                                dao.insertBlocks(blocks.iterator());
                                insertedBlocks += BATCH_SIZE;
                                logger.info("inserted: " + ((int) 100 * insertedBlocks / blocksCount) + " %");
                                blocks.clear();
                            }
                        }
                    }

                    if (!blocks.isEmpty()) {
                        dao.insertBlocks(blocks.iterator());
                        blocks.clear();
                    }

                    logger.info("done.");
                } else {
                    logger.warn("csv blocks file can't be found ... skipping rebuild");
                }
            } finally {
                rebuilding = false;
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
        }

        private void rebuildLocations() throws IOException {
            BufferedReader reader = null;
            try {
                if (locationsFile.exists()) {
                    logger.info("deleting all ip locations");
                    dao.deleteAllLocations();
                    int locationsCount = countLines(locationsFile);
                    logger.info("inserting " + locationsCount + " locations");
                    int insertedLocations = 0;
                    reader = new BufferedReader(new FileReader(locationsFile));
                    ArrayList<Location> locations = new ArrayList<Location>();

                    //Skip the first 2 lines of the file, copyrights and column names
                    if (reader.ready()) {
                        reader.readLine();
                        reader.readLine();
                    }

                    while (reader.ready()) {
                        String line = reader.readLine();
                        if (line != null) {
                            locations.add(new Location(line));
                            if (locations.size() >= BATCH_SIZE) {
                                dao.insertLocations(locations.iterator());
                                insertedLocations += BATCH_SIZE;
                                logger.info("inserted: " + ((int) 100 * insertedLocations / locationsCount) + " %");
                                locations.clear();
                            }
                        }
                    }

                    if (!locations.isEmpty()) {
                        dao.insertLocations(locations.iterator());
                        logger.info("done.");
                    }
                } else {
                    logger.warn("csv locations file can't be found ... skipping rebuild");
                }
            } finally {
                rebuilding = false;
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
        }

        private int countLines(File file) throws IOException {
            int lines = 0;
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                while (reader.ready()) {
                    reader.readLine();
                    lines++;
                }
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
            return lines;
        }
    }
}