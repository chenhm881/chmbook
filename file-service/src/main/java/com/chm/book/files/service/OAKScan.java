package com.chm.book.files.service;

import com.chm.book.files.domain.FileRaw;
import com.chm.book.files.inteface.IScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class OAKScan implements IScan {

    @Autowired
    private ScanFile scanFile;

    @Autowired
    private OAKPiece oakPiece;


    private static Integer count = 1000;

    private String currentFileName = "";

    @Override
    public void action(Integer projectId, Map<Integer, String> dirs, String fileLocation, String action) {

        List<FileRaw> fileRawList = new ArrayList<>();

        Consumer<Map<String, Object>> execute = inputObj -> {
            Map<String, Object> fileLevelMap = inputObj;

            File file = (File) fileLevelMap.get("file");
            currentFileName = file.getPath();
            Map<Integer, String> dir = (Map<Integer, String>) fileLevelMap.get("dir");

            FileRaw fileRaw = oakPiece.check(file, dir, projectId);;
            fileRawList.add(fileRaw);
        };

        Function<Object, Integer> checkDirection = inputObj ->
                oakPiece.checkDirection((Map<String, Object>) inputObj, projectId);
        try {
            scanFile.ScanEntrance(fileLocation, dirs, checkDirection, execute);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
