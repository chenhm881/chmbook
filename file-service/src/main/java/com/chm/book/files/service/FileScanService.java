package com.chm.book.files.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileScanService {

    @Autowired
    private ExecuteScan executeScan;

    public Integer addFolderScanning(Integer projectId, String location, String action) {
        executeScan.onDemandExecute(projectId, location, action);
        return 1;
    }
}
