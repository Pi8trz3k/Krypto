import org.example.DES;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.example.DES.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class DESTest {
    DES des = new DES("C5475BAFE74A3C1B");

//    @Test
//    void buildingSubKeysTest() {
//        String key = "0001001100110100010101110111100110011011101111001101111111110001";
//        String[] subKeys = new String[] {
//                "000110110000001011101111111111000111000001110010",
//                "011110011010111011011001110110111100100111100101",
//                "010101011111110010001010010000101100111110011001",
//                "011100101010110111010110110110110011010100011101",
//                "011111001110110000000111111010110101001110101000",
//                "011000111010010100111110010100000111101100101111",
//                "111011001000010010110111111101100001100010111100",
//                "111101111000101000111010110000010011101111111011",
//                "111000001101101111101011111011011110011110000001",
//                "101100011111001101000111101110100100011001001111",
//                "001000010101111111010011110111101101001110000110",
//                "011101010111000111110101100101000110011111101001",
//                "100101111100010111010001111110101011101001000001",
//                "010111110100001110110111111100101110011100111010",
//                "101111111001000110001101001111010011111100001010",
//                "110010110011110110001011000011100001011111110101"
//        };
//        des.buildSubKeys(key);
//        for(int i = 0; i < 16; i++) {
//            assertEquals(subKeys[i], des.getSubKey(i));
//        }
//    }

//
    @Test
    void test() throws IOException {
        // szyfrowanie z pliku do stringa
        File file = new File("C:\\Users\\Kacper\\Documents\\Studia\\4sem\\Krypto\\messageDES.txt");
        byte[] fileBytes = Files.readAllBytes(Path.of(file.toURI()));
        String data = decToBin(fileBytes);
        String encrypted = binToHex(des.encrypt(data));
        System.out.println("Encrypted string: " + encrypted);

        // deszyfrowanie ze stringa do stringa
        String ciphered = "0697d8f329774573";
        String hexToBin = hexToBin(ciphered);
        String uncipheredInBin = des.decrypt(hexToBin, "1");
        String uncipheredInHex = binToHex(uncipheredInBin);
        byte[] decryptedBytes = hexToBytes(uncipheredInHex);
        String jojojojo = new String(decryptedBytes, StandardCharsets.UTF_8);
        System.out.println("Test: " + jojojojo);
//
//        // zapis do zaszyfrowanego tekstu do pliku
//        byte[] encryptedBytes = hexToBytes(encrypted);
//        File fileTest = new File("C:\\Users\\Kacper\\Documents\\Studia\\4sem\\Krypto\\messageDESciphereddd");
//        try (FileOutputStream fout = new FileOutputStream(fileTest)) {
//            fout.write(encryptedBytes);
//        }
//
//        // deszyfrowanie z pliku
//        File fileEncrypted = new File("C:\\Users\\Kacper\\Documents\\Studia\\4sem\\Krypto\\messageDESciphered");
//        byte[] cipheredBytes = Files.readAllBytes(Path.of(fileEncrypted.toURI()));
//        String cipheredFileInBin = decToBin(cipheredBytes);
//        String decipheredFileInBin = des.decrypt(cipheredFileInBin, "1");
//        byte[] decipheredBytes = hexToBytes(binToHex(decipheredFileInBin));
//        String trololo = new String(decipheredBytes, StandardCharsets.UTF_8);
//        System.out.println("CHUJ: " + trololo);

//        byte[] fileToDecrpyt = encrypted.getBytes();
//        String toDecryptBin = decToBin(fileToDecrpyt);
//        String decryptedInHex = binToHex(des.decrypt(toDecryptBin, "1"));
//        byte[] bytesbytes = decryptedInHex.getBytes();
//        String message = new String(bytesbytes, StandardCharsets.UTF_8);
//        System.out.println("Decrpted string: " + message);

        // encrypted file
//        File encryptedFile = new File("C:\\Users\\Kacper\\Documents\\Studia\\4sem\\Krypto\\testEncryptedFile");
////        byte[] fooBytes
//        Files.write(encryptedFile.toPath(), fileToDecrpyt);
//
//        // decrypted file
//        File fileToDecrypt = new File("C:\\Users\\Kacper\\Documents\\Studia\\4sem\\Krypto\\testEncryptedFile");
//        byte[] byteToDecrypt = Files.readAllBytes(Path.of(fileToDecrypt.toURI()));
//        String decryptedDataInBin = decToBin(byteToDecrypt);
//        String encryptedFileString = des.decrypt(decryptedDataInBin, "1");
//
//        String encryptedInHex = binToHex(encryptedFileString);
//        byte[] encryptedBytes = hexToBytes(encryptedInHex);
//        String foooo = new String(encryptedBytes, StandardCharsets.UTF_8);
//        System.out.println("Odszyfrowane z pliku: " + foooo);

//        System.out.println("Encrypted in bin: " + DecryptBin);
//        System.out.println("DECRYPTION");
//        System.out.println("Decrypted: " + decrypted);
//        String decryptedInHex = binToHex(decrypted);
//        byte [] decryptedBytes = hexToBytes(decryptedInHex);
//        String foooo = new String(decryptedBytes, StandardCharsets.UTF_8);
//        System.out.println("Decrypted in ascii: " + foooo);

//        StringBuilder newString = new StringBuilder();
//        for (int i = 0; i < decrypted.length() - 8; i += 8) {
//            StringBuilder info = new StringBuilder(decrypted.substring(i, i + 8));
//            int charCode = Integer.parseInt(info.toString(), 2);
//            String str = Character.toString((char) charCode);
//            newString.insert(0, str);
//        }
//        System.out.println("New string: " + newString);
//        String s = new String(decrypted.getBytes(), StandardCharsets.UTF_8);
//        System.out.println("S: " + s);
//        String hexMessage = binToHex(decrypted);
//        byte[] messageByte = hexMessage.getBytes(StandardCharsets.UTF_8);
//        String s = new String(fileToDecrpyt);
//        String message = new String(binToHex(decrypted).getBytes(), StandardCharsets.UTF_8);
//        System.out.println("Message: " + s);

    }
}
