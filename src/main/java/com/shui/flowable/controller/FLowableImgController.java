package com.shui.flowable.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "flowable-img测试")
@RequestMapping("/flowable/img")
public class FLowableImgController extends FlowableBaseController{

    /**
     * 根据流程实例 ID 查询流程图
     *
     * @param processInstanceId 流程实例 ID
     */
    @GetMapping("processDiagram/{processInstanceId}")
    @ApiOperation(value = "根据流程实例 ID 查询流程图", produces = "application/octet-stream")
    public void genProcessDiagram(HttpServletResponse response, @PathVariable("processInstanceId") String processInstanceId) throws Exception {
        // 流程定义 ID
        String processDefinitionId;

        // 查看完成的进程中是否存在此进程
        long count = historyService.createHistoricProcessInstanceQuery().finished().processInstanceId(processInstanceId).count();
        if (count > 0) {
            // 如果流程已经结束，则得到结束节点
            HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

            processDefinitionId = pi.getProcessDefinitionId();
        } else {
            // 如果流程没有结束，则取当前活动节点
            // 根据流程实例ID获得当前处于活动状态的ActivityId合集
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            processDefinitionId = pi.getProcessDefinitionId();
        }

        List<String> highLightedActivityIds = new ArrayList<>();

        // 获得所有活动过的节点
        List<HistoricActivityInstance> highLightedActivityList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().list();

        for (HistoricActivityInstance tempActivity : highLightedActivityList) {
            String activityId = tempActivity.getActivityId();
            highLightedActivityIds.add(activityId);
        }

        // 获得正在活动的节点
//        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
//        String instanceId = task.getProcessInstanceId();
//        List<Execution> executions = runtimeService
//                .createExecutionQuery()
//                .processInstanceId(instanceId)
//                .list();
//        for (Execution exe : executions) {
//            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
//            highLightedActivityIds.addAll(ids);
//        }

        List<String> flows = new ArrayList<>();
        // 获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        ProcessEngineConfiguration processEngineConfig = processEngine.getProcessEngineConfiguration();

        ProcessDiagramGenerator diagramGenerator = processEngineConfig.getProcessDiagramGenerator();
        InputStream inputStream = diagramGenerator.generateDiagram(bpmnModel, "bmp", highLightedActivityIds, flows, processEngineConfig.getActivityFontName(),
                processEngineConfig.getLabelFontName(), processEngineConfig.getAnnotationFontName(), processEngineConfig.getClassLoader(), 1.0, true);

        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
            response.getOutputStream().write(IOUtils.toByteArray(inputStream));
    }

    /**
     * 根据任务 ID 获取任务进度流程图
     *
     * @param taskId 任务节点 Id
     */
    @GetMapping("processDiagramByTaskId/{taskId}")
    @ApiOperation("根据任务 ID 获取任务进度流程图")
    public void processDiagramByTaskId(@PathVariable(value = "taskId") String taskId, HttpServletResponse response) throws Exception{

        // 根据任务 ID 获取流程实例 ID
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        // 根据流程实例获取流程图
        // 流程定义 ID
        String processDefinitionId;

        // 查看完成的进程中是否存在此进程
        long count = historyService.createHistoricProcessInstanceQuery().finished().processInstanceId(processInstanceId).count();
        if (count > 0) {
            // 如果流程已经结束，则得到结束节点
            HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

            processDefinitionId = pi.getProcessDefinitionId();
        } else {// 如果流程没有结束，则取当前活动节点
            // 根据流程实例ID获得当前处于活动状态的ActivityId合集
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            processDefinitionId = pi.getProcessDefinitionId();
        }

        List<String> highLightedActivityIds = new ArrayList<>();

        // 获得所有活动过的节点
        List<HistoricActivityInstance> highLightedActivityList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().list();

        for (HistoricActivityInstance tempActivity : highLightedActivityList) {
            String activityId = tempActivity.getActivityId();
            highLightedActivityIds.add(activityId);
        }

        // 获得正在活动的节点
//        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
//        String instanceId = task.getProcessInstanceId();
//        List<Execution> executions = runtimeService
//                .createExecutionQuery()
//                .processInstanceId(instanceId)
//                .list();
//        for (Execution exe : executions) {
//            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
//            highLightedActivityIds.addAll(ids);
//        }

        List<String> flows = new ArrayList<>();
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        ProcessEngineConfiguration processEngineConfig = processEngine.getProcessEngineConfiguration();

        ProcessDiagramGenerator diagramGenerator = processEngineConfig.getProcessDiagramGenerator();
        InputStream inputStream = diagramGenerator.generateDiagram(bpmnModel, "bmp", highLightedActivityIds, flows, processEngineConfig.getActivityFontName(),
                processEngineConfig.getLabelFontName(), processEngineConfig.getAnnotationFontName(), processEngineConfig.getClassLoader(), 1.0, true);

        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.getOutputStream().write(IOUtils.toByteArray(inputStream));
    }
}
