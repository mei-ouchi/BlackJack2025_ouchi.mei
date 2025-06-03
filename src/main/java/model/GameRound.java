package model;

public class GameRound {
	
	public enum GameResult {
		PLAYER_WIN("PLAYER_WIN"),
		DEALER_WIN("DEALER_WIN"),
		DRAW("DRAW"),
		PLAYER_BJ("PLAYER_BJ"),
		DEALER_BJ("DEALER_BJ"),
		PLAYER_BUST("PLAYER_BUST"),
		DEALER_BUST("DEALER_BUST");
		
		private final String db;
		
		GameResult(String db){
			this.db=db;
		}
		
		public String getDb() {
			return db;
		}
		
		public static GameResult fromDbValue(String db) {
			for(GameResult result : GameResult.values()){
				if(result.getDb().equals(db)) {
					return result;
				}
			}
			throw new IllegalArgumentException("対応する値が見つかりません："+db);
		}
	}
	
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
	
	public int getRoundNumber() {
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
	
	public int getDealerScore() {
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
	
	public int getChipChange() {
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
	
	public GameResult getGameResult() {
		return gameResult;
	}
	
	public void setGameResult(GameResult gameResult) {
		this.gameResult=gameResult;
	}
}
	