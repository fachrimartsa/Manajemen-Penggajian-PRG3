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
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CRUDKaryawan implements Initializable {
    @FXML
    private Button btnEdit, btnCreate, btnCancel;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField tbID;
    @FXML
    private TextField tbNama;
    @FXML
    private TextField tbEmail;
    @FXML
    private TextField tbTglLahir;
    @FXML
    private TextField tbTglMasuk;
    @FXML
    private ComboBox cbIdJabatan;
    @FXML
    private ComboBox cbIdDivisi;
    @FXML
    private ComboBox cbIdGolongan;
    @FXML
    private ComboBox cbIdShift;
    @FXML
    private TextField tbNoRekening;
    @FXML
    private TextField tbStatus;
    @FXML
    private ComboBox cbJnsKaryawan;
    @FXML
    private TextField tbCari;
    @FXML
    private ImageView btnCari;
    @FXML
    private TableView<CRUDKaryawan> viewKaryawan;
    @FXML
    private TableColumn<CRUDKaryawan, String> cID , cNama, cEmail, cTglLahir, cTglMasuk, cIdJabatan, cIdDivisi, cIdGolongan,
            cIdShift, cNoRekening, cStatus, cJnsKaryawan;

    DBConnect connect = new DBConnect();
    private String IDKaryawan, nama, email, tanggal_lahir, tanggal_masuk, IDJabatan, IDDivisi, IDGolongan, IDShift,
            no_rekening, status, jenis_karyawan;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<CRUDKaryawan> oblist = FXCollections.observableArrayList();
        try {
            connect.stat = connect.conn.createStatement();
            String query = "SELECT * FROM Karyawan";
            connect.result = connect.stat.executeQuery(query);

            while (connect.result.next()) {
                oblist.add(new CRUDKaryawan(
                        connect.result.getString("IDKaryawan"),
                        connect.result.getString("nama"),
                        connect.result.getString("email"),
                        connect.result.getString("tanggal_lahir"),
                        connect.result.getString("tanggal_masuk"),
                        connect.result.getString("IDJabatan"),
                        connect.result.getString("IDDivisi"),
                        connect.result.getString("IDGolongan"),
                        connect.result.getString("IDShift"),
                        connect.result.getString("no_rekening"),
                        connect.result.getString("status"),
                        connect.result.getString("jenis_karyawan")));
            };
            connect.stat.close();
            connect.result.close();
        } catch (Exception exception) {
            System.out.println("Error When Load Data" + exception);
        }
        cID.setCellValueFactory(new PropertyValueFactory<>("IDKaryawan"));
        cNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cTglLahir.setCellValueFactory(new PropertyValueFactory<>("tanggal_lahir"));
        cTglMasuk.setCellValueFactory(new PropertyValueFactory<>("tanggal_masuk"));
        cIdJabatan.setCellValueFactory(new PropertyValueFactory<>("IDJabatan"));
        cIdDivisi.setCellValueFactory(new PropertyValueFactory<>("IDDivisi"));
        cIdGolongan.setCellValueFactory(new PropertyValueFactory<>("IDGolongan"));
        cIdShift.setCellValueFactory(new PropertyValueFactory<>("IDShift"));
        cNoRekening.setCellValueFactory(new PropertyValueFactory<>("no_rekening"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        cJnsKaryawan.setCellValueFactory(new PropertyValueFactory<>("jenis_karyawan"));
        viewKaryawan.setItems(oblist);

        cbJnsKaryawan.getItems().addAll("Tetap","Kontrak");
    }

    public CRUDKaryawan() {
    }

    public CRUDKaryawan(String IDKaryawan, String nama, String email,String tanggal_lahir,String tanggal_masuk,String IDJabatan,String IDDivisi,String IDGolongan,String IDShift,
                          String no_rekening,String status,String jenis_karyawan) {
        this.IDKaryawan = IDKaryawan;
        this.nama = nama;
        this.email = email;
        this.tanggal_lahir = tanggal_lahir;
        this.tanggal_masuk = tanggal_masuk;
        this.IDJabatan = IDJabatan;
        this.IDDivisi = IDDivisi;
        this.IDGolongan = IDGolongan;
        this.IDShift = IDShift;
        this.no_rekening = no_rekening;
        this.status = status;
        this.jenis_karyawan = jenis_karyawan;
    }

    public String getIDKaryawan() {
        return IDKaryawan;
    }

    public void setIDKaryawan(String IDKaryawan) {
        this.IDKaryawan = IDKaryawan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getTanggal_masuk() {
        return tanggal_masuk;
    }

    public void setTanggal_masuk(String tanggal_masuk) {
        this.tanggal_masuk = tanggal_masuk;
    }

    public String getIDJabatan() {
        return IDJabatan;
    }

    public void setIDJabatan(String IDJabatan) {
        this.IDJabatan = IDJabatan;
    }

    public String getIDDivisi() {
        return IDDivisi;
    }

    public void setIDDivisi(String IDDivisi) {
        this.IDDivisi = IDDivisi;
    }

    public String getIDGolongan() {
        return IDGolongan;
    }

    public void setIDGolongan(String IDGolongan) {
        this.IDGolongan = IDGolongan;
    }

    public String getIDShift() {
        return IDShift;
    }

    public void setIDShift(String IDShift) {
        this.IDShift = IDShift;
    }

    public String getNo_rekening() {
        return no_rekening;
    }

    public void setNo_rekening(String no_rekening) {
        this.no_rekening = no_rekening;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJenis_karyawan() {
        return jenis_karyawan;
    }

    public void setJenis_karyawan(String jenis_karyawan) {
        this.jenis_karyawan = jenis_karyawan;
    }

    private String generateID() throws SQLException {
        String prefix = "KRY";
        String query = "SELECT COUNT(*) FROM karyawan";
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
        btnSave.setDisable(false);
        tbID.setDisable(false);
        tbNama.setDisable(false);
        tbEmail.setDisable(false);
        tbTglLahir.setDisable(false);
        tbTglMasuk.setDisable(false);
        cbIdDivisi.setDisable(false);
        cbIdGolongan.setDisable(false);
        cbIdShift.setDisable(false);
        cbIdJabatan.setDisable(false);
        tbNoRekening.setDisable(false);
        tbStatus.setDisable(false);
        cbJnsKaryawan.setDisable(false);
        btnCancel.setDisable(false);

        // Set ID Karyawan dan Status Karyawan
        tbID.setText(generateID());
        tbStatus.setText("Active");

        // Data Combo Box
        loadJabatanData();
        loadDivisiData();
        loadGolonganData();
        loadShiftData();
    }

    @FXML
    private void btnEditClick() {
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
        tbID.setDisable(false);
        tbNama.setDisable(false);
        tbEmail.setDisable(false);
        tbTglLahir.setDisable(false);
        tbTglMasuk.setDisable(false);
        cbIdDivisi.setDisable(false);
        cbIdGolongan.setDisable(false);
        cbIdShift.setDisable(false);
        cbIdJabatan.setDisable(false);
        tbNoRekening.setDisable(false);
        tbStatus.setDisable(false);
        cbJnsKaryawan.setDisable(false);
        btnCancel.setDisable(false);
    }
    
    @FXML
    private void btnCancelClick() {
        tbID.setDisable(true);
        tbNama.setDisable(true);
        tbEmail.setDisable(true);
        tbTglLahir.setDisable(true);
        tbTglMasuk.setDisable(true);
        cbIdJabatan.setDisable(true);
        cbIdDivisi.setDisable(true);
        cbIdGolongan.setDisable(true);
        cbIdShift.setDisable(true);
        tbNoRekening.setDisable(true);
        tbStatus.setDisable(true);
        cbJnsKaryawan.setDisable(true);
        btnSave.setDisable(true);
        btnCancel.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    private void loadJabatanData() {
        ObservableList<String> jabatanList = FXCollections.observableArrayList();
        try {
            connect.stat = connect.conn.createStatement();
            String query = "SELECT IDJabatan FROM Jabatan";
            connect.result = connect.stat.executeQuery(query);

            while (connect.result.next()) {
                jabatanList.add(connect.result.getString("IDJabatan"));
            }

            cbIdJabatan.setItems(jabatanList);
            connect.stat.close();
            connect.result.close();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Could not load jabatan data");
            alert.setContentText("An error occurred while loading jabatan data from the database: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void loadDivisiData() {
        ObservableList<String> divisiList = FXCollections.observableArrayList();
        try {
            connect.stat = connect.conn.createStatement();
            String query = "SELECT IDDivisi FROM Divisi";
            connect.result = connect.stat.executeQuery(query);

            while (connect.result.next()) {
                divisiList.add(connect.result.getString("IDDivisi"));
            }

            cbIdDivisi.setItems(divisiList);
            connect.stat.close();
            connect.result.close();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Could not load divisi data");
            alert.setContentText("An error occurred while loading divisi data from the database: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void loadGolonganData() {
        ObservableList<String> golonganList = FXCollections.observableArrayList();
        try {
            connect.stat = connect.conn.createStatement();
            String query = "SELECT IDGolongan FROM Golongan";
            connect.result = connect.stat.executeQuery(query);

            while (connect.result.next()) {
                golonganList.add(connect.result.getString("IDGolongan"));
            }

            cbIdGolongan.setItems(golonganList);
            connect.stat.close();
            connect.result.close();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Could not load golongan data");
            alert.setContentText("An error occurred while loading golongan data from the database: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void loadShiftData() {
        ObservableList<String> shiftList = FXCollections.observableArrayList();
        try {
            connect.stat = connect.conn.createStatement();
            String query = "SELECT IDShift FROM Shift";
            connect.result = connect.stat.executeQuery(query);

            while (connect.result.next()) {
                shiftList.add(connect.result.getString("IDShift"));
            }

            cbIdShift.setItems(shiftList);
            connect.stat.close();
            connect.result.close();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Could not load shift data");
            alert.setContentText("An error occurred while loading shift data from the database: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void btnSaveClick() {
        setIDKaryawan(tbID.getText());
        setNama(tbNama.getText());
        setEmail(tbEmail.getText());
        setTanggal_lahir(tbTglLahir.getText());
        setTanggal_masuk(tbTglMasuk.getText());
        setIDJabatan((String) cbIdJabatan.getValue());
        setIDDivisi((String) cbIdDivisi.getValue());
        setIDGolongan((String) cbIdGolongan.getValue());
        setIDShift((String) cbIdShift.getValue());
        setNo_rekening(tbNoRekening.getText());
        setStatus(tbStatus.getText());
        setJenis_karyawan((String) cbJnsKaryawan.getValue());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        if (tbID.getText().isEmpty() || tbNama.getText().isEmpty() || tbEmail.getText().isEmpty() ||
                tbTglLahir.getText().isEmpty() || tbTglMasuk.getText().isEmpty() || cbIdJabatan.getValue() == null ||
                cbIdDivisi.getValue() == null || cbIdGolongan.getValue() == null || cbIdShift.getValue() == null ||
                tbNoRekening.getText().isEmpty() || tbStatus.getText().isEmpty() || cbJnsKaryawan.getValue() == null) {

            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("All fields must be filled");
            alert.showAndWait();
        } else {
            try {
                String sql = "{CALL sp_InsertKaryawan(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
                connect.pstat = connect.conn.prepareStatement(sql);
                connect.pstat.setString(1, getIDKaryawan());
                connect.pstat.setString(2, getNama());
                connect.pstat.setString(3, getEmail());
                connect.pstat.setString(4, getTanggal_lahir());
                connect.pstat.setString(5, getTanggal_masuk());
                connect.pstat.setString(6, getIDJabatan());
                connect.pstat.setString(7, getIDDivisi());
                connect.pstat.setString(8, getIDGolongan());
                connect.pstat.setString(9, getIDShift());
                connect.pstat.setString(10, getNo_rekening());
                connect.pstat.setString(11, getStatus());
                connect.pstat.setString(12, getJenis_karyawan());
                connect.pstat.executeUpdate();
                connect.pstat.close();

                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText("Save Data Successfully");
                alert.show();

                // Disable form inputs after saving
                tbID.setDisable(true);
                tbNama.setDisable(true);
                tbEmail.setDisable(true);
                tbTglLahir.setDisable(true);
                tbTglMasuk.setDisable(true);
                cbIdJabatan.setDisable(true);
                cbIdDivisi.setDisable(true);
                cbIdGolongan.setDisable(true);
                cbIdShift.setDisable(true);
                tbNoRekening.setDisable(true);
                tbStatus.setDisable(true);
                cbJnsKaryawan.setDisable(true);
                btnSave.setDisable(true);
                btnCancel.setDisable(true);

            } catch (SQLException ex) {
                ex.printStackTrace();
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Error : " + ex);
                alert.show();
            }
        }
    }

    @FXML
    private void rowTableClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            CRUDKaryawan selectedKaryawan = viewKaryawan.getSelectionModel().getSelectedItem();
            if (selectedKaryawan != null) {
                tbID.setText(selectedKaryawan.getIDKaryawan());
                tbNama.setText(selectedKaryawan.getNama());
                tbEmail.setText(selectedKaryawan.getEmail());
                tbTglLahir.setText(selectedKaryawan.getTanggal_lahir());
                tbTglMasuk.setText(selectedKaryawan.getTanggal_masuk());
                cbIdJabatan.setValue(selectedKaryawan.getIDJabatan());
                cbIdDivisi.setValue(selectedKaryawan.getIDDivisi());
                cbIdGolongan.setValue(selectedKaryawan.getIDGolongan());
                cbIdShift.setValue(selectedKaryawan.getIDShift());
                tbNoRekening.setText(selectedKaryawan.getNo_rekening());
                tbStatus.setText(selectedKaryawan.getStatus());
                cbJnsKaryawan.setValue(selectedKaryawan.getJenis_karyawan());
            }
        }
    }

    @FXML
    private void btnDeleteClick() {
        setIDKaryawan(tbID.getText());
        try {
            String query =  "{CALL sp_DeleteKaryawan(?)}";
            connect.pstat = connect.conn.prepareStatement(query);
            connect.pstat.setString(1,getIDKaryawan());
            connect.pstat.executeUpdate();
            connect.pstat.close();
        } catch (Exception ex) {
            System.out.println("Error When DeletingData : " + ex);
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Delete Data Succesfully");
        alert.show();
        // Disable form inputs after saving
        tbID.setDisable(true);
        tbNama.setDisable(true);
        tbEmail.setDisable(true);
        tbTglLahir.setDisable(true);
        tbTglMasuk.setDisable(true);
        cbIdJabatan.setDisable(true);
        cbIdDivisi.setDisable(true);
        cbIdGolongan.setDisable(true);
        cbIdShift.setDisable(true);
        tbNoRekening.setDisable(true);
        tbStatus.setDisable(true);
        cbJnsKaryawan.setDisable(true);
        btnSave.setDisable(true);
        btnCancel.setDisable(true);
    }

    @FXML
    private void btnUpdateClick() {
        setIDKaryawan(tbID.getText());
        setNama(tbNama.getText());
        setEmail(tbEmail.getText());
        setTanggal_lahir(tbTglLahir.getText());
        setTanggal_masuk(tbTglMasuk.getText());
        setIDJabatan((String) cbIdJabatan.getValue());
        setIDDivisi((String) cbIdDivisi.getValue());
        setIDGolongan((String) cbIdGolongan.getValue());
        setIDShift((String) cbIdShift.getValue());
        setNo_rekening(tbNoRekening.getText());
        setStatus(tbStatus.getText());
        setJenis_karyawan((String) cbJnsKaryawan.getValue());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        try {
            String sql = "{CALL sp_UpdateKaryawan(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            connect.pstat = connect.conn.prepareStatement(sql);
            connect.pstat.setString(1, getIDKaryawan());
            connect.pstat.setString(2, getNama());
            connect.pstat.setString(3, getEmail());
            connect.pstat.setString(4, getTanggal_lahir());
            connect.pstat.setString(5, getTanggal_masuk());
            connect.pstat.setString(6, getIDJabatan());
            connect.pstat.setString(7, getIDDivisi());
            connect.pstat.setString(8, getIDGolongan());
            connect.pstat.setString(9, getIDShift());
            connect.pstat.setString(10, getNo_rekening());
            connect.pstat.setString(11, getStatus());
            connect.pstat.setString(12, getJenis_karyawan());

            int rowsUpdated = connect.pstat.executeUpdate();

            if (rowsUpdated > 0) {
                alert.setContentText("Update Data Succesfully");
                alert.show();
                // Disable form inputs after saving
                tbID.setDisable(true);
                tbNama.setDisable(true);
                tbEmail.setDisable(true);
                tbTglLahir.setDisable(true);
                tbTglMasuk.setDisable(true);
                cbIdJabatan.setDisable(true);
                cbIdDivisi.setDisable(true);
                cbIdGolongan.setDisable(true);
                cbIdShift.setDisable(true);
                tbNoRekening.setDisable(true);
                tbStatus.setDisable(true);
                cbJnsKaryawan.setDisable(true);
                btnSave.setDisable(true);
                btnCancel.setDisable(true);
            } else {
                alert.setContentText("Data Undefined");
                alert.show();
            }

            connect.pstat.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            alert.setContentText("Error When Load Data : " +ex);
            alert.show();
        }
    }

/*    @FXML
    private void btnSearchClick() {
        ObservableList<CRUDTunjangan> oblist = FXCollections.observableArrayList();
        String searchQuery = tbCari.getText();

        try {
            connect.stat = connect.conn.createStatement();
            String sql = "{CALL sp_findAllKaryawan(?)}";
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
    }*/
}
