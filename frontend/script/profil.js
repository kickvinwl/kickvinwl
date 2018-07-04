$(document).ready(function(){

	//check if search or own profile
	var searchName = getUrlParameter('name');
	if(searchName == null)
	{
		$('#groupRow').css('display', 'flex');
		$('#editTitel').css('display', 'inline-block');
		$('#editTitel').click(function(){
			$('#TitelModal').modal('show');	
		});

		//change Title and send AJAX set
		$('#changeTitel').click(function(){
			var id = "";
			$.each($('.achv_radio'),function(i,v){
				if($(v).prop("checked") == true)
				{
					var titletext = $(v).parent().children(".achv_title");
					$('#cur_Titel').text(titletext);
					id = $(v).prop("name");
				}
			});
			$('#TitelModal').modal('hide');
			setUserData(id, "title");
		});
		$('#file-input').change(function(){
			setUserData($('#file-input').val(), "picture");
		});

		var token2 = Cookies.get('token');
		if (typeof token2 != 'undefined') {
			$.getJSON( urlPath + "backend/user/get/" + token2, function(result){
				$('#name').val(result.userName);
				if(result.displayedTitle != null)
				{
					$('#cur_Titel').text(result.displayedTitle.title);
				}
				else
					$('#cur_Titel').text("Hier Titel auswählen");
				var pic = result.userPicture;
				if(pic != null){
					$('#profilBild').attr('src', pic);
				}
				$('#aktPkt').val(result.Punkte);
				$('#aktPlatz').val(result.Platz);
				$('#besterTag').val(result.Bester);
			});
			$.ajax({
			  type: "GET",
			  url: urlPath + "backend/user/getUserAchievements?token=" + token2,
			  success: function(response){
			  	$.each(response, function(i,v){
			  		var radiobtn = `<td><input type="radio" name="${v.id}" class="achv_radio"></td>`;
			  		var title = `<td class="achv_title">${v.title}</td>`;
			  		var description = `<td>${v.achievementDescription}</td>`;
			  		$('#tableRowAchivements').append('<tr>' + radiobtn + title + description + '</tr>');
			  	});
			  },
			  error: function(response){
			  	console.log("Error");
			  }
			});
		}
		else{
			window.location.href = urlPath + '?page=login';
		}
		
		//Userdata refresh
		function setUserData(data, what){
			var token2 = Cookies.get('token');
			if (typeof token2 != 'undefined') {
				if(what == "title")
				{
					$.ajax({
					  type: "POST",
					  url: urlPath + "backend/user/setAchievement/" + token2 + "?id=" + data,
					  success: function(response){
					  }
					});
				}
				else
				{
					$.ajax({
					  type: "POST",
					  headers: { 
					        'Content-Type': 'application/json' 
					  },
					  url: urlPath + "backend/user/setImage/" + token2,
					  data: JSON.stringify("https://as.ftcdn.net/r/v1/pics/ea2e0032c156b2d3b52fa9a05fe30dedcb0c47e3/landing/images_photos.jpg"),
					  success: function(response){
					  	location.reload();	
					  }					  
					});	
				}
			}
			else{
				window.location.href = urlPath + '?page=login';
			}
		}	
	}
	else
	{
		//ajax call for user over search
		var token2 = Cookies.get('token')
		$.ajax({
			type: "GET",
			url: "backend/user/getUserByName?token=" + token2 + "&userName=" + searchName,
			success: function(result){
				$('#name').val(result.userName);
				if(result.displayedTitle != null)
				{
					$('#cur_Titel').text(result.displayedTitle.title);
				}
				else
					$('#cur_Titel').text("Kein Titel vorhanden");
				var pic = result.userPicture;
				if(pic != null){
					$('#profilBild').attr('src', pic);
				}
				$('#aktPkt').val(result.Punkte);
				$('#aktPlatz').val(result.Platz);
				$('#besterTag').val(result.Bester);
			}
		});
	}
	
});