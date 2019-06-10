package com.mak.baca.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	
	private ArrayList<BaccaratCards> deckOfCards;
	
	/*public Deck(){
		deckOfCards = new ArrayList<String>();
		deckOfCards.add("AS");
		deckOfCards.add("2S");
		deckOfCards.add("3S");
		deckOfCards.add("4S");
		deckOfCards.add("5S");
		deckOfCards.add("6S");
		deckOfCards.add("7S");
		deckOfCards.add("8S");
		deckOfCards.add("9S");
		deckOfCards.add("10S");
		deckOfCards.add("JS");
		deckOfCards.add("QS");
		deckOfCards.add("KS");
		deckOfCards.add("AC");
		deckOfCards.add("2C");
		deckOfCards.add("3C");
		deckOfCards.add("4C");
		deckOfCards.add("5C");
		deckOfCards.add("6C");
		deckOfCards.add("7C");
		deckOfCards.add("8C");
		deckOfCards.add("9C");
		deckOfCards.add("10C");
		deckOfCards.add("JC");
		deckOfCards.add("QC");
		deckOfCards.add("KC");
		deckOfCards.add("AH");
		deckOfCards.add("2H");
		deckOfCards.add("3H");
		deckOfCards.add("4H");
		deckOfCards.add("5H");
		deckOfCards.add("6H");
		deckOfCards.add("7H");
		deckOfCards.add("8H");
		deckOfCards.add("9H");
		deckOfCards.add("10H");
		deckOfCards.add("JH");
		deckOfCards.add("QH");
		deckOfCards.add("KH");
		deckOfCards.add("AD");
		deckOfCards.add("2D");
		deckOfCards.add("3D");
		deckOfCards.add("4D");
		deckOfCards.add("5D");
		deckOfCards.add("6D");
		deckOfCards.add("7D");
		deckOfCards.add("8D");
		deckOfCards.add("9D");
		deckOfCards.add("10D");
		deckOfCards.add("JD");
		deckOfCards.add("QD");
		deckOfCards.add("KD");
	}*/
	
	public Deck(){
		deckOfCards = new ArrayList<BaccaratCards>();
		for(BaccaratCards.Suit s: BaccaratCards.Suit.values()){
            for(BaccaratCards.Rank r: BaccaratCards.Rank.values()){
            	deckOfCards.add(new BaccaratCards(r,s));
            }
        }
	}
	
	public void shuffleDeck(int times){
		for(int i=0; i < times; i++){
			Collections.shuffle(deckOfCards);
		}
	}
	
	public List<BaccaratCards> getDeckOfCards(){
		return deckOfCards;
	}
	
	
	public static void main(String[] args) {
		Deck d = new Deck();
		d.shuffleDeck(5);
		System.out.println(d.getDeckOfCards());
	}

}
