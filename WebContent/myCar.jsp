<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="torvercar.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src='https://kit.fontawesome.com/a076d05399.js'></script>

<meta charset="ISO-8859-1">
<title>TorVerCar</title>
</head>
<header>
	<div id="navbar">
		<a id="title" class="title">TorVerCar.</a> <a href="homepage.jsp"><i
			class='fas fa-home' style='font-size: 36px'></i></a> <a href="book.jsp">Book</a>
		<a href="offer.jsp">Offer</a> <a class="active" href="myCar.jsp">MyCar</a>
		<a href="myLift.jsp">MyLift</a>
		<form action="LoginControllerServlet" method="POST">
			<a class="right" href="index.jsp" name="action" value="logout"><i
				class='fas fa-door-open' style='font-size: 36px'></i></a>
		</form>
		<a class="right" href="profile.jsp"> <i
			class='fas fa-user-graduate' style='font-size: 36px'></i></a>

	</div>

</header>
<body>

	<div class="bg-image mycar">
		<div class="row fullscreen">
			<div class="column2" style="background-color: transparent;">
				<div class="card animate">
					<form action="CarControllerServlet" method="POST"
						style="width: 60%;">
						<h2>Car Card</h2>
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

						<div class="col-50">
							<button id="btnSave" type="submit" name="action" value="save"
								class="disabled">Save</button>

						</div>
					</form>

					<div class="col-50">
						<button id="btnBack" class="disabled" onclick="back()">
							Back <i class='fas fa-angle-double-left'></i>
						</button>
						<button id="btnEdit" onclick="edit()">Edit</button>
					</div>
				</div>
			</div>
		</div>
	</div>

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

			document.getElementById("mySeats").value = '${user.getCarInfo().getSeats() }'; //TODO get from database
			document.getElementById("myPlate").value = '${user.getCarInfo().getPlate() }';
			document.getElementById("myColor").value = '${user.getCarInfo().getColour() }';
			document.getElementById("myModel").value = '${user.getCarInfo().getModel() }';

			document.getElementById("mySeats").disabled = true;
			document.getElementById("myPlate").disabled = true;
			document.getElementById("myColor").disabled = true;
			document.getElementById("myModel").disabled = true;
		}
	</script>




</body>
</html>