<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
<title>BlackJack</title>
<style>

body {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
    
	background-image: url('image/1624203006_wXuRKy.jpg');
	background-size: cover;
	background-repeat: no-repeat;
	background-position: center center;
	background-attachment: fixed;
}

main {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 20px 0;
}


.welcome-session h1{
	font-family: 'M PLUS Rounded 1c', sans-serif;
    font-weight: 700;
    color: #333;
}

.welcome-session p{
	font-family: 'M PLUS Rounded 1c', sans-serif;
    font-weight: 500;
    color: #333;
}

.login-heading, .register-heading {
    font-family: 'Mochiy Pop One', cursive, sans-serif;
    font-size: 2.5rem;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
    margin-bottom: 1.5rem !important;
}

.btn-primary {
    background-color: #fd7e00;
    border-color: #fd7e00;
    color: #FFFFFF;
    border-radius: 10spx !important;
    font-weight: bold; 
}

.btn-primary:hover {
    background-color: #ffa965; 
    border-color: #ffa965;
}

.btn-secondary {
    background-color: #ffa965;
    border-color: #ffa965;
    color: #ffffff;
    border-radius: 10px !important;
}

.btn-secondary:hover {
    background-color: #ffbf8b;
    color: #ffffff;
    border-color: #ffbf8b;
}

.btn-success {
    background-color: #bcbcbc;
    border-color: #bcbcbc;
    color: #000000;
    border-radius: 10px !important; 
    font-weight: bold;
}

.btn-success:hover {
    background-color: #f5f5f5; 
    border-color: #f5f5f5;
    color: #333333;
}

.btn-del {
    background-color: #bcbcbc;
    border-color: #bcbcbc;
    color: #CC0000;
    border-radius: 10px !important; 
    font-weight: bold;
}

.btn-del:hover {
    background-color: #f5f5f5; 
    border-color: #f5f5f5;
    color: #ff0000;
}

.btn-start {
    background-color: #fd7e00;
    border-color: #fd7e00;
    color: #FFFFFF;
    border-radius: 50px !important;
    font-weight: bold;
}

.btn-start:hover {
    background-color: #ff9d30; 
    border-color: #ff9d30;
}

.user-name strong{
	color: #ff8c00;
	margin-left: 400px;
}

.welcome-text{
	font-size: 1.5em;
	color: #FFFFFF;
	margin-left: 300px;
}

.user strong{
	color: #C2185B;
}

.lead {
	margin-left: 300px;
}

.loginlink{
	margin-left: 700px;
}

</style>

</head>
