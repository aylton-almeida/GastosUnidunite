module GastosUnidunite {
    requires javafx.fxml;
    requires javafx.controls;
    requires com.jfoenix;
    opens logic to javafx.base;

    opens controllers;
}