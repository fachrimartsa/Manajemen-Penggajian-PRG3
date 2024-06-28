package com.example.manajemen_penggajian;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

import java.util.Optional;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Optional;
import java.util.ResourceBundle;

//CRUD Golongan
public class CRUDGolongan implements Initializable {
    private ObservableList<CRUDGolongan.Golongan> oblist = FXCollections.observableArrayList();
    DBConnect connect = new DBConnect();
    @FXML
    private TableView<CRUDGolongan.Golongan> dgvGolongan;
    @FXML
    private TableColumn<CRUDAsuransi.Golongan, Integer> cNum;
    @FXML
    private TableColumn<Golongan,String> Nama;
    @FXML
    private TableColumn<Golongan,Integer> Gaji;
    @FXML
    private TableColumn<Golongan,Integer> Masa;
    @FXML
    private TableColumn<Golongan,Integer> UangMakan;
    @FXML
    private TableColumn<Golongan,Integer> UangTransport;
    @FXML
    private TableColumn<Golongan,String> Status;


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
    private Button btnClear;
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtNama;
    @FXML
    private TextField txtGaji;
    @FXML
    private TextField txtMasa;
    @FXML
    private TextField txtUangmkn;
    @FXML
    private TextField txtUangTransport;
    @FXML
    private TextField txtCari;
    @FXML
    private TextField txtStatus;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connect = new DBConnect();
            CallableStatement statement = connect.conn.prepareCall("{CALL sp_viewGolongan(?)}");
            statement.setNull(1, Types.VARCHAR);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                oblist.add(new CRUDGolongan.Golongan(
                        rs.getString("IDGolongan"),
                        rs.getString("nama"),
                        rs.getInt("masa_kerja"),
                        rs.getInt("gaji"),
                        rs.getInt("uang_makan"),
                        rs.getInt("uang_transport"),
                        rs.getString("status")
                ));
            }

            cNum.setCellValueFactory(cellData -> new SimpleIntegerProperty(dgvGolongan.getItems().indexOf(cellData.getValue()) +1).asObject());
            Nama.setCellValueFactory(new PropertyValueFactory<>("Nama"));
            Gaji.setCellValueFactory(new PropertyValueFactory<>("Gaji"));
            Masa.setCellValueFactory(new PropertyValueFactory<>("Masa"));
            UangMakan.setCellValueFactory(new PropertyValueFactory<>("UangMakan"));
            UangTransport.setCellValueFactory(new PropertyValueFactory<>("UangTransport"));
/*
            Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
*/
            dgvGolongan.setItems(oblist);

            txtID.setDisable(true);
            txtGaji.setDisable(true);
            txtMasa.setDisable(true);
            txtNama.setDisable(true);
            txtUangmkn.setDisable(true);
            txtUangTransport.setDisable(true);

            btnSave.setDisable(true);
            btnClear.setDisable(false);
            btnCreate.setDisable(false);

            dgvGolongan.setOnMouseClicked(mouseEvent -> ClickdgvGolongan());
            searchGolongan();

        }catch (Exception ex){
            System.out.println("Terjadi error saat load data golongan: " + ex);
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(dgvGolongan.getScene().getWindow());
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean ValidasiTextBox(String namaText, String gajiText, String masaText, String uangMakanText, String uangTransportText) {
        namaText = namaText.trim();
        gajiText = gajiText.trim();
        masaText = masaText.trim();
        uangMakanText = uangMakanText.trim();
        uangTransportText = uangTransportText.trim();

        if (namaText.isEmpty()) {
            showAlert("Please fill in the name.");
            return false;
        }

        if (gajiText.isEmpty()) {
            showAlert("Salary must be filled in.");
            return false;
        }

        if (!gajiText.matches("\\d+")) {
            showAlert("Salary must be a number.");
            return false;
        }

        if (masaText.isEmpty()) {
            showAlert("Length of service must be filled in.");
            return false;
        }

        if (!masaText.matches("\\d+")) {
            showAlert("Length of service must be a number.");
            return false;
        }

        if (uangMakanText.isEmpty()) {
            showAlert("Meal allowance must be filled in.");
            return false;
        }

        if (!uangMakanText.matches("\\d+")) {
            showAlert("Meal allowance must be a number.");
            return false;
        }

        if (uangTransportText.isEmpty()) {
            showAlert("Transport allowance must be filled in.");
            return false;
        }

        if (!uangTransportText.matches("\\d+")) {
            showAlert("Transport allowance must be a number.");
            return false;
        }
        return true;
    }

    @FXML
    protected void ClickdgvGolongan(){
        Golongan selectedGolongan = dgvGolongan.getSelectionModel().getSelectedItem();

        txtGaji.setDisable(false);
        txtMasa.setDisable(false);
        txtNama.setDisable(false);
        txtUangmkn.setDisable(false);
        txtUangTransport.setDisable(false);

        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);

        if(selectedGolongan != null){
            txtID.setText(selectedGolongan.getID());
            txtNama.setText(selectedGolongan.getNama());
            txtGaji.setText(String.valueOf(selectedGolongan.getGaji()));
            txtMasa.setText(String.valueOf(selectedGolongan.getMasa()));
            txtUangmkn.setText(String.valueOf(selectedGolongan.getUangMakan()));
            txtUangTransport.setText(String.valueOf(selectedGolongan.getUangTransport()));
        }
        btnRefreshClick();
    }
    @FXML
    protected void searchGolongan(){
        try {
            String searchKeyword = txtCari.getText();

            connect = new DBConnect();
            CallableStatement statement = connect.conn.prepareCall("{CALL sp_viewGolongan(?)}");
            statement.setString(1, searchKeyword);
            ResultSet rs = statement.executeQuery();

            oblist.clear();
            while (rs.next()){
                oblist.add(new CRUDGolongan.Golongan(
                        rs.getString("IDGolongan"),
                        rs.getString("nama"),
                        rs.getInt("masa_kerja"),
                        rs.getInt("gaji"),
                        rs.getInt("uang_makan"),
                        rs.getInt("uang_transport"),
                        rs.getString("status")
                ));
            }
            dgvGolongan.setItems(oblist);
        } catch (Exception ex){
            System.out.println("Error when search data: " + ex);
        }
    }

    @FXML
    protected void btnCreateClick() {
        try {
            connect = new DBConnect();
            CallableStatement statement = connect.conn.prepareCall("{CALL sp_IDGolongan(?)}");
            statement.registerOutParameter(1, Types.VARCHAR);
            statement.execute();

            String newID = statement.getString(1);
            txtID.setText(newID);

            txtID.setDisable(false);
            txtGaji.setDisable(false);
            txtMasa.setDisable(false);
            txtNama.setDisable(false);
            txtUangmkn.setDisable(false);
            txtUangTransport.setDisable(false);

            btnSave.setDisable(false);
        } catch (Exception ex) {
            System.out.println("Error when generate Group ID: " + ex);
        }
    }
    @FXML
    protected void btnRefreshClick(){
        oblist.clear();
        try {
            connect = new DBConnect();
            CallableStatement statement = connect.conn.prepareCall("{CALL sp_viewGolongan(?)}");
            statement.setNull(1, Types.VARCHAR);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                oblist.add(new CRUDGolongan.Golongan(
                        rs.getString("IDGolongan"),
                        rs.getString("nama"),
                        rs.getInt("gaji"),
                        rs.getInt("masa_kerja"),
                        rs.getInt("uang_makan"),
                        rs.getInt("uang_transport"),
                        rs.getString("status")
                ));
            }
            Nama.setCellValueFactory(new PropertyValueFactory<>("Nama"));
            Gaji.setCellValueFactory(new PropertyValueFactory<>("Gaji"));
            Masa.setCellValueFactory(new PropertyValueFactory<>("Masa"));
            UangMakan.setCellValueFactory(new PropertyValueFactory<>("UangMakan"));
            UangTransport.setCellValueFactory(new PropertyValueFactory<>("UangTransport"));
/*
            Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
*/
            dgvGolongan.setItems(oblist);

            btnSave.setDisable(true);
            btnClear.setDisable(false);
            btnCreate.setDisable(false);
        }catch (Exception ex){
            System.out.println("Error when reload Group data: " + ex);
        }
    }
    @FXML
    protected void btnUpdateClick() {
        String namaText = txtNama.getText();
        String gajiText = txtGaji.getText();
        String masaText = txtMasa.getText();
        String uangMakanText = txtUangmkn.getText();
        String uangTransportText = txtUangTransport.getText();

        if (ValidasiTextBox(namaText, gajiText, masaText, uangMakanText, uangTransportText)) {
            try {
                String id = txtID.getText();
                String nama = txtNama.getText();
                int gaji = Integer.parseInt(txtGaji.getText());
                int masaKerja = Integer.parseInt(txtMasa.getText());
                int uangMakan = Integer.parseInt(txtUangmkn.getText());
                int uangTransport = Integer.parseInt(txtUangTransport.getText());

                connect = new DBConnect();
                CallableStatement statement = connect.conn.prepareCall("{CALL sp_updateGolongan(?, ?, ?, ?, ?, ?)}");
                statement.setString(1, id);
                statement.setString(2, nama);
                statement.setInt(3, gaji);
                statement.setInt(4, masaKerja);
                statement.setInt(5, uangMakan);
                statement.setInt(6, uangTransport);
                statement.execute();

                txtID.clear();
                txtNama.clear();
                txtGaji.clear();
                txtMasa.clear();
                txtUangmkn.clear();
                txtUangTransport.clear();

                txtID.setDisable(true);
                txtNama.setDisable(true);
                txtGaji.setDisable(true);
                txtMasa.setDisable(true);
                txtUangmkn.setDisable(true);
                txtUangTransport.setDisable(true);

                btnDelete.setDisable(true);
                btnUpdate.setDisable(true);

                btnRefreshClick();
            } catch (Exception ex) {
                System.out.println("Error when update Group data: " + ex);
            }
        }
    }
    @FXML
    protected void btnDeleteClick(){
        try {
            String id = txtID.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(dgvGolongan.getScene().getWindow());
            alert.setTitle("Confirmation for De-Active");
            alert.setHeaderText("Are you sure to De-Active Tax?");
            alert.setContentText("Select 'OK' to De-Active or 'Cancel' to cancel.");

            ButtonType deactiveButton = new ButtonType("De-Active");
            ButtonType cancelButton = new ButtonType("Cancel",ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(deactiveButton,cancelButton);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == deactiveButton){
                connect = new DBConnect();
                CallableStatement statement = connect.conn.prepareCall("{CALL sp_deleteGolongan(?, ?)}");
                statement.setString(1, id);
                statement.setString(2, "De-Active");
                statement.execute();

                txtID.clear();
                txtNama.clear();
                txtGaji.clear();
                txtMasa.clear();
                txtUangmkn.clear();
                txtUangTransport.clear();



                txtID.setDisable(true);
                txtGaji.setDisable(true);
                txtMasa.setDisable(true);
                txtNama.setDisable(true);
                txtUangmkn.setDisable(true);
                txtUangTransport.setDisable(true);

                btnRefreshClick();
            }
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);
        } catch (Exception ex){
            System.out.println("Error when De-Active: " + ex);
        }
    }
    @FXML
    protected void btnSaveClick() {
        String namaText = txtNama.getText();
        String gajiText = txtGaji.getText();
        String masaText = txtMasa.getText();
        String uangMakanText = txtUangmkn.getText();
        String uangTransportText = txtUangTransport.getText();

        if (ValidasiTextBox(namaText, gajiText, masaText, uangMakanText, uangTransportText)) {
            try {
                String nama = txtNama.getText();
                int gaji = Integer.parseInt(txtGaji.getText());
                int masaKerja = Integer.parseInt(txtMasa.getText());
                int uangMakan = Integer.parseInt(txtUangmkn.getText());
                int uangTransport = Integer.parseInt(txtUangTransport.getText());
                String status = "Active";

                connect = new DBConnect();
                CallableStatement statement = connect.conn.prepareCall("{CALL sp_insertGolongan(?, ?, ?, ?, ?, ?)}");
                statement.setString(1, nama);
                statement.setInt(2, gaji);
                statement.setInt(3, masaKerja);
                statement.setInt(4, uangMakan);
                statement.setInt(5, uangTransport);
                statement.setString(6, status);
                statement.execute();

                txtID.clear();
                txtNama.clear();
                txtGaji.clear();
                txtMasa.clear();
                txtUangmkn.clear();
                txtUangTransport.clear();

                txtID.setDisable(true);
                txtGaji.setDisable(true);
                txtMasa.setDisable(true);
                txtNama.setDisable(true);
                txtUangmkn.setDisable(true);
                txtUangTransport.setDisable(true);

                btnRefreshClick();
            } catch (Exception ex) {
                System.out.println("Error when save Group data: " + ex);
            }
        }
    }
    @FXML
    protected void btnClearClick() {
        txtID.clear();
        txtNama.clear();
        txtGaji.clear();
        txtMasa.clear();
        txtUangmkn.clear();
        txtUangTransport.clear();

        txtID.setDisable(true);
        txtGaji.setDisable(true);
        txtMasa.setDisable(true);
        txtNama.setDisable(true);
        txtUangmkn.setDisable(true);
        txtUangTransport.setDisable(true);
        btnRefreshClick();
    }
    public static class Golongan{
        private String ID;
        private String Nama;
        private Integer Gaji;
        private Integer Masa;
        private Integer UangMakan;
        private Integer UangTransport;
        private String Status;
        public Golongan(String ID,String Nama,Integer Gaji,Integer Masa,Integer UangMakan,Integer UangTransport,String Status){
            this.ID = ID;
            this.Nama = Nama;
            this.Gaji = Gaji;
            this.Masa = Masa;
            this.UangMakan = UangMakan;
            this.UangTransport = UangTransport;
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

        public Integer getGaji() {
            return Gaji;
        }

        public void setGaji(Integer gaji) {
            Gaji = gaji;
        }

        public Integer getMasa() {
            return Masa;
        }

        public void setMasa(Integer masa) {
            Masa = masa;
        }

        public Integer getUangMakan() {
            return UangMakan;
        }

        public void setUangMakan(Integer uangMakan) {
            UangMakan = uangMakan;
        }

        public Integer getUangTransport() {
            return UangTransport;
        }

        public void setUangTransport(Integer uangTransport) {
            UangTransport = uangTransport;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }
    }
}
