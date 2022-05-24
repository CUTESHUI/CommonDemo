package com.shui.pipeline;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.shui.common.Result;
import com.shui.pipeline.enums.PipelineStatusEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 流程控制器
 */
@Slf4j
@Data
public class ProcessController {

    /**
     * 模板映射
     */
    private Map<String, ProcessTemplate> templateConfig = null;


    /**
     * 执行责任链
     *
     * @param context
     * @return 返回上下文内容
     */
    public ProcessContext process(ProcessContext context) {

        /**
         * 前置检查
         */
        if (!preCheck(context)) {
            return context;
        }

        /**
         * 遍历流程节点
         */
        List<BusinessProcess> processList = templateConfig.get(context.getCode()).getProcessList();
        for (BusinessProcess businessProcess : processList) {
            businessProcess.process(context);
            if (context.getNeedBreak()) {
                break;
            }
        }
        return context;
    }


    private Boolean preCheck(ProcessContext context) {
        // 上下文
        if (context == null) {
            context = new ProcessContext();
            context.setResponse(new Result().error(PipelineStatusEnum.CONTEXT_IS_NULL.getMsg()));
            return false;
        }

        // 业务代码
        String businessCode = context.getCode();
        if (StrUtil.isBlank(businessCode)) {
            context.setResponse(new Result().error(PipelineStatusEnum.BUSINESS_CODE_IS_NULL.getMsg()));
            return false;
        }

        // 执行模板
        ProcessTemplate processTemplate = templateConfig.get(businessCode);
        if (processTemplate == null) {
            context.setResponse(new Result().error(PipelineStatusEnum.PROCESS_TEMPLATE_IS_NULL.getMsg()));
            return false;
        }

        // 执行模板列表
        List<BusinessProcess> processList = processTemplate.getProcessList();
        if (CollUtil.isEmpty(processList)) {
            context.setResponse(new Result().error(PipelineStatusEnum.PROCESS_LIST_IS_NULL.getMsg()));
            return false;
        }

        return true;
    }


}
