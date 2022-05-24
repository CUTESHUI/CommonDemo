package com.shui.controller;

import com.shui.common.Result;
import com.shui.pipeline.ProcessContext;
import com.shui.pipeline.ProcessController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "pipeline测试")
@RestController
@RequestMapping("/pipeline")
public class PipelineController {

    @Autowired
    private ProcessController processController;

    @GetMapping("importAll")
    public Result importAll() {

        ProcessContext context = ProcessContext.builder()
                .code("test1")
                .processModel(null)
                .needBreak(false)
                .response(new Result())
                .build();

        ProcessContext process = processController.process(context);

        return process.getResponse();
    }

}
