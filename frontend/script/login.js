$(document).ready(function() {
	var token = Cookies.get('token');
	if (typeof token != 'undefined') {
		$.ajax({
			url: urlPath + 'backend/user/get/' + token,
			type: 'GET',
			success: function(data, textStatus, jqXHR) {
				// Session valide, Weiterleitung auf Home-Seite
				window.location.href = urlPath;
			},
			error: function(data) {
				// keine valide Session, auf Login-Seite bleiben
			}
		});
	}
});

$('#loginSubmit').click(function() {
    login($('#loginName').val(), $('#loginPassword').val());
});
$(document).keypress(function(e) {
    if (e.which == 13) 
		login($('#loginName').val(), $('#loginPassword').val());
});


function login(user, pw) {
	$.ajax({
		url: urlPath + 'backend/login?name=' + user + '&pw=' + pw, // group nur temporär für Testzwecke
		type: 'GET',
		success: function(data, textStatus, jqXHR) {
			$('#loginError').addClass('d-none');
			Cookies.set('token', data.token, { expires: 1/24 });
			window.location.href = urlPath;
		},
		error: function(data) {
			var text = data.status == 401 ? "Nutzername oder Passwort falsch" :  "Unbekannter Fehler";
			$('#loginError span').text(text);
			$('#loginError').removeClass('d-none');
		}
	});
}