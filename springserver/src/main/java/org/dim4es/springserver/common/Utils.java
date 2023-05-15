package org.dim4es.springserver.common;

public class Utils {

    private Utils() {
    }

    public static String concatenateCoordinates(String latitude, String longitude) {
        return latitude + " " + longitude;
    }

    public static String[] parseCoordinates(String coordinates) {
        return coordinates.split(" ");
    }
}
