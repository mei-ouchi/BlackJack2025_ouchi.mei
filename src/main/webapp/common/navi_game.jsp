<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow fixed-top">
	<div class="container">
	
	<a class="navbar-brand" href="game_top.jsp">BlackJack</a>
	
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
    </button>
    
	<%
		User user = (User)session.getAttribute("user");
		if(user != null){
	 %>
	    
 		<div class="collapse navbar-collapse" id="navbarResponsive">
 		<div class="navbar-text text-mute ml-3">
					ログイン中：<%= user.getUserName() %>さん
		</div>
			<ul class="navbar-nav ml-auto">
				<li class="nav-item">
					<a class="btn btn-gray" href="game_top.jsp">トップへ戻る</a>
				</li>
				<li class="nav-item">
					<a class="btn btn-gray" href="RankingServlet">あなたの戦績</a>
				</li>
				<li class="nav-item">
					<a class="btn btn btn-gray" href="LoginServlet">ログアウト</a>
				</li>
				<li class="nav-item">
					<a class="btn btn-del" href="#" onclick="return checkDelete()">アカウントの削除</a>
				</li>
			</ul>
		</div>  
	<%} %>	          
	
	</div>
</nav>

<script type="text/javascript">
	function checkDelete(){
		if(confirm("アカウント情報は復元することはできません。本当にアカウントを削除しますか？")){
			window.location.href = "UserServlet?action=delete";
			return true;
			}else {
				return false;
		}
	}
</script>


