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
import application.model.SolitaireSettings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class GameController extends SuperController implements Initializable {

	private List<Double> xLayout;
	private List<Double> yLayout;
	private Double cardHeight;
	private Double cardWidth;
	private List<Integer> drawIndices;
	private List<Integer> foundationIndices;
	private SolitaireSettings appSettingsObject;
	private Game newGame;
	private Deque<Card> srcStack;
	private Card cardToMove;
	// private Game CurrentGame;

	@FXML
	private Canvas gameCanvas;
	@FXML
	private Button DrawCardButton;

	@FXML
	void dropCard(MouseEvent event) {
		System.out.println("In getDest");
		Deque<Card> destStack = null;

		if (event.getY() < this.yLayout.get(1)) {
			// If it's within the draw box, do this
			if (event.getX() > this.xLayout.get(foundationIndices.get(0))) {
				Double tempX = event.getX();
				Integer foundationNum;
				// Transform the X value for this index to find the relevant foundation number
				tempX = tempX - this.xLayout.get(foundationIndices.get(0));
				tempX = tempX / (cardWidth + 15);
				foundationNum = ((Double) Math.floor(tempX)).intValue();
				System.out.println("Foundation number: " + foundationNum.toString());
				destStack = newGame.getAFoundation(foundationNum);
				newGame.moveCardToFoundation(cardToMove, srcStack, destStack);
				System.out.println(srcStack);
				System.out.println(destStack);
			}
		}

		drawCards();
	}

	@FXML
	void pickUpCard(MouseEvent event) {
		System.out.println("In getSrc");
		this.srcStack = null;
		if (event.getY() < this.yLayout.get(1)) {
			// If it's within the draw box, do this
			if (event.getX() > this.xLayout.get(1) && event.getX() < this.xLayout.get(3)) {
				srcStack = this.newGame.getDrawDiscard();
				cardToMove = srcStack.peek();
			} else if (event.getX() > this.xLayout.get(foundationIndices.get(0))) {
				Double tempX = event.getX();
				Integer foundationNum;
				// Transform the X value for this index to find the relevant foundation number
				tempX = tempX - this.xLayout.get(foundationIndices.get(0));
				tempX = tempX / (cardWidth + 15);
				foundationNum = ((Double) Math.floor(tempX)).intValue();
				System.out.println("Foundation number: " + foundationNum.toString());
				srcStack = newGame.getAFoundation(foundationNum);
				cardToMove = srcStack.peek();

			}
		}
	}

	@FXML
	void dragCard(MouseEvent event) {
	}

	@FXML
	void Draw(ActionEvent event) {

		newGame.drawNextCard();
		drawCards();

	}

	public void setGame(Game game) {
		newGame = game;
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

		newGame = new Game();
		newGame.startNewGame();
	}

	/**
	 * 
	 */
	public void drawCards() {
		// Clear the entire canvas, so we don't get any duplicate cards
		GraphicsContext gc = gameCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

		// Draw the whole table for the game
		drawTop(this.newGame);
		drawStacks(this.newGame);
	}

	/**
	 * @param runningGame
	 */
	public void drawTop(Game runningGame) {
		GraphicsContext currentGC = gameCanvas.getGraphicsContext2D();

		Deque<Card> drawStack = runningGame.getDraw();
		Double currentX = xLayout.get(0);
		Double currentY = yLayout.get(0);
		if (drawStack.isEmpty()) {
			currentGC.strokeRoundRect(currentX, currentY, cardWidth, cardHeight, 10, 10);
		} else {
			currentGC.drawImage(new Image(appSettingsObject.getSelectedCardBack().toURI().toString()), currentX,
					currentY, cardWidth, cardHeight);
		}
		Deque<Card> drawDiscard = runningGame.getDrawDiscard();
		currentX = xLayout.get(1);
		if (drawDiscard.isEmpty()) {
			currentGC.strokeRoundRect(currentX, currentY, cardWidth, cardHeight, 10, 10);
		} else {
			Card topCard = drawDiscard.peek();
			currentGC.drawImage(new Image(topCard.getFaceLocation().toURI().toString()), currentX, currentY, cardWidth,
					cardHeight);
		}

	}

	public void drawStacks(Game runningGame) {
		GraphicsContext currentGC = gameCanvas.getGraphicsContext2D();
		Double currentX;
		Double currentY;
		for (int i = 0; i < 4; i++) {
			Deque<Card> FoundationStack = runningGame.getAFoundation(i);
			currentX = xLayout.get(foundationIndices.get(i));
			currentY = yLayout.get(0);
			if (!FoundationStack.isEmpty()) {
				Card topCard = FoundationStack.peek();

				if (topCard.getIsFaceUp()) {
					currentGC.drawImage(new Image(topCard.getFaceLocation().toURI().toString()), currentX, currentY,
							cardWidth, cardHeight);
				}
			} else {
				currentGC.strokeRoundRect(currentX, currentY, cardWidth, cardHeight, 10, 10);
			}
		}

		for (int i = 0; i < 7; i++) {
			Deque<Card> currentStack = runningGame.getAPlayArea(i);

			currentX = xLayout.get(i);
			currentY = yLayout.get(1);
			int offset = 0;
			for (Card someCard : currentStack) {
				if (someCard.getIsFaceUp()) {
					currentGC.drawImage(new Image(someCard.getFaceLocation().toURI().toString()), currentX,
							currentY + offset,

							cardWidth, cardHeight);
				} else {
					currentGC.drawImage(new Image(someCard.getFaceLocation().toURI().toString()), currentX,
							currentY + offset, cardWidth, cardHeight);
				}
				offset += 20;
			}
		}

	}

	/**
	 * @return the appSettingsObject
	 */
	public SolitaireSettings getAppSettingsObject() {
		return appSettingsObject;
	}

	/**
	 * @param appSettingsObject the appSettingsObject to set
	 */
	public void setAppSettingsObject(SolitaireSettings appSettingsObject) {
		this.appSettingsObject = appSettingsObject;
		this.newGame.getDrawTypeProperty().bind(this.appSettingsObject.getDrawTypeProperty());
	}

	/**
	 * @return the newGame
	 */
	public Game getNewGame() {
		return newGame;
	}

	/**
	 * @param newGame the newGame to set
	 */
	public void setNewGame(Game newGame) {
		this.newGame = newGame;
	}

}
