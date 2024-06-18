package com.example.manajemen_penggajian;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class FormLogin {
    @FXML
    ImageView templateLogin;
    @FXML
    AnchorPane mainPanel;
    @FXML
    TextField tbUsername;
    @FXML
    TextField tbPassword;
    @FXML
    TextField tbCaptcha;
    @FXML
    TextField tbKodeCaptcha;
    @FXML
    Button btnLogin;

    public void onbtnLoginClick() {
        if (tbUsername.getText() == "Admin" && tbPassword.getText() == "1234") {
            Alert alert = new Alert(null);
            alert.setContentText("Login Berhasil");
            alert.show();
        } else {
            Alert alert = new Alert(null);
            alert.setContentText("Username Atau Password Salah");
            alert.show();
        }
    }
}
