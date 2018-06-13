$(document).ready(function() {
	loadBestenlisten();
	loadGroups();
});

$(document).ready(function(){
	$('.bestenliste-group').click(function() {
		$('#bestenliste-group-selected').text($(this).text());
		loadGroupBestenlisten($(this).attr('id'));
	});
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
		},
		error: function(data) {
			
		}
	});
}

function loadBestenlisten() {
	// Saison
	// TODO url anpassen
	loadBestenliste(urlPath + 'bestenliste-example.json', 'saisonBestenliste');
	
	// aktueller Spieltag
	// TODO url anpassen
	loadBestenliste(urlPath + 'bestenliste-example.json', 'spieltagBestenliste');
	
	// ewig
	// TODO url anpassen
	loadBestenliste(urlPath + 'bestenliste-example.json', 'ewigeBestenliste');
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
			$.each(data.placements, function(i, val) {
				$('#' + tableId + ' tbody').append(`<tr>
						<td>${val.placement}</td>
						<td>${val.user}</td>
						<td>${val.points}</td>
					</tr>`);
			});
		},
		error: function(data) {
			
		}
	});
}