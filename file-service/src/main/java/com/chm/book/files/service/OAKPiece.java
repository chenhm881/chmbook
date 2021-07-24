package com.chm.book.files.service;

import com.chm.book.files.domain.FileRaw;
import com.chm.book.files.domain.FileRawJson;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

import java.time.Instant;

import java.util.Map;

@Service
public class OAKPiece {


    public FileRaw check(File file, Map<Integer, String> dir, Integer projectId) {

        FileRawJson fileJsonInfo = new FileRawJson();
        boolean isJsonFile = file.getName().toLowerCase().endsWith(".json");
        if (isJsonFile) {
            fileJsonInfo.setFileId(String.valueOf(projectId));
        }
        FileRaw fileRaw = new FileRaw();

        if(fileJsonInfo != null) {
            fileRaw.setLocale(fileJsonInfo.getLocale());
            fileRaw.setJsonString(fileJsonInfo.getJsonString());
            fileRaw.setCheckTime(Instant.now().toEpochMilli());
            fileRaw.setStatus(0);
        }

        return fileRaw;
    }

    public Integer checkDirection( Map<String, Object> fileLevelMap, Integer projectId) {

        Map<Integer, String> dir = (Map<Integer, String>) fileLevelMap.get("dir");
        Integer key = (Integer) fileLevelMap.get("key");

        if(key >= 2) {
            return 1;
        }
        return 0;
    }

}
