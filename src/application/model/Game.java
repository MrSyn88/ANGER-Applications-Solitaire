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
	private Deque<Card> drawDiscardStack;

	private IntegerProperty drawNumber;

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
		this.drawDiscardStack = new ArrayDeque<Card>();
		// Add a new Deque to hold cards that are being moved
		this.tempCardStack = new ArrayDeque<Card>();

		// Initialize the 7 card Deques for the play area
		this.playArea = new ArrayList<Deque<Card>>(7);
		for (int i = 0; i < 7; i++) {
			this.playArea.add(i, new ArrayDeque<Card>(i + 1));
		}
		this.drawNumber = new SimpleIntegerProperty();
		this.setDrawNumber(3);
	}

	public void startNewGame() {
		for (int i = 0; i < 7; i++) {
			for (int j = i; j < 7; j++) {
				int lastElement = gameDeck.size() - 1;
				this.playArea.get(j).add(this.gameDeck.get(lastElement));
				this.gameDeck.remove(lastElement);
				if (j == i) {
					this.playArea.get(j).getLast().setIsFaceUp(true);
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
			for (Card currentCard : this.drawDiscardStack) {
				currentCard.setIsFaceUp(false);
				this.draw.push(currentCard);
			}
			this.drawDiscardStack.clear();
			return;
		}

		// Otherwise, move up to the draw number of cards from the draw stack to the
		// draw discard stack
		for (int i = 0; i < this.getDrawNumber() && !this.draw.isEmpty(); i++) {
			this.draw.peek().setIsFaceUp(true);
			this.drawDiscardStack.push(this.draw.pop());
		}
	}

	/**
	 * @param cardToMove
	 * @param sourceColumn
	 * @param destColumn
	 */
	public void moveCardToStack(Card cardToMove, Integer sourceColumn, Integer destColumn) {

		Deque<Card> source = this.playArea.get(sourceColumn);
		Deque<Card> dest = this.playArea.get(destColumn);
		// Can't move a card if it's not face up
		if (!cardToMove.getIsFaceUp() || !cardToMove.isValidTableCard(dest.peek())) {
			return;
		}

		while (!source.peek().equals(cardToMove)) {
			this.tempCardStack.push(source.pop());
		}
		dest.push(cardToMove);
		for (Card currentCard : this.tempCardStack) {
			dest.push(currentCard);
		}
		this.tempCardStack.clear();
	}

	public void moveCardToFoundation(Card cardToMove, Integer sourceColumn, Integer destFoundation) {
		Deque<Card> source = this.playArea.get(sourceColumn);
		Deque<Card> dest = this.foundations.get(destFoundation);
		// Can't move a card if it's not face up
		if (!cardToMove.getIsFaceUp() || !cardToMove.isValidFoundationCard(dest.peek())) {
			return;
		} else if (source.peek() != cardToMove) {
			return;
		}
	}

	public void moveCardFromDraw(Card cardToMove) {

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
	 * @return the drawDiscardStack
	 */
	public Deque<Card> getDrawDiscardStack() {
		return drawDiscardStack;
	}

	/**
	 * @param drawDiscardStack the drawDiscardStack to set
	 */
	public void setDrawDiscardStack(Deque<Card> drawDiscardStack) {
		this.drawDiscardStack = drawDiscardStack;
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
	 * @return the drawNumber
	 */
	public Integer getDrawNumber() {
		return drawNumber.getValue();
	}

	/**
	 * @param drawNumber the drawNumber to set
	 */
	public void setDrawNumber(Integer drawNumber) {
		this.drawNumber.setValue(drawNumber);
	}

	public IntegerProperty getDrawNumberProperty() {
		if (this.drawNumber == null) {
			this.drawNumber = new SimpleIntegerProperty();
		}
		return this.drawNumber;
	}

}
