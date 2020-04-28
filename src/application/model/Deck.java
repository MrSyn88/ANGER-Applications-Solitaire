package application.model;

import java.util.ArrayList;
import java.util.Collections;

public class Deck extends ArrayList<Card> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3948168530009032981L;

	/**
	 * Initialize the deck to contain every card
	 */
	public Deck() {
		for (String currentSuit : Card.getSuitsCodes()) {
			for (String currentValue : Card.getValuesCodes()) {
				this.add(new Card(currentSuit, currentValue));
			}
		}
		this.shuffle();

	}

	public Deck(Deck oldDeck) {
		for (Card currentCard : oldDeck) {
			this.add(new Card(currentCard.getCardSuit(), currentCard.getCardValue()));
		}
	}

	public void shuffle() {
		Collections.shuffle(this);
	}

}
