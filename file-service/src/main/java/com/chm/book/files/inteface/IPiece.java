package com.chm.book.files.inteface;

import com.chm.book.files.domain.FileRaw;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public interface IPiece {
    public List<FileRaw> check(File file, List<String> dir, Integer categoryId);
    public Integer checkDirection( Map<String, Object> fileLevelMap, Integer categoryId);
    void startTask(File file, ThreadPoolExecutor poolExecutor, List<String> dir, Integer categoryId);
}
