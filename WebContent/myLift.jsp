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
	<meta charset="UTF-8">
	<title>TorVerCar</title>
</head>

<header class="header_area clearfix">
		<div id="navbar">
			<a id="title" class="title">TorVerCar.</a>
			<a href="homepage.jsp"><i class='fas fa-home' style='font-size:36px'></i></a> 
			<a href="book.jsp">Book</a> 
			<a href="offer.jsp">Offer</a>
			<a href="myCar.jsp">MyCar</a>
			<a class="active" href="myLift.jsp">MyLift</a> 
			<form action="LoginControllerServlet" method="POST">
			<button type="submit" name="action" value="logout" class="right"><i class='fas fa-door-open' style='font-size:36px'></i></button>
			</form>
			<a class="right" href="profile.jsp"><i class='fas fa-user-graduate' style='font-size:36px'></i></a> 
		</div>
</header>



<body>
<div class="bg-image book">

<%
		if (message.getMessage() != null) {
	%>
	 <script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
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
	<div class="row fullscreen">
		<div class="col-75">
			<div class="card lift">
				<label  class="center switch">
		  			<input type="checkbox" id="cb" name="switch" value="offered" onclick="change()" checked>
		 	    		<div class="slider round">
		 	    			<span class="Booked">BOOKED</span>
		 	    			<span class="Offered">OFFERED</span>
		 	    		</div>
		 	    </label>
	 	    	<span id="swap" class="show-booked">
		 	    	<span class="offered">
		 	    	<c:choose>
						<c:when test = "${role eq 'driver'}">
							<c:forEach items="${user.getOfferedLift()}" var="item">
						      	<label class="container">
				  				From: ${item.getRoute().getPickupPosition().getAddress()}
			  					<br>
			  					To: ${item.getRoute().getDropoffPosition().getAddress()}
			  					<br>
			  					When: ${item.getStringDate()}
			  					<br>
			  					Depart at: ${item.getStringStartTime()}	
			  					<br>
			  					Arrive up: ${item.getStringStopTime()}				
								</label>
					    	</c:forEach> 
						</c:when>
						<c:otherwise>
							<h3>You havent't offered any lifts!</h3>
						</c:otherwise>
					</c:choose>	
					</span>
		 	    	<span class="booked">
		 	    		<c:forEach items="${user.getBookedLift()}" var="item">
					      	<label class="container">
			  					From: ${item.getRoute().getPickupPosition().getAddress()}
			  					<br>
			  					To: ${item.getRoute().getDropoffPosition().getAddress()}
			  					<br>
			  					When: ${item.getStringDate()}
			  					<br>
			  					Depart at: ${item.getStringStartTime()}	
			  					<br>
			  					Arrive up: ${item.getStringStopTime()}  					
							</label>
					    </c:forEach>
					</span>
	 	    	</span>
	 	    	</div>
	 	    </div>
		</div>
<div class="column" style="background-color:trasparent;"></div>
</div>
</body>

<script>
function change(){
	var cb = document.getElementById("cb").checked;
	if(cb == true){
		document.getElementById("swap").classList.remove("show-offered");		
		document.getElementById("swap").classList.add("show-booked");
	}else{
		document.getElementById("swap").classList.remove("show-booked");		
		document.getElementById("swap").classList.add("show-offered");
	}
}
</script>
</html>