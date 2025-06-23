package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.BlackJackException;
import model.User;


public class UserDao extends BaseDao{
	
	//ログイン処理
	public User doLogin(String userId, String loginPassword) throws BlackJackException{
		User user = null;
		
		try(Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT user_id, user_name, password, total_game, wins, loses, draws, now_chip  FROM user WHERE user_id=? AND password=?")) {
			
			ps.setString(1, userId);
			ps.setString(2, loginPassword);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					user = new User(rs.getString("user_id"), rs.getString("user_name"), rs.getString("password"), rs.getInt("total_game"), rs.getInt("wins"), rs.getInt("loses"),  rs.getInt("draws"), rs.getInt("now_chip"));
				}
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
			try(PreparedStatement ps = con.prepareStatement("INSERT INTO user (user_id, user_name, password, total_game, wins, loses, draws, now_chip) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")){
			ps.setString(1, userId);
			ps.setString(2, userName);
			ps.setString(3, loginPassword);
			ps.setInt(4, 0);
			ps.setInt(5, 0);
			ps.setInt(6, 0);
			ps.setInt(7, 0);
			ps.setInt(8, 100);
			ps.executeUpdate();
			}
			
		}catch(SQLException e) {
				e.printStackTrace();
				throw new BlackJackException("アカウントの登録に失敗しました");
			}
	}
	
	//IDチェック
	public boolean userIdExist(String userId) throws BlackJackException{
		
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM user WHERE user_id = ?")) {
			ps.setString(1, userId);
			try(ResultSet rs = ps.executeQuery();) {
				if(rs.next()) {
				return rs.getInt(1)>0;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
			throw new BlackJackException("IDチェック処理中にエラーが発生しました");
		}
			return false;
	}
	
	//ユーザの戦績情報の取得
	public User getUserStats(String userId) throws BlackJackException{
		User user = null;
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(
					"SELECT user_id, user_name, total_game, wins, loses, draws, now_chip FROM user WHERE user_id=?")){
			ps.setString(1, userId);
			
			try(ResultSet rs = ps.executeQuery()) {
				if(rs.next()) {
					user = new User(
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
		return user;
	}
	
	//勝率トップ5の取得
	
	public List<User> getTopUserStats(int limit) throws BlackJackException{
		List<User> topUserList = new ArrayList<>();
		
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(
						"SELECT user_id, user_name, total_game, wins, loses, draws, now_chip, rnk " +
								"FROM ( " +
								"SELECT *, " +
								"CASE WHEN total_game > 0 THEN (CAST(wins AS DECIMAL) / total_game) ELSE 0 END AS win_rate, " +
								"DENSE_RANK() OVER (ORDER BY (CASE WHEN total_game > 0 THEN (CAST(wins AS DECIMAL) / total_game) ELSE 0 END) DESC) as rnk " +
								"FROM user " +
								"WHERE total_game > 0 " +
								") AS ranked_users " +
								"WHERE rnk <= ? " +
								"ORDER BY rnk ASC, user_id ASC "
								)){

							ps.setInt(1, limit);
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					topUserList.add(new User(
						rs.getString("user_id"),
						rs.getString("user_name"),
						rs.getInt("total_game"),
						rs.getInt("wins"),
						rs.getInt("loses"),
						rs.getInt("draws"),
						rs.getInt("now_chip"),
						rs.getInt("rnk")
						));
				}
			}
		}catch (SQLException e) {
			e.printStackTrace(); 
			throw new BlackJackException("ランキング情報の取得に失敗しました");
		}
		return topUserList;
	}
	
	//ユーザの削除
	public void deleteUser(String userId) throws BlackJackException{
		
		try(Connection con = getConnection()) {
			try(PreparedStatement psUser = con.prepareStatement("DELETE FROM user WHERE user_id = ?")){
				psUser.setString(1, userId);
				int rawCount = psUser.executeUpdate();
				if(rawCount == 0) {
					throw new BlackJackException("指定されたアカウント情報は取得できませんでした");
				}
			}
		}catch (SQLException e) {
			e.printStackTrace(); 
			throw new BlackJackException("アカウントを削除できませんでした");
		}
	}
	
	
	//ユーザの戦績更新
	public void updateUserStats(String userId, int totalGame, int wins, int loses, int draws, int nowChip) throws BlackJackException{
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement("UPDATE user SET total_game=?, wins=?, loses=?, draws=?, now_chip=? WHERE user_id=?")) {
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
