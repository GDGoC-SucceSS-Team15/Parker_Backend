package com.si9nal.parker.parkingviolation.util;


import java.util.List;


public class SidoNameUtil {

    private SidoNameUtil(){

    }

    public static String standardizeSidoName(String sidoName) {
        if ("강원특별자치도".equals(sidoName)) {
            return "강원도";
        }
        return sidoName;
    }

    public static List<String> getPossibleSidoNames(String sidoName) {
        if ("강원도".equals(sidoName)) {
            return List.of("강원도", "강원특별자치도");
        }
        return List.of(sidoName);
    }
}

