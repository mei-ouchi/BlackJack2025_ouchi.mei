package controller;

import java.io.IOException;

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


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	//ログイン
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("user_id");
		String loginPassword = request.getParameter("password");
		String nextPage = null;
		
		try {
			UserDao userDao = new UserDao();
			User user = userDao.doLogin(userId, loginPassword);
			
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			
			nextPage="game_top.jsp";
		}catch(BlackJackException e){
			String message = e.getMessage();
			request.setAttribute("message", message);
			request.setAttribute("error", "true");
			nextPage = "login.jsp";
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "システムエラー：再度お試しください");
			request.setAttribute("error", "true");
			nextPage = "login.jsp";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}
	//ログアウト
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		request.setAttribute("message", "ログアウトしました");
		
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
	}

}

