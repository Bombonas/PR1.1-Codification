package com.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller0 {

    @FXML
    private Button encrypt, decrypt;

    public void initialize() {
        encrypt.setOnAction(this::Encrypt);
        decrypt.setOnAction(this::Decrypt);
    }

    @FXML
    private void Encrypt(ActionEvent event) {
        UtilsViews.setView("View1");
    }
    
    @FXML
    private void Decrypt(ActionEvent event) {
        UtilsViews.setView("View2");
    }


}