<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
	<jsp:include page="common/header.jsp"/>
<body>
	<jsp:include page="common/navi.jsp"/>
	
	<main>
		<div class="login-form-container">
		<h2 class="register-heading text-center text-white my-4">新規アカウント登録</h2>
		
		<%
			String message = (String)request.getAttribute("message");
			String error = (String)request.getAttribute("error");
			
			if(message != null && error ==null){
		%>
			<div class="alert alert-success" role="alert">
				<%= message %>
			</div>
		<%
			} else if(message != null && error != null) { %>
			<div class="alert alert-danger" role="alert">
				<%= message %>
			</div>
		<% } %>
		
		<form action="UserServlet" method="post">
			<div class="input-group input-group-custom">
				<div class="input-group-prepend">
					<span class="input-group-text">ユーザーID</span>
				</div>
				<input type="text" id="userId" name="user_id" class="form-control" required autofocus>
			</div>
			<div class="input-group input-group-custom">
				<div class="input-group-prepend">
					<span class="input-group-text">ユーザー名</span>
				</div>
				<input type="text" id="userName" name="user_name" class="form-control" required>
			</div>
			<div class="input-group input-group-custom">
				<div class="input-group-prepend">
					<span class="input-group-text">パスワード</span>
				</div>
				<input type="password" id="loginPassword" name="password" class="form-control" required>
			</div>
			<div class="input-group input-group-custom">
				<div class="input-group-prepend">
					<span class="input-group-text">パスワード(確認用)</span>
				</div>
				<input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
			</div>
			
			<button type="submit" class="btn btn-orange btn-block">アカウント作成</button>
		</form>
		<div>
		<div class="text-center mt-3">
		<button type="button" class="btn btn-lightorange btn-block" onclick="location.href='login.jsp'">ログインはこちら</button>
		</div>
		</div>
	</main>
	<jsp:include page="common/footer.jsp"/>
	
</body>
</html>