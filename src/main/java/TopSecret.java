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

    //this method accesses files from the data folder, depending on number of arguments given.
    public static void accessFiles(String[] args) {
        // Makes sure only two arguments are passed at most
        if (args.length > 2) {
            throw new IllegalArgumentException("Cannot have more than two arguments");
        }
        List<String> files = new ArrayList<>();      //create list of files.
        List<String> cipherKeys = new ArrayList<>();
        Path dataPath = Paths.get("./data").normalize();
        Path keyPath = Paths.get("./ciphers").normalize();
        //path to the files; I created a new folder under TopSecret with random files a.txt and b.txt
        //this code makes it so that anyone with a folder called data can use this method regardless of absolute or relative paths.

        String cipherKey = "key.txt"; // default key


        // using try catch for stuff involving file systems.
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dataPath)) { //opens the system for files
            for (Path aFile : stream) {  //for every file in the stream
                files.add(aFile.getFileName().toString()); //converts the filename to string and adds it into the prepared arraylist
            }
            //if the system has issues finding the file or folder:
        } catch (IOException e) { //stream is automatically closed by now.
            System.out.println("Some error in accessing files.");
            e.printStackTrace(); //this code identifies why the error happened
        }
        // Use try catch to catch issues with getting all the keys
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(keyPath)) {
            for (Path aFile : stream) {
                // Add cipher key names to a list to access depending on the index passed as an arg
                cipherKeys.add(aFile.getFileName().toString());
            }
        } catch (IOException e) {
            System.out.println("Some error in accessing files.");
        }

        if (args.length == 0) { //if arguments = 0
            System.out.println("The following are available files and their indexes:");
            int counter = 0; //used for the counter next to file name
            for (int i = 0; i < files.size(); i++) {
                if (counter < 10) {
                    System.out.println("0" + counter + " " + files.get(i));
                    counter++;
                } else {
                    System.out.println(counter + " " + files.get(i));
                    counter++;
                }
            }
            return;
        }
        Cipher cipher;
        // Gets cipherKey if arg length == 2
        if (args.length == 2) {
            try {
                int cipherKeyInd = Integer.parseInt(args[1]);
                cipherKey =  cipherKeys.get(cipherKeyInd);
            } catch (NumberFormatException e) {
                System.out.println("The second argument must be an integer.");
                return;
            }
        }
        // Create cipher with either the default key, or if second arg, set to new key
        cipher = new Cipher(cipherKey);
        // Get the filename based on the index passed through arg 1
        int index = Integer.parseInt(args[0]);
        String fileName;
        try {
            fileName = files.get(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: Index does not match an existing file.");
            return;
        }
        // Read the file, and pass the contents through the cipher
        FileHandler handler = new FileHandler(fileName);
        String contents = handler.returnFileContents();
        System.out.println("Reading " + fileName + " with cipherKey of " + cipherKey +":");
        System.out.println(cipher.decipher(contents));

    }

    public static void main(String[] args) {
        accessFiles(args);
    }
}