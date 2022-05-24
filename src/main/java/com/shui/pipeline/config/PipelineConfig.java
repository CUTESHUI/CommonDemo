package com.shui.pipeline.config;

import com.shui.pipeline.BusinessProcess;
import com.shui.pipeline.ProcessController;
import com.shui.pipeline.ProcessTemplate;
import com.shui.pipeline.action.AssembleAction;
import com.shui.pipeline.action.PreParamCheckAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * pipeline配置类
 */
@Configuration
public class PipelineConfig {


    /**
     * 普通发送执行流程
     * 1. 前置参数校验
     * 2. 组装参数
     */
    @Bean("commonTemplate")
    public ProcessTemplate commonTemplate() {
        ArrayList<BusinessProcess> processList = new ArrayList<>();
        processList.add(preParamCheckAction());
        processList.add(assembleAction());

        ProcessTemplate processTemplate = new ProcessTemplate();
        processTemplate.setProcessList(processList);
        return processTemplate;
    }

    /**
     * pipeline流程控制器
     * 目前暂定只有 普通发送的流程
     * 后续扩展则加BusinessCode和ProcessTemplate
     */
    @Bean
    public ProcessController processController() {
        ProcessController processController = new ProcessController();
        Map<String, ProcessTemplate> templateConfig = new HashMap<>(4);
        templateConfig.put("test", commonTemplate());
        processController.setTemplateConfig(templateConfig);
        return processController;
    }


    /**
     * 组装参数Action
     *
     * @return
     */
    @Bean
    public AssembleAction assembleAction() {
        return new AssembleAction();
    }

    /**
     * 前置参数校验Action
     *
     * @return
     */
    @Bean
    public PreParamCheckAction preParamCheckAction() {
        return new PreParamCheckAction();
    }

}
