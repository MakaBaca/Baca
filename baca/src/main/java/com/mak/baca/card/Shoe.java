package com.mak.baca.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Shoe {
	
	private int numberOfDecks = 0;
	
	private List<BaccaratCards> shoeOfCards;
	
	private Stack<BaccaratCards> stackOfCards;
	
	public Stack<BaccaratCards> getStackOfCards() {
		return stackOfCards;
	}

	public Shoe(int count){
		numberOfDecks = count;
		shoeOfCards = new ArrayList<BaccaratCards>();
		for(int i= 0; i < count; i++){
			Deck d = new Deck();
			Iterator<BaccaratCards> itr = d.getDeckOfCards().iterator();
			while(itr.hasNext()){
				shoeOfCards.add(itr.next());
			}
		}
	}
	
	public void shuffleShoe(int times){
		for(int i=0; i < times; i++){
			Collections.shuffle(shoeOfCards);
		}
	}
	
	public void stackShoe(){
		stackOfCards = new Stack<BaccaratCards>();
		for(BaccaratCards card: shoeOfCards){
			stackOfCards.push(card);
		}
	}
	
	public List<BaccaratCards> getShoeOfCards() {
		return shoeOfCards;
	}
	
	public BaccaratCards burnCards(){
		BaccaratCards card = stackOfCards.pop();
		System.out.println("Burn Card is "+card);
		int rank = card.getRank().getPriority();
		//Jack, queen and kings are considered as 10
		if(rank > 10){
			rank = 10;
		}
		for(int i =0; i<rank; i++){
			stackOfCards.pop();
		}
		return card;
	}
	
	public BaccaratCards dealOneCard(){
		return stackOfCards.pop();
	}
	
	public int getCardsLeftInShoe(){
		return stackOfCards.size();
	}
	
	public static void main(String[] args) {
		Shoe s = new Shoe(8);
		s.shuffleShoe(100);
		s.stackShoe();
		System.out.println(s.stackOfCards);
		System.out.println(s.stackOfCards.size());
		s.burnCards();
		System.out.println(s.stackOfCards);
		System.out.println(s.stackOfCards.size());
		
	}

}
