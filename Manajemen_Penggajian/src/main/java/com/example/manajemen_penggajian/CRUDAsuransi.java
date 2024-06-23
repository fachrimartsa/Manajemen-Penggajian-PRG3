package com.example.manajemen_penggajian;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CRUDAsuransi {

    private ObservableList<Asuransi> Aslist = FXCollections.observableArrayList();
    @FXML
    private TableView<Asuransi> tbAsuransi;
    @FXML
    private TableColumn<Asuransi, String> tcIDAsuransi;
    @FXML
    private TableColumn<Asuransi, String> tcJAsuransi;
    @FXML
    private TableColumn<Asuransi, String> tcIDGolongan;
    @FXML
    private TableColumn<Asuransi, String> tcStatus;


    @FXML
    private TextField txtSearch;
    @FXML
    private TextField txtIDAsuransi;
    @FXML
    private TextField txtJAsuransi;
    @FXML
    private TextField txtStatus;
    @FXML
    private ComboBox<Golongan> cbIDGolongan;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnDelete;

    String IDAsuransi, JAsuransi, status;
    Golongan  IDGolongan;
    DBConnect CONAS = new DBConnect();


    @FXML
    public void initialize() {
        // Inisialisasi ComboBox dengan data dari database
        loadDataGolongan();
        loadviewtable();
    }

    //========================= METHOD ON ACTION =========================

    //METHOD UNTUK OPEN MENGGUNAKAN TEXTFIELD ASURANSI, EXCEPT SEARCH !!!
    @FXML
    protected void onBtnCreateClick(){
        txtIDAsuransi.setDisable(false);
        txtJAsuransi.setDisable(false);
        cbIDGolongan.setDisable(false);
        txtIDAsuransi.setText(autoIDAsuransi());

    }
    @FXML
    protected void onBtnRefreshClick(){
        refreshview();
    }

    //METHOD SAVE DATA
    @FXML
    protected void onBtnSaveClick() {
        IDAsuransi = txtIDAsuransi.getText();
        JAsuransi = txtJAsuransi.getText();
        IDGolongan = cbIDGolongan.getValue();
        status = "Active";

        try {
            // Membuat koneksi dan callable statement untuk memeriksa keberadaan jenis asuransi
            String checkQuery = "SELECT COUNT(*) FROM asuransi WHERE LOWER(Jenis_Asuransi) = ?";
            PreparedStatement checkStmt = CONAS.conn.prepareStatement(checkQuery);
            checkStmt.setString(1, JAsuransi);
            ResultSet rs = checkStmt.executeQuery();

            // Memeriksa apakah jenis asuransi sudah ada
            rs.next(); // Pindah ke baris pertama hasil
            int count = rs.getInt(1); // Ambil nilai COUNT(*)

            if (count > 0) {
                JOptionPane.showMessageDialog(null, "Data Same, Data fail to save");
            } else {
                // Membuat koneksi dan callable statement untuk menyimpan data baru
                CONAS.pstat = CONAS.conn.prepareCall("{call sp_CreateAsuransi(?, ?, ?, ?)}");
                CONAS.pstat.setString(1, IDAsuransi);
                CONAS.pstat.setString(2, txtJAsuransi.getText());
                CONAS.pstat.setString(3, IDGolongan.getIDGolongan()); // Mengambil ID Golongan dari objek Golongan
                CONAS.pstat.setString(4, status);

                // Eksekusi stored procedure
                CONAS.pstat.executeUpdate();
                CONAS.pstat.close();

                JOptionPane.showMessageDialog(null, "Insert Data Success!");
            }
            rs.close();
            checkStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace(); // Tampilkan trace error ke konsol untuk debugging
            JOptionPane.showMessageDialog(null, "Insert data Fail :\n" + ex.getMessage());
        }
        txtIDAsuransi.setDisable(true);
        txtJAsuransi.setDisable(true);
        cbIDGolongan.setDisable(true);
    }
    @FXML
    protected void onBtnClearClick(){
        txtIDAsuransi.setText("");
        txtJAsuransi.setText("");
        cbIDGolongan.setValue(null);
        txtStatus.setText("");
    }


    //============================= METHOD ===========================

    //VIEW TABLE
    private void loadviewtable() {
        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM asuransi WHERE status ='Active'";
            connection.result = connection.stat.executeQuery(query);
            while (connection.result.next()) {
                Aslist.add(new Asuransi(
                        connection.result.getString("IDAsuransi"),
                        connection.result.getString("Jenis_Asuransi"),
                        connection.result.getString("IDGolongan"),
                        connection.result.getString("status"))
                );
            }
            connection.stat.close();
            connection.result.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR:\n" + ex.getMessage());
        }
        refreshview();
    }
    //REFRESH TABLE VIEW
    private void refreshview(){
        // Menampilkan data ke dalam TableView
        tcIDAsuransi.setCellValueFactory(new PropertyValueFactory<>("IDAsu"));
        tcJAsuransi.setCellValueFactory(new PropertyValueFactory<>("JAsu"));
        tcIDGolongan.setCellValueFactory(new PropertyValueFactory<>("IDGo"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("stat"));
        tbAsuransi.setItems(Aslist);
    }


    //method COMBO BOX IDGOLONGAN
    private void loadDataGolongan() {
        String query = "SELECT IDGolongan, nama FROM golongan";

        try (Connection connection = new DBConnect().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            ObservableList<Golongan> observableGolonganNames = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String idGolongan = resultSet.getString("IDGolongan");
                String nama = resultSet.getString("nama");

                Golongan golongan = new Golongan(idGolongan, nama);
                observableGolonganNames.add(golongan);
            }
            cbIDGolongan.setItems(observableGolonganNames); // Mengatur items ComboBox dengan nama-nama Golongan
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String autoIDAsuransi() {
        String nextID = "AS001"; // Nilai default

        try {
            Connection  connection = CONAS.getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call sp_maxID}");
            ResultSet rs = callableStatement.executeQuery();

            // Ambil hasil dari stored procedure
            if (rs.next()) {
                nextID = rs.getString("HighestIdSupplier");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Menghasilkan format ID berikutnya
        return generateNextID(nextID);
    }

    private String generateNextID(String currentID) {
        // Memproses untuk menghasilkan ID berikutnya dari ID terakhir yang didapatkan
        String prefix = currentID.substring(0, 2); // Mengambil "AS"
        int sequence = Integer.parseInt(currentID.substring(2)); // Mengambil nomor urutan
        sequence++; // Menambah urutan

        // Menghasilkan ID berikutnya dengan format "AS" diikuti nomor urutan yang diubah kembali menjadi string
        return prefix + String.format("%03d", sequence); // Format untuk mendapatkan 3 digit nomor
    }

    //======================================= ENCAPSULATED ================================

    public class Asuransi {
        String IDAsu, JAsu, IDGo, stat;
        public Asuransi(String IDAsu, String JAsu, String IDGo, String stat) {
            this.IDAsu = IDAsu;
            this.JAsu = JAsu;
            this.IDGo = IDGo;
            this.stat = stat;
        }
        public String getIDAsu() {
            return IDAsu;
        }
        public String getJAsu() {
            return JAsu;
        }
        public String getIDGo() {
            return IDGo;
        }
        public String getStat() {
            return stat;
        }
    }

    public class Golongan {
        String IDGolongan;
        String nama;

        public Golongan(String IDGolongan, String nama) {
            this.IDGolongan = IDGolongan;
            this.nama = nama;
        }

        public String getIDGolongan() {
            return IDGolongan;
        }

        public String getNama() {
            return nama;
        }

        @Override
        public String toString() {
            return nama; // Mengatur cara ComboBox menampilkan nama golongan
        }
    }


}
