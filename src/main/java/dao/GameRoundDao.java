package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exception.BlackJackException;
import model.GameRound;

public class GameRoundDao extends BaseDao{
	
	//新しいゲームラウンド情報をDBに追加
	public int newGameRound(GameRound gameRound) throws BlackJackException{
		String sql = "INSERT INTO game_round (session_id, round_number, player_card, dealer_card, player_score, dealer_score, bet_chip, chip_change, player_card_index, game_result) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int generatedId = -1;
		
		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, gameRound.getSessionId());
			ps.setInt(2, gameRound.getRoundNumber());
			ps.setString(3, gameRound.getPlayerCard());
			ps.setString(4, gameRound.getDealerCard());
			ps.setInt(5, gameRound.getPlayerScore());
			ps.setInt(6, gameRound.getDealerScore());
			ps.setInt(7, gameRound.getBetChip());
			ps.setInt(8, gameRound.getChipChange());
			ps.setInt(9, gameRound.getPlayerCardIndex());
			ps.setString(10, gameRound.getGameResult().getDbValue());
			
			ps.executeUpdate();
			
			//自動生成IDの取得
			try(ResultSet rs = ps.getGeneratedKeys()) {
				if(rs.next()) {
					generatedId = rs.getInt(1);
					gameRound.setRoundId(generatedId);
				}
			}
		}catch(SQLException e) {
			 e.printStackTrace();
			 throw new BlackJackException("ゲーム開始処理中にエラーが発生しました", e);
		}
		return generatedId;
	}
	
	//セッションIDからゲームラウンドリストの取得
	public List<GameRound> getGameRoundList(int sessionId) throws BlackJackException{
		List<GameRound> gameRoundList = new ArrayList<>();
		
		String sql = "SELECT round_id, session_id, round_number, player_card, dealer_card, player_score, dealer_score, bet_chip, chip_change, player_card_index game_result"+
					"FROM gaem_round WHERE session_id = ? ORDER BY round_number ASC";
		
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1, sessionId);
			
			try(ResultSet rs = ps.executeQuery()) {
				while(rs.next()) {
					gameRoundList.add(new GameRound(
						rs.getInt("round_id"),
						rs.getInt("session_id"),
						rs.getInt("round_number"),
						rs.getString("player_card"),
						rs.getString("dealer_card"),
						rs.getInt("player_score"),
						rs.getInt("dealer_score"),
						rs.getInt("bet_chip"),
						rs.getInt("chip_change"),
						rs.getInt("player_card_index"),
						GameRound.GameResult.fromDbValue(rs.getString("game_result"))
						));
				}
			}
		}catch(SQLException e) {
			 e.printStackTrace();
			 throw new BlackJackException("ゲームの情報を取得できませんでした", e);
		}
		return gameRoundList;
	}
	
	//ラウンドIDからゲームラウンド情報を取得
	public GameRound getGameRound(int roundId) throws BlackJackException{
		GameRound gameRound = null;
		
		String sql = "SELECT round_id, session_id, round_number, player_card, dealer_card, player_score, dealer_score, bet_chip, chip_change, player_card_index, game_result "+
				"FROM game_round WHERE round_id = ?";
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1, roundId);
			
			try(ResultSet rs = ps.executeQuery()) {
				if(rs.next()) {
					gameRound = new GameRound(
						rs.getInt("round_id"),
						rs.getInt("session_id"),
						rs.getInt("round_number"),
						rs.getString("palyer_card"),
						rs.getString("dealer_card"),
						rs.getInt("player_score"),
						rs.getInt("dealer_score"),
						rs.getInt("bet_chip"),
						rs.getInt("chip_change"),
						rs.getInt("player_card_index"),
						GameRound.GameResult.fromDbValue(rs.getString("game_result"))
						);
				}
			}
		}catch(SQLException e) {
			 e.printStackTrace();
			 throw new BlackJackException("ゲーム情報の取得に失敗しました", e);
		}
		return gameRound;
	}
	
	//ゲームラウンド情報の更新
	public void UpdateGameRound(GameRound gameRound) throws BlackJackException{
		String sql = "UPDATE game_round SET session_id=?, round_number=?, player_card=?, dealer_card=?, player_score=?, dealer_score=?, bet_chip=?, chip_change=?, player_card_index=?, game_result=? WHERE round_id=?" ;
		
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1, gameRound.getSessionId());
			ps.setInt(2, gameRound.getRoundNumber());
			ps.setString(3, gameRound.getPlayerCard());
			ps.setString(4, gameRound.getDealerCard());
			ps.setInt(5, gameRound.getPlayerScore());
			ps.setInt(6, gameRound.getDealerScore());
			ps.setInt(7, gameRound.getBetChip());
			ps.setInt(8, gameRound.getChipChange());
			ps.setInt(9, gameRound.getPlayerCardIndex());
			ps.setString(10, gameRound.getGameResult().getDbValue());
			ps.setInt(11, gameRound.getRoundId());
			
			ps.executeUpdate();
			
		}catch(SQLException e) {
			 e.printStackTrace();
			 throw new BlackJackException("ゲーム結果の更新中にエラーが発生しました", e);
		}
	}
	
	//ゲームラウンドの削除
	public void deleteGameRound(int roundId) throws BlackJackException{
		String sql = "DELETE FRO game_round WHEHE round_id=?";
		
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1, roundId);
			
			ps.executeUpdate();
			
		}catch(SQLException e) {
			 e.printStackTrace();
			 throw new BlackJackException("ゲームの結果削除中にエラーが発生しました", e);
		}
	}
}	
	