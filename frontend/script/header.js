$(document).ready(function(){
	$('#searchSubmit').submit(function(){
		window.location.href = urlPath + '?page=suchergebnis&searchString=' + $('#suche').val();
		return false;
	});

	var token = Cookies.get('token');
	if(typeof token != 'undefined'){
		$.ajax({
			url: urlPath + 'backend/user/get/' + token,
			type: 'GET',
			success: function(result){
				$('.dropdownMenuLink').attr("src", result.Picture);
				console.log(result.userIsAdmin);
				if(result.userIsAdmin)
				{
					$('#btnModalShow').css('display', 'block');
					$('.fas.fa-times').css('display', 'inline-block');
				}
			},
			error: function(response)
			{
				handleError(response);
			}
		});
		$.ajax({
			url: 'backend/news/get?token=' + token,
			type: 'GET',
			success: function(result){
				$.each(result, function(index, value){
					var r = `<div class="card m-1" style="width: 18rem;"><div class="card-body">
					<i class="fas fa-times" style="position: absolute; left: 260; top: 2; color: red; font-size: 25; display:none;"></i>
					<h5 class="card-title">
					${value.messageTitle}</h5>
					<p class="card-text">${value.messageText}</p>
					<input name="prodId" type="hidden" value="${value.id}">
					</div>
					</div>
					`;
					$('#newsFeed').append(r);
				});


				$('.fas.fa-times').click(function(){
						var ok = confirm("Sind Sie sich sicher, dass Sie löschen wollen?");
						if(ok)
						{
							var id = $(this).parent().children('input').val();
							var token2 = Cookies.get('token');
							if(token2 != 'undefined')
							{
								var dataToSend = new Object();
								dataToSend.token = token2;
								dataToSend = JSON.stringify(dataToSend);
								$.ajax({
									type: 'POST',
									contentType: 'application/json', 
									url: urlPath + 'backend/news/delete/' + id,
									data: dataToSend,
									success: function(result)
									{
										location.reload();
									},
									error: function(response){
										console.log(response);
									}
								});
							}
							else
							{
								window.location.href = urlPath + '?page=login';
							}
						}
					});
			},
			error: function(response){
				handleError(response);
			}
		});
	}

	$('#NewsModal').ready(function(){
		var today = new Date();
		var month = today.getMonth() + 1;
		if(month > 12)
			month = 1;
		var standard = "" + today.getFullYear() + "-" + checkLengthDate(month) + "-" + checkLengthDate(today.getDate()) + "T" + checkLengthDate(today.getHours()) + 
					":" + checkLengthDate(today.getMinutes()) + ":" + checkLengthDate(today.getSeconds());
		$('#startdatum').val(standard);
		today.setDate(today.getDate()+1);
		var standard2 = "" + today.getFullYear() + "-" + checkLengthDate(month) + "-" + checkLengthDate(today.getDate()) + "T" + checkLengthDate(today.getHours()) + 
					":" + checkLengthDate(today.getMinutes()) + ":" + checkLengthDate(today.getSeconds());
		$('#enddatum').val(standard2);
	});

	$('#btnNewsAnlegen').click(function(){
		var title = $('#newsTitle').val();
		var text = $('#newsText').val();
		if(title === "" || text === "")
		{
			alert("Titel und Text dürfen nicht leer sein!");
			return;
		}
		var startdatum = $('#startdatum').val().replace("T"," ");
		var enddatum = $('#enddatum').val().replace("T"," ");
		var token2 = Cookies.get('token');
		if(token2 != 'undefined')
		{
			var dataToSend = new Object();
			dataToSend.messageText = text;
			dataToSend.messageTitle = title;
			dataToSend.startDate = startdatum;
			dataToSend.endDate = enddatum;
			dataToSend.token = token2;
			dataToSend = JSON.stringify(dataToSend);
			$.ajax({
				type: 'POST',
				url: urlPath + 'backend/news/create',
				data: dataToSend,
				contentType: 'application/json', 
				success: function(result){
					$('#NewsModal').modal('hide');
					location.reload();
				},
				error: function(response){
					handleError(response);
				}
			});
		}
		else
		{
			window.location.href = urlPath + '?page=login';
		}
	});

});

function logout() {
	var token = Cookies.get('token');
	if (typeof token != 'undefined') {
		Cookies.remove('token');
		$.ajax({
			url: 'backend/user/logout/' + token,
			type: 'GET'
		});
	}
	window.location.href = urlPath + '?page=login';
};

function checkLengthDate(input){
	if(input.toString().length == 1)
		input = "0" + input;
	return input;
}