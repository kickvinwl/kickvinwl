$(document).ready(function(){
	$('#searchSubmit').submit(function(){
		window.location.href = urlPath + '?page=suchergebnis&searchString=' + $('#suche').val();
		return false;
	});

	var token = Cookies.get('token');
	if(typeof token != 'undefined'){
		$.ajax({
			url: 'backend/user/get/' + token,
			type: 'GET',
			success: function(result){
				$('.dropdownMenuLink').attr("src", result.Picture);
				if(result.userIsAdmin == "1")
				{
					$
				}
			},
			error: function(response)
			{
				handleError(response);
			}
		});
		$.ajax({
			url: 'newsfeed-example.json',
			type: 'GET',
			success: function(result){
				$.each(result, function(index, value){
					$.each(value, function(i, v){
						var r = `<div class="card m-1" style="width: 18rem;"><div class="card-body"><h5 class="card-title">
						${v.messageTitle}</h5>
						<p class="card-text">${v.messageText}</p>
						</div>
						</div>
						`;
						$('#newsFeed').append(r);	
					});
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
		$('#enddatum').val(standard);
	});

	$('#btnNewsAnlegen').click(function(){
		var title = $('#newsTitle').val();
		var text = $('#newsText').val();
		if(title === "" || text === "")
		{
			alert("Titel und Text dürfen nicht leer sein!");
			return;
		}
		var startdatum = $('#startdatum').val();
		var enddatum = $('#enddatum').val();
		console.log("Startdatum: " + startdatum);
		console.log("Enddatum: " + enddatum);
		var token2 = Cookies.get('token');
		if(token2 != 'undefined')
		{
			var dataToSend = new object();
			dataToSend.messageText = text;
			dataToSend.messageTitle = title;
			dataToSend.startDate = startdatum;
			dataToSend.endDate = enddatum;
			dataToSend.token = token2;
			dataToSend = JSON.stringify(dataToSend);
			console.log(dataToSend);
			/*$.ajax({
				type: 'POST',
				headers: 
					{ 
					    'Content-Type': 'application/json' 
					},
				url: '',
				data: dataToSend,
				dataType: "json"
			});*/
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