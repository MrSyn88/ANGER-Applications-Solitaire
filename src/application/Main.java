package application;
	
import java.util.HashMap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	//Store an arbitrary number of panes and controllers 
	private HashMap<String, Object> paneMap;
	private HashMap<String, SuperController> controllerMap;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//Initialize the hashmap
			paneMap = new HashMap<String, Object>();
			controllerMap = new HashMap<String, SuperController>();
			
			//Create a loader for the main fxml
			FXMLLoader rootLoader = new FXMLLoader();
			rootLoader.setLocation(getClass().getResource("Main.fxml"));
			
			//Load it and put its controller in the controllerMap
			BorderPane rootPane = (BorderPane) rootLoader.load();
			paneMap.put("mainScreen", rootPane);
			controllerMap.put("mainController", rootLoader.getController());
			controllerMap.get("mainController").setAppPaneMap(paneMap);
			Scene rootScene = new Scene(rootPane);
			
			//Create a loader for the title screen
			FXMLLoader titleLoader = new FXMLLoader();
			titleLoader.setLocation(getClass().getResource("Title.fxml"));
			
			//Load it and store both it and its controller
			paneMap.put("titleScreen", (BorderPane) titleLoader.load());
			controllerMap.put("titleController", titleLoader.getController());
			
			//Create a loader for the settings
			FXMLLoader settingsLoader = new FXMLLoader();
			settingsLoader.setLocation(getClass().getResource("Settings.fxml"));
			
			//Load and store as above
			paneMap.put("settingsScreen", (GridPane) settingsLoader.load());
			controllerMap.put("settingsController", settingsLoader.getController());
			
			FXMLLoader gameLoader = new FXMLLoader();
			gameLoader.setLocation(getClass().getResource("Game.fxml"));
			paneMap.put("gameScreen", (BorderPane) gameLoader.load());
			//controllerMap.put("gameController", gameLoader.getController());
			
			//Attempted to create a loader for the how to play
			//FXMLLoader HTPLoader = new FXMLLoader();
			//HTPLoader.setLocation(getClass().getResource("HowToPlay.fxml"));
			
			//Attempted to Load and store as above
			//paneMap.put("HTPScreen", (AnchorPane) settingsLoader.load());
			//controllerMap.put("settingsController", settingsLoader.getController());
			
			//Allow each controller to access every other controller
			for (SuperController currentControl: controllerMap.values()) {
				currentControl.setAppControllerMap(controllerMap);
			}
			
			rootPane.setCenter((Node) paneMap.get("titleScreen"));
			rootScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("ANGER Applications Solitaire");
			primaryStage.setScene(rootScene);
			primaryStage.show();
		} catch(Exception e) {
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
