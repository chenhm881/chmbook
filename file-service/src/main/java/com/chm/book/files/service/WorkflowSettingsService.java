package com.chm.book.files.service;

import com.chm.book.files.domain.WorkflowSettings;
import com.chm.book.files.mapper.WorkflowSettingsMapper;
import com.chm.book.files.schedule.WorkflowScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WorkflowSettingsService {

    @Autowired
    private WorkflowSettingsMapper workflowSettingsMapper;

    @Autowired
    private WorkflowScheduleService workflowScheduleService;

    public WorkflowSettings findWorkflowSettings(Integer categoryId, Integer workflowId) {
        WorkflowSettings workflowSettings = workflowSettingsMapper.findWorkflowSettings(categoryId, workflowId);
        if (workflowSettings == null) {
            workflowSettings = new WorkflowSettings();
            workflowSettings.setDays("");
            workflowSettings.setHours("");
        }
        return workflowSettings;
    }

    public Integer insertOrUpdate(WorkflowSettings workflowSettings) {
        Integer retValue;
        retValue = workflowSettingsMapper.insertOrUpdate(workflowSettings);
        workflowScheduleService.runTask(workflowSettings);
        return retValue;
    }

    public Integer updateMatterInfo(Integer categoryId, Integer workflowId, String matterInfo) {
        return workflowSettingsMapper.updateMatterInfo(categoryId, workflowId, matterInfo);
    }
}

