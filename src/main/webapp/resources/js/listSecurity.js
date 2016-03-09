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