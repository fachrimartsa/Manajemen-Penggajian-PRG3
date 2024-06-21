package com.example.manajemen_penggajian;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

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
        if (tbUsername.getText().equals("Admin") && tbPassword.getText().equals("1234")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Login Berhasil");
            alert.setTitle("Login Berhasil");
            alert.showAndWait();

            // Close the current panel or stage
            Stage stage = (Stage) mainPanel.getScene().getWindow();
            stage.close();

            // Load and show the dashboard
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FormDashboard.fxml"));
                AnchorPane dashboardPane = fxmlLoader.load();
                Stage dashboardStage = new Stage();
                dashboardStage.setTitle("Dashboard");
                dashboardStage.setScene(new Scene(dashboardPane));
                dashboardStage.setFullScreen(true);
                dashboardStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Username atau Password salah");
            alert.setTitle("Login Error");
            alert.showAndWait();
        }
    }
}
