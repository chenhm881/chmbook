package com.chm.book.fileapi.controller;

import com.chm.book.fileapi.domain.BatchInfo;
import com.chm.book.fileapi.service.EntityPronPublish;
import com.chm.book.fileapi.service.FileHitProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class IndexController {

    @Autowired
    private FileHitProducer fileHitProducer;

    @Autowired
    private EntityPronPublish entityPronPublish;

    @CrossOrigin
    @RequestMapping("hello/{mes}")
    public String helloWorld(@PathVariable String mes) {
        //int a = 5/0;
        fileHitProducer.send(mes);
        return mes;
    }

    @RequestMapping("find")
    public String getBlog(@RequestBody BatchInfo batchInfo)
    {
        fileHitProducer.send(batchInfo);
        return "hello article";
    }

    @CrossOrigin
    @RequestMapping("search")
    public String search(@RequestBody BatchInfo batchInfo) {
        //int a = 5/0;
        entityPronPublish.setMessage(batchInfo);
        return "search file";
    }

}
