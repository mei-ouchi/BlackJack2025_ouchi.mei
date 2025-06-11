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
	
	@Override
	public String toString() {
		String cardMark;
		String cardColor;
		
		switch(this.getMark()) {
			case "Spade":
				cardMark="&#x2660;";
				cardColor="spade-card";
				break;
			case "Heart":
				cardMark="&#x2665;";
				cardColor="heart-card";
				break;
			case "diamond":
				cardMark="&#x2666;";
				cardColor="diamond-card";
				break;
			case "clover":
				cardMark="&#x2663;";
				cardColor="clover-card";
				break;
			default:
				cardMark="";
				cardColor="";
		}
		
		int cardNumber=this.getNo();
		String cardNo;
		String cardDesign="";
		if(cardNumber == 1) {
			cardNo="A";
			cardDesign="ADesign";
		}else if(cardNumber == 11) {
			cardNo="J";
			cardDesign="JDesign";
		}else if(cardNumber == 12) {
			cardNo="Q";
			cardDesign="QDesign";
		}else if(cardNumber == 13) {
			cardNo="K";
			cardDesign="KDesign";
		}else {
			cardNo=String.valueOf(cardNumber);
		}
		
		return "<div class='card-inner " + cardColor + " " + cardDesign + "'>"+
					"<span class='rank top-right'>" + cardNo + "</span>"+
					"<span class='suit middle'>" + cardMark + "</span>"+
					"<span class='rank bottom-left'>" + cardNo + "</span>"+
				"</div>";
	}
	
	public int getPoint() {
		switch(no) {
			case 1:
				return 1;
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
}
