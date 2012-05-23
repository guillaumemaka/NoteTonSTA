<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Note Ton STA | Login</title>
<script type="text/javascript">
	$(function() {

		$("#login")
				.validate(
						{
							validClass : "success",
							errorClass : "error",
							highlight : function(element, errorClass,
									validClass) {
								$(element).addClass(errorClass).removeClass(
										validClass);
								$(element.form).find(
										"label[for=" + element.name + "]")
										.parent("div")
										.addClass(errorClass)
										.removeClass(validClass);
							},
							unhighlight : function(element, errorClass,
									validClass) {
								$(element).removeClass(errorClass).addClass(
										validClass);
								$(element.form).find(
										"label[for=" + element.name + "]")
										.parent("div").removeClass(errorClass)
										.addClass(validClass);
							},
							errorPlacement : function(error, element) {
								error.addClass("help-inline");
								error.appendTo(element.parent("div"));
							},
							errorClass : "error",
							errorElement : "span",
							messages : {
								email : {
									required : "Please enter a valid email address",
								},
								password : {
									required : "Provide a password"
								}
							},
							rules : {
								email : {
									required : true,
									email : true,
								},
								password : {
									required : true,
								}
							}
						});
	});
</script>
</head>
<body>
	<h1>Login</h1>
	<div class="clearfix form-stacked hero-unit span8">
		<form id="login" class="clearfix span8" method="post"
			action="${ pageContext.request.contextPath }/login">
			<div class="clearfix">
				<label for="email">Email:</label>
				<div class="input">
					<input type="text" name="email" value="" />
				</div>
			</div>
			<div class="clearfix">
				<label for="password">Password:</label>
				<div class="input">
					<input type="password" name="password" value="" />
				</div>
			</div>
			<div class="actions">
				<input type="submit" class="btn primary" value="Login">&nbsp;
				<button type="reset" class="btn">Cancel</button>
			</div>
		</form>
	</div>
</body>
</html>