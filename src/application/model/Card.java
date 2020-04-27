package application.model;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Card {
	private static final Map<String, String> suitsMap;
	private static final Map<String, Integer> valuesMap;
	private static final String fileExtension = ".png";

	static {
		valuesMap = new HashMap<String, Integer>();
		valuesMap.put("a", 1);
		valuesMap.put("2", 2);
		valuesMap.put("3", 3);
		valuesMap.put("4", 4);
		valuesMap.put("5", 5);
		valuesMap.put("6", 6);
		valuesMap.put("7", 7);
		valuesMap.put("8", 8);
		valuesMap.put("9", 9);
		valuesMap.put("t", 10);
		valuesMap.put("j", 11);
		valuesMap.put("q", 12);
		valuesMap.put("k", 13);

		suitsMap = new HashMap<String, String>();
		suitsMap.put("h", "Hearts");
		suitsMap.put("d", "Diamonds");
		suitsMap.put("c", "Clubs");
		suitsMap.put("s", "Spades");

	}

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
		return new File("src/application/view/" + suitsMap.get(suit) + "/" + value + suit + fileExtension);
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
		if (otherCard == null) {
			return (thisCardValue == 1);
		}
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
		if (otherCard == null) {
			return (thisCardValue == 13);
		}
		Integer otherCardValue = Card.valuesMap.get(otherCard.getCardValue());
		// Return true if the other card is one higher than this card and they are not
		// the same color
		return (thisCardValue == (otherCardValue - 1) && this.getIsRed() != otherCard.getIsRed());

	}

	public static Collection<String> getSuitsCodes() {
		return suitsMap.keySet();
	}

	public static Collection<String> getValuesCodes() {
		return valuesMap.keySet();
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

	public String toString() {
		return this.getCardValue() + this.getCardSuit();
	}
}
