package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TitleController extends SuperController {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button NewGamePush;

	@FXML
	private Button LeaderboardPush;

	@FXML
	private Button HTPPush;

	@FXML
	private Button SettingsPush;

	@FXML
	void callHowToPlay(ActionEvent event) {
		((MainController) this.getAppControllerMap().get("mainController")).howToPlayEventHandler(event);
	}

	@FXML
	void callOpenPreferences(ActionEvent event) {
		((MainController) this.getAppControllerMap().get("mainController")).openPreferencesEventHandler(event);
	}

	@FXML
	void callStartNewGame(ActionEvent event) throws IOException {
		// Load the mainController from the hashmap, then call the startNewGame method,
		// passing the event
		((MainController) this.getAppControllerMap().get("mainController")).startNewGame(event);
	}

	@FXML
	void loadLeaderBoard(ActionEvent event) {
		
	}

	@FXML
	void initialize() {

	}

}
