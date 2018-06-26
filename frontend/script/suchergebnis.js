var token2 = Cookies.get('token');
if(token2 != 'undefined')
{
	var searchvalue = getUrlParameter('searchString');
	$.ajax({
		type: "GET",
		url: "backend/search/withParameters?token=" + token2 + "&searchString=" + searchvalue,
		success: function(response)
		{
			console.log(response);
			if(response.users.length == 0  && response.groups.length == 0)
				$('#NotFound').text("Keine Suchergebnisse gefunden");
			if(response.users.length != 0)
			{
				var t = $('#userList');
				$.each(response.users, function(i, v){
					var child = `<li><a href='?page=profil&name=${v.userName}' ><img src='${v.userPicture}'/>${v.userName}</a></li>`; 
					t.append(child);
				});
				t.show();
			}
			/*if(response.groups.length != 0)
			{
				var t = $('#userList');
				$.each(response.users, function(i, v){
					var child = `<li><a href='#'><img src='${v.userPicture}'/>${v.userName}</a></li>`; 
				});
				t.show();
			}*/
		},
		error: function(response)
		{
			handleError(response);
		}
	});
}
else
{
	window.location.href = urlPath + "?page=login";
}