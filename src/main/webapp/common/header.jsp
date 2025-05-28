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
    border-radius: 20px !important;
    font-weight: bold; 
}

.btn-primary:hover {
    background-color: #FF1493; 
    border-color: #FF1493;
}

.btn-success {
    background-color: #C2185B;
    border-color: #D81B60;
    color: #FFFFFF;
    border-radius: 20px !important; 
    font-weight: bold;
}

.btn-success:hover {
    background-color: #C2185B; 
    border-color: #C2185B;
}

.btn-secondary {
    background-color: #C2185B;
    border-color: #FFC0CB;
    color: #FFFFFF;
    border-radius: 20px !important;
}

.btn-secondary:hover {
    background-color: #C2185B;
    border-color: #FFB6C1;
}

</style>

</head>
