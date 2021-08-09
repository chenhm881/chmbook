package com.chm.book.files.service;

import com.chm.book.files.domain.FileRawJson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

@Service
public class JsonCovertService<T> {
    public T createJsonOjb(String jsonFileStr, Type type) {
        T t = null;
        try {
            t = new Gson().fromJson(jsonFileStr, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public FileRawJson createFileRawObj(File file) {
        FileRawJson fileRawJson = null;
        try {
            String jsonFileStr = FileUtils.readFileToString(file, "UTF-8");
            fileRawJson = (FileRawJson)createJsonOjb(jsonFileStr, new TypeToken<FileRawJson>() {}.getType());
            fileRawJson.setJsonString(jsonFileStr);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileRawJson;
    }
}
