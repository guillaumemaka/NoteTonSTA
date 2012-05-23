$(function() {
	var form = $("#evaluate_form").validate(
			{
				validClass : "success",
				onclick : false,
				errorClass : "error",
				highlight : function(element, errorClass, validClass) {
					$(element).addClass(errorClass).removeClass(validClass);
					$(element.form).find("label[for=" + element.name + "]")
							.parent("div").removeClass(validClass).addClass(
									errorClass);
				},
				unhighlight : function(element, errorClass, validClass) {
					$(element).removeClass(errorClass).addClass(validClass);
					$(element.form).find("label[for=" + element.name + "]")
							.parent("div").removeClass(errorClass).addClass(
									validClass);
				},
				errorPlacement : function(error, element) {
					
					if(element.name = "idbooster"){
						error.addClass("help-inline error");
						error.appendTo(element.parent("div"));
					}
					
					error.addClass("help-inline error");					
					error.appendTo(element.parent("td").parent("tr"));					
				},
				errorElement : "span",
				messages : {
					idbooster : {
						required : "Please enter your id booster",
 						digits : "Please enter a valid ID Booster (Only digits)"
					},
					q1 : "You missed this question",
					q2 : "You missed this question",
					q3 : "You missed this question",
					q4 : "You missed this question",
					q5 : "You missed this question",
					q6 : "You missed this question",										
				},
				rules : {
					idbooster : {
						required:true,
						digits:true
					},
					q1 : "required",
					q2 : "required",
					q3 : "required",
					q4 : "required",
					q5 : "required",
					q6 : "required",										
				}
			});

	$("#modal").modal({
		backdrop : "static",
		keyboard : "true"
	});

	$("#modal").bind("hidden", function() {
		form.resetForm();
		return;
	});

	$("#evaluate").click(function() {
		$("#modal").modal("show");
		return false;
	});
});