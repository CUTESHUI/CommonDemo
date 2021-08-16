package com.shui.controller;

import com.shui.dynamic.datasource.annotation.DataSource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "测试多数据源")
public class TestDataSourceController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @GetMapping("test1")
    @ApiOperation("test1")
    @DataSource("eblog")
    public List test1() {
        return jdbcTemplate.queryForList("SELECT * FROM m_user");
    }

}
