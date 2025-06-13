package model;

public class Player extends PlayerBase{
	private String name;
	
	public Player(String name) {
		super();
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public void takeTurnCard(Deck deck) {
		//プレイヤーの選択によってカードを引くか決める
	}	
}