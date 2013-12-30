function fill_form(box, id) {
	
	var cl = ".title" + id; 
	$("#id").css("display", "none");
	  $("#id").val($(".id"+id).text());
	  $("#title").val($(".title" + id).text());
	  $("#authors").val($(".authors" + id).text());
	  $("#year").val($(".year" + id).text());
	  $("#publication").val($(".publication" + id).text());
	  $("#pages").val($(".pages" + id).text());
	  $("#description").val($(".description" + id).text());
	  $("#term").val($(".term" + id).text());
	  $("#bookcase").val($(".bookcase" + id).text());
	  $("#shelf").val($(".shelf" + id).text());
	  $("#genre").val($(".genre" + id).text());
	  $("#description").val($(".desc"+id).text());
	  $("#term").val($(".term"+id).text());
	  
	  $("#uname").val($(".uname" + id).text());
	  $("#surname").val($(".surname" + id).text());
	  $("#email").val($(".email" + id).text());
	  $("#cellphone").val($(".cellphone" + id).text());
	  $("#multibookAllowed").val($(".multibookAllowed" + id).text());
	  $("#untimelyReturns").val($(".untimelyReturns" + id).text());
	  $("#timelyReturns").val($(".timelyReturns" + id).text());
	  $("#failedOrders").val($(".failedOrders" + id).text());
	  $("#confirm").attr('checked', $("#confirm").attr('checked'));
	}

function reset_form() {
	$("#id").css("display", "none");
	  $("#id").val(0);
	  $("#title").val("");
	  $("#authors").val("");
	  $("#year").val(0);
	  $("#publication").val("");
	  $("#pages").val(0);
	  $("#description").val("");
	  $("#term").val(14);
	  $("#bookcase").val(0);
	  $("#shelf").val(0);
	  $("#genre").val(1);
	  $("#description").val("");
	  
	  $("#uname").val("");
	  $("#surname").val("");
	  $("#email").val("");
	  $("#cellphone").val("");
	  $("#multibookAllowed").val(0);
	  $("#untimelyReturns").val(0);
	  $("#timelyReturns").val(0);
	  $("#failedOrders").val(0);
	  $("#confirm").attr('checked', false);
	  $("#confirm").val(false);
}

$(document).ready(function() { 
	$("table").tablesorter();
	$(".alert .close").click(function() {
		$(".alert").hide();
	})
	/**
	 * New book/user click
	 */
	$("#newbookbutton, #newuserbutton").click(function() {
		reset_form();
		$('#action_popup').modal();
	})
	
	/**
	 * Edit book/user click
	 */
	$("a[id^=editbook], a[id^=edituser]").click(function() {
		var tr = "#" + $(this).parent().parent().attr("id");
		var id = $(tr + " td:first-child").text();
		//var name = $(tr + " td:nth-child(2)").text() + " " + $("#" + tr + " td:nth-child(3)").text(); 
		fill_form("#action_popup", id)
		$('#action_popup').modal();
	})
	
	/**
	 * Delete book/user click
	 */
	$("a[id^=deletebiu], a[id^=deleteorder], a[id^=deletebook], a[id^=deleteuser]").click(function() {
		var tr = "#" + $(this).parent().parent().attr("id");
		var id = $(tr + " td:first-child").text();
		$("#name").text(id);
		console.log(id);
		$('#popup').modal();
	})
	
	/**
	 * Return/issue click
	 */  
	$("a[id^=returnbook], a[id^=issueorder]").click(function() {
		var tr = "#" + $(this).parent().parent().attr("id");
		var id = $(tr + " td:first-child").text();
		var pid= $(".pid" + id).text();
		$("#aname").text(id);
		console.log(id);
		$('#action_popup').modal();
	})

 
  
  /**
   * Delete book/user action
   */
  $("#deleteLink").click(function(event) {
	  var id = $("#name").text();
	  var href = $(event.target).attr("href")+id;
	  $.ajax({
		  url: href,
          type: "DELETE",
          success: function(data) {
        	  if (data == 0) {
        		  console.log("error delete");
        		  $(".alert").show();
        	  }
        	  else {
        		  console.log("success");
        		  $(".table" + id).remove();
        	  }
        	  $('#popup').modal("hide");
          },
          error: function(data) {
        	  console.log("error");
        	}
	  });
	  event.preventDefault();
  });
	  
  
  /**
   * Return book click
   */
	
  $("#actionLink").click(function(event) {
	  var id = $("#aname").text();
	  var href = $(event.target).attr("href")+id;
      $.ajax({
    	  url: href,
    	  type: "GET",
    	  success: function(data) {
    		  console.log("success");
    		  $(".table" + id).remove();
    		  $('#action_popup').modal("hide");
    	  },
    	  error: function(data) {
    		  console.log("error");
		  }
      });
      event.preventDefault();
  });
  
  
  /**
   * Edit book action
   */
  $("#editbook").submit(function(event) {
	  var id = $("#id").val();
	  var title = $("#title").val();
	  var authors = $("#authors").val();
	  var year = $("#year").val();
	  var publication = $("#publication").val();
	  var pages = $("#pages").val();
	  var description = $("#description").val();
	  var term = $("#term").val();
	  var bookcase = $("#bookcase").val();
	  var shelf = $("#shelf").val();
	  var genre = $("#genre").val();
	  var json = { "id" : id, "title" : title, "authors": authors, "year" : year, "publication": publication, 
			  "pages" : pages, "description": description, "term" : term, "bookcase": bookcase,
			  "shelf" : shelf, "genre": genre};
	  $.ajax({
		  url: $("#editbook").attr( "action"),
		  data: JSON.stringify(json),
		  type: "POST",
		  beforeSend: function(xhr) {  
	            xhr.setRequestHeader("Accept", "application/json");  
	            xhr.setRequestHeader("Content-Type", "application/json");  
	      },
	      success: function(data) {
			console.log("success");
			$('#action_popup').modal("hide");
		  },
		  error: function() {
			console.log("error");
			
		}
		  
	  });
	  event.preventDefault();	  
});
  
  /**
   * Edit user action
   */
  $("#edituser").submit(function(event) {
	  console.log("begin");
	  var id = $("#id").val();
	  var uname = $("#uname").val();
	  var surname = $("#surname").val();
	  var email = $("#email").val();
	  var cellphone = $("#cellphone").val();
	  var multibookAllowed = $("#multibookAllowed").val();
	  var untimelyReturns = $("#untimelyReturns").val();
	  var timelyReturns = $("#timelyReturns").val();
	  var failedOrders = $("#failedOrders").val();
	  var role = "USER";
	  var sms = true;
	  var password = "";
	  var salt = "";
	  var confirm = $("#confirm").val();
	  var json = { "id" : id, "name" : uname, "surname": surname, 
			  "email" : email, "cellphone": cellphone, "role": role, 
			  "confirm": confirm, "sms": sms, "password" : password, 
			  "salt": salt, "timelyReturns": timelyReturns,
			  "untimelyReturns": untimelyReturns, "multibookAllowed" : multibookAllowed, 
			  "failedOrders" : failedOrders
			  };
	  $.ajax({
		  url: $("#edituser").attr( "action"),
		  data: JSON.stringify(json),
		  type: "POST",
		  beforeSend: function(xhr) {  
	            xhr.setRequestHeader("Accept", "application/json");  
	            xhr.setRequestHeader("Content-Type", "application/json");  
	      },
	      success: function(data) {
			console.log("success");
			$('#action_popup').modal("hide");
		  },
		  error: function() {
			console.log("error");
		}
		  
	  });
	  event.preventDefault();	  
});

  
  
});