package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.BlackJackException;
import model.User;
import model.UserStats;


public class UserDao extends BaseDao{
	
	//ログイン処理
	public User doLogin(String userId, String loginPassword) throws BlackJackException{
		User user = null;
		
		try(Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT user_id, user_name, password FROM user WHERE user_id=? AND password=?")) {
			
			ps.setString(1, userId);
			ps.setString(2, loginPassword);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					user = new User(rs.getString("user_id"), rs.getString("user_name"), rs.getString("password"));
				}
			}
			
			if(user == null) {
				throw new BlackJackException("アカウントは未登録、または、ログイン情報が間違って間違っています");
			}
		}catch(SQLException e) {
			e.printStackTrace(); 
			throw new BlackJackException("ログイン処理中にエラーが発生しました");
		}
		return user;
	}
	
	//新規登録
	public void registerUser(String userId, String userName, String loginPassword) throws BlackJackException{
		try(Connection con = getConnection()) {
			
			if(UserIdExist(userId, con)) {
				throw new BlackJackException("このIDはすでに使用されています");
			}
			try(PreparedStatement ps = con.prepareStatement("INSERT INTO user (user_id, user_name, password) VALUES (?, ?, ?)")){
			ps.setString(1, userId);
			ps.setString(2, userName);
			ps.setString(3, loginPassword);
			ps.executeUpdate();
			}
			
			try(PreparedStatement ps = con.prepareStatement("INSERT INTO game_stats (user_id, total_game, wins, loses, draws, now_chip) VALUES (?, ?, ?, ?, ?, ?)")){
			ps.setString(1, userId);
			ps.setInt(2, 0);
			ps.setInt(3, 0);
			ps.setInt(4, 0);
			ps.setInt(5, 0);
			ps.setInt(6, 100);
			ps.executeUpdate();
			}
			
			}catch(SQLException e) {
				e.printStackTrace();
				throw new BlackJackException("アカウントの登録に失敗しました");
			}
		}
	
	//IDチェック
	private boolean UserIdExist(String userId, Connection con) throws BlackJackException{
		
		try(PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM user WHERE user_id = ?")) {
			ps.setString(1, userId);
			try(ResultSet rs = ps.executeQuery();) {
				if(rs.next()) {
				return rs.getInt(1)>0;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new BlackJackException("処理中にエラーが発生しました");
		}
			return false;
	}
	
	//ユーザの戦績情報の取得
	public UserStats getUserStats(String userId) throws BlackJackException{
		UserStats userStats = null;
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(
					"SELECT game_stats.user_id, user.user_name, game_stats.total_game, game_stats.wins, game_stats.loses, game_stats.draws, game_stats.now_chip"
					+ "FROM game_stats JOIN user ON user.user_id = game_stats.user_id"
					+ "WHERE game_stats.user_id=?")){
			ps.setString(1, userId);
			
			try(ResultSet rs = ps.executeQuery()) {
				if(rs.next()) {
					userStats = new UserStats(
						rs.getString("user_id"),
						rs.getString("user_name"),
						rs.getInt("total_game"),
						rs.getInt("wins"),
						rs.getInt("loses"),
						rs.getInt("draws"),
						rs.getInt("now_chip")
						);
				}
			}
		}catch (SQLException e) {
			e.printStackTrace(); 
			throw new BlackJackException("戦績情報の取得に失敗しました");
		}
		return userStats;
	}
	
	//勝率トップ5の取得
	
	public List<UserStats> getTopUserStats(int limit) throws BlackJackException{
		List<UserStats> topUserStatsList = new ArrayList<>();
		
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(
						"SELECT game_stats.user_id, user.user_name, game_stats.total_game, game_stats.wins, game_stats.loses, game_stats.draws, game_stats.now_chip,"
						+"CASE WHEN game_stats.total_game>0 THEN (CAST(game_stats.wins AS DECIMAL) / game_stats.total_game) ELSE 0 END AS win_rate "
						+ "FROM game_stats JOIN user ON user.user_id = game_stats.user_id "
						+ "ORDER BY win_rate DESC, game_stats.wins DESC, game_stats.total_game ASC LIMIT ?")){

			ps.setInt(1, limit);
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					topUserStatsList.add(new UserStats(
						rs.getString("user_id"),
						rs.getString("user_name"),
						rs.getInt("total_game"),
						rs.getInt("wins"),
						rs.getInt("loses"),
						rs.getInt("draws"),
						rs.getInt("now_chip")
						));
				}
			}
		}catch (SQLException e) {
			e.printStackTrace(); 
			throw new BlackJackException("ランキング情報の取得に失敗しました");
		}
		return topUserStatsList;
	}
	
	//ユーザの削除
	public void deleteUser(String userId) throws BlackJackException{
		
		try(Connection con = getConnection()) {
			try(PreparedStatement psStats = con.prepareStatement("DELETE FROM game_stats WHERE user_id = ?")){
			psStats.setString(1, userId);
			psStats.executeUpdate();
			}
			try(PreparedStatement psUser = con.prepareStatement("DELETE FROM user WHERE user_id = ?")){
				psUser.setString(1, userId);
				int count = psUser.executeUpdate();
				if(count == 0) {
					throw new BlackJackException("指定されたアカウント情報は取得できませんでした");
				}
			}
		}catch (SQLException e) {
			e.printStackTrace(); 
			throw new BlackJackException("アカウントを削除できませんでした");
		}
	}
	
	
	//ユーザの戦績更新
	public void uodateUserStats(String userId, int totalGame, int wins, int loses, int draws, int nowChip) throws BlackJackException{
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement("UPDATE game_stats SET total_game=?, wins=?, loses=?, draws=?, now_chip=? WHERE user_id=?")) {
			ps.setInt(1, totalGame);
			ps.setInt(2, wins);
			ps.setInt(3, loses);
			ps.setInt(4, draws);
			ps.setInt(5, nowChip);
			ps.setString(6, userId);
			
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace(); 
			throw new BlackJackException("戦績の更新に失敗しました");
		}
	}
}
