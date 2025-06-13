<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
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
	
	<main>
	 
	 <%
	User user = (User) session.getAttribute("user");
	if (user != null) {
	%>
	
	<div class="welcome-session">
		<h1 class="display-4"><span class="welcome-text text-white">ようこそ！</span>
			<p class="user-name text-white"><strong><%= user.getUserName() %></strong>さん</p></h1>
			<p class="lead text-white">一緒にブラックジャックをプレイしましょう！</p>
	</div>
	
	<div class="action-buttons d-flex flex-column align-items-center">
		<form action="GameServlet" method="post">
			<input type="hidden" name="action" value="start">
			<button type="submit" class="btn btn-login btn-lg">Game Start!</button>
		</form>
	</div>
	
	
	<%
	}else{
	%>
	
	<div class="welcome-session">
		<h1 class="display-4"><span class="welcome-text text-white">ようこそ！</span></h1>
			<p class="lead text-white">ログイン、または新規アカウント登録をしてから遊びましょう！</p>
			<p class="loginlink"><a href="login.jsp">ログインページへ</a></p>
	</div>
	
	
	<%} %>
	
	</main>
	<jsp:include page="common/footer.jsp"/>
	
</body>
</html>