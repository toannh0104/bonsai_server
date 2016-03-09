var indexPractice = 0;

$(document).ready(function() {

	// Invisible all lesson info block
	$('.memory').addClass('invisible');

	// Show current lesson info
	$('.memoryPage_' + indexPractice).removeClass('invisible');

	// Invisible all lesson info block
	$('.memoryVocabulary').addClass('invisible');

	// Show current lesson info
	$('.memoryVocabularyPage_' + indexPractice).removeClass('invisible');

});

/**
 * Toggle lesson
 */
function toggleLessonPractice() {

	// Invisible all lesson info block
	$('.memory').addClass('invisible');

	// Show current page scenario
	$('.memoryPage_' + indexPractice).removeClass('invisible');

	// Invisible all lesson info block
	$('.memoryVocabulary').addClass('invisible');

	// Show current page vocabulary
	$('.memoryVocabularyPage_' + indexPractice).removeClass('invisible');

}
function speakerAnswer(index) {
	var userId = $("#clientUserId").val();
	var lessonNo = $("#lessonNo").val();
	var txtQuestion = $("#inputQuestionJSon" + index).html();
	var modeLanguage = getModeLanguage(parseInt($('#lessonNo').val()));

	try {
		// Android
		Android.getSpeakerAnswer(userId, index, txtQuestion, lessonNo, modeLanguage);
	} catch (e) {
		// ios
		getSpeakerAnswer(userId, index, txtQuestion, lessonNo, modeLanguage);
	}
}
/**
 * Call app to get answer
 */
function getSpeakerAnswer(userId, index, txtQuestion, lessonNo, modeLanguage) {
	var url = "ghs://bonsai/" + userId + "/" + index + "/" + txtQuestion + "/" + lessonNo + "/" + modeLanguage;
	window.location.href = url;
}
/**
 * Fill app to get answer
 */
function fillSpeakResult(index, result) {
	$(".inputAnswer" + index).val(result);
}

/**
 * Show app message
 */
function showSpeakResultMessage(content) {
	bootbox.alert(content);
}