package application.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.ResourceBundle;

import application.model.Card;
import application.model.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class GameController extends SuperController implements Initializable {

	private List<Double> xLayout;
	private List<Double> yLayout;
	private Double cardHeight;
	private Double cardWidth;
	private List<Integer> drawIndices;
	private List<Integer> foundationIndices;

	@FXML
	private Canvas gameCanvas;
	@FXML
	private Button DrawCardButton;

	@FXML
	void Draw(ActionEvent event) {
		System.out.println("Testing the draw button");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Set variables for the card height and width, with padding
		cardHeight = (double) 90;
		cardWidth = (double) 70;
		// Set the x and y values for the start of each card row & column
		xLayout = new ArrayList<Double>();
		for (int i = 0; i < 7; i++) {
			xLayout.add(i * (cardWidth + 15));
		}
		yLayout = new ArrayList<Double>();
		yLayout.add((double) 0);
		yLayout.add(cardHeight + 20);

		drawIndices = new ArrayList<Integer>();
		drawIndices.add(0);
		drawIndices.add(1);

		foundationIndices = new ArrayList<Integer>();
		foundationIndices.add(3);
		foundationIndices.add(4);
		foundationIndices.add(5);
		foundationIndices.add(6);

		Game newGame = new Game();
		newGame.startNewGame();
		drawCards(newGame);
	}

	public void drawCards(Game runningGame) {
		GraphicsContext currentGC = gameCanvas.getGraphicsContext2D();

		Deque<Card> drawStack = runningGame.getDraw();
		if (drawStack.isEmpty()) {
			currentGC.strokeRoundRect(xLayout.get(0), yLayout.get(0), cardWidth, cardHeight, 10, 10);
		}
		Deque<Card> drawDiscard = runningGame.getDrawDiscard();
		if (drawDiscard.isEmpty()) {
			currentGC.strokeRoundRect(xLayout.get(1), yLayout.get(0), cardWidth, cardHeight, 10, 10);
		}

		for (int i = 0; i < 4; i++) {
			Deque<Card> FoundationStack = runningGame.getAFoundation(i);
			Double currentX = xLayout.get(foundationIndices.get(i));
			Double currentY = yLayout.get(0);
			if (!FoundationStack.isEmpty()) {
				Card topCard = FoundationStack.peek();
				if (topCard.getIsFaceUp()) {
					currentGC.drawImage(new Image(topCard.getFaceLocation().toURI().toString()), currentX, currentY,
							cardWidth, cardHeight);
					System.out
							.println("Adding a card to the canvas at " + topCard.getFaceLocation().toURI().toString());
				}
			} else {
				currentGC.strokeRoundRect(currentX, currentY, cardWidth, cardHeight, 10, 10);
			}
		}

		for (int i = 0; i < 7; i++) {
			Deque<Card> currentStack = runningGame.getAPlayArea(i);
			Double currentX = xLayout.get(i);
			Double currentY = yLayout.get(1);
			int offset = 0;
			for(Card someCard: currentStack) {
				if (someCard.getIsFaceUp()) {
					currentGC.drawImage(new Image(someCard.getFaceLocation().toURI().toString()), currentX, currentY+offset,
							cardWidth, cardHeight);
					System.out
							.println("Adding a card to the canvas at " + someCard.getFaceLocation().toURI().toString());
				} else {
					currentGC.drawImage(new Image(someCard.getFaceLocation().toURI().toString()), currentX, currentY+offset,
							cardWidth, cardHeight);
				}
				offset+=20;
			} 
		}

	}

}
