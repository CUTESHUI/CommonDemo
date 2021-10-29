package com.shui.flowable.controller;

import com.shui.common.Result;
import com.shui.exception.BaseException;
import com.shui.flowable.dto.TaskRequestDTO;
import com.shui.flowable.dto.TaskResponseDTO;
import com.shui.flowable.service.FlowableTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "flowable-task测试")
@RequestMapping("/flowable/task")
public class FlowableTaskController extends FlowableBaseController {

    @Autowired
    private FlowableTaskService flowableTaskService;

    /**
     * 任务处理
     *
     * @param taskId   任务 Id，来自 ACT_RU_TASK
     * @param variable 完成任务需要的条件参数
     */
    @PostMapping(value = "/task/{taskId}")
    @ApiOperation("任务处理")
    public String task(@PathVariable(value = "taskId") String taskId, @RequestBody Map<String, Object> variable) {
        boolean isSuspended = taskService.createTaskQuery().taskId(taskId).singleResult().isSuspended();
        if (isSuspended) {
            throw new BaseException("任务已挂起，无法完成");
        }
        // 完成任务
        taskService.complete(taskId, variable);
        return "任务完成，任务Id：" + taskId;
    }

    /**
     * 任务处理-审核人
     *
     * @param taskId   任务 Id，来自 ACT_RU_TASK
     * @param assignee 设置审核人，替换
     * @param variable 完成任务需要的条件参数
     */
    @PostMapping(value = "/task/{taskId}/assignee")
    @ApiOperation("任务处理-指定审核人")
    public String taskByAssignee(@PathVariable(value = "taskId") String taskId, @RequestParam(value = "assignee") String assignee, @RequestBody Map<String, Object> variable) {
        boolean isSuspended = taskService.createTaskQuery().taskId(taskId).singleResult().isSuspended();
        if (isSuspended) {
            throw new BaseException("任务已挂起，无法完成");
        }
        // 设置谁审核的
        taskService.setAssignee(taskId, assignee);

        // 完成任务
        taskService.complete(taskId, variable);
        return "任务完成，任务Id：" + taskId;
    }

    /**
     * 根据任务节点获取流程实例 Id
     *
     * @param taskId 任务节点 Id
     */
    @GetMapping(value = "/getTaskInfo/{taskId}")
    @ApiOperation("根据任务节点获取流程实例Id")
    public String getTaskInfo(@PathVariable(value = "taskId") String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new BaseException("任务已完成或者不存在");
        }
        return task.getProcessInstanceId();
    }

    @GetMapping(value = "getTaskToString")
    @ApiOperation("获取任务")
    public String getTaskId(@RequestParam(value = "processInstanceId") String processInstanceId) {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (pi == null) {
            throw new BaseException("任务已结束");
        }
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        return task.toString();
    }

    @GetMapping("getGroupTaskList")
    @ApiOperation("获取指定用户组流程任务列表")
    public Object list(String group) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(group).list();
        return tasks.toString();
    }

    @GetMapping("getTask")
    @ApiOperation(value = "获取任务详情")
    public TaskResponseDTO getTask(@RequestParam(value = "taskId") String taskId) {
        return flowableTaskService.getTask(taskId);
    }

    @PostMapping("update")
    @ApiOperation(value = "修改任务")
    public TaskResponseDTO update(@RequestBody TaskRequestDTO taskRequestDTO) {
        return flowableTaskService.updateTask(taskRequestDTO);
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除任务")
    public void delete(@RequestParam String taskId) {
        flowableTaskService.deleteTask(taskId);
    }

    @PostMapping(value = "/assign")
    @ApiOperation(value = "转办任务")
    public void assign(@RequestBody TaskRequestDTO taskRequest) {
        flowableTaskService.assignTask(taskRequest);
    }

    @ApiOperation(value = "委派任务")
    @PostMapping(value = "/delegate")
    public void delegate(@RequestBody TaskRequestDTO taskRequest) {
        flowableTaskService.delegateTask(taskRequest);
    }

    @PostMapping(value = "/claim")
    @ApiOperation(value = "认领任务")
    public void claim(@RequestBody TaskRequestDTO taskRequest) {
        flowableTaskService.claimTask(taskRequest);
    }

    @PutMapping(value = "/unclaim")
    @PostMapping(value = "取消认领任务")
    public void unclaim(@RequestBody TaskRequestDTO taskRequest) {
        flowableTaskService.unclaimTask(taskRequest);
    }

}
