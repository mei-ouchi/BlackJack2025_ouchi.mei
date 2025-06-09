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
		String userId = request.getParameter("user_id");
		String userName = request.getParameter("user_name");
		String loginPassword = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		
		String message = null;
		String error = null;
		String nextPage = null;
		
		UserDao userDao = new UserDao();
		
		try {
			if(userId == null || userId.isEmpty()
				|| userName == null || userName.isEmpty()
				|| loginPassword ==null || loginPassword.isEmpty()
				|| !loginPassword.equals(confirmPassword)
				) {
				message="パスワードが一致しません";
				error="true";
				nextPage="NewAccount.jsp";
				request.setAttribute("message", message);
				request.setAttribute("error", "true");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
				requestDispatcher.forward(request, response);
				return;
			}
			
			if(userDao.userIdExist(userId)) {
				message="このIDは既に使用されています";
				error="true";
				nextPage="NewAccount.jsp";
				request.setAttribute("message", message);
				request.setAttribute("error", "true");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
				requestDispatcher.forward(request, response);
				return;
			}
			
			userDao.registerUser(userId, userName, loginPassword);
			message = "アカウントの新規登録が完了しました";
			error="";
			request.setAttribute("message", message);
			nextPage = "login.jsp";
			 
		}catch(BlackJackException e) {
			e.printStackTrace();
			message = e.getMessage();
			nextPage = "NewAccount.jsp";
		}catch (Exception e) {
			e.printStackTrace();
			message = "アカウント登録に失敗しました";
			error="true";
			nextPage = "NewAccount.jsp";
		}
		
		request.setAttribute("message", message);
		request.setAttribute("error", error);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
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
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
			requestDispatcher.forward(request, response);
			return;
		}
		//ユーザ削除処理
		
		user = (User)session.getAttribute("user");
		UserDao userDao = new UserDao();
		
		if("delete".equals(action)) {
			 if(user == null) {
				 message="削除するユーザ情報は見つかりませんでした";
				 error="true";
			 }else{
				 userDao.deleteUser(user.getUserId());
				 
				 session.invalidate();
				 message = "アカウントの削除が完了しました";
				 nextPage = "login.jsp";
			 }
		}else {
			message="この操作はできません";
			error="true";
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

