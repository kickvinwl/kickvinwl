$(document).ready(function(){

	var searchName = getUrlParameter('name');
	if(searchName == null)
	{
		$('#groupRow').css('display', 'flex');
		$('#gruppenTable').DataTable();
		$('#editTitel').css('display', 'inline-block');
		$('#editTitel').click(function(){
			$('#TitelModal').modal('show');	
		});
		$('#changeTitel').click(function(){
			$.each($('.achv_radio'),function(i,v){
				if($(v).prop("checked") == true)
				{
					$('#cur_Titel').text($(v).prop("name"));
				}
			});
			$('#TitelModal').modal('hide');
			lieferNeueDaten($('#cur_Titel').text(), "titel");
		});
		$('#file-input').change(function(){
			lieferNeueDaten($('#file-input').val(), "bild");
		});
		var token2 = Cookies.get('token');
		if (typeof token2 != 'undefined') {
			$.getJSON("backend/user/get/" + token2, function(result){
				$('#name').val(result.userName);
				var pic = result.userPicture;
				if(pic != null){
					$('#profilBild').attr('src', pic);
				}
				$('#aktPkt').val(result.Punkte);
				$('#aktPlatz').val(result.Platz);
				$('#besterTag').val(result.Bester);
			});
		}
		else{
			window.location.href = urlPath + '?page=login';
		}
		
		function lieferNeueDaten(daten, was){
			var token2 = Cookies.get('token');
			if (typeof token2 != 'undefined') {
				if(was == "titel")
				{
					$.ajax({
					  type: "POST",
					  headers: { 
					        'Content-Type': 'application/json' 
					  },
					  url: "backend/user/setAchievment/" + token2,
					  data: JSON.stringify(daten),
					  success: function(response){
					  	location.reload();	
					  },
					  dataType: "json"
					});
				}
				else
				{
					$.ajax({
					  type: "POST",
					  headers: { 
					        'Content-Type': 'application/json' 
					  },
					  url: "backend/user/setImage/" + token2,
					  data: JSON.stringify("https://as.ftcdn.net/r/v1/pics/ea2e0032c156b2d3b52fa9a05fe30dedcb0c47e3/landing/images_photos.jpg"),
					  success: function(response){
					  	location.reload();	
					  },
					  dataType: "json"
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
		//ajax call für den jeweiligen User
		//variable für aktPkt, aktPlatz, besterTag, username, bild
		var token2 = Cookies.get('token')
		$.ajax({
			type: "GET",
			url: "backend/user/getUserByName?token=" + token2 + "&userName=" + searchName,
			success: function(result){
				$('#name').val(result.userName);
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