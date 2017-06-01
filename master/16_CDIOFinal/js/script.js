
var users;
	
$(document).ready(function(){
	/* Init */
	$("#main").load("Brugere.html");

	$(document).on('submit', '#formOpretBruger', function(event){
		event.preventDefault();
		createUser();
	});
	
	$(document).on('submit', '#formRetBruger', function(event){
		event.preventDefault();
		updateUser();
	});

	$(document).on('click', '.updateBruger', function(event){
		console.log(users);
		me = $(this);
		var selectedUser;
		$.each(users, function(i, user) {
			if(user.oprCpr == me.data('bruger-id')){
				selectedUser = user;
			}
		});
		console.log(selectedUser);
		
		var modal = $("#modalRetBruger");
		
		modal.find('input#oprCpr').val(selectedUser.oprCpr);
		modal.find('input#oprNavn').val(selectedUser.oprNavn);
		modal.find('input#ini').val(selectedUser.ini);
		modal.find('input#password').val(selectedUser.password);
		modal.find('input#gentagPassword').val(selectedUser.password);
		modal.find('select#rolleId').val(selectedUser.rolleId);
		modal.modal('show');
	});

	$(document).on('click', '.deleteBruger', function(event){
		me = $(this);
		bootbox.confirm({
		    message: "Er du sikker på at du vil slette brugeren?",
		    buttons: {
		        confirm: {
		            label: 'Ja',
		            className: 'btn-success'
		        },
		        cancel: {
		            label: 'Nej',
		            className: 'btn-danger'
		        }
		    },
		    callback: function (result) {
		    	if(result){
		    		deleteUser(me);
		    	}
		    }
		});
	});
	
	$(document).on('click', '.navbar-nav a', function(event){
		event.preventDefault();
		var link = $(this).attr('href');
		$(this).parent().siblings().removeClass('active');
		$(this).parent().addClass('active');
		$("#main").load(link);
	});
})


function createUser(){
	var data = $("#formOpretBruger").serializeJSON();
//	console.log(data);
	
	$.ajax({
		url : "http://localhost:8080/16_CDIO3/rest2/users",
		data : data,
		contentType: "application/json",
		method: 'POST',
		success : function(data){
			if(data){
//				alert(data);
				$("#formOpretBruger")[0].reset();
				$("#modalOpretBruger").modal('hide');
				loadUsers();
			}
		},
		error: function(jqXHR, text, error){
			alert(jqXHR.status + text + error);
		}
	});
}


function deleteUser(me){
	var bruger_id = me.data('bruger-id');
	console.log(bruger_id);
	
	$.ajax({
		url : "http://localhost:8080/16_CDIO3/rest2/users",
		data : '{"oprCpr":"' + bruger_id + '"}',
		contentType: "application/json",
		method: 'DELETE',
		success : function(data){
			if(data){
//				alert(data);
				loadUsers();
			}
		},
		error: function(jqXHR, text, error){
			alert(jqXHR.status + text + error);
		}
	});
}


function updateUser(me){
	var data = serializeJSONIncludingDisabledFields($("#formRetBruger"));
	console.log(data);
	
	$.ajax({
		url : "http://localhost:8080/16_CDIO3/rest2/users",
		data : data,
		contentType: "application/json",
		method: 'PUT',
		success : function(data){
			if(data){
//				alert(data);
				$("#formRetBruger")[0].reset();
				$("#modalRetBruger").modal('hide');
				loadUsers();
			}
		},
		error: function(jqXHR, text, error){
			alert(jqXHR.status + text + error);
		}
	});
}

function loadUsers(){
	$.ajax({
		url : "http://localhost:8080/16_CDIO3/rest2/users",
		method: 'GET',
		success : function(data){
			users = data;
//			console.log(data);

			$(function() {
				$('#tableBrugere>tbody').html('');
			    $.each(data, function(i, item) {
			        $('<tr>').append(
			            $('<td>').text(item.oprCpr),
			            $('<td>').text(item.oprNavn),
			            $('<td>').text(item.ini),
			            $('<td>').text((item.rolleId == 1 ? 'Administrator' : (item.rolleId == 2 ? 'Værkfører' : (item.rolleId == 3 ? 'Pharmaceut' : (item.rolleId == 4 ? 'Operatør' : 'Der er ikke valgt en rolle'))))),
			            $('<td>').text(item.password),
			            $('<td>').html('<div class="btn btn-xs btn-info updateBruger" data-bruger-id="' + item.oprCpr + '"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></div>'),
			            $('<td>').html('<div class="btn btn-xs btn-danger deleteBruger" data-bruger-id="' + item.oprCpr + '"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></div>')
			        ).appendTo('#tableBrugere>tbody');
			    });
			});
		},
		error: function(jqXHR, text, error){
			alert(jqXHR.status + text + error);
		}
	});
}

function serializeJSONIncludingDisabledFields (form) {
	  var fields = form.find('[disabled]');
	  fields.prop('disabled', false);
	  var json = form.serializeJSON();
	  fields.prop('disabled', true);
	  return json;
	}