package com.example.manajemen_penggajian;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class CRUDKaryawan {
    @FXML
    private Button btnEdit, btnCreate, btnSave, btnUpdate, btnDelete, btnCancel;
    @FXML
    private TextField tbSearch, tbId, tbNama, tbEmail, tbPassword, tbNoRekening;
    @FXML
    private DatePicker tbTanggalLahir, tbTanggalMasuk;
    @FXML
    private ComboBox cbJnsKaryawan;
    @FXML
    private ComboBox<Jabatan> cbJabatan;
    @FXML
    private ComboBox<Divisi> cbDivisi;
    @FXML
    private ComboBox<Golongan> cbGolongan;
    @FXML
    private ComboBox<Shift> cbShift;
    @FXML
    private TableView<CRUDKaryawan> viewKaryawan;
    @FXML
    private TableColumn<CRUDTunjangan, String> columnNama;
    @FXML
    private TableColumn<CRUDTunjangan, String> columnEmail;
    @FXML
    private TableColumn<CRUDTunjangan, String> columnPass;
    @FXML
    private TableColumn<CRUDTunjangan, String> columnTglLahir;
    @FXML
    private TableColumn<CRUDTunjangan, String> columnTglMasuk;
    @FXML
    private TableColumn<CRUDTunjangan, String> columnNoRekening;
    @FXML
    private TableColumn<CRUDTunjangan, String> columnDivisi;
    @FXML
    private TableColumn<CRUDTunjangan, String> columnJabatan;
    @FXML
    private TableColumn<CRUDTunjangan, String> columnGolongan;
    @FXML
    private TableColumn<CRUDTunjangan, String> columnShift;
    @FXML
    private TableColumn<CRUDTunjangan, String> columnJnsKaryawan;

    private ObservableList<CRUDKaryawan> oblist = FXCollections.observableArrayList();


    DBConnect connect = new DBConnect();

    private String IDKaryawan;
    private String nama;
    private String email;
    private String tanggal_lahir;
    private String tanggal_masuk;
    private String IDJabatan;
    private String IDDivisi;
    private String IDGolongan;
    private String IDShift;
    private String no_rekening;
    private String status;
    private String jenis_karyawan;
    private String password;

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

    public String getTanggalLahir() {
        return tanggal_lahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggal_lahir = tanggalLahir;
    }

    public String getTanggalMasuk() {
        return tanggal_masuk;
    }

    public void setTanggalMasuk(String tanggalMasuk) {
        this.tanggal_masuk = tanggalMasuk;
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

    public String getNoRekening() {
        return no_rekening;
    }

    public void setNoRekening(String noRekening) {
        this.no_rekening = noRekening;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJenisKaryawan() {
        return jenis_karyawan;
    }

    public void setJenisKaryawan(String jenisKaryawan) {
        this.jenis_karyawan = jenisKaryawan;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CRUDKaryawan() {

    }

    public CRUDKaryawan(String IDKaryawan, String nama, String email, String tanggal_lahir, String tanggal_masuk,
                        String IDJabatan, String IDDivisi, String IDGolongan, String IDShift, String no_rekening,
                        String status, String jenis_karyawan, String password) {
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
        this.password = password;
    }

    public class Jabatan {
        private String id;
        private String name;

        public Jabatan(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getIdJabatan() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name; // This will be used by ComboBox to display the name
        }
    }

    public class Golongan {
        private String id;
        private String name;

        public Golongan(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getIdGolongan() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name; // This will be used by ComboBox to display the name
        }
    }

    public class Divisi {
        private String id;
        private String name;

        public Divisi(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getIdDivisi() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name; // This will be used by ComboBox to display the name
        }
    }

    public class Shift {
        private String id;
        private String name;

        public Shift(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getIdShift() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name; // This will be used by ComboBox to display the name
        }
    }

    public String displayJabatanById(String idJabatan) {
        String namaJabatan = "";
        try {
            String query = "SELECT nama FROM Jabatan WHERE IDJabatan = ?";
            connect.pstat = connect.conn.prepareStatement(query);
            connect.pstat.setString(1, idJabatan);
            connect.result = connect.pstat.executeQuery();

            if (connect.result.next()) {
                namaJabatan = connect.result.getString("nama");
            }
        } catch (Exception e) {
            System.out.println("Terjadi error saat mengambil nama jabatan: " + e);
        }
        return namaJabatan;
    }



    public String displayDivisiById(String id) throws SQLException {
        String namaDivisi = null;
        String query = "SELECT nama FROM Divisi WHERE IDDivisi";

        connect.pstat = connect.conn.prepareStatement(query);
        try {
            connect.pstat.setString(1, id);
            connect.result = connect.pstat.executeQuery();

            if (connect.result.next()) {
                namaDivisi = connect.result.getString("nama");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving Divisi: " + e.getMessage());
        }

        return namaDivisi;
    }

    public String displayGolonganById(String id) throws SQLException {
        String namaGolongan = null;
        String query = "SELECT nama FROM Golongan WHERE IDGolongan = ?";

        connect.pstat = connect.conn.prepareStatement(query);
        try {
            connect.pstat.setString(1, id);
            connect.result = connect.pstat.executeQuery();

            if (connect.result.next()) {
                namaGolongan = connect.result.getString("nama");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving Divsi: " + e.getMessage());
        }

        return namaGolongan;
    }

    public String displayShiftById(String id) throws SQLException {
        String jenisShift = null;
        String query = "SELECT jenis_shift FROM Shift WHERE IDShift = ?";

        connect.pstat = connect.conn.prepareStatement(query);
        try {
            connect.pstat.setString(1, id);
            connect.result = connect.pstat.executeQuery();

            if (connect.result.next()) {
                jenisShift = connect.result.getString("jenis_shift");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving Shift: " + e.getMessage());
        }

        return jenisShift;
    }

    public void refreshDataSet() {
        ArrayList<CRUDKaryawan> tempList = new ArrayList<>();
        try {
            connect.stat = connect.conn.createStatement();
            String query = "SELECT * FROM karyawan";
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
                        connect.result.getString("jenis_karyawan"),
                        connect.result.getString("password")));
            }

            for (CRUDKaryawan karyawan : tempList) {
                String idJabatan = karyawan.getIDJabatan();
                String namaJabatan = "";

                if (namaJabatan == null) {
                    namaJabatan = displayJabatanById(idJabatan);
                }

                oblist.add(karyawan);
            }
        } catch (SQLException e) {
            System.out.println("Error When Load Data: " + e.getMessage());
        }

        // Set cell value factories after populating oblist
        columnNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnPass.setCellValueFactory(new PropertyValueFactory<>("password"));
        columnTglLahir.setCellValueFactory(new PropertyValueFactory<>("tanggalLahir"));
        columnTglMasuk.setCellValueFactory(new PropertyValueFactory<>("tanggalMasuk"));
        columnJabatan.setCellValueFactory(new PropertyValueFactory<>("IDJabatan"));
        columnDivisi.setCellValueFactory(new PropertyValueFactory<>("IDDivisi"));
        columnGolongan.setCellValueFactory(new PropertyValueFactory<>("IDGolongan"));
        columnShift.setCellValueFactory(new PropertyValueFactory<>("IDShift"));
        columnNoRekening.setCellValueFactory(new PropertyValueFactory<>("noRekening"));
        columnJnsKaryawan.setCellValueFactory(new PropertyValueFactory<>("jenisKaryawan"));

        viewKaryawan.setItems(oblist);
    }

    public void Clear() {
        tbId.setText("");
        tbNama.setText("");
        tbEmail.setText("");
        tbPassword.setText("");
        tbTanggalLahir.setValue(null);
        tbTanggalMasuk.setValue(null);
        tbNoRekening.setText("");
        cbDivisi.setValue(null);
        cbJabatan.setValue(null);
        cbGolongan.setValue(null);
        cbShift.setValue(null);
        cbJnsKaryawan.setValue(null);
    }

    public void initialize() throws SQLException {
        refreshDataSet();

        // Memanggil FK Jabatan Untuk Combo Box
        ObservableList<Jabatan> jabatanList = FXCollections.observableArrayList();
        connect.conn = connect.getConnection();
        connect.stat = connect.conn.createStatement();
        String query = "SELECT * FROM Jabatan";
        connect.result = connect.stat.executeQuery(query);
        while (connect.result.next()) {
            String id = connect.result.getString("IDJabatan");
            String nama = connect.result.getString("nama");
            jabatanList.add(new Jabatan(id, nama));
        }
        cbJabatan.setItems(jabatanList);
        connect.stat.close();
        connect.result.close();
        // Memanggil FK Divisi Untuk Combo Box
        ObservableList<Divisi> divisiList = FXCollections.observableArrayList();
        connect.conn = connect.getConnection();
        connect.stat = connect.conn.createStatement();
        String query2 = "SELECT * FROM Divisi";
        connect.result = connect.stat.executeQuery(query2);
        while (connect.result.next()) {
            String id = connect.result.getString("IDDivisi");
            String nama = connect.result.getString("nama");
            divisiList.add(new Divisi(id, nama));
        }
        cbDivisi.setItems(divisiList);
        connect.stat.close();
        connect.result.close();
        // Memanggil FK Golongan Untuk Combo Box
        ObservableList<Golongan> golonganList = FXCollections.observableArrayList();
        connect.conn = connect.getConnection();
        connect.stat = connect.conn.createStatement();
        String query3 = "SELECT * FROM Golongan";
        connect.result = connect.stat.executeQuery(query3);
        while (connect.result.next()) {
            String id = connect.result.getString("IDGolongan");
            String nama = connect.result.getString("nama");
            golonganList.add(new Golongan(id, nama));
        }
        cbGolongan.setItems(golonganList);
        connect.stat.close();
        connect.result.close();
        // Memanggil FK Shift Untuk Combo Box
        ObservableList<Shift> shiftList = FXCollections.observableArrayList();
        connect.conn = connect.getConnection();
        connect.stat = connect.conn.createStatement();
        String query4 = "SELECT * FROM Shift";
        connect.result = connect.stat.executeQuery(query4);
        while (connect.result.next()) {
            String id = connect.result.getString("IDShift");
            String nama = connect.result.getString("jenis_shift");
            shiftList.add(new Shift(id, nama));
        }
        cbShift.setItems(shiftList);
        connect.stat.close();
        connect.result.close();


        // Combo Box Jenis Karyawan
        ObservableList<String> jenis_karyawan = FXCollections.observableArrayList();
        jenis_karyawan.add("Tetap");
        jenis_karyawan.add("Kontrak");
        cbJnsKaryawan.setItems(jenis_karyawan);
    }

    @FXML
    private void btnCreateClick() throws SQLException {
        tbId.setDisable(false);
        tbNama.setDisable(false);
        tbEmail.setDisable(false);
        tbPassword.setDisable(false);
        tbTanggalLahir.setDisable(false);
        tbTanggalMasuk.setDisable(false);
        tbNoRekening.setDisable(false);
        cbDivisi.setDisable(false);
        cbJabatan.setDisable(false);
        cbGolongan.setDisable(false);
        cbShift.setDisable(false);
        cbJnsKaryawan.setDisable(false);
        btnSave.setDisable(false);
        btnCancel.setDisable(false);

        // Mendapatkan ID Karyawan baru dari fungsi SQL Server
        String getIdQuery = "SELECT dbo.GenerateKaryawanID() AS newID";
        connect.pstat = connect.conn.prepareStatement(getIdQuery);
        connect.result = connect.pstat.executeQuery();
        if (connect.result.next()) {
            tbId.setText(connect.result.getString("newID"));
        }
        connect.result.close();
        connect.pstat.close();

        // Menetapkan status active
        setStatus("Active");
    }

    @FXML
    private void btnEditClick() {
        tbId.setDisable(false);
        tbNama.setDisable(false);
        tbEmail.setDisable(false);
        tbPassword.setDisable(false);
        tbTanggalLahir.setDisable(false);
        tbTanggalMasuk.setDisable(false);
        tbNoRekening.setDisable(false);
        cbDivisi.setDisable(false);
        cbJabatan.setDisable(false);
        cbGolongan.setDisable(false);
        cbShift.setDisable(false);
        cbJnsKaryawan.setDisable(false);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
        btnCancel.setDisable(false);
    }

    @FXML
    private void btnCancelClick() {
        tbId.setDisable(true);
        tbNama.setDisable(true);
        tbEmail.setDisable(true);
        tbPassword.setDisable(true);
        tbTanggalLahir.setDisable(true);
        tbTanggalMasuk.setDisable(true);
        tbNoRekening.setDisable(true);
        cbDivisi.setDisable(true);
        cbJabatan.setDisable(true);
        cbGolongan.setDisable(true);
        cbShift.setDisable(true);
        cbJnsKaryawan.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnCancel.setDisable(true);
        btnSave.setDisable(true);
    }

    @FXML
    private void btnSaveClick() {
        Jabatan selectedJabatan = cbJabatan.getValue();
        Divisi selectedDivisi = cbDivisi.getValue();
        Golongan selectedGolongan = cbGolongan.getValue();
        Shift selectedShift = cbShift.getValue();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        if (tbId.getText().isEmpty() || tbNama.getText().isEmpty() || tbEmail.getText().isEmpty() ||
                tbTanggalLahir.getValue() == null || tbTanggalMasuk.getValue() == null || cbJabatan.getValue() == null ||
                cbDivisi.getValue() == null || cbGolongan.getValue() == null || cbShift.getValue() == null ||
                tbNoRekening.getText().isEmpty() || cbJnsKaryawan.getValue() == null) {

            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("All fields must be filled");
            alert.showAndWait();
        } else {
            setIDKaryawan(tbId.getText());
            setNama(tbNama.getText());
            setEmail(tbEmail.getText());
            setPassword(tbPassword.getText());

            LocalDate tanggalLahir = tbTanggalLahir.getValue().plusDays(2);
            LocalDate tanggalMasuk = tbTanggalMasuk.getValue().plusDays(2);

            // Set nilai ke setter
            setTanggalLahir(tanggalLahir.toString()); // Ubah ke string jika setter membutuhkan String
            setTanggalMasuk(tanggalMasuk.toString());

            setIDJabatan(selectedJabatan.getIdJabatan()); // Use getValue() to get selected item
            setIDDivisi(selectedDivisi.getIdDivisi());
            setIDGolongan(selectedGolongan.getIdGolongan());
            setIDShift(selectedShift.getIdShift());
            setNoRekening(tbNoRekening.getText());
            setStatus("Active");
            setJenisKaryawan((String) cbJnsKaryawan.getValue());

            try {
                String sql = "{CALL sp_InsertKaryawan(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
                connect.pstat = connect.conn.prepareStatement(sql);
                connect.pstat.setString(1, getIDKaryawan());
                connect.pstat.setString(2, getNama());
                connect.pstat.setString(3, getEmail());
                connect.pstat.setString(4, getTanggalLahir());
                connect.pstat.setString(5, getTanggalMasuk());
                connect.pstat.setString(6, getIDJabatan());
                connect.pstat.setString(7, getIDDivisi());
                connect.pstat.setString(8, getIDGolongan());
                connect.pstat.setString(9, getIDShift());
                connect.pstat.setString(10, getNoRekening());
                connect.pstat.setString(11, getStatus());
                connect.pstat.setString(12, getJenisKaryawan());
                connect.pstat.setString(13, getPassword());
                connect.pstat.executeUpdate();
                connect.pstat.close();

                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText("Save Data Successfully");
                alert.show();

                // Disable form inputs after saving
                tbId.setDisable(true);
                tbNama.setDisable(true);
                tbEmail.setDisable(true);
                tbTanggalLahir.setDisable(true);
                tbTanggalMasuk.setDisable(true);
                cbJabatan.setDisable(true);
                cbDivisi.setDisable(true);
                cbGolongan.setDisable(true);
                cbShift.setDisable(true);
                tbNoRekening.setDisable(true);
                cbJnsKaryawan.setDisable(true);
                btnSave.setDisable(true);
                btnCancel.setDisable(true);
                Clear();

            } catch (SQLException ex) {
                ex.printStackTrace();
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Error : " + ex);
                alert.show();
            }
            viewKaryawan.getItems().clear();
            refreshDataSet();
        }
    }

    // Method untuk menemukan objek berdasarkan ID
    private Jabatan findJabatanById(String id) {
        for (Jabatan jabatan : cbJabatan.getItems()) {
            if (jabatan.getIdJabatan().equals(id)) {
                return jabatan;
            }
        }
        return null; // atau lempar exception jika tidak ditemukan
    }

    private Divisi findDivisiById(String id) {
        for (Divisi divisi : cbDivisi.getItems()) {
            if (divisi.getIdDivisi().equals(id)) {
                return divisi;
            }
        }
        return null; // atau lempar exception jika tidak ditemukan
    }

    private Golongan findGolonganById(String id) {
        for (Golongan golongan : cbGolongan.getItems()) {
            if (golongan.getIdGolongan().equals(id)) {
                return golongan;
            }
        }
        return null; // atau lempar exception jika tidak ditemukan
    }

    private Shift findShiftById(String id) {
        for (Shift shift : cbShift.getItems()) {
            if (shift.getIdShift().equals(id)) {
                return shift;
            }
        }
        return null; // atau lempar exception jika tidak ditemukan
    }

    @FXML
    private void handleTableDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            CRUDKaryawan selectedKaryawan = viewKaryawan.getSelectionModel().getSelectedItem();
            if (selectedKaryawan != null) {
                tbId.setText(selectedKaryawan.getIDKaryawan());
                tbNama.setText(selectedKaryawan.getNama());
                tbEmail.setText(selectedKaryawan.getEmail());
                tbTanggalLahir.setValue(LocalDate.parse(selectedKaryawan.getTanggalLahir()));
                tbTanggalMasuk.setValue(LocalDate.parse(selectedKaryawan.getTanggalMasuk()));
                tbPassword.setText(selectedKaryawan.getPassword());
                tbNoRekening.setText(selectedKaryawan.getNoRekening());
                // Menemukan objek yang sesuai berdasarkan ID
                Jabatan jabatan = findJabatanById(selectedKaryawan.getIDJabatan());
                Divisi divisi = findDivisiById(selectedKaryawan.getIDDivisi());
                Golongan golongan = findGolonganById(selectedKaryawan.getIDGolongan());
                Shift shift = findShiftById(selectedKaryawan.getIDShift());
                // Menetapkan nilai yang ditemukan ke ComboBox
                cbJabatan.setValue(jabatan);
                cbDivisi.setValue(divisi);
                cbGolongan.setValue(golongan);
                cbShift.setValue(shift);
                cbJnsKaryawan.setValue(selectedKaryawan.getJenisKaryawan());
            }
        }
    }

    @FXML
    private void btnDeleteClick() {
        setIDKaryawan(tbId.getText());
        try {
            String sql = "{CALL sp_DeleteKaryawan(?)}";
            connect.pstat = connect.conn.prepareStatement(sql);
            connect.pstat.setString(1,getIDKaryawan());

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
    private void btnSearchClick() {
        oblist.clear();
        try {
            String sql = "{CALL sp_findAllKaryawan(?)}";
            connect.pstat = connect.conn.prepareStatement(sql);
            connect.pstat.setString(1, tbSearch.getText());
            connect.result = connect.pstat.executeQuery();

            while (connect.result.next()) {
                String idKaryawan = connect.result.getString("IDKaryawan");
                String nama = connect.result.getString("nama");
                String email = connect.result.getString("email");
                String tanggal_lahir = connect.result.getString("tanggal_lahir");
                String tanggal_masuk = connect.result.getString("tanggal_masuk");
                String idJabatan = connect.result.getString("IDJabatan");
                String idDivisi = connect.result.getString("IDDivisi");
                String idGolongan = connect.result.getString("IDGolongan");
                String idShift = connect.result.getString("IDShift");
                String no_rekening = connect.result.getString("no_rekening");
                String status = connect.result.getString("status");
                String jenis_karyawan = connect.result.getString("jenis_karyawan");
                String password = connect.result.getString("password");

                CRUDKaryawan karyawan = new CRUDKaryawan(idKaryawan, nama, email, tanggal_lahir, tanggal_masuk,
                        idJabatan, idDivisi, idGolongan, idShift, no_rekening,
                        status, jenis_karyawan, password);
                oblist.add(karyawan);
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

    @FXML
    private void btnUpdateClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        if (tbId.getText().isEmpty() || tbNama.getText().isEmpty() || tbEmail.getText().isEmpty() ||
                tbTanggalLahir.getValue() == null || tbTanggalMasuk.getValue() == null || cbJabatan.getValue() == null ||
                cbDivisi.getValue() == null || cbGolongan.getValue() == null || cbShift.getValue() == null ||
                tbNoRekening.getText().isEmpty() || cbJnsKaryawan.getValue() == null) {

            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("All fields must be filled");
            alert.showAndWait();
        } else {
            try {
                // Ambil nilai dari form
                setIDKaryawan(tbId.getText());
                setNama(tbNama.getText());
                setEmail(tbEmail.getText());
                setPassword(tbPassword.getText());

                // Ambil tanggal dari DatePicker
                LocalDate tanggalLahir = tbTanggalLahir.getValue().plusDays(2);
                LocalDate tanggalMasuk = tbTanggalMasuk.getValue().plusDays(2);

                // Set nilai ke setter
                setTanggalLahir(tanggalLahir.toString()); // Ubah ke string jika setter membutuhkan String
                setTanggalMasuk(tanggalMasuk.toString());

                // Ambil jabatan, divisi, golongan, shift dari combo box
                Jabatan selectedJabatan = cbJabatan.getValue();
                Divisi selectedDivisi = cbDivisi.getValue();
                Golongan selectedGolongan = cbGolongan.getValue();
                Shift selectedShift = cbShift.getValue();

                // Set nilai ke setter
                setIDJabatan(selectedJabatan.getIdJabatan()); // Pastikan getIdJabatan() mengembalikan String ID
                setIDDivisi(selectedDivisi.getIdDivisi());
                setIDGolongan(selectedGolongan.getIdGolongan());
                setIDShift(selectedShift.getIdShift());
                setNoRekening(tbNoRekening.getText());
                setStatus("Active");
                setJenisKaryawan((String) cbJnsKaryawan.getValue());

                // Panggil stored procedure
                String sql = "{CALL sp_UpdateKaryawan(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
                connect.pstat = connect.conn.prepareStatement(sql);
                connect.pstat.setString(1, getIDKaryawan());
                connect.pstat.setString(2, getNama());
                connect.pstat.setString(3, getEmail());
                connect.pstat.setString(4, getTanggalLahir()); // Gunakan setDate() untuk java.sql.Date
                connect.pstat.setString(5, getTanggalMasuk());
                connect.pstat.setString(6, getIDJabatan());
                connect.pstat.setString(7, getIDDivisi());
                connect.pstat.setString(8, getIDGolongan());
                connect.pstat.setString(9, getIDShift());
                connect.pstat.setString(10, getNoRekening());
                connect.pstat.setString(11, getStatus());
                connect.pstat.setString(12, getJenisKaryawan());
                connect.pstat.setString(13, getPassword());
                connect.pstat.executeUpdate();
                connect.pstat.close();

                // Tampilkan pesan sukses
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText("Save Data Successfully");
                alert.show();

                // Nonaktifkan input setelah menyimpan
                tbId.setDisable(true);
                tbNama.setDisable(true);
                tbEmail.setDisable(true);
                tbTanggalLahir.setDisable(true);
                tbTanggalMasuk.setDisable(true);
                cbJabatan.setDisable(true);
                cbDivisi.setDisable(true);
                cbGolongan.setDisable(true);
                cbShift.setDisable(true);
                tbNoRekening.setDisable(true);
                cbJnsKaryawan.setDisable(true);
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
                btnCancel.setDisable(true);
                // Clear form
                Clear();
                viewKaryawan.getItems().clear();
                // Refresh tampilan data setelah update
                refreshDataSet();

            } catch (SQLException ex) {
                ex.printStackTrace();
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Error : " + ex.getMessage());
                alert.show();
            }
        }
    }
}