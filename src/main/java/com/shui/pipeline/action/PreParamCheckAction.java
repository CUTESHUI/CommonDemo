package com.shui.pipeline.action;

import com.shui.common.Result;
import com.shui.pipeline.BusinessProcess;
import com.shui.pipeline.ProcessContext;
import com.shui.pipeline.ProcessModel;
import lombok.extern.slf4j.Slf4j;

/**
 * 前置参数校验
 */
@Slf4j
public class PreParamCheckAction implements BusinessProcess {

    @Override
    public void process(ProcessContext context) {
        try {
            ProcessModel processModel = context.getProcessModel();
            System.out.println("前置参数校验");
        } catch (Exception e) {
            context.setNeedBreak(true).setResponse(new Result().error(e.getMessage()));
        }

    }
}
