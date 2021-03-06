<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:useBean id="currentUser" class="logic.bean.UserBean" scope="session"></jsp:useBean>
<jsp:useBean id="offerBean" class="logic.bean.OfferBean" scope="session"></jsp:useBean>
<jsp:useBean id="message" class="logic.bean.MessageBean" scope="request"></jsp:useBean>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="description" content="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- The above 4 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	
	<!-- Core Stylesheet -->
	<link href="torvercar.css" rel="stylesheet">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
	<script src='https://kit.fontawesome.com/a076d05399.js'></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>	

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
		<a href="book.jsp">Book</a> 
		<a class="active" href="offer.jsp">Offer</a>
		<a href="myCar.jsp">MyCar</a>
		<a href="myLift.jsp">MyLift</a> 
		<form action="LoginControllerServlet" method="POST">
		<button type="submit" name="action" value="logout" class="right"><i class='fas fa-door-open' style='font-size:36px'></i></button>
		</form>
		<a class="right" href="profile.jsp"><i class='fas fa-user-graduate' style='font-size:36px'></i></a> 
	</div>
</header>


<body>
<div  class="bg-image offer">

	<c:if test="${not empty message.getMessage()}">
		<script>
			swal({
			  title: '${message.getTitle()}',
			  text: '${message.getMessage()}',
			  type: '${message.getType()}',
			  confirmButtonText: 'Close'
			});
		</script>
	</c:if>
	
	<c:if test = "${role eq 'student'}">
		<script>
			swal({
				title : 'Hey!',
				text : 'You need a car! Do you want to add it now?',
				type : 'warning',
				showCancelButton: true,
				confirmButtonText : 'Yes!',
				cancelButtonText : 'No',
				allowOutsideClick: false
			}).then(function(result){
		        if(result.value){
		        	window.location = "myCar.jsp";
		        }else if(result.dismiss == 'cancel'){
		        	window.location = "homepage.jsp";
		        }

		    });
		</script>
	</c:if>
<!-- START OFFER FORM -->
	<div class="row fullscreen">
		<div class="col-75">
			<form class="card" action="OfferControllerServlet" method="POST">
				<h2>Offer Lift</h2>

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
										<input type="text" style="font:corsive;" placeholder="via prima strada" id="start" name="start">
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
								<button class="button invisible" type="submit" name='action' value='desPos'><i class='fas fa-search-location fa-2x'></i></button>							
							</div>
						</div>
						
						<div class="row">
							<div class="col-50">
								<label><i class="fa fa-calendar-alt"></i> Day: </label> 
								<input type="date" placeholder="..." id="day" name="day" style="width:550px; height:42px" >
							</div>
							<div class="col-50">
								<label><i class="fa fa-clock"></i> Depart at: </label> 
								<input type="time" placeholder="HH:MM" id="time" name="time" style="width:550px; height:42px" >

							</div>
						</div>
						<div class="row" style="height:20px;"></div>
						<label><i class="fa fa-stopwatch-20"></i> Max duration: </label> 
						<t style="font-size:14px">*required field</t>
						<input type="text" placeholder="100 Minutes" id="maxTime" name="maxTime"> 
						
						<label><i class="fa fa-comment-dots"></i> Add notes: </label> 
						<input type="text" placeholder=" ..." id="notes" name="notes">
						<div class="row" style="height:15px"></div>
						<button id="btnConfirm" class="button" type="submit" name="action" value="offer">Confirm</button>

					</div>
				</div>
			</form>
		</div>
	</div>
<!-- END OFFER FORM -->

<!-- START POSITION LIST DIALOG -->
	<div id="listDialog" class="modal">
		  <form class="modal-content animate" action="OfferControllerServlet" method="POST">
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
		     
		      <button  type="submit" class="button" name="action" value="stop">Choose</button>
		    </div>
		  </form>
	</div>
<!-- END POSITION DIALOG -->

		

</div>
</body>

<script>
	 <% String status= offerBean.getStatus(); %>
	var s="<%=status%>";
	if(s  == 'startPos'){
		document.getElementById("listDialog").style.display = 'block';
		<% offerBean.setStatus(""); %>
		
	}	
	
	function show(){
		document.getElementById("listDialog").style.display = 'block';
	}
	
	
	

</script>




</html>