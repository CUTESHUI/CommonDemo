package com.shui.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shui.domain.entity.UserSignIntegralLog;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSignIntegralLogDao extends BaseMapper<UserSignIntegralLog> {

    @Select("SELECT count(1) from user_sign_integral_log where integral_type=2 and user_id=#{userId} and STR_TO_DATE( create_date, '%Y-%m-%d' ) BETWEEN #{startTime} AND #{endTime}")
    int selectSignByTime(Long userId, String startTime, String endTime);
}
