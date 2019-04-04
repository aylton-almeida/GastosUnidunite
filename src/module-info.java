module GastosUnidunite {
    requires javafx.fxml;
    requires javafx.controls;
    requires com.jfoenix;
    requires java.sql;
    opens logic to javafx.base;

    opens controllers;
}