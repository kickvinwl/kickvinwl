$(document).ready(function() {
	var page = getUrlParameter('page');
	if (page != null) {
		var file = 'views/' + page + '.html';
	} else {
		var file = 'views/landing.html';
	}

	if (page != 'login') {
		var token = Cookies.get('token');
		if (typeof token == "undefined") {
			window.location.href = urlPath + '?page=login';
		}
		
		includeFile($('#header'), 'views/header.html');
		//includeFile($('#footer'), 'views/footer.html');
	}
	includeFile($('#content'), file);
	
	$($('.active')[0]).removeClass("active");
	if (page === "landing" || page == null) {
		$($('.nav-link')[0]).parent().addClass("active");
	} else {
		var links = $('a.nav-link');
		$.each(links, function(i, v) {
			if ($(v).text().toLowerCase() ===  page) {
				$(v).parent().addClass("active");
				return false;
			}
		});	
	}
});

function handleError(result){
	if(result.status == 401)
		window.location.href = urlPath + "?page=login";
}