TopSecret documentation starter file

This program is divided into four different parts:

User Interface:
- Should cause different outputs depending on the arguments run

Reading the Data
- Create a class that, given a file, reads the entire file and stores it
- The file contents should be easily accessed in some sort of getter method

Connecting Command Line with Data Reading
- When arguments are passed into the main method, create a FileReader and Cipher object.
- Pass the specified file into the FileReader, and shift the outputted text using the Cipher

Cipher
- Create a Cipher class that when given a string and key, shifts each character by the key
- Right CaesarCipher is assumed