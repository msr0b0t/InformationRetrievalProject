/*
 * Created by Mary
 */

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        //modify path to get the negs and the test files
        File pos = new File("/home/mary/Desktop/data/test");
        File[] files = pos.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                try {
                    //content's value is the string of the file read
                    String content = FileUtils.readFileToString(file, "UTF-8");
                    //creating object of PreparingData to convert content
                    PreparingData pd = new PreparingData(content);
                    //newContent's value is the converted string
                    List<String> newContent = pd.getFinalText();
                    StringBuilder strarray = new StringBuilder();
                    for (String s : newContent){
                        strarray.append(s).append(" ");
                    }
                    //delete all content from file
                    PrintWriter writer = new PrintWriter(file);
                    writer.print("");
                    writer.close();
                    //write newContent on file
                    FileUtils.writeStringToFile(file, strarray.toString(), "UTF-8");
                }catch(IOException e){
                    System.out.println("Could not read file");
                }
            }
        }

        long endTime = System.currentTimeMillis();
        long totalTime = (endTime - startTime) /1000;
        System.out.println(totalTime);
    }
}
