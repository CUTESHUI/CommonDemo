package com.shui.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisGeoUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 增加name位置
     */
    public Long addGeoLocation(String geoKey, String name, Double x, Double y) {
        try {
            return redisTemplate.opsForGeo().add(geoKey, new Point(x, y), name);
        } catch (Exception exception) {
            throw new RuntimeException("addGeoLocation error.", exception);
        }
    }

    /**
     * 删除name位置
     */
    public Long deleteGeoLocation(String geoKey, String name) {
        try {
            return redisTemplate.opsForZSet().remove(geoKey, name);
        } catch (Exception exception) {
            throw new RuntimeException("deleteGeoLocation error.", exception);
        }
    }

    /**
     * 获取name位置
     */
    public List<Point> getGeoLocationByName(String geoKey, String name) {
        try {
            return redisTemplate.opsForGeo().position(geoKey, name);
        } catch (Exception e) {
            throw new RuntimeException("getGetLocation error.", e);
        }
    }

    /**
     * 获取name集合位置
     */
    public List<Point> getGeoLocationByNames(String geoKey, String... names) {
        try {
            return redisTemplate.opsForGeo().position(geoKey, names);
        } catch (Exception e) {
            throw new RuntimeException("getGetLocation error.", e);
        }
    }


    /**
     * 计算name1, name2相隔距离
     */
    public Distance getDistance(String geoKey, String name1, String name2, RedisGeoCommands.DistanceUnit unit) {
        try {
            return redisTemplate.opsForGeo().distance(geoKey, name1, name2, unit);
        } catch (Exception e) {
            throw new RuntimeException("getDistance error.", e);
        }
    }

    /**
     * 获取name附近distance内的limit个用户
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> getRadiusByName(String geoKey, String name, Integer distance, RedisGeoCommands.DistanceUnit unit, Integer limit) {
        try {
            Distance distances = new Distance(distance, unit);
            RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs
                    .newGeoRadiusArgs()
                    .includeDistance() // 包含具中心坐标距离信息
                    .includeCoordinates() // 包含坐标信息
                    .sortAscending() // 距离升序排序
                    .limit(limit);
            return redisTemplate.opsForGeo().radius(geoKey, name, distances, args);
        } catch (Exception e) {
            throw new RuntimeException("getDistance error.", e);
        }
    }

    /***
     * 获取x y附近distance内的limit个用户
     */
    public GeoResults<RedisGeoCommands.GeoLocation<String>> getRadiusByXY(String geoKey, Double x, Double y, Integer distance, RedisGeoCommands.DistanceUnit unit, Integer limit) {
        try {
            Circle circle = new Circle(new Point(x, y), new Distance(distance, unit));
            RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs
                    .newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending().limit(limit);
            return redisTemplate.opsForGeo().radius(geoKey, circle, args);
        } catch (Exception e) {
            throw new RuntimeException("getDistance error.", e);
        }
    }

    /***
     * 返回一个或多个位置元素的 Geohash 表示
     */
    public List<String> geoHash(String key, String... names) {
        return redisTemplate.opsForGeo().hash(key, names);
    }

}
