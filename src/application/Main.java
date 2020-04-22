package application;

import java.io.File;
import java.util.HashMap;

import application.controller.SettingsController;
import application.controller.SuperController;
import application.model.SolitaireSettings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Main extends Application {
	// Store an arbitrary number of panes and controllers
	private HashMap<String, Object> paneMap;
	private HashMap<String, SuperController> controllerMap;
	private SolitaireSettings settingsObject;

	@Override
	public void start(Stage primaryStage) {
		try {
			// Initialize the hashmap
			paneMap = new HashMap<String, Object>();
			controllerMap = new HashMap<String, SuperController>();
			settingsObject = new SolitaireSettings();

			// Create a loader for the main fxml
			FXMLLoader rootLoader = new FXMLLoader();
			rootLoader.setLocation(getClass().getResource("view/Main.fxml"));

			// Load it and put its controller in the controllerMap
			BorderPane rootPane = (BorderPane) rootLoader.load();
			paneMap.put("mainScreen", rootPane);
			controllerMap.put("mainController", rootLoader.getController());
			controllerMap.get("mainController").setAppPaneMap(paneMap);
			Scene rootScene = new Scene(rootPane);

			// Create a loader for the title screen
			FXMLLoader titleLoader = new FXMLLoader();
			titleLoader.setLocation(getClass().getResource("view/Title.fxml"));

			// Load it and store both it and its controller
			paneMap.put("titleScreen", (BorderPane) titleLoader.load());
			controllerMap.put("titleController", titleLoader.getController());

			// Create a loader for the settings
			FXMLLoader settingsLoader = new FXMLLoader();
			settingsLoader.setLocation(getClass().getResource("view/Settings.fxml"));

			// Load and store as above
			paneMap.put("settingsScreen", (GridPane) settingsLoader.load());
			controllerMap.put("settingsController", settingsLoader.getController());

			FXMLLoader gameLoader = new FXMLLoader();
			gameLoader.setLocation(getClass().getResource("view/Game.fxml"));
			paneMap.put("gameScreen", (BorderPane) gameLoader.load());
			controllerMap.put("gameController", gameLoader.getController());

			// Attempted to create a loader for the how to play
			FXMLLoader HTPLoader = new FXMLLoader();
			HTPLoader.setLocation(getClass().getResource("view/HowToPlay.fxml"));

			// Attempted to Load and store as above
			paneMap.put("HTPScreen", (AnchorPane) HTPLoader.load());
			controllerMap.put("HTPController", HTPLoader.getController());
			
			//Load the about fxml
			FXMLLoader AboutLoader = new FXMLLoader();
			AboutLoader.setLocation(getClass().getResource("view/About.fxml"));
			
			//Put references for the about fxml into the appropriate maps
			paneMap.put("AboutScreen", (AnchorPane) AboutLoader.load());
			controllerMap.put("AboutController", AboutLoader.getController());

			// Allow each controller to access every other controller
			for (SuperController currentControl : controllerMap.values()) {
				currentControl.setAppControllerMap(controllerMap);
			}

			// Pass a reference to the settings for the entire application
			((SettingsController) controllerMap.get("settingsController")).setAppSettingsObject(settingsObject);

			rootPane.setCenter((Node) paneMap.get("titleScreen"));
			rootScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("ANGER Applications Solitaire");
			primaryStage.setScene(rootScene);
			primaryStage.show();
			
			String musicFile = "ChillVibes.mp3";     // Plays Roberts playlist

			Media sound = new Media(new File(musicFile).toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.volumeProperty().bind(this.settingsObject.musicVolumeProperty());
			mediaPlayer.play();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public HashMap<String, Object> getPaneMap() {
		return paneMap;
	}

	public HashMap<String, SuperController> getControllerMap() {
		return controllerMap;
	}
}
