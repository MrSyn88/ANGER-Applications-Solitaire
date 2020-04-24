package application.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class GameController extends SuperController{

    @FXML
    private BorderPane gamePane;

    @FXML
    private GridPane gridPane;
    
    @FXML
    private Button DrawCardButton;


    @FXML
    void Draw(ActionEvent event) {
    	System.out.println("Testing the draw button");
    }
    
}
