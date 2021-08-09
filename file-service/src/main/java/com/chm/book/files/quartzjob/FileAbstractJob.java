package com.chm.book.files.quartzjob;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;


@Service
public abstract class FileAbstractJob extends QuartzJobBean {

    @Override
    protected abstract void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException;
}
