package com.shui.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shui.dao.UserDao;
import com.shui.domain.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author shui
 */
@Api(tags = "mybatisplus测试")
@RestController
@RequestMapping("/mp")
public class MybatisPlusTestController {

    @Autowired
    private UserDao userDao;

    @PostMapping("getAll")
    public List<User> getAll() {
        return userDao.getAllUser();
    }

    @PostMapping("all")
    public List<User> all() {
        return userDao.selectList(null);
    }

    @GetMapping("save")
    @ApiOperation("保存")
    public User save(@RequestParam("name") String name, @RequestParam("password") String password) {
        User user = User.builder()
                .name(name)
                .password(password)
                .address("测试地址")
                .phone("123").build();
        if (userDao.insert(user) > 0) {
            return user;
        }
        return null;
    }

    @GetMapping("update")
    @ApiOperation("更新user")
    public User geupdatetAll(@RequestParam("name") String name, @RequestParam("id") Long id) {
        User user = userDao.selectOne(new QueryWrapper<User>().eq("id", id));
        user.setName(name);
        if (userDao.updateById(user) > 0) {
            return user;
        }
        return null;
    }

    @GetMapping("getByPhone")
    @ApiOperation("通过phone查找user")
    public User getByPhone(@RequestParam("phone") String phone) {
        return userDao.getByPhone(phone);
    }

    @PostMapping("saveUser")
    @ApiOperation("保存user")
    public User saveUser(@RequestBody User user) {
        if (userDao.insert(user) > 0) {
            return user;
        }
        return null;
    }

    @GetMapping("dateTest")
    @ApiOperation("date测试")
    public void saveUser(@RequestParam Date date) {
        System.out.println(date);
    }

    @GetMapping("deleteUser/{id}")
    @ApiOperation("删除user")
    public void deleteUser(@PathVariable("id") Integer id) {
        userDao.deleteById(id);
    }

}
