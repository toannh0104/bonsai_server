/**
 * @param redirect
 * @param lessonId
 */
function submitform(redirect, lessonId) {
	var form = document.forms["lessonForm"];
	form.action = $('#url').val() + "/" + redirect + "/lesson/" + lessonId
			+ "?lang=" + $('#languageId').val();
	form.submit();
}

/**
 * @param redirect
 * @param lessonId
 */
function submitFormSecurity(redirect, lessonId) {
	var form = document.forms["lessonForm"];
	form.action = $('#url').val() + "/" + redirect + "/security/" + lessonId
			+ "?lang=" + $('#languageId').val();
	form.submit();
}

/**
 * redirect admin lesson
 *
 * @param lessonId
 */
function adminLesson(lessonId) {
	submitform('admin', lessonId);
}

/**
 * redirect client lesson
 *
 * @param lessonId
 */
function clientLesson(lessonId) {
	submitform('client', lessonId);
}

/**
 * redirect security lesson
 *
 * @param lessonId
 */
function adminSecurity(lessonId) {
	submitFormSecurity('admin', lessonId);
}

/**
 * redirect security lesson
 *
 * @param lessonId
 */
function clientSecurity(lessonId) {
	submitFormSecurity('client', lessonId);
}

$(function() {
	$('#languageId').val('EN');
});
