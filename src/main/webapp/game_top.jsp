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
		<h1 class="display-4"><span class="welcome-text">ようこそ！</span>
			<p class="user-name text-white"><strong><%= user.getUserName() %></strong>さん</p></h1>
			<p class="lead text-white">一緒にブラックジャックをプレイしましょう！</p>
	</div>
	
	<div class="action-buttons d-flex flex-column align-items-center">
		<button type="button" class="btn btn-start btn-lg" onclick="location.href='GameServlet'">Game Start!</button>
	</div>
	
	
	<%
	}else{
	%>
	
	<div class="welcome-session">
		<h1 class="display-4"><span class="welcome-text">ようこそ！</span></h1>
			<p class="lead text-white">ログイン、または新規アカウント登録をしてください！</p>
			<p class="loginlink"><a href="login.jsp">ログインページへ</a></p>
	</div>
	
	
	<%} %>
	
	</main>
	<jsp:include page="common/footer.jsp"/>
	
</body>
</html>