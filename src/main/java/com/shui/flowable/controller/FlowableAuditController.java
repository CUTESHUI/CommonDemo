package com.shui.flowable.controller;

import com.shui.wx.utils.common.CollectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "flowable-audit测试")
@RequestMapping("/flowable/audit")
public class FlowableAuditController extends FlowableBaseController {

    /**
     * 获取用户发起的流程实例列表
     *
     * @param userId 用户 Id
     */
    @GetMapping("/getStartHistoryByUser/{userId}")
    @ApiOperation("获取用户发起的流程实例列表")
    public List<HistoricProcessInstance> getStartHistoryByUser(@PathVariable("userId") String userId) {
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery()
                .startedBy(userId)
                .orderByProcessInstanceStartTime()
                .desc()
                .list();
        return list;
    }

    /**
     * 获取用户审核历史
     *
     * @param userId 用户 Id
     */
    @GetMapping(value = "/getHistoryByUser/{userId}")
    @ApiOperation("获取用户审核历史")
    public List getHistoryByUser(@PathVariable(value = "userId") String userId) {
        List<Map<String, Object>> res = new ArrayList<>();
        // 根据用户，查询任务实例历史
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(userId)
                .finished()
                .orderByHistoricTaskInstanceEndTime()
                .desc()
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return res;
        }
        list.forEach(historicTaskInstance -> {
            // 历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(historicTaskInstance.getProcessInstanceId()).singleResult();
            // 获取需要的历史数据
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("assignee", historicTaskInstance.getAssignee());
            historyInfo.put("nodeName", historicTaskInstance.getName());
            historyInfo.put("startTime", historicTaskInstance.getCreateTime());
            // 节点操作时间（本流程节点结束时间）
            historyInfo.put("endTime", historicTaskInstance.getEndTime());
            // 流程定义名称
            historyInfo.put("processName", historicProcessInstance.getProcessDefinitionName());
            // 流程实例 ID
            historyInfo.put("processInstanceId", historicTaskInstance.getProcessInstanceId());
            // 业务键
            historyInfo.put("businessKey", historicProcessInstance.getBusinessKey());
            res.add(historyInfo);
        });
        return res;
    }

    /**
     * 根据用户，获取需要审核的业务键 business_key 列表
     *
     * @param userId 用户 Id
     */
    @GetMapping(value = "/getRuntimeBusinessKeyByUser/{userId}")
    @ApiOperation("根据用户，获取需要审核的业务键 business_key 列表")
    public List getRuntimeBusinessKeyByUser(@PathVariable(value = "userId") String userId) {
        List<Map<String, Object>> res = new ArrayList<>();
        // 根据用户获取正在进行的任务
        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee(userId)
                .list();
        if (CollectionUtils.isEmpty(tasks)) {
            return res;
        }
        tasks.forEach(task -> {
            Map<String, Object> data = new HashMap<>();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            data.put("businessKey", processInstance.getBusinessKey());
            data.put("taskId", task.getId());
            data.put("processInstanceName", processInstance.getProcessDefinitionName());
            data.put("startTime", processInstance.getStartTime());
            res.add(data);
        });
        return res;
    }

    /**
     * 根据组，获取需要审核的业务键 business_key 列表
     *
     * @param groupIds 组 Id
     */
    @GetMapping(value = "/getRuntimeBusinessKeyByGroup")
    @ApiOperation("根据组，获取需要审核的业务键 business_key 列表")
    public List getRuntimeBusinessKeyByGroup(@RequestBody List<String> groupIds) {
        List<Map<String, Object>> res = new ArrayList<>();
        // 判断是否有组信息
        if (groupIds != null && groupIds.size() > 0) {
            // 根据发起人获取正在执行的任务列表
            List<Task> tasks = taskService.createTaskQuery().taskCandidateGroupIn(groupIds).list();
            if (CollectionUtils.isEmpty(tasks)) {
                return res;
            }
            tasks.forEach(task -> {
                Map<String, Object> data = new HashMap<>();
                ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
                data.put("businessKey", processInstance.getBusinessKey());
                data.put("taskId", task.getId());
                data.put("processInstanceName", processInstance.getProcessDefinitionName());
                data.put("startTime", processInstance.getStartTime());
                res.add(data);
            });
        }
        return res;
    }
}
