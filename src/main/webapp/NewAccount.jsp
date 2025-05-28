<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
	<jsp:include page="common/header.jsp"/>
<body>
	<jsp:include page="common/navi.jsp"/>
	
	<main>
		<div class="login-form-container">
		<h2 class="register-heading text-center my-4">新規アカウント登録</h2>
		
		<form action="UserServlet" method="post">
			<div class="input-group input-group-custom">
				<div class="input-group-prepend">
					<span class="input-group-text">ユーザーID</span>
				</div>
				<input type="text" id="loginId" name="loginId" class="form-control" required autofocus>
			</div>
			<div class="input-group input-group-custom">
				<div class="input-group-prepend">
					<span class="input-group-text">ユーザー名</span>
				</div>
				<input type="text" id="username" name="username" class="form-control" required>
			</div>
			<div class="input-group input-group-custom">
				<div class="input-group-prepend">
					<span class="input-group-text">パスワード</span>
				</div>
				<input type="password" id="loginPassword" name="loginPassword" class="form-control" required>
			</div>
			<div class="input-group input-group-custom">
				<div class="input-group-prepend">
					<span class="input-group-text">パスワード(確認用)</span>
				</div>
				<input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
			</div>
			
			<button type="submit" class="btn btn-success btn-block">アカウント作成</button>
		</form>
		<div>
		<div class="text-center mt-3">
		<button type="button" class="btn btn-secondary btn-block" onclick="location.href='login.jsp'">ログインはこちら</button>
		</div>
		</div>
	</main>
	<jsp:include page="common/footer.jsp"/>
	
</body>
</html>