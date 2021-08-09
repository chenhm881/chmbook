package com.chm.book.files.schedule;


import com.chm.book.files.domain.TaskEntity;
import com.chm.book.files.inteface.IFileScheduleService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.quartz.*;
import org.quartz.impl.matchers.EverythingMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class QuartzScheduleService implements IFileScheduleService {

    @Autowired
    private Scheduler scheduler;

    public void addTask(TaskEntity taskEntity) {

        String jobName = taskEntity.getJobName(),
                jobGroup = taskEntity.getJobGroup(),
                cronExpression = taskEntity.getCronExpression(),
                jobDescription = taskEntity.getJobDescription();
        Date startTime = new Date(taskEntity.getStartTime()),
                endTime = new Date(taskEntity.getEndTime());
        JobDataMap jobDataMap = taskEntity.getJobDataMap();

        try {
            Class<? extends Job> clazz = (Class<? extends Job>) Class.forName(taskEntity.getJobClass());
            JobDetail jobDetail = JobBuilder.newJob(clazz).withDescription(jobDescription).withIdentity(jobName, jobGroup).build();

            jobDetail.getJobDataMap().putAll(jobDataMap);  //transfer data to the job
            //withMisfireHandlingInstructionFireAndProceed
            //withMisfireHandlingInstructionDoNothing
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup)
                    .startAt(startTime)
                    .endAt(endTime)
                    .withDescription(jobDescription)
                    .withSchedule(cronScheduleBuilder).build();


            //commit 1
            scheduler.getListenerManager().addTriggerListener(new MyTriggerListener(), EverythingMatcher.allTriggers());
            scheduler.getListenerManager().addSchedulerListener(new MySchedulerListener());
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateTask(TaskEntity taskEntity) {
        String jobName = taskEntity.getJobName(),
                jobGroup = taskEntity.getJobGroup(),
                cronExpression = taskEntity.getCronExpression(),
                jobDescription = taskEntity.getJobDescription(),
                createTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        Date startTime = new Date(taskEntity.getStartTime()),
                endTime = new Date(taskEntity.getEndTime());
        JobDataMap jobDataMap = taskEntity.getJobDataMap();
        try {
            JobKey jobKey = new JobKey(jobName, jobGroup);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            jobDetail.getJobBuilder().withDescription(jobDescription);
            jobDetail.getJobDataMap().putAll(jobDataMap);  //transfer data to the job

            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);

            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(createTime)
                    .startAt(startTime)
                    .endAt(endTime)
                    .withSchedule(cronScheduleBuilder).build();

            Set<Trigger> triggerSet = new HashSet<>();
            triggerSet.add(cronTrigger);

            scheduler.scheduleJob(jobDetail, triggerSet, true);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void resumeTask(TaskEntity taskEntity) {
        try {
            scheduler.resumeJob(JobKey.jobKey(taskEntity.getJobName(), taskEntity.getJobGroup()));
        } catch (Exception e) {
            //log.error("", e);
        }
    }

    public void pauseTask(TaskEntity taskEntity) {
        TriggerKey triggerKey = TriggerKey.triggerKey(taskEntity.getJobName(), taskEntity.getJobGroup());
        try {
            if (checkExists(taskEntity.getJobName(), taskEntity.getJobGroup())) {
                scheduler.pauseTrigger(triggerKey); //停止触发器
            }
        } catch (Exception e) {
            //log.error("", e);
        }
    }

    public void unscheduleTask(TaskEntity taskEntity) {
        TriggerKey triggerKey = TriggerKey.triggerKey(taskEntity.getJobName(), taskEntity.getJobGroup());
        try {
            if (checkExists(taskEntity.getJobName(), taskEntity.getJobGroup())) {
                scheduler.pauseTrigger(triggerKey); //停止触发器
                scheduler.unscheduleJob(triggerKey); //移除触发器
            }
        } catch (SchedulerException e) {
            //log.error("", e);
        }
    }

    public boolean checkExists(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        return scheduler.checkExists(triggerKey);
    }
}
