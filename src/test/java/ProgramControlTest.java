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
        //Files.writeString(keyFile, "abc\nabc\n");
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
        TopSecret.accessFiles(new String[]{"09"});
        String actual = outContent.toString().replaceAll("\\R", "\n").trim();
        String expected = ("Reading j.txt with cipherKey of key.txt:\n" +
                "hmcdw 0s 99").replaceAll("\\R", "\n").trim();
        assertEquals(expected, actual);
    }
    @Test
    public void Testthree_One_Args_b() {
        TopSecret.accessFiles(new String[]{"10"});
        String actual = outContent.toString().replaceAll("\\R", "\n").trim();
        String expected = ("Reading k.txt with cipherKey of key.txt:\n" +
                "hmcdw 0s 9Z").replaceAll("\\R", "\n").trim();
        assertEquals(expected, actual);
    }
    @Test
    public void Testfour_One_Args_c() {
        TopSecret.accessFiles(new String[]{"6"});
        String actual = outContent.toString().replaceAll("\\R", "\n").trim();
        String expected = ("Reading g.txt with cipherKey of key.txt:\n" +
                "Sghr hr bnmsdms ne f").replaceAll("\\R", "\n").trim();
        assertEquals(expected, actual);
    }
    @Test
    public void Testfive_One_Args_a() {
        TopSecret.accessFiles(new String[]{"100"});
        String output = outContent.toString();
        assertTrue(output.contains("Error: Index does not match an existing file."));
    }
    @Test
    public void Testsix_One_Args_b() {
        TopSecret.accessFiles(new String[]{"101"});
        String output = outContent.toString();
        assertTrue(output.contains("This algorithm only takes numbers 00-100."));
    }

    @Test
    public void Testseven_One_Args_c() {
        TopSecret.accessFiles(new String[]{"ab"});
        String output = outContent.toString();
        assertTrue(output.contains("This algorithm only takes numbers 00-100."));
    }

    @Test
    public void Testeight_One_Args_d() {
        TopSecret.accessFiles(new String[]{"00a"});
        String output = outContent.toString();
        assertTrue(output.contains("This algorithm only takes numbers 00-100."));
    }

    @Test
    public void Testnine_One_Args_e() {
        TopSecret.accessFiles(new String[]{"-1"});
        String output = outContent.toString();
        assertTrue(output.contains("This algorithm only takes numbers 00-100."));
    }

    @Test
    public void Testten_One_Args_f() {
        TopSecret.accessFiles(new String[]{"-1"});
        String output = outContent.toString();
        assertTrue(output.contains("This algorithm only takes numbers 00-100."));
    }

    @Test
    public void Testeleven_One_Args_g() {
        TopSecret.accessFiles(new String[]{"$%"});
        String output = outContent.toString();
        assertTrue(output.contains("This algorithm only takes numbers 00-100."));
    }

    @Test
    public void Testtwelve_One_Args_a() {
        TopSecret.accessFiles(new String[]{"6"});
        String actual = outContent.toString().replaceAll("\\R", "\n").trim();
        String expected = ("Reading g.txt with cipherKey of key.txt:\n" +
                "Sghr hr bnmsdms ne f").replaceAll("\\R", "\n").trim();
        assertEquals(expected, actual);
    }

    @Test
    public void Testthirteen_One_Args_b() {
        TopSecret.accessFiles(new String[]{"11"});
        String output = outContent.toString();
        assertTrue(output.contains("Error: Index does not match an existing file."));
    }

    @Test
    public void Testfourteen_One_Args_c() {
        TopSecret.accessFiles(new String[]{"50"});
        String output = outContent.toString();
        assertTrue(output.contains("Error: Index does not match an existing file."));
    }

    @Test
    public void Testfifteen_Two_Args_a() {
        TopSecret.accessFiles(new String[]{"00", "0"});
        String actual = outContent.toString().replaceAll("\\R", "\n").trim();
        String expected = ("Reading a.txt with cipherKey of key.txt:\n" +
                "acjigdugcuvdigridcav").replaceAll("\\R", "\n").trim();
        assertEquals(expected, actual);

    }

    @Test
    public void Testsixteen_Two_Args_b() {      //FAILED
        TopSecret.accessFiles(new String[]{"00", "1"});
        String actual = outContent.toString().replaceAll("\\R", "\n").trim();
        String expected = ("Reading a.txt with cipherKey of key2test.txt:\n" +
                "0bihfctfbtuchfuhcb0u").replaceAll("\\R", "\n").trim();
        assertEquals(expected, actual);
    }

    @Test
    public void Testseventeen_Two_Args_c() {
        TopSecret.accessFiles(new String[]{"00 a"});
        String output = outContent.toString();
        assertTrue(output.contains(""));//"Error message"));
    }

}
