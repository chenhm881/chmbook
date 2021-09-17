package com.chm.book.files.service;


import com.chm.book.files.domain.FileScanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FileScanExecute {

    @Autowired
    private ExecuteScan executeScan;

    public FileScanResponse execute(Integer projectId, String location, String action) {
        executeScan.onDemandExecute(projectId, location, action);
        return new FileScanResponse();
    }
}
