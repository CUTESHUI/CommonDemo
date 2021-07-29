package com.shui.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.shui.domain.RyxxExcel;
import com.shui.exception.BaseException;
import com.shui.utils.ConvertUtils;
import com.shui.utils.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "excel测试")
@RequestMapping("/excel")
public class ExcelTestController {

    @PostMapping("importRyxx")
    @ApiOperation("导入")
    public void importRyxx(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws Exception {
        List<RyxxExcel> list;
        try {
            ImportParams params = new ImportParams();
            params.setSheetNum(1);
            params.setNeedVerify(true);
            ExcelImportResult<RyxxExcel> result = ExcelImportUtil.importExcelMore(file.getInputStream(), RyxxExcel.class, params);
            if (result.getFailList().size() > 0) {
                throw new BaseException("未读取到数据，请检查excel文档");
            }
            if (result.getList().size() > 0) {
                list = ConvertUtils.sourceToTarget(result.getList(), RyxxExcel.class);
            } else {
                throw new BaseException("未读取到数据，请检查excel文档");
            }
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        }

        List<RyxxExcel> newList = handleExcel(list);

        ExcelUtils.exportExcelToTarget(response, null, newList, RyxxExcel.class);
    }

    private List<RyxxExcel> handleExcel(List<RyxxExcel> list) {
        List<RyxxExcel> res = Collections.synchronizedList(new ArrayList<>(list.size()));
        List<RyxxExcel> result = Collections.synchronizedList(new ArrayList<>(list.size()));

        list.stream().peek(excel -> {
            String deptName = excel.getDeptName();
            if (deptName.contains(";")) {
                String[] split = deptName.split(";");
                for (String deptname : split) {
                    RyxxExcel ryxxExcel = retNew(excel, deptname);
                    res.add(ryxxExcel);
                }
            } else if (deptName.contains("；")) {
                String[] split = deptName.split("；");
                for (String deptname : split) {
                    RyxxExcel ryxxExcel = retNew(excel, deptname);
                    res.add(ryxxExcel);
                }
            } else {
                res.add(excel);
            }
        }).collect(Collectors.toList());


        res.stream().peek(excel -> {
            String deptName = excel.getDeptName();
            if (deptName.contains("-")) {
                String newDeptName = deptName.replaceAll("-", "@");
                excel.setDeptName(newDeptName);
            }
            result.add(excel);
        }).collect(Collectors.toList());

        return result;
    }

    private RyxxExcel retNew(RyxxExcel old, String deptName) {
        RyxxExcel ryxxExcel = new RyxxExcel();
        ryxxExcel.setName(old.getName());
        ryxxExcel.setDeptName(deptName);
        ryxxExcel.setEmail(old.getEmail());
        ryxxExcel.setMobile(old.getMobile());
        ryxxExcel.setFjh(old.getFjh());
        ryxxExcel.setZw(old.getZw());
        ryxxExcel.setIsSj(old.getIsSj());
        ryxxExcel.setSjName(old.getSjName());
        ryxxExcel.setSjMobile(old.getSjMobile());
        ryxxExcel.setGh(old.getGh());
        return ryxxExcel;
    }

    @GetMapping("export")
    @ApiOperation("导出")
    public void export(HttpServletResponse response) throws Exception {
        List<RyxxExcel> list = new ArrayList<>();

        ExcelUtils.exportExcelToTarget(response, null, list, RyxxExcel.class);
    }

}
