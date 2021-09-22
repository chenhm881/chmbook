package com.chm.book.files.holderes;

import com.chm.book.files.domain.FileRaw;

import java.util.ArrayList;
import java.util.List;


public class FileRawListHolder {

    private static final ThreadLocal<List<FileRaw>> contextHolder = new ThreadLocal<>();

    public static void setFileRawList(List<FileRaw> fileRawList) {
        contextHolder.set(fileRawList);
    }
    public static List<FileRaw> getFileRawList() {
        return (contextHolder.get());
    }

    public static void clearFileRawList() {
        contextHolder.remove();
    }

}
