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
    private Button btnAsuransi;
    @FXML
    private Button btnGolongan;
    @FXML
    private Button btnJabatan;
    @FXML
    private Button btnDivisi;
    @FXML
    private Button btnShift;
    @FXML
    private Button btnPajak;
    @FXML
    private Button btnKaryawan;

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

    public void btnAsuransiClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/manajemen_penggajian/CRUDAsuransi.fxml"));
            Node crudAsuransi = loader.load();
            mainPanel.getChildren().clear();
            mainPanel.getChildren().add(crudAsuransi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnPajakClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/manajemen_penggajian/CRUDPajak.fxml"));
            Node crudPajak = loader.load();
            mainPanel.getChildren().clear();
            mainPanel.getChildren().add(crudPajak);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnGolonganClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/manajemen_penggajian/CRUDGolongan.fxml"));
            Node crudGolongan = loader.load();
            mainPanel.getChildren().clear();
            mainPanel.getChildren().add(crudGolongan);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnDivisiClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/manajemen_penggajian/CRUDDivisi.fxml"));
            Node crudDivisi = loader.load();
            mainPanel.getChildren().clear();
            mainPanel.getChildren().add(crudDivisi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnJabatanClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/manajemen_penggajian/CRUDJabatan.fxml"));
            Node crudJabatan = loader.load();
            mainPanel.getChildren().clear();
            mainPanel.getChildren().add(crudJabatan);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnShiftClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/manajemen_penggajian/CRUDShift.fxml"));
            Node crudShift = loader.load();
            mainPanel.getChildren().clear();
            mainPanel.getChildren().add(crudShift);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
