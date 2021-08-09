package com.chm.book.files.service;

import com.chm.book.files.domain.FileRaw;
import com.chm.book.files.inteface.IScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class OAKScan implements IScan {

    @Autowired
    private ScanFile scanFile;

    @Autowired
    private OAKPiece oakPiece;

    @Autowired
    private AudioJsonStore audioJsonStore;

    private static Integer count = 1000;

    private String currentFileName = "";

    @Override
    public void action(Integer categoryId, Map<Integer, String> dirs, String fileLocation, String action) {

        List<FileRaw> fileRawList = new ArrayList<>();

        Consumer<Map<String, Object>> execute = inputObj -> {
            Map<String, Object> fileLevelMap = inputObj;

            File file = (File) fileLevelMap.get("file");
            currentFileName = file.getPath();
            Map<Integer, String> dir = (Map<Integer, String>) fileLevelMap.get("dir");

            FileRaw fileRaw = oakPiece.check(file, dir, categoryId);
            Optional.ofNullable(fileRaw).ifPresent(fileRawNew ->  fileRawList.add(fileRaw));
        };

        Function<Object, Integer> checkDirection = inputObj ->
                oakPiece.checkDirection((Map<String, Object>) inputObj, categoryId);
        try {
            scanFile.ScanEntrance(fileLocation, dirs, checkDirection, execute);

            audioJsonStore.store(fileRawList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}