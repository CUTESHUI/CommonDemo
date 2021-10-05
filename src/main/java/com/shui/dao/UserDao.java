package com.shui.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shui.domain.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends BaseMapper<User> {

    @Select("select * from user where is_delete = 0")
    List<User> getAllUser();

    User getByPhone(@Param("phone") String phone);
}
