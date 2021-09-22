package com.chm.book.files.controller;

import com.chm.book.files.service.FileScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/")
public class FileScanController {
    @Autowired
    private FileScanService fileScanService;

    @CrossOrigin
    @RequestMapping("addFiles")
    public ResponseEntity<Map<String,Object>> scanOnDemand(List<String> locations, Integer projectId) {
        Map<String,Object> initializedMap = new HashMap<>();
        initializedMap.put("data", fileScanService.addFolderScanning(projectId, locations, "add"));
        initializedMap.put("status", HttpStatus.OK);
        return new ResponseEntity<>(initializedMap, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping("addFilesAsync")
    public ResponseEntity<Map<String,Object>> scanOnDemandAsync(List<String> locations, Integer projectId) {
        Map<String,Object> initializedMap = new HashMap<>();
        initializedMap.put("data", fileScanService.addFolderScanning(projectId, locations, "addAsync"));
        initializedMap.put("status", HttpStatus.OK);
        return new ResponseEntity<>(initializedMap, HttpStatus.OK);
    }
}
