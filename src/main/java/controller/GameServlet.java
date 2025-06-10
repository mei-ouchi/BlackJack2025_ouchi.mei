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
import model.GameRound;
import model.GameRound.GameResult;
import model.GameSession;
import model.Player;
import model.User;


@WebServlet("/GameServlet")
public class GameServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		String action = request.getParameter("action");
		String nextPage = "game.jsp";
		String error = null;
		String message = null;
		
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
				
				if(betAmount <= 0 ) {
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
				
				//初期カード配布
				player.addHandCard(deck.takeCard());
				dealer.addHandCard(deck.takeCard());
				player.addHandCard(deck.takeCard());
				dealer.addHandCard(deck.takeCard());
				
				session.setAttribute("deck", deck);
				session.setAttribute("player", player);
				session.setAttribute("dealer", dealer);
				session.setAttribute("roundNumber", roundNumber);
			
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
				player.addHandCard(deck.takeCard());
				session.setAttribute("player", player);
				
				if(player.bust()) {
					roundGameEnd(session, request, user, player, dealer, betChip, GameResult.PLAYER_BUST, userDao, gameSessionDao, gameRoundDao, gameSession, roundNumber, deck);
					session.setAttribute("roundEnd", true);
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
				dealer.takeTurnCard(deck);
				session.setAttribute("dealer", dealer);
				
				GameResult gameResult = whichWin(player, dealer);
				roundGameEnd(session, request, user, player, dealer, betChip, gameResult, userDao, gameSessionDao, gameRoundDao, gameSession, roundNumber, deck);
				session.setAttribute("roundEnd", true);
			
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
		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}
	//勝敗判定
	private GameResult whichWin(Player player, Dealer dealer) {
		int playerTotal = player.getCountHandCard();
		int dealerTotal = dealer.getCountHandCard();
		
		if(player.bust()) {
			return GameResult.PLAYER_BUST;
		}else if(dealer.bust()) {
			return GameResult.DEALER_BUST;
		}else if(playerTotal > dealerTotal) {
			return GameResult.PLAYER_WIN;
		}else if(playerTotal < dealerTotal) {
			return GameResult.DEALER_WIN;
		}else {
			return GameResult.DRAW;
		}
	}
	//ラウンド終了処理
	private void roundGameEnd(HttpSession session, HttpServletRequest request, User user, Player player, Dealer dealer, int betChip, GameResult gameResult, UserDao userDao, GameSessionDao gameSessionDao, GameRoundDao gameRoundDao, GameSession gameSession, int roundNumber, Deck deck) throws BlackJackException{
		int chipChange = 0;
		String message = null;
		String error = null;
		
		int totalGame = user.getTotalGame();
		int wins = user.getWins();
		int loses = user.getLoses();
		int draws = user.getDraws();
		int nowChip = user.getNowChip();
		
		totalGame++;
		
		switch(gameResult){
			case PLAYER_WIN:
				chipChange = betChip;
				nowChip += betChip*2;
				wins++;
				message = "あなたの勝ちです！チップが"+chipChange+"増えました";
				break;
			case DEALER_WIN:
				chipChange = -betChip;
				loses++;
				message = "ディーラーの勝ちです！チップが"+betChip+"減りました";
				break;
			case DRAW:
				chipChange = 0;
				nowChip += betChip;
				draws++;
				message = "引き分けです！チップが戻りました";
				break;
			case PLAYER_BUST:
				chipChange = -betChip;
				loses++;
				message = "あなたはバーストしました…ディーラーの勝ちです！チップが"+betChip+"減りました";
				break; 
			case DEALER_BUST:
				chipChange = betChip;
				nowChip += betChip*2;
				wins++;
				message = "ディーラーがバーストしました！あなたの勝ちです！チップが"+chipChange+"増えました";
				break;
		}
		
		user.setTotalGame(totalGame);
		user.setWins(wins);
		user.setLoses(loses);
		user.setDraws(draws);
		user.setNowChip(nowChip);
		
		try {//ゲーム終了後のいろいろな更新
			userDao.updateUserStats(user.getUserId(), totalGame, wins, loses, draws, nowChip);
			
			gameSession.setEndChip(nowChip);
			gameSessionDao.updateGameSession(gameSession);
			
			String playerHandCardString = cardListString(player.getHandCard());
			String dealerHandCardString = cardListString(dealer.getHandCard());
			
			GameRound gameRound = new GameRound(
			gameSession.getSessionId(),
			roundNumber,
			playerHandCardString,
			dealerHandCardString,
			player.getCountHandCard(),
			dealer.getCountHandCard(),
			betChip,
			chipChange,
			player.getHandCard().size(),
			gameResult
			);
			
			gameRoundDao.newGameRound(gameRound);
			
			session.setAttribute("user", user);
			
			request.setAttribute("message", message);
			request.setAttribute("error", error);
			
			
		}catch (Exception e) {
			e.printStackTrace(); 
			message="ゲーム結果の保存に失敗しました";
			error="true";
		}
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
