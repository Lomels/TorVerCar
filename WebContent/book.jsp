<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="torvercar.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<meta charset="ISO-8859-1">
<title>TorVerCar</title>
</head>

<header>
	<div id="navbar">
		<a href=""><i class='fas fa-door-open' style='font-size:36px'></i></a> 
		<a class="active" href="">Book</a> 
		<a href="">Offer</a> 
		<a href="">MyCar</a> 
		<a href="">MyLift</a> 
		<a class="right" href=""><i class='fas fa-door-open' style='font-size:36px'></i></a>
		<a class="right" href=""><i class='fas fa-user-graduate' style='font-size:36px'></i></a>
	</div>
</header>

<body>

<div  class="bg-image book">
<!-- START OFFER FORM -->
	<div class="row fullscreen">
		<div class="col-75">
			<form class="card animate" action="OfferControllerServlet" method="POST">
				<h2>Book Lift</h2>

				<div class="row">
					<div class="col-50">
						<label><i class="fa fa-map-marked-alt"></i> Starting
							point:</label>
						<div class="row">
							<div class="col-50">
								<input type="text" placeholder="via prima strada" id="start" name="start">
							</div>
							<div class="col-25">
								<button class="invisible" type="submit" name='action' value='startPos'><i class='fas fa-search-location fa-2x'></i></button>
							</div>
						</div>

						<label><i class="fa fa-flag-checkered"></i> Destination: </label>
						<div class="row">
							<div class="col-50">
								<input type="text" placeholder="via cambridge" id="destination" name="dest">
							</div>
							<div class="col-50">
								<button class="invisible" type="submit" name='action' value='destPos'><i class='fas fa-search-location fa-2x'></i></button>							
							</div>
						</div>

						<div class="row">
							<div class="col-50">
								<label><i class="fa fa-calendar-alt"></i> Day: </label> 
								<input type="text" placeholder="..." id="day" name="day">
							</div>
							<div class="col-50">
								<label><i class="fa fa-clock"></i> Depart at: </label> 
								<input type="text" placeholder="HH:MM" id="departureTime" name="startTime">
							</div>
						</div>
						<label><i class="fa fa-stopwatch-20"></i> Max duration: </label> 
						<input type="text" placeholder="100 Minutes" id="maxTime" name="maxTime"> 
						
						

						<button id="btnConfirm" type="submit" name="action" value="offer" style="width:150px;">Book Lift</button>
						
					</div>
				</div>
			</form>
		</div>
	</div>
<!-- END OFFER FORM -->
</div>

</body>
</html>