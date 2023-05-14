package com.example.des;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class HelloController {

    @FXML
    private TextField keyValue;

    @FXML
    private TextField keyPath;

    @FXML
    private TextField pathToSaveKey;

    @FXML
    private TextField pathUncipheredFile;

    @FXML
    private TextField pathCipheredFile;

    @FXML
    private TextField pathToWriteUnciphered;

    @FXML
    private TextField pathToWriteCiphered;

    @FXML
    private TextArea uncipheredText;

    @FXML
    private TextArea cipheredText;

    private byte[] textNonCipheredInBytes;

    private byte[] textCipheredInBytes;

    private String keyInHex;
    private String keyInBin;

    public void generateKey(ActionEvent event) {
        keyInHex = "C5475BAFE74A3C1B";
        keyInBin = DES.hexToBin(keyInHex);
        keyValue.setText(keyInHex);
    }

    public void loadKey(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedKeyFile = fileChooser.showOpenDialog(HelloApplication.getCurrentStage());
        keyPath.setText(selectedKeyFile.getAbsolutePath());
        try {
            byte[] keyBytes = Files.readAllBytes(Path.of(selectedKeyFile.toURI()));
            keyInBin = bytesToBinary(keyBytes);
            keyInHex = DES.binToHex(keyInBin);
            keyValue.setText(keyInHex);

        } catch(IOException e) {
            System.out.println("Nie udalo otworzyc sie pliku z kluczem");
        }

    }

    public void saveKey(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File saveKey = fileChooser.showSaveDialog(HelloApplication.getCurrentStage());

        byte[] keyBytes = binaryToBytes(DES.hexToBin(keyValue.getText()));

        try (FileOutputStream fou = new FileOutputStream(saveKey)) {
            fou.write(keyBytes);
        }

        pathToSaveKey.setText(saveKey.getAbsolutePath());
    }

    public static String bytesToBinary(byte[] bytes) {
        BigInteger bigInt = new BigInteger(1, bytes);
        String binary = bigInt.toString(2);
        int padding = (bytes.length * 8) - binary.length();

        if (padding > 0) {
            String zeros = new String(new char[padding]).replace("\0", "0");
            binary = zeros + binary;
        }

        return binary;
    }

    public static byte[] binaryToBytes(String binary) {
        BigInteger bigInt = new BigInteger(binary, 2);
        byte[] bytes = bigInt.toByteArray();

        if (bytes.length > 1 && bytes[0] == 0) {
            byte[] tmp = new byte[bytes.length - 1];
            System.arraycopy(bytes, 1, tmp, 0, tmp.length);
            bytes = tmp;
        }

        return bytes;
    }

    public void loadUncipheredFile(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        File uncipheredFile = fileChooser.showOpenDialog(HelloApplication.getCurrentStage());
        byte[] table = new byte[(int)uncipheredFile.length()];

        try {
            try (FileInputStream fin = new FileInputStream(uncipheredFile)) {
                fin.read(table);
                textNonCipheredInBytes = table;
                String text = new String(table, StandardCharsets.UTF_8);
                uncipheredText.setText(text);
            }
        } catch (Exception e) {
            System.out.println("Operacja nie powiodła się");
        }

        pathUncipheredFile.setText(uncipheredFile.getAbsolutePath());
    }

    public void cipherFromText(ActionEvent event) {
        String key = keyValue.getText();
        String text = uncipheredText.getText();
        String textInBin = DES.decToBin(text.getBytes());
        DES des = new DES(key);
        String encryptedBin = des.encrypt(textInBin);
        String encryptedHex = DES.binToHex(encryptedBin);
        cipheredText.setText(encryptedHex);
    }

    public void cipherFromFile(ActionEvent event) throws IOException {
        String keyHex = keyValue.getText();
        byte[] bytes = textNonCipheredInBytes;

        String textInBin = DES.decToBin(bytes);
        DES des = new DES(keyHex);
        String encryptedBin = des.encrypt(textInBin);
        String encryptedHex = DES.binToHex(encryptedBin);
        cipheredText.setText(encryptedHex);
        textCipheredInBytes = binaryToBytes(encryptedBin);
    }

    public void loadCipheredFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File cipheredFile = fileChooser.showOpenDialog(HelloApplication.getCurrentStage());

        try {
            byte[] cipheredBytes;
            try (FileInputStream fin = new FileInputStream(cipheredFile)) {
                byte[] table = new byte[(int)cipheredFile.length()];
                fin.read(table);
                cipheredBytes = table;
                String cipheredBin = DES.decToBin(cipheredBytes);
                System.out.println("CipheredBin: " + cipheredBin);
                String cipheredHex = DES.binToHex(cipheredBin);
                System.out.println("CipheredHex: " + cipheredHex);
                cipheredText.setText(cipheredHex);
            }
        } catch (Exception e) {
            System.out.println("Operacja nie powiodła się");
        }

        pathCipheredFile.setText(cipheredFile.getAbsolutePath());
    }

    public void saveUncipherToFile(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        File textWriteFile = fileChooser.showSaveDialog(HelloApplication.getCurrentStage());

        byte[] bytes = textNonCipheredInBytes;

        try(FileOutputStream out = new FileOutputStream(textWriteFile)) {
            out.write(bytes);
        } catch (IOException e) {
                throw new RuntimeException(e);
        }
        pathToWriteUnciphered.setText(textWriteFile.getAbsolutePath());
    }

    public void saveCipherToFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File textWriteFile = fileChooser.showSaveDialog(HelloApplication.getCurrentStage());

        byte[] bytes = textCipheredInBytes;

        if(textWriteFile != null) {
            try(FileOutputStream out = new FileOutputStream(textWriteFile)) {
                out.write(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            pathToWriteCiphered.setText(textWriteFile.getAbsolutePath());
        }
    }

    public void decipher(ActionEvent event) {
        String cipheredInHex = cipheredText.getText();
        String cipheredInBin = DES.hexToBin(cipheredInHex);
        String key = keyValue.getText();
        DES des = new DES(key);

        String decipheredInBin = des.decrypt(cipheredInBin);
        String decipheredInHex = DES.binToHex(decipheredInBin);
        byte[] decryptedBytes = DES.hexToBytes(decipheredInHex);
        String decipheredText = new String(decryptedBytes, StandardCharsets.UTF_8);
        uncipheredText.setText(decipheredText);
        textNonCipheredInBytes = decryptedBytes;
    }
}