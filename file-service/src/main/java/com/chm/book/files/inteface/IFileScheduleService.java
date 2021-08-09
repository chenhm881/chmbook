package com.chm.book.files.inteface;

import com.chm.book.files.domain.TaskEntity;
import org.quartz.SchedulerException;

public interface IFileScheduleService {
    public void addTask(TaskEntity taskEntity);
    public void updateTask(TaskEntity taskEntity);
    public void resumeTask(TaskEntity taskEntity);
    public void pauseTask(TaskEntity taskEntity);
    public boolean checkExists(String jobName, String jobGroup) throws SchedulerException;
}
