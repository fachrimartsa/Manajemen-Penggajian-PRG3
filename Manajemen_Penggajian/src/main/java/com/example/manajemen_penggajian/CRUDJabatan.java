package com.example.manajemen_penggajian;

import com.example.manajemen_penggajian.DBConnect;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CRUDJabatan implements Initializable {

    public static class Jabatan {
        private String ID, name, description, status;

        public Jabatan() {}

        public Jabatan(String ID, String name, String description, String status) {
            this.ID = ID;
            this.name = name;
            this.description = description;
            this.status = status;
        }

        public String getID() { return ID; }
        public void setID(String ID) { this.ID = ID; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    @FXML private TableView<Jabatan> tbJabatan;
    @FXML private TableColumn<Jabatan, Integer> cNum;
    @FXML private TableColumn<Jabatan, String> cName;
    @FXML private TableColumn<Jabatan, String> cDesc;
    @FXML private TableColumn<Jabatan, String> cStatus;

    @FXML private TextField txtID, txtName, txtStatus, txtSearch, txtPercent;
    @FXML private TextArea txtDesc;
    @FXML private ChoiceBox<String> cbFilter;
    @FXML private ComboBox<String> cbAllowance;

    @FXML private TableView<Jabatan> tbTunjangan;
    @FXML private TableColumn<Jabatan, Integer> ctNum;
    @FXML private TableColumn<Jabatan, String> ctName;
    @FXML private TableColumn<Jabatan, String> ctPersent;

    @FXML private Button btnSave, btnCreate, btnSearch, btnUpdate, btnActive, btnDeactive, btnAdd;

    private final ObservableList<Jabatan> list = FXCollections.observableArrayList();
    private final DBConnect connection = new DBConnect();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData("SELECT * FROM Jabatan WHERE status = 'Active'");
        cbFilter.setValue("Active");
        txtID.setText(generateNextId());
        cbFilter.setItems(FXCollections.observableArrayList("Active", "Deactive", "All"));
        cbFilter.setOnAction(event -> handleFilterSelection(cbFilter.getValue()));

        tbJabatan.setRowFactory(tv -> {
            TableRow<Jabatan> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Jabatan rowData = row.getItem();
                    fillCell(rowData);
                }
            });
            return row;
        });
        btnUpdate.setDisable(true);
    }

    private void fillCell(Jabatan jabatan) {
        txtID.setText(jabatan.getID());
        txtName.setText(jabatan.getName());
        txtDesc.setText(jabatan.getDescription());

        txtName.setDisable(false);
        txtDesc.setDisable(false);
        btnSave.setDisable(true);

        btnUpdate.setDisable(false);
        if ("Active".equals(jabatan.getStatus())) {
            btnActive.setDisable(true);
            btnActive.setVisible(false);
            btnDeactive.setVisible(true);
            btnDeactive.setDisable(false);
            txtStatus.setPromptText("Active");
        } else {
            btnDeactive.setVisible(true);
            btnActive.setVisible(true);
            btnActive.setDisable(false);
            txtStatus.setPromptText("Deactive");
        }
    }

    private int jabatanCount() {
        try (var stat = connection.conn.createStatement()) {
            String query = "SELECT COUNT(*) FROM Jabatan";
            try (var result = stat.executeQuery(query)) {
                if (result.next()) {
                    return result.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String generateNextId() {
        int count = jabatanCount();
        return generateId(count + 1);
    }

    private String generateId(int number) {
        return String.format("POS%03d", number);
    }

    private void handleFilterSelection(String selectedFilter) {
        if (selectedFilter != null) {
            switch (selectedFilter) {
                case "Active":
                    loadData("SELECT * FROM Jabatan WHERE status = 'Active'");
                    break;
                case "Deactive":
                    loadData("SELECT * FROM Jabatan WHERE status = 'Deactive'");
                    break;
                case "All":
                    loadData("SELECT * FROM Jabatan");
                    break;
            }
        }
    }

    @FXML
    private void loadData(String query) {
        list.clear();
        try {
            connection.stat = connection.conn.createStatement();
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()) {
                list.add(new Jabatan(
                        connection.result.getString("IDJabatan"),
                        connection.result.getString("nama"),
                        connection.result.getString("deksripsi"),
                        connection.result.getString("status")
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        cNum.setCellValueFactory(cellData -> new SimpleIntegerProperty(tbJabatan.getItems().indexOf(cellData.getValue()) + 1).asObject());
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tbJabatan.setItems(list);
    }

    @FXML
    private void onbtnCreateClick() {
        btnSave.setDisable(false);
        txtID.setDisable(false);
        txtName.setDisable(false);
        txtDesc.setDisable(false);
        txtStatus.setPromptText("Active");
        clear();
        cbFilter.setValue("");
    }

    @FXML
    private void onbtnSaveClick() {
        int confirmation = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to save this record?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            Jabatan jabatan = new Jabatan();
            jabatan.ID = txtID.getText();
            jabatan.name = txtName.getText();
            jabatan.description = txtDesc.getText();
            jabatan.status = "Active";

            String pattern = "^POS\\d{3}$";
            if (!Pattern.matches(pattern, jabatan.getID())) {
                JOptionPane.showMessageDialog(null, "ID must follow the format JAB followed by 3 digits (JABxxx).", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (jabatan.ID.isEmpty() || jabatan.name.isEmpty() || jabatan.description.isEmpty() || jabatan.status.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                clear();
                return;
            }

            try {
                String checkQuery = "SELECT COUNT(*) FROM Jabatan WHERE IDJabatan = ?";
                try (var pstat = connection.conn.prepareStatement(checkQuery)) {
                    pstat.setString(1, jabatan.ID);
                    try (var rs = pstat.executeQuery()) {
                        if (rs.next() && rs.getInt(1) > 0) {
                            JOptionPane.showMessageDialog(null, "ID already exists in the database!", "Error", JOptionPane.ERROR_MESSAGE);
                            clear();
                            return;
                        }
                    }
                }

                String query = "{call sp_InsertJabatan(?, ?, ?, ?)}";
                try (var pstat = connection.conn.prepareStatement(query)) {
                    pstat.setString(1, jabatan.getID());
                    pstat.setString(2, jabatan.getName());
                    pstat.setString(3, jabatan.getDescription());
                    pstat.setString(4, jabatan.getStatus());
                    pstat.execute();
                }

                JOptionPane.showMessageDialog(null, "Successfully saved!");
                clear();
                loadData("SELECT * FROM Jabatan WHERE status = 'Active'");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Failed: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Save canceled.");
        }
    }

    @FXML
    private void onbtnUpdateClick() {
        int confirmation = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to update this data?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            Jabatan jabatan = new Jabatan();
            jabatan.ID = txtID.getText();
            jabatan.name = txtName.getText();
            jabatan.description = txtDesc.getText();
            jabatan.status = "Active";
            if (jabatan.ID.isEmpty() || jabatan.name.isEmpty() || jabatan.description.isEmpty() || jabatan.status.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                clear();
                return;
            }

            try {
                String query = "{call sp_UpdateJabatan(?, ?, ?,?)}";
                try (var pstat = connection.conn.prepareStatement(query)) {
                    pstat.setString(1, jabatan.getID());
                    pstat.setString(2, jabatan.getName());
                    pstat.setString(3, jabatan.getDescription());
                    pstat.setString(4, jabatan.getStatus());
                    pstat.execute();
                }

                JOptionPane.showMessageDialog(null, "Successfully updated!");
                clear();
                loadData("SELECT * FROM Jabatan WHERE status = 'Active'");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Failed: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Update canceled.");
        }
    }

    @FXML
    private void onbtnSearchClick() {
        tbJabatan.getItems().clear();
        String currentFilter = cbFilter.getValue();
        if (txtSearch.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Search field cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "{call sp_SearchJabatan(?)}";
            connection.pstat = connection.conn.prepareStatement(query);
            connection.pstat.setString(1, txtSearch.getText());
            connection.result = connection.pstat.executeQuery();

            boolean found = false;
            while (connection.result.next()) {
                list.add(new Jabatan(
                        connection.result.getString("IDJabatan"),
                        connection.result.getString("nama"),
                        connection.result.getString("deksripsi"),
                        connection.result.getString("status")
                ));
                found = true;
            }
            if (!found) {
                JOptionPane.showMessageDialog(null, "Position not found!", "Error", JOptionPane.ERROR_MESSAGE);
                cbFilter.setOnAction(event -> handleFilterSelection(currentFilter));
                clear();
                loadData("SELECT * FROM Jabatan WHERE status = 'Active'");
            }

            connection.result.close();
            connection.pstat.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    @FXML
    private void onbtnActiveClick() {
        changeStatus("Active");
    }

    @FXML
    private void onbtnDeactiveClick() {
        changeStatus("Deactive");
    }

    private void changeStatus(String newStatus) {
        int confirmation = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to " + newStatus.toLowerCase() + " this record?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                String query = "UPDATE Jabatan SET status = ? WHERE IDJabatan = ?";
                try (var pstat = connection.conn.prepareStatement(query)) {
                    pstat.setString(1, newStatus);
                    pstat.setString(2, txtID.getText());
                    pstat.execute();
                }

                JOptionPane.showMessageDialog(null, "Successfully " + newStatus.toLowerCase() + "d!");
                clear();
                loadData("SELECT * FROM Jabatan WHERE status = 'Active'");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Failed: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, newStatus + " canceled.");
        }
    }

    private void clear() {
        txtID.setText(generateNextId());
        txtName.setText("");
        txtDesc.setText("");
        txtStatus.setText("");
    }
}
