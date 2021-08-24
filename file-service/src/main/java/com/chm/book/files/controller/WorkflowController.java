package com.chm.book.files.controller;

import com.chm.book.files.domain.WorkflowSettings;
import com.chm.book.files.service.WorkflowSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@EnableAsync
@RequestMapping("/api/")
public class WorkflowController {

    @Autowired
    private WorkflowSettingsService workflowSettingsService;

    @RequestMapping("findWorkflowSettings")
    public ResponseEntity<Map<String, Object>> findWorkflowSettings(@RequestBody Map<String, Integer> ids) {
        Integer projectId = ids.get("projectId");
        Integer workflowId = ids.get("workflowId");
        WorkflowSettings workflowSettings = workflowSettingsService.findWorkflowSettings(projectId, workflowId);
        Map<String, Object> workflowSettingsMap = new HashMap<>();
        workflowSettingsMap.put("data", workflowSettings);
        workflowSettingsMap.put("status", HttpStatus.OK);
        return new ResponseEntity<>(workflowSettingsMap, HttpStatus.OK);
    }

    @RequestMapping("updateWorkflowSettings")
    public ResponseEntity<Map<String,Object>> insertOrUpdate(@RequestBody WorkflowSettings workflowSettings) {
        Integer updateStatus = workflowSettingsService.insertOrUpdate(workflowSettings);
        Map<String, Object> returnValueMap = new HashMap<>();
        returnValueMap.put("data", updateStatus != 0);
        returnValueMap.put("status", HttpStatus.OK);
        return new ResponseEntity<>(returnValueMap, HttpStatus.OK);
    }
}
