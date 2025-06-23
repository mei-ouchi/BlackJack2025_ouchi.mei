<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
<link rel="stylesheet" href="css/common.css">
<title>BlackJack</title>
</head>

<body background="image/gametopbg.jpg">
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
	
		<div class="ranking-session mb-4">
			<h2><span>あなたの戦績</span></h2>
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
			
			<p class="user"><strong><%=userStats.getUserName() %></strong>さん</p>
			<p><strong>総プレイ回数：</strong><%=totalGame %></p>
			<p><strong>勝利数：</strong><%=wins %></p>
			<p><strong>敗北数：</strong><%=loses %></p>
			<p><strong>引き分け数：</strong><%=draws %></p>
			<p><strong>勝率:</strong> <%= df.format(winRate) %>%</p>
			<p><strong>現在の保有チップ数：</strong><%=nowChip %></p>
			
			<%}else{%> 
				<p class="text-center">あなたの戦績はまだありません</p>
			<%}%>
			</div>
		</div>
		<div class="ranking-session text-center mb-4">
			<h2><span>勝率ランキングTop5</span></h2>
		</div>
		<div class="row justify-content-center">
			<div class="col-12 col-md-12 col-lg-12">
				<div class="table-responsive">
					<table class="table table-dark bg-white table-striped table-hover text-dark">
						<thead class="table-head">
							<tr class="text-center">
								<th scope="col">順位</th>
								<th scope="col">ユーザ名</th>
								<th scope="col">勝率</th>
								<th scope="col">保有チップ数</th>
							</tr>
						</thead>
						<tbody>
						<%
							List<User> topUserList = (List<User>)request.getAttribute("topUserStatsList");
							DecimalFormat df_top = new DecimalFormat("#.##");
			
							if(topUserList != null && !topUserList.isEmpty()){
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
							
							<tr class="text-center">
								<td><strong><%=topUserStats.getRank() %>位</strong></td>
								<td><p class="user"><strong><%=topUserStats.getUserName() %></strong>さん<p></td>
								<td><%=df_top.format(topUserWinRate) %>%</td>
								<td><%=topUserNowChip %></td>
							</tr>
            				<%
								}
							}else{
							%>
							<tr>
								<td colspan="6" class="text-center text-whote">現在表示できるユーザはいません</td>
							</tr>
							<%} %>
						</tbody>
					</table>
					</div>
				</div>
			</div>
	</main>
	<jsp:include page="common/footer.jsp"/>
	
</body>
</html>
