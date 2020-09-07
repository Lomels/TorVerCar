<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="offerBean" class="logic.bean.OfferBean" scope="session"></jsp:useBean>
<jsp:useBean id="message" class="logic.bean.MessageBean" scope="request"></jsp:useBean>
    

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="torvercar.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
			<a class="active">MyCar</a>
			<a href="myLift.jsp">MyLift</a> 
			<form action="LoginControllerServlet" method="POST">
			<button type="submit" name="action" value="logout" class="right"><i class='fas fa-door-open' style='font-size:36px'></i></button>
			</form>
			<a class="right" href="profile.jsp"><i class='fas fa-user-graduate' style='font-size:36px'></i></a> 
		</div>
</header>
<body>
	<div class="bg-image mycar">
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
			<div class="column2" style="background-color: transparent;">
				<div class="card animate">
					<form action="CarControllerServlet" method="POST"
						style="width: 60%;">

						<h2><i class='fas fa-address-card'></i>&nbsp;Car Card</h2>
						<c:choose>
						<c:when test = "${role eq 'driver'}">
							<label>Model: </label> 

						

						<input type="text" name="model"
							value="${user.getCarInfo().getModel() }" id="myModel"
							style="width: 300px; height: 30px;" disabled> 
						<label>Color: </label> 
						<input type="text" name="color" class="label"
							value="${user.getCarInfo().getColour() }" id="myColor"
							style="width: 300px; height: 30px;"
							disabled> 
						<label>Plate: </label> 
						<input type="text" name="plate"
							value="${user.getCarInfo().getPlate() }" id="myPlate"
							style="width: 300px; height: 30px;"
							disabled> 
						<label>Seats available: </label> 
						<input type="text" name="seats"
							value="${user.getCarInfo().getSeats() }" id="mySeats"
							style=" width: 300px; height: 30px;"
							disabled> <br>
						</c:when>
						<c:otherwise>
							<label>Model: </label> 
							<input type="text" name="model" id="myModel"
								style="width: 300px; height: 30px;"> 
							<label>Color: </label> 
							<input type="text" name="color" class="label" id="myColor"
								style="width: 300px; height: 30px;"> 
							<label>Plate: </label> 
							<input type="text" name="plate" id="myPlate"
								style="width: 300px; height: 30px;"> 
							<label>Seats available: </label> 
							<input type="text" name="seats" id="mySeats"
								style=" width: 300px; height: 30px;"> <br>
						</c:otherwise>
					</c:choose>
						
						<div class="col-50">

							<div class="row" style="height:50px">
							<button id="btnSave" type="submit" name="action" value="save" style="visibility:hidden; width:150px;"
								class="disabled">Save</button>
							</div>


						</div>
					</form>

					<div class="col-50">

						<div class="row" style="height:50px">
						<button id="btnBack" class="disabled" style="visibility:hidden; width:150px;" onclick="back()">
							Back <i class='fas fa-angle-double-left'></i>
						</button>
						</div>
						<div class="row" style="height:50px">
						<button id="btnEdit" style="width:150px;" onclick="edit()">Edit</button>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>


</body>

<script>
	function edit() {
		document.getElementById("btnEdit").classList.add("disabled");
		document.getElementById("btnSave").classList.remove("disabled");
		document.getElementById("btnBack").classList.remove("disabled");
		
		document.getElementById("mySeats").disabled = false;
		document.getElementById("myPlate").disabled = false;
		document.getElementById("myColor").disabled = false;
		document.getElementById("myModel").disabled = false;

	}
	function back() {
		document.getElementById("btnEdit").classList.remove("disabled");
		document.getElementById("btnSave").classList.add("disabled");
		document.getElementById("btnBack").classList.add("disabled");

		document.getElementById("mySeats").disabled = true;
		document.getElementById("myPlate").disabled = true;
		document.getElementById("myColor").disabled = true;
		document.getElementById("myModel").disabled = true;
	} 
</script>

</html>