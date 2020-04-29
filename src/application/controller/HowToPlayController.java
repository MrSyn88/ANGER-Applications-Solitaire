package application.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

// controls how to play screen and allows user to return to home screen
public class HowToPlayController extends SuperController{

    @FXML
    private Button HTPButton;

    @FXML
    void goBack(ActionEvent event) {
    	((MainController) this.appControllerMap.get("mainController")).restoreCenterPane();
    }

}