package com.chm.book.files.schedule;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySchedulerListener implements SchedulerListener {

    Logger logger = LoggerFactory.getLogger(MySchedulerListener.class);

    @Override
    public void jobScheduled(Trigger trigger) {
        String name = trigger.getJobKey().getName();
        String group = trigger.getJobKey().getGroup();
        logger.warn("Job Scheduled: Quartz job key name: " + name);
        logger.warn("Job Scheduled: Quartz job group name: " + group);
    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {

    }

    @Override
    public void triggerFinalized(Trigger trigger) {

    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {

    }

    @Override
    public void triggersPaused(String s) {

    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {

    }

    @Override
    public void triggersResumed(String s) {

    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        String name = jobDetail.getKey().getName();
        String group = jobDetail.getKey().getGroup();
        logger.warn("Job Added: Quartz job key name: " + name);
        logger.warn("Job Added: Quartz job group name: " + group);
    }

    @Override
    public void jobDeleted(JobKey jobKey) {

    }

    @Override
    public void jobPaused(JobKey jobKey) {

    }

    @Override
    public void jobsPaused(String s) {

    }

    @Override
    public void jobResumed(JobKey jobKey) {

    }

    @Override
    public void jobsResumed(String s) {

    }

    @Override
    public void schedulerError(String s, SchedulerException e) {

    }

    @Override
    public void schedulerInStandbyMode() {

    }

    @Override
    public void schedulerStarted() {

    }

    @Override
    public void schedulerStarting() {

    }

    @Override
    public void schedulerShutdown() {

    }

    @Override
    public void schedulerShuttingdown() {

    }

    @Override
    public void schedulingDataCleared() {

    }
}
