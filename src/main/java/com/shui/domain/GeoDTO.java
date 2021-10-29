package com.shui.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GeoDTO implements Serializable {

    private String geoKey;

    private List<String> names;

    private Double x;

    private Double y;

    private Integer limit;

    private Integer distance;

    @ApiModelProperty("0 米，1 千米")
    private Integer distanceUnitType;
}
