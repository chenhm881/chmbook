package com.chm.book.files.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyTriggerListener implements TriggerListener {

    Logger logger = LoggerFactory.getLogger(MyTriggerListener.class);

    @Override
    public String getName() {
        return "Scanning Trigger Listener";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
       //
        String name = jobExecutionContext.getJobDetail().getKey().getName();
        String group = jobExecutionContext.getJobDetail().getKey().getGroup();
        logger.warn("Trigger Fire: Quartz job key name: " + name);
        logger.warn("Trigger Fire: Quartz job group name: " + group);

    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        //
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        String name = trigger.getJobKey().getName();
        String group = trigger.getJobKey().getGroup();
        logger.warn("Trigger Misfire: Quartz job key name: " + name);
        logger.warn("Trigger Misfire: Quartz job group name: " + group);
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {
        String name = jobExecutionContext.getJobDetail().getKey().getName();
        String group = jobExecutionContext.getJobDetail().getKey().getGroup();
        logger.warn("Trigger Complete: Quartz job key name: " + name);
        logger.warn("Trigger Complete: Quartz job group name: " + group);
    }
}
