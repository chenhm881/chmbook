package com.chm.book.files.service;

import com.chm.book.files.domain.FileRaw;
import com.chm.book.files.mapper.FileRawMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AudioJsonStore {

    @Autowired
    private FileRawMapper fileRawMapper;


    @Transactional(value = "jtaTransactionManager",
            isolation = Isolation.READ_COMMITTED,
            timeout = 300000,
            rollbackFor = {RuntimeException.class, Exception.class})
    public void store(List<FileRaw> fileRawList) {
        if (fileRawList.size() > 0) fileRawMapper.insertFileRawList(fileRawList);
    }
}
