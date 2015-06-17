import com.servise.impl.FileServiseImpl;
import com.util.ReportWriter;

import java.io.*;
import java.nio.file.Files;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Main class application contains method main
 *
 */

public class Application {


    public static FileServiseImpl fileServise = new FileServiseImpl();

    public static void main(String[] args) throws IOException {
        String dirName = args[0];
        String searchKey = args[1];
        fileServise.scan(dirName);
        List<String> result = fileServise.analyze(searchKey);
        for (String s: result){
            System.out.println(s);
        }
    }
}
