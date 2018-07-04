$(document).ready(function() {
	loadBestenlisten();
	//loadGroups();
});

function loadGroups() {
	$.ajax({
		url: urlPath + 'gruppen-example.json', // TODO
		type: 'GET',
		success: function(data, textStatus, jqXHR) {
			$.each(data.groups, function(i, val) {
				if ($('#bestenliste-group-selected').text() == "") {
					$('#bestenliste-group-selected').text(val.name);
					loadGroupBestenlisten(val.id);
				}
				$('#groupDropdown').append(`<button id='${val.id}' class='dropdown-item bestenliste-group' type='button'>${val.name}</button>`);
			});
			$('.bestenliste-group').click(function() {
				$('#bestenliste-group-selected').text($(this).text());
				loadGroupBestenlisten($(this).attr('id'));
			});
		},
		error: function(data) {
			handleError(data);
		}
	});
}

function loadBestenlisten() {
	// Saison
	// TODO url anpassen
	loadBestenliste(urlPath + 'backend/leaderboard/season', 'saisonBestenliste');
	
	// aktueller Spieltag
	// TODO url anpassen
	loadBestenliste(urlPath + 'backend/leaderboard/gameday', 'spieltagBestenliste');
	
	// ewig
	// TODO url anpassen
	loadBestenliste(urlPath + 'backend/leaderboard/alltime', 'ewigeBestenliste');
}

function loadGroupBestenlisten(groupId) {
	// Saison
	// TODO url anpassen
	loadBestenliste(urlPath + 'bestenliste-example.json', 'saisonBestenlisteGroup');
	
	// aktueller Spieltag
	// TODO url anpassen
	loadBestenliste(urlPath + 'bestenliste-example.json', 'spieltagBestenlisteGroup');
	
	// ewig
	// TODO url anpassen
	loadBestenliste(urlPath + 'bestenliste-example.json', 'ewigeBestenlisteGroup');
}

function loadBestenliste(url, tableId) {
	$.ajax({
		url: url,
		type: 'GET',
		success: function(data, textStatus, jqXHR) {
			$('#' + tableId + ' tbody').empty();
			$.each(data, function(i, val) {
				$('#' + tableId + ' tbody').append(`<tr>
						<td>${val.platzierung}</td>
						<td>${val.username}</td>
						<td>${val.points}</td>
					</tr>`);
			});
			$('#' + tableId).DataTable({
				"paginate": false,
				"filter": true,
				"info": false
			});
		},
		error: function(data) {
			
		}
	});
}