package com.chm.book.files.schedule;


import com.chm.book.files.domain.TaskEntity;
import com.chm.book.files.domain.WorkflowSettings;
import com.chm.book.files.quartzjob.FileInfoScanningJob;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkflowScheduleService {

    @Autowired
    private QuartzScheduleService quartzScheduleService;

    private static String jobGroup = "engineerMC";

    public void runTask(WorkflowSettings workflowSettings){
        Integer projectId = workflowSettings.getProjectId(),
                workflowId = workflowSettings.getWorkflowId();
        boolean enable = workflowSettings.getEnable();
        String jobName = projectId + "_" + workflowId;
        try {
            Boolean jobExisted = quartzScheduleService.checkExists(jobName, jobGroup);
            if(jobExisted) {
                if(enable) {updateTask(workflowSettings);}
                else {unscheduleTask(projectId, workflowId);}
            } else {
                if(enable) addTask(workflowSettings);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void addTask(WorkflowSettings workflowSettings) {
        TaskEntity taskEntity = createTaskEntity(workflowSettings);
        quartzScheduleService.addTask(taskEntity);
    }

    public void pauseTask(Integer projectId, Integer workflowId) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setJobName(projectId + "_" + workflowId);
        taskEntity.setJobGroup(jobGroup);
        quartzScheduleService.pauseTask(taskEntity);
    }

    public void resumeTask(Integer projectId, Integer workflowId) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setJobName(projectId + "_" + workflowId);
        taskEntity.setJobGroup(jobGroup);
        quartzScheduleService.resumeTask(taskEntity);
    }

    public void updateTask(WorkflowSettings workflowSettings) {
        TaskEntity taskEntity = createTaskEntity(workflowSettings);
        quartzScheduleService.updateTask(taskEntity);
    }

    public void unscheduleTask(Integer projectId, Integer workflowId) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setJobName(projectId + "_" + workflowId);
        taskEntity.setJobGroup(jobGroup);
        quartzScheduleService.unscheduleTask(taskEntity);
    }

    private TaskEntity createTaskEntity(WorkflowSettings workflowSettings) {
        Integer projectId = workflowSettings.getProjectId(),
                workflowId = workflowSettings.getWorkflowId();
        String days = workflowSettings.getDays(),
                hours = workflowSettings.getHours();
        LocalDateTime startTime = workflowSettings.getStart(),
                endTime = workflowSettings.getEnd();

        Map<String, String> fileLocationMap = new HashMap<>();

        TaskEntity taskEntity = new TaskEntity();

        taskEntity.setJobClass(FileInfoScanningJob.class.getCanonicalName());

        taskEntity.setJobGroup(jobGroup);

        taskEntity.setJobName(projectId + "_" + workflowId);

        taskEntity.setCronExpression(createCronExpression(hours, days));
        taskEntity.setStartTime(startTime.atZone(ZoneId.of("-07:00")).toInstant().toEpochMilli());
        taskEntity.setEndTime(endTime.atZone(ZoneId.of("-07:00")).toInstant().toEpochMilli());

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("fileLocationMap", fileLocationMap);
        taskEntity.setJobDataMap(jobDataMap);
        return taskEntity;
    }

    private String createCronExpression(String hours, String days) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0 0"); // second and minute
        stringBuilder.append(" ");
        stringBuilder.append(hours); // hour
        stringBuilder.append(" ");
        stringBuilder.append("?"); // day of month
        stringBuilder.append(" ");
        stringBuilder.append("*");  // month
        stringBuilder.append(" ");
        stringBuilder.append(days); // weeks
        stringBuilder.append(" ");
        stringBuilder.append("*"); // year
        return stringBuilder.toString();
    }
}
