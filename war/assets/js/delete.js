$(function() {
	var current;
	
	$("#dialog-confirm").dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		modal : true,
		buttons : {
			"Delete intervention" : function() {
				$(this).dialog("close");
				window.location.href = current.href;
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		}
	});

	$("#delete .delete").click(function() {
		current = $(this);
		$("#dialog-confirm").dialog("open");
		return false;
	});
});