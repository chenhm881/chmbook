package com.chm.book.files.mapper;

import com.chm.book.files.domain.FileRaw;

public interface FileRawMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileRaw record);

    int insertSelective(FileRaw record);

    FileRaw selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileRaw record);

    int updateByPrimaryKeyWithBLOBs(FileRaw record);

    int updateByPrimaryKey(FileRaw record);
}
