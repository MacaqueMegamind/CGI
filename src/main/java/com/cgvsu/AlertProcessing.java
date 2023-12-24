package com.cgvsu;

import javafx.scene.control.Alert;

public class AlertProcessing {

    public static void showErrorDialog(Exception e, String className) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("Произошла ошибка в: " + className);
        alert.setContentText(e.getMessage());

        alert.showAndWait();
    }

    public static void showInfoDialog(String title, String header, String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(info);
        alert.showAndWait();
    }

}
