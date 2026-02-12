import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileHandler {
    private String fileName;
    private String fileContents;

    public FileHandler(String fn) {
        fileName = fn;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContents() {
        return fileContents;
    }

    public String getFileName() {
        return fileName;
    }

    public String returnFileContents() {
        String fc = "";
        File currentFile = new File("data/"+fileName); //files are located a folder called 'data'
        try(Scanner scanner = new Scanner(currentFile)) {
            while (scanner.hasNextLine()) { //reads the file contents line by line
                String line = scanner.nextLine();
                fc += (line + "\n"); //concatenates the file contents to a string
            }
            fileContents = fc;
            return fc;
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred while trying to access " + fileName + ".");
            e.printStackTrace();
        }
        fileContents = "";
        return "";
    }
}
