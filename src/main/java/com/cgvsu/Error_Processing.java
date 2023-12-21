package com.cgvsu;

import javafx.scene.control.Alert;

public class Error_Processing {

    public void showErrorDialog(Exception e, String className) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("Произошла ошибка в: " + className);
        alert.setContentText(e.getMessage());

        alert.showAndWait();
    }

}
