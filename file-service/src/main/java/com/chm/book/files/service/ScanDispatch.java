package com.chm.book.files.service;

import com.chm.book.files.domain.FileRaw;
import com.chm.book.files.holderes.FileRawListHolder;
import com.chm.book.files.inteface.IPiece;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class ScanDispatch {

    @Setter
    private IPiece iPiece;

    @Getter
    private Integer fileRawCount = 0;

    public Integer checkDirection(Map<String, Object> inputObj, Integer categoryId) {
        return iPiece.checkDirection((Map<String, Object>) inputObj, categoryId);
    }

    public String run(Map<String, Object> inputObj, ThreadPoolExecutor poolExecutor, Integer categoryId) {
        Map<String, Object> fileLevelMap = inputObj;

        File file = (File) fileLevelMap.get("file");
        List<String> dir = (List<String>) fileLevelMap.get("dir");
        StringUtils.join(dir, "/");

        iPiece.startTask(file, poolExecutor, dir, categoryId);
        return file.getPath();
    };
}
