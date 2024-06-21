package com.example.manajemen_penggajian;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Loading {
    @FXML
    private ImageView car;
    @FXML
    private ImageView car1;

    @FXML
    private void initialize() {
        // Panggil oncarClick saat inisialisasi, misalnya untuk keperluan demonstrasi
        onanothercarClick();
        oncarClick();
    }

    public void oncarClick() {
        // Create a new TranslateTransition
        TranslateTransition transition = new TranslateTransition();

        // Set the duration of the transition
        transition.setDuration(Duration.seconds(6));

        // Set the node to be translated
        transition.setNode(car);

        // Set the start and end points of the transition
        transition.setFromX(10);
        transition.setToX(1580 - car.getFitWidth()); // 1580 is the scene width, adjust as necessary

        // Set the cycle count
        transition.setCycleCount(1); // Change to 1 because we only want to go once

        // Event handler to switch to formDashboard after animation finishes
        transition.setOnFinished(event -> {
            try {
                switchToFormLogin();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Play the transition
        transition.play();
    }

    public void onanothercarClick() {
        // Create a new TranslateTransition
        TranslateTransition transition = new TranslateTransition();

        // Set the duration of the transition
        transition.setDuration(Duration.seconds(6));

        // Set the node to be translated
        transition.setNode(car1);

        // Set the start and end points of the transition
        transition.setFromX(1580);
        transition.setToX(0); // 1580 is the scene width, adjust as necessary

        // Set the cycle count
        transition.setCycleCount(1); // Change to 1 because we only want to go once
        transition.play();
    }

    private void switchToFormLogin() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FormLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
}
