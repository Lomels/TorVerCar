<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="message" class="logic.bean.MessageBean" scope="request"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
href="torvercar.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>

<meta charset="ISO-8859-1">
<title>TorVerCar</title>
</head>

<header class="header_area clearfix">
		<div id="navbar">
			<a id="title" class="title">TorVerCar.</a>
			<a href="homepage.jsp"><i class='fas fa-home' style='font-size:36px'></i></a> 
			<a href="book.jsp">Book</a> 
			<a href="offer.jsp">Offer</a>
			<a href="myCar.jsp">MyCar</a>
			<a href="myLift.jsp">MyLift</a> 
			<form action="LoginControllerServlet" method="POST">
			<button type="submit" name="action" value="logout" class="right"><i class='fas fa-door-open' style='font-size:36px'></i></button>
			</form>
			<a class="active right" href="profile.jsp"><i class='fas fa-user-graduate' style='font-size:36px'></i></a> 
		</div>
</header>

<body>
<div class="bg-image profile">
<%
		if (message.getMessage() != null) {
	%>
	 <script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
	<script>
	swal({
	  title: 'Error!',
	  text: '${message.getMessage()}',
	  type: '${message.getType()}',
	  confirmButtonText: 'Cool'
	});</script>
	<%
		}
	%>
	<div class="row fullscreen">
	

	<div class="column" style="background-color:transparent;"></div>
		<div class="column" style="background-color:transparent;"></div>
			<div class="column2" style="background-color:transparent;">
				<div  class="card animate" > 
				<form action="ProfileControllerServlet" method="POST" style="width:60%;">

  						  <h2><i class='fas fa-user-graduate'></i>&nbsp; Profile Card</h2>
						  <label><i class="fa fa-user"></i>&nbsp;Name:  </label>
						  <input type="text" name="name" value="${user.getName() }" id="myName" style="width: 300px; height:30px;" disabled>
						  
						  <label><i class="fa fa-user"></i>&nbsp;Surname:  </label>
						  <input type="text" name="surname" value="${user.getSurname() }" id="mySurname" style="width: 300px; height:30px;" disabled>
						  
						  <label><i class="fa fa-id-badge"></i>&nbsp;Matriculation number:  </label>
						  <input type="text" name="userID" value="${user.getUserID() }" id="myID" style="width: 300px; height:30px;" disabled>
						  
						  
						  <label><i class="fa fa-phone"></i>&nbsp;Phone number:  </label>
						  <input type="text" name="phone" value="${user.getPhone() }" id="myPhoneNum" style="width: 300px; height:30px;" disabled>
						  
						  <label><i class="fa fa-envelope"></i>&nbsp;Email:  </label>
						  <input type="text" name="email" name="surname" value="${user.getEmail() }" id="myEmail" style="width: 300px; height:30px;" disabled>

						 
 						  <label><i class="fa fa-lock"></i>&nbsp;Password: <label  class="switch" style="margin-left:20px">
						  <input type="checkbox" onclick="showPass()">
						  <div class="slider round"></div></label> </label>
 
 						  <input type="password" name="password" value="${user.getPassword() }" id="myPass" style="width: 300px; height:30px;" disabled>
						
							<div class="col-50">

								<div id="saveRow" class="row" style="height:50px; display:none;">
									<button id="btnSave" class="button" type="submit" name="action" value="save" style="width:150px;">Save</button>
								</div>	
 							</div>
						</form>
					
 						<div class="col-50">
 							<div id="backRow" class="row" style="height:50px; display:none;">
 								<button id="btnBack" class="button" style="width:150px;" onclick="back()">Back <i class='fas fa-angle-double-left'></i></button>
 							</div>
 							<div id="editRow" class="row" style="height:50px">
 								<button id="btnEdit" class="button" style="width:150px" onclick="edit()">Edit</button>
							</div>
						</div>
 				</div>
 			</div>
		</div>
 </div>

<script>
	function edit() {
		document.getElementById("editRow").style.display = "none";
		document.getElementById("backRow").style.display = "block";
		document.getElementById("saveRow").style.display = "block";

		document.getElementById("myPhoneNum").disabled = false;
		document.getElementById("myEmail").disabled = false;
		document.getElementById("myPass").disabled = false;
	}
</script>

<script>
	function back() {
		document.getElementById("editRow").style.display = "block";
		document.getElementById("backRow").style.display = "none";
		document.getElementById("saveRow").style.display = "none";

		document.getElementById("myPhoneNum").value = '${user.getPhone() }'; //TODO get from database
		document.getElementById("myEmail").value = '${user.getEmail() }';
		document.getElementById("myPass").value = '${user.getPassword() }';

		document.getElementById("myPhoneNum").disabled = true;
		document.getElementById("myEmail").disabled = true;
		document.getElementById("myPass").disabled = true;
	}

	function showPass() {
		var x = document.getElementById("myPass");
		if (x.type === "password") {
			x.type = "text";
		} else {
			x.type = "password";
		}
	}
</script>




  
  



</body>
</html>