package com.servise;


import java.util.List;

/**
 * Created by Колян on 25.05.15.
 * interface announces all methods to work with files and directories
 */

public interface FileService {

    public long getFileSize(String path);

    public long getDirSize(String path);

    public String getReport(long size);

    public void scan(String dir);

    public List<String> analyze(String searchKey);

}
