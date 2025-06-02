package model;

public class GameSession {
	private int sessionId;
	private String userId;
	private int startChip;
	private int endChip;
	
	public GameSession(int sessionId, String userId, int startChip, int endChip) {
		this.sessionId=sessionId;
		this.userId=userId;
		this.startChip=startChip;
		this.endChip=endChip;
	}
	
	public GameSession(String userId, int startChip) {
		this.userId=userId;
		this.startChip=startChip;
		this.endChip=0;
	}
	
	public int getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(int sessionId) {
		this.sessionId=sessionId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId=userId;
	}
	
	public int getStartChip() {
		return startChip;
	}
	
	public void setStartChip(int startChip) {
		this.startChip=startChip;
	}
	
	public int getEndChip() {
		return endChip;
	}
	
	public void setEndChip(int endChip) {
		this.endChip=endChip;
	}
}
