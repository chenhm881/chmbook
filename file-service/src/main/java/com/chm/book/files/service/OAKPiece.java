package com.chm.book.files.service;

import com.chm.book.files.domain.FileDispatch;
import com.chm.book.files.domain.FileRaw;
import com.chm.book.files.domain.FileRawJson;

import com.chm.book.files.domain.FileScanResponse;
import com.chm.book.files.holderes.FileListHolder;
import com.chm.book.files.holderes.FileRawListHolder;
import com.chm.book.files.inteface.IPiece;
import lombok.Synchronized;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;

import java.time.Instant;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
public class OAKPiece implements IPiece {

    @Autowired
    private JsonCovertService<FileRawJson> jsonCovertService;

    @Autowired
    private AudioJsonStore audioJsonStore;

    @Synchronized
    public List<FileRaw> check(File file, List<String> dir, Integer categoryId) {

        List<FileRaw> fileRaws = new ArrayList<>();
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
        Optional.ofNullable(fileRaw).ifPresent(fileRaw1 -> fileRaws.add(fileRaw1));
        return fileRaws;
    }

    @Synchronized
    public Integer checkDirection(Map<String, Object> fileLevelMap, Integer projectId) {

        Map<Integer, String> dir = (Map<Integer, String>) fileLevelMap.get("dir");
        Integer key = (Integer) fileLevelMap.get("key");
        if(key >= 2) {
            return 1;
        }
        return 0;
    }

    @Override
    public void startTask(File file, ThreadPoolExecutor poolExecutor, List<String> dir, Integer categoryId) {
        List<FileDispatch> fileList = FileListHolder.getFileList();
        FileDispatch fileDispatch = new FileDispatch();
        fileDispatch.setFile(file);
        fileDispatch.setDir(dir);
        fileDispatch.setPath(StringUtils.join(dir,"/").toString());
        fileDispatch.setCategoryId(categoryId);
        fileList.add(fileDispatch);
        Map<String, List<FileDispatch>> groupFileDispatchList = fileList.stream().collect(Collectors.groupingBy(FileDispatch::getPath));
        if (groupFileDispatchList.size() > 1)
        {
            List<FileDispatch> taskFileList = groupFileDispatchList.entrySet().stream()
                    .filter(dispatch -> !dispatch.getKey().equals(fileDispatch.getPath())).findFirst().get().getValue();
            Future<FileScanResponse> taskContext = poolExecutor.submit(new Callable<FileScanResponse>() {
                @Override
                public FileScanResponse call() throws Exception {
                    return executeStore(taskFileList, dir, categoryId, 1000);
                }
            });
            fileList = groupFileDispatchList.get(fileDispatch.getPath());
            FileListHolder.setFileRawList(fileList);
        }
    }

    private List<FileRaw> prepareStore(List<FileRaw> hitFileInfoList, FileRaw hitFileInfo, Integer count) {

        List<FileRaw> leftHitFileInfoList = new ArrayList<>();

        Map<String, List<FileRaw>> corpusHitFileInfoList = hitFileInfoList.stream()
                .collect(Collectors.groupingBy(FileRaw::getCorpus));
        int corpusSize = corpusHitFileInfoList.size();
        if (corpusSize > 1) {
            String corpusKey = corpusHitFileInfoList.keySet().stream()
                    .filter(item -> !item.equals(hitFileInfo.getCorpus())).findFirst().get();
            audioJsonStore.store(corpusHitFileInfoList.get(corpusKey));

            leftHitFileInfoList = corpusHitFileInfoList.get(hitFileInfo.getCorpus());
        } else {
            if (hitFileInfoList.size() >= count) {
                Map<String, List<FileRaw>> groupHitFileInfoList = hitFileInfoList.stream()
                        .collect(Collectors.groupingBy(FileRaw::getCompareKey));
                int groupSize = groupHitFileInfoList.size();
                if (groupSize > 1) {
                    List<FileRaw> storeHitFileInfoList = new ArrayList<>();
                    groupHitFileInfoList.entrySet().stream().filter(item -> !item.getKey().equals(hitFileInfo.getCompareKey()))
                            .forEach(item -> storeHitFileInfoList.addAll(item.getValue()));

                    audioJsonStore.store(storeHitFileInfoList);

                    String key = groupHitFileInfoList.keySet().stream()
                            .filter(item -> item.equals(hitFileInfo.getCompareKey())).findFirst().get();
                    leftHitFileInfoList = groupHitFileInfoList.get(key);
                }
            }
        }
        return leftHitFileInfoList;
    }


    private FileScanResponse executeStore(List<FileDispatch> fileDispatchList, List<String> dir, Integer categoryId,  Integer count) {
        List<FileRaw> fileRawList = new ArrayList<>();
        for(FileDispatch fileDispatch : fileDispatchList) {
            List<FileRaw> fileRaws = check(fileDispatch.getFile(), dir, categoryId);
            for(FileRaw fileRaw : fileRaws) {
                fileRawList.add(fileRaw);
                List<FileRaw> leftHitFileInfoList = prepareStore(fileRawList, fileRaw, count);
                if(leftHitFileInfoList.size() > 0) fileRawList = leftHitFileInfoList;
            }
        }
        audioJsonStore.store(fileRawList);
        return new FileScanResponse();
    }
}
