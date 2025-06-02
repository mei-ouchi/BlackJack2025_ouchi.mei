package model;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayerBase {
	
	protected List<Card> handCard;
	
	public PlayerBase() {
		this.handCard = new ArrayList<>();
	}
	
	//手札にカードの追加
	public void addHandCard(Card card) {
		handCard.add(card);
	}
	
	//手札リストの取得
	public List<Card> getHandcard(){
		return new ArrayList<>(handCard);
	}
	
	//現在の手札の合計点
	public int getCountHandCard() {
		int count = 0;
		for(Card card : handCard) {
			count += card.getPoint();
		}
		return count;
	}
	
	//バーストかの判断
	public boolean bust() {
		return getCountHandCard() >21;
	}
	
	//自分のターン中にカードを引く(Player,Dealerの共通部)
	public abstract void takeTurnCard(Deck deck);
	
	public void clearHandCard() {
		handCard.clear();
	}
}
