package com.shui.controller;

import com.shui.common.Result;
import com.shui.dao.UserDao;
import com.shui.domain.User;
import com.shui.domain.UserElastic;
import com.shui.search.UserRepository;
import com.shui.utils.ConvertUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author shui
 */
@Api(tags = "elasticsearch测试")
@RestController
@RequestMapping("/elastic")
public class ElasticsearchTestController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @GetMapping("importAll")
    public int importAll() {
        List<User> users = userDao.selectList(null);
        List<UserElastic> userElastics = ConvertUtils.sourceToTarget(users, UserElastic.class);
        Iterable<UserElastic> all = userRepository.saveAll(userElastics);
        Iterator<UserElastic> iterator = all.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    @GetMapping("getAllUser")
    @ApiOperation("获取所有user")
    public List<User> getAllUser() {
        Iterable<UserElastic> all = userRepository.findAll();
        List<UserElastic> list = new ArrayList<>();
        all.forEach(list::add);
        return ConvertUtils.sourceToTarget(list, User.class);
    }

    @GetMapping("search")
    @ApiOperation("搜索")
    public Result search() {

        return new Result();
    }
}
