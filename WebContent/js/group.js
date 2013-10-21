$(document).ready(function(){

	var flag = 0;
	
	//FUNCION PARA CHECAR QUE LA SESSION ESTE BUENA sino redirecciona a login
	checkSession();

	function checkSession()
	{
		var email = localStorage.getItem('email');
		var token = localStorage.getItem('token');

		if(email==null||token==null){
			window.location.replace("login.html");
		}


		console.log("Checking session");

		$.ajax({
			type: "POST",
			url: "CheckSession",
			data: { email: email, token: token},
			dataType: "JSON"
		})
		.done(function( msg ) {
			if(msg.info == "invalidSession")
			{
				newInfo("Session invalid please login again");
				localStorage.removeItem('email');
				localStorage.removeItem('token');
				window.location.replace("login.html");
			}		

			else if(msg.info == "valid"){
				newInfo("Welcome");
			}
		});
	}

	 $( document ).ajaxStart(function() {
		 $.mobile.loading('show');
	 });
	 	
	 $( document ).ajaxStop(function() {
		 $.mobile.loading('hide');
	 });


	$( "#logout" ).click(function(){

		//alert("LOGOUT");
		logout();

	});

	/////////////////////////////


	/**
	 * 			LOGOUT
	 */	

	function logout(){

		var email = localStorage.getItem('email');
		var token = localStorage.getItem('token');

		$.ajax({
			type: "POST",
			url: "Logout",
			data: { email: email, token: token},
			dataType: "JSON"
		})
		.done(function( msg ) {
			if(msg.typeOfMessage == "success")
			{
				flag = 1;
				localStorage.removeItem('email');
				localStorage.removeItem('token');
				newInfo(msg.description);
				setTimeout(function() {
					window.location.replace("login.html");
				}, 2000);
				//window.location.replace("login.html");

			}		

			else if(msg.typeOfMessage == "error"){
				//show errors
				flag = 1;
				newInfo(msg.description);
				localStorage.removeItem('email');
				localStorage.removeItem('token');
				window.location.replace("login.html");

			}
		});

	}

	function newInfo(txt){
		$("#popupInfo").empty();
		$('#popupInfo').append("<p>"+txt+"</p>");
		$("#popupInfo").popup( "open", "tolerance", "0,0" );
	}
	
	$("#popupInfo").on( "popupafterclose", function( event, ui ) {
		$("#popupInfo").empty();
		
	} );

});