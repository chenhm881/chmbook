package com.chm.book.files.service;


import com.chm.book.files.availability.FilesHystrixObservableCommand;
import com.chm.book.files.domain.FileScanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileScanService {

    @Autowired
    private FileScanExecute fileScanExecute;

    public List<FileScanResponse> addFolderScanning(Integer projectId, List<String> locations, String action) {
        final List<FileScanResponse> fileScanResponses = new ArrayList<>();

        FilesHystrixObservableCommand filesHystrixObservableCommand = new FilesHystrixObservableCommand(fileScanExecute, projectId, locations, action);
        filesHystrixObservableCommand.observe().subscribe(new Observer<FileScanResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(FileScanResponse fileScanResponse) {
                fileScanResponses.add(fileScanResponse);
            }
        });
        return fileScanResponses;
    }
}
