$(document).ready(function(){
	checkSession();
	 $( document ).ajaxStart(function() {
		 $.mobile.loading('show');
	 });
	 	
	 $( document ).ajaxStop(function() {
		 $.mobile.loading('hide');
	 });

	//First focus on email
	$("#login-name").focus();
	
	$("#login").click(function() {

		console.log($("#login-name").val());

		if(checkEmail($("#login-name").val()) && checkPassword($("#login-pass").val()))
		{
			sendData();
		}
	});

	function sendData(){
		console.log("Sending user information");

		$.ajax({
			type: "POST",
			url: "Login",
			data: { email: $("#login-name").val(),
				password: $("#login-pass").val()
			},
			dataType: "JSON"
		})
		.done(function( msg ) {
			if(msg.typeOfMessage=="error"){
				
				newInfo(msg.description);
				$("#login-name").focus();
				if(msg.description=="Wrong password"){
					$("#login-pass").focus();
				}
					
			}else if (msg.typeOfMessage=="success"){
				localStorage.setItem('token', msg.token);
				localStorage.setItem('email', msg.email);
				//window.location.replace("loginSuccess.html");
				window.location.replace("groups.html");
			}
		});
	}
	
	function checkSession()
	{
		 //$( "#loading" ).show();
		var email= localStorage.getItem('email');
		var token = localStorage.getItem('token');
		
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
				localStorage.removeItem('email');
				localStorage.removeItem('token');
			}				
			else if(msg.info == "valid"){
				
				window.location.replace("groups.html");
			}
		});
	}
	
	function checkEmail(email){
		var emailPattern = /^[\d\w\.\-]{2,}@(unimelb.edu.au|student.unimelb.edu.au|gmail.com)$/;
		if(emailPattern.test(email))
		{
			$('.show-error').empty();
			return true;
		}
		else{
			newInfo("<b>Email format is invalid<br><br>Valid formats are:</b><br>user@unimelb.edu.au <br>or <br>user@student.unimelb.edu.au");
			$("#login-name").focus();
		}
		return false;
	}
	
	
	function checkPassword(pass){
		if(pass==""){
			newInfo("Password field is empty");
			$("#login-pass").focus();
			return false;
		}
		return true;
	}
	
	function newInfo(txt){
		$('#popupInfo').append("<p>"+txt+"</p>");
		$("#popupInfo").popup( "open", "tolerance", "0,0" );
	}
	
	$("#popupInfo").on( "popupafterclose", function( event, ui ) {
		$("#popupInfo").empty();
		
	} );

	
	
});
