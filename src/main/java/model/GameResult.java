package model;

public enum GameResult {
	PLAYER_WIN,
	DEALER_WIN,
	DRAW,
	PLAYER_BJ,
	DEALER_BJ,
	PLAYER_BUST,
	DEALER_BUST;
	
	public static GameResult fromString(String text) {
		for(GameResult gr : GameResult.values()) {
			if(gr.name().equalsIgnoreCase(text)) {
				return gr;
			}
		}
		throw new IllegalArgumentException("この定数は見つかりません："+text);
	}
}