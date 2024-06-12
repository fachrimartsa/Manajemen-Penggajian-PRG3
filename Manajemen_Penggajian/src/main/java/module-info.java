module com.example.manajemen_penggajian {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.manajemen_penggajian to javafx.fxml;
    exports com.example.manajemen_penggajian;
}