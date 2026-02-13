import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

//Created with help from Google
public class ProgramControlTest {
    private PrintStream originalOut;
    private ByteArrayOutputStream outContent;
    private final Path dataDir = Paths.get("data");
    private final Path cipherDir = Paths.get("ciphers");
    private final Path keyFile = cipherDir.resolve("key.txt");

    @BeforeEach
    void setup() throws IOException {
        // capture System.out
        originalOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // ensure key.txt exists for decipher tests (you can overwrite per-test)
        Files.writeString(keyFile, "abc\nabc\n");
    }
    @AfterEach
    void teardown() throws IOException {
        // restore System.out
        System.setOut(originalOut);
    }

    @Test
    public void Testone_No_Args() {
        TopSecret.accessFiles(new String[]{});
        String output = outContent.toString();

        // Check header printed
        assertTrue(output.contains("The following are available files"));

        // Check specific files printed
        assertTrue(output.contains("a.txt"));
        assertTrue(output.contains("b.txt"));
    }

    @Test
    public void Testtwo_One_Args_a() {
        TopSecret.accessFiles(new String[]{"00"});
        assertEquals("Reading j.txt with cipherKey of 0:\n" +
                "This is file j content\n" +
                "index at 00", outContent.toString().trim(), "File contents of a.txt should be: Reading j.txt with cipherKey of 0");
    }
    @Test
    public void Testthree_One_Args_b() {
        TopSecret.accessFiles(new String[]{"01"});
        assertEquals("Reading k.txt with cipherKey of 0:\n" +
                "this is file k content\n" +
                "index at 01", outContent.toString().trim(), "File contents of a.txt should be: Reading k.txt with cipherKey of 0:\n" +
                "this is file k content\n" +
                "index at 01");
    }
    @Test
    public void Testfour_One_Args_c() {
        TopSecret.accessFiles(new String[]{"10"});
        assertEquals("Reading g.txt with cipherKey of 0:\n" +
                "This is content of g", outContent.toString().trim(), "File contents of a.txt should be: Reading g.txt with cipherKey of 0:\n" +
                "This is content of g");
    }
    @Test
    public void Testfive_One_Args_a() {
        TopSecret.accessFiles(new String[]{"100"});
        String output = outContent.toString();
        assertTrue(output.contains("Error: Index does not match an existing file. Try a different number."));
    }
    @Test
    public void Testsix_One_Args_b() {
        TopSecret.accessFiles(new String[]{"101"});
        String output = outContent.toString();
        assertTrue(output.contains("This algorithm only takes numbers 00-100."));
    }





}
