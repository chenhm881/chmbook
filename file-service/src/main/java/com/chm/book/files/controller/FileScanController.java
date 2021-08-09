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

    @CrossOrigin
    @RequestMapping("addFiles")
    public ResponseEntity<Map<String,Object>> scanOnDemand(@RequestBody Map<String, Object> params) {

        Integer projectId = Integer.valueOf(params.get("projectId").toString());
        String fixedLocation = params.get("location").toString();
        Map<String,Object> initializedMap = new HashMap<>();
        initializedMap.put("data", fileScanService.addFolderScanning(projectId, fixedLocation, "add"));
        initializedMap.put("status", HttpStatus.OK);
        return new ResponseEntity<>(initializedMap, HttpStatus.OK);
    }

}
