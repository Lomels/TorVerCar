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
			<a href="myCar.jsp">MyCar</a> 
			<a href="myLift.jsp">MyLift</a> 
			<form action="LoginControllerServlet" method="POST">
			<a class="right" href="index.jsp" name="action" value="logout"><i class='fas fa-door-open' style='font-size:36px'></i></a>
			</form> 
			<a class="right active" href=""> <i class='fas fa-user-graduate' style='font-size:36px'></i></a>
			
</div>

</header>
<body>
<div class="bg-image profile">
	<div class="row fullscreen">

	<div class="column" style="background-color:transparent;"></div>
		<div class="column" style="background-color:transparent;"></div>
			<div class="column2" style="background-color:transparent;">
				<div  class="card animate" > 
				<form action="ProfileControllerServlet" method="POST" style="width:60%;">

  						  <h2>Profile Card</h2>
						  <label>Name:  </label>
						  <input type="text" name="name" value="${user.getName() }" id="myName" style="width: 300px; height:30px;" disabled>
						  
						  <label>Surname:  </label>
						  <input type="text" name="surname" value="${user.getSurname() }" id="mySurname" style="width: 300px; height:30px;" disabled>
						  
						  <label>Matriculation number:  </label>
						  <input type="text" name="userID" value="${user.getUserID() }" id="myID" style="width: 300px; height:30px;" disabled>
						  
						  
						  <label>Phone number:  </label>
						  <input type="text" name="phone" value="${user.getPhone() }" id="myPhoneNum" style="width: 300px; height:30px;" disabled>
						  
						  <label>Email:  </label>
						  <input type="text" name="email" name="surname" value="${user.getEmail() }" id="myEmail" style="width: 300px; height:30px;" disabled>

						 
 						  <label>Password: <label  class="switch" style="margin-left:20px">
						  <input type="checkbox" onclick="showPass()">
						  <div class="slider round"></div></label> </label>
 
 						  <input type="password" name="password" value="${user.getPassword() }" id="myPass" style="width: 300px; height:30px;" disabled>
						
							<div class="col-50">
									<button id="btnSave" type="submit" name="action" value="save" class="disabled">Save</button>
	
 							</div>
						</form>
					
 						<div class="col-50">
							<button id="btnBack" class="disabled" onclick="back()">Back <i class='fas fa-angle-double-left'></i></button>
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
 
  document.getElementById("myPhoneNum").disabled= false;
  document.getElementById("myEmail").disabled = false;
  document.getElementById("myPass").disabled = false;
}

function back(){
  document.getElementById("btnEdit").classList.remove("disabled");	  
  document.getElementById("btnSave").classList.add("disabled");	  
  document.getElementById("btnBack").classList.add("disabled");
  
  document.getElementById("myPhoneNum").value= '${user.getPhone() }';  //TODO get from database
  document.getElementById("myEmail").value= '${user.getEmail() }';
  document.getElementById("myPass").value = '${user.getPassword() }';
	  
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