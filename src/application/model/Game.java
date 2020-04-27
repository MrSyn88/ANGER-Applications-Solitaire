package application.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Game {
	private Deck gameDeck;
	private List<Deque<Card>> foundations;
	private Deque<Card> draw;
	private Deque<Card> tempCardStack;
	private Deque<Card> drawDiscard;

	private IntegerProperty drawType;
	private static Integer[] drawNumber = { 1, 3 };

	private List<Deque<Card>> playArea;

	public Game() {
		// Create a new Deck
		setGameDeck(new Deck());
		// Initialize the "foundations", or the 4 spaces to put each solved suit, by
		// created a new Deque for each position
		this.foundations = new ArrayList<Deque<Card>>();
		for (int i = 0; i < 4; i++) {
			this.foundations.add(new ArrayDeque<Card>());
		}

		// Add a new Deque for the draw pile
		this.draw = new ArrayDeque<Card>();
		this.drawDiscard = new ArrayDeque<Card>();
		// Add a new Deque to hold cards that are being moved
		this.tempCardStack = new ArrayDeque<Card>();

		// Initialize the 7 card Deques for the play area
		this.playArea = new ArrayList<Deque<Card>>(7);
		for (int i = 0; i < 7; i++) {
			this.playArea.add(i, new ArrayDeque<Card>(i + 1));
		}
		this.drawType = new SimpleIntegerProperty();
		this.setDrawType(3);
	}

	public void startNewGame() {
		for (int i = 0; i < 7; i++) {
			for (int j = i; j < 7; j++) {
				int lastElement = gameDeck.size() - 1;
				this.playArea.get(j).add(this.gameDeck.get(lastElement));
				this.gameDeck.remove(lastElement);
				if (j == i) {
					this.playArea.get(j).peek().setIsFaceUp(true);
				}
			}
		}
		this.draw.addAll(gameDeck);
	}

	/**
	 * Function that draws the next card from the top of the draw pile
	 * 
	 */
	public void drawNextCard() {
		// If the draw pile is empty, then move all cards from the discard stack to the
		// draw stack
		if (this.draw.isEmpty()) {
			for (Card currentCard : this.drawDiscard) {
				currentCard.setIsFaceUp(false);
				this.draw.push(currentCard);
			}
			this.drawDiscard.clear();
			return;
		}

		// Otherwise, move up to the draw number of cards from the draw stack to the
		// draw discard stack
		for (int i = 0; i < Game.drawNumber[this.getDrawType()] && !this.draw.isEmpty(); i++) {
			this.draw.peek().setIsFaceUp(true);
			this.drawDiscard.push(this.draw.pop());
		}
	}

	/**
	 * @param cardToMove
	 * @param sourceColumn
	 * @param destColumn
	 */
	public void moveCardToStack(Card cardToMove, Deque<Card> source, Deque<Card> dest) {
		if (source == dest) {
			return;
		}
		// Can't move a card if it's not face up
		if (!cardToMove.getIsFaceUp() || !cardToMove.isValidTableCard(dest.peek())) {
			return;
		}

		while (!source.peek().equals(cardToMove)) {
			this.tempCardStack.push(source.pop());
		}
		dest.push(source.pop());
		for (Card currentCard : this.tempCardStack) {
			dest.push(currentCard);
		}
		this.tempCardStack.clear();
		if (!source.isEmpty())
			source.peek().setIsFaceUp(true);
	}

	/**
	 * @param cardToMove
	 * @param source
	 * @param dest
	 */
	public void moveCardToFoundation(Card cardToMove, Deque<Card> source, Deque<Card> dest) {
		if (source == dest) {
			return;
		} else if (!cardToMove.getIsFaceUp() || !cardToMove.isValidFoundationCard(dest.peek())) {
			// Can't move a card if it's not face up
			return;
		} else if (source.peek() != cardToMove) {
			return;
		}

		dest.push(source.pop());
		if (!source.isEmpty())
			source.peek().setIsFaceUp(true);
	}

	public Boolean checkGameComplete() {
		Boolean isGameDone = true;
		for (Deque<Card> currentPlayArea : this.playArea) {
			isGameDone = isGameDone && currentPlayArea.isEmpty();
		}

		isGameDone = isGameDone && this.draw.isEmpty();
		isGameDone = isGameDone && this.drawDiscard.isEmpty();
		isGameDone = isGameDone && this.tempCardStack.isEmpty();

		return isGameDone;

	}

	/**
	 * @return the gameDeck
	 */
	public Deck getGameDeck() {
		return gameDeck;
	}

	/**
	 * @param gameDeck the gameDeck to set
	 */
	public void setGameDeck(Deck gameDeck) {
		this.gameDeck = gameDeck;
	}

	/**
	 * @return the draw
	 */
	public Deque<Card> getDraw() {
		return draw;
	}

	/**
	 * @param draw the draw to set
	 */
	public void setDraw(Deque<Card> draw) {
		this.draw = draw;
	}

	/**
	 * @return the foundations
	 */
	public List<Deque<Card>> getFoundations() {
		return foundations;
	}

	/**
	 * @param index
	 * @return
	 */
	public Deque<Card> getAFoundation(Integer index) {
		return foundations.get(index);
	}

	/**
	 * @return the drawDiscard
	 */
	public Deque<Card> getDrawDiscard() {
		return drawDiscard;
	}

	/**
	 * @param drawDiscard the drawDiscard to set
	 */
	public void setDrawDiscard(Deque<Card> drawDiscard) {
		this.drawDiscard = drawDiscard;
	}

	/**
	 * @return the tempCardStack
	 */
	public Deque<Card> getTempCardStack() {
		return tempCardStack;
	}

	/**
	 * @return the playArea
	 */
	public List<Deque<Card>> getPlayArea() {
		return playArea;
	}

	/**
	 * @param index
	 * @return
	 */
	public Deque<Card> getAPlayArea(Integer index) {
		return playArea.get(index);
	}

	/**
	 * @return the drawType
	 */
	public Integer getDrawType() {
		return drawType.getValue();
	}

	/**
	 * @param drawType the drawType to set
	 */
	public void setDrawType(Integer drawNumber) {
		this.drawType.setValue(drawNumber);
	}

	public IntegerProperty getDrawTypeProperty() {
		if (this.drawType == null) {
			this.drawType = new SimpleIntegerProperty();
		}
		return this.drawType;
	}

}
