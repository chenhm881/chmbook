package com.chm.book.files.service;


import com.chm.book.files.domain.FileScanResponse;
import com.chm.book.files.inteface.IScan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class TSVScan implements IScan {


    private String currentFileName = "";


    private Integer totalCount;

    private Integer successCount;

    private StringBuilder errorMessages;

    @Override
    public void action(Integer projectId, Map<Integer, String> dirs, String fileLocation, String action) {

    }

    @Override
    public FileScanResponse run(ScanDispatch scanDispatch, Integer categoryId, List<String> dirs, String fileLocation) {
        return null;
    }

}
