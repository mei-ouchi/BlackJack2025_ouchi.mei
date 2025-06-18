package model;

import java.util.ArrayList;
import java.util.List;

public class Player extends PlayerBase{
	private String name;
	private List<Hand> hands;//複数の手札のリスト
	private int activeHandIndex;//現在操作中の手札の目印
	
	public Player(String name) {
		super();
		this.name=name;
		this.hands=new ArrayList<>();
		this.hands.add(new Hand());
		this.activeHandIndex=0;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Hand> getHands(){
		return hands;
	}
	
	public Hand getActiveHand() {
		if(activeHandIndex>=0 && activeHandIndex<hands.size()) {
			return hands.get(activeHandIndex);
		}
		return null;
	}
	
	public void setActiveHandIndex(int index) {
		this.activeHandIndex=index;
	}
	
	public int getActiveHandIndex() {
		return activeHandIndex;
	}
	
	public void addHand(Hand hand) {
		this.hands.add(hand);
	}
	
	public boolean canSplit() {
		if(hands.size() == 1 && getActiveHand().getHandCard().size() == 2) {
			Card card1=getActiveHand().getHandCard().get(0);
			Card card2=getActiveHand().getHandCard().get(1);
			return card1.getNo() == card2.getNo();
		}
		return false;
	}
	
	public  void performSplit(int betChip) {
		Hand originalHand=getActiveHand();
		Card cardToMove = originalHand.getHandCard().remove(1);
		
		Hand newHand=new Hand();
		newHand.addHandCard(cardToMove);
		newHand.setBet(betChip);
		
		addHand(newHand);
		originalHand.setBet(betChip);
		this.activeHandIndex=0;
	}
	
	@Override
	public void addHandCard(Card card) {
		if(getActiveHand() != null) {
			getActiveHand().addHandCard(card);
		}else {
			Hand newHand=new Hand();
			newHand.addHandCard(card);
			hands.add(newHand);
			activeHandIndex=hands.size() - 1;
		}
	}
	
	@Override
	public int getCountHandCard() {
		if(getActiveHand() != null) {
			return getActiveHand().getCountHandCard();
		}
		return 0;
	}
	
	@Override
	public boolean bust() {
		if(getActiveHand() != null) {
			return getActiveHand().bust();
		}
		return false;
	}
	
	@Override
	public void clearHandCard() {
		for(Hand hand : hands) {
			hand.clearHandCard();
		}
		hands.clear();
		hands.add(new Hand());
		activeHandIndex=0;
	}
	
	@Override
	public void takeTurnCard(Deck deck) {
		//プレイヤーの選択によってカードを引くか決める
	}	
}

