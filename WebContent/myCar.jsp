<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
href="torvercar.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src='https://kit.fontawesome.com/a076d05399.js'></script>

<meta charset="ISO-8859-1">
<title>TorVerCar</title>
</head>
<header>
<div id="navbar">
			<a id="title" class="title">TorVerCar.</a>
			<a  href="homepage.jsp"><i class='fas fa-home' style='font-size:36px'></i></a> 
			<a href="book.jsp">Book</a> 
			<a href="offer.jsp">Offer</a>
			<a class="active" href="myCar.jsp">MyCar</a> 
			<a href="myLift.jsp">MyLift</a> 
			<a class="right" href="index.jsp"><i class='fas fa-door-open' style='font-size:36px'></i></a> 
			<a class="right" href="profile.jsp"> <i class='fas fa-user-graduate' style='font-size:36px'></i></a>
			
</div>

</header>
<body>

<div class="bg-image mycar">
	<div class="row fullscreen">
	<div class="column2" style="background-color: transparent;">
		<div class="card animate" style="width:60%;">
		  <h2>Car Card</h2>
		  <label><i class='fas fa-car'></i>Model:  </label>
		  <input type="text" value="i20" id="myModel" style="color:#39393a; background-color:#FFFFFF; width: 300px; height:30px;"disabled>
		  
		  <label>Color:  </label>
		  <input type="text" value="BLACK" id="myColor" style="color:#39393a;background-color:#FFFFFF; width: 300px; height:30px;" disabled>
		  
		  
		  <label>Plate:  </label>
		  <input type="text" value="PDPDPD" id="myPlate" style="color:#39393a;background-color:#FFFFFF; width: 300px; height:30px;" disabled>
		  
		  <label>Seats available:  </label>
		  <input type="text" value="3" id="mySeats" style="color:#39393a; background-color:#FFFFFF; width: 300px; height:30px;" disabled>
		 <br>

		<div class="col-50">	
			<div class="row">
				<button id="btnBack" class="disabled" onclick="back()">Back <i class='fas fa-angle-double-left'></i></button>
				<button id="btnSave" class="disabled" onclick="save()">Save</button>
				<button id="btnEdit" onclick="edit()">Edit</button>
			</div>
		</div>
		<br>

 
		</div>
	</div>
	</div>
</div>

<script>
function edit() {
  document.getElementById("btnEdit").classList.add("disabled");
  
  document.getElementById("btnSave").classList.remove("disabled");
  
  document.getElementById("btnBack").classList.remove("disabled");

  document.getElementById("mySeats").value= '';
  document.getElementById("myPlate").value= '';
  document.getElementById("myColor").value = '';
  document.getElementById("myModel").value = '';
  
  document.getElementById("mySeats").disabled= false;
  document.getElementById("myPlate").disabled = false;
  document.getElementById("myColor").disabled = false;
  document.getElementById("myModel").disabled = false;
  
}
function back(){
	  document.getElementById("btnEdit").classList.remove("disabled");
	  
	  document.getElementById("btnSave").classList.add("disabled");
	  
	  document.getElementById("btnBack").classList.add("disabled");
	  
	  document.getElementById("mySeats").value= '3';  //TODO get from database
	  document.getElementById("myPlate").value= 'PDPDPD';
	  document.getElementById("myColor").value = 'BLACK';
	  document.getElementById("myModel").value = 'i20';

	  document.getElementById("mySeats").disabled= true;
	  document.getElementById("myPlate").disabled = true;
	  document.getElementById("myColor").disabled = true;
	  document.getElementById("myModel").disabled = true;
}
</script>




</body>
</html>