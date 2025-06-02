package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
	private List<Card> cards;
	
	public Deck() {
		this.cards = new ArrayList<>();
		shuffle();
	}
	
	private void shuffle() {
		String[] marks = {"Spade", "Heart", "diamond", "clover"};
		for(String mark : marks) {
			for(int no=1; no<=13; no++) {
				cards.add(new Card(mark, no));
			}
		}
		Collections.shuffle(cards, new Random());
	}
	
	public Card takeCard() {
		if(cards.isEmpty()) {
			throw new IllegalStateException("山札が空です");
		}
		return cards.remove(0);
	}
	
	public int getCardCount() {
		return cards.size();
	}
}
