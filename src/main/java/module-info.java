module com.example.prodigy_sd_03 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.prodigy_sd_03 to javafx.fxml;
    exports com.example.prodigy_sd_03;
}