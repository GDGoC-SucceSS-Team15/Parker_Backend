package com.si9nal.parker.global.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class GeometryUtil {

    /**
     * 현재 위치에서 특정 거리만큼 이동한 지점의 좌표 계산
     * @param baseLatitude 기준 위도
     * @param baseLongitude 기준 경도
     * @param distance 이동 거리 (km)
     * @param bearing 이동 방향 (도 단위, 0: 북, 90: 동, 180: 남, 270: 서)
     * @return 이동 후 좌표 (위도, 경도)
     */
    public static Location calculate(Double baseLatitude, Double baseLongitude, Double distance, Double bearing) {
        Double radianLatitude = toRadian(baseLatitude);
        Double radianLongitude = toRadian(baseLongitude);
        Double radianAngle = toRadian(bearing);
        Double distanceRadius = distance / 6371.01; // Radius of Earth in kilometers

        Double latitude = Math.asin(Math.sin(radianLatitude) * Math.cos(distanceRadius)
                + Math.cos(radianLatitude) * Math.sin(distanceRadius) * Math.cos(radianAngle));
        Double longitude = radianLongitude + Math.atan2(Math.sin(radianAngle) * Math.sin(distanceRadius)
                * Math.cos(radianLatitude), Math.cos(distanceRadius) - Math.sin(radianLatitude) * Math.sin(latitude));

        longitude = normalizeLongitude(longitude);

        return new Location(toDegree(latitude), toDegree(longitude));
    }

    private static Double toRadian(Double coordinate) {
        return coordinate * Math.PI / 180.0;
    }

    private static Double toDegree(Double coordinate) {
        return coordinate * 180.0 / Math.PI;
    }

    private static Double normalizeLongitude(Double longitude) {
        return (longitude + 540) % 360 - 180;
    }

    /**
     * 하버사인 공식을 기반으로 위도, 경도를 기반으로 두 지점 간 직선 거리를 계산
     * @param lat1 첫 번째 위도
     * @param lon1 첫 번째 경도
     * @param lat2 두 번째 위도
     * @param lon2 두 번째 경도
     * @return 두 지점 간 거리 (km)
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final double EARTH_RADIUS_KM = 6371; // R = 지구 반지름

        // 위도와 경도 차이를 라디안으로 변환
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        // a값 계산
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        // c값 계산 (atan2 함수로 최종 각도 변환)
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        //최종 거리 계산 (km 단위의 거리 반환)
        return EARTH_RADIUS_KM * c;
    }
}
