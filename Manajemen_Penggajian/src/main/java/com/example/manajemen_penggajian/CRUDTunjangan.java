package com.example.manajemen_penggajian;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CRUDTunjangan implements Initializable {
    @FXML
    private Button btnCreate, btnEdit, btnSave, btnUpdate, btnDelete, btnCancel;
    @FXML
    private TextField tbID;
    @FXML
    private TextField tbNama;
    @FXML
    private TextField tbStatus;
    @FXML
    private TextField tbSearch;
    @FXML
    private ImageView btnSearch;
    @FXML
    private TableView<CRUDTunjangan> viewTunjangan;
    @FXML
    private TableColumn<CRUDTunjangan, String> cIDTunjangan;
    @FXML
    private TableColumn<CRUDTunjangan, String> cNamaTunjangan;
    @FXML
    private TableColumn<CRUDTunjangan, String> cStatusTunjangan;

    private ObservableList<CRUDTunjangan> oblist = FXCollections.observableArrayList();

    DBConnect connect = new DBConnect();

    private String IDTunjangan;
    private String nama;
    private String status;

    public CRUDTunjangan() {

    }

    public CRUDTunjangan(String IDTunjangan, String nama, String status) {
        this.IDTunjangan = IDTunjangan;
        this.nama = nama;
        this.status = status;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connect.stat = connect.conn.createStatement();
            String query = "SELECT * FROM Tunjangan";
            connect.result = connect.stat.executeQuery(query);

            while (connect.result.next()) {
                oblist.add(new CRUDTunjangan(
                        connect.result.getString("IDTunjangan"),
                        connect.result.getString("nama"),
                        connect.result.getString("status")));
            }
            connect.stat.close();
            connect.result.close();
        } catch (Exception exception) {
            System.out.println("Error When Load Data: " + exception);
        }

        cIDTunjangan.setCellValueFactory(new PropertyValueFactory<>("IDTunjangan"));
        cNamaTunjangan.setCellValueFactory(new PropertyValueFactory<>("nama"));
        cStatusTunjangan.setCellValueFactory(new PropertyValueFactory<>("status"));

        viewTunjangan.setItems(oblist);
    }


    public String getIDTunjangan() {
        return IDTunjangan;
    }

    public void setIDTunjangan(String IDTunjangan) {
        this.IDTunjangan = IDTunjangan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String generateID() {
        String prefix = "TJN";
        String query = "SELECT COUNT(*) FROM Tunjangan";
        int count = 0;

        connect.pstat = null;
        connect.result = null;

        try {
            connect.pstat = connect.conn.prepareStatement(query);
            connect.result = connect.pstat.executeQuery();

            if (connect.result.next()) {
                count = connect.result.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle the exception appropriately (e.g., log it or rethrow it as a runtime exception)
        } finally {
            if (connect.result != null) {
                try {
                    connect.result.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (connect.pstat != null) {
                try {
                    connect.pstat.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        count++; // Increment count by 1 to get the new ID
        String newID = prefix + String.format("%03d", count);
        return newID;
    }

    @FXML
    public void btnSaveClick() {
        setIDTunjangan(generateID());
        setNama(tbNama.getText());
        setStatus("Active");

        if (tbNama.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Value Cannot Be Null");
            alert.showAndWait();
        } else {
            try {
                String sql = "{CALL sp_InsertTunjangan(?, ?, ?)}";
                connect.pstat = connect.conn.prepareStatement(sql);
                connect.pstat.setString(1, getIDTunjangan());
                connect.pstat.setString(2, getNama());
                connect.pstat.setString(3, getStatus());

                int rowsUpdated = connect.pstat.executeUpdate();

                if (rowsUpdated > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setContentText("Save Data Successfully");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Failed to save data");
                    alert.showAndWait();
                }

                connect.pstat.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("SQL Error: " + ex.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleTableDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            CRUDTunjangan selectedTunjangan = viewTunjangan.getSelectionModel().getSelectedItem();
            if (selectedTunjangan != null) {
                tbID.setText(selectedTunjangan.getIDTunjangan());
                tbNama.setText(selectedTunjangan.getNama());
                tbStatus.setText(selectedTunjangan.getStatus());
            }
        }
    }

    @FXML
    private void btnUpdateClick() {
        setIDTunjangan(tbID.getText());
        setNama(tbNama.getText());
        setStatus(tbStatus.getText());
        try {
            String sql = "{CALL sp_UpdateTunjangan(?, ?, ?)}";
            connect.pstat = connect.conn.prepareStatement(sql);
            connect.pstat.setString(1, getIDTunjangan());
            connect.pstat.setString(2, getNama());
            connect.pstat.setString(3, getStatus());

            int rowsUpdated = connect.pstat.executeUpdate();

            if (rowsUpdated > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText("Update Data Successfully");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Failed to save data");
                alert.showAndWait();
            }

            connect.pstat.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("SQL Error: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void btnDeleteClick() {
        setIDTunjangan(tbID.getText());
        try {
            String sql = "{CALL sp_DeleteTunjangan(?)}";
            connect.pstat = connect.conn.prepareStatement(sql);
            connect.pstat.setString(1, getIDTunjangan());

            int rowsUpdated = connect.pstat.executeUpdate();

            if (rowsUpdated > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText("Delete Data Successfully");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Failed to delete data");
                alert.showAndWait();
            }

            connect.pstat.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("SQL Error: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void btnCancelClick() {
        tbStatus.setText(null);
        tbNama.setText(null);
        tbStatus.setText(null);
        tbID.setDisable(true);
        tbNama.setDisable(true);
        tbStatus.setDisable(true);
        btnSave.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnCancel.setDisable(true);
    }

    @FXML
    private void btnSearchClick() {
        oblist.clear();
        try {
            String sql = "{CALL sp_findAllTunjangan(?)}";
            connect.pstat = connect.conn.prepareStatement(sql);
            connect.pstat.setString(1, tbSearch.getText());
            connect.result = connect.pstat.executeQuery();

            while (connect.result.next()) {
                String idTunjangan = connect.result.getString("IDTunjangan");
                String nama = connect.result.getString("nama");
                String status = connect.result.getString("status");

                CRUDTunjangan tunjangan = new CRUDTunjangan(idTunjangan, nama, status);
                oblist.add(tunjangan);
            }

            connect.result.close();
            connect.pstat.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("SQL Error: " + ex.getMessage());
            alert.showAndWait();
        }
    }
}
