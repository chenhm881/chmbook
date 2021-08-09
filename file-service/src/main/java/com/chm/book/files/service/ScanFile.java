package com.chm.book.files.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ScanFile {


    public void ScanEntrance(String fileLocation, Map<Integer, String> dirs, Function<Object, Integer> checkDirection, Consumer<Map<String, Object>> execute) throws IOException, ClassNotFoundException {
        File file = new File(fileLocation);
        Integer maxKey = dirs.size();
        scanDirection(file, maxKey, dirs, checkDirection, execute);
    }

    private void scanDirection(File f, int level, Map<Integer, String> dir, Function<Object, Integer> checkDirection, Consumer<Map<String, Object>> execute) throws IOException, ClassNotFoundException {

        if(f.isDirectory()) level++;

        Map<Object, List<File>> children = Arrays.stream(f.listFiles())
                .collect(Collectors.groupingBy(name -> name.getName()));

        for (Map.Entry<Object, List<File>> entry : children.entrySet()) {
            List<File> files = entry.getValue();
            files.sort((o1, o2) -> {
                String file1 = o1.getName();
                String file2 = o2.getName();
                if (file1.length() > file2.length()) return 1;
                else if(file1.length() < file2.length()) return - 1;
                return file1.compareTo(file2);
            });
            int size = files.size();
            for (int i= 0; i < size; i++) {
                File file = files.get(i);
                if (file.isDirectory()) {
                    String value = file.getName();
                    dir.put(level, value);

                    Map<String, Object> fileLevelMap = new HashMap<>();
                    fileLevelMap.put("file", file);
                    fileLevelMap.put("key", level);
                    fileLevelMap.put("dir", dir);
                    Integer existed = checkDirection.apply(fileLevelMap);

                    scanDirection(file, level, dir, checkDirection, execute);
                } else {
                    Map<String, Object> fileLevelMap = new HashMap<>();
                    fileLevelMap.put("file", file);
                    fileLevelMap.put("dir", dir);
                    execute.accept(fileLevelMap);
                }
            }
        }
    }

}
