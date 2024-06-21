package com.example.manajemen_penggajian;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class CRUDTunjangan {
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnEdit;
    @FXML
    private TextField tbID;
    @FXML
    private TextField tbNama;
    @FXML
    private TextField tbStatus;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    DBConnect connect = new DBConnect();

    @FXML
    public void btnCreateClick() {
        // Enable komponen yang diperlukan
        tbID.setDisable(false);
        tbNama.setDisable(false);
        tbStatus.setDisable(false);
        btnSave.setDisable(false);
        btnCancel.setDisable(false);
    }
    
    @FXML
    public void btnEditClick() {
        tbID.setDisable(false);
        tbNama.setDisable(false);
        tbStatus.setDisable(false);
        btnCancel.setDisable(false);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }

    @FXML
    public void btnSaveClick() {
        String id, nama, status;
        id = tbID.getText();
        nama = tbNama.getText();
        status = tbStatus.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        try {
            String sql = "{CALL sp_updateObat(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            connect.pstat = connect.conn.prepareStatement(sql);
            connect.pstat.setString(1, kodeObat);
            connect.pstat.setString(2, nama);
            connect.pstat.setString(3, merk);
            connect.pstat.setString(4, kemasan);
            connect.pstat.setString(5, efek);
            connect.pstat.setInt(6,hrgBeli);
            connect.pstat.setInt(7,hrgJual);
            connect.pstat.setString(8,kadaluarsa);
            connect.pstat.setInt(9,stok);

            int rowsUpdated = connect.pstat.executeUpdate();

            if (rowsUpdated > 0) {
                alert.setContentText("Data Berhasil Di Update");
                alert.show();
            } else {
                alert.setContentText("Data Tidak Ditemukan");
                alert.show();
            }

            connect.pstat.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            alert.setContentText("Terjadi error saat load obat : " +ex);
            alert.show();
        }
    }
}
