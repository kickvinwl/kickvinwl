$(document).ready(function() {
	loadTabelle();
});

function loadTabelle() {
	$.ajax({
		url: urlPath + 'backend/table/bl1',
		type: 'GET',
		success: function(data, textStatus, jqXHR) {
			$.each(data.teams, function(i, val) {
				$('#bundesligaTabelle tbody').append(`<tr>
				  <th scope="row">${val.place}</th>
				  <td><img src='${val.imageLink}' class="img-fluid" style="width: 20px;"> ${val.clubName}</td>
				  <td>${val.games}</td>
				  <td>${val.wins}</td>
				  <td>${val.losses}</td>
				  <td>${val.draws}</td>
				  <td>${val.goals}:${val.opponentGoals}</td>
				  <td>${val.difference}</td>
				  <td>${val.points}</td>
				</tr>`);
			});
		},
		error: function(data) {
			
		}
	});
}