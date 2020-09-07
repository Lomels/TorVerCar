<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="currentUser" class="logic.bean.UserBean" scope="session"></jsp:useBean>
<jsp:useBean id="message" class="logic.bean.MessageBean" scope="request"></jsp:useBean>


<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="description" content="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="text/html; charset=iso-8859-2" http-equiv="Content-Type">
	<meta name="viewport"
		content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- The above 4 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	
	<!-- Core Stylesheet -->
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<link href="torvercar.css" rel="stylesheet">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" href="css/font-awesome.min.css"/>
	<link rel="stylesheet" href="css/flaticon.css"/>
	<link rel="stylesheet" href="css/slicknav.min.css"/>
	<link rel="stylesheet" href="css/jquery-ui.min.css"/>
	<link rel="stylesheet" href="css/owl.carousel.min.css"/>
	<link rel="stylesheet" href="css/animate.css"/>
	<link rel="stylesheet" href="sweetalert2.min.css">
	<link rel="stylesheet" href="css/style.css"/>
	
	<script src='https://kit.fontawesome.com/a076d05399.js'></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>

</head>

<!-- ***** Header Area Start ***** -->
<header class="header_area clearfix">
	<div id="navbar">
		
		<a id="title" class="title">TorVerCar.</a>
		<a class="active" href="">Home</a> 
		<a class="right" onclick="document.getElementById('regDialog').style.display='block'">Sign in</a>
		<a class="login right" onclick="document.getElementById('loginDialog').style.display='block'">Login</a> 
		
	</div>
</header>

<body>	
 <div class="bg-image TorVerCar">
	
	<!-- ***** Header Area End ***** -->

	<!-- ***** Welcome Area Start ***** -->
	
	<div class="bg-image home">
	
	<%
		if (message.getMessage() != null) {
	%>
	<script>
	swal({
	  title: 'Error!',
	  text: '${message.getMessage()}',
	  type: '${message.getType()}',
	  confirmButtonText: 'Got it!'
	});</script>
	<%
		}
	%>
	
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
		
		      <label for="psw"><i class="fa fa-lock"></i> Password<label  class="switch" style="margin-left:20px">
						  <input type="checkbox" onclick="showPass()">
						  <div class="slider round"></div></label>
			  </label>
		      <input type="password" id="myPass" placeholder="Enter Password" name="pwd">
		 	  
		 	  <button type="button" class="button cancel" onclick="document.getElementById('loginDialog').style.display='none'">Cancel</button>
		      <button type="submit" class="button" name="action" value="login">Login</button>
		      
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
				              
					              <div class="col-50" >
					              <div class="row">
					                <input type="text" id="userID" name="userID" placeholder="0123456">
					              </div>
					              <div class="col-25">
				        			<button id="btnCheck" class="button" type="submit" name="action" value="check" style="width:300px;">Check Identity</button>
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
				        <button id="btnConfirm" class="button" type="submit" name="action" value="register" style="width:300px; margin-left:40px;">Confirm Registration</button>
				    </div>
				  </div> 
				</div>
				
		  </form>
		</div>		
	
	<!-- ***** Welcome Area End ***** -->

	
	</div>

</body>



<!--====== Javascripts & Jquery ======-->
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
	<script src="js/jquery-3.2.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.slicknav.min.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<script src="js/jquery.nicescroll.min.js"></script>
	<script src="js/jquery.zoom.min.js"></script>
	<script src="js/jquery-ui.min.js"></script>
	<script src="js/main.js"></script>

<script>var message = ${sessionScope.message}
	if(message != null){
		swal({
	  title: 'Error!',
	  text: message,
	  icon: 'error',
	  confirmButtonText: 'Cool'
	});
	}</script>

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
	
</script>	

<script>




	
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
	
	function showPass(){
		 var x = document.getElementById("myPass");
		  if (x.type === "password") {
		    x.type = "text";
		  } else {
		    x.type = "password";
		  }
	}
	

	
</script>

</html>