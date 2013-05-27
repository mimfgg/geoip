package se.omik.geoip.model;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * @author Jeremy Comte
 */
public class Location {

    public int locId;
    public String country;
    public String region;
    public String city;
    public String postalCode;
    public Double latitude;
    public Double longitude;
    public String metroCode;
    public String areaCode;

    public Location(String line) {
        String cleanedLine = line.replaceAll("\"", "");
        String[] props = cleanedLine.split(",");
        this.locId = Integer.parseInt(props[0]);
        this.country = props[1];
        this.region = props[2];
        this.city = props[3];
        this.postalCode = props[4];
        this.latitude = Double.parseDouble(props[5]);
        this.longitude = Double.parseDouble(props[6]);
        if (props.length > 7) {
            this.metroCode = props[7];
        }
        if (props.length > 8) {
            this.areaCode = props[8];
        }
    }

    public Location(int locId, String country, String region, String city, String postalCode, Double latitude, Double longitude, String metroCode, String areaCode) {
        this.locId = locId;
        this.country = country;
        this.region = region;
        this.city = city;
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.metroCode = metroCode;
        this.areaCode = areaCode;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("locId", locId);
        json.put("country", country);
        json.put("region", region);
        json.put("city", city);
        json.put("postalCode", postalCode);
        json.put("latitude", latitude);
        json.put("longitude", longitude);
        json.put("metroCode", metroCode);
        json.put("areaCode", areaCode);
        return json;
    }

    public JSONObject toSmallJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("lat", latitude);
        json.put("lon", longitude);
        return json;
    }

    public int getLocId() {
        return locId;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getMetroCode() {
        return metroCode;
    }

    public String getAreaCode() {
        return areaCode;
    }
}