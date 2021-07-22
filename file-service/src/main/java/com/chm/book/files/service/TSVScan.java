package com.chm.book.files.service;


import com.chm.book.files.inteface.IScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public abstract class TSVScan implements IScan {



    private String currentFileName = "";


    private Integer totalCount;

    private Integer successCount;

    private StringBuilder errorMessages;

}
