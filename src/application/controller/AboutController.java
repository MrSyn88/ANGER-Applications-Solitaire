package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

// controls about screen and allows user to return to home screen
public class AboutController extends SuperController{

    @FXML
    private Button AboutButton;

    @FXML
    void goBack(ActionEvent event) {
    	((MainController) this.appControllerMap.get("mainController")).restoreCenterPane();
    }

}