import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Cipher {
    private File keyfile;
    public Cipher() {
        keyfile = new File("ciphers/key.txt");
    }

    public Cipher(String key) {
        keyfile = new File("ciphers/" + key);
    }
    public String decipher(String text) {
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

        // Shift file contents
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
}
