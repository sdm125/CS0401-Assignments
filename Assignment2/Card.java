import java.util.Random;

public class Card {

	/*
		Value is an array of all possible card values. Suit is an array of
		all possible suits. cardValue is the amount of points the card is worth.
		cardString is the combination of the value and suit.
	*/

	private String[] value = new String[13];
	private char[] suit = new char[4];
	private int cardValue;
	private String cardString;

	public Card(){
		value = new String[]{ "A","2","3","4","5","6","7","8","9","T","J","Q","K" };
		suit = new char[]{ 'c','d','h','s' };
		cardValue = 0;
		cardString = "";
	}

	// Generates a new random card based on a random index of value + suit arrays

	public void cardGen(){
		Random rand = new Random();
		int randValue = rand.nextInt(13);
		cardString = value[randValue] + suit[rand.nextInt(4)];
	}

	// returns cardString

	public String getCardStr(){
		return cardString;
	}

	// Determines point value of card

	public int getCardVal(){
		int aces = 0;
		if(cardString.charAt(0) == 'J' || cardString.charAt(0) == 'Q' || cardString.charAt(0) == 'K' || cardString.charAt(0) == 'T'){
			cardValue = 10;
		}
		else if(cardString.charAt(0) == 'A'){
			cardValue = 11;
		}
		else{
			cardValue = Character.getNumericValue(cardString.charAt(0));
		}
		return cardValue;
	}

}