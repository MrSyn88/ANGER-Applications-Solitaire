package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HowToPlayController extends SuperController{

    @FXML
    private Button HTPButton;

    @FXML
    void goBack(ActionEvent event) {
    	((MainController) this.appControllerMap.get("mainController")).restoreCenterPane();
    }

}