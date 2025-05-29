<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html>
	<jsp:include page="common/header.jsp"/>
<body>
	<jsp:include page="common/navi_game.jsp"/>
	
	<main>
	 
	<div class="welcome-session">
		<h1 class="display-4">ようこそ！ <%= user.getUserName() %> さん</h1>
			<p class="lead">一緒にブラックジャックをプレイしましょう！</p>
	</div>
	
	<div class="action-buttons d-flex flex-column align-items-center">
		<button type="button" class="btn btn-start btn-lg" onclick="location.href='GameServlet'">Game Start!</button>
	</div>
	
	
	</main>
	<jsp:include page="common/footer.jsp"/>
	
</body>
</html>