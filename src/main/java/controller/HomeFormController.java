package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HomeFormController {

    public static Stage homeStage;

    private void hideHomeStage(ActionEvent event){
        homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        homeStage.hide();
    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void btnAdminLoginFormOnAction(ActionEvent event) {
        try {
            Stage stage=new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AdminLogin.fxml"))));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            stage.setResizable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        hideHomeStage(event);
    }

    @FXML
    void btnEmployeeLoginFormOnAction(ActionEvent event) {
        try {
            Stage stage=new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/EmployeeLogin.fxml"))));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            stage.setResizable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        hideHomeStage(event);
    }

}
