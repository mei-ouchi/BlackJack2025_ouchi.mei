package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exception.BlackJackException;
import model.GameSession;

public class GameSessionDao extends BaseDao{
	
	//新しいゲームセッションをDBに追加
	public int newGameSession(GameSession gameSession) throws BlackJackException{
		String sql="INSERT INTO game_session (user_id, start_chip, end_chip) VALUES (?, ?, ?)";
		int generatedId= -1;
		
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1,gameSession.getUserId());
			ps.setInt(2,gameSession.getStartChip());
			ps.setInt(3,gameSession.getEndChip());
			
			int raw = ps.executeUpdate();
			
			if(raw == 0) {
				throw new BlackJackException("ゲームセッションの作成に失敗しました");
			}
			//自動生成IDの取得
			try(ResultSet rs = ps.getGeneratedKeys()) {
				if(rs.next()) {
					generatedId = rs.getInt(1);
					gameSession.setSessionId(generatedId);
				}else {
					throw new BlackJackException("セッションIDの取得に失敗しました");
				}
			}
		}catch(SQLException e) {
			 e.printStackTrace();
			 throw new BlackJackException("ゲームセッションの作成時にエラーが発生しました");
		}
		return generatedId;
	}
	
	//ゲームセッション情報の更新
	public void updateGameSession(GameSession gameSession)  throws BlackJackException{
		String sql = "UPDAE game_session SET end_chip=? WHERE session_id=?";
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1, gameSession.getEndChip());
			ps.setInt(2, gameSession.getSessionId());
			
			int raw = ps.executeUpdate();
			if(raw == 0) {
				throw new BlackJackException("このセッションIDは見つかりませんでした");
			}
		}catch(SQLException e) {
			 e.printStackTrace();
			 throw new BlackJackException("ゲームセッションの更新に失敗しました");
		}
	}
	
	//セッションIDからゲームセッションを取得
	public GameSession getGameSessionId(int sessionId) throws BlackJackException{
		GameSession  gameSession = null;
		String sql = "SELECT session_id, user_id, start_chip, end_chip FROM game_session WHERE session_id = ?";
		
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1, sessionId);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					gameSession = new GameSession(
						rs.getInt("session_id"),
						rs.getString("user_id"),
						rs.getInt("start_chip"),
						rs.getInt("end_chip"));
				}
			}
		}catch(SQLException e) {
			 e.printStackTrace();
			 throw new BlackJackException("ゲームセッションの取得に失敗しました");
		}
		return gameSession;
	}
	
	//ユーザIDに紐づくゲームセッションリストを取得
	public List<GameSession> getGameSessionList(String userId) throws BlackJackException{
		List<GameSession> gameSessionList = new ArrayList<>();
		String sql = "SELECT session_id, user_id, start_chip, end_chip FROM game_session WHERE user_id=? ORDER BY session_id DESC";
		
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1,userId);
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					gameSessionList.add(new GameSession(
						rs.getInt("session_id"),
						rs.getString("user_id"),
						rs.getInt("start_chip"),
						rs.getInt("end_chip")
						));
				}
			}
		}catch(SQLException e) {
			 e.printStackTrace();
			 throw new BlackJackException("ゲームセッションリストの取得に失敗しました");
		}
		return gameSessionList;
	}
}

