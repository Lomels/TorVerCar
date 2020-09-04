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
			<a  href="homepage.jsp"><i class='fas fa-home' style='font-size:36px'></i></a> 
			<a href="book.jsp">Book</a> 
			<a href="offer.jsp">Offer</a>
			<a href="myCar.jsp">MyCar</a> 
			<a href="myLift.jsp">MyLift</a> 
			<a class="right" href="index.jsp"><i class='fas fa-door-open' style='font-size:36px'></i></a> 
			<a class="right active" href=""> <i class='fas fa-user-graduate' style='font-size:36px'></i></a>
			
</div>

</header>
<body>
<div class="bg-image profile">
	<div class="row fullscreen">

	<div class="column" style="background-color:transparent;"></div>
		<div class="column" style="background-color:transparent;"></div>
			<div class="column2" style="background-color:transparent;">

				<div class="card animate" style="width:60%;">

  						  <h2>Profile Card</h2>
						  <label>Name and Surname:  </label>
						  <input type="text" value="Giulia Desideri" id="myName" style="color:#39393a; background-color:#FFFFFF; width: 300px; height:30px; "disabled>
						  
						  <label>Matriculation number:  </label>
						  <input type="text" value="0245061" id="myID" style="color:#39393a;background-color:#FFFFFF; width: 300px; height:30px;" disabled>
						  
						  
						  <label>Phone number:  </label>
						  <input type="text" value="3923165944" id="myPhoneNum" style="color:#39393a;background-color:#FFFFFF; width: 300px; height:30px;" disabled>
						  
						  <label>Email:  </label>
						  <input type="text" value="giuls.desideri@gmail.com" id="myEmail" style="color:#39393a; background-color:#FFFFFF; width: 300px; height:30px;" disabled>
						 
 						  <label>Password: <label  class="switch" style="margin-left:20px">
						  <input type="checkbox" onclick="showPass()">
						  <div class="slider round"></div></label> </label>
 
  
 						  <input type="password" value="aaaAAA123@" id="myPass" style="color:#39393a; background-color:#FFFFFF; width: 300px; height:30px;" disabled>


 <br>
 						<div class="col-50">	
								<div class="row">
									<button id="btnBack" class="disabled" onclick="back()">Back <i class='fas fa-angle-double-left'></i></button>
									<button id="btnSave" class="disabled" onclick="save()">Save</button>
									<button id="btnEdit" onclick="edit()">Edit</button>
								</div>
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

  document.getElementById("myPhoneNum").value= '';
  document.getElementById("myEmail").value= '';
  document.getElementById("myPass").value = '';
 
  document.getElementById("myPhoneNum").disabled= false;
  document.getElementById("myEmail").disabled = false;
  document.getElementById("myPass").disabled = false;
 
  
 
  
  
}
function back(){
  document.getElementById("btnEdit").classList.remove("disabled");
	  
  document.getElementById("btnSave").classList.add("disabled");
	  
  document.getElementById("btnBack").classList.add("disabled");
  
  document.getElementById("myPhoneNum").value= '3923165499';  //TODO get from database
  document.getElementById("myEmail").value= 'giuls.desideri@gmail.com';
  document.getElementById("myPass").value = 'aaaAAA123@';
	  

  document.getElementById("myPhoneNum").disabled= true;
  document.getElementById("myEmail").disabled = true;
  document.getElementById("myPass").disabled = true;
	  
	  
	
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




  
  



</body>
</html>