package com.shui.controller;

import com.shui.domain.influxdb.Sensor;
import com.shui.utils.InfluxdbUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "influx测试")
@RequestMapping("/influx")
public class InfluxTestController {

    @Autowired
    private InfluxDB influxDB;
    @Autowired
    private InfluxdbUtils influxdbUtils;

    @GetMapping("/test")
    @ApiOperation("test")
    public Object test() {
        Query query = new Query("show databases");
        return influxDB.query(query);
    }

    @GetMapping("insert1")
    @ApiOperation("插入单条1")
    public void insert1() {
        Point point = Point.measurement("sensor")
                .tag("deviceId", "1")
                .addField("temp", "123")
                .addField("voltage", "345")
                .addField("field1", "fdg")
                .addField("field2", "ghj")
                .build();
        influxDB.write(point);
    }

    @PostMapping("insert2")
    @ApiOperation("插入单条2")
    public void insert2(@RequestBody Sensor sensor) {
        influxdbUtils.insertOne(sensor);
    }

    @GetMapping("batchInsert1")
    @ApiOperation("批量插入记录1")
    public void batchInsert1() {
        List<Sensor> sensorList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Sensor sensor = Sensor.builder()
                    .deviceId(i + "").field1(i + "").field2(i + "").temp(i + "").voltage(i + "").build();
            sensorList.add(sensor);
        }
        influxdbUtils.insertBatchByRecords(sensorList);
    }

    @GetMapping("batchInsert2")
    @ApiOperation("批量插入记录2")
    public void batchInsert2() {
        List<Sensor> sensorList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Sensor sensor = Sensor.builder()
                    .deviceId(i + "").field1(i + "").field2(i + "").temp(i + "").voltage(i + "").build();
            sensorList.add(sensor);
        }
        influxdbUtils.insertBatchByPoints(sensorList);
    }

    @GetMapping("list1")
    @ApiOperation("获取数据1")
    public List list1(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        String pageQuery = " LIMIT " + limit + " OFFSET " + (page - 1) * limit;

        String queryCondition = "";
        String queryCmd = "SELECT * FROM sensor"
                // 查询指定设备下的日志信息
                // 要指定从 RetentionPolicyName.measurement中查询指定数据,默认的策略可以不加；
                // + 策略name + "." + measurement
                // 添加查询条件(注意查询条件选择tag值,选择field数值会严重拖慢查询速度)
                + queryCondition
                // 查询结果需要按照时间排序
                + " ORDER BY time DESC"
                // 添加分页查询条件
                + pageQuery;

        return influxdbUtils.fetchRecords(queryCmd);
    }

    @GetMapping("list2")
    @ApiOperation("获取数据2")
    public List<Sensor> datas1(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        String pageQuery = " LIMIT " + limit + " OFFSET " + (page - 1) * limit;

        String queryCondition = "";
        String queryCmd = "SELECT * FROM sensor"
                // 查询指定设备下的日志信息
                // 要指定从 RetentionPolicyName.measurement中查询指定数据,默认的策略可以不加；
                // + 策略name + "." + measurement
                // 添加查询条件(注意查询条件选择tag值,选择field数值会严重拖慢查询速度)
                + queryCondition
                // 查询结果需要按照时间排序
                + " ORDER BY time DESC"
                // 添加分页查询条件
                + pageQuery;
        return influxdbUtils.fetchResults(queryCmd, Sensor.class);
    }
}
