package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.model.Game;
import javafx.fxml.Initializable;

public class GameController extends SuperController implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Game newGame = new Game();
		newGame.startNewGame();
	}

}
