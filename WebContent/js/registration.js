$(document).ready(function(){
		
	 $( document ).ajaxStart(function() {
		 $.mobile.loading('show');
	 });
	 	
	 $( document ).ajaxStop(function() {
		 $.mobile.loading('hide');
	 });

	//First focus on email
	$("#login-name-signup").focus();


	$("#signup").click(function() {
		
		//console.log($("#login-name").val());
		if(checkEmail($("#login-name-signup").val()))
		{
			sendData();
		}
	});

	function sendData(){
		$.ajax({
			type: "POST",
			url: "Register",
			data: { email: $("#login-name-signup").val()},
			dataType: "JSON"
		})
		.done(function( msg ) {
			if(msg.typeOfMessage=="error"){
				newInfo(msg.description);
				$("#login-name-signup").focus();
			}else if (msg.typeOfMessage=="success"){
				newInfo(msg.description+"<br>You will be redirected in 10 seconds to login page");
				$('#signup').attr('disabled','disabled');
				setTimeout(function() {
					window.location.replace("login.html");
				},10000);
			}
			
			console.log( "Data Saved: " + msg.typeOfMessage + " msg: "+msg.description);
		});
	}
	function checkEmail(email){
		var emailPattern = /^[\d\w\.\-]{2,}@(unimelb.edu.au|student.unimelb.edu.au|gmail.com)$/;
		if(emailPattern.test(email))
		{
			return true;
		}

		else{
			newInfo("<b>Email format is invalid<br><br>Valid formats are:</b><center><br>user@unimelb.edu.au <br>or <br>user@student.unimelb.edu.au</center>");
			$("#login-name").focus();
		}
		return false;		
	}

	function newInfo(txt){
		$('#popupInfo').append("<p>"+txt+"</p>");
		$("#popupInfo").popup( "open", "tolerance", "0,0" );
	}
	
	$("#popupInfo").on( "popupafterclose", function( event, ui ) {
		$("#popupInfo").empty();
		
	} );




});