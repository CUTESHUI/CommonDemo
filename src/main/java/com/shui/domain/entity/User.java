package com.shui.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
@ApiModel("用户")
public class User implements Serializable {

    @ApiModelProperty("主键")
    private Long id;

    @NotBlank
    @ApiModelProperty("姓名")
    private String name;

    @NotBlank
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("电话")
    private String phone;

    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    @TableField(fill = FieldFill.UPDATE)
    private Long updator;

    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateDate;

    @Version
    private Integer version;

    @TableLogic
    private Integer isDelete;
}
