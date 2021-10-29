package com.shui.flowable.service.impl;

import org.flowable.engine.*;

import javax.annotation.Resource;

public class FlowableBaseServiceImpl {
    /**
     * 流程引擎
     */
    @Resource
    protected ProcessEngine processEngine;
    /**
     * 用户以及组管理服务
     */
    @Resource
    protected IdentityService identityService;
    /**
     * 部署服务
     */
    @Resource
    protected RepositoryService repositoryService;
    /**
     * 流程实例服务
     */
    @Resource
    protected RuntimeService runtimeService;
    /**
     * 流程节点任务服务
     */
    @Resource
    protected TaskService taskService;
    /**
     * 历史数据服务
     */
    @Resource
    protected HistoryService historyService;
    /**
     *
     */
    @Resource
    protected ManagementService managementService;

}
