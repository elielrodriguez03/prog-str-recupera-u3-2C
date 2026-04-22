module com.example.prestamocalculadoras {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.prestamocalculadoras.controller to javafx.fxml;
    exports com.example.prestamocalculadoras;
}
