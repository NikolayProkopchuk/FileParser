package com.util;



import java.io.*;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: Колян
 * Date: 30.05.15
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 * This class create file and consists methods to write file names in it
 * and write report for directory
 */

public class ReportWriter {
    //private RandomAccessFile writer;
    private BufferedWriter writer;
    private File targetFile;

    /**
     * Create file to write names of file and report
     */
    public ReportWriter() {
        GregorianCalendar calendar = new GregorianCalendar();
        String dateResult =  "" + calendar.getTimeInMillis();

        String path =  System.getProperty("user.dir") + File.separator;
        path = path + "fileLog-" + dateResult + ".db";
        targetFile = new File(path);
        try {
            writer = new BufferedWriter(new FileWriter(targetFile));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        /*try {
            writer = new RandomAccessFile(targetFile, "rws");
        } catch (Exception e){
            e.printStackTrace();
        }*/
    }

    /**
     * Writes file names in file
     * @param fileTree String's describing the location of directory
     */
    /*public void writeFileName(String fileTree){
        try {
            if (targetFile.length() == 0){
                fileTree = "**********************************************************"
                        +  System.lineSeparator() + "*************************" + fileTree;
            }
            /*writer.seek(targetFile.length());
            writer.writeBytes((fileTree + System.lineSeparator()));
        } catch (Exception e){
            System.out.println(fileTree + " cannot write");
        }
    }*/

    /**
     * Writes strings in file
     * @param fileTree String's describing the location of directory
     */
    public void writeInFile(String fileTree){
        try {
            writer.write(fileTree + System.lineSeparator());
        } catch (IOException e) {
            System.out.println(fileTree + " cannot write");
        }
    }

    /**
     * Writes report for directory in file
     * @param fileTree String's describing the location of directory
     */
    /*public void writeReport(String fileTree){
        try {
            writer.seek(0);
            writer.writeBytes((fileTree  + System.lineSeparator()));
        } catch (Exception e){
            System.out.println(fileTree + " cannot write");
        }
    }*/

    /**
     * Finishes the work, clothes buffer and file
     */
    public void finishMyJob(){
        try {
            writer.close();
        } catch (Exception e){
            System.out.println("cannot close buffer");
        }

    }

    /**
     * Returns path of file in which write file names and report
     */
    public String getLogFilePath(){
        return targetFile.getPath();
    }
}
