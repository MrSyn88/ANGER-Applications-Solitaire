package application.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Game {
	private Deck gameDeck;
	private List<Deque<Card>> foundations;
	private Deque<Card> draw;
	private Deque<Card> tempCardStack;

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
		// Add a new Deque to hold cards that are being moved
		this.tempCardStack = new ArrayDeque<Card>();

		// Initialize the 7 card Deques for the play area
		this.playArea = new ArrayList<Deque<Card>>(7);
		for (int i = 0; i < 7; i++) {
			this.playArea.add(i, new ArrayDeque<Card>(i + 1));
		}
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

}
