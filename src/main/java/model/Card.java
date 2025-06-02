package model;

public class Card {
	
	private String mark;
	private int no;
	
	public Card(String mark, int no) {
		this.mark=mark;
		this.no=no;
	}
	
	public String getMark() {
		return mark;
	}
	
	public int getNo() {
		return no;
	}
	
	public String getNoString() {
		switch(no){
			case 1:
				return "A";
			case 11:
				return "J";
			case 12:
				return "Q";
			case 13:
				return "K";
			default:
				return String.valueOf(no);
		}
	}
	
	public int getPoint() {
		switch(no) {
			case 11:
				return 10;
			case 12:
				return 10;
			case 13:
				return 10;
			default:
				return no;
		}
	}
	
	@Override
	public String toString() {
		return mark+""+getNoString();
	}
}
