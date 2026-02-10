//did part of #3, trying to get gradle to work before I work on the final half. 

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Commmand Line Utility
 */
public class TopSecret {
    public static void accessFiles(String[] args) {         //this method attempts to access files from the data folder

        List<String> files = new ArrayList<>();      //create list of files.
        Path dataPath = Paths.get("./docs").normalize();
        //Path dataPath = Paths.get(System.getProperty("user.dir")).resolve("../../..").resolve("data").normalize();
        // System.out.println("dataPath: " + dataPath);
        //path to the files; I created a new folder under TopSecret with random files a.txt and b.txt
        //this code makes it so that anyone with a folder called data can use this method regardless of absolute or relative paths.
        int cipherKey = 0; // default key
        if (args.length == 0) { //if arguments = 0
            System.out.println("The following are available files and their indexes:");
            int counter = 0; //used for the counter next to file name
            // using try catch for stuff involving file systems.
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dataPath)) { //opens the system for files
                for (Path aFile : stream) {  //for every file in the stream
                    files.add(aFile.getFileName().toString()); //converts the filename to string and adds it into the prepared arraylist
                    //counter logistics
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
                e.printStackTrace(); //this code identifies why the error happened
            }
            return;
        } else if(args.length == 2) {
            cipherKey = Integer.parseInt(args[1]);
        }
        // Insert code for handling file opening
        System.out.println("Reading " + " placeholderName "/*replace with filename*/ + " with cipherKey of " + cipherKey +":");

        // Shift file contents (if cipherKey == 0) return the same string

        // Print shifted contents
        System.out.println("placeholder" /*replace with shifted file contents*/);
    }

    public String returnFileContents(String filename) {
        String fileContents = "";
        File currentFile = new File("data/"+filename); //files are located a folder called 'data'
        try(Scanner scanner = new Scanner(currentFile)) {
            while (scanner.hasNextLine()) { //reads the file contents line by line
                String line = scanner.nextLine();
                fileContents += (line + "\n"); //concatenates the file contents to a string
            }
            return fileContents;
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred while trying to access " + filename + ".");
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        accessFiles(args);
    }
}