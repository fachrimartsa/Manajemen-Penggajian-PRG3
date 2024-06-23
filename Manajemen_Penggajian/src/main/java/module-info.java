module com.example.manajemen_penggajian {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;

    // Menggunakan paket com.example.manajemen_penggajian
    opens com.example.manajemen_penggajian to javafx.fxml;
    exports com.example.manajemen_penggajian;
}