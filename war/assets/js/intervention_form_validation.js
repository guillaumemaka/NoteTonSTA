$(function() {
	var dates = $("#from, #to")
			.datepicker(
					{
						minDate : "+1",
						defaultDate : "+1w",
						changeMonth : true,
						numberOfMonths : 3,
						onSelect : function(selectedDate) {
							var option = this.id == "from" ? "minDate"
									: "maxDate", instance = $(this).data(
									"datepicker"), date = $.datepicker
									.parseDate(
											instance.settings.dateFormat
													|| $.datepicker._defaults.dateFormat,
											selectedDate, instance.settings);
							dates.not(this).datepicker("option", option, date);
						}
					});

	/******
	
		Form validation
		
	 */

	$("#new_intervention").validate(
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
					error.addClass("help-inline");
					error.appendTo(element.parent("div"));
				},
				errorElement : "span",
				messages : {
					subject : "Please enter the subject",
					campus_id : "Please select a campus",
					from : "Choose a begin date",
					to : "Choose a end date",
					description : "Please enter a short description"
				},
				rules : {
					subject : "required",
					campus_id : "required",
					from : "required",
					to : "required",
					description : "required"
				}
			});

});