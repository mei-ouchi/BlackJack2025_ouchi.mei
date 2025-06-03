<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %> 
<!DOCTYPE html>
<html>
	<jsp:include page="common/header.jsp"/>
<body>
	<jsp:include page="common/navi_game.jsp"/>
	
	<%
		String message = (String)request.getAttribute("message");
		String error = (String)request.getAttribute("error");
			
		if(message != null && error ==null){
	%>
	
	<div class="alert alert-success" role="alert">
		<%= message %>
	</div>
	
	<%
	} else if(message != null && error != null) { 
	%>
			
	<div class="alert alert-danger" role="alert">
		<%= message %>
	</div>
	
	<% } %>
	
	<main>
	
	<div class="welcome-session">
		<h1 class="display-4 text-center text-white">あなたの戦績</h1>
		<div class="card shadow p-4 mb-5">
			<div class="card-body">
			<%
			User userStats = (User)request.getAttribute("userStats");
			DecimalFormat df = new DecimalFormat("#.##");//小数点以下２桁
			
			if(userStats != null){
			int totalGame = userStats.getTotalGame();
			int wins = userStats.getWins();
			int loses = userStats.getLoses();
			int draws = userStats.getDraws();
			int nowChip = userStats.getNowChip();
			
			double winRate = 0.0;
			if(totalGame > 0){
				winRate = (double)wins / totalGame * 100;
			}
			%>
			
			<p class="user"><strong><%=userStats.getUserName() %>さん</strong></p>
			<p><strong>総プレイ回数：</strong><%=totalGame %></p>
			<p><strong>勝利数：</strong><%=wins %></p>
			<p><strong>敗北数：</strong><%=loses %></p>
			<p><strong>引き分け数：</strong><%=draws %></p>
			<p><strong>勝率:</strong> <%= df.format(winRate) %>%</p>
			<p><strong>現在の保有チップ数：</strong><%=nowChip %></p>
			<p><strong></strong></p>
			
			<%}else{%> 
				<p class="text-center">あなたの戦績はまだありません</p>
			<%}%>
			</div>
		</div>
	
		<h1 class="welcome-session text-white">勝率ランキングTop5</h1>
		<div class="row justify-content-center">
			<%
			List<User> topUserList = (List<User>)request.getAttribute("topUserStatsList");
			DecimalFormat df_top = new DecimalFormat("#.##");
			
			if(topUserList != null && topUserList.isEmpty()){
				for(int i=0; i<topUserList.size(); i++){
					User topUserStats= topUserList.get(i);
					
					int topUserTotalGame = topUserStats.getTotalGame();
					int topUserWins = topUserStats.getWins();
					int topUserNowChip = topUserStats.getNowChip();
					
					double topUserWinRate = 0.0;
					if (topUserTotalGame > 0) {
					topUserWinRate = (double) topUserWins / topUserTotalGame * 100;
			}
			%>
			<div class="col-md-4 col-sm-6 mb-4">
				<div class="card shadow ranking-card">
					<div class="card-bodytext-center">
					<h5 class="card-title ranking-position"><%=i+1 %>位</h5>
					<h6 class="card-subtitle mb-2 text-muted"><%=topUserStats.getUserName() %>さん</h6>
					<p class="card-text">
						勝率：<strong class="text-primary"><%=df_top.format(topUserWinRate) %>%</strong><br>
						総プレイ回数：<%=topUserTotalGame %><br>
						勝利数：<%=topUserWins %><br>
						保有チップ数：<%=topUserNowChip %>
					</p>
					</div>
				</div>
			</div>
            <%
				}
			}else{
			%>
			
			<div class="col-12 text-center">
				<p class="text-white">not person</p>
			</div>
			<%	
			}
			%>
			</div>
		</div>
	</main>
	<jsp:include page="common/footer.jsp"/>
	
</body>
</html>
