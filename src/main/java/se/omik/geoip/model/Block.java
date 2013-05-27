package se.omik.geoip.model;

/**
 * @author Jeremy Comte
 */
public class Block {

    private long startIpNum;
    private long endIpNum;
    private int locId;
    
    public Block(String line) {
        String cleanedLine = line.replaceAll("\"", "");
        String[] props = cleanedLine.split(",");
        this.startIpNum = Long.parseLong(props[0]);
        this.endIpNum = Long.parseLong(props[1]);
        this.locId =  Integer.parseInt(props[2]);
    }

    public Block(int startIpNum, int endIpNum, int locId) {
        this.startIpNum = startIpNum;
        this.endIpNum = endIpNum;
        this.locId = locId;
    }

    public long getEndIpNum() {
        return endIpNum;
    }

    public long getStartIpNum() {
        return startIpNum;
    }

    public int getLocId() {
        return locId;
    }
}