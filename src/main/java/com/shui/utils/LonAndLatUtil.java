package com.shui.utils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.Data;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;


public class LonAndLatUtil {

    @Data
    private static class LonAndLat {
        private String lon;
        private String lat;
    }

    private static LonAndLat getLonAndLat(String address) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://restapi.amap.com/v3/geocode/geo?address=" + address + "&key=bb0ba8aa789730fcad2c904aa4639734";
        JSONObject res = restTemplate.getForObject(url, JSONObject.class);
        ArrayList geocodes = (ArrayList) res.get("geocodes");
        if (CollectionUtils.isNotEmpty(geocodes)) {
            LinkedHashMap geocodesValue = (LinkedHashMap) geocodes.get(0);
            String location = geocodesValue.get("location").toString();
            if (StringUtils.isNotBlank(location)) {
                String[] split = location.split(",");
                LonAndLat lngAndLat = new LonAndLat();
                lngAndLat.setLon(split[0]);
                lngAndLat.setLat(split[1]);
                return lngAndLat;
            }
        }
        return new LonAndLat();
    }

    public static void main(String[] args) {
        LonAndLat lngAndLat = getLonAndLat("昆明");
        System.out.println("(经度：" + lngAndLat.getLon() + ",纬度：" + lngAndLat.getLat() + ")");
    }
}
