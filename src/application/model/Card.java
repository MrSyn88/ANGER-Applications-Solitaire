package application.model;

import java.io.File;
import java.util.Map;

public class Card {
	private final Map<String, String> suits = Map.ofEntries(Map.entry("h", "Hearts"), Map.entry("d", "Diamonds"),
			Map.entry("c", "Clubs"), Map.entry("s", "Spades"));
	private final Map<String, String> values = Map.ofEntries(Map.entry("a", "ace.png"), Map.entry("2", "2.png"),
			Map.entry("3", "3.png"), Map.entry("4", "4.png"), Map.entry("5", "5.png"), Map.entry("6", "6.png"),
			Map.entry("7", "7,png"), Map.entry("8", "8.png"), Map.entry("9", "9.png"), Map.entry("t", "10.png"),
			Map.entry("j", "jack.png"), Map.entry("q", "queen.png"), Map.entry("k", "king.png"));

	private String cardSuit;
	private String cardValue;

	/**
	 * @param suit  A one character code to match a suit
	 * @param value The face value of the card
	 * @return A file object that matches the face of this card
	 */
	public File getFaceLocation(String suit, String value) {
		return new File(suits.get(suit) + "/" + values.get(value));
	}

	/**
	 * @return The file pointing to this card's face
	 */
	public File getFaceLocation() {
		return this.getFaceLocation(this.cardSuit, this.cardValue);
	}

}
