package com.goeuro.model;

/**
 * @author Pelumi<pelumi@maven.ai>
 *         Created on 05/07/15 at 10:05.
 */
public class GeoPosition {
    private double latitude;
    private double longitude;

    public GeoPosition() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
