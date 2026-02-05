//did part of #3, trying to get gradle to work before I work on the final half. 

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Commmand Line Utility
 */
public class TopSecret {
    public static void accessFiles(String[] args) {         //this method attempts to access files from the data folder

        List<String> files = new ArrayList<>();      //create list of files.
        Path dataPath = Paths.get(System.getProperty("user.dir")).resolve("../../..").resolve("data").normalize();
        //path to the files; I created a new folder under TopSecret with random files a.txt and b.txt

        if (args.length == 0) { //if arguments = 0
            int counter = 0;
            // using try catch for stuff involving file systems.
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dataPath)) { //opens the system for files
                for (Path aFile : stream) {  //for every file in the stream
                    files.add(aFile.getFileName().toString()); //converts the filename to string and adds it into the prepared arraylist
                    if (counter < 10) {
                        System.out.println("0" + counter + " " + aFile.getFileName());
                        counter++;
                    } else {
                        System.out.println(counter + " " + aFile.getFileName());
                        counter++;
                    }

                }
                //if the system has issues finding the file or folder:
            } catch (IOException e) { //stream is automatically closed by now.
                System.out.println("Some error in accessing files.");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        accessFiles(args);
    }
}