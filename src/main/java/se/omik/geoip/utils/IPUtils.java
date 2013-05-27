package se.omik.geoip.utils;

/**
 * @author Jeremy Comte
 */
public class IPUtils {

    private IPUtils() {
    }

    public static long ipToLong(String addr) {
        String[] addrArray = addr.split("\\.");

        long num = 0;
        for (int i = 0; i < addrArray.length; i++) {
            int power = 3 - i;
            num += ((Integer.parseInt(addrArray[i]) % 256 * Math.pow(256, power)));
        }
        return num;
    }
}
