package com.example.manajemen_penggajian;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.sql.*;
import java.util.Optional;

public class CRUDAsuransi {

    private ObservableList<Asuransi> Aslist = FXCollections.observableArrayList();
    @FXML
    private TableView<Asuransi> tbAsuransi;
    @FXML
    private TableColumn<Asuransi, Integer> tcNum;
    @FXML
    private TableColumn<Asuransi, String> tcJAsuransi;
    @FXML
    private TableColumn<Asuransi, String> tcIDGolongan;



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
        addTableListener();
    }

    //========================= METHOD ON ACTION =========================

    //METHOD UNTUK OPEN MENGGUNAKAN TEXTFIELD ASURANSI, EXCEPT SEARCH !!!
    @FXML
    protected void onBtnCreateClick(){
        txtIDAsuransi.setDisable(false);
        txtJAsuransi.setDisable(false);
        cbIDGolongan.setDisable(false);
        btnSave.setDisable(false);
        txtIDAsuransi.setText(autoIDAsuransi());

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

    }
    @FXML
    protected void onBtnTVClick(){
        txtIDAsuransi.setDisable(false);
        txtJAsuransi.setDisable(false);
        cbIDGolongan.setDisable(false);

        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
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
                showErrorAlert(null, "Data Same, Data fail to save");
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

                showInformationAlert("Insert Data Success!");
            }
            rs.close();
            checkStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace(); // Tampilkan trace error ke konsol untuk debugging
            showErrorAlert(ex.getMessage(), "Insert data Fail :\n");
        }
        refreshTable();
        //textfield
        txtIDAsuransi.setDisable(true);
        txtJAsuransi.setDisable(true);
        cbIDGolongan.setDisable(true);
        //button
        btnDelete.setDisable(true);
        btnSave.setDisable(true);
        btnUpdate.setDisable(true);


    }
    @FXML
    protected void onBtnClearClick(){
        txtIDAsuransi.setText("");
        txtJAsuransi.setText("");
        cbIDGolongan.setValue(null);
    }
    @FXML
    protected void onBtnUpdateClick() {
        String IDAsuransi = txtIDAsuransi.getText();
        String Jenis_Asuransi = txtJAsuransi.getText();
        Golongan golongan = cbIDGolongan.getValue(); // Ambil objek Golongan dari ComboBox
        String status = "Active";

        // Pastikan IDGolongan tidak null dan ada dalam database
        if (golongan != null) {
            try {
                // Menggunakan koneksi yang sama dengan kelas DBConnect
                String query = "{call sp_UpdateAsuransi(?, ?, ?, ?)}";
                CONAS.pstat = CONAS.conn.prepareCall(query);
                CONAS.pstat.setString(1, IDAsuransi); // Memastikan IDAsuransi diset terlebih dahulu
                CONAS.pstat.setString(2, Jenis_Asuransi);
                CONAS.pstat.setString(3, golongan.getIDGolongan()); // Mengambil ID Golongan dari objek Golongan
                CONAS.pstat.setString(4, status);
                CONAS.pstat.executeUpdate();
                CONAS.pstat.close();

                showInformationAlert("Update data Success");
            } catch (SQLException ex) {
                ex.printStackTrace(); // Tampilkan trace error ke konsol untuk debugging
                showErrorAlert(ex.getMessage(), "Update data Fail :\n");
            }
        } else {
            showErrorAlert(null, "Golongan Not Valid");
        }
        refreshview();
        refreshTable();
        //textfield
        txtIDAsuransi.setDisable(true);
        txtJAsuransi.setDisable(true);
        cbIDGolongan.setDisable(true);
        //button
        btnDelete.setDisable(true);
        btnSave.setDisable(true);
        btnUpdate.setDisable(true);
    }


    @FXML
    protected void onBtnSearchClick(){
        String searchText = txtSearch.getText().trim(); // Mengambil teks pencarian dan menghapus spasi di sekitarnya

        // Membuat koneksi dan mempersiapkan panggilan stored procedure
        Connection conn = CONAS.getConnection(); // Ganti CONAS.getConnection() sesuai dengan cara Anda mendapatkan koneksi
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            // Panggil stored procedure dengan parameter searchText
            stmt = conn.prepareCall("{call sp_CariAsuransi(?)}");
            stmt.setString(1, searchText);

            // Eksekusi query
            rs = stmt.executeQuery();

            // Bersihkan data sebelum memuat hasil pencarian baru
            tbAsuransi.getItems().clear(); // Menghapus item yang ada di dalam TableView

            // Memuat hasil pencarian ke dalam TableView
            while (rs.next()) {
                Asuransi asuransi = new Asuransi(
                        rs.getString("IDAsuransi"),
                        rs.getString("Jenis_Asuransi"),
                        rs.getString("IDGolongan"),
                        rs.getString("status")
                );
                tbAsuransi.getItems().add(asuransi); // Menambahkan asuransi ke dalam TableView
            }

        }catch (SQLException ex){
            ex.printStackTrace();
            showErrorAlert(ex.getMessage(), "Error searching data:");
        }
        refreshview();

    }

    @FXML
    protected void onBtnDeleteClick() {
        String IDAsuransi = txtIDAsuransi.getText();
        String status = "Non Active";

        // Membuat alert konfirmasi
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation for De-Active");
        alert.setHeaderText("Are you sure to De-Active this data?");
        alert.setContentText("Select 'OK' to De-Active or 'Cancel' to cancel.");

        // Menambahkan tombol OK dan Cancel
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, cancelButton);

        // Menunggu respons pengguna
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == okButton) {
            // Pengguna mengklik OK, maka lanjutkan menonaktifkan data
            try {
                // Menggunakan koneksi yang sama dengan kelas DBConnect
                String query = "{call sp_DeleteAsuransi(?, ?)}";
                CONAS.pstat = CONAS.conn.prepareStatement(query);
                CONAS.pstat.setString(1, IDAsuransi);
                CONAS.pstat.setString(2, status);
                CONAS.pstat.executeUpdate();
                CONAS.pstat.close();

                showInformationAlert("Data berhasil di-nonaktifkan");
            } catch (SQLException ex) {
                ex.printStackTrace(); // Tampilkan trace error ke konsol untuk debugging
                showErrorAlert(ex.getMessage(),"Deactive data Fail :" );
            }
        } else {
            // Pengguna mengklik Cancel, tindakan dibatalkan
            showInformationAlert("Disabled action cancelled");
        }
        refreshTable();
        //textfield
        txtIDAsuransi.setDisable(true);
        txtJAsuransi.setDisable(true);
        cbIDGolongan.setDisable(true);
        //button
        btnDelete.setDisable(true);
        btnSave.setDisable(true);
        btnUpdate.setDisable(true);
    }



    //============================= METHOD ===========================

    private void addTableListener() {
        tbAsuransi.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });
    }
    private void populateFields(Asuransi asur) {
        txtIDAsuransi.setText(asur.getIDAsu());
        txtJAsuransi.setText(asur.getJAsu());
        Golongan selectedGolongan = findGolonganById(asur.getIDGo());// Mengambil objek Golongan dari ComboBox berdasarkan IDGolongan yang ada pada Asuransi
        cbIDGolongan.setValue(selectedGolongan);

    }

    // Method untuk mencari objek Golongan berdasarkan IDGolongan
    private Golongan findGolonganById(String idGolongan) {
        for (Golongan golongan : cbIDGolongan.getItems()) {
            if (golongan.getIDGolongan().equals(idGolongan)) {
                return golongan;
            }
        }
        return null; // Mengembalikan null jika tidak ditemukan
    }
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
            showErrorAlert(ex.getMessage(), "ERROR:\n");
        }
        refreshview();
    }

    private void refreshTable() {
        // Simulate refreshing data (replace with actual data refreshing logic)
        Aslist.clear(); // Clear existing data
        // Load new data (for demonstration, load the same initial data)
        loadviewtable();
    }


    //REFRESH TABLE VIEW
    private void refreshview(){
        // Menampilkan data ke dalam TableView
        tcNum.setCellValueFactory(cellData -> new SimpleIntegerProperty(tbAsuransi.getItems().indexOf(cellData.getValue())+1).asObject());
        tcJAsuransi.setCellValueFactory(new PropertyValueFactory<>("JAsu"));
        tcIDGolongan.setCellValueFactory(new PropertyValueFactory<>("IDGo"));
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
            showErrorAlert(e.getMessage(), "ERROR:\n");
        }
    }

    private String autoIDAsuransi() {
        String nextID = "AS000"; // Nilai default

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

    //===================================== VALIDASI ============================================== SHOW ERROR ==========
    public void showErrorAlert(String message, String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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