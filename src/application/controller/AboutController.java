package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

// controls about screen and allows user to return to home screen
public class AboutController extends SuperController{

    @FXML
    private Button AboutButton;
    
    @FXML
    private GridPane AboutPane;
    //Returns to previous pane
    @FXML
    void goBack(ActionEvent event) {
    	((MainController) this.appControllerMap.get("mainController")).restoreCenterPane();
    }

}