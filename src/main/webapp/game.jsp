<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="model.User" %>
<%@ page import="model.Card" %>
<%@ page import="model.Player" %>
<%@ page import="model.Dealer" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/card.css">
<title>BlackJack</title>
</head>
	
<body>
	<jsp:include page="common/navi_game.jsp"/>
	<div class="game-container">
	
	<%
		String message = (String)request.getAttribute("message");
		String error = (String)request.getAttribute("error");
	%>
		<% if(message != null && error != null) { %>
		<div class="alert alert-danger" role="alert">
			<%= message %>
		</div>
		<% } %>
		
		<%
			User user = (User)session.getAttribute("user");
			Player player = (Player)session.getAttribute("player");
			Dealer dealer = (Dealer)session.getAttribute("dealer");
			Integer roundNumber = (Integer)session.getAttribute("roundNumber");
			Integer betChip = (Integer)session.getAttribute("betChip");
			Boolean roundEnd = (Boolean)session.getAttribute("roundEnd");
			
			if(roundEnd == null){
				roundEnd = false;
			}
		%>
		
		<% if(roundNumber != null && roundNumber > 0){ %>
		<div class="text-center">
			<h3 class="user" mb-0><strong>ラウンド<%= roundNumber %></strong></h3>
		</div>
		<% } %>
		
		<div class="d-flex justify-content-end align-items-start px-3 py-2">
			<% if(user != null){ %>
			<div class="text-center text-dark bg-white">
				<p class="mb-0"><strong><%= user.getUserName() %></strong>さん</p>
				<p class="mb-0">あなたの保有チップ数：<strong><%= user.getNowChip() %></strong></p>
			</div>
			<% } %>
		</div>
		
			<h3 class="card-title text-center text-white"><strong>ディーラーの手札</strong></h3>
			<div class="card-area text-center">
			<div class="d-flex flex-wrap justify-content-center">
			
			<%
			if(dealer != null && !dealer.getHandCard().isEmpty()){
				List<Card> dealerHandCard = dealer.getHandCard();
					if(roundEnd){
						for(Card card : dealerHandCard){
			%>
			
				<div class="card-item"><%=card.toString() %></div>
			
			<%} %>
			
				<p class="card-score text-dark bg-white">合計：<%=dealer.getCountHandCard() %></p>
				
			<%
			}else{
				Card firstDealerCard = dealerHandCard.get(0);
			%>
			
				<div class="card-item"><%=firstDealerCard.toString()%></div>
			
			<%if(dealerHandCard.size()>1){%>
			
				<div class="card-item card-back"></div>
				
			<%}
					}
			}else{
			%>
			
			<p class="text-white">no card</p>
			
			<%} %>
			
			</div>
			</div>
			
			<div style="min-height: 50px;">
			<%if(roundEnd != null && roundEnd){//ゲーム終了時のメッセージ %>
			<div class="table-head">
				<h1 class="text-dark text-center"><%= message %></h1>
			</div>
			<%} %>
			</div>
			
			<h3 class="card-title text-center text-white mt-5"><strong>あなたの手札</strong></h4>
			<div class="card-area text-center">
			<div class="d-flex flex-wrap justify-content-center">
			<%
			if(player != null && !player.getHandCard().isEmpty()){
				for(Card card : player.getHandCard()){
			%>
			
			<div class="card-item"><%=card.toString()%></div>
			
			<%} %>
			
			<p class="card-score text-dark bg-white">合計：<%=player.getCountHandCard() %></p>
			
			<%}else{ %>
			
				<p class="text-white">no card</p>
				
			<%} %>
			</div>
			</div>
			
		<div class="game-controls text-center mt-4">
		<%
		if(betChip == null){//ゲーム開始前
		%>
		
			<form action="GameServlet" method="post" class="mb-3">
				<div class="form-group">
					<div class="text-center mb-1">
						<label for="betAmount" class="col-form-label text-white">賭けるチップ数を選んでください</label>
					</div>
					<div class="row justify-content-center align-items-center">
					<div class="col-sm-1">
						<input type="number" id="betAmount" name="betAmount" value="0" class="form-control" min="0" max="10" required>
					</div>
					</div>
					<div class="row justify-content-center align-items-center">
					<div class="mt-3 mb-5 col-sm-2">
						<button type="submit" name="action" value="bet" class="btn btn-login btn-block">ゲーム開始</button>
					</div>
					</div>
				</div>
			</form>
		
		<%}else if(roundEnd != null && !roundEnd){//ゲームラウンド中%>
			<div class="mt-5 mb-5">
				<form action="GameServlet" method="post" class="d-inline-block mr-2">
					<button type="submit" name="action" value="hit" class="btn btn-hit">カードを引く</button>
				</form>
				
				<form action="GameServlet" method="post" class="d-inline-block mr-2">
					<button type="submit" name="action" value="stand" class="btn btn-stand">確定</button>
				</form>
			</div>
			
		<%}else if(roundEnd != null && roundEnd){//ゲーム終了 %>
			<form action="GameServlet" method="post" class="mb-3">
				<div class="form-group">
				<div class="text-center mb-1">
					<label for="nextBetAmount" class="col-form-label text-white">次のラウンドで賭けるチップ数を選んでください</label>
				</div>
				<div class="row justify-content-center align-items-center">
					<div class="col-sm-1">
						<input type="number" id="nextBetAmount" name="betAmount" value="0" class="form-control" min="0" max="10" required >
					</div>
				</div>
				</div>
				<div>
				<button type="submit" name="action" value="bet" class="btn btn-login">賭けるチップ数を選んだら、ここを押してください!<br><strong class="text-dark">－次のラウンドを開始－</strong></button>
				</div>
			</form>
			
			<form action="GameServlet" method="post" class="d-inline-block mr-2">
				<button type="submit" name="action" value="reset" class="btn btn-end mb-5" style="margin-top: 30px;">ゲーム終了</button>
			</form>
		<%} %>
		</div>
	</div>
</body>
</html>
