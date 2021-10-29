package com.shui.flowable.service;


import com.shui.flowable.dto.TaskRequestDTO;
import com.shui.flowable.dto.TaskResponseDTO;
import com.shui.flowable.enums.CommentTypeEnum;
import org.flowable.engine.task.Comment;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;

import java.util.List;

public interface FlowableTaskService {
    /**
     * 查询任务详情
     */
    TaskResponseDTO getTask(String taskId);

    /**
     * 修改任务
     */
    TaskResponseDTO updateTask(TaskRequestDTO taskRequestDTO);

    /**
     * 删除任务
     */
    void deleteTask(String taskId);

    /**
     * 转办任务
     */
    void assignTask(TaskRequestDTO taskRequest);

    /**
     * 新增任务参与人
     */
    void involveUser(String taskId, String involveUserId);

    /**
     * 移除任务参与人
     */
    void removeInvolvedUser(String taskId, String involveUserId);

    /**
     * 认领任务
     */
    void claimTask(TaskRequestDTO taskRequest);

    /**
     * 取消认领
     */
    void unclaimTask(TaskRequestDTO taskRequest);

    /**
     * 新增任务关联人
     *
     * @param task
     * @param userId
     * @param linkType
     */
    void addIdentiyLinkForUser(Task task, String userId, String linkType);

    /**
     * 委派任务
     */
    void delegateTask(TaskRequestDTO taskRequest);

    /**
     * 新增过程意见
     *
     * @param taskId
     * @param processInstanceId
     * @param userId
     * @param type
     * @param message
     */
    void addComment(String taskId, String processInstanceId, String userId, CommentTypeEnum type, String message);

    /**
     * 查询过程意见
     *
     * @param taskId
     * @param processInstanceId
     * @param type
     * @param userId
     */
    List<Comment> getComments(String taskId, String processInstanceId, String type, String userId);

    /**
     * 新增任务关联信息
     *
     * @param taskIdentityRequest
     */
    void saveTaskIdentityLink(TaskRequestDTO taskIdentityRequest);

    /**
     * 删除任务关联信息
     *
     * @param taskId
     * @param identityId
     * @param identityType
     */
    void deleteTaskIdentityLink(String taskId, String identityId, String identityType);

    /**
     * 查询单一任务详情
     */
    Task getTaskNotNull(String taskId);

    /**
     * 查询单一历史任务详情
     */
    HistoricTaskInstance getHistoricTaskInstanceNotNull(String taskId);
}