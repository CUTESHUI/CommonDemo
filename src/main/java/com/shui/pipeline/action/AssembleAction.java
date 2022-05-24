package com.shui.pipeline.action;

import com.shui.common.Result;
import com.shui.pipeline.BusinessProcess;
import com.shui.pipeline.ProcessContext;
import com.shui.pipeline.ProcessModel;
import lombok.extern.slf4j.Slf4j;

/**
 * 拼装参数
 */
@Slf4j
public class AssembleAction implements BusinessProcess {

    @Override
    public void process(ProcessContext context) {
        try {
            ProcessModel processModel = context.getProcessModel();
            System.out.println("拼装参数");
        } catch (Exception e) {
            context.setNeedBreak(true).setResponse(new Result().error(e.getMessage()));
        }
    }
}
