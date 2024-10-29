package controller.admin;

import controller.HomeFormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class AdminLoginFormController {

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        HomeFormController.homeStage.show();
    }
}
