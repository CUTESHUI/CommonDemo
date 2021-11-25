package com.shui.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * 用户签到积分总表
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_sign_integral")
public class UserSignIntegral {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 当前积分
     */
    private Integer integral;
    /**
     * 累计积分
     */
    private Integer integralTotal;


    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    @TableField(fill = FieldFill.UPDATE)
    private Long updator;

    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateDate;

}