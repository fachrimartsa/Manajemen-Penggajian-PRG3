package com.example.manajemen_penggajian;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private boolean edit = false;
    @FXML
    private TableView<CRUDGolongan.Golongan> dgvGolongan;
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
    private TextField txtGaji;
    @FXML
    private TextField txtMasa;
    @FXML
    private TextField txtUangmkn;
    @FXML
    private TextField txtUangTransport;
    @FXML
    private TextField txtCari;
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
            Nama.setCellValueFactory(new PropertyValueFactory<>("Nama"));
            Gaji.setCellValueFactory(new PropertyValueFactory<>("Gaji"));
            Masa.setCellValueFactory(new PropertyValueFactory<>("Masa"));
            UangMakan.setCellValueFactory(new PropertyValueFactory<>("UangMakan"));
            UangTransport.setCellValueFactory(new PropertyValueFactory<>("UangTransport"));
            Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
            dgvGolongan.setItems(oblist);

            txtID.setDisable(true);
            txtGaji.setDisable(true);
            txtMasa.setDisable(true);
            txtNama.setDisable(true);
            txtUangmkn.setDisable(true);
            txtUangTransport.setDisable(true);

            dgvGolongan.setOnMouseClicked(mouseEvent -> ClickdgvGolongan());

        }catch (Exception ex){
            System.out.println("Terjadi error saat load data golongan: " + ex);
        }
    }
    @FXML
    protected void ClickdgvGolongan(){
        if(!edit){
            return;
        }
        Golongan selectedGolongan = dgvGolongan.getSelectionModel().getSelectedItem();

        txtGaji.setDisable(false);
        txtMasa.setDisable(false);
        txtNama.setDisable(false);
        txtUangmkn.setDisable(false);
        txtUangTransport.setDisable(false);
        if(selectedGolongan != null){
            txtID.setText(selectedGolongan.getID());
            txtNama.setText(selectedGolongan.getNama());
            txtGaji.setText(String.valueOf(selectedGolongan.getGaji()));
            txtMasa.setText(String.valueOf(selectedGolongan.getMasa()));
            txtUangmkn.setText(String.valueOf(selectedGolongan.getUangMakan()));
            txtUangTransport.setText(String.valueOf(selectedGolongan.getUangTransport()));
        }
    }
    @FXML
    protected void btnSearchClick(){
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
                        rs.getInt("masa_kerja"),
                        rs.getInt("gaji"),
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
            Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
            dgvGolongan.setItems(oblist);
        }catch (Exception ex){
            System.out.println("Error when reload Group data: " + ex);
        }
    }
    @FXML
    protected void btnUpdateClick(){
        if(!edit){
            return;
        }
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
            txtGaji.setDisable(true);
            txtMasa.setDisable(true);
            txtNama.setDisable(true);
            txtUangmkn.setDisable(true);
            txtUangTransport.setDisable(true);

            btnRefreshClick();
        } catch (Exception ex){
            System.out.println("Error when update Group data: " + ex);
        }
    }
    @FXML
    protected void btnDeleteClick(){
        try {
            String id = txtID.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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
        } catch (Exception ex){
            System.out.println("Error when De-Active: " + ex);
        }
    }
    @FXML
    protected void btnEditClick(){
        edit = true;
    }
    @FXML
    protected void btnSaveClick() {
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
