<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="offerBean" class="logic.bean.OfferBean" scope="session"></jsp:useBean>
<jsp:useBean id="message" class="logic.bean.MessageBean" scope="request"></jsp:useBean>
    
<!DOCTYPE html>
<html>
<head>
<link href="torvercar.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>

<script src='https://kit.fontawesome.com/a076d05399.js'></script>

<meta charset="ISO-8859-1">
<title>TorVerCar</title>
</head>

<header>
	<% 
		if(session.getAttribute("status") == null){
			session.setAttribute("status", "");
		}
	%>
	<c:set var="startP" scope="session" value="${offerBean.getStops()}"/>


	<div id="navbar">
		<a id="title" class="title">TorVerCar.</a>
		<a href="homepage.jsp"><i class='fas fa-home' style='font-size:36px'></i></a> 
		<a class="active" href="book.jsp">Book</a> 
		<a href="offer.jsp">Offer</a>
		<a href="myCar.jsp">MyCar</a>
		<a href="myLift.jsp">MyLift</a> 
		<form action="LoginControllerServlet" method="POST">
		<button class="right"><i class='fas fa-door-open' style='font-size:36px'></i></button>
		</form>
		<a class="right" href="profile.jsp"><i class='fas fa-user-graduate' style='font-size:36px'></i></a> 
	</div>
</header>

<body>

<div  class="bg-image book">

<%
		if (message.getMessage() != null) {
	%>
	 <script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
	<script>
	swal({
	  title: '${message.getTitle()}',
	  text: '${message.getMessage()}',
	  type: '${message.getType()}',
	  confirmButtonText: 'Close'
	});</script>
	<%
		}
	%>
<!-- START OFFER FORM -->
	<div class="row fullscreen">
		<div class="col-75">
			<form class="card" action="BookControllerServlet" method="POST">
				<h2>Book Lift</h2>

				<div class="row">
					<div class="col-50">
						<label><i class="fa fa-map-marked-alt"></i> Starting
							point:</label>
						<div class="row">
							<div class="col-50">
								<c:choose>
									<c:when test = "${startP.size() > 0}">
										<input type="text" placeholder="${startP.get(0).getAddress()}" id="start" name="start" disabled required>
									</c:when>
									<c:otherwise>
										<input type="text" placeholder="via prima strada" id="start" name="start">
									</c:otherwise>
								</c:choose>							
							</div>
							<div class="col-25">
								<button class="button invisible" type="submit" name='action' value='startPos'><i class='fas fa-search-location fa-2x'></i></button>
							</div>
						</div>

						<label><i class="fa fa-flag-checkered"></i> Destination: </label>
						<div class="row">
							<div class="col-50">
								<c:choose>
									<c:when test = "${startP.size() > 1}">
										<input type="text" placeholder="${startP.get(1).getAddress()}" id="destination" name="dest" disabled required>
									</c:when>
									<c:otherwise>
										<input type="text" placeholder="via cambridge" id="destination" name="dest">
									</c:otherwise>
								</c:choose>	
							</div>
							<div class="col-50">
								<button class="button invisible" type="submit" name='action' value='destPos'><i class='fas fa-search-location fa-2x'></i></button>							
							</div>
						</div>
						
						<div class="row">
							<div class="col-50">
								<div class="row">
								<h2 id="hGoing" style="margin-left:15px">Going: </h2>
									<div class="col-25">
										<label class="switch" style="margin-top:47px; margin-right:20px;">
									  		<input type="checkbox" id="switch" name="switch" value="going" onclick="change()">
									 	    	<div class="slider round">
									 	    		<span class="Booked"></span>
									 	   			<span class="Offered"></span>
									 	   		</div>
								 		</label>
									</div>
								</div>
						    </div> 
						</div>						    
						
						
						<div class="row">
							<div class="col-25">
								<label><i class="fa fa-calendar-alt"></i> Day: </label> 
								<input type="date" placeholder="" id="day" name="day" style="width:550px; height:42px;  ">
							</div>
							<div class="col-25">
								<div class="row">
									<i class="fa fa-clock"></i>
										<div class="col-25">
											<label id="lbArrive"> Arrive at: </label> 
										</div>	
										<input type="time" placeholder="HH:MM" id="arrivalTime" name="time" style="width:550px; height:42px;">
								</div>
							</div>
						</div>
						<br>
						<button id="btnConfirm" class="button" type="submit" name="action" value="search" style="width:150px; margin-left:0px;">Book Lift</button>

						
					</div>
				</div>
			</form>
		</div>
	</div>
<!-- END OFFER FORM -->
<!-- START POSITION LIST DIALOG -->
	<div id="listDialog" class="modal">
		  <form class="modal-content animate" action="BookControllerServlet" method="POST">
		    <div class="container">
		    
		    <div class="row">
		    	<div class="col-50">
			    <h3>Choose the correct position</h3>
			    </div>
			    <div class="col-50">
			    <span onclick="document.getElementById('listDialog').style.display='none'" class="close" title="Close Modal">&times;</span>
			 	</div>
			 </div>
			      <c:forEach items="${offerBean.getResult()}" var="item">
			      	<label class="container">
	  					<input type="radio" name="index" value="${offerBean.getResult().indexOf(item)}"><span class="checkmark"></span>
	  					${item.getAddress()}	  					
					</label>
			      </c:forEach>
		     
		      <button type="submit" class="button" name="action" value="stop">Choose</button>
		    </div>
		  </form>
	</div>
<!-- END POSITION DIALOG -->

<!-- START LIFT LIST DIALOG -->
	<div id="liftDialog" class="modal" >
	<form class="modal-content animate" action="BookControllerServlet" method="POST">
		    <div class="container" >
		    
		    <div class="row">
		    	<div class="col-50">
			    <h3>Choose you lift!</h3>
			    </div>
			    <div class="col-50">
			    <span onclick="document.getElementById('liftDialog').style.display='none'" class="close" title="Close Modal">&times;</span>
			 	</div>
			 </div>
			 <c:choose>
			 	<c:when test="${not empty offerBean.getLiftResult() }">
			      <c:forEach items="${offerBean.getLiftResult()}" var="item">
			      	<label class="container">
	  					<input type="radio" name="index" value="${offerBean.getLiftResult().indexOf(item)}"><span class="checkmark"></span>
	  							From: ${item.getLift().getRoute().getPickupPosition().getAddress()}
			  					<br>
			  					To: ${item.getLift().getRoute().getDropoffPosition().getAddress()}
			  					<br>
			  					When: ${item.getLift().getStringDate()}
			  					<br>
			  					Depart at: ${item.getLift().getStringStartTime()}	
			  					<br>
			  					Arrive up: ${item.getLift().getStringStopTime()}	
	  					  		<br>
	  					  		Notes: ${item.getLift().getNote()}
	  					  		<br>
	  					  		Driver: ${item.getLift().getDriver().getFullName()}
	  					  		<br>
	  					  		Rating: ${item.getLift().getDriver().getRating()}		
					</label>
			      </c:forEach>
			      <input class="button" type="submit" name="action" value="book">
			    </c:when>
			    <c:otherwise>
			    	<h2>There are no available lifts!</h2>
			    </c:otherwise>
			 </c:choose>
		     	
		      
		    </div>
		  </form>
	</div>

<!-- END LIFT LIST DIALOG -->
</div>
</body>

<script>
	<% String status= offerBean.getStatus(); %>
	var s="<%=status%>";
	if(s  == 'liftResult'){
		document.getElementById("liftDialog").style.display = 'block';
		<% offerBean.setStatus(""); %>
	}else if(s == 'startPos'){
		document.getElementById("listDialog").style.display = 'block';
		<% offerBean.setStatus(""); %>
	}
</script>

<script>
function change(){
	var x = document.getElementById("hGoing");
	var cb = document.getElementById("switch").checked;
	if(cb == true){
		document.getElementById("lbArrive").innerHTML =  "Depart at:" ;
		document.getElementById("hGoing").innerHTML = "Return";
		document.getElementById("switch").value="return";
		<% offerBean.setBookStatus("arrive"); %>
	
	}else{
		document.getElementById("lbArrive").innerHTML = "Arrive at:"
		document.getElementById("hGoing").innerHTML  ="Going";
		document.getElementById("switch").value="going";
		<% offerBean.setBookStatus("going"); %>
	}
}
</script>

</html>