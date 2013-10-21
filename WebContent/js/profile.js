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

			}
		});
	}

	//Hides popup
	$( "#dialog-confirm" ).dialog({
		autoOpen: false
	});


	 $( document ).ajaxStart(function() {
		 $.mobile.loading('show');
	 });
	 	
	 $( document ).ajaxStop(function() {
		 $.mobile.loading('hide');
	 });


	$( "#reset" ).click(function(){
		//alert("RESET");
		resetPassword();
	});


	$( "#delete" ).click(function(){

		//alert("DELETE");
		deletePopup();


	});

	$( "#logout" ).click(function(){

		//alert("LOGOUT");
		logout();

	});



	/**
	 * 			RESET
	 */	

	function resetPassword(){

		var email = localStorage.getItem('email');
		var token = localStorage.getItem('token');

		$.ajax({
			type: "POST",
			url: "Reset",
			data: { email: email, token: token},
			dataType: "JSON"
		})
		.done(function( msg ) {
			if(msg.typeOfMessage == "success")
			{
				flag=1;
				localStorage.removeItem('email');
				localStorage.removeItem('token');
				newInfo(msg.description);
				
				$('#reset').attr('disabled','disabled');
				$('#delete').attr('disabled','disabled');
				$('#logout').attr('disabled','disabled');
				
				setTimeout(function() {
					window.location.replace("login.html");
				}, 5000);
			}		

			else if(msg.typeOfMessage == "error"){
				flag = 0;
				newInfo(msg.description);

			}
		});

	}



	/**
	 * 			DELETE
	 */	
	function deletePopup(){

		$("#popup-delete").popup( "open", "tolerance", "0,0" );
	}


	$("#delete-account-button").click(function(){
		deleteAccount();
	});

	///////DELETE////

	function deleteAccount(){

		var email = localStorage.getItem('email');
		var token = localStorage.getItem('token');

		$.ajax({
			type: "POST",
			url: "Delete",
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
			}		

			else if(msg.typeOfMessage == "error"){
				flag = 0;
				newError(msg.description);

			}
		});

	}

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