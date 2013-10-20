$(document).ready(function(){
	
	$( "#signup" ).click(function(){
		$("#popupInfo").append("<p>HELLLO</p>");
		$("#popupInfo").popup("open");
		
	});
	
	$("#popupInfo").on( "popupafterclose", function( event, ui ) {
		$("#popupInfo").empty();
		
	} );
}
);
