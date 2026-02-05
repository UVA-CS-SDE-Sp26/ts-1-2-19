import org.junit.jupiter.api.*;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class UITest {
    Scanner sc;

    @BeforeEach
    public void setupOutputReading() {
        sc = new Scanner(System.in);
    }

    @Test
    public void noArgTest() {
        String[] args = new String[0];
        TopSecret.main(args);
        assertEquals("The following are available files and their indexes:", sc.nextLine());

        int i = 0;
        while (sc.hasNextLine()) {
            if (i < 10) assertEquals("0" + i, sc.nextLine().substring(0, 2));
            else assertEquals (i + "", sc.nextLine().substring(0, 2));
            i++;
        }


    }
}
