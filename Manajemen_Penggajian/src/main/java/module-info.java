module com.example.manajemen_penggajian {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;

    opens com.example.manajemen_penggajian to javafx.fxml;
    exports com.example.manajemen_penggajian;
    exports org.example.manajemen_penggajian;
    opens org.example.manajemen_penggajian to javafx.fxml;
    exports org.example.manajemenpenggajian;
    opens org.example.manajemenpenggajian to javafx.fxml;
}