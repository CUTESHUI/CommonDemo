package com.shui.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * 人员信息
 *
 * @author  
 * @since 1.0.0 2021-03-23
 */
@Data
public class RyxxExcel {

    @Excel(name = "姓名", width = 20)
    private String name;
    @Excel(name = "部门", width = 80)
    private String deptName;
    @Excel(name = "邮箱", width = 20)
    private String email;
    @Excel(name = "手机号", width = 20)
    private String mobile;
    @Excel(name = "分机号", width = 20)
    private String fjh;
    @Excel(name = "职位", width = 30)
    private String zw;
    @Excel(name = "是否为高位", width = 20)
    private Long isSj;
    @Excel(name = "上级姓名", width = 20)
    private String sjName;
    @Excel(name = "上级手机号", width = 20)
    private String sjMobile;
    @Excel(name = "工号", width = 20)
    private String gh;

}