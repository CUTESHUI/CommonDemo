package com.shui.flowable.service;

import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskInfo;
import org.flowable.task.api.history.HistoricTaskInstance;

public interface PermissionService {

    /**
     * 校验用户是否有权限读取任务
     *
     * @param taskId
     * @param userId
     * @param validateReadProcessInstance
     * @param validateReadParentTask
     * @return
     */
    HistoricTaskInstance validateReadPermissionOnTask(String taskId, String userId, boolean validateReadProcessInstance, boolean validateReadParentTask);

    /**
     * 校验用户是否有权限读取任务
     *
     * @param taskId
     * @param userId
     * @param validateReadProcessInstance
     * @param validateReadParentTask
     */
    Task validateReadPermissionOnTask2(String taskId, String userId, boolean validateReadProcessInstance, boolean validateReadParentTask);

    /**
     * 判断用户是否是任务的所有者或者执行人
     *
     * @param currentUser
     * @param task
     */
    boolean isTaskOwnerOrAssignee(String currentUser, Task task);

    /**
     * 判断用户是否是任务的所有者或者执行人
     *
     * @param currentUser
     * @param taskId
     */
    boolean isTaskOwnerOrAssignee(String currentUser, String taskId);

    /**
     * 判断是否流程启动人，且流程启动人是否可以完成任务
     *
     * @param userId
     * @param task
     */
    boolean validateIfUserIsInitiatorAndCanCompleteTask(String userId, TaskInfo task);

    /**
     * 判断流程启动人是否可以完成任务
     *
     * @param task
     */
    boolean validateIfInitiatorCanCompleteTask(TaskInfo task);

    /**
     * 判断是否任务的关联人
     *
     * @param userId
     * @param taskId
     */
    boolean isInvolved(String userId, String taskId);

    /**
     * 校验用户是否有权限读取该流程实例
     *
     * @param userId
     * @param processInstanceId
     */
    HistoricProcessInstance validateReadPermissionOnProcessInstance(String userId, String processInstanceId);

    /**
     * 判断用户是否有权限读取该流程实例
     *
     * @param userId
     * @param processInstanceId
     */
    boolean hasReadPermissionOnProcessInstance(String userId, String processInstanceId);

    /**
     * 判断用户是否有权限读取该流程实例
     *
     * @param userId
     * @param historicProcessInstance
     * @param processInstanceId
     */
    boolean hasReadPermissionOnProcessInstance(String userId, HistoricProcessInstance historicProcessInstance, String processInstanceId);

    /**
     * 判断任务是否挂起
     *
     * @param task
     * @return
     */
    boolean isTaskPending(Task task);

    /**
     * 是否可以转办任务
     * <p>
     * 1.任务所有人可以转办
     * 2.任务执行人可以转办，但要求任务非委派状态
     * 3.被转办人不能是当前任务执行人
     *
     * @param taskId
     * @param userId
     * @param assignee
     */
    Task validateAssignPermissionOnTask(String taskId, String userId, String assignee);

    /**
     * 是否可以委派任务
     * <p>
     * 1.任务所有人可以委派
     * 2.任务执行人可以委派
     * 3.被委派人不能是任务所有人和当前任务执行人
     *
     * @param taskId
     * @param userId
     * @param delegater
     * @return
     */
    Task validateDelegatePermissionOnTask(String taskId, String userId, String delegater);

    /**
     * 校验用户是否可以执行任务
     *
     * @param taskId
     * @param userId
     */
    Task validateExcutePermissionOnTask(String taskId, String userId);

    /**
     * 校验用户是否可以终止流程
     *
     * @param taskId
     * @param userId
     */
    ProcessInstance validateStopProcessInstancePermissionOnTask(String taskId, String userId);

}
