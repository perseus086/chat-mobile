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
				alert("Session invalid please login again");
				localStorage.removeItem('email');
				localStorage.removeItem('token');
				window.location.replace("login.html");
			}		

			else if(msg.info == "valid"){
				$( ".menu" ).show();
			}
		});
	}

	//Hides popup
	$( "#dialog-confirm" ).dialog({
		autoOpen: false
	});


	$( document ).ajaxStart(function() {
		$( "#loading" ).show();
		$('#reset').attr('disabled','disabled');
		$('#delete').attr('disabled','disabled');
		$('#logout').attr('disabled','disabled');
	});

	$( document ).ajaxStop(function() {
		$( "#loading" ).hide();
		if(flag == 0){
			$('#reset').removeAttr('disabled');
			$('#delete').removeAttr('disabled');
			$('#logout').removeAttr('disabled');
		}
		
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
				newSuccess(msg.description);
				
				$('#reset').attr('disabled','disabled');
				$('#delete').attr('disabled','disabled');
				$('#logout').attr('disabled','disabled');
				
				setTimeout(function() {
					window.location.replace("login.html");
				}, 5000);
			}		

			else if(msg.typeOfMessage == "error"){
				flag = 0;
				newError(msg.description);

			}
		});

	}



	/**
	 * 			DELETE
	 */	
	function deletePopup(){

		$( "#dialog-confirm" ).dialog({
			autoOpen: true,
			resizable: false,
			height:200,
			width: 500,
			modal: true,
			buttons: {
				"Delete account": function() {
					deleteAccount();
					$( this ).dialog( "close" );
				},
				Cancel: function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}



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
				newSuccess(msg.description);
				$('#reset').attr('disabled','disabled');
				$('#delete').attr('disabled','disabled');
				$('#logout').attr('disabled','disabled');
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
				$('#reset').attr('disabled','disabled');
				$('#delete').attr('disabled','disabled');
				$('#logout').attr('disabled','disabled');
				newSuccess(msg.description);
				setTimeout(function() {
					window.location.replace("login.html");
				}, 2000);
				//window.location.replace("login.html");

			}		

			else if(msg.typeOfMessage == "error"){
				//show errors
				flag = 1;
				alert(msg.description);
				localStorage.removeItem('email');
				localStorage.removeItem('token');
				$('#reset').attr('disabled','disabled');
				$('#delete').attr('disabled','disabled');
				$('#logout').attr('disabled','disabled');
				window.location.replace("login.html");

			}
		});

	}

	function newError(txt){
		removeMessages();
		$('.container-error').append("<div id=\"error\">"+txt+"</div>");
	}

	function newSuccess(txt){
		removeMessages();
		$('.container-error').append("<div id=\"success\">"+txt+"</div>");
	}

	function removeMessages(){
		$('#error').remove();
		$('#success').remove();
	}

});