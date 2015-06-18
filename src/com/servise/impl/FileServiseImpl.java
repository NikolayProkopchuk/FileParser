package com.servise.impl;

import com.servise.FileService;
import com.util.ReportWriter;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Колян on 25.05.15.
 * Counts size of directory and scanning it
 * Writes report and file names in file by means of instance of ReportWriter
 */

public class FileServiseImpl implements FileService {
    int dirCount = 0;
    int filesCount = 0;
    List<String> targetFileList = new ArrayList<String>();
    List<String> targetDirectoryList = new ArrayList<String>();
    private static ReportWriter report = new ReportWriter();

    /**
     * Returns size of file
     * @param path String's describing the location of file
     * @return     file's size
     */
    @Override
    public long getFileSize(String path) {
        File file = new File(path);
        if (file.exists()){
            return file.length();
        }
        return 0;
    }

    /**
     * Returns size of directory
     * Writes names of files in file
     * @param path String's describing the location of directory
     * @return     directories size
     */
    @Override
    public long getDirSize(String path) {
        long dirSize = 0;
        File file = new File(path);
        //Set<String> targetFileNames = new HashSet<String>();

        if (file.exists() && file.isDirectory()){
            File[] files = file.listFiles();
            if (files != null){
                List<File> fileList = Arrays.asList(files);
                Collections.sort(fileList,
                        (file1, file2)->(file1.getName()).compareTo(file2.getName()));

                for (File f : fileList){
                    if (f.isFile()){
                        //report.writeFileName("File:" + f.getPath());
                        //targetFileNames.add("File:" + f.getPath());
                        targetFileList.add(f.getPath());
                        dirSize += f.length();
                        filesCount++;
                    } else {
                        dirCount++;
                        targetDirectoryList.add(f.getPath());
                        long size = this.getDirSize(f.getPath());
                        dirSize = dirSize + size;
                    }
                }
                /*for (String fileNames : targetFileNames){
                    report.writeFileName(fileNames);
                }*/
            }
        }
        return dirSize;
    }

    /**
     * Returns string, in which contains size from bytes
     * to gigabytes, megabytes, kilobytes and bytes
     * Writes this string in top of file
     * @param size long number of file size
     * @return      string directorie size
     */
    @Override
    public String getReport(long size) {
        String result = "";
        if(size < 1024){
            result = "Size is " + size + " bytes";
        } else if(size > 1023 && size < 1048576){
            result = "Size is " + size/1024 + " KBytes " + size%1024 + " bytes.";
        }else if(size > 1048575 && size < 1073741824){
            result = "Size is " + size/1048576 + " MBytes " + (size%1048576)/1024
                    + " KBytes " + size%1024 + " bytes.";
        }else {
            result = "Size is " + size/1073741824 + " GBytes "+ (size%1073741824)/1048576
                    + " MBytes " + (size%1048576)/1024 + " KBytes " + size%1024 + " bytes";
        }
        report.writeInFile(result + System.lineSeparator() + "Count of directories is: " +
                getDirCount() +  System.lineSeparator() +
                "Count of files is: " + getFilesCount());
        //report.finishMyJob();
        return result;
    }

    /**
     * launches getDirSize to count directory size
     * launches getReport  to write report
     * @param dir String's describing the location of directory     *
     */
    @Override
    public void scan(String dir) {
        long size = this.getDirSize(dir);
        report.writeInFile(getReport(size));
        for (String fileNames: targetFileList){
            report.writeInFile(fileNames);
        }
        report.finishMyJob();
    }

    /**
     * Create and returns list of files, in which contains string key
     * @param searchKey string key, which contains in file name
     * @return      list of file names with key
     */
    @Override
    public List<String> analyze(String searchKey) {
        /*List<String> fileList = new ArrayList<String>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(report.getLogFilePath())), StandardCharsets.UTF_8);
            fileList = Arrays.asList(content.split(System.lineSeparator()));
        } catch (Exception e){
            e.printStackTrace();
        }*/
        List<String> result = new ArrayList<String>();

        Stream<String> stream = targetFileList.stream();
        stream.filter((String a) -> (a.contains(searchKey))).forEach(result :: add);

        return result;
    }



    /**
     * Returns count of directories
     * @return      count of directories
     */
    public int getDirCount() {
        return  dirCount;
    }

    /**
     * Returns count of files
     * @return      count of files
     */
    public int getFilesCount() {
        return  filesCount;
    }

    /**
     * Returns list of file names
     * @return      count of files
     */
    public List<String> getFileList() {
        return  targetFileList;
    }

    /**
     * Returns list of directory names
     * @return      count of files
     */
    public List<String> getDirectoryList() {
        return  targetDirectoryList;
    }
}
