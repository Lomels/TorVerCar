<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="description" content="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport"
		content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- The above 4 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	
	<!-- Core Stylesheet -->
	<link href="torvercar.css" rel="stylesheet">
	
</head>

<body>
	<!-- ***** Header Area Start ***** -->
	<header class="header_area clearfix">
		<div id="navbar">
			<a class="active" href="">Home</a> 
			<a href="">Book</a> 
			<a href="">Offer</a>
			<a href="">MyCAR</a> 
			<a href="">MyLIFT</a> 
			<a class="right" onclick="document.getElementById('regDialog').style.display='block'">Sign in</a>
			<a class="login right" onclick="document.getElementById('loginDialog').style.display='block'">Login</a> 
		</div>
	</header>
	<!-- ***** Header Area End ***** -->

	<!-- ***** Welcome Area Start ***** -->
	<div class="bg-image">	
	
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
	<!-- ***** Welcome Area End ***** -->
</body>

</html>