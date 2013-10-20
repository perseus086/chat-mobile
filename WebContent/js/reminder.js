$(document).ready(function(){
//$(window).on('pageinit', function() {

	
	$( document ).ajaxStart(function() {
		 $.mobile.loading('show');
	 });
	 	
	 $( document ).ajaxStop(function() {
		 $.mobile.loading('hide');
	 });

	//First focus on email
	$("#login-name-reminder").focus();


	$("#reminder").click(function() {
		
		if(checkEmail($("#login-name-reminder").val()))
		{
			sendData();
		}
	});

	function sendData(){
		console.log("Sending email to user");

		$.ajax({
			type: "POST",
			url: "Reminder",
			data: { email: $("#login-name-reminder").val()},
			dataType: "JSON"
		})
		.done(function( msg ) {
			if(msg.typeOfMessage=="error"){
				newInfo(msg.description);
				$("#login-name-reminder").focus();
			}else if (msg.typeOfMessage=="success"){
				newInfo(msg.description);
				$("#login-name-reminder").focus();
				setTimeout(function() {
					window.location.replace("login.html");
				}, 5000);
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
			$("#login-name-reminder").focus();
		}
		return false;		
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