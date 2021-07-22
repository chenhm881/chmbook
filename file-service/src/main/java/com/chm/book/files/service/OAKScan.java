package com.chm.book.files.service;

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
import java.util.stream.Collectors;

@Component
public class OAKScan implements IScan {



    private Integer totalCount;

    private Integer successCount;

    private StringBuilder errorMessages;

    private static Integer count = 1000;

    private String currentFileName = "";


    @Override
    public void action(Integer projectId, Map<Integer, String> dirs, String fileLocation, String action) {

    }
}
