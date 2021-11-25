package com.shui.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * 用户签到积分流水表
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_sign_integral_log")
public class UserSignIntegralLog {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 积分类型 1.签到 2.连续签到 3.福利任务 4.每日任务 5.补签
     */
    private Integer integralType;
    /**
     * 积分
     */
    private Integer integral;
    /**
     * 积分补充文案
     */
    private String bak;
    /**
     * 操作时间(签到和补签的具体日期)
     */
    private Date operationTime;

    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    @TableField(fill = FieldFill.UPDATE)
    private Long updator;

    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateDate;
}