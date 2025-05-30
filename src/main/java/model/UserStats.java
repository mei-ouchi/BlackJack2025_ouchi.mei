package model;

public class UserStats {
	
	private String userId;
	private String userName;
	
	private int totalGame;
	private int wins;
	private int loses;
	private int draws;
	private int nowChip;
	
	public UserStats(String userId, String userName, int totalGame, int wins, int loses, int draws, int nowChip) {
		this.userId = userId;
		this.userName = userName;
		this.totalGame = totalGame;
		this.wins = wins;
		this.loses = loses;
		this.draws = draws;
		this.nowChip = nowChip;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public int getTotalGame() {
		return totalGame;
	}
	
	public void setTotalGame(int totalGame) {
		this.totalGame = totalGame;
	}
	
	public int getWins() {
		return wins;
	}
	
	public void setWins(int wins) {
		this.wins = wins;
	}
	
	public int getLoses() {
		return loses;
	}
	
	public void setLoses(int loses) {
		this.loses = loses;
	}
	
	public int getDraws() {
		return draws;
	}
	
	public void setDraws(int draws) {
		this.draws = draws;
	}
	
	public int getNowChip() {
		return nowChip;
	}
	
	public void setNowChip(int nowChip) {
		this.nowChip = nowChip;
	}
}

