package com.chm.book.files.holderes;

import com.chm.book.files.domain.FileDispatch;

import java.io.File;
import java.util.List;


public class FileListHolder {

    private static final ThreadLocal<List<FileDispatch>> contextHolder = new ThreadLocal<>();

    public static void setFileRawList(List<FileDispatch> fileList) {
        contextHolder.set(fileList);
    }
    public static List<FileDispatch> getFileList() {
        return (contextHolder.get());
    }
    public static void clearFileList() {
        contextHolder.remove();
    }

}
