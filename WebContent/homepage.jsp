<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<jsp:useBean id="message" class="logic.bean.MessageBean" scope="request"></jsp:useBean>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link href="torvercar.css" rel="stylesheet">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@7.12.15/dist/sweetalert2.all.min.js"></script>
	<script src='https://kit.fontawesome.com/a076d05399.js'></script>
	<title>Insert title here</title>
</head>

<header class="header_area clearfix">
		<div id="navbar">
			<a id="title" class="title">TorVerCar.</a>
			<a class="active" href=""><i class='fas fa-home' style='font-size:36px'></i></a> 
			<a href="book.jsp">Book</a> 
			<a href="offer.jsp">Offer</a>
			<a href="myCar.jsp">MyCar</a>
			<a href="myLift.jsp">MyLift</a> 
			<form action="LoginControllerServlet" method="POST">
			<button type="submit" name="action" value="logout" class="right"><i class='fas fa-door-open' style='font-size:36px'></i></button>
			</form>
			<a class="right" href="profile.jsp"><i class='fas fa-user-graduate' style='font-size:36px'></i></a> 
		</div>
</header>
	
<body>
	<div class="bg-image home">
	<!-- MESSAGE CHECK -->
	<%if (message.getMessage() != null) {%>
	<script>
	swal({
	  title: 'Error!',
	  text: '${message.getMessage()}',
	  type: '${message.getType()}',
	  confirmButtonText: 'Got it!'
	});</script>
	<%}%>
	<!-- END CHECK -->
	
	<!-- NOTIFICATIONS CHECK -->
	<c:if test="${not empty user.getNotifications() }">
		<c:forEach items="${user.getNotifications() }" var="note">
			<script>
				swal({
				  title: 'Notification!',
				  text: '${note}',
				  type: 'alert',
				  confirmButtonText: 'Got it!'
				});
			</script>
		</c:forEach>
	</c:if>
	<!-- END CHECK -->
	<div class="column" style="background-color:trasparent;">
	</div>
	<div class="column" style="background-color:trasparent;"></div>	
	<div class="column2" style="background-color:trasparent;">
	<div class="row" style="height:75%;"></div>
	<h1 style="font-size:96px;">Welcome ${user.getName()}</h1>
	</div>
</body>
</html>