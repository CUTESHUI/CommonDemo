package com.shui.domain.influxdb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Measurement(name = "sensor")
public class Sensor {

    @Column(name = "deviceId", tag = true)
    private String deviceId;

    @Column(name = "temp")
    private String temp;

    @Column(name = "voltage")
    private String voltage;

    @Column(name = "field1")
    private String field1;

    @Column(name = "field2")
    private String field2;

    @Column(name = "time")
    private String time;
}
