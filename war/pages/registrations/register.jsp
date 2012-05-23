<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>	
<head>
<title>Note Ton STA | Register</title>
<script type="text/javascript">
	$(function(){
		
		$("#registration").validate({
			validClass:"success",
			errorClass:"error",
			highlight: function(element, errorClass, validClass) {
			     $(element).addClass(errorClass).removeClass(validClass);
			     $(element.form).find("label[for=" + element.name + "]")
			     				.parent("div")
			     				.removeClass(validClass)
			                    .addClass(errorClass);
			  },
			  unhighlight: function(element, errorClass, validClass) {
			     $(element).removeClass(errorClass).addClass(validClass);
			     $(element.form).find("label[for=" + element.name + "]")
			     				.parent("div")
			                    .removeClass(errorClass)
			                    .addClass(validClass);
			  },
			errorPlacement: function(error, element) {
				error.addClass("help-inline");
				error.appendTo(element.parent("div"));
			},
			errorElement:"span",
			messages: { 
	            firstname: "Enter your firstname", 
	            lastname: "Enter your lastname", 	             
	            password: { 
	                required: "Provide a password" 	                 
	            }, 
	            password_confirm: { 
	                required: "Repeat your password", 	              
	                equalTo: "Enter the same password as above" 
	            }, 
	            email: { 
	                required: "Please enter a valid email address", 	                 	           
	            } 
	        },
			rules : { 
		            firstname: "required", 
		            lastname: "required", 		             
		            password: { 
		                required: true,  
		            }, 
		            password_confirm: { 
		                required: true,		                
		                equalTo: "#password" 
		            }, 
		            email: { 
		                required: true, 
		                email: true, 		                 
		            } 
		        }
		});
		
	});
</script>
</head>
<body>
	<h1>Register</h1>
	<form id="registration" class="clearfix" action="${ pageContext.request.contextPath }/register" method="post">
		<div class="clearfix">
			<label for="firstname">First Name:</label>
			<div class="input">
				<input type="text" name="firstname" value="" />
			</div> 
		</div>
		<div class="clearfix">
			<label for="lastname">Last Name:</label>
			<div class="input">
				<input type="text" name="lastname" value="" />
			</div> 
		</div>
		<div class="clearfix">
			<label for="email">Email:</label>
			<div class="input">
				<input type="text" name="email" value="" />
			</div> 
		</div>
		<div class="clearfix">
			<label for="password">Password:</label>
			<div class="input">
				<input id="password" type="password" name="password" value="" />
			</div> 
		</div>
		<div class="clearfix">
			<label for="password_confirm">Confirm password:</label>
			<div class="input">
				<input type="password" name="password_confirm" value="" />
			</div> 
		</div>
		<div class="actions">
            <input type="submit" class="btn primary" value="Register">&nbsp;<button type="reset" class="btn">Cancel</button>
        </div>
	</form>
</body>
</html>