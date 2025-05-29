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
    
	background-image: url('image/d884b87e9adac683aa302a7735feb852.jpg');
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
    color: #C2185B;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
    margin-bottom: 1.5rem !important;
}

.btn-primary {
    background-color: #C2185B;
    border-color: #C2185B;
    color: #FFFFFF;
    border-radius: 10spx !important;
    font-weight: bold; 
}

.btn-primary:hover {
    background-color: #FF1493; 
    border-color: #FF1493;
}

.btn-secondary {
    background-color: #FFB6C1;
    border-color: #C2185B;
    color: #C2185B;
    border-radius: 10px !important;
}

.btn-secondary:hover {
    background-color: #FFC0CB;
    color: #FF1493;
    border-color: #FF1493;
}

.btn-success {
    background-color: #FFFFFF;
    border-color: #FFFFFF;
    color: #000000;
    border-radius: 10px !important; 
    font-weight: bold;
}

.btn-success:hover {
    background-color: #F0F0F0; 
    border-color: #AAAAAA;
}

.btn-del {
    background-color: #FFFFFF;
    border-color: #FFFFFF;
    color: #CC0000;
    border-radius: 10px !important; 
    font-weight: bold;
}

.btn-del:hover {
    background-color: #F0F0F0; 
    border-color: #AAAAAA;
}

.btn-start {
    background-color: #C2185B;
    border-color: #C2185B;
    color: #FFFFFF;
    border-radius: 50px !important;
    font-weight: bold;
}

.btn-start:hover {
    background-color: #FF1493; 
    border-color: #FF1493;
}

</style>

</head>
