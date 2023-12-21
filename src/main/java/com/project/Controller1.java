package com.project;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.ResourceBundle;

import javax.crypto.Cipher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Controller1 {

    @FXML
    Button back, encrypt;

    @FXML
    TextField publicKey, file, destination;

    String keyDirectory = "src/main/resources/data/keys/";
    String inDirectory = "src/main/resources/data/in/";
    String outDirectory = "src/main/resources/data/out/";

    @FXML
    private void back(ActionEvent event) {
        UtilsViews.setView("View0");
    }

    @FXML
    private void Encrypt(ActionEvent event){
        if(publicKey.getText() != "" && file.getText() != "" && destination.getText() != ""){
            try {
                PublicKey publicKeyFile = loadPublicKey(keyDirectory + publicKey.getText());
                String document = new String(Files.readAllBytes(Paths.get(inDirectory + file.getText())));
                byte[] encryptedData = encryptData(document, publicKeyFile);
                Files.write(Paths.get(outDirectory + destination.getText()), encryptedData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static PublicKey loadPublicKey(String filename) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (PublicKey) ois.readObject();
        }
    }

    public static byte[] encryptData(String data, PublicKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data.getBytes());
    }
}