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

import dao.GameRoundDao;
import dao.GameSessionDao;
import dao.UserDao;
import exception.BlackJackException;
import model.Card;
import model.Dealer;
import model.Deck;
import model.GameSession;
import model.Hand;
import model.Player;
import model.User;


@WebServlet("/GameServlet")
public class GameServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		String action = request.getParameter("action");
		String nextPage = "game.jsp";
		String message = null;
		String error = null;
		
		//ログイン確認
		if(user == null) {
			request.setAttribute("message", "ログインしてください");
			request.setAttribute("error", "true");
			
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
			return;
		}
		
		try {
			Deck deck = (Deck)session.getAttribute("deck");
			Player player = (Player)session.getAttribute("player");
			Dealer dealer = (Dealer)session.getAttribute("dealer");
			GameSession gameSession = (GameSession)session.getAttribute("gameSession");
			Integer roundNumber = (Integer)session.getAttribute("roundNumber");
			Integer betChip = (Integer)session.getAttribute("betChip");
			
			UserDao userDao = new UserDao();
			GameSessionDao gameSessionDao = new GameSessionDao();
			GameRoundDao gameRoundDao = new GameRoundDao();
			
			//分岐
			//新しいゲームセッションの開始
			if("start".equals(action)) {
				user = userDao.getUserStats(user.getUserId());
				
				gameSession = new GameSession(user.getUserId(), user.getNowChip());
				int sessionId = gameSessionDao.newGameSession(gameSession);
				gameSession.setSessionId(sessionId);
				
				session.setAttribute("gameSession", gameSession);
				session.setAttribute("roundNumber", 0);
				session.setAttribute("message", message);
				session.setAttribute("error", error);
				session.removeAttribute("player");
				session.removeAttribute("dealer");
				session.removeAttribute("betChip");
				session.setAttribute("roundEnd", false);
			
			//チップ賭けてゲーム開始
			}else if("bet".equals(action)) {
				session.removeAttribute("deck");
				session.removeAttribute("player");
				session.removeAttribute("dealer");
				
				session.setAttribute("roundEnd", false);
				String betAmountString = request.getParameter("betAmount");
				
				int betAmount = Integer.parseInt(betAmountString);
				
				if(betAmount == 0 ) {
					message="チップを賭けてください";
					error="true";
					nextPage="game.jsp";
					request.setAttribute("message", message);
					request.setAttribute("error", "true");
					session.removeAttribute("betChip");
					RequestDispatcher rd = request.getRequestDispatcher(nextPage);
					rd.forward(request, response);
					return;
				}
				
				if(gameSession == null) {
					message="ゲームを開始してください";
					error="true";
					request.setAttribute("message", message);
					request.setAttribute("error", error);
					session.removeAttribute("betChip");
					RequestDispatcher rd = request.getRequestDispatcher(nextPage);
					rd.forward(request, response);
					return;
				}
				
				betChip = betAmount;
				session.setAttribute("betChip", betChip);
				user.setNowChip(user.getNowChip()-betChip);
				session.setAttribute("user", user);
				
				//ラウンドの開始
				roundNumber = (roundNumber == null) ? 1 : roundNumber + 1;
				session.setAttribute("roundNumber", roundNumber);
				
				session.setAttribute("gameSession", gameSession);
				
				deck = new Deck();
				player = new Player(user.getUserName());
				dealer = new Dealer();
				player.getActiveHand().setBet(betChip);
				
				//初期カード配布
				player.addHandCard(deck.takeCard());
				dealer.addHandCard(deck.takeCard());
				player.addHandCard(deck.takeCard());
				dealer.addHandCard(deck.takeCard());
				
				session.setAttribute("deck", deck);
				session.setAttribute("player", player);
				session.setAttribute("dealer", dealer);
				session.setAttribute("roundNumber", roundNumber);
				
				request.setAttribute("canSplit", player.canSplit());
				request.setAttribute("message", null); 
				request.setAttribute("error", null);
			
			//プレイヤーがカードを引く
			}else if("hit".equals(action)) {
				if(player == null || deck == null || dealer ==null ||gameSession == null || betChip == null) {
					message="ゲームを開始できません";
					error="true";
					request.setAttribute("message", message);
					request.setAttribute("error", error);
					session.setAttribute("roundEnd", true);
					RequestDispatcher rd = request.getRequestDispatcher(nextPage);
					rd.forward(request, response);
					return;
				}
				Hand activeHand=player.getActiveHand();
				activeHand.addHandCard(deck.takeCard());
				session.setAttribute("player", player);
				
				if(activeHand.bust()) {
					int currentActiveHandIndex = player.getHands().indexOf(activeHand);
			        if (currentActiveHandIndex + 1 < player.getHands().size()) {
			        	player.setActiveHandIndex(currentActiveHandIndex + 1);
			        	session.setAttribute("player", player);
			        	message = "手札" + (currentActiveHandIndex + 1) + "がバーストしました。次の手札に切り替わります。手札" + (player.getActiveHandIndex() + 1) + "を操作中";
			        	request.setAttribute("canSplit", player.canSplit());
			        }else {
			        	message = processEndRound(session, request, user, player, dealer, userDao, gameSessionDao, gameRoundDao, gameSession, roundNumber, deck);
				}
			}else {
					request.setAttribute("canSplit", false);
				}
			
			//手札の確定
			}else if("stand".equals(action)) {
				if(player == null || deck == null || dealer == null || gameSession == null || betChip == null) {
					message="ゲームを開始できません";
					error="true";
					request.setAttribute("message", message);
					request.setAttribute("error", error);
					session.setAttribute("roundEnd", true);
					RequestDispatcher rd = request.getRequestDispatcher(nextPage);
					rd.forward(request, response);
					return;
				}
				
				int currentActiveHandIndex = player.getHands().indexOf(player.getActiveHand());
				
				if (currentActiveHandIndex + 1 < player.getHands().size()) {

					player.setActiveHandIndex(currentActiveHandIndex + 1);
					session.setAttribute("player", player);
					message = "次の手札に切り替わりました。手札" + (player.getActiveHandIndex() + 1) + "を操作してください。";
					request.setAttribute("canSplit", player.canSplit());
			        
			    } else {
			    	message = processEndRound(session, request, user, player, dealer, userDao, gameSessionDao, gameRoundDao, gameSession, roundNumber, deck); 
			    }
				
			}else if("split".equals(action)) {
				if(player == null || deck == null || dealer == null || gameSession == null || betChip == null) {
					message="ゲームを開始できません";
					error="true";
					request.setAttribute("message", message);
					request.setAttribute("error", error);
					session.setAttribute("roundEnd", true);
					RequestDispatcher rd = request.getRequestDispatcher(nextPage);
					rd.forward(request, response);
					return;
				}
				if(player.canSplit()) {
					player.performSplit(betChip);
					user.setNowChip(user.getNowChip()-betChip);
					session.setAttribute("user", user);
					
					player.getHands().get(0).addHandCard(deck.takeCard());
					player.getHands().get(1).addHandCard(deck.takeCard());
					
					session.setAttribute("player", player);
					request.setAttribute("canSplit", false);
				}else {
					message="スプリットできませんでした";
					error="true";
				}
				
				
			//ゲームリセット
			}else if("reset".equals(action)) {
				session.removeAttribute("deck");
				session.removeAttribute("player");
				session.removeAttribute("dealer");
				session.removeAttribute("gameSession");
				session.removeAttribute("roundNumber");
				session.removeAttribute("betChip");
				session.removeAttribute("roundEnd");
				session.setAttribute("message", "ゲームがリセットされました");
				session.setAttribute("error", error);
				nextPage = "game_top.jsp";
			}
			
		}catch (BlackJackException e) {
			e.printStackTrace();
			session.removeAttribute("player");
			session.removeAttribute("dealer");
			session.removeAttribute("betChip");
			session.removeAttribute("roundEnd");
			request.setAttribute("message", e.getMessage());
			request.setAttribute("error", "true");
		}catch (Exception e) {
			e.printStackTrace();
			session.removeAttribute("player");
			session.removeAttribute("dealer");
			session.removeAttribute("betChip");
			session.removeAttribute("roundEnd");
			request.setAttribute("message", "システムエラーが発生しました。再度お試しください。");
			request.setAttribute("error", "true");
		}
		request.setAttribute("message",message);
		request.setAttribute("error", error);
		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}
	
	
	
	private String cardListString(List<Card> cards) {
		StringBuilder sb = new StringBuilder();
		for(Card card : cards) {
			sb.append(card.toString()).append(",");
		}
		
		if(sb.length()>0) {
			return sb.substring(0, sb.length()-1);
		}
			return "";
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			request.setAttribute("message", "ログインしてください");
			request.setAttribute("error", "true");
			
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
			return;
		}
		
		Player player=(Player)session.getAttribute("player");
		if(player != null) {
			request.setAttribute("playerHands", player.getHands());
			request.setAttribute("activeHandIndex", player.getHands().indexOf(player.getActiveHand()));
			request.setAttribute("canSplit", player.canSplit());
		}
		
		if(session.getAttribute("player") == null || session.getAttribute("dealer") == null || session.getAttribute("gameSession") == null || session.getAttribute("deck") == null) {
			request.setAttribute("message", "ゲームを開始してください");
			request.setAttribute("error", "false");
			RequestDispatcher rd = request.getRequestDispatcher("game_top.jsp");
			rd.forward(request, response);
			return;
		}
			
			try {
				UserDao userDao =new UserDao();
				user = userDao.getUserStats(user.getUserId());
				session.setAttribute("user", user);
				session.setAttribute("roundEnd", false);
			}catch (BlackJackException e) {
				request.setAttribute("message", e.getMessage());
				request.setAttribute("error", "true");
			}catch(Exception e) {
				request.setAttribute("message", "システムエラーが発生しました");
				request.setAttribute("error", "true");
			}
			RequestDispatcher rd = request.getRequestDispatcher("game.jsp");
			rd.forward(request, response);
	}
}