package com.project;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import javax.crypto.Cipher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller2{

    @FXML
    Button back, decrypt;

    @FXML
    TextField privateKey, file, password, destination;

    String keyDirectory = "src/main/resources/data/keys/";
    String inDirectory = "src/main/resources/data/in/";
    String outDirectory = "src/main/resources/data/out/";

    String pass = "1234";

    @FXML
    private void back(ActionEvent event) {
        UtilsViews.setView("View0");
    }

    @FXML
    private void Decrypt(ActionEvent event){
        if(pass.equals(password.getText())){
            try {
                PrivateKey privateKeyFile = loadPrivateKey(keyDirectory + privateKey.getText());
                byte[] encryptedData = Files.readAllBytes(Paths.get(outDirectory + file.getText()));
                byte[] decryptedData = decryptData(encryptedData, privateKeyFile);
                Files.write(Paths.get(outDirectory + destination.getText()), decryptedData);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static PrivateKey loadPrivateKey(String filename) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (PrivateKey) ois.readObject();
        }
    }

    public static byte[] decryptData(byte[] data, PrivateKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }
    
}