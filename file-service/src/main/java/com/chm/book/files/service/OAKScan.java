package com.chm.book.files.service;

import com.chm.book.files.domain.FileDispatch;
import com.chm.book.files.domain.FileRaw;
import com.chm.book.files.domain.FileScanResponse;
import com.chm.book.files.holderes.FileListHolder;
import com.chm.book.files.inteface.IScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OAKScan implements IScan {

    @Autowired
    private ScanFile scanFile;

    @Autowired
    private OAKPiece oakPiece;

    @Autowired
    private AudioJsonStore audioJsonStore;

    private static Integer count = 1000;

    private List<FileRaw> fileRawList;

    @Override
    public void action(Integer categoryId, Map<Integer, String> dirs, String fileLocation, String action) {

        Consumer<Map<String, Object>> execute = inputObj -> {
            Map<String, Object> fileLevelMap = inputObj;

            File file = (File) fileLevelMap.get("file");
            List<String> dir = (List<String>) fileLevelMap.get("dir");

            List<FileRaw> fileRaws = oakPiece.check(file, dir, categoryId);
            for(FileRaw fileRaw : fileRaws) {
                fileRawList.add(fileRaw);
                List<FileRaw> leftHitFileInfoList = prepareStore(fileRawList, fileRaw, count);
                if(leftHitFileInfoList.size() > 0) fileRawList = leftHitFileInfoList;
            }
        };
        Function<Object, Integer> checkDirection = inputObj ->
                oakPiece.checkDirection((Map<String, Object>) inputObj, categoryId);
        try {
            fileRawList = new ArrayList<>();
            scanFile.ScanEntrance(fileLocation, dirs, checkDirection, execute);
            audioJsonStore.store(fileRawList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileScanResponse run(ScanDispatch scanDispatch, Integer categoryId, List<String> dirs, String fileLocation) {

        ExecutorService executor = Executors.newFixedThreadPool(1);
        CompletableFuture.runAsync(() -> {
            scanDispatch.setIPiece(oakPiece);
            try {
                FileListHolder.clearFileList();
                FileListHolder.setFileRawList(new ArrayList<>());
                ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                        new LinkedBlockingDeque<Runnable>(5));
                scanFile.ScanStart(scanDispatch, poolExecutor, new ArrayList<String>(), fileLocation, categoryId);
                List<FileDispatch> fileDispatchList = FileListHolder.getFileList();
                poolExecutor.submit(new Callable<FileScanResponse>() {
                    @Override
                    public FileScanResponse call() throws Exception {
                        List<FileRaw> fileRawList1 = new ArrayList<>();
                        for(FileDispatch fileDispatch : fileDispatchList) {
                            List<FileRaw> fileRaws  = oakPiece.check(fileDispatch.getFile(),fileDispatch.getDir(), categoryId);
                            for (FileRaw fileRaw : fileRaws) {
                                fileRawList1.add(fileRaw);
                                List<FileRaw> leftHitFileInfoList = prepareStore(fileRawList1, fileRaw, 1000);
                                if(leftHitFileInfoList.size() > 0) fileRawList1 = leftHitFileInfoList;
                            }
                        }
                        audioJsonStore.store(fileRawList1);
                        return new FileScanResponse();
                    }
                });
                poolExecutor.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }, executor);
        return new FileScanResponse();
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

}
