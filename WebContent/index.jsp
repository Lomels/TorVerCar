<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="currentUser" class="logic.bean.UserBean" scope="session"></jsp:useBean>

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
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
</head>

<body>	<div class="bg-image home">	

	
	<!-- ***** Header Area Start ***** -->
	<header class="header_area clearfix">
		<div id="navbar">
			<a id="title" class="title">TorVerCar.</a>
			<a class="active" href="">Home</a> 
			<a href="">Book</a> 
			<a href="">Offer</a>
			<a href="">MyCar</a> 
			<a href="">MyLift</a> 
			<a class="right" onclick="document.getElementById('regDialog').style.display='block'">Sign in</a>
			<a class="login right" onclick="document.getElementById('loginDialog').style.display='block'">Login</a> 
			
		</div>
	</header>
	
	<div class="column2" style="background-color:trasparent;"></div>
	<div class="column" style="background-color:trasparent; width:40%;">
	<div class="row" style="height:75%;"></div>
	<h1 style="font-size:96px;">We are not Uber.</h1>
	</div>
	
	<!-- ***** Header Area End ***** -->

	<!-- ***** Welcome Area Start ***** -->
	
	<!-- Login Dialog -->	
		<div id="loginDialog" class="modal">
		
		  <form class="modal-content animate" action="LoginControllerServlet" method="POST">
		    <div class="container">
		    
		    <div class="row">
		    	<div class="col-50">
			    <h3>Login</h3>
			    </div>
			    <div class="col-50">
			    <span onclick="document.getElementById('loginDialog').style.display='none'" class="close" title="Close Modal">&times;</span>
			 	</div>
			 </div>
		      
		      <label for="uname"><i class="fa fa-id-badge"></i> Student ID</label>
		      <input type="text" placeholder="Enter Username" name="userID" required>
		
		      <label for="psw"><i class="fa fa-lock"></i> Password</label>
		      <input type="text" placeholder="Enter Password" name="pwd">
		 	  
		 	  <button type="button" class="cancel" onclick="document.getElementById('loginDialog').style.display='none'">Cancel</button>
		      <button type="submit" name="action" value="login">Login</button>
		      
		    </div>
		  </form>
		</div>
	<!-- End Dialog -->
	
	<!-- Registration Dialog -->
		<div id="regDialog" class="modal">
		  
		  <form class="modal-content animate bigger" action="LoginControllerServlet" method="post">
		
				<div class="row">
				  <div class="col-75">
				    <div class="container">
				      <div class="row">
			    		<span onclick="closeReg()" class="close" title="Close Modal">&times;</span>
			 		</div>
				        <div class="row">
				          <div class="col-25">
				            <h3>Registration</h3>
				            <label for="fname"><i class="fa fa-id-badge"></i> Student ID</label>
				              <div class="row">
					              <div class="col-50">
					                <input type="text" id="userID" name="userID" placeholder="0123456">
					              </div>
					              <div class="col-50">
				        			<button id="btnCheck" type="submit" name="action" value="check" style="width:auto;">Check Identity</button>
					              </div>
				             </div>
			
				          <div class="row ">
				          	<div id="pwdForm" class="col-50 hidden">
				              <label for="adr"><i class="fa fa-lock"></i> Password</label>
				              <input type="password" id="password" name="password" placeholder="">
				              <label for="city"><i class="fa fa-institution"></i> Repeat Password</label>
				              <input type="password" class="invalid" id="repeat" name="repeat" placeholder="">
				 			</div>
				          </div>
				        </div>
				          <div id="dbData" class="col-50 hidden">
				            <h3>Your Data</h3>
				            <label for="fname"><i class="fa fa-user"></i> Full Name</label>
				            <input type="text" id="fname" name="fullname" placeholder="<%= currentUser.getName() %>" disabled>
				            <label for="email"><i class="fa fa-envelope"></i> Email</label>
				            <input type="text" id="email" name="email" placeholder="<%= currentUser.getEmail() %>" disabled>
				            <label for="adr"><i class="fa fa-phone"></i> Phone</label>
				            <input type="text" id="phone" name="phone" placeholder="3336669990" >
				            
				          </div>				          
				          
				        </div>
				        <hr>
				        <button id="btnConfirm" type="submit" name="action" value="register" style="width:auto;">Confirm Registration</button>
				    </div>
				  </div> 
				</div>
				
		  </form>
		</div>		
	</div>
	<!-- ***** Welcome Area End ***** -->
	
	
</body>

<script>
	var sessionCheck = ${sessionScope.check};
	if(sessionCheck == true){
		document.getElementById("userID").placeholder = "0"+${sessionScope.userID};
		document.getElementById("userID").disabled = true;
		document.getElementById("password").required = true;
		document.getElementById("repeat").required = true;
		document.getElementById("phone").required = true;
		document.getElementById('regDialog').style.display='block';
		document.getElementById("dbData").style.display = 'block';
        document.getElementById("pwdForm").style.display = 'block';
        document.getElementById("btnCheck").style.display = 'none';
	}	
	
	var password = document.getElementById("password");
	var confirm_password = document.getElementById("repeat");
	var status = document.getElementById("status");

	
	function validatePassword(){
	  if(password.value != confirm_password.value | password.value == '') {
		confirm_password.classList.remove("valid");
		confirm_password.classList.add("invalid");
	    document.getElementById("btnConfirm").disabled = true;
	} else {
		confirm_password.classList.remove("invalid");
		confirm_password.classList.add("valid");
	    document.getElementById("btnConfirm").disabled = false;
	  }
	}

	password.onchange = validatePassword;
	confirm_password.onkeyup = validatePassword;
	
	
	function show(){
    	document.getElementById("dbData").style.display = 'block';
        document.getElementById("pwdForm").style.display = 'block';
        document.getElementById("btnCheck").style.display = 'none';
    }
	
	function closeReg(){
		document.getElementById('regDialog').style.display='none';
		document.getElementById("dbData").style.display = 'none';
        document.getElementById("pwdForm").style.display = 'none';
        document.getElementById("btnCheck").style.display = 'block';
	}
</script>

</html>