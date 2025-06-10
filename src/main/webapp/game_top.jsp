<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html>
	<jsp:include page="common/header.jsp"/>
<body>
	<jsp:include page="common/navi_game.jsp"/>
	
	<main>
	 
	 <%
	User user = (User) session.getAttribute("user");
	if (user != null) {
	%>
	
	<div class="welcome-session">
		<h1 class="display-4"><span class="welcome-text text-dark">ようこそ！</span>
			<p class="user-name text-dark"><strong><%= user.getUserName() %></strong>さん</p></h1>
			<p class="lead text-dark">一緒にブラックジャックをプレイしましょう！</p>
	</div>
	
	<div class="action-buttons d-flex flex-column align-items-center">
		<form action="GameServlet" method="post">
			<input type="hidden" name="action" value="start">
			<button type="submit" class="btn btn-start btn-lg">Game Start!</button>
		</form>
	</div>
	
	
	<%
	}else{
	%>
	
	<div class="welcome-session">
		<h1 class="display-4"><span class="welcome-text text-dark">ようこそ！</span></h1>
			<p class="lead text-dark">ログイン、または新規アカウント登録をしてから遊びましょう！</p>
			<p class="loginlink"><a href="login.jsp">ログインページへ</a></p>
	</div>
	
	
	<%} %>
	
	</main>
	<jsp:include page="common/footer.jsp"/>
	
</body>
</html>