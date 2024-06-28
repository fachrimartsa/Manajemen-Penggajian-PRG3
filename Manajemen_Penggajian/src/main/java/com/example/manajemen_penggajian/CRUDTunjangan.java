package com.example.manajemen_penggajian;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
<<<<<<< Updated upstream
import javafx.scene.input.MouseEvent;

import java.net.URL;
=======
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.CallableStatement;
>>>>>>> Stashed changes
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CRUDTunjangan implements Initializable {
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
    @FXML
<<<<<<< Updated upstream
=======
    private TextField tbSearch;
    @FXML
    private ImageView btnSearch;
    @FXML
>>>>>>> Stashed changes
    private TableView<CRUDTunjangan> viewTunjangan;
    @FXML
    private TableColumn<CRUDTunjangan, String> cIDTunjangan;
    @FXML
    private TableColumn<CRUDTunjangan, String> cNamaTunjangan;
    @FXML
    private TableColumn<CRUDTunjangan, String> cStatusTunjangan;

    DBConnect connect = new DBConnect();

    private String IDTunjangan;
    private String nama;
    private String status;

    public CRUDTunjangan() {
<<<<<<< Updated upstream

    }

    public CRUDTunjangan(String IDTunjangan, String nama, String status) {
        this.IDTunjangan = IDTunjangan;
=======
    }

    // Constructor
    public CRUDTunjangan(String id, String nama, String status) {
        this.IDTunjangan = id;
>>>>>>> Stashed changes
        this.nama = nama;
        this.status = status;
    }

<<<<<<< Updated upstream
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<CRUDTunjangan> oblist = FXCollections.observableArrayList();
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


=======
    // Getters and Setters
>>>>>>> Stashed changes
    public String getIDTunjangan() {
        return IDTunjangan;
    }

<<<<<<< Updated upstream
    public void setIDTunjangan(String IDTunjangan) {
        this.IDTunjangan = IDTunjangan;
=======
    public void setIDTunjangan(String id) {
        this.IDTunjangan = id;
>>>>>>> Stashed changes
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<CRUDTunjangan> oblist = FXCollections.observableArrayList();
        try {
            connect.stat = connect.conn.createStatement();
            String query = "SELECT * FROM Tunjangan";
            connect.result = connect.stat.executeQuery(query);

            while (connect.result.next()) {
                oblist.add(new CRUDTunjangan(
                        connect.result.getString("IDTunjangan"),
                        connect.result.getString("nama"),
                        connect.result.getString("status")));
            };
            connect.stat.close();
            connect.result.close();
        } catch (Exception exception) {
            System.out.println("Error When Load Data" + exception);
        }
        cIDTunjangan.setCellValueFactory(new PropertyValueFactory<>("IDTunjangan"));
        cNamaTunjangan.setCellValueFactory(new PropertyValueFactory<>("nama"));
        cStatusTunjangan.setCellValueFactory(new PropertyValueFactory<>("status"));
        viewTunjangan.setItems(oblist);
    }

    private String generateID() throws SQLException {
        String prefix = "TJN";
        String query = "SELECT COUNT(*) FROM Tunjangan";
        int count = 0;

        connect.pstat = connect.conn.prepareStatement(query);

        try {
             connect.result = connect.pstat.executeQuery();

            if (connect.result.next()) {
                count = connect.result.getInt(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        count++; // Increment count by 1 to get the new ID
        String newID = prefix + String.format("%03d", count);
        return newID;
    }

    @FXML
    private void btnCreateClick() throws SQLException {
        // Enable komponen yang diperlukan
        tbID.setDisable(false);
        tbNama.setDisable(false);
        tbStatus.setDisable(false);
        btnSave.setDisable(false);
        btnCancel.setDisable(false);
        // Inisiasi id dan status
        tbID.setText(generateID());
        tbStatus.setText("Active");
    }
    
    @FXML
    private void btnEditClick() {
        tbID.setDisable(false);
        tbNama.setDisable(false);
        tbStatus.setDisable(false);
        btnCancel.setDisable(false);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }

<<<<<<< Updated upstream
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
=======
    @FXML
    private void btnSaveClick() {
        setIDTunjangan(tbID.getText());
        setNama(tbNama.getText());
        setStatus(tbStatus.getText());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        if (tbNama.getText().isEmpty()) {
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
                connect.pstat.executeUpdate();
                connect.pstat.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                alert.setContentText("Error : " +ex);
                alert.show();
            }
            alert.setTitle("Information");
            alert.setContentText("Save Data Succesfully");
            alert.show();
            tbID.setDisable(true);
            tbNama.setDisable(true);
            tbStatus.setDisable(true);
            btnSave.setDisable(true);
            btnCancel.setDisable(true);
        }
    }

    @FXML
    private void btnCancelClick() {
        tbID.setDisable(true);
        tbNama.setDisable(true);
        tbStatus.setDisable(true);
        btnSave.setDisable(true);
        btnCancel.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        // Inisiasi id dan status
        tbID.setText("");
        tbStatus.setText("");
        tbNama.setText("");
    }

    @FXML
    private void btnSearchClick() {
        ObservableList<CRUDTunjangan> oblist = FXCollections.observableArrayList();
        String searchQuery = tbSearch.getText();

        try {
            connect.stat = connect.conn.createStatement();
            String sql = "{CALL sp_findAllTunjangan(?)}";
            CallableStatement cstmt = connect.conn.prepareCall(sql);
            cstmt.setString(1, searchQuery.isEmpty() ? null : searchQuery);
            connect.result = cstmt.executeQuery();

            while (connect.result.next()) {
                oblist.add(new CRUDTunjangan(
                        connect.result.getString("IDTunjangan"),
                        connect.result.getString("nama"),
                        connect.result.getString("status")));
            }
            connect.result.close();
            cstmt.close();

        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error executing search");
            alert.setContentText("There was an error while executing the search: " + exception.getMessage());
            alert.showAndWait();
        }

        cIDTunjangan.setCellValueFactory(new PropertyValueFactory<>("IDTunjangan"));
        cNamaTunjangan.setCellValueFactory(new PropertyValueFactory<>("nama"));
        cStatusTunjangan.setCellValueFactory(new PropertyValueFactory<>("status"));

        viewTunjangan.setItems(oblist);
    }

    @FXML
    private void rowTableClick(MouseEvent event) {
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

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        try {
>>>>>>> Stashed changes
            String sql = "{CALL sp_UpdateTunjangan(?, ?, ?)}";
            connect.pstat = connect.conn.prepareStatement(sql);
            connect.pstat.setString(1, getIDTunjangan());
            connect.pstat.setString(2, getNama());
            connect.pstat.setString(3, getStatus());

            int rowsUpdated = connect.pstat.executeUpdate();

            if (rowsUpdated > 0) {
<<<<<<< Updated upstream
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText("Update Data Successfully");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Failed to save data");
                alert.showAndWait();
=======
                alert.setContentText("Update Data Succesfully");
                alert.show();
                tbID.setDisable(true);
                tbNama.setDisable(true);
                tbStatus.setDisable(true);
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
                btnCancel.setDisable(true);
            } else {
                alert.setContentText("Data Undefined");
                alert.show();
>>>>>>> Stashed changes
            }

            connect.pstat.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
<<<<<<< Updated upstream
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("SQL Error: " + ex.getMessage());
            alert.showAndWait();
=======
            alert.setContentText("Error When Load Data : " +ex);
            alert.show();
>>>>>>> Stashed changes
        }
    }

    @FXML
    private void btnDeleteClick() {
        setIDTunjangan(tbID.getText());
        try {
<<<<<<< Updated upstream
            String sql = "{CALL sp_DeleteTunjangan(?, ?, ?)}";
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
=======
            String query =  "{CALL sp_DeleteTunjangan(?)}";
            connect.pstat = connect.conn.prepareStatement(query);
            connect.pstat.setString(1,getIDTunjangan());
            connect.pstat.executeUpdate();
            connect.pstat.close();
        } catch (Exception ex) {
            System.out.println("Error When DeletingData : " + ex);
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Delete Data Succesfully");
        alert.show();
        tbID.setDisable(true);
        tbNama.setDisable(true);
        tbStatus.setDisable(true);
>>>>>>> Stashed changes
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnCancel.setDisable(true);
    }
}
