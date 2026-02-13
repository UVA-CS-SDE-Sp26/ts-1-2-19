//did part of #3, trying to get gradle to work before I work on the final half. 

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
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
        List<String> files = new ArrayList<>();      //create list of files.
        Path dataPath = Paths.get("./data").normalize();
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
        }
        ////////
        else if (args.length == 1) {
            if (!args[0].matches("0[0-9]|[1-9][0-9]|100")) {
                System.out.println("This algorithm only takes numbers 00-100.");
                return;
            }
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dataPath)) { //copy paste from above
                for (Path aFile : stream) {  //for every file in the stream
                    files.add(aFile.getFileName().toString());
                }
            } catch (IOException e) {
                System.out.println("Some error in accessing files.");
                return;
            }
            //At some point, the folders went out of alphabetical order, for ease of testing, I am sorting them now.
            files.sort(String::compareTo);
            //assigns an int to each file in the folder based on the argument in position one, which is the number
            int index = Integer.parseInt(args[0]);
            try {
                String fileName = files.get(index);
                FileHandler handler = new FileHandler(fileName);
                String contents = handler.returnFileContents();
                System.out.println("Reading " + fileName + " with cipherKey of " + cipherKey +":");
                System.out.println(contents);
                return;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error: Index does not match an existing file. Try a different number.");
                return;
            }
        }
        ////////
        else if(args.length == 2) {
            int index;
            cipherKey = Integer.parseInt(args[1]);
            try {
                index = Integer.parseInt(args[0]);
                cipherKey = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("The second argument must be an integer.");
                return;
            }
            //load files
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dataPath)) { //copy paste from above
                for (Path aFile : stream) {  //for every file in the stream
                    files.add(aFile.getFileName().toString());
                }
            } catch (IOException e) {
                System.out.println("Some error in accessing files.");
                return;
            }
            String fileName = files.get(index);
            FileHandler handler = new FileHandler(fileName);
            String contents = handler.returnFileContents();

            //Insert code for handling file opening
            System.out.println("Reading " + fileName + " with cipherKey of " + cipherKey +":");
            // Shift file contents (if cipherKey == 0) return the same string
            if (cipherKey != 0) {
                TopSecret ts = new TopSecret();
                contents = ts.decipher(contents);
            }
            // Print shifted contents
            System.out.println(contents /*replace with shifted file contents*/);
        }

        }



    public String decipher(String text) {
        File keyfile = new File("ciphers/key.txt");
        String actualLine = "";
        String cipherLine = "";
        try (Scanner scanner = new Scanner(keyfile)) {
            actualLine = scanner.nextLine();
            if (!scanner.hasNextLine()) {
                throw new IllegalArgumentException("Cipher key missing second line.");
            }
            cipherLine = scanner.nextLine(); //the shifted alphabet

            if (actualLine.length() != cipherLine.length()) {
                throw new IllegalArgumentException("Cipher key must be same length.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error happened while trying to access key.txt");
        }

        // Shift file contents (if cipherKey == 0) return the same string
        HashMap<Character, Character> cipher_map = new HashMap<>();

        for (int i = 0; i < actualLine.length(); i++) {
            cipher_map.put(cipherLine.charAt(i),actualLine.charAt(i)); //Key-value pairs between ciphered and deciphered characters
        }
        String result = "";
        for (int j = 0; j < text.length(); j++) {
            char letter = text.charAt(j);
            char lowercase = Character.toLowerCase(letter);

            if (cipher_map.containsKey(lowercase)) {
                char deciphered = cipher_map.get(lowercase);

                if (Character.isUpperCase(letter)){
                    result += Character.toUpperCase(deciphered);
                } else {
                    result += deciphered;
                }
            } else {
                result += letter;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        accessFiles(args);
    }
}