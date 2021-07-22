package com.chm.book.files.controller;

import com.chm.book.files.service.FileScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/")
public class FileScanController {
    @Autowired
    private FileScanService fileScanService;


    @RequestMapping("scanOnDemand")
    public ResponseEntity<Map<String,Object>> scanOnDemand(@RequestBody Map<String, Object> param) {
        Integer projectId = Integer.valueOf(param.get("projectId").toString());
        String fixedLocation = param.get("location").toString();
        Map<String,Object> initializedMap = new HashMap<>();
        initializedMap.put("data", fileScanService.addFolderScanning(projectId, fixedLocation, "add"));
        initializedMap.put("status", HttpStatus.OK);
        return new ResponseEntity<>(initializedMap, HttpStatus.OK);
    }

}
