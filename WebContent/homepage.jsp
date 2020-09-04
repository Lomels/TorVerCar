<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link href="torvercar.css" rel="stylesheet">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<script src='https://kit.fontawesome.com/a076d05399.js'></script>
	<title>Insert title here</title>
</head>
<body>
<!-- ***** Header Area Start ***** -->
	<header class="header_area clearfix">
		<div id="navbar">
			<a class="active" href=""><i class='fas fa-home' style='font-size:36px'></i></a> 
			<a href="book.jsp">Book</a> 
			<a href="offer.jsp">Offer</a>
			<a href="myCar.jsp">MyCAR</a> 
			<a href="myLift.html">MyLIFT</a> 
			<a class="right" onclick="document.getElementById('regDialog').style.display='block'"><i class='fas fa-door-open' style='font-size:36px'></i></a>
			<a class="right" onclick="document.getElementById('loginDialog').style.display='block'"><i class='fas fa-user-graduate' style='font-size:36px'></i></a> 
		</div>
	</header>
	<!-- ***** Header Area End ***** -->

	<!-- ***** Welcome Area Start ***** -->
	<div class="bg-image home">	
	
	<!-- Login Dialog -->	
		<div id="loginDialog" class="modal">
		  <form class="modal-content animate" action="LoginControllerServlet" method="post">
		    <div class="container">
		      <label class="greentext" for="uname"><b>UserID</b></label>
		      <input id="usr" type="text" placeholder="Enter Username" name="userID" required>
		
		      <label class="greentext" for="psw"><b>Password</b></label>
		      <input id="psw" type="password" placeholder="Enter Password" name="psw" required>
		 	  
		 	  <button type="button" class="cancel" onclick="document.getElementById('loginDialog').style.display='none'">Cancel</button>
		      <button type="submit" name="action" value="login">Login</button>
		      
		    </div>
		  </form>
		</div>
	<!-- End Dialog -->
	
	<!-- Registration Dialog -->
		<div id="regDialog" class="modal">
		  
		  <form class="modal-content animate" action="/action_page.php" method="post">
		
		    <div class="container">
		      <label class="greentext" for="uname"><b>Student ID</b></label>
		      <input type="text" placeholder="Enter your Student ID" name="uname" required>
		
		      <label class="greentext" for="psw"><b>Password</b></label>
		      <input type="password" placeholder="Enter Password" name="psw" required>
		 	  
		 	  <button type="button" class="cancel" onclick="document.getElementById('id01').style.display='none'">Cancel</button>
		      <button type="submit">Sign up</button>
		      
		    </div>
		  </form>
		</div>
	</div>
</body>
</html>