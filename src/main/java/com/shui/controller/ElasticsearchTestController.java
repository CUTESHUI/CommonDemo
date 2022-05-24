package com.shui.controller;

import com.shui.common.Result;
import com.shui.service.elasticsearch.ElasticsearchRestHighLevelClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shui
 */
@Api(tags = "elasticsearch测试")
@RestController
@RequestMapping("/elastic")
public class ElasticsearchTestController {

    //    @Autowired
//    private UserEsRepository userEsRepository;
//    @Autowired
//    private UserDao userDao;
//    @Autowired
//    private ElasticsearchService elasticsearchService;
    @Autowired
    private ElasticsearchRestHighLevelClientService elasticsearchRestHighLevelClientService;

//    @GetMapping("importAll")
//    public int importAll() {
//        List<User> users = userDao.selectList(null);
//        List<UserElastic> userElastics = ConvertUtils.sourceToTarget(users, UserElastic.class);
//        Iterable<UserElastic> all = userEsRepository.saveAll(userElastics);
//        Iterator<UserElastic> iterator = all.iterator();
//        int result = 0;
//        while (iterator.hasNext()) {
//            result++;
//            iterator.next();
//        }
//        return result;
//    }
//
//    @GetMapping("getAllUser")
//    @ApiOperation("获取所有user")
//    public List<User> getAllUser() {
//        Iterable<UserElastic> all = userEsRepository.findAll();
//        List<UserElastic> list = new ArrayList<>();
//        all.forEach(list::add);
//        return ConvertUtils.sourceToTarget(list, User.class);
//    }
//
//    @GetMapping("search")
//    @ApiOperation("搜索")
//    public Result search() {
//
//        return new Result();
//    }
//
//    @GetMapping("bank/search")
//    @ApiOperation("bank搜索")
//    public Result bankSearch() {
//
//        return new Result().ok(elasticsearchService.bankSearch());
//    }

    @GetMapping("bank/search")
    @ApiOperation("bank搜索")
    public Result bankSearch() {

        try {
            return new Result().ok(elasticsearchRestHighLevelClientService.bankSearch());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result();
    }
}
