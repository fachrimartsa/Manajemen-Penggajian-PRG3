package com.example.manajemen_penggajian;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


import java.sql.*;
import java.util.Optional;

public class CRUDShift {
    private final ObservableList<Shift> SList = FXCollections.observableArrayList();
    public Button btnClear;

    @FXML
    private Button btnSave;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnEdit;
    @FXML
    private Button BtnUpdate;
    @FXML
    private Button btnCreate;
    @FXML
    private TextField txtIDShift;
    @FXML
    private TextField txtWShift;
    @FXML
    private TextField txtJumKar;
    @FXML
    private TextField txtSearch;
    @FXML
    private TextField txtJShift;
    @FXML
    private TableView<Shift> tbShift;
    @FXML
    private TableColumn<Shift, Integer> tcNum;
    @FXML
    private TableColumn<Shift, String> tcJShift;
    @FXML
    private TableColumn<Shift, String> tcWShift;
    @FXML
    private TableColumn<Shift, Integer> tcJumShift;


    String IDShift, JShift, WShift, status;
    Integer JumShift;
    DBConnect CONAS = new DBConnect();


    @FXML
    public void initialize() {
        loadviewtable();
        addTableListener();
        addValidationListener();
        onBtnSearchClick();
    }

    //========================= METHOD ON ACTION =========================

    @FXML
    protected void onBtnCreateClick(){
        txtIDShift.setDisable(false);
        txtJShift.setDisable(false);
        txtWShift.setDisable(false);
        txtJumKar.setDisable(false);
        btnSave.setDisable(false);
        txtIDShift.setText(autoIDShift());
    }
    @FXML
    protected void onBtnTVClick(){
        txtIDShift.setDisable(false);
        txtJShift.setDisable(false);
        txtWShift.setDisable(false);
        txtJumKar.setDisable(false);
    }

    @FXML
    protected void onBtnSaveClick() {
        IDShift = txtIDShift.getText();
        JShift = txtJShift.getText();
        WShift = txtWShift.getText();
        JumShift = Integer.parseInt(txtJumKar.getText());
        status = "Active";

        try {
            String checkQuery = "SELECT COUNT(*) FROM shift WHERE LOWER(Jenis_Shift) = ? AND status = 'Active'";
            PreparedStatement checkStmt = CONAS.conn.prepareStatement(checkQuery);
            checkStmt.setString(1, JShift.toLowerCase()); // Convert to lowercase to match the case-insensitive comparison
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                showErrorAlert(null, "Data Same, Data fail to save");
            } else {
                CONAS.pstat = CONAS.conn.prepareCall("{call sp_CreateShift(?, ?, ?, ?, ?)}");
                CONAS.pstat.setString(1, IDShift);
                CONAS.pstat.setString(2, WShift);
                CONAS.pstat.setString(3,status);
                CONAS.pstat.setString(4, JShift);
                CONAS.pstat.setInt(5, JumShift);
                CONAS.pstat.executeUpdate();
                CONAS.pstat.close();
                showInformationAlert("Insert Data Success!");
            }
            rs.close();
            checkStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorAlert(ex.getMessage(), "Insert data Fail :\n");
        }
        txtIDShift.setDisable(true);
        txtJShift.setDisable(true);
        txtWShift.setDisable(true);
        txtJumKar.setDisable(true);
        refreshTable();
    }

    @FXML
    protected void onBtnClearClick(){
        txtIDShift.setText("");
        txtJShift.setText("");
        txtWShift.setText("");
        txtJumKar.setText("");

        txtIDShift.setDisable(true);
        txtJShift.setDisable(true);
        txtWShift.setDisable(true);
        txtJumKar.setDisable(true);

    }

    @FXML
    protected void onBtnUpdateClick() {
        String IDShift = txtIDShift.getText();
        String Jenis_Shift = txtJShift.getText();
        String Waktu_Shift = txtWShift.getText();
        int Jumlah_Karyawan = Integer.parseInt(txtJumKar.getText());
        String stat = "Active";


        try {
            String query = "{call sp_UpdateShift(?, ?, ?, ?, ?)}";
            CONAS.pstat = CONAS.conn.prepareCall(query);
            CONAS.pstat.setString(1, IDShift);
            CONAS.pstat.setString(2, Waktu_Shift);
            CONAS.pstat.setString(3, stat);
            CONAS.pstat.setString(4, Jenis_Shift);
            CONAS.pstat.setInt(5, Jumlah_Karyawan);


            CONAS.pstat.executeUpdate();
            CONAS.pstat.close();
            showInformationAlert("Update data Success");
        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorAlert(ex.getMessage(), "Update data Fail :\n");
        }
        refreshTable();
        txtIDShift.setDisable(true);
        txtJShift.setDisable(true);
        txtWShift.setDisable(true);
        txtJumKar.setDisable(true);
    }

    @FXML
    protected void onBtnSearchClick() {
        try {
            String searchText = txtSearch.getText();
            // Membuat koneksi dan mempersiapkan panggilan stored procedure
            Connection conn = CONAS.getConnection();
            CallableStatement stmt;

            // Jika searchText tidak kosong, panggil stored procedure dengan parameter searchText
            stmt = conn.prepareCall("{call sp_CariShift(?)}");
            stmt.setString(1, searchText);

            // Eksekusi query
            ResultSet rs = stmt.executeQuery();

            // Bersihkan data sebelum memuat hasil pencarian baru
            SList.clear();

            // Memuat hasil pencarian ke dalam ObservableList SList
            while (rs.next()) {
                Shift shift = new Shift(
                        rs.getString("IDShift"),
                        rs.getString("Jenis_Shift"),
                        rs.getString("Waktu_Shift"),
                        rs.getInt("Jumlah_Karyawan"),
                        rs.getString("status")
                );
                SList.add(shift);
            }

            // Menampilkan ulang data ke dalam TableView
            refreshview();

            // Tutup statement dan result set
            stmt.close();
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorAlert(ex.getMessage(), "Error searching data:");

        }

    }

    @FXML
    protected void onBtnDeleteClick() {
        String IDShift = txtIDShift.getText();
        String status = "Non Active";

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation for De-Active");
        alert.setHeaderText("Are you sure to De-Active this data?");
        alert.setContentText("Select 'OK' to De-Active or 'Cancel' to cancel.");

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == okButton) {
            try {
                String query = "{call sp_DeleteShift(?, ?)}";
                CONAS.pstat = CONAS.conn.prepareStatement(query);
                CONAS.pstat.setString(1, IDShift);
                CONAS.pstat.setString(2, status);
                CONAS.pstat.executeUpdate();
                CONAS.pstat.close();
                showInformationAlert("Data berhasil di-nonaktifkan");
            } catch (SQLException ex) {
                ex.printStackTrace();
                showErrorAlert(ex.getMessage(),"Deactive data Fail :" );
            }
        } else {
            showInformationAlert("Disabled action cancelled");
        }
        refreshTable();
        txtIDShift.setDisable(true);
        txtJShift.setDisable(true);
        txtWShift.setDisable(true);
        txtJumKar.setDisable(true);
    }

    private String autoIDShift() {
        String nextID = "SH000"; // Nilai default

        try {
            Connection connection = CONAS.getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call sp_maxIDShift}");
            ResultSet rs = callableStatement.executeQuery();

            // Ambil hasil dari stored procedure
            if (rs.next()) {
                nextID = rs.getString("HighIdShift");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Menghasilkan format ID berikutnya
        return generateNextID(nextID);
    }

    private String generateNextID(String currentID) {
        // Memproses untuk menghasilkan ID berikutnya dari ID terakhir yang didapatkan
        String prefix = currentID.substring(0, 2); // Mengambil "SH"
        int sequence = Integer.parseInt(currentID.substring(2)); // Mengambil nomor urutan
        sequence++; // Menambah urutan

        // Menghasilkan ID berikutnya dengan format "AS" diikuti nomor urutan yang diubah kembali menjadi string
        return prefix + String.format("%03d", sequence); // Format untuk mendapatkan 3 digit nomor
    }

    //============================= METHOD ===========================

    private void addTableListener() {
        tbShift.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });
    }
    private void populateFields(Shift shift) {
        txtIDShift.setText(shift.getIDSh());
        txtJShift.setText(shift.getJSh());
        txtWShift.setText(shift.getWSh());
        txtJumKar.setText(String.valueOf(shift.getJumSh()));
    }

    private void loadviewtable() {
        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM shift WHERE status ='Active'";
            connection.result = connection.stat.executeQuery(query);
            while (connection.result.next()) {
                SList.add(new Shift(
                        connection.result.getString("IDShift"),
                        connection.result.getString("Jenis_Shift"),
                        connection.result.getString("Waktu_Shift"),
                        connection.result.getInt("Jumlah_Karyawan"),
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
    //REFRESH TABLE VIEW
    private void refreshTable() {
        SList.clear();
        loadviewtable();
    }
    private void refreshview(){
        // Menampilkan data ke dalam TableView
        tcNum.setCellValueFactory(cellData -> new SimpleIntegerProperty(tbShift.getItems().indexOf(cellData.getValue())+1).asObject());
        tcJShift.setCellValueFactory(new PropertyValueFactory<>("JSh"));
        tcWShift.setCellValueFactory(new PropertyValueFactory<>("WSh"));
        tcJumShift.setCellValueFactory(new PropertyValueFactory<>("JumSh"));
        tbShift.setItems(SList);
    }
    //===================================== VALIDASI OBJEK ========================================
    private void addValidationListener() {
        txtWShift.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // focus lost
                String input = txtWShift.getText();

                // Check if input does not match the correct format or contains invalid characters
                if (!input.matches("\\d{2}:\\d{2}\\s*-\\s*\\d{2}:\\d{2}") || !input.matches("[0-9:\\s-]+")) {
                    showErrorAlert("Invalid input! Please use HH:MM - HH:MM format\nand only digits, colon (:) and hyphen (-) are allowed.",null);
                }
            }
        });
    }





    //===================================== VALIDASI ALERT INFORMATION ============== ==========
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

    public class Shift {
        String IDSh, JSh, WSh,  stat;
        Integer JumSh;
        public Shift(String IDSh, String JSh, String Wsh,Integer JumSh, String stat) {
            this.IDSh = IDSh;
            this.JSh = JSh;
            this.WSh = Wsh;
            this.JumSh = JumSh;
            this.stat = stat;
        }
        public String getIDSh() {
            return IDSh;
        }
        public String getJSh() {
            return JSh;
        }
        public String getWSh() {
            return WSh;
        }
        public Integer getJumSh(){ return JumSh;}
        public String getStat() {
            return stat;
        }
    }
}
