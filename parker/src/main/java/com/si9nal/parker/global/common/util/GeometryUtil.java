package com.si9nal.parker.global.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class GeometryUtil {

    public static Location calculate(Double baseLatitude, Double baseLongitude, Double distance, Double bearing) {
        Double radianLatitude = toRadian(baseLatitude);
        Double radianLongitude = toRadian(baseLongitude);
        Double radianAngle = toRadian(bearing);
        Double distanceRadius = distance / 6371.01; // Radius of Earth in kilometers

        Double latitude = Math.asin(Math.sin(radianLatitude) * Math.cos(distanceRadius)
                + Math.cos(radianLatitude) * Math.sin(distanceRadius) * Math.cos(radianAngle));
        Double longitude = radianLongitude + Math.atan2(Math.sin(radianAngle) * Math.sin(distanceRadius)
                * Math.cos(radianLatitude), Math.cos(distanceRadius) - Math.sin(radianLatitude) * Math.sin(latitude));

        return new Location(toDegree(latitude), toDegree(longitude));
    }

    private static Double toRadian(Double coordinate) {
        return coordinate * Math.PI / 180.0;
    }

    private static Double toDegree(Double coordinate) {
        return coordinate * 180.0 / Math.PI;
    }

    @Getter
    @AllArgsConstructor
    public static class Location {
        private Double latitude;
        private Double longitude;
    }
}
