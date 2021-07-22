package com.chm.book.files.mapper;

import com.chm.book.files.domain.Files;

public interface FilesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Files record);

    int insertSelective(Files record);

    Files selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Files record);

    int updateByPrimaryKeyWithBLOBs(Files record);

    int updateByPrimaryKey(Files record);
}