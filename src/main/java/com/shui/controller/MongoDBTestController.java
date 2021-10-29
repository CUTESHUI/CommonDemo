package com.shui.controller;

import com.shui.domain.mongodb.MongoUser;
import com.shui.repository.UserMongoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Api(tags = "mongodb测试")
@RestController
@RequestMapping("/mongodb")
public class MongoDBTestController {

    @Autowired
    private UserMongoRepository userMongoRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("getUser")
    @ApiOperation("获取全部User")
    public List<MongoUser> getUser() {
        List<MongoUser> mongoUsers = mongoTemplate.findAll(MongoUser.class);
        return userMongoRepository.findAll();
    }

    @PostMapping("saveUser")
    @ApiOperation("保存User")
    public void saveUser(@RequestBody MongoUser mongoUser) {
        userMongoRepository.save(mongoUser);
        mongoTemplate.insert(mongoUser);

        // 需要Id
        mongoTemplate.save(mongoUser);
        userMongoRepository.insert(mongoUser);
    }

    @PutMapping("update")
    @ApiOperation("更新User")
    public void update(@RequestParam("name") String name) {
        Query query = new Query(Criteria.where("name").is(name));
        Update update = new Update();
        update.set("name", "123");
        mongoTemplate.updateFirst(query, update, MongoUser.class);
    }


    @GetMapping("getUserByName")
    @ApiOperation("获取User通过名字")
    public MongoUser getUserByName(@RequestParam("name") String name) {
        Query query = new Query(Criteria
                .where("name").is(name));
        MongoUser mongoUser = mongoTemplate.findOne(query, MongoUser.class);
        return mongoUser;
    }


    @PostMapping("findUpdate")
    @ApiOperation("找到并更新")
    public MongoUser findUpdate(@RequestBody MongoUser mongoUser) {
        Query query = new Query(Criteria
                .where("name").is(mongoUser.getName()));
        boolean exists = mongoTemplate.exists(query, MongoUser.class);
        if (exists) {
            Update update = new Update();
            update.set("name", "123");
            return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), MongoUser.class);
        } else {
            mongoTemplate.insert(mongoUser);
            return mongoUser;
        }
    }

    @GetMapping("insertAll")
    @ApiOperation("批量插入")
    public void insertAll() {
        List<MongoUser> dataList = new ArrayList<>();
        Random random = new Random();
        IntStream.rangeClosed(1, 100).forEach(i -> {
            MongoUser user = new MongoUser();
            user.setName(i + "");
            user.setAge(random.nextInt(i));
            dataList.add(user);
        });
        BulkOperations bulkOp = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, MongoUser.class);
        bulkOp.insert(dataList);
        bulkOp.execute();
    }


    @GetMapping("updateAll")
    @ApiOperation("批量更新")
    public void updateAll() {
        List<MongoUser> dataList = new ArrayList<>();
        Random random = new Random();
        IntStream.rangeClosed(1, 100).forEach(i -> {
            MongoUser user = new MongoUser();
            user.setName(i + "");
            user.setAge(random.nextInt(i));
            dataList.add(user);
        });
        BulkOperations bulkOp = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, MongoUser.class);
        List<Pair<Query, Update>> updateList = new ArrayList<>();
        dataList.forEach(data -> {
            Query query = new Query(Criteria.where("name").is("123"));
            Update update = new Update().set("name", "321");
            Pair<Query, Update> updatePair = Pair.of(query, update);
            updateList.add(updatePair);
        });
        bulkOp.upsert(updateList);
        bulkOp.execute();
    }


}
