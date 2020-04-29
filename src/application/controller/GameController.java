package application.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
/**
 * 
 * The gameController is where all the code for the actual game is implemented.
 * This varies from picking up cards and dropping them to redrawing the deck.
 *
 */
public class GameController extends SuperController implements Initializable {

	private List<Double> xLayout;
	private List<Double> yLayout;
	private Double cardHeight;
	private Double cardWidth;
	private List<Integer> drawIndices;
	private List<Integer> foundationIndices;
	private SolitaireSettings appSettingsObject;
	private Game currentGame;
	private Deque<Card> srcStack;
	private Card cardToMove;
	private Integer cardYOffset;
	private Integer xMargin;
	private Integer yMargin;

	@FXML
	private Pane gamePane;
	@FXML
	private Canvas gameCanvas;
	@FXML
	private Button DrawCardButton;

	/**
	 * @param event
	 */ 
	
	// checks to see if the player's card move is valid and drops the selected card onto the stack
	@FXML
	void dropCard(MouseEvent event) {
		Deque<Card> destStack = null;

		if (event.getY() < this.yLayout.get(1)) {
			// The only place you can put things in the top row are the foundations
			if (event.getX() > this.xLayout.get(foundationIndices.get(0))) {
				// Store the X to manipulate it
				Double tempX = event.getX();
				Integer foundationNum;
				// Transform the X value for this index to find the relevant foundation number
				tempX = tempX - this.xLayout.get(foundationIndices.get(0));
				tempX = tempX / (cardWidth + 15);
				foundationNum = ((Double) Math.floor(tempX)).intValue();

				// Set the destination stack based on where the user clicked, then move
				destStack = currentGame.getAFoundation(foundationNum);
				currentGame.moveCardToFoundation(cardToMove, srcStack, destStack);
			}
		} else { // Handle putting cards in the stack here
			// Store the X to manipulate
			Double tempX = event.getX();

			// Calculate which stack to grab cards from
			tempX = tempX / (cardWidth + 15);
			Integer stackNum = ((Double) Math.floor(tempX)).intValue();
			destStack = currentGame.getAPlayArea(stackNum);

			if (destStack == srcStack) {
				drawCards();
				return;
			} else {
				currentGame.moveCardToStack(cardToMove, srcStack, destStack);
			}

		}

		drawCards();
	}

	/**
	 * @param event
	 */
	
	// allows player to click on a card to move and stores it in stack to determine if valid move
	@FXML
	void pickUpCard(MouseEvent event) {
		this.srcStack = null;
		if (event.getY() < this.yLayout.get(1)) {
			// If it's within the draw box, do this
			if (event.getX() > this.xLayout.get(1) && event.getX() < this.xLayout.get(3)) {
				srcStack = this.currentGame.getDrawDiscard();
				cardToMove = srcStack.peek();
			} else if (event.getX() > this.xLayout.get(foundationIndices.get(0))) { // If it's in one of the
																					// foundations, do this
				// Store the X to manipulate it
				Double tempX = event.getX();
				Integer foundationNum;

				// Transform the X value for this index to find the relevant foundation number
				tempX = tempX - this.xLayout.get(foundationIndices.get(0));
				tempX = tempX / (cardWidth + 15);
				foundationNum = ((Double) Math.floor(tempX)).intValue();

				// Set the source stack based on the foundation number calculated
				srcStack = currentGame.getAFoundation(foundationNum);
				cardToMove = srcStack.peek();

			}
		} else { // Handle getting cards from the stack in here
					// Store the X to manipulate
			Double tempX = event.getX();

			// Calculate which stack to grab cards from
			tempX = tempX / (cardWidth + 15);
			Integer stackNum = ((Double) Math.floor(tempX)).intValue();
			srcStack = currentGame.getAPlayArea(stackNum);

			// Figure out where in the stack the user clicked
			Double bottomOfStack = ((srcStack.size() - 1) * cardYOffset) + cardWidth;
			Double startOfLastCard = (double) ((srcStack.size() - 1) * cardYOffset);
			Double tempY = event.getY();
			tempY -= this.yLayout.get(1);

			if (tempY > startOfLastCard && tempY < bottomOfStack) {
				cardToMove = srcStack.peek();
			} else if (tempY < startOfLastCard) {
				int i = 0;
				Integer index = ((Double) Math.floor(((startOfLastCard - tempY) / cardYOffset))).intValue() + 1;
				Card currentCard = srcStack.peek();
				for (Iterator<Card> stkIt = srcStack.iterator(); stkIt.hasNext() && i <= index;) {

					currentCard = stkIt.next();
					i++;
				}
				cardToMove = currentCard;
			}

		}

	}

	/**
	 * @param event
	 */ 
	// allows player to move a selected card to a location
	@FXML
	void dragCard(MouseEvent event) {
	}

	/**
	 * @param event
	 */
	// draws cards from pile in top left of screen
	@FXML
	void Draw(ActionEvent event) {

		currentGame.drawNextCard();
		drawCards();

	}

	/**
	 * @param game
	 */ 
	// sets the game to whatever is chosen by the player
	public void setGame(Game game) {
		currentGame = game;
	}

	/**
	 *
	 */
	// sets up the start of a new game
	@Override 
	public void initialize(URL arg0, ResourceBundle arg1) {
		// gamePane.setPrefWidth(900);
		// gamePane.setPrefHeight(650);

		gameCanvas.widthProperty().bind(gamePane.widthProperty());
		gameCanvas.heightProperty().bind(gamePane.heightProperty());
		gameCanvas.widthProperty().addListener(evt -> updateValues());
		gameCanvas.heightProperty().addListener(evt -> updateValues());
		// Set variables for the card height and width, with padding
		cardHeight = (double) 90;
		cardWidth = (double) 70;
		cardYOffset = 20;
		xMargin = 15;
		yMargin = 20;
		// Set the x and y values for the start of each card row & column
		xLayout = new ArrayList<Double>();
		for (int i = 0; i < 7; i++) {
			xLayout.add(i * (cardWidth + xMargin));
		}
		yLayout = new ArrayList<Double>();
		yLayout.add((double) 0);
		yLayout.add(cardHeight + yMargin);

		drawIndices = new ArrayList<Integer>();
		drawIndices.add(0);
		drawIndices.add(1);

		foundationIndices = new ArrayList<Integer>();
		foundationIndices.add(3);
		foundationIndices.add(4);
		foundationIndices.add(5);
		foundationIndices.add(6);

	}

	/**
	 * 
	 */ 
	// displays card in an offset pattern which displays previous and current cards
	public void updateValues() {

		cardWidth = gameCanvas.getWidth() / 8;
		cardHeight = cardWidth * 1.4529;
		cardYOffset = (int) (cardHeight / 8);

		for (int i = 0; i < 7; i++) {
			xLayout.set(i, i * (cardWidth + xMargin));
		}
		yLayout.set(0, (double) 0);
		yLayout.set(1, cardHeight + yMargin);

		drawCards();

	}

	/**
	 * 
	 */ 
	// checks to see if the card stack is empty and if it is then the current game is over
	public void drawCards() {
		// Clear the entire canvas, so we don't get any duplicate cards
		GraphicsContext gc = gameCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
		if (this.currentGame != null) {
			// Draw the whole table for the game
			drawTop(this.currentGame);
			drawStacks(this.currentGame);
			if (this.currentGame.checkGameComplete()) {
				Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION, "You have won the game!");
				gameOverAlert.show();
			}
		}
	}

	/**
	 * @param runningGame
	 */
	
	// make current card available to be selected from stack
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

	/**
	 * @param runningGame
	 */ 
	// draws completed ordered decks of cards 
	public void drawStacks(Game runningGame) {
		GraphicsContext currentGC = gameCanvas.getGraphicsContext2D();
		Double currentX;
		Double currentY;
		//Put the foundation stack and populate accordingly
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
		//Populate the game deck
		for (int i = 0; i < 7; i++) {
			Deque<Card> currentStack = runningGame.getAPlayArea(i);

			currentX = xLayout.get(i);
			currentY = yLayout.get(1);
			int offset = 0;
			Card someCard;
			for (Iterator<Card> it = currentStack.descendingIterator(); it.hasNext();) {
				someCard = it.next();
				if (someCard.getIsFaceUp()) {
					currentGC.drawImage(new Image(someCard.getFaceLocation().toURI().toString()), currentX,
							currentY + offset, cardWidth, cardHeight);
				} else {
					currentGC.drawImage(new Image(appSettingsObject.getSelectedCardBack().toURI().toString()), currentX,
							currentY + offset, cardWidth, cardHeight);
				}

				offset += cardYOffset;
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
	}

	/**
	 * @return the currentGame
	 */
	public Game getCurrentGame() {
		return currentGame;
	}

	/**
	 * @param newGame The new Game to set currentGame to
	 */
	public void setCurrentGame(Game newGame) {
		this.currentGame = newGame;
		this.currentGame.getDrawTypeProperty().bind(this.appSettingsObject.getDrawTypeProperty());
	}

}
