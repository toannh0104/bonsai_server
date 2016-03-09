$(document).ready(function() {
	var infor = navigator.userAgent;
	var browser;
	var version = 0;
	var index;

	// Set browser
	if (infor.indexOf('MSIE') >= 0) {
		browser = 'MSIE';
	} else if (infor.indexOf('Firefox') >= 0) {
		browser = 'Firefox';
	} else if (infor.indexOf('Chrome') >= 0) {
		browser = 'Chrome';
	} else if (infor.indexOf('Safari') >= 0) {
		browser = 'Safari';
	} else if (infor.indexOf('Opera') >= 0) {
		browser = 'Opera';
	} else {
		browser = 'UNKNOWN';
	}
	$('#device_name').val("PC");

	// Set version
	index = infor.indexOf(browser) + browser.length + 1;
	version = parseFloat(infor.substring(index, index + 3));
	$('#device_version').val(browser + " " + version);

	// Set location
	$.get("http://freegeoip.net/json/", function(response) {
		var city = response.city;
		var countryName = response.country_name;
		
		if (city == "null" || city == "") {
			city = "N/A";
		}

		if (countryName == "null" || countryName == "") {
			countryName = "N/A";
		}

		if (city == "N/A" && countryName == "N/A") {
			$('#location').val("N/A");
		} else if (city == "N/A") {
			$('#location').val(countryName);
		} else if (countryName == "N/A") {
			$('#location').val(city);
		} else {
			$('#location').val(response.city + ", " + response.country_name);
		}

	}, "jsonp");
});