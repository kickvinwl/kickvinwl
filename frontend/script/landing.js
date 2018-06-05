$(document).ready(function() {
	$.ajax({
		url: urlPath + 'match-example.json', // group nur temporär für Testzwecke
		type: 'GET',
		success: function(data, textStatus, jqXHR) {
			$('#gameday').text(data.gameday);
			var dateOptions = { weekday: 'long', /*year: 'numeric',*/ month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric' };
			var lastDate;
			$.each(data.matches, function(i, val) {
				var date = new Date(val.date * 1000);
				if (typeof lastDate == 'undefined' || lastDate.getTime() !== date.getTime()) {
					$('#gamedayTable tbody').append('<tr><th>' + date.toLocaleDateString('de-DE', dateOptions) + ' Uhr</th></tr>');
				}
				lastDate = date;
				
				if (val.homeTeam.tipScore == null) val.homeTeam.tipScore = '';
				if (val.awayTeam.tipScore == null) val.awayTeam.tipScore = '';
				if (val.homeTeam.realScore == null) val.homeTeam.realScore = '-';
				if (val.awayTeam.realScore == null) val.awayTeam.realScore = '-';
				var gameStarted = new Date() >= date;
				var tipDisabled = gameStarted ? 'disabled' : '';
				var points = gameStarted && val.points != null ? val.points + (' Punkt' + (val.points > 1 ? 'e' : '')) : '';
				
				$('#gamedayTable tbody').append(`<tr>
						<td id="${val.id}">
							<div class="row d-flex justify-content-between">
								<div class="col-5 align-middle mt-3">
									<div class="float-right" style="font-size: 20px">
										${val.homeTeam.name} <img src="${val.homeTeam.logo}" />
									</div>
								</div>
								<div class="col-2"  title="${points}">
									<div class="row">
										<div class="input-group">
											<input type="text" class="form-control form-control-sm homeTip" aria-describedby="basic-addon1" maxlength="2" style="height: 33px; text-align: right;"  value="${val.homeTeam.tipScore}" ${tipDisabled}></input>
											<div class="input-group-prepend" style="height: 33px">
												<span class="input-group-text" id="basic-addon1">:</span>
											</div>
											<input type="text" class="form-control form-control-sm awayTip" aria-describedby="basic-addon1" maxlength="2" style="height: 33px" value="${val.awayTeam.tipScore}" ${tipDisabled}></input>
										</div>
									</div>
									<div class="row justify-content-md-center">
										<span style="font-size: 24px;">${val.homeTeam.realScore} : ${val.awayTeam.realScore}</span>
									</div>
								</div>
								<div class="col-5 mt-3">
									<div style="font-size: 20px">
										<img src="${val.awayTeam.logo}" /> ${val.awayTeam.name}
									</div>
								</div>
							</div>
						</td>
					</tr>`);
			});
		},
		error: function(data) {
			
		}
	});
});

$('#submitTip').click(function() {
	$('#gamedayTable tbody tr td').each(function() {
		var val = $(this);
		var matchId = this.id;
		var homeTip = val.find('.homeTip').val();
		var awayTip = val.find('.awayTip').val();
		console.log(matchId, homeTip, awayTip);
	});
});