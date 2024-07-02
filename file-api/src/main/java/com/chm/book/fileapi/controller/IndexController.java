package com.chm.book.fileapi.controller;

import com.chm.book.fileapi.domain.BatchInfo;
import com.chm.book.fileapi.service.EntityPronPublish;
import com.chm.book.fileapi.service.FileHitProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class IndexController {

    @Autowired
    private FileHitProducer fileHitProducer;

    @Autowired
    private EntityPronPublish entityPronPublish;

    @CrossOrigin
    @RequestMapping("hello/{mes}")
    public String helloWorld(@PathVariable Date mes) {
        //int a = 5/0;
        fileHitProducer.send(mes.toString());
        return mes.toString();
    }

    @RequestMapping("find")
    public String getBlog(Date batchInfo)
    {
        fileHitProducer.send(batchInfo.toString());
        return batchInfo.toString();
    }

    @GetMapping("date")
    public String getDate(@RequestParam("date") Date batchInfo)
    {
        fileHitProducer.send(batchInfo.toString());
        return batchInfo.toString();
    }

    @CrossOrigin
    @RequestMapping("search")
    public String search(@RequestBody BatchInfo batchInfo) {
        //int a = 5/0;
        entityPronPublish.setMessage(batchInfo);
        return "search file";
    }

}
