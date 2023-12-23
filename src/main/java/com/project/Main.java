package com.project;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        final int windowWidth = 800;
        final int windowHeight = 600;

        UtilsViews.parentContainer.setStyle("-fx-font: 14 arial;");
        UtilsViews.addView(getClass(), "View0", "/assets/view0.fxml");
        UtilsViews.addView(getClass(), "View1", "/assets/view1.fxml");
        UtilsViews.addView(getClass(), "View2", "/assets/view2.fxml");

        Scene scene = new Scene(UtilsViews.parentContainer);
        
        stage.setScene(scene);
        stage.setTitle("Encrypt");
        stage.setMinWidth(windowWidth);
        stage.setMinHeight(windowHeight);
        stage.show();

        Security.addProvider(new BouncyCastleProvider());

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
        keyGen.initialize(2048, new SecureRandom());
        KeyPair keyPair = keyGen.generateKeyPair();

        try (ObjectOutputStream publicKeyOS = new ObjectOutputStream(new FileOutputStream("src/main/resources/data/keys/publicKey.key"))) {
            publicKeyOS.writeObject(keyPair.getPublic());
        }

        try (ObjectOutputStream privateKeyOS = new ObjectOutputStream(new FileOutputStream("src/main/resources/data/keys/privateKey.key"))) {
            privateKeyOS.writeObject(keyPair.getPrivate());
        }

        // Add icon only if not Mac
        if (!System.getProperty("os.name").contains("Mac")) {
            Image icon = new Image("file:/icons/icon.png");
            stage.getIcons().add(icon);
        }
        
    }
}