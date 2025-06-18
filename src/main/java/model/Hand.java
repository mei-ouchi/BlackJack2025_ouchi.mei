package model;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	private List<Card> handCard;//手札のカードリスト
	private int bet;//この手札の賭けチップ
	
	public Hand() {
		this.handCard=new ArrayList<>();
		this.bet=0;
	}
	
	public void addHandCard(Card card) {
		handCard.add(card);
	}
	
	public List<Card> getHandCard(){
		return this.handCard;
	}
	
	public int getCountHandCard() {
		int count=0;
		for(Card card : handCard) {
			count += card.getPoint();
		}
		return count;
	}
	
	public boolean bust() {
		return getCountHandCard() > 21;
	}
	
	public void clearHandCard() {
		handCard.clear();
	}
	
	public int getBet() {
		return bet;
	}
	
	public void setBet(int bet) {
		this.bet=bet;
	}

}
