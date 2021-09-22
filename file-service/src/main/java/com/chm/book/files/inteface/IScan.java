package com.chm.book.files.inteface;


import com.chm.book.files.domain.FileScanResponse;
import com.chm.book.files.service.ScanDispatch;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface IScan {
    void action(Integer categoryId, Map<Integer, String> dirs, String fileLocation, String action);
    FileScanResponse run(ScanDispatch scanDispatch, Integer projectId,  List<String> dirs,
                         String fileLocation);
}
