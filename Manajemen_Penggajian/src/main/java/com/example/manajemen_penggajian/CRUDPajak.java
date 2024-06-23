package com.example.manajemen_penggajian;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Optional;
import java.util.ResourceBundle;

public class CRUDPajak implements Initializable {
    private ObservableList<CRUDPajak.Pajak> oblist = FXCollections.observableArrayList();
    DBConnect connect = new DBConnect();
    private boolean edit = false;
    @FXML
    private TableView<CRUDPajak.Pajak> dgvPajak;
/*    @FXML
    private TableColumn<Pajak, String> ID;*/
    @FXML
    private TableColumn<Pajak, String> Nama;
    @FXML
    private TableColumn<Pajak, Double> Persentase;
    @FXML
    private TableColumn<Pajak, String> Status;
    @FXML
    private TextField txtCari;
    @FXML
    private Button btnCari;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnClear;
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtNama;
    @FXML
    private TextField txtPersentase;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        oblist.clear();
        try {
            connect = new DBConnect();
            CallableStatement statement = connect.conn.prepareCall("{CALL sp_viewPajak(?)}");
            statement.setNull(1, Types.VARCHAR);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                oblist.add(new Pajak(
                        rs.getString("IDPajak"),
                        rs.getString("nama"),
                        rs.getDouble("persentase"),
                        rs.getString("status")
                ));
            }
/*            ID.setCellValueFactory(new PropertyValueFactory<>("ID"));*/
            Nama.setCellValueFactory(new PropertyValueFactory<>("Nama"));
            Persentase.setCellValueFactory(new PropertyValueFactory<>("Persentase"));
            Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
            dgvPajak.setItems(oblist);

            txtID.setDisable(true);
            txtNama.setDisable(true);
            txtPersentase.setDisable(true);

            dgvPajak.setOnMouseClicked(mouseEvent -> ClickdgvPajak());

        }catch (Exception ex){
            System.out.println("Error when load tax data: " + ex);
        }
    }
    @FXML
    protected void btnRefreshClick(){
        oblist.clear();
        try {
            connect = new DBConnect();
            CallableStatement statement = connect.conn.prepareCall("{CALL sp_viewPajak(?)}");
            statement.setNull(1, Types.VARCHAR);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                oblist.add(new Pajak(
                        rs.getString("IDPajak"),
                        rs.getString("nama"),
                        rs.getDouble("persentase"),
                        rs.getString("status")
                ));
            }
/*            ID.setCellValueFactory(new PropertyValueFactory<>("ID"));*/
            Nama.setCellValueFactory(new PropertyValueFactory<>("Nama"));
            Persentase.setCellValueFactory(new PropertyValueFactory<>("Persentase"));
            Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
            dgvPajak.setItems(oblist);
        }catch (Exception ex){
            System.out.println("Error when load tax data: " + ex);
        }
    }
    @FXML
    protected void btnCreateClick() {
        try {
            connect = new DBConnect();
            CallableStatement statement = connect.conn.prepareCall("{CALL sp_IDPajak(?)}");
            statement.registerOutParameter(1, Types.VARCHAR);
            statement.execute();

            String newID = statement.getString(1);
            txtID.setText(newID);

            txtID.setDisable(false);
            txtNama.setDisable(false);
            txtPersentase.setDisable(false);
        } catch (Exception ex) {
            System.out.println("Error when generate Tax ID: " + ex);
        }
    }
    @FXML
    protected void btnSaveClick(){
        try {
            String id = txtID.getText();
            String nama = txtNama.getText();
            double persentase = Double.parseDouble(txtPersentase.getText());
            String status = "Active";

            connect = new DBConnect();
            CallableStatement statement = connect.conn.prepareCall("{CALL sp_insertPajak(?, ?, ?)}");
            statement.setString(1, nama);
            statement.setDouble(2, persentase);
            statement.setString(3, status);
            statement.execute();

            txtID.clear();
            txtNama.clear();
            txtPersentase.clear();

            txtID.setDisable(true);
            txtNama.setDisable(true);
            txtPersentase.setDisable(true);

            btnRefreshClick();

        } catch (Exception ex) {
            System.out.println("Error when save Tax data: " + ex);
        }
    }
    @FXML
    protected void btnEditClick(){
        edit = true;
    }
    @FXML
    protected void ClickdgvPajak(){
        if(!edit){
            return;
        }
        Pajak selectedPajak = dgvPajak.getSelectionModel().getSelectedItem();

        txtNama.setDisable(false);
        txtPersentase.setDisable(false);

        if (selectedPajak != null) {
            txtID.setText(selectedPajak.getID());
            txtNama.setText(selectedPajak.getNama());
            txtPersentase.setText(String.valueOf(selectedPajak.getPersentase()));
        }
    }
    @FXML
    protected void btnClearClick(){
        txtID.clear();
        txtNama.clear();
        txtPersentase.clear();

        txtID.setDisable(true);
        txtNama.setDisable(true);
        txtPersentase.setDisable(true);
    }
    @FXML
    protected void btnUpdateClick(){
        if(!edit){
            return;
        }
        try {
            String id = txtID.getText();
            String nama = txtNama.getText();
            double persentase = Double.parseDouble(txtPersentase.getText());

            connect = new DBConnect();
            CallableStatement statement = connect.conn.prepareCall("{CALL sp_updatePajak(?, ?, ?)}");
            statement.setString(1, id);
            statement.setString(2, nama);
            statement.setDouble(3, persentase);
            statement.execute();

            txtID.clear();
            txtNama.clear();
            txtPersentase.clear();

            txtID.setDisable(true);
            txtNama.setDisable(true);
            txtPersentase.setDisable(true);

            btnRefreshClick();

        } catch (Exception ex) {
            System.out.println("Error when update Tax data: " + ex);
        }
    }
    @FXML
    protected void btnDeleteClick(){
        if(!edit){
            return;
        }
        try {
            String id = txtID.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation for De-Active");
            alert.setHeaderText("Are you sure to De-Active Tax?");
            alert.setContentText("Select 'OK' to De-Active or 'Cancel' to cancel.");

            ButtonType deactiveButton = new ButtonType("De-Active");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(deactiveButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == deactiveButton) {

                connect = new DBConnect();
                CallableStatement statement = connect.conn.prepareCall("{CALL sp_deletePajak(?, ?)}");
                statement.setString(1, id);
                statement.setString(2, "De-Active");
                statement.execute();

                txtID.clear();
                txtNama.clear();
                txtPersentase.clear();

                txtID.setDisable(true);
                txtNama.setDisable(true);
                txtPersentase.setDisable(true);

                btnRefreshClick();
            }

        } catch (Exception ex) {
            System.out.println("Error when De-Active: " + ex);
        }
    }
    @FXML
    protected void btnSearchClick(){
        try {
            String searchKeyword = txtCari.getText();

            oblist.clear();

            connect = new DBConnect();
            CallableStatement statement = connect.conn.prepareCall("{CALL sp_viewPajak(?)}");
            statement.setString(1, searchKeyword);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                oblist.add(new Pajak(
                        rs.getString("IDPajak"),
                        rs.getString("nama"),
                        rs.getDouble("persentase"),
                        rs.getString("status")
                ));
            }

            dgvPajak.setItems(oblist);

        } catch (Exception ex) {
            System.out.println("Error when search data: " + ex);
        }
    }

    public static class Pajak {
        private String ID;
        private String Nama;
        private Double Persentase;
        private String Status;

        public Pajak(String ID, String Nama, Double Persentase, String Status) {
            this.ID = ID;
            this.Nama = Nama;
            this.Persentase = Persentase;
            this.Status = Status;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getNama() {
            return Nama;
        }

        public void setNama(String nama) {
            Nama = nama;
        }

        public Double getPersentase() {
            return Persentase;
        }

        public void setPersentase(Double persentase) {
            Persentase = persentase;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }
    }
}
