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


@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	//新規登録
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String UserId = request.getParameter("user_id");
		String UserName = request.getParameter("user_name");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		
		String message = null;
		String error = null;
		String nextPage = null;
		
		UserDao userDao = new UserDao();
		
		try {
			if(UserId == null || UserId.isEmpty()
				|| UserName == null || UserName.isEmpty()
				|| password ==null || password.isEmpty()
				|| !password.equals(confirmPassword)
				) {
				throw new BlackJackException("すべてのユーザ情報が入力されていないか、パスワードが一致しません");
			}
			
			userDao.registerUser(UserId, UserName, password);
			message = "アカウントの新規登録が完了しました";
			nextPage = "login.jsp";
			 
		}catch(BlackJackException e) {
			e.printStackTrace();
			message = e.getMessage();
			request.setAttribute("message", message);
			request.setAttribute("error", "true");
			
			nextPage = "NewAccount.jsp";
		}catch (Exception e) {
			e.printStackTrace();
			message = "アカウント登録に失敗しました";
			request.setAttribute("message", message);
			request.setAttribute("error", "true");
			
			nextPage = "NewAccount.jsp";
		}
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("nextPage");
		requestDispatcher.forward(request, response);

		
	}
	
	//ユーザ削除
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession session = request.getSession(false);
		
		String message = null;
		String error = null;
		String nextPage = null;
		
		User user = null;
		
		//ユーザを削除するにはログインが必要
		try {
		if(session == null || session.getAttribute("user") == null) {
			message = "ログインしてください";
			error = "true";
			nextPage = "login.jsp";
			
			request.setAttribute("message", message);
			request.setAttribute("error", "true");
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("nextPage");
			requestDispatcher.forward(request, response);
			return;
		}
		//ユーザ削除処理
		
		user = (User)session.getAttribute("user");
		UserDao userDao = new UserDao();
		
		if("delete".equals(action)) {
			 if(user == null) {
				 throw new BlackJackException("削除するユーザ情報は見つかりませんでした");
			 }else{
				 userDao.deleteUser(user.getUserId());
				 
				 session.invalidate();
				 message = "アカウントの削除が完了しました";
				 nextPage = "login.jsp";
			 }
		}else {
			throw new BlackJackException("この操作はできません");
		}
		}catch(BlackJackException e){
			e.printStackTrace();
			message = e.getMessage();
			error = "true";
			nextPage = "game_top.jsp";
		
		}catch(Exception e) {
			e.printStackTrace();
			message = "正常に処理できませんでした";
			error = "true";
			nextPage = "game_top.jsp";
		}
		request.setAttribute("message", message);
        request.setAttribute("error", error);
        
		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}
}

