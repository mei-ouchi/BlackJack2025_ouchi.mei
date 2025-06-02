package model;

public class Dealer extends PlayerBase{
	
	public Dealer() {
		super();
	}
	
	@Override
	public void takeTurnCard(Deck deck) {
		while(getCountHandCard()<17 && !bust()) {
			addHandCard(deck.takeCard());
		}
	}
}
