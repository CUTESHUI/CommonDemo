package com.shui.flowable.controller;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.shui.exception.BaseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "flowable-processInstance测试")
@RequestMapping("/flowable/processInstance")
public class FlowableProcessInstance extends FlowableBaseController {

    /**
     * 启动流程
     *
     * @param modelId    部署的流程 Id，来自 ACT_RE_PROCDEF
     * @param userId      用户 Id
     * @param businessKey 数据 Key，业务键，一般为表单数据的 ID，仅作为表单数据与流程实例关联的依据
     * @param variable    完成任务需要的条件参数
     */
    @GetMapping(value = "/start/{modelId}/{userId}/{businessKey}")
    @ApiOperation("创建流程")
    public String start(@PathVariable(value = "modelId") String modelId, @PathVariable(value = "userId") String userId, @PathVariable(value = "businessKey") String businessKey, @RequestBody(required = false) Map<String, Object> variable) {
        try {
            // 设置发起人
            identityService.setAuthenticatedUserId(userId);
            ProcessInstance processInstance;
            if (CollectionUtils.isNotEmpty(variable)) {
                processInstance = runtimeService.startProcessInstanceById(modelId, businessKey, variable);
            } else {
                processInstance = runtimeService.startProcessInstanceById(modelId, businessKey);
            }
            return "流程启动成功，流程ID为：" + processInstance.getId();
        } catch (FlowableObjectNotFoundException e) {
            throw new BaseException("未找到流程实例，流程可能已发生变化");
        }
    }

    @GetMapping("historyList")
    @ApiOperation("查看历史流程记录")
    public List getHistoryList(@RequestParam("processInstanceId") String processInstanceId) {
        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .finished()
                .orderByHistoricActivityInstanceEndTime()
                .asc()
                .list();

        return historicActivityInstances;
    }

    @GetMapping("deleteProcessInstanceById")
    @ApiOperation("终止流程实例")
    public String deleteProcessInstanceById(@RequestParam("processInstanceId") String processInstanceId, @RequestParam("deleteRemark") String deleteRemark) {
        if (!isExistProcIntRunning(processInstanceId)) {
            throw new BaseException("流程已结束");
        }
        runtimeService.deleteProcessInstance(processInstanceId, deleteRemark);
        return "终止流程实例成功";
    }

    @GetMapping("hangUp")
    @ApiOperation("挂起流程实例")
    public String handUpProcessInstance(@RequestParam("processInstanceId") String processInstanceId) {
        if (!isExistProcIntRunning(processInstanceId)) {
            throw new BaseException("流程已结束");
        }
        runtimeService.suspendProcessInstanceById(processInstanceId);
        return "挂起流程成功";
    }

    @GetMapping("recovery")
    @ApiOperation("恢复（唤醒）被挂起的流程实例")
    public String activateProcessInstance(@RequestParam("processInstanceId") String processInstanceId) {
        if (!isExistProcIntRunning(processInstanceId)) {
            throw new BaseException("流程已结束");
        }
        runtimeService.activateProcessInstanceById(processInstanceId);
        return "恢复流程成功";
    }

    /**
     * 通过流程实例 Id，判断流程实例在历史记录中是否存在
     *
     * @param processInstanceId 流程实例 Id
     */
    @GetMapping("isExist/history")
    @ApiOperation("判断流程实例在历史记录中是否存在")
    public Boolean isExistProcInHistory(@RequestParam("processInstanceId") String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        return historicProcessInstance != null;
    }

    /**
     * 通过流程实例 Id，判断传入流程实例在运行中是否存在
     *
     * @param processInstanceId 流程实例 Id
     */
    @GetMapping("isExist/running")
    @ApiOperation("判断流程在运行中是否存在")
    public Boolean isExistProcIntRunning(@RequestParam("processInstanceId") String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        return processInstance != null;

//        boolean isFinish = false;
//        // 根据流程 ID 获取未完成的流程
//        long count = historyService.createHistoricProcessInstanceQuery()
//                .unfinished()
//                .processInstanceId(processInstanceId)
//                .count();
//        if (count == 0) {
//            isFinish = true;
//        }
//        return isFinish;
    }

}
