var url;
var page = 0;
var CLIENT = {};
CLIENT.ONSCENARIO = 0;
CLIENT.ONVOCABULARY = 1;
// Flag for check scored Scenario
var flagScenario = 0;
// Flag for check scored Vocabulary
var flagVocabulary = 0;
// variable to save score of Scenario and Vocabulary (init = -1)
var saveScoreScenario = -1;
var saveScoreVocabulary = -1;

// flagAvg check has finish scenario or vocabulary
var flagAvgScenario = false;

// flagAvg check has finish vocabulary
var flagAvgVocabulary = false;

var endTime;

var pageIndexPracticeMemory = 1;

var pageIndexPracticeWriting = 1;

var pageIndexPracticeConversation = 1;

var pageIndexPracticeMemoryVocabulary = 1;

var pageIndexPracticeWritingVocabulary = 1;

var pageIndexPracticeConversationVocabulary = 1;
/**
 * Set up ajax loading
 */
$(document).ajaxStart(function() {
	$('#loader').show();
}).ajaxStop(function() {
	$('#loader').hide();
});

$(document).ready(function() {

	url = $('#url').val();
	lessonId = $('#id').val();
	currentLessonInfoIndex = $('#currentLessonInfoIndex').val();
	lessonInfoId = $('#lessonInfoId').val();

	// Check show for event onload
	if ($('#scenario_vocabulary').val() == "scenario") {
		$('.contenInfoSenario').removeClass('invisible');
		$('.vocabularyImg').removeClass('invisible');
	} else if ($('#scenario_vocabulary').val() == "vocabulary") {
		$('.contenInforVocabulary').removeClass('invisible');
		$('.scenarioImg').removeClass('invisible');
	} else {
		$('.scenarioImg').removeClass('invisible');
		$('.vocabularyImg').removeClass('invisible');
	}

});

/**
 * On click image Scenario
 */
function onClickScenarioImg() {
	// Set flag
	$('#scenario_vocabulary').val('scenario');

	onOffAreaScenarioVocabulary(CLIENT.ONSCENARIO);
}

/**
 * On click image Vocabulary
 */
function onClickVocabularyImg() {
	// Set flag
	$('#scenario_vocabulary').val('vocabulary');

	onOffAreaScenarioVocabulary(CLIENT.ONVOCABULARY);
}

/**
 * On or Off Scenario Vocabulary
 * 
 * @param state
 */
function onOffAreaScenarioVocabulary(state) {
	if (state == 0) {
		$('.vocabularyImg').removeClass('invisible');
		$('.contenInforVocabulary').addClass('invisible');
		$('.scenarioImg').addClass('invisible');
		$('.contenInfoSenario').removeClass('invisible');
	} else {
		$('.contenInfoSenario').addClass('invisible');
		$('.scenarioImg').removeClass('invisible');
		$('.vocabularyImg').addClass('invisible');
		$('.contenInforVocabulary').removeClass('invisible');
	}
}

/**
 * Function even back
 */
function evenBack() {
	var form = document.forms["lessonForm"];
	form.action = url;
	form.submit();
}

/**
 * Call ajax for scoring
 * 
 * @param lessonInfoIndex
 * @param pageIndex
 * @param lessonType
 */
function ajax(lessonInfoIndex, pageIndex, lessonType, areaScore) {

	// List questions
	var questions = [];

	// List answers
	var answers = [];

	// List question id
	var questionIds = [];

	// List vocabulary questions
	var vocabularyQuestions = [];

	// List vocabulary answers
	var vocabularyAnswers = [];

	var postStringData = "var postData = {";

	// Set value type memory or writing or speech
	var memo_writ_spee = memoryWritingConversation;

	// Set value type scenario or vocabulary
	var scena_voca = $("#scenario_vocabulary").val();

	var stringClass = '';

	var classValue = getStringCheck("").replace('Vocabulary','');
	var flagScenario = getScenarioVocabulary(classValue, 'memory', 'flagScenario');

	var flagVocabulary = getScenarioVocabulary(classValue + 'Vocabulary', 'memoryVocabulary', 'flagVocabulary');

	saveScoreScenario = getScenarioVocabulary(classValue, 'memory', 'scoreScenario');
	saveScoreVocabulary = getScenarioVocabulary(classValue + 'Vocabulary', 'memoryVocabulary', 'scoreVocabulary');

	var startTime = getScenarioVocabulary(classValue + 'Vocabulary', 'memoryVocabulary', 'startTime');

	var currentIdRecordScenario = getScenarioVocabulary(classValue, 'memory', 'currentIdRecordScenario');
	var	currentIdRecordVocabulary = getScenarioVocabulary(classValue + 'Vocabulary', 'memoryVocabulary', 'currentIdRecordVocabulary');
	// Score for Scenario
	if (areaScore == 'scenario') {
		stringClass = getStringCheck("");
		$('.lessonScenario_' + currentLessonIndex).find('.contenInfoSenario')
				.find('.Information').find('.' + stringClass).each(function(index) {
					if ($(this).is(':visible')) {
						$(this).find('.memoryPage_' + pageIndex).each(function(index) {
							if ($(this).is(':visible')) {
								$(this).find('.row').each(function(index1) {
									var answersList = '';
									var no = $(this).find('.no').val();
									// Set list questions form
									// view to list questions
									var inforQuestion = $(this).find(
											'.question_'
											+ lessonInfoIndex
											+ '_'
											+ pageIndex
											+ '_' + no)
											.val();
									questions.push(inforQuestion);
									
									var check = false;
									// Set list answers form
									// view to list answers
									if (memo_writ_spee == 'writing'
										&& scena_voca == 'scenario') {
										var element = $(this).find('.element').val();
										for (var int = 1; int <= element; int++) {
											var inforAnswer = $(this).find(
													'.answer_'
													+ lessonInfoIndex
													+ '_'
													+ pageIndex
													+ '_'
													+ no
													+ '_'
													+ int).val();
											if (inforAnswer.trim() != '') {
												check = true;
												answersList += inforAnswer + ';';
											} else {
												answersList += " " + ';';
											}
										}
										if (!check) {
											answers.push(null);
										} else if (answersList != '') {
											answers.push(answersList);
										}
									} else {
										var inforAnswer = $(this).find(
												'.answer_'
												+ lessonInfoIndex
												+ '_'
												+ pageIndex
												+ '_'
												+ no).val();
										if (inforAnswer.trim() == '') {
											answers.push(null);
										} else {
											answers.push(inforAnswer);
										}
									}
									questionIds.push('inforAnswer_'
											+ lessonInfoIndex
											+ '_'
											+ pageIndex
											+ '_' + no);
								});
							}
						});
					}
				});

		for (var int = 0; int < questionIds.length; int++) {
			postStringData += '"questionIds[' + int + ']" :  "'
					+ questionIds[int] + '"' + ",";
		}

		// Set Post String for Scenario
		for (var i = 0; i < questions.length; i++) {
			if (i != 0) {
				postStringData += ",";
			}
			postStringData += '"questions[' + i + ']" :  "' + questions[i]
					+ '"';
		}

		// If both questions and answers have data
		if (questions.length != 0 && answers.length != 0) {
			postStringData += ",";
		}

		for (var i = 0; i < answers.length; i++) {
			postStringData += '"answers[' + i + ']" :  "' + answers[i] + '"'
					+ ",";
		}

		// Score for Vocabulary
	} else {
		stringClass = getStringCheck("Vocabulary");
		$('.lessonVocabulary_' + currentLessonIndex).find('.contenInforVocabulary')
				.find('.' + stringClass).each(function(index) {
			if ($(this).is(':visible')) {
				$(this).find('.memoryVocabularyPage_' + pageIndex).each(function(index) {
					if ($(this).is(':visible')) {
						$(this).find('.row').each(function(index1) {
							var no = $(this).find('.no').val();
							// Set list questions form
							// view to list questions
							var inforQuestion = $(this).find(
									'.question_'
									+ lessonInfoIndex
									+ '_'
									+ pageIndex
									+ '_' + no)
									.val();
							vocabularyQuestions.push(inforQuestion);
							questionIds.push('inforAnswerVoca_' + lessonInfoIndex + '_'
									+ pageIndex + '_' + no);
							
							
							var inforAnswer = $(this).find(
									'.listPageVocabularyAnswer_'
									+ lessonInfoIndex + "_" + pageIndex).val();
							if (inforAnswer.trim() == '') {
								vocabularyAnswers.push(null);
							} else {
								vocabularyAnswers.push(inforAnswer);
							};
						});
					}
				});
			}
		});

		for (var int = 0; int < questionIds.length; int++) {
			postStringData += '"questionIds[' + int + ']" :  "'
					+ questionIds[int] + '"' + ",";
		}

		// Set Post String for Vocabulary
		for (var i = 0; i < vocabularyQuestions.length; i++) {
			if (i != 0) {
				postStringData += ",";
			}
			postStringData += '"vocabularyQuestions[' + i + ']" :  "'
					+ vocabularyQuestions[i] + '"';
		}

		// If both vocabularyQuestions and vocabularyAnswers have data
		if (vocabularyQuestions.length != 0 && vocabularyAnswers.length != 0) {
			postStringData += ",";
		}

		for (var i = 0; i < vocabularyAnswers.length; i++) {
			postStringData += '"vocabularyAnswers[' + i + ']" :  "'
					+ vocabularyAnswers[i] + '"' + ",";
		}

	}

	// Set value type practice or test
	var pract_test = $("#practice_test").val();
	if (practice_test == "")
		pract_test = "practice";

	if (scena_voca == "")
		scena_voca = "scenario";

	// Type = memory when memo_writ_spee not set
	if (memo_writ_spee == "")
		memo_writ_spee = "memory";
	
	var practiceTime = 1/1;

	if(memo_writ_spee == "memory" && pract_test == "practice") {
		practiceTime = $('.numberPracticeMemoryVocabulary').find('.numberPractice').html();
	} else if (memo_writ_spee == "writing" && pract_test == "practice") {
		practiceTime = $('.numberPracticeWritingVocabulary').find('.numberPractice').html();
	} else if (memo_writ_spee == "speech" && pract_test == "practice") {
		practiceTime = $('.numberPracticeConversationVocabulary').find('.numberPractice').html();
	} else if (memo_writ_spee == "memory" && pract_test == "test") {
		practiceTime = $('.numberTestMemoryVocabulary').find('.numberPractice').html();
	} else if (memo_writ_spee == "writing" && pract_test == "test") {
		practiceTime = $('.numberTestWritingVocabulary').find('.numberPractice').html();
	} else {
		practiceTime = $('.numberTestConversationVocabulary').find('.numberPractice').html();
	}

	// information about lesson
	postStringData += '"lessonType" : ' + '"' + $('#lessonType').val() + '"';
	postStringData += ",";
	postStringData += '"lessonId" : ' + '"' + $('#id').val() + '"';
	postStringData += ",";
	postStringData += '"lessonInfo" : ' + '"' + $('#lessonInfoId').val() + '"';
	postStringData += ",";
	postStringData += '"scenarioVocabulary" : ' + '"' + scena_voca + '"';
	postStringData += ",";
	postStringData += '"practiceTest" : ' + '"' + pract_test + '"';
	postStringData += ",";
	postStringData += '"memoryWriteSpeech" : ' + '"' + memo_writ_spee + '"';
	postStringData += ",";
	postStringData += '"areaScore" : ' + '"' + areaScore + '"';
	postStringData += ",";
	postStringData += '"saveScoreScenario" : ' + '"' + saveScoreScenario + '"';
	postStringData += ",";
	postStringData += '"saveScoreVocabulary" : ' + '"' + saveScoreVocabulary + '"';
	postStringData += ",";
	postStringData += '"flagAvgScenario" : ' + '"' + flagScenario + '"';
	postStringData += ",";
	postStringData += '"flagAvgVocabulary" : ' + '"' + flagVocabulary + '"';
	postStringData += ",";
	postStringData += '"practiceTime" : ' + '"' + practiceTime + '"';
	postStringData += ",";
	postStringData += '"lessonNo" : ' + '"' + $('#lessonNo').val() + '"';
	postStringData += ",";
	postStringData += '"lessonName" : ' + '"' + $('.name').html() + '"';
	postStringData += ",";
	postStringData += '"startTime" : ' + '"' + startTime + '"';
	postStringData += ",";
	postStringData += '"stringClass" : ' + '"' + stringClass + '"';
	postStringData += ",";
	postStringData += '"currentIdRecordScenario" : ' + '"' + currentIdRecordScenario + '"';
	postStringData += ",";
	postStringData += '"currentIdRecordVocabulary" : ' + '"' + currentIdRecordVocabulary + '"';

	// End postStringData
	postStringData += "}";

	// Evaluate javascript string to init post data
	eval(postStringData);
	// call ajax
	$.ajax({
		url : url + "/client/score/",
		data : postData,
		type : "POST",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(result) {
			if (result.status == RESULT_STATUS_OK) {
				// Update status is score
				$("#iScore").val("true");

				// Set data to view
				setDateOnView(result);
			} else {
				var settings = {
					closeButton : false,
					hideClass : "btn-primary"
				};
				createBootboxDialog(MSG.E0037, result.message, settings);
			}
		}
	});

}

/*
 * function checkIsScored(lessonInfoIndex, lessonType, areaScore) {
 * if((areaScore == 'scenario' && flagScenario == 0) || (areaScore ==
 * 'vocabulary' && flagVocabulary == 0)) { checkBeforScore(lessonInfoIndex,
 * lessonType, areaScore); } else { } }
 */

/**
 * Check condition before score
 * 
 * @param lessonInfoIndex
 * @param lessonType
 */
function checkBeforScore(lessonInfoIndex, lessonType, areaScore) {

	var flagCheckAnswerScenario = 0;
	var flagCheckAnswerVocabulary = 0;
	var flagCheckAnswerScenarioTest = 0;
	var flagCheckAnswerVocabularyTest = 0;
	var pract_test = $("#practice_test").val();
	var currentPage = 0;

	$('.lessonVocabulary_' + currentLessonIndex).find('#practiceVocabularyInforArea').find('.numberVocabulary').each(function(index) {
		if ($(this).is(':visible')) {
			var indexPage = parseInt(getIndexPage($(this).find('.numberPractice').html()));
			var limitPage = parseInt(getLimitPage($(this).find('.numberPractice').html()));
			currentPage = getCurrentPage(indexPage, limitPage);
		}
	});

	// get string
	var stringClass;
	if (areaScore == "vocabulary") {
		stringClass = getStringCheck("Vocabulary");
	} else {
		stringClass = getStringCheck("");
	}

	// Check answer
	$('.lessonScenario_' + lessonInfoIndex).find('.contenInfoSenario').find('.Information').find('.' + stringClass).each(function(index) {
		if ($(this).is(':visible')) {
			$(this).find('.memoryPage_' + currentPage).each(function(index) {
				if ($(this).is(':visible')) {
					$(this).find('.row').each(function(index1) {
						var no = $(this).find('.no').val();

						var check = false;
						// Set list answers form
						// view to list answers
						if (stringClass.indexOf("Writing") != -1) {
							var element = $(this).find('.element').val();
							for (var int = 1; int <= element; int++) {
								var inforAnswer = $(this).find( '.answer_' + lessonInfoIndex + '_' + currentPage + '_' + no + '_' + int).val();
								if (inforAnswer.trim() != '') {
									check = true;
								}
							}
							if (check) {
								flagCheckAnswerScenario = 1;
							} else {
								flagCheckAnswerScenarioTest = 1;
							}
						} else {
							var inforAnswer = $(this).find('.answer_' + lessonInfoIndex + '_' + currentPage + '_' + no).val();
							if (inforAnswer.trim() != '') {
								flagCheckAnswerScenario = 1;
							} else {
								flagCheckAnswerScenarioTest = 1;
							}
						}
					});
				}
			});
		}
	});

	// Check vocabulary
	var listVocabularyAnswer = $("." + stringClass + " .listPageVocabularyAnswer_" + lessonInfoIndex + "_" + currentPage);
	for (var i = 0; i < listVocabularyAnswer.length; i++) {
		var value = listVocabularyAnswer[i].value;
		if (value.trim() != "") {
			flagCheckAnswerVocabulary = 1;
		} else {
			flagCheckAnswerVocabularyTest = 1;
		}
	}

	var settings = {
		closeButton : false,
		hideClass : "btn-success"
	};

	if (lessonType == 0) {
		if (flagCheckAnswerScenario == 0 && areaScore == 'scenario') {

			createBootboxDialog(MSG.E0037, MSG.E0030, settings);
		} else if (flagCheckAnswerVocabulary == 0 && areaScore == 'vocabulary') {

			createBootboxDialog(MSG.E0037, MSG.E0031, settings);
		} else if (pract_test == 'test' && flagCheckAnswerScenarioTest == 1
				&& areaScore == 'scenario') {
			createBootboxDialog(MSG.E0037, "Please input full scenario !",
					settings);
		} else if (pract_test == 'test' && flagCheckAnswerVocabularyTest == 1
				&& areaScore == 'vocabulary') {
			createBootboxDialog(MSG.E0037, "Please input full vocabulary !",
					settings);
		} else {

			if (areaScore == 'scenario') {
				// update flagScenario = 1 when you choose scoring for Scenario
				flagScenario = 1;
			}

			if (areaScore == 'vocabulary') {
				// update flagVocabulary = 1 when you choose scoring for
				// Vocabulary
				flagVocabulary = 1;
			}

			// Call function score
			ajax(lessonInfoIndex, currentPage, lessonType, areaScore);
		}
	} else {
		if (flagCheckAnswerVocabulary == 0) {

			createBootboxDialog(MSG.E0037, MSG.E0031, settings);
		} else if (pract_test == 'test' && flagCheckAnswerVocabularyTest == 1) {
			createBootboxDialog(MSG.E0037, "Please input full vocabulary !",
					settings);
		} else {

			// case lesson type is Vocabulary update flagScenario = 1 and
			// flagVocabulary = 1
			flagScenario = 1;
			flagVocabulary = 1;

			// Call function score
			ajax(lessonInfoIndex, currentPage, lessonType, areaScore);
		}
	}

}

/**
 * show data on view
 * 
 * @param result
 */
function setDateOnView(result) {

	var stringClass = result.stringClass;

	// Set endTime
	endTime = result.endTime;

	// Set saveScore
	if (result.areaScore == 'scenario') {
		saveScoreScenario = result.score;
	} else {
		saveScoreVocabulary = result.score;
	}

	if (result.practiceMemoryScore != null) {
		$('.pra-memory-per').html(
				result.practiceMemoryScore + "%" + "("
						+ $('.practiceMemoryConditionHidden').val() + "%)");
	} else {
		$('.pra-memory-per').html(
				"0%" + "(" + $('.practiceMemoryConditionHidden').val() + "%)");
	}

	if (result.practiceWritingScore != null) {
		$('.pra-write-per').html(
				result.practiceWritingScore + "%" + "("
						+ $('.practiceWritingConditionHidden').val() + "%)");
	} else {
		$('.pra-write-per').html(
				"0%" + "(" + $('.practiceWritingConditionHidden').val() + "%)");
	}

	if (result.practiceConversationScore != null) {
		$('.pra-conver-per').html(
				result.practiceConversationScore + "%" + "("
						+ $('.practiceConversationConditionHidden').val()
						+ "%)");
	} else {
		$('.pra-conver-per').html(
				"0%" + "(" + $('.practiceConversationConditionHidden').val()
						+ "%)");
	}

	if (result.testMemoryScore != null) {
		$('.test-memory-per').html(
				result.testMemoryScore + "%" + "("
						+ $('.testMemoryCondition').val() + "%)");
	} else {
		$('.test-memory-per').html(
				"0%" + "(" + $('.testMemoryCondition').val() + "%)");
	}

	if (result.testWritingScore != null) {
		$('.test-write-per').html(
				result.testWritingScore + "%" + "("
						+ $('.testWritingCondition').val() + "%)");
	} else {
		$('.test-write-per').html(
				"0%" + "(" + $('.testWritingCondition').val() + "%)");
	}

	if (result.testConversationScore != null) {
		$('.test-conver-per').html(
				result.testConversationScore + "%" + "("
						+ $('.testConversationCondition').val() + "%)");
	} else {
		$('.test-conver-per').html(
				"0%" + "(" + $('.testConversationCondition').val() + "%)");
	}

	var htmlContent = "";

	// get result value
	var listOutCome = result.listOutCome;
	htmlContent += '<table class="table table-striped">';
	htmlContent += '<tbody>';
	var maxColumn = 7;
	var index = 0;
	for (var int = 0; int < listOutCome.length; int++) {
		var listIndexError = listOutCome[int].listIndexError;
		var questionId = listOutCome[int].questionId;
		var question = listOutCome[int].question;

		if (listIndexError.length > 0) {

			// question
			htmlContent += '<tr>';
			htmlContent += '<td><b>' + (++index) + '</b></td>';
			htmlContent += '<td><b>Model Answer:</b></td>';
			if (question.indexOf(";") != -1) {
				question = question.substring(0, question.length - 1);
				question = question.split(";");
				if (maxColumn < question.length) {
					maxColumn = question.length;
				}
				for (var i = 0; i < question.length; i++) {
					htmlContent += '<td>' + question[i] + '</td>';
				}
				for (var int2 = 0; int2 < maxColumn - question.length; int2++) {
					htmlContent += '<td></td>';
				}
			} else {
				htmlContent += '<td>' + question + '</td>';
			}

			// answer
			var answer = listOutCome[int].answer;
			htmlContent += '</tr>';
			htmlContent += '<tr>';
			htmlContent += '<td></td>';
			htmlContent += '<td><b>Your Answer :</b></td>';
			if (answer.indexOf(";") != -1) {
				answer = answer.substring(0, answer.length - 1);
				answer = answer.split(";");
				for (var i2 = 0; i2 < answer.length; i2++) {
					var value = answer[i2];
					if (jQuery.inArray(i2, listIndexError) != -1) {
						htmlContent += '<td><span class="red">' + value
								+ '</span></td>';
					} else {
						htmlContent += '<td>' + value + '</td>';
					}
				}
				for (var int2 = 0; int2 < maxColumn - answer.length; int2++) {
					htmlContent += '<td></td>';
				}
			} else {
				if (listIndexError.length > 0) {
					htmlContent += '<td><span class="red">' + answer
							+ '</span></td>';
				} else {
					htmlContent += '<td>' + answer + '</td>';
				}
			}
			htmlContent += '</tr>';
		}
		setErrIncon(questionId, listIndexError, stringClass);

	}
	htmlContent += '</tbody></table>';
	htmlContent += "<span><h3><b>Your's score:</b>   " + result.score
			+ "</h3></span>";

	flagAvgScenario = result.flagAvgScenario;
	setScenarioVocabulary(stringClass, 'memory', 'flagScenario', flagAvgScenario);
	setScenarioVocabulary(stringClass, 'memory', 'scoreScenario', result.score);
	setScenarioVocabulary(stringClass, 'memory', 'currentIdRecordScenario', result.currentIdRecordScenario);

	if (flagAvgScenario && $('#practice_test').val() == 'test') {
		$('.score_scenario_test_' + getCurrentMode(stringClass)).addClass('invisible');
	}

	flagAvgVocabulary = result.flagAvgVocabulary;
	setScenarioVocabulary(stringClass, 'memoryVocabulary', 'flagVocabulary', flagAvgVocabulary);
	setScenarioVocabulary(stringClass, 'memoryVocabulary', 'scoreVocabulary', result.score);
	setScenarioVocabulary(stringClass, 'memoryVocabulary', 'currentIdRecordVocabulary', result.currentIdRecordVocabulary);

	if (flagAvgVocabulary && $('#practice_test').val() == 'test') {
		$('.score_vocabulary_test_' + getCurrentMode(stringClass)).addClass('invisible');
	}

	var settings = {
		closeButton : false,
		callbackSuccess : function() {
			var classValue = stringClass.replace('Vocabulary','');
			var flagAvgScenario = getScenarioVocabulary(classValue, 'memory', 'flagScenario');
			var flagAvgVocabulary = getScenarioVocabulary(classValue + 'Vocabulary', 'memoryVocabulary', 'flagVocabulary');

			// Go to next page (when scored Scenario and Vocabulary)
			if ($('#lessonType').val() == 1) {
				if (flagAvgVocabulary == "true") {
					nextPageAjax(classValue, endTime);
				}
			} else {
				if (flagAvgVocabulary == "true" && flagAvgScenario == "true") {
					nextPageAjax(classValue, endTime);
				}
			}
		},
		hideClass : "btn-primary"
	};
	createBootboxDialog(MSG.E0037, htmlContent, settings);
}

/**
 * Event next page by ajax
 */
function nextPageAjax(classValue, endTime) {
	var indexPage;
	var limitPage;
	// Case lesson type is senario
	if ($('#lessonType').val() == "0") {
		// Case practice
		if ($("#practice_test").val() == "practice") {
			// Case memory
			if (memoryWritingConversation == "memory") {
				indexPage = parseInt(getIndexPage($('.numberPracticeMemory')
						.find('.numberPractice').html()));
				limitPage = parseInt(getLimitPage($('.numberPracticeMemory')
						.find('.numberPractice').html()));

				if (indexPage == limitPage || indexPage % limitPage == 0) {

					var settings = {
						callbackSuccess : function() {

							indexPage += 1;
							pageIndexPracticeMemory = indexPage;
							pageIndexPracticeMemoryVocabulary = indexPage;
							indexPractice = 0;
							page = 0;
							flagAvgScenario = false;
							flagAvgVocabulary = false;
							// Show loading for reload page
							remode();
							$('#loader').show();
						},
						closeButton : false
					};

					createBootboxDialog(MSG.E0037, MSG.E0033, settings);

				} else {

					indexPage += 1;
					pageIndexPracticeMemory = indexPage;
					pageIndexPracticeMemoryVocabulary = indexPage;

					indexPractice = (indexPage % limitPage) - 1 ;
					page = (indexPage % limitPage) - 1 ;
					if(indexPractice < 0){
						indexPractice = limitPage - 1;
						page = limitPage - 1;
					}

					$('.numberPracticeMemory').removeClass("invisible");
					$('.numberPracticeWriting').addClass("invisible");
					$('.numberPracticeConversation').addClass("invisible");
					$('.numberPracticeMemoryVocabulary').removeClass(
							"invisible");
					$('.numberPracticeWritingVocabulary').addClass("invisible");
					$('.numberPracticeConversationVocabulary').addClass(
							"invisible");

					$('.numberPracticeMemory').html(
							'<div class="numberPractice">' + indexPage + '/'
									+ limitPage + '</div>');
					$('.numberPracticeMemoryVocabulary').html(
							'<div class="numberPractice">' + indexPage + '/'
									+ limitPage + '</div>');

					toggleLessonPractice();

					// update flags
					flagScenario = 0;
					flagVocabulary = 0;

					// update
					flagAvgScenario = false;
					flagAvgVocabulary = false;

					// rescore
					saveScoreScenario = -1;
					saveScoreVocabulary = -1;
				}
				// Case writing
			} else if (memoryWritingConversation == "writing") {
				indexPage = parseInt(getIndexPage($('.numberPracticeWriting')
						.find('.numberPractice').html()));
				limitPage = parseInt(getLimitPage($('.numberPracticeWriting')
						.find('.numberPractice').html()));

				if (indexPage == limitPage || indexPage % limitPage == 0) {

					var settings = {
						callbackSuccess : function() {

							indexPage += 1;
							pageIndexPracticeWriting = indexPage;
							pageIndexPracticeWritingVocabulary = indexPage;
							indexPractice = 0;
							page = 0;
							flagAvgScenario = false;
							flagAvgVocabulary = false;
							// Show loading for reload page
							remode();
							$('#loader').show();
						},
						closeButton : false
					};

					createBootboxDialog(MSG.E0037, MSG.E0033, settings);

				} else {

					indexPage += 1;
					pageIndexPracticeWriting = indexPage;
					pageIndexPracticeWritingVocabulary = indexPage;

					// Set starTime after = endTime befor
					$('#startTime').val(endTime);

					indexPractice = (indexPage % limitPage) - 1 ;
					page = (indexPage % limitPage) - 1 ;
					if(indexPractice < 0){
						indexPractice = limitPage - 1;
						page = limitPage - 1;
					}

					$('.numberPracticeMemory').addClass("invisible");
					$('.numberPracticeWriting').removeClass("invisible");
					$('.numberPracticeConversation').addClass("invisible");
					$('.numberPracticeMemoryVocabulary').addClass("invisible");
					$('.numberPracticeWritingVocabulary').removeClass(
							"invisible");
					$('.numberPracticeConversationVocabulary').addClass(
							"invisible");

					$('.numberPracticeWriting').html(
							'<div class="numberPractice">' + indexPage + '/'
									+ limitPage + '</div>');
					$('.numberPracticeWritingVocabulary').html(
							'<div class="numberPractice">' + indexPage + '/'
									+ limitPage + '</div>');

					toggleLessonPractice();

					// update flags
					flagScenario = 0;
					flagVocabulary = 0;

					// update
					flagAvgScenario = false;
					flagAvgVocabulary = false;

					// rescore
					saveScoreScenario = -1;
					saveScoreVocabulary = -1;
				}
				// Case speech
			} else {
				indexPage = parseInt(getIndexPage($(
						'.numberPracticeConversation').find('.numberPractice')
						.html()));
				limitPage = parseInt(getLimitPage($(
						'.numberPracticeConversation').find('.numberPractice')
						.html()));

				if (indexPage == limitPage || indexPage % limitPage == 0) {

					var settings = {
						callbackSuccess : function() {

							indexPage += 1;
							pageIndexPracticeConversation = indexPage;
							pageIndexPracticeConversationVocabulary = indexPage;
							indexPractice = 0;
							page = 0;
							flagAvgScenario = false;
							flagAvgVocabulary = false;
							// Show loading for reload page
							remode();
							$('#loader').show();
						},
						closeButton : false
					};

					createBootboxDialog(MSG.E0037, MSG.E0033, settings);

				} else {

					indexPage += 1;
					pageIndexPracticeConversation = indexPage;
					pageIndexPracticeConversationVocabulary = indexPage;

					// Set starTime after = endTime befor
					$('#startTime').val(endTime);

					indexPractice = (indexPage % limitPage) - 1 ;
					page = (indexPage % limitPage) - 1 ;
					if(indexPractice < 0){
						indexPractice = limitPage - 1;
						page = limitPage - 1;
					}

					$('.numberPracticeMemory').addClass("invisible");
					$('.numberPracticeWriting').addClass("invisible");
					$('.numberPracticeConversation').removeClass("invisible");
					$('.numberPracticeMemoryVocabulary').addClass("invisible");
					$('.numberPracticeWritingVocabulary').addClass("invisible");
					$('.numberPracticeConversationVocabulary').removeClass(
							"invisible");

					$('.numberPracticeConversation').html(
							'<div class="numberPractice">' + indexPage + '/'
									+ limitPage + '</div>');
					$('.numberPracticeConversationVocabulary').html(
							'<div class="numberPractice">' + indexPage + '/'
									+ limitPage + '</div>');

					toggleLessonPractice();

					// update flags
					flagScenario = 0;
					flagVocabulary = 0;

					// update
					flagAvgScenario = false;
					flagAvgVocabulary = false;

					// rescore
					saveScoreScenario = -1;
					saveScoreVocabulary = -1;
				}
			}
			// Case test
		} else {

			var settings = {
				callbackSuccess : function() {

					indexPractice = 0;
					page = 0;
					flagAvgScenario = false;
					flagAvgVocabulary = false;
					// Show loading for reload page
					remode();
					$('#loader').show();
				},
				closeButton : false
			};

			createBootboxDialog(MSG.E0037, MSG.E0033, settings);

		}
		// Set starTime after = endTime befor
		setScenarioVocabulary(classValue, 'memory', 'startTime', endTime);
		setScenarioVocabulary(classValue + 'Vocabulary', 'memoryVocabulary', 'startTime', endTime);

		// Case lesson type is Vocabulary
	} else {

		// Case practice
		if ($("#practice_test").val() == "practice") {
			// Case memory
			if (memoryWritingConversation == "memory") {
				indexPage = parseInt(getIndexPage($(
						'.numberPracticeMemoryVocabulary').find(
						'.numberPractice').html()));
				limitPage = parseInt(getLimitPage($(
						'.numberPracticeMemoryVocabulary').find(
						'.numberPractice').html()));

				if (indexPage == limitPage || indexPage % limitPage == 0) {

					var settings = {
						callbackSuccess : function() {

							indexPage += 1;
							pageIndexPracticeMemoryVocabulary = indexPage;
							indexPractice = 0;
							page = 0;
							flagAvgVocabulary = false;
							// Show loading for reload page
							remode();
							$('#loader').show();
						},
						closeButton : false
					};

					createBootboxDialog(MSG.E0037, MSG.E0033, settings);

				} else {

					indexPage += 1;
					pageIndexPracticeMemoryVocabulary = indexPage;
					
					// Set starTime after = endTime befor
					$('#startTime').val(endTime);

					indexPractice = (indexPage % limitPage) - 1 ;
					page = (indexPage % limitPage) - 1 ;
					if(indexPractice < 0){
						indexPractice = limitPage - 1;
						page = limitPage - 1;
					}

					$('.numberPracticeMemoryVocabulary').removeClass(
							"invisible");
					$('.numberPracticeWritingVocabulary').addClass("invisible");
					$('.numberPracticeConversationVocabulary').addClass(
							"invisible");

					$('.numberPracticeMemoryVocabulary').html(
							'<div class="numberPractice">' + indexPage + '/'
									+ limitPage + '</div>');

					toggleLessonPractice();

					// update flags
					flagScenario = 0;
					flagVocabulary = 0;

					// update
					flagAvgScenario = false;
					flagAvgVocabulary = false;

					// rescore
					saveScoreScenario = -1;
					saveScoreVocabulary = -1;
				}
				// Case writing
			} else if (memoryWritingConversation == "writing") {
				indexPage = parseInt(getIndexPage($(
						'.numberPracticeWritingVocabulary').find(
						'.numberPractice').html()));
				limitPage = parseInt(getLimitPage($(
						'.numberPracticeWritingVocabulary').find(
						'.numberPractice').html()));

				if (indexPage == limitPage || indexPage % limitPage == 0) {

					var settings = {
						callbackSuccess : function() {

							indexPage += 1;
							pageIndexPracticeWritingVocabulary = indexPage;
							indexPractice = 0;
							page = 0;
							flagAvgVocabulary = false;
							// Show loading for reload page
							remode();
							$('#loader').show();
						},
						closeButton : false
					};

					createBootboxDialog(MSG.E0037, MSG.E0033, settings);

				} else {

					indexPage += 1;
					pageIndexPracticeWritingVocabulary = indexPage;

					// Set starTime after = endTime befor
					$('#startTime').val(endTime);

					indexPractice = (indexPage % limitPage) - 1 ;
					page = (indexPage % limitPage) - 1 ;
					if(indexPractice < 0){
						indexPractice = limitPage - 1;
						page = limitPage - 1;
					}

					$('.numberPracticeMemoryVocabulary').addClass("invisible");
					$('.numberPracticeWritingVocabulary').removeClass(
							"invisible");
					$('.numberPracticeConversationVocabulary').addClass(
							"invisible");

					$('.numberPracticeWritingVocabulary').html(
							'<div class="numberPractice">' + indexPage + '/'
									+ limitPage + '</div>');

					toggleLessonPractice();

					// update flags
					flagScenario = 0;
					flagVocabulary = 0;

					// update
					flagAvgScenario = false;
					flagAvgVocabulary = false;

					// rescore
					saveScoreScenario = -1;
					saveScoreVocabulary = -1;
				}
				// Case speech
			} else {
				indexPage = parseInt(getIndexPage($(
						'.numberPracticeConversationVocabulary').find(
						'.numberPractice').html()));
				limitPage = parseInt(getLimitPage($(
						'.numberPracticeConversationVocabulary').find(
						'.numberPractice').html()));

				if (indexPage == limitPage || indexPage % limitPage == 0) {

					var settings = {
						callbackSuccess : function() {

							indexPage += 1;
							pageIndexPracticeConversationVocabulary = indexPage;
							indexPractice = 0;
							page = 0;
							flagAvgVocabulary = false;
							// Show loading for reload page
							remode();
							$('#loader').show();
						},
						closeButton : false
					};

					createBootboxDialog(MSG.E0037, MSG.E0033, settings);

				} else {

					indexPage += 1;
					pageIndexPracticeConversationVocabulary = indexPage;

					// Set starTime after = endTime befor
					$('#startTime').val(endTime);

					indexPractice = (indexPage % limitPage) - 1 ;
					page = (indexPage % limitPage) - 1 ;
					if(indexPractice < 0){
						indexPractice = limitPage - 1;
						page = limitPage - 1;
					}

					$('.numberPracticeMemoryVocabulary').addClass("invisible");
					$('.numberPracticeWritingVocabulary').addClass("invisible");
					$('.numberPracticeConversationVocabulary').removeClass(
							"invisible");

					$('.numberPracticeConversationVocabulary').html(
							'<div class="numberPractice">' + indexPage + '/'
									+ limitPage + '</div>');

					toggleLessonPractice();

					// update flags
					flagScenario = 0;
					flagVocabulary = 0;

					// update
					flagAvgScenario = false;
					flagAvgVocabulary = false;

					// rescore
					saveScoreScenario = -1;
					saveScoreVocabulary = -1;
				}
			}
			// Case test
		} else {

			var settings = {
				callbackSuccess : function() {

					indexPractice = 0;
					page = 0;
					flagAvgVocabulary = false;
					// Show loading for reload page
					remode();
					$('#loader').show();
				},
				closeButton : false
			};

			createBootboxDialog(MSG.E0037, MSG.E0033, settings);

		}
		// Set starTime after = endTime befor
		setScenarioVocabulary(classValue + 'Vocabulary', 'memoryVocabulary', 'startTime', endTime);
	}
}

/**
 * Event next page
 */
function nextPage() {
	var limit = parseInt($('#pageLimitClient').val());
	var type = $('#typeScenario').val();
	var practiceNo = parseInt($('#practiceNo').val());

	if (limit > (indexPractice + 1) && type != "") {

		practiceNo += 1;
		// Set new practiceNo = old practiceNo + 1
		$('#practiceNo').val(practiceNo);

		// Set starTime after = endTime befor
		$('#startTime').val(endTime);

		page += 1;
		// $('.numberPractice').html((indexPractice + 2) + '/' + limit);
		$('.numberPractice').html(practiceNo + '/' + limit);

		indexPractice++;
		toggleLessonPractice();
      
		// update flags
		flagScenario = 0;
		flagVocabulary = 0;

		// update
		flagAvgScenario = false;
		flagAvgVocabulary = false;

		// rescore
		saveScoreScenario = -1;
		saveScoreVocabulary = -1;

	} else {
		var flagSucess = false;
		if ($('#lessonType').val() == 1) {
			if (flagAvgVocabulary) {
				flagSucess = true;
			}
		} else {
			if (flagAvgScenario && flagAvgVocabulary) {
				flagSucess = true;
			}
		}
		if (flagSucess) {
			var settings = {
				callbackSuccess : function() {

					practiceNo += 1;
					// Set new practiceNo = old practiceNo + 1
					$('#practiceNo').val(practiceNo);

					// Show loading for reload page
					remode();
					$('#loader').show();
				},
				closeButton : false
			};

			createBootboxDialog(MSG.E0037, MSG.E0033, settings);
		}
	}

}

/**
 * Refresh mode learning
 */
function remode() {

	// rescore
	saveScore = -1;

	var mode = memoryWritingConversation;
	// var mode = $("#memo_writ_spee").val();
	if (mode == "memory") {
		$("#memo_writ_spee").val("memory");
		submitFormByMode();
	} else if (mode == "writing") {
		$("#memo_writ_spee").val("writing");
		submitFormByMode();
	} else if (mode == "speech") {
		$("#memo_writ_spee").val("speech");
		submitFormByMode();
	} else {
		$("#memo_writ_spee").val("memory");
		submitFormByMode();
	}
}

// set icon in list question
function setErrIncon(questionId, listIndexError, stringClass) {
	$('.' + stringClass).find('#' + questionId).find('.glyphicon').remove();
	if (listIndexError.length > 0) {
		if (!$('.' + stringClass).find('#' + questionId + " span").hasClass('glyphicon-remove')) {
			$('.' + stringClass).find('#' + questionId).append(
					'<span class="glyphicon glyphicon-remove"></span>');
		}
	} else {
		if (!$('.' + stringClass).find('#' + questionId + " span").hasClass('glyphicon-ok')) {
			$('.' + stringClass).find('#' + questionId).append(
					'<span class="glyphicon glyphicon-ok"></span>');
		}
	}
}

// get string for check practice or test
function getStringCheck(scenarioOrVocabulary) {
	var modeString;
	var practiceOrTest;
	if (memoryWritingConversation == "memory") {
		modeString = "Memory";
	} else if (memoryWritingConversation == "writing") {
		modeString = "Writing";
	} else {
		modeString = "Conversation";
	}

	if ($("#practice_test").val() == "practice") {
		practiceOrTest = "practice";
	} else {
		practiceOrTest = "test";
	}
	var stringReturn = practiceOrTest + modeString + scenarioOrVocabulary;
	return stringReturn;
}

//Get index page by string
function getIndexPage(numberString) {
	var number = numberString.split("/");
	return number[0];
}

// Get limit page by string
function getLimitPage(numberString) {
	var number = numberString.split("/");
	return number[1];
}

/**
 * get current mode
 * 
 * @param stringClass
 * @return current mode
 */
function getCurrentMode(stringClass) {
	stringClass = stringClass.replace("test", "").replace("Vocabulary", "");
	if (stringClass == 'Conversation') {
		return 'speech';
	} else {
		return stringClass.toLowerCase();
	}
}

/**
 * get value scanario or vocabulary
 * 
 * @param classValue
 * @param memory
 * @param returnClass
 */
function getScenarioVocabulary(classValue, memory, returnClass) {
	var value = "0";
	$('.' + classValue).find('.' + memory).each(function(index) {
		if (!$(this).hasClass('invisible')) {
			value = $(this).find('.' + returnClass).val();
			return false;
		}
	});
	return value;
}

/**
 * set value scanario or vocabulary
 * 
 * @param classValue
 * @param memory
 * @param returnClass
 * @param value
 */
function setScenarioVocabulary(classValue, memory, returnClass, value) {
	$('.' + classValue).find('.' + memory).each(function(index) {
		if (!$(this).hasClass('invisible')) {
			$(this).find('.' + returnClass).val(value);
			return false;
		}
	});
}