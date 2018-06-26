$(document).ready(function(){
	$('#searchSubmit').submit(function(){
		window.location.href = urlPath + '?page=suchergebnis&searchString=' + $('#suche').val();
		return false;
	});

	var token = Cookies.get('token');
	if(typeof token != 'undefined'){
		$.ajax({
			url: 'backend/user/getImageOfUser?token=' + token,
			type: 'GET',
			success: function(result){
				$('.dropdownMenuLink').attr("src", result.Picture);
			},
			error: function(response)
			{
				handleError(response);
			}
		});
		$.ajax({
			url: 'https://api.myjson.com/bins/pezia',
			type: 'GET',
			success: function(result){
				$.each(result, function(index, value){
					var subTitle = "";
					if(value.SubTitle == undefined)
					{
						subTitle = "";	
					}
					else
						subTitle = value.SubTitle;
					var r = `<div class="card m-1" style="width: 18rem;"><div class="card-body"><h5 class="card-title">
					${index}</h5><h6 class="card-subtitle mb-2 text-muted">${subTitle}</h6>
					<p class="card-text">${value.Text}</p>
					</div>
					</div>
					`;
					console.log(`${subTitle}`);
					$('#newsFeed').append(r);
				});
			},
			error: function(response){
				handleError(response);
			}
		});
	}

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