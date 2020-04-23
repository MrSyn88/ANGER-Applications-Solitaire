package application.model;

import java.io.File;
import java.util.Map;

public class Card {
	private static final Map<String, String> suitsMap = Map.ofEntries(Map.entry("h", "Hearts"),
			Map.entry("d", "Diamonds"), Map.entry("c", "Clubs"), Map.entry("s", "Spades"));
	private static final Map<String, Integer> valuesMap = Map.ofEntries(Map.entry("a", 1), Map.entry("2", 2),
			Map.entry("3", 3), Map.entry("4", 4), Map.entry("5", 5), Map.entry("6", 6), Map.entry("7", 7),
			Map.entry("8", 8), Map.entry("9", 9), Map.entry("t", 10), Map.entry("j", 11), Map.entry("q", 12),
			Map.entry("k", 13));
	private static final String fileExtension = ".png";

	private String cardSuit;
	private String cardValue;
	private Boolean isRed;
	private Boolean isFaceUp;

	/**
	 * @param suit  A one character code to match a suit
	 * @param value The face value of the card
	 * @return A file object for the given suit and value
	 */
	public static File getFaceLocation(String suit, String value) {
		return new File(suitsMap.get(suit) + "/" + value + suit + fileExtension);
	}

	/**
	 * @return A file object that matches the face of this card
	 */
	public File getFaceLocation() {
		return Card.getFaceLocation(this.cardSuit, this.cardValue);
	}

	/**
	 * Function to return whether it is valid to put this card on top of any given
	 * foundation stack; That is, whether is it possible to put them "away" or out
	 * of play
	 * 
	 * @param otherCard
	 * @return
	 */
	public Boolean isValidFoundationCard(Card otherCard) {
		Integer thisCardValue = Card.valuesMap.get(this.getCardValue());
		Integer otherCardValue = Card.valuesMap.get(otherCard.getCardValue());
		return (thisCardValue == (otherCardValue + 1) && this.getCardSuit() == otherCard.getCardSuit());

	}

	/**
	 * Function to return whether it is valid to put a card on top of another card
	 * that is face-up and in play
	 * 
	 * @param otherCard
	 * @return
	 */
	public Boolean isValidTableCard(Card otherCard) {
		Integer thisCardValue = Card.valuesMap.get(this.getCardValue());
		Integer otherCardValue = Card.valuesMap.get(otherCard.getCardValue());
		// Return true if the other card is one higher than this card and they are not
		// the same color
		return (thisCardValue == (otherCardValue - 1) && this.getIsRed() != otherCard.getIsRed());

	}

	/**
	 * @param suit  One character string representation of the suit of the card to
	 *              create
	 * @param value One character string representation of the value of the card to
	 *              create
	 */
	public Card(String suit, String value) {
		this.setCardSuit(suit);
		this.setCardValue(value);
		this.setIsFaceUp(false);
		if (suit == "h" || suit == "d") {
			this.setIsRed(true);
		} else {
			this.setIsRed(false);
		}
	}

	/**
	 * @param otherCard
	 * @return
	 */
	public Boolean equals(Card otherCard) {
		return (this.cardSuit.equals(otherCard.getCardSuit()) && this.cardValue.equals(otherCard.getCardValue()));
	}

	/**
	 * @return the cardSuit
	 */
	public String getCardSuit() {
		return cardSuit;
	}

	/**
	 * @param cardSuit the cardSuit to set
	 */
	public void setCardSuit(String cardSuit) {
		this.cardSuit = cardSuit;
	}

	/**
	 * @return the cardValue
	 */
	public String getCardValue() {
		return cardValue;
	}

	/**
	 * @param cardValue the cardValue to set
	 */
	public void setCardValue(String cardValue) {
		this.cardValue = cardValue;
	}

	/**
	 * @return the isFaceUp
	 */
	public Boolean getIsFaceUp() {
		return isFaceUp;
	}

	/**
	 * @param isFaceUp the isFaceUp to set
	 */
	public void setIsFaceUp(Boolean isFaceUp) {
		this.isFaceUp = isFaceUp;
	}

	/**
	 * @return the isRed
	 */
	public Boolean getIsRed() {
		return isRed;
	}

	/**
	 * @param isRed the isRed to set
	 */
	public void setIsRed(Boolean isRed) {
		this.isRed = isRed;
	}

}
