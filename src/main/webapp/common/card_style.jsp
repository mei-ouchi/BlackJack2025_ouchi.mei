<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>

.card-area{
	padding: 15px;
	min-height: 120px;
	border-radius: 5px;
	display: flex;
	flex-wrap: wrap;
	justify-content: center;
	align-items: flex-start;
}

.card-item{
	border: 1px solid #ccc;
    padding: 0px;
    margin: 5px;
    background-color: white;
    color: black;
    width: 120px;
    height: 180px;
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
    box-shadow: 2px 2px 5px rgba(0,0,0,0.5);
    border-radius: 8px;
    overflow: hidden;
    position: relative;
}

.card-inner{
	width: 100%;
	height: 100%;
	position: relative;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	align-items: center;
	padding: 8px;
	font-family: 'Courier';
}

.rank{
	font-size: 3em;
	font-weight: bold;
	line-height: 1;
}

.suit{
	font-size: 5em;
	line-height: 1;
	display: block;
	margin-top: auto;
	margin-bottom: auto;
}

.top-right{
	position: absolute;
	top: 5px;
	right: 5px;
}

.bottom-left{
	position: absolute;
	bottom: 5px;
	left: 5px;
	transform: rotate(180deg);
}

.spade-card{
	color: #4169E1;
}

.heart-card{
	color: #FF367F;
}

.diamond-card{
	color: #FF773E;
}

.clover-card{
	color: #32CD32;
}

.card-back{
	background-color: #990000;
	background-image:url('image/cardback.jpg');
	background-size: cover;
	border: 5px solid #fff;
	display: flex;
	justify-content: center;
	align-items: center;
	font-size: 0.8em;
	text-align: center;
	line-height: normal;
	margin-bottom: 0;
}

.card-score{
	font-size: 1.2em;
	font-weight: bold;
	margin-left: 15px;
	align-self: flex-end;
	margin-top: 10px;
}

.card-inner.ADesign{
	background-image: url('image/ace.jpg');
	background-size: contain;
	background-repeat: no-repeat;
	background-position: center;
}

.card-inner.JDesign{
	background-image: url('image/jack.jpg');
	background-size: contain;
	background-repeat: no-repeat;
	background-position: center;
}

.card-inner.QDesign{
	background-image: url('image/queen.jpg');
	background-size: contain;
	background-repeat: no-repeat;
	background-position: center;
}

.card-inner.KDesign{
	background-image: url('image/king.jpg');
	background-size: contain;
	background-repeat: no-repeat;
	background-position: center;
}


</style>
