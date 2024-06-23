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
import java.sql.*;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CRUDDivisi implements Initializable {

    public static class Division {
        private String ID, name, location, description, status;

        public Division() {

        }

        public Division(String Id, String Name, String Location, String Description, String Status) {
            this.ID = Id;
            this.name = Name;
            this.location = Location;
            this.description = Description;
            this.status = Status;
        }

        public String getID() { return ID; }
        public void setID(String ID) { this.ID = ID; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    @FXML
    private TableView<Division> tbDivisi;
    @FXML
    private TableColumn<Division, Integer> cNum;
    @FXML
    private TableColumn<Division, String> cName;
    @FXML
    private TableColumn<Division, String> cLocation;
    @FXML
    private TableColumn<Division, String> cDesc;
    @FXML
    private TableColumn<Division, String> cStatus;
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtLoc;
    @FXML
    private TextArea txtDesc;
    @FXML
    private TextField txtStatus;
    @FXML
    private TextField txtSearch;
    @FXML
    private ChoiceBox<String> cbFilter;
    @FXML
    private Button btnSave, btnCreate, btnSearch, btnUpdate, btnActive, btnDeactive;

    private ObservableList<Division> list = FXCollections.observableArrayList();
    DBConnect connection = new DBConnect();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData("SELECT * FROM Divisi WHERE status = 'Active'");
        cbFilter.setValue("Active");
        txtID.setText(generateNextId());
        String currentFilter = cbFilter.getValue();
        cbFilter.setItems(FXCollections.observableArrayList("Active", "Deactive", "All"));
        cbFilter.setOnAction(event -> handleFilterSelection(currentFilter));

        tbDivisi.setRowFactory(tv -> {
            TableRow<Division> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Division rowData = row.getItem();
                    fillCell(rowData);
                }
            });
            return row;
        });
        btnUpdate.setDisable(true);
    }

    private void fillCell(Division divisi) {
        txtID.setText(divisi.getID());
        txtName.setText(divisi.getName());
        txtLoc.setText(divisi.getLocation());
        txtDesc.setText(divisi.getDescription());

        txtName.setDisable(false);
        txtDesc.setDisable(false);
        txtLoc.setDisable(false);
        btnSave.setDisable(true);

        btnUpdate.setDisable(false);
        if (divisi.getStatus().equals("Active")) {
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

    private int divisiCount() {
        int count = 0;

        try {
            connection.stat = connection.conn.createStatement();
            String query = "SELECT COUNT(*) FROM Divisi";
            connection.result = connection.stat.executeQuery(query);
            if (connection.result.next()) {
                count = connection.result.getInt(1);
            }
            connection.stat.close();
            connection.result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public String generateNextId() {
        int count = divisiCount();
        return generateId(count + 1);
    }

    private String generateId(int number) {
        return String.format("DIV%03d", number);
    }

    private void handleFilterSelection(String selectedFilter) {
        selectedFilter = cbFilter.getSelectionModel().getSelectedItem();
        if (selectedFilter != null) {
            switch (selectedFilter) {
                case "Active":
                    loadData("SELECT * FROM Divisi WHERE status = 'Active'");
                    break;
                case "Deactive":
                    loadData("SELECT * FROM Divisi WHERE status = 'De-active'");
                    break;
                case "All":
                    loadData("SELECT * FROM Divisi ");
                    break;
                default:
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
                list.add(new Division(
                        connection.result.getString("IDDivisi"),
                        connection.result.getString("nama"),
                        connection.result.getString("lokasi"),
                        connection.result.getString("deksripsi"),
                        connection.result.getString("status")
                ));
            }

            connection.stat.close();
            connection.result.close();
        } catch (Exception ex) {
            System.out.println("Error view :" + ex);
        }
        cNum.setCellValueFactory(cellData -> new SimpleIntegerProperty(tbDivisi.getItems().indexOf(cellData.getValue()) + 1).asObject());
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        cDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tbDivisi.setItems(list);
    }


    @FXML
    private void onbtnCreateClick() {
            btnSave.setDisable(false);
            txtID.setDisable(false);
            txtName.setDisable(false);
            txtLoc.setDisable(false);
            txtDesc.setDisable(false);
            txtStatus.setPromptText("ACTIVE");
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
            Division division = new Division();

            division.ID = txtID.getText();
            division.name = txtName.getText();
            division.location = txtLoc.getText();
            division.description = txtDesc.getText();
            division.status = "Active";


            String pattern = "^DIV\\d{3}$";
            if (!Pattern.matches(pattern, division.getID())) {
                JOptionPane.showMessageDialog(null, "ID must follow the format DIV followed by 3 digits (DIVxxx).", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (division.ID.isEmpty() || division.name.isEmpty() || division.location.isEmpty() ||
                    division.description.isEmpty() || division.status.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                clear();
                return;
            }

            try {
                String checkQuery = "SELECT COUNT(*) FROM Divisi WHERE IDDivisi = ?";
                connection.pstat = connection.conn.prepareStatement(checkQuery);
                connection.pstat.setString(1, division.ID);
                ResultSet rs = connection.pstat.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(null, "ID already exists in the database!", "Error", JOptionPane.ERROR_MESSAGE);
                    rs.close();
                    connection.pstat.close();
                    clear();
                    return;
                }
                rs.close();
                connection.pstat.close();

                String query = "{call sp_InsertDivisi(?, ?, ?, ?, ?)}";
                connection.pstat = connection.conn.prepareStatement(query);

                connection.pstat.setString(1, division.getID());
                connection.pstat.setString(2, division.getName());
                connection.pstat.setString(3, division.getLocation());
                connection.pstat.setString(4, division.getDescription());
                connection.pstat.setString(5, division.getStatus());

                connection.pstat.execute();
                connection.pstat.close();

                JOptionPane.showMessageDialog(null, "Successfully saved!");
                clear();

                loadData("SELECT * FROM Divisi WHERE status = 'Active'");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Failed: " + ex);
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
            Division division = new Division();

            division.ID = txtID.getText();
            division.name = txtName.getText();
            division.location = txtLoc.getText();
            division.description = txtDesc.getText();
            division.status = "Active";
            String currentFilter = cbFilter.getValue();

            try {
                String query = "{call sp_UpdateDivisi(?, ?, ?, ?, ?)}";
                connection.pstat = connection.conn.prepareStatement(query);

                connection.pstat.setString(1, division.getID());
                connection.pstat.setString(2, division.getName());
                connection.pstat.setString(3, division.getLocation());
                connection.pstat.setString(4, division.getDescription());
                connection.pstat.setString(5, division.getStatus());

                connection.pstat.execute();
                connection.pstat.close();

                JOptionPane.showMessageDialog(null, "Successfully updated!");
                clear();
                cbFilter.setOnAction(event -> handleFilterSelection(currentFilter));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Failed: " + ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Update canceled.");
        }
    }

    @FXML
    private void onbtnSearchClick() {

            list.clear();
            tbDivisi.getItems().clear();
            String currentFilter = cbFilter.getValue();

            if (txtSearch.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Search field cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String query = "{call sp_SearchDivisi(?)}";
                connection.pstat = connection.conn.prepareStatement(query);
                connection.pstat.setString(1, txtSearch.getText());
                connection.result = connection.pstat.executeQuery();

                boolean found = false;
                while (connection.result.next()) {
                    list.add(new Division(
                            connection.result.getString("IDDivisi"),
                            connection.result.getString("nama"),
                            connection.result.getString("lokasi"),
                            connection.result.getString("deksripsi"),
                            connection.result.getString("status")
                    ));
                    found = true;
                }

                if (!found) {
                    JOptionPane.showMessageDialog(null, "Division not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    cbFilter.setOnAction(event -> handleFilterSelection(currentFilter));
                    clear();
                    loadData("SELECT * FROM Divisi WHERE status = 'Active'");
                }

                connection.result.close();
                connection.pstat.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Failed: " + ex);
            }

            cNum.setCellValueFactory(cellData -> new SimpleIntegerProperty(tbDivisi.getItems().indexOf(cellData.getValue()) + 1).asObject());
            cName.setCellValueFactory(new PropertyValueFactory<>("name"));
            cLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
            cDesc.setCellValueFactory(new PropertyValueFactory<>("description"));

            tbDivisi.setItems(list);
    }

    @FXML
    private void onbtnActiveClick() {
        updateStatus(txtID.getText(), "Active");
        loadData("SELECT * FROM Divisi WHERE status = 'Active'");
        cbFilter.setValue("Active");
    }

    @FXML
    private void onbtnDeactiveClick() {
        updateStatus(txtID.getText(), "De-active");
        loadData("SELECT * FROM Divisi WHERE status = 'De-active'");
        cbFilter.setValue("Deactive");
    }

    private void updateStatus(String id, String status) {
        int confirmation = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to change the status?",
                "Status Change Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                String query = "{call sp_statusDivisi(?,?)}";
                connection.pstat = connection.conn.prepareStatement(query);

                connection.pstat.setString(1, id);
                connection.pstat.setString(2, status);

                connection.pstat.execute();
                connection.pstat.close();

                JOptionPane.showMessageDialog(null, "Status successfully updated!");
                clear();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Failed: " + ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Status change canceled.");
        }
    }

    private void clear() {
        txtID.setText(generateNextId());
        txtName.clear();
        txtLoc.clear();
        txtDesc.clear();
    }
}
