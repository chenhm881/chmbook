package com.chm.book.files.service;

import com.chm.book.files.inteface.IScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ScanFactory {

    @Autowired
    @Resource(type = OAKScan.class)
    private IScan oakSan;


    @Autowired
    @Resource(type = TSVScan.class)
    private IScan tsvScan;

    public IScan getScan(Integer projectId) {
        IScan scanning = null;
        switch (projectId) {
            case 1:
                scanning = oakSan;
                break;
            case 2:
                scanning = tsvScan;
                break;
        }
        return scanning;
    }
}
