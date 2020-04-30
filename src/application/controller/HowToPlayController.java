package application.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controls the (How to play) screen and allows the user to return to the previous pane.
 * */

public class HowToPlayController extends SuperController{

    @FXML
    private Button HTPButton;
    
    //Calls previous pane
    @FXML
    private void goBack(ActionEvent event) {
    	((MainController) this.appControllerMap.get("mainController")).restoreCenterPane();
    }

}