package com.chm.book.files.mapper;

import com.chm.book.files.domain.FileRaw;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FileRawMapper {

    Integer insertFileRawList(List<FileRaw> fileRawList);

}
