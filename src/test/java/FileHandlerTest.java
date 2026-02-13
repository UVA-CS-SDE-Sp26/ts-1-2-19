import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {
    @Test
    public void testOne() {
        FileHandler fh = new FileHandler("a.txt");
        assertEquals("bdkjhevhdvwejhsjedbw", fh.returnFileContents(), "File contents of a.txt should be:\nbdkjhevhdvwejhsjedbw");
    }

    @Test
    public void testTwo() {
        FileHandler fh = new FileHandler("b.txt");
        assertEquals("ejwbdbdd", fh.returnFileContents(), "File contents of b.txt should be:\nnejwbdbdd");
    }

    @Test
    public void testThree() {
        FileHandler fh = new FileHandler("z.txt"); //try to retrieve content from a file that does not exist
        assertEquals("", fh.returnFileContents(), "File contents of z.txt should be empty.");
    }
}
