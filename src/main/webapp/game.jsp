<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="model.User" %>
<%@ page import="model.Card" %>
<%@ page import="model.Player" %>
<%@ page import="model.Dealer" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
	<jsp:include page="common/header.jsp"/>
<body>
	<jsp:include page="common/navi_game.jsp"/>
	
	
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
			<h1 class="user" mb-0"><strong>ラウンド<%= roundNumber %></strong></h1>
		</div>
		<% } %>
		
		<div class="d-flex justify-content-end align-items-start px-3 py-2">
			<% if(user != null){ %>
			<div class="text-center text-white">
				<p class="mb-0"><strong><%= user.getUserName() %></strong>さん</p>
				<p class="mb-0">あなたの保有チップ数：<strong><%= user.getNowChip() %></strong></p>
			</div>
			<% } %>
		</div>
		
			<h4 class="card-title text-center text-white">ディーラーの手札</h4>
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
			
				<p class="card-score text-white">合計：<%=dealer.getCountHandCard() %></p>
				
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
			
			<h4 class="card-title text-center text-white" style="margin-top: 70px;">あなたの手札</h4>
			<div class="card-area text-center">
			<div class="d-flex flex-wrap justify-content-center">
			<%
			if(player != null && !player.getHandCard().isEmpty()){
				for(Card card : player.getHandCard()){
			%>
			
			<div class="card-item"><%=card.toString()%></div>
			
			<%} %>
			
			<p class="card-score text-white">合計：<%=player.getCountHandCard() %></p>
			
			<%}else{ %>
			
				<p class="text-white">no card</p>
				
			<%} %>
			</div>
			</div>
			
		<div class="game-controls text-center mt-4">
		