package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.UserDao;
import exception.BlackJackException;
import model.User;

@WebServlet("/RankingServlet")
public class RankingServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		String message = null;
		String error = null;
		String nextPage = "ranking.jsp";
		
		try {
			if(session ==null || session.getAttribute("user") == null) {
				message="ログインしてください";
				error="true";
				nextPage = "login.jsp";
				request.setAttribute("message", message);
				request.setAttribute("error", "true");
				RequestDispatcher rd = request.getRequestDispatcher(nextPage);
				rd.forward(request, response);
				return;
			}
			
		User user = (User)session.getAttribute("user");
		UserDao userDao = new UserDao();
		
		User userStats = userDao.getUserStats(user.getUserId());
		
		if(userStats != null) {
			request.setAttribute("userStats", userStats);
		}else {
			message = "あなたの戦績は見つかりませんでした";
			error = "true";
		}
		
		List<User> topUserList = userDao.getTopUserStats(5);
		request.setAttribute("topUserStatsList", topUserList);
		}catch(BlackJackException e) {
			e.printStackTrace();
			message=e.getMessage();
			error="true";
		}catch(Exception e) {
			e.printStackTrace();
			message="エラーが発生したため、勝率ランキングが表示できません";
			error="true";
		}
		request.setAttribute("message", message);
		request.setAttribute("error", error);
		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}
}

