package com.shui.pipeline.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * pipeline状态枚举
 **/
@Getter
@ToString
@AllArgsConstructor
public enum PipelineStatusEnum {
    /**
     * pipeline
     */
    CONTEXT_IS_NULL("P0001", "流程上下文为空"),
    BUSINESS_CODE_IS_NULL("P0002", "业务代码为空"),
    PROCESS_TEMPLATE_IS_NULL("P0003", "流程模板配置为空"),
    PROCESS_LIST_IS_NULL("P0004", "业务处理器配置为空");

    /**
     * 响应状态
     */
    private final String code;
    /**
     * 响应编码
     */
    private final String msg;
}
