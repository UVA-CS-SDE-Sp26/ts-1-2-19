// src/test/java/UITest.java
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class UITest {
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;
    private Scanner sc;

    @BeforeEach
    public void setupOutputCapture() {
        originalOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreOutput() {
        System.setOut(originalOut);
        if (sc != null) sc.close();
    }

    @Test
    public void noArgTest() {
        String[] args = new String[0];
        TopSecret.main(args);
        System.out.flush();

        sc = new Scanner(outContent.toString());
        assertEquals("The following are available files and their indexes:", sc.nextLine());

        int i = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (i < 10) assertEquals("0" + i, line.substring(0, 2));
            else assertEquals(i + "", line.substring(0, 2));
            i++;
        }
    }

    @Test
    public void oneArgTest() {
        String[] args = new String[1];
        args[0] = "1";
        TopSecret.main(args);
        System.out.flush();

        sc = new Scanner(outContent.toString());
    }
    @Test
    public void twoArgTest() {

    }
}
