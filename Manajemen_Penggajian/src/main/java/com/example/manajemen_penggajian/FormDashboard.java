package com.example.manajemen_penggajian;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class FormDashboard {
    @FXML
    private Pane mainPanel;
    @FXML
    private Button btnMaster;
    @FXML
    private Button btnTransaksi;
    @FXML
    private Button btnLaporan;
    @FXML
    private Button btnLogOut;
    @FXML
    private VBox dropdownBox;
    @FXML
    private VBox dropdownBox2;
    @FXML
    private VBox dropdownBox3;
    @FXML
    private Button btnTunjangan;

    @FXML
    public void toggleDropdown() {
        boolean isVisible = dropdownBox.isVisible();
        dropdownBox.setVisible(!isVisible);
        dropdownBox.setManaged(!isVisible);
        if (isVisible) {
            btnTransaksi.setLayoutY(288);
            btnLaporan.setLayoutY(365);
        } else {
            btnTransaksi.setLayoutY(520);
            btnLaporan.setLayoutY(592);
        }
    }

    public void toggleDropdown2() {
        boolean isVisible = dropdownBox2.isVisible();
        dropdownBox2.setVisible(!isVisible);
        dropdownBox2.setManaged(!isVisible);
        if (isVisible) {
            btnLaporan.setLayoutY(365);
        } else {
            btnLaporan.setLayoutY(430);
        }
    }

    public void toggleDropdown3() {
        boolean isVisible = dropdownBox3.isVisible();
        dropdownBox3.setVisible(!isVisible);
        dropdownBox3.setManaged(!isVisible);
    }

    public void btnTunjanganClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/manajemen_penggajian/CRUDTunjangan.fxml"));
            Node crudTunjangan = loader.load();
            mainPanel.getChildren().clear();
            mainPanel.getChildren().add(crudTunjangan);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
