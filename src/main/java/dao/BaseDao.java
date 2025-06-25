package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.BlackJackException;

public abstract class BaseDao {
	
	protected Connection getConnection() throws BlackJackException{
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String url = "jdbc:mysql://localhost/blackjack_db";
			String user = "root";
			String password = "kii1201mei";
			
			con = DriverManager.getConnection(url, user, password);
			return con;
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			throw new BlackJackException("JDBCドライバが見つかりません");
		}catch(SQLException e) {
			e.printStackTrace();
			throw new BlackJackException("データベース接続に失敗しました");
		}
	}
}

