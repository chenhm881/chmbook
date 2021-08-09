package com.chm.book.files.quartzjob;

import com.chm.book.files.service.ExecuteScan;
import lombok.Getter;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class FileInfoScanningJob extends FileAbstractJob {

    @Autowired
    private ExecuteScan executeScan;

    @Getter
    private Integer projectId;

    @Getter
    private Integer workflowId;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String[] idsArr= jobExecutionContext.getTrigger().getJobKey().getName().split("_");
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        Map<String, String> fileLocationMap = (Map<String, String>) jobDataMap.get("fileLocationMap");
        projectId = Integer.valueOf(idsArr[0]);
        workflowId = Integer.valueOf(idsArr[1]);
        executeScan.setProjectId(projectId);
        executeScan.setWorkflowId(workflowId);
        executeScan.setTriggeredBy("system");
        executeScan.regularExecute(projectId);
    }
}
