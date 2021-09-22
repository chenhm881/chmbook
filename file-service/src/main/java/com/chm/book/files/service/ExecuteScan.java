package com.chm.book.files.service;

import com.chm.book.files.inteface.IScan;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ExecuteScan {

    @Autowired
    private ScanFactory scanFactory;

    @Setter
    @Getter
    private Integer projectId;
    @Setter
    @Getter
    private Integer workflowId;
    @Setter
    @Getter
    private String triggeredBy;

    @Autowired
    private ScanDispatch scanDispatch;

    public void onDemandExecute(Integer projectId, String fixedLocation, String action)  {

        String location = "E:\\oneforma\\tmp\\TSV";
        List<String> folders = new ArrayList<>();
        Map<Integer, String> dirs;
        if(fixedLocation.contains(location)) recursivePathParent(location, fixedLocation, folders);
        Collections.reverse(folders);
        dirs = IntStream.range(0, folders.size()).boxed().collect(Collectors.toMap(i -> i + 1, folders::get, (a, b) -> b));
        this.scanningAction(projectId, dirs, fixedLocation, action);
    }

    public void regularExecute(Integer projectId)  {

        String location = "E:\\oneforma\\tmp\\TSV";
        Map<Integer, String> dirs = new HashMap<>();
        this.scanningAction(projectId, dirs, location, "add");
    }

    private void recursivePathParent(String locationPath, String fixedLocation, List<String> folders) {
        if(!fixedLocation.equalsIgnoreCase(locationPath)) {
            File dir = new File(fixedLocation);
            folders.add(dir.getName());
            recursivePathParent(locationPath, dir.getParent(), folders);
        }
    }

    private void scanningAction(Integer projectId,  Map<Integer, String> dirs,
                                String fileLocation,  String action) {
        IScan scan= scanFactory.getScan(projectId);
        switch (action) {
            case "add":
                scan.action(projectId, dirs, fileLocation, action);
            break;
            case "addAsync":
                scan.run(scanDispatch, projectId, dirs.values().stream().collect(Collectors.toList()), fileLocation);
        }

    }
}
