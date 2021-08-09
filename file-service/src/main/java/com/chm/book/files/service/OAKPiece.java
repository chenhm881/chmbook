package com.chm.book.files.service;

import com.chm.book.files.domain.FileRaw;
import com.chm.book.files.domain.FileRawJson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;

import java.time.Instant;

import java.util.Map;

@Service
public class OAKPiece {

    @Autowired
    private JsonCovertService<FileRawJson> jsonCovertService;

    public FileRaw check(File file, Map<Integer, String> dir, Integer categoryId) {

        FileRaw fileRaw = null;
        boolean isJsonFile = file.getName().toLowerCase().endsWith(".json");
        if (isJsonFile) {
            FileRawJson fileJsonInfo = jsonCovertService.createFileRawObj(file);
            fileJsonInfo.setFileId(String.valueOf(categoryId));

            if(fileJsonInfo != null) {
                fileRaw = new FileRaw();
                fileRaw.setLocale(fileJsonInfo.getLocale());
                fileRaw.setJsonString(fileJsonInfo.getJsonString());
                fileRaw.setCheckTime(Instant.now().toEpochMilli());
                String fileUrl = UriComponentsBuilder.newInstance().scheme("http").host("localhost")
                        .pathSegment("file_storage", file.getName()).build().toUriString();
                fileRaw.setFileUrl(fileUrl);
                fileRaw.setStatus(0);
                fileRaw.setCategoryId(categoryId);
            }
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
