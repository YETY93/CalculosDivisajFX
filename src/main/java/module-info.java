module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.controlsfx.controls;

    opens com.alura.conversor.main to javafx.fxml;
    exports com.alura.conversor.main;
}