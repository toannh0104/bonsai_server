function returnLogin() {
	var isMobile = /iphone|ipod|ipad|android|blackberry|opera mini|opera mobi|skyfire|maemo|windows phone|palm|iemobile|symbian|symbianos|fennec/i.test(navigator.userAgent.toLowerCase());
	var url = "";
	if (isMobile) {
		url = "ghs://bonsai/login";
		try {
			// Android
			Android.goLogin();

		} catch (e) {
			// ios
			goLogin(url);
		}
	} else {
		url = $('#contextPath').val() + "/login";
		goLogin(url);
	}
}
/**
 * Call app to get answer
 */
function goLogin(url) {
	window.location.href = url;
}