$(document).ready(function(){
	$('#searchSubmit').submit(function(){
		window.location.href = urlPath + '?page=suchergebnis&searchString=' + $('#suche').val();
		return false;
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