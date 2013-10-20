$(document).ready(function(){

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
			if(msg.info == "success")
			{
				newSuccess("SUCCESS<BR>GOOD");
			}		
			
			else if(msg.info == "error"){
				newSuccess("ERROR:<br>"+msg.content);
				
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
			if(msg.info == "valid")
			{
				newSuccess("SUCCESS<BR>GOOD");
			}		
			
			else if(msg.info == "invalidSession"){
				newError("ERROR:<br>"+msg.content);
				
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
			if(msg.info == "success")
			{
				alert("GOOD");
			}		
			
			else if(msg.info == "error"){
				//Present errors
				alert("Errors");
				
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