  function signUp()
    {
    var myusername = $("#accountId").val();
    
    if(!myusername){
    	$("#errorMsg").html("Account Id cannot be empty");
  	  $("#errorMsg").show();
    }else{
    	$("#errorMsg").hide();
    
    
    $.ajax({
      type: "POST",
      url: "http://localhost:8080/account/"+myusername,
      data: myusername,
      cache: false,
      success: function(data){
    	  console.log("success");
    	  $("#succesMsg").html(data.description);
    	  $("#succesMsg").show();
      },
      error:function(data){
    	  console.log("error"+data);
    	  $("#errorMsg").html("Error occured in creating account");
    	  $("#errorMsg").show();
      }
    
    });
    }
    }