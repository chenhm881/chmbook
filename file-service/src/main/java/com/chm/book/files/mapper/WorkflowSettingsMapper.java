package com.chm.book.files.mapper;


import com.chm.book.files.domain.WorkflowSettings;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WorkflowSettingsMapper {
    WorkflowSettings findWorkflowSettings(Integer categoryId, Integer workflowId);
    Integer insertOrUpdate(WorkflowSettings workflowSettings);
    Integer update(WorkflowSettings workflowSettings);
    Integer updateMatterInfo(Integer categoryId, Integer workflowId, String matterInfo);
}
