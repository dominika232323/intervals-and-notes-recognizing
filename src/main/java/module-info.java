module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jooq;
    requires org.jooq.codegen;
    requires org.jooq.meta;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}