import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CipherTest {
    @Test
    public void WordTest(){
        String contents = "eph";
        Cipher cipher = new Cipher("key.txt");
        contents = cipher.decipher(contents);
        assertEquals("dog", contents);
    }

    @Test
    public void SentenceTest(){
        String contents = "ifmmp xpsme";
        Cipher cipher = new Cipher("key.txt");
        contents = cipher.decipher(contents);
        assertEquals("hello world", contents);
    }

    @Test
    public void PunctuationTest(){
        String contents = "ifmmp, xpsme!";
        Cipher cipher = new Cipher("key.txt");
        contents = cipher.decipher(contents);
        assertEquals("hello, world!", contents);
    }

    @Test
    public void CapitalizationTest(){
        String contents = "Eph";
        Cipher cipher = new Cipher("key.txt");
        contents = cipher.decipher(contents);
        assertEquals("Dog", contents);
    }

    @Test
    public void EmptyInputTest(){
        String contents = "";
        Cipher cipher = new Cipher("key.txt");
        contents = cipher.decipher(contents);
        assertEquals("", contents);
    }

    @Test
    public void CustomKeyFileTest(){
        String contents = "jgnnq";
        Cipher cipher = new Cipher("key2test.txt");
        contents = cipher.decipher(contents);
        assertEquals("hello", contents);
    }
}
