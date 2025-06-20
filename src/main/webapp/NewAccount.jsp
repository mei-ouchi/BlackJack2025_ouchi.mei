<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

<title>BlackJack</title>
</head>

<body background="image/loginbg.jpg">
	<jsp:include page="common/navi.jsp"/>
	
	<main>
		<div class="login-form-container">
		<h2 class="login-heading text-center text-white my-4"><i class="fas fa-laptop"></i> New Account</h2>
		
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
			
			<button type="submit" class="btn btn-login btn-block mt-4">アカウント作成</button>
		</form>
		<div>
		<div class="text-center mt-3">
		<button type="button" class="btn btn-register btn-block" onclick="location.href='login.jsp'">ログインはこちら</button>
		</div>
		</div>
	</main>
	<jsp:include page="common/footer.jsp"/>
	
</body>
</html>