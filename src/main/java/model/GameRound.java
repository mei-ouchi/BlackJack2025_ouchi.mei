package model;

public class GameRound {
	
	private int roundId;
	private int sessionId;
	private int roundNumber;
	private String playerCard;
	private String dealerCard;
	private int playerScore;
	private int dealerScore;
	private int betChip;
	private int chipChange;
	private int playerCardIndex;
	
	private GameResult gameResult;
	
	public GameRound(int roundId, int sessionId, int roundNumber, String playerCard, String dealerCard, int playerScore, int dealerScore, int betChip, int chipChange, int playerCardIndex, GameResult gameResult) {
		this.roundId=roundId;
		this.sessionId=sessionId;
		this.roundNumber=roundNumber;
		this.playerCard=playerCard;
		this.dealerCard=dealerCard;
		this.playerScore=playerScore;
		this.dealerScore=dealerScore;
		this.betChip=betChip;
		this.chipChange=chipChange;
		this.playerCardIndex=playerCardIndex;
		this.gameResult=gameResult;
	}
	
	public GameRound(int sessionId, int roundNumber, String playerCard, String dealerCard, int playerScore, int dealerScore, int betChip, int chipChange, int playerCardIndex, GameResult gameResult) {
		this.sessionId=sessionId;
		this.roundNumber=roundNumber;
		this.playerCard=playerCard;
		this.dealerCard=dealerCard;
		this.playerScore=playerScore;
		this.dealerScore=dealerScore;
		this.betChip=betChip;
		this.chipChange=chipChange;
		this.playerCardIndex=playerCardIndex;
		this.gameResult=gameResult;
	}
	
	public int getRoundId() {
		return roundId;
	}
	
	public void setRoundId(int roundId) {
		this.roundId=roundId;
	}
	
	public int getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(int sessionId) {
		this.sessionId=sessionId;
	}
	
	public int getRoundNUmber() {
		return roundNumber;
	}
	
	public void setRoundNumber(int roundNumber) {
		this.roundNumber=roundNumber;
	}
	
	public String getPlayerCard() {
		return playerCard;
	}
	
	public void setPlayerCard(String playerCard) {
		this.playerCard=playerCard;
	}
	
	public String getDealerCard() {
		return dealerCard;
	}
	
	public void setDealerCard(String dealerCard) {
		this.dealerCard=dealerCard;
	}
	
	public int getPlayerScore() {
		return playerScore;
	}
	
	public void setPlayerScore(int playerScore) {
		this.playerScore=playerScore;
	}
	
	public int getdealerScore() {
		return dealerScore;
	}
	
	public void setDealerScore(int dealerScore) {
		this.dealerScore=dealerScore;
	}
	
	public int getBetChip() {
		return betChip;
	}
	
	public void setBetChip(int betChip) {
		this.betChip=betChip;
	}
	
	public int chipChange() {
		return chipChange;
	}
	
	public void setChipChange(int chipChange) {
		this.chipChange=chipChange;
	}
	
	public int getPlayerCardIndex() {
		return playerCardIndex;
	}
	
	public void setPlayerCardIndex(int playerCardIndex) {
		this.playerCardIndex=playerCardIndex;
	}
	
	public GameResult gameResult() {
		return gameResult;
	}
	
	public void gameResult(GameResult gameResult) {
		this.gameResult=gameResult;
	}
	
	@Override
	public String toString() {
		return "GameRound{"+
				"roundId:"+roundId+
				", sessionId:"+sessionId+
				", roundNUmber:"+roundNumber+
				", playerCard:\""+playerCard+"\""+
				", dealerCrad:\""+dealerCard+"\""+
				", playerScore:"+playerScore+
				", dealerScore:"+dealerScore+
				", betChip:"+betChip+
				",  chipChange:"+chipChange+
				", playerCardIndex:"+playerCardIndex+
				"gameResult:"+gameResult+
				"}";
	}
}
