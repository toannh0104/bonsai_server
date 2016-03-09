var lessonId;
var currentLessonInfoIndex;
var lessonInfoId;
// Start with lesson info index 0
var currentLessonIndex = 0;
var LESSON = 'lesson';
var SECURITY = 'security';
var memoryWritingConversation = "";


$(document).ready(function() {

					// Toggle lesson
					toggleLesson();

					// Set color for text practice when on load
					if ($("#practice_test").val() == "test") {
						$(".lbtest").css('color', 'red');
						$('.lbpractice').css('color', '#0088cc');
					} else {
						$(".lbtest").css('color', '#0088cc');
						$('.lbpractice').css('color', 'red');
					}

					// Set color for text mode when on load
					toggleColor($("#memo_writ_spee").val());

					$("#practice_test").val("practice");
					/**
					 * event for click mode memory
					 */
					$('.mode-memory').click(
									function() {
										setColor('red', '#0088cc', '#0088cc');
										memoryWritingConversation = "memory";
										/*$('#memo_writ_spee').val("memory");*/
										// Check befor call ajax
										if(($('.practiceMemoryVocabulary').html() != "" && $("#practice_test").val() == "practice") || ($('.testMemoryVocabulary').html() != "" && $("#practice_test").val() == "test")){
											setDisplayLessonType($('#lessonType').val(), $("#practice_test").val(), 'memory', false);
										// Call ajax
										} else {
											if (!flagCheckShowConversation
													|| (flagCheckShowConversation && $(
															"#memo_writ_spee")
															.val() != 'memory')) {
												$("#practiceNo").val(1);
											}
											flagCheckShowConversation = false;
											$('#modeScenario').val(1);
											callMemoryWritingConversation(memoryWritingConversation, $("#practice_test").val());
										}
									});

					/**
					 * event for click mode write
					 */
					$('.mode-write')
							.click(
									function() {
										setColor('#0088cc', 'red', '#0088cc');
										memoryWritingConversation = "writing";
										/*$('#memo_writ_spee').val("writing");*/
										// Check befor call ajax
										if(($('.practiceWritingVocabulary').html() != "" && $("#practice_test").val() == "practice") || ($('.testWritingVocabulary').html() != "" && $("#practice_test").val() == "test")){
											setDisplayLessonType($('#lessonType').val(), $("#practice_test").val(), 'writing', false);
										// Call ajax
										} else {
											if (!flagCheckShowConversation
													|| (flagCheckShowConversation && $(
															"#memo_writ_spee")
															.val() != 'writing')) {
												$("#practiceNo").val(1);
											}
											flagCheckShowConversation = false;
											$('#modeScenario').val(2);
											callMemoryWritingConversation(memoryWritingConversation, $("#practice_test").val());
										}
									});

					/**
					 * event for click mode speech
					 */
					$('.mode-speech')
							.click(
									function() {
										setColor('#0088cc', '#0088cc', 'red');
										memoryWritingConversation = "speech";
										/*$('#memo_writ_spee').val("speech");*/
										// Check befor call ajax
										if(($('.practiceConversationVocabulary').html() != "" && $("#practice_test").val() == "practice") || ($('.testConversationVocabulary').html() != "" && $("#practice_test").val() == "test")){
											setDisplayLessonType($('#lessonType').val(), $("#practice_test").val(), 'speech', false);
										// Call ajax
										} else {
											if (!flagCheckShowConversation
													|| (flagCheckShowConversation && $(
															"#memo_writ_spee")
															.val() != 'speech')) {
												$("#practiceNo").val(1);
											}
											flagCheckShowConversation = false;
											$('#modeScenario').val(3);
											callMemoryWritingConversation(memoryWritingConversation, $("#practice_test").val());
										}
									});

					/**
					 * event for click mode practice
					 */
					$('.lbpractice').click(
									function() {
										if ($("#practice_test").val() == "test") {
											var settings = {
												callbackSuccess : function() {
													$("#practiceNo").val(1);
													// Set practice_test =
													// "practice"
													$("#practice_test").val("practice");

													// Set color for text practice when on reload
													if ($("#practice_test").val() == "test") {
														$(".lbtest").css('color', 'red');
														$('.lbpractice').css('color', '#0088cc');
													} else {
														$(".lbtest").css('color', '#0088cc');
														$('.lbpractice').css('color', 'red');
													}

													// Close area test
													setInvisibleClass('test');

													var value = '.practice';
													if (memoryWritingConversation == 'speech') {
														value += 'Conversation';
													} else {
														value += upperCaseFirstLetter(memoryWritingConversation);
													}

													if($(value).html() == "") {
														remode();
														$('#loader').show();
														setDisplayLessonType($('#lessonType').val(), 'practice', memoryWritingConversation, true);
													} else {
														setDisplayLessonType($('#lessonType').val(), 'practice', memoryWritingConversation, false);
													}

													/*remode();*/
													// Show loading for reload
													// page
													/*$('#loader').show();*/
												},
												closeButton : false
											};
											createBootboxDialog(MSG.E0037,
													MSG.E0038, settings);
										}
									});

					/**
					 * event for click mode test
					 */
					$('.lbtest').click(
							function() {
								if ($("#practice_test").val() == "practice"
										|| $("#practice_test").val() == "") {
									checkSwitchToTest();
								}
							});
				});

/**
 * Handle event when memory/writing/conversation link is clicked
 */
function callMemoryWritingConversation(mode, practiceTest) {
	var modeScenario = $('#modeScenario').val();
	var memo_writ_spee = $('#memo_writ_spee').val();
	var submitFlag = false;
	$('.modeScore').addClass('invisible');
	if (memo_writ_spee == mode) {
		if (modeScenario != "") {
			$('.modeScenarioNull').addClass('invisible');
			if ($('#backtoScenario').val() == "true") {
				submitFlag = true;
			}
		} else {
			$('.modeScenarioNull').removeClass('invisible');
			$('.modeScenarioNotNull').addClass('invisible');
		}
		$('#backtoScenario').val("true");
	} else {
		$('.modeScenarioNull').addClass('invisible');
		if (practiceTest == 'practice') {
			$('.score_scenario').removeClass('invisible');
			$('.score_vocabulary').removeClass('invisible');
		} else {
			$('.score_scenario_test_' + mode).removeClass('invisible');
			$('.score_vocabulary_test_' + mode).removeClass('invisible');
		}
		submitFlag = true;
	}
	if (submitFlag == true) {
		// Set color for text mode memory when on click
		toggleColor(mode);
		$("#memo_writ_spee").val(mode);
		submitFormByMode();
	}
}

/**
 * Submit form by mode memory or writing or speech
 */
function submitFormByMode() {

	var memo_writ_spee = memoryWritingConversation;

	// Set value type scenario or vocabulary
	var scena_voca = $("#scenario_vocabulary").val();

	var postStringData = "var postData = {";

	// Set value type practice or test
	var pract_test = $("#practice_test").val();
	if (pract_test == "") {
		pract_test = "practice";
		$("#practice_test").val("practice");
	}

	if (scena_voca == "")
		scena_voca = "scenario";

	// Type = memory when memo_writ_spee not set
	if (memo_writ_spee == "")
		memo_writ_spee = "memory";

	// information about lesson.
	postStringData += '"languageId" : ' + '"' + $('#languageId').val() + '"';
	postStringData += ",";
	postStringData += '"lessonNo" : ' + '"' + $('#lessonNo').val() + '"';
	postStringData += ",";
	postStringData += '"lessonType" : ' + '"' + $('#lessonType').val() + '"';
	postStringData += ",";
	postStringData += '"practiceTest" : ' + '"' + pract_test + '"';
	postStringData += ",";
	postStringData += '"lessonInfoId" : ' + '"' + $('#lessonInfoId').val() + '"';
	postStringData += ",";
	postStringData += '"memoryWritingConversation" : ' + '"' + memo_writ_spee + '"';
	postStringData += ",";
	postStringData += '"modeLanguage" : ' + '"' + getModeLanguage(parseInt($("#lessonNo").val())) + '"';
	postStringData += ",";
	postStringData += '"pageIndexPracticeMemory" : ' + '"' + pageIndexPracticeMemory + '"';
	postStringData += ",";
	postStringData += '"pageIndexPracticeWriting" : ' + '"' + pageIndexPracticeWriting + '"';
	postStringData += ",";
	postStringData += '"pageIndexPracticeConversation" : ' + '"' + pageIndexPracticeConversation + '"';
	postStringData += ",";
	postStringData += '"pageIndexPracticeMemoryVocabulary" : ' + '"' + pageIndexPracticeMemoryVocabulary + '"';
	postStringData += ",";
	postStringData += '"pageIndexPracticeWritingVocabulary" : ' + '"' + pageIndexPracticeWritingVocabulary + '"';
	postStringData += ",";
	postStringData += '"pageIndexPracticeConversationVocabulary" : ' + '"' + pageIndexPracticeConversationVocabulary + '"';

	// End postStringData
	postStringData += "}";

	// Evaluate javascript string to init post data
	eval(postStringData);

	$.ajax({
		url : url + "/client/lesson/practicetest",
		data : postData,
		type : "POST",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(result) {
			if (result.status == RESULT_STATUS_OK) {
				showPracticeTestView(result);
			} else {

			}
		}
	});
}

/**
 * Show practice test view
 * 
 * @param result
 */
function showPracticeTestView(result) {
	var indexPracticeMemory = result.pageIndexPracticeMemory;
	var indexPracticeWriting = result.pageIndexPracticeWriting;
	var indexPracticeConversation = result.pageIndexPracticeConversation;
	var indexPracticeMemoryVocabulary = result.pageIndexPracticeMemoryVocabulary;
	var indexPracticeWritingVocabulary = result.pageIndexPracticeWritingVocabulary;
	var indexPracticeConversationVocabulary = result.pageIndexPracticeConversationVocabulary;
	var memoryWritingConversation = result.memoryWritingConversation;
	var pageLimit = result.pageLimit;
	var practiceTest = result.practiceTest;
	var lessonType = result.lessonType;
	var htmlContentPracticeMemory = "";
	var htmlContentPracticeWriting = "";
	var htmlContentPracticeConversation = "";
	var htmlContentPracticeMemoryVocabulary = "";
	var htmlContentPracticeWritingVocabulary = "";
	var htmlContentPracticeConversationVocabulary = "";
	var htmlContentTestMemory = "";
	var htmlContentTestWriting = "";
	var htmlContentTestConversation = "";
	var htmlContentTestMemoryVocabulary = "";
	var htmlContentTestWritingVocabulary = "";
	var htmlContentTestConversationVocabulary = "";
	var htmlContentNumberPracticeMemory = "";
	var htmlContentNumberPracticeWriting = "";
	var htmlContentNumberPracticeConversation = "";
	var htmlContentNumberPracticeMemoryVocabulary = "";
	var htmlContentNumberPracticeWritingVocabulary = "";
	var htmlContentNumberPracticeConversationVocabulary = "";
	var htmlContentNumberTestMemory = "";
	var htmlContentNumberTestWriting = "";
	var htmlContentNumberTestConversation = "";
	var htmlContentNumberTestMemoryVocabulary = "";
	var htmlContentNumberTestWritingVocabulary = "";
	var htmlContentNumberTestConversationVocabulary = "";

	if(practiceTest == 'practice'){
		var resultList = createHtmlPracticeTest(result, memoryWritingConversation, lessonType);
		htmlContentPracticeMemory += resultList.htmlMemory;
		htmlContentPracticeWriting += resultList.htmlWriting;
		htmlContentPracticeConversation += resultList.htmlConversation;
		htmlContentNumberPracticeMemory = resultList.htmNumberMemory;
		htmlContentNumberPracticeWriting = resultList.htmlNumberWriting;
		htmlContentNumberPracticeConversation = resultList.htmlNumberConversation;
		
		// Vocabulary ------------------------------------------------------------------------------------------
		htmlContentPracticeMemoryVocabulary += resultList.htmlMemoryVocabulary;
		htmlContentPracticeWritingVocabulary += resultList.htmlWritingVocabulary;
		htmlContentPracticeConversationVocabulary += resultList.htmlConversationVocabulary;
		htmlContentNumberPracticeMemoryVocabulary = resultList.htmNumberMemoryVocabulary;
		htmlContentNumberPracticeWritingVocabulary = resultList.htmlNumberWritingVocabulary;
		htmlContentNumberPracticeConversationVocabulary = resultList.htmlNumberConversationVocabulary;

		setDisplayLessonType(0, 'practice', memoryWritingConversation, true);

		// Scenario
		if(memoryWritingConversation == 'memory'){
			$('.practiceMemory').html(htmlContentPracticeMemory);
			$('.numberPracticeMemory').html(htmlContentNumberPracticeMemory);

		} else if(memoryWritingConversation == 'writing'){
		    htmlContentPracticeWriting = '<div class="input_type_voca_'+ $('.lessonNo').val() +'" style="margin-left: 45%;color: #444;opacity: 0.5;">' + 
		    setTextIputType(getModeLanguage(parseInt( $('#lessonNo').val() ))) + '</div>'  + htmlContentPracticeWriting;
			$('.practiceWriting').html(htmlContentPracticeWriting);
			$('.numberPracticeWriting').html(htmlContentNumberPracticeWriting);

		} else {
			$('.practiceConversation').html(htmlContentPracticeConversation);
			$('.numberPracticeConversation').html(htmlContentNumberPracticeConversation);
		}

		// Vocabulary
		if(memoryWritingConversation == 'memory'){
			$('.practiceMemoryVocabulary').html(htmlContentPracticeMemoryVocabulary);
			$('.numberPracticeMemoryVocabulary').html(htmlContentNumberPracticeMemoryVocabulary);

		} else if(memoryWritingConversation == 'writing'){
		    htmlContentPracticeWritingVocabulary = '<div class="input_type_voca_'+ $('.lessonNo').val() +'" style="margin-left: 55%;color: #444;opacity: 0.5;">' + 
            setTextIputType(getModeLanguage(parseInt( $('#lessonNo').val() ))) + '</div>'  + htmlContentPracticeWritingVocabulary;
			$('.practiceWritingVocabulary').html(htmlContentPracticeWritingVocabulary);
			$('.numberPracticeWritingVocabulary').html(htmlContentNumberPracticeWritingVocabulary);

		} else {
			$('.practiceConversationVocabulary').html(htmlContentPracticeConversationVocabulary);
			$('.numberPracticeConversationVocabulary').html(htmlContentNumberPracticeConversationVocabulary);
		}

	// Case test
	} else {
		var resultList = createHtmlPracticeTest(result, memoryWritingConversation, lessonType);
		htmlContentTestMemory += resultList.htmlMemory;
		htmlContentTestWriting += resultList.htmlWriting;
		htmlContentTestConversation += resultList.htmlConversation;
		htmlContentNumberTestMemory = resultList.htmNumberMemory;
		htmlContentNumberTestWriting = resultList.htmlNumberWriting;
		htmlContentNumberTestConversation = resultList.htmlNumberConversation;

		// Vocabulary ------------------------------------------------------------------------------------------
		htmlContentTestMemoryVocabulary += resultList.htmlMemoryVocabulary;
		htmlContentTestWritingVocabulary += resultList.htmlWritingVocabulary;
		htmlContentTestConversationVocabulary += resultList.htmlConversationVocabulary;
		htmlContentNumberTestMemoryVocabulary = resultList.htmNumberMemoryVocabulary;
		htmlContentNumberTestWritingVocabulary = resultList.htmlNumberWritingVocabulary;
		htmlContentNumberTestConversationVocabulary = resultList.htmlNumberConversationVocabulary;

		setDisplayLessonType(0, 'test', memoryWritingConversation, true);

		// Scenario
		if(memoryWritingConversation == 'memory'){
			$('.testMemory').html(htmlContentTestMemory);
			$('.numberTestMemory').html(htmlContentNumberTestMemory);

		} else if(memoryWritingConversation == 'writing'){
			htmlContentTestWriting = '<div class="input_type_voca_'+ $('.lessonNo').val() +'" style="margin-left: 45%;color: #444;opacity: 0.5;">' + 
            setTextIputType(getModeLanguage(parseInt( $('#lessonNo').val() ))) + '</div>'  + htmlContentTestWriting;
			$('.testWriting').html(htmlContentTestWriting);
			$('.numberTestWriting').html(htmlContentNumberTestWriting);

		} else {
			$('.testConversation').html(htmlContentTestConversation);
			$('.numberTestConversation').html(htmlContentNumberTestConversation);
		}

		// Vocabulary
		if(memoryWritingConversation == 'memory'){
			$('.testMemoryVocabulary').html(htmlContentTestMemoryVocabulary);
			$('.numberTestMemoryVocabulary').html(htmlContentNumberTestMemoryVocabulary);

		} else if(memoryWritingConversation == 'writing'){
			htmlContentTestWritingVocabulary = '<div class="input_type_voca_'+ $('.lessonNo').val() +'" style="margin-left: 55%;color: #444;opacity: 0.5;">' + 
            setTextIputType(getModeLanguage(parseInt( $('#lessonNo').val() ))) + '</div>'  + htmlContentTestWritingVocabulary;
			$('.testWritingVocabulary').html(htmlContentTestWritingVocabulary);
			$('.numberTestWritingVocabulary').html(htmlContentNumberTestWritingVocabulary);

		} else {
			$('.testConversationVocabulary').html(htmlContentTestConversationVocabulary);
			$('.numberTestConversationVocabulary').html(htmlContentNumberTestConversationVocabulary);

		}
	}

	// Invisible all lesson info block
	$('.memory').addClass('invisible');

	// Invisible all lesson info block
	$('.memoryVocabulary').addClass('invisible');

	var currentPracticeMemory = getCurrentPage(indexPracticeMemory, pageLimit);
	var currentPracticeWriting = getCurrentPage(indexPracticeWriting, pageLimit);
	var currentPracticeConversation = getCurrentPage(indexPracticeConversation, pageLimit);
	var currentPracticeMemoryVocabulary = getCurrentPage(indexPracticeMemoryVocabulary, pageLimit);
	var currentPracticeWritingVocabulary = getCurrentPage(indexPracticeWritingVocabulary, pageLimit);
	var currentPracticeConversationVocabulary = getCurrentPage(indexPracticeConversationVocabulary, pageLimit);
	// Show current lesson info
	if (practiceTest == 'practice') {
		$('.practiceMemory').find('.memoryPage_' + currentPracticeMemory).removeClass('invisible');
		$('.practiceWriting').find('.memoryPage_' + currentPracticeWriting).removeClass('invisible');
		$('.practiceConversation').find('.memoryPage_' + currentPracticeConversation).removeClass('invisible');

		$('.practiceMemoryVocabulary').find('.memoryVocabularyPage_' + currentPracticeMemoryVocabulary).removeClass('invisible');
		$('.practiceWritingVocabulary').find('.memoryVocabularyPage_' + currentPracticeWritingVocabulary).removeClass('invisible');
		$('.practiceConversationVocabulary').find('.memoryVocabularyPage_' + currentPracticeConversationVocabulary).removeClass('invisible');
	} else {
		$('.testMemory').find('.memoryPage_' + 0).removeClass('invisible');
		$('.testWriting').find('.memoryPage_' + 0).removeClass('invisible');
		$('.testConversation').find('.memoryPage_' + 0).removeClass('invisible');

		$('.testMemoryVocabulary').find('.memoryVocabularyPage_' + 0).removeClass('invisible');
		$('.testWritingVocabulary').find('.memoryVocabularyPage_' + 0).removeClass('invisible');
		$('.testConversationVocabulary').find('.memoryVocabularyPage_' + 0).removeClass('invisible');
	}
	

	// Show current lesson info

}

/**
 * Toggle color
 * 
 * @param stringMode
 */
function toggleColor(stringMode) {

	// Set color for text mode memory
	if (stringMode == "memory") {

		setColor('red', '#0088cc', '#0088cc');
	}

	// Set color for text mode write
	if (stringMode == "writing") {

		setColor('#0088cc', 'red', '#0088cc');
	}

	// Set color for text mode speech
	if (stringMode == "speech") {

		setColor('#0088cc', '#0088cc', 'red');
	}
}

/**
 * Set color for text mode
 * 
 * @param color1
 * @param color2
 * @param color3
 */
function setColor(color1, color2, color3) {
	$('.mode-memory').css('color', color1);
	$('.mode-write').css('color', color2);
	$('.mode-speech').css('color', color3);
}

/**
 * Toggle lesson
 */
function toggleLesson() {

	// Invisible all lesson info block
	$('.lesson_info').addClass('invisible');
	$('.thescenario').addClass('invisible');
	$('.theLessonVocabulary').addClass('invisible');

	// Show current lesson info
	$('.lesson_info_' + currentLessonIndex).removeClass('invisible');
	$('.lessonScenario_' + currentLessonIndex).removeClass('invisible');
	$('.lessonVocabulary_' + currentLessonIndex).removeClass('invisible');
}

/**
 * Check condition for switch to test
 */
function checkSwitchToTest() {
	$.ajax({
		url : url + "/client/lesson/" + lessonId + "/" + lessonInfoId
				+ "/checkswitchtotest",
		type : "POST",
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(result) {
			if (result.status == RESULT_STATUS_OK) {
				if (result.result == "true") {
					var settings = {
						callbackSuccess : function() {
							$("#practiceNo").val(1);
							// Set practice_test = "test"
							$("#practice_test").val("test");

							// Set color for text practice when on reload
							if ($("#practice_test").val() == "test") {
								$(".lbtest").css('color', 'red');
								$('.lbpractice').css('color', '#0088cc');
							} else {
								$(".lbtest").css('color', '#0088cc');
								$('.lbpractice').css('color', 'red');
							}

							$("#practice_test").val("test");

							// Close area practice
							setInvisibleClass('practice');

							var value = '.test';
							if (memoryWritingConversation == 'speech') {
								value += 'Conversation';
							} else {
								value += upperCaseFirstLetter(memoryWritingConversation);
							}
							if ($('#lessonType').val() == 1) {
								value += 'Vocabulary';
							}
							if($(value).html() == "") {
								remode();
								$('#loader').show();
							}
							setDisplayLessonType($('#lessonType').val(), 'test', memoryWritingConversation, true);

							
							/*remode();*/
							
							// Show loading for reload page
							/*$('#loader').show();*/
						},
						closeButton : false
					};
					createBootboxDialog(MSG.E0037, MSG.E0039, settings);
				} else {
					$("#practice_test").val("practice");
					var msg = getMessage([ $('#lessonNo').val() ], MSG.E0035);
					var settings = {
						closeButton : false,
						hideClass : "btn-primary"
					};
					createBootboxDialog(MSG.E0037, msg, settings);
				}
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

/**
 * Go to specified lesson info
 * 
 * @param isNext
 */
function gotoLessonInfo(isNext) {

	if (isNext) {
		$.ajax({
			url : url + "/client/lesson/" + lessonId + "/" + lessonInfoId
					+ "/checkNextLessonInfo",
			type : "POST",
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success : function(result) {
				if (result.status == RESULT_STATUS_OK) {
					if (result.result == "true") {
						var form = document.forms["lessonForm"];
						form.action = url + "/client/lesson/" + lessonId + "/"
								+ currentLessonInfoIndex + "/next";
						form.submit();
					} else {
						var msg = getMessage([ $('#lessonNo').val() ],
								MSG.E0036);
						var settings = {
							closeButton : false,
							hideClass : "btn-primary"
						};
						createBootboxDialog(MSG.E0037, msg, settings);
					}
				} else {
					var settings = {
						closeButton : false,
						hideClass : "btn-primary"
					};
					createBootboxDialog(MSG.E0037, result.message, settings);
				}
			}
		});
	} else {
		if (parseInt(currentLessonInfoIndex) > 0) {
			var form = document.forms["lessonForm"];
			form.action = url + "/client/lesson/" + lessonId + "/"
					+ currentLessonInfoIndex + "/prev";
			form.submit();
		} else {
			var settings = {
				closeButton : false,
				hideClass : "btn-primary"
			};
			createBootboxDialog(MSG.E0037, MSG.E0034, settings);
		}
	}
}

/**
 * Show information about lesson
 */
function allLessonInfo() {
	var lessonNoHidden = $('.lessonNoHidden');
	var lessonMethodHidden = $('.lessonMethodHidden');
	lessonNoHidden.each(function(index) {
		var lessonNo = $(this).text();
		// declare lesson method
		var lessonMethod = '';
		// get method name
		lessonMethod = getMethodName(getModeLanguage(parseInt(lessonNo)));
		lessonMethodHidden.eq(index).text(lessonMethod);
	});

	var title = "All Lesson Info";
	var msg = $('.allLessonInfo').html();
	var settings = {
		closeButton : false,
		hideClass : "btn-primary"
	};
	createBootboxDialog(title, msg, settings);
}

/**
 * set display lesson tyoe
 * 
 * @param lessonType
 * @param practiceTest
 * @param mode
 */
function setDisplayLessonType(lessonType, practiceTest, mode, flagFistLoad) {
	var removeClass = [];
	var classMode = null;
	$('.modeScore').addClass('invisible');
	$('.memory').addClass('invisible');
	$('.memoryVocabulary').addClass('invisible');
	if (practiceTest == 'practice') {
		$('.score_scenario').removeClass('invisible');
		$('.score_vocabulary').removeClass('invisible');
	} else {
		$('.score_scenario_test_' + mode).removeClass('invisible');
		$('.score_vocabulary_test_' + mode).removeClass('invisible');
	}
	if (lessonType == 0) {
		if (mode == 'memory') {
			removeClass.push(practiceTest + 'Memory');
			removeClass.push(practiceTest + 'MemoryVocabulary');
			removeClass.push('number' + upperCaseFirstLetter(practiceTest) + 'Memory');
			removeClass.push('number' + upperCaseFirstLetter(practiceTest) + 'MemoryVocabulary');
		} else if (mode == 'writing') {
			removeClass.push(practiceTest + 'Writing');
			removeClass.push(practiceTest + 'WritingVocabulary');
			removeClass.push('number' + upperCaseFirstLetter(practiceTest) + 'Writing');
			removeClass.push('number' + upperCaseFirstLetter(practiceTest) + 'WritingVocabulary');
		} else if (mode == 'speech') {
			removeClass.push(practiceTest + 'Conversation');
			removeClass.push(practiceTest + 'ConversationVocabulary');
			removeClass.push('number' + upperCaseFirstLetter(practiceTest) + 'Conversation');
			removeClass.push('number' + upperCaseFirstLetter(practiceTest) + 'ConversationVocabulary');
		}
		classMode = [
				practiceTest + 'Memory',
				practiceTest + 'Writing',
				practiceTest + 'Conversation',
				practiceTest + 'MemoryVocabulary',
				practiceTest + 'WritingVocabulary',
				practiceTest + 'ConversationVocabulary',
				'number' + upperCaseFirstLetter(practiceTest) + 'Memory',
				'number' + upperCaseFirstLetter(practiceTest) + 'Writing',
				'number' + upperCaseFirstLetter(practiceTest) + 'Conversation',
				'number' + upperCaseFirstLetter(practiceTest) + 'MemoryVocabulary',
				'number' + upperCaseFirstLetter(practiceTest) + 'WritingVocabulary',
				'number' + upperCaseFirstLetter(practiceTest) + 'ConversationVocabulary' ];
	// vocabulary
	} else {
		if (mode == 'memory') {
			removeClass.push(practiceTest + 'MemoryVocabulary');
			removeClass.push('number' + upperCaseFirstLetter(practiceTest) + 'MemoryVocabulary');
		} else if (mode == 'writing') {
			removeClass.push(practiceTest + 'WritingVocabulary');
			removeClass.push('number' + upperCaseFirstLetter(practiceTest) + 'WritingVocabulary');
		} else if (mode == 'speech') {
			removeClass.push(practiceTest + 'ConversationVocabulary');
			removeClass.push('number' + upperCaseFirstLetter(practiceTest) + 'ConversationVocabulary');
		}
		classMode = [
				practiceTest + 'MemoryVocabulary',
				practiceTest + 'WritingVocabulary',
				practiceTest + 'ConversationVocabulary',
				'number' + upperCaseFirstLetter(practiceTest) + 'MemoryVocabulary',
				'number' + upperCaseFirstLetter(practiceTest) + 'WritingVocabulary',
				'number' + upperCaseFirstLetter(practiceTest) + 'ConversationVocabulary'];
	}

	setInvisibleClass(practiceTest);

	for (var i = 0; i < classMode.length; i++) {
		for (var j = 0; j < removeClass.length; j++) {
			var value = classMode[i];
			if (removeClass[j] == value) {
				var currentPage = 0;
				if (!flagFistLoad && value.indexOf('number') == -1) {
					var numberHtml = '.number' + upperCaseFirstLetter(value);
					if (numberHtml.indexOf('Vocabulary') == -1) {
						numberHtml += 'Vocabulary';
					}
					var indexPage = parseInt(getIndexPage($(numberHtml).find('.numberPractice').html()));
					var limitPage = parseInt(getLimitPage($(numberHtml).find('.numberPractice').html()));
					currentPage = getCurrentPage(indexPage, limitPage);

				}
				$('.' + value).find('.memoryPage_' + currentPage).removeClass('invisible');
				$('.' + value).find('.memoryVocabularyPage_' + currentPage).removeClass('invisible');
				$('.' + value).removeClass("invisible");
				break;
			}
		}
	}
	$('.modeScenarioNull').addClass("invisible");
	$('.modeScenarioNotNull').removeClass("invisible");

}

/**
 * upperCase
 * @param str
 * @returns str
 */
function upperCaseFirstLetter(str) {
	return str.replace(/\b./g, function(m) {
		return m.toUpperCase();
	});
}

/**
 * upperCase
 * @param str
 * @returns str
 */
function lowerCaseFirstLetter(str) {
	return str.replace(/\b./g, function(m) {
		return m.toLowerCase();
	});
}

/**
 * set add class is invisible
 * @param mode
 * @param practiceTest
 */
function setInvisibleClass(practiceTest) {
	var classMode = [
					practiceTest + 'Memory',
					practiceTest + 'Writing',
					practiceTest + 'Conversation',
					practiceTest + 'MemoryVocabulary',
					practiceTest + 'WritingVocabulary',
					practiceTest + 'ConversationVocabulary',
					'number' + upperCaseFirstLetter(practiceTest) + 'Memory',
					'number' + upperCaseFirstLetter(practiceTest) + 'MemoryVocabulary',
					'number' + upperCaseFirstLetter(practiceTest) + 'Writing',
					'number' + upperCaseFirstLetter(practiceTest) + 'WritingVocabulary',
					'number' + upperCaseFirstLetter(practiceTest) + 'Conversation',
					'number' + upperCaseFirstLetter(practiceTest) + 'ConversationVocabulary' ];

	for (var i = 0; i < classMode.length; i++) {
		$('.' + classMode[i]).addClass("invisible");
	}
}

/**
 * get current page
 * 
 * @param indexPage
 * @param pageLimit
 * @returns {Number}
 */
function getCurrentPage(indexPage, pageLimit) {
	if (indexPage == pageLimit || (indexPage % pageLimit) == 0) {
		return pageLimit - 1;
	} else {
		return (indexPage % pageLimit) - 1;
	}
}

/**
 * create Html Practice Test
 * 
 * @param result
 * @param memoryWritingConversation
 * @param lessonType
 * @returns listReslt
 */
function createHtmlPracticeTest(result, memoryWritingConversation, lessonType) {

	var htmlMemory = '';
	var htmlWriting = '';
	var htmlConversation = '';
	var htmNumberMemory = '';
	var htmlNumberWriting = '';
	var htmlNumberConversation = '';
	var htmlMemoryVocabulary = '';
	var htmlWritingVocabulary = '';
	var htmlConversationVocabulary = '';
	var htmNumberMemoryVocabulary = '';
	var htmlNumberWritingVocabulary = '';
	var htmlNumberConversationVocabulary = '';
	var starTime = result.startTime;
	var listResult = {};

	if (lessonType == 0) {
		for (var i = 0; i < result.scenarioList.length; i++) {
			htmlMemory += '<div class="memory memoryPage_' + i + '">';
			htmlWriting += '<div class="memory memoryPage_' + i + '">';
			htmlConversation += '<div class="memory memoryPage_' + i + '">';
			
			htmlMemory += '<input type="hidden" class="flagScenario" value=false>';
			htmlWriting += '<input type="hidden" class="flagScenario" value=false>';
			htmlConversation += '<input type="hidden" class="flagScenario" value=false>';
			
			htmlMemory += '<input type="hidden" class="scoreScenario" value="0">';
			htmlWriting += '<input type="hidden" class="scoreScenario" value="0">';
			htmlConversation += '<input type="hidden" class="scoreScenario" value="0">';
			
			if (memoryWritingConversation == 'memory') {
				htmlMemory += '<input type="hidden" class="startTime" value="' + starTime + '">';
				htmlMemory += '<input type="hidden" class="currentIdRecordScenario" value="0">';
			} else if (memoryWritingConversation == 'writing') {
				htmlWriting += '<input type="hidden" class="startTime" value="' + starTime + '">';
				htmlWriting += '<input type="hidden" class="currentIdRecordScenario" value="0">';
			} else {
				htmlConversation += '<input type="hidden" class="startTime" value="' + starTime + '">';
				htmlConversation += '<input type="hidden" class="currentIdRecordScenario" value="0">';
			}
			
			for (var j = 0; j < result.scenarioList[i].length; j++) {
				
				htmlMemory += '<div class="row">';
				htmlWriting += '<div class="row">';
				htmlConversation += '<div class="row">';
				
				var listResultMemory = createContentHtmlPracticeTest(result, memoryWritingConversation, i, j);
				htmlMemory += listResultMemory.htmlValue;
				htmNumberMemory = listResultMemory.htmlNumber;
				
				var listResultWriting = createContentHtmlPracticeTest(result, memoryWritingConversation, i, j);
				htmlWriting += listResultWriting.htmlValue;
				htmlNumberWriting = listResultWriting.htmlNumber;
				
				var listResultConversation = createContentHtmlPracticeTest(result, memoryWritingConversation, i, j);
				htmlConversation += listResultConversation.htmlValue;
				htmlNumberConversation = listResultConversation.htmlNumber;
				
				// close answer
				htmlMemory += '</div>';
				htmlWriting += '</div>';
				htmlConversation += '</div>';
				// close row
				htmlMemory += '</div>';
				htmlWriting += '</div>';
				htmlConversation += '</div>';
			}
			// close page
			htmlMemory += '</div>';
			htmlWriting += '</div>';
			htmlConversation += '</div>';
		}
		listResult.htmlMemory = htmlMemory;
		listResult.htmlWriting = htmlWriting;
		listResult.htmlConversation = htmlConversation;
		listResult.htmNumberMemory = htmNumberMemory;
		listResult.htmlNumberWriting = htmlNumberWriting;
		listResult.htmlNumberConversation = htmlNumberConversation;
	}

	for (var i = 0; i < result.vocabularyList.length; i++){

		htmlMemoryVocabulary += '<div class="memoryVocabulary memoryVocabularyPage_' + i + '">';
		htmlWritingVocabulary += '<div class="memoryVocabulary memoryVocabularyPage_' + i + '">';
		htmlConversationVocabulary += '<div class="memoryVocabulary memoryVocabularyPage_' + i + '">';

		htmlMemoryVocabulary += '<input type="hidden" class="flagVocabulary" value=false>';
		htmlWritingVocabulary += '<input type="hidden" class="flagVocabulary" value=false>';
		htmlConversationVocabulary += '<input type="hidden" class="flagVocabulary" value=false>';

		htmlMemoryVocabulary += '<input type="hidden" class="scoreVocabulary" value="0">';
		htmlWritingVocabulary += '<input type="hidden" class="scoreVocabulary" value="0">';
		htmlConversationVocabulary += '<input type="hidden" class="scoreVocabulary" value="0">';

		if (memoryWritingConversation == 'memory') {
			htmlMemoryVocabulary += '<input type="hidden" class="startTime" value="' + starTime + '">';
			htmlMemoryVocabulary += '<input type="hidden" class="currentIdRecordVocabulary" value="0">';
		} else if (memoryWritingConversation == 'writing') {
			htmlWritingVocabulary += '<input type="hidden" class="startTime" value="' + starTime + '">';
			htmlWritingVocabulary += '<input type="hidden" class="currentIdRecordVocabulary" value="0">';
		} else {
			htmlConversationVocabulary += '<input type="hidden" class="startTime" value="' + starTime + '">';
			htmlConversationVocabulary += '<input type="hidden" class="currentIdRecordVocabulary" value="0">';
		}

		for (var j = 0; j < result.vocabularyList[i].length; j++){

			htmlMemoryVocabulary += '<div class="row">';
			htmlWritingVocabulary += '<div class="row">';
			htmlConversationVocabulary += '<div class="row">';

			var listResultMemoryVocabulary = createContentHtmlPracticeTestVocabulary(result, memoryWritingConversation, i, j);
			htmlMemoryVocabulary += listResultMemoryVocabulary.htmlVocabulary;
			htmNumberMemoryVocabulary = listResultMemoryVocabulary.htmlNumberVocabulary;

			var listResultWritingVocabulary = createContentHtmlPracticeTestVocabulary(result, memoryWritingConversation, i, j);
			htmlWritingVocabulary += listResultWritingVocabulary.htmlVocabulary;
			htmlNumberWritingVocabulary = listResultWritingVocabulary.htmlNumberVocabulary;

			var listResultConversationVocabulary = createContentHtmlPracticeTestVocabulary(result, memoryWritingConversation, i, j);
			htmlConversationVocabulary += listResultConversationVocabulary.htmlVocabulary;
			htmlNumberConversationVocabulary = listResultConversationVocabulary.htmlNumberVocabulary;
			
			// close answer
			htmlMemoryVocabulary += '</div>';
			htmlWritingVocabulary += '</div>';
			htmlConversationVocabulary += '</div>';
			// close row
			htmlMemoryVocabulary += '</div>';
			htmlWritingVocabulary += '</div>';
			htmlConversationVocabulary += '</div>';
		}
		// close page
		htmlMemoryVocabulary += '</div>';
		htmlWritingVocabulary += '</div>';
		htmlConversationVocabulary += '</div>';
	}
	listResult.htmlMemoryVocabulary = htmlMemoryVocabulary;
	listResult.htmlWritingVocabulary = htmlWritingVocabulary;
	listResult.htmlConversationVocabulary = htmlConversationVocabulary;
	listResult.htmNumberMemoryVocabulary = htmNumberMemoryVocabulary;
	listResult.htmlNumberWritingVocabulary = htmlNumberWritingVocabulary;
	listResult.htmlNumberConversationVocabulary = htmlNumberConversationVocabulary;
	return listResult;
}

/**
 * create Content Html Practice Test
 * 
 * @param result
 * @param memoryWritingConversation
 * @param i
 * @param j
 */
function createContentHtmlPracticeTest(result, memoryWritingConversation, i, j) {
	var indexPracticeMemory = result.pageIndexPracticeMemory;
	var indexPracticeWriting = result.pageIndexPracticeWriting;
	var indexPracticeConversation = result.pageIndexPracticeConversation;
	if (result.practiceTest != 'practice') {
		indexPracticeMemory = result.pageIndex;
		indexPracticeWriting = result.pageIndex;
		indexPracticeConversation = result.pageIndex;
	}
	var memoryWritingConversation = result.memoryWritingConversation;
	var pageLimit = result.pageLimit;
	var htmlValue = '';
	var htmlNumber = '';
	if (memoryWritingConversation == 'memory') {

		htmlNumber = '<div class="numberPractice">'+indexPracticeMemory+'/'+pageLimit+'</div>';

		htmlValue += '<div class="IndexQuestion">' + (j + 1) + '.</div>';
		htmlValue += '<div class="InforQuestion wall" id="indexQuestion_0_' + i + '_' + (j + 1) + '">' + result.scenarioShowList[i][j] + '</div>';
		htmlValue += '<input type="hidden" class="listPageQuestion_0_' + i + ' question_0_' + i + '_' + (j + 1) + '" value="' + result.scenarioList[i][j] + '" />';
		htmlValue += '<input class="" type="hidden" value="' + result.scenarioRubyList[i][j] + '" />';
		htmlValue += '<div class="IndexAnswer">' + (j + 1) + '.</div>';
		htmlValue += '<input type="hidden" class="no" value="' + (j + 1) + '">';
		htmlValue += '<div class="InforAnswer" id="inforAnswer_0_' + i + '_' + (j + 1) + '">';
		htmlValue += '<select class="listPageAnswer_0_' + i + ' selectBox answer_0_' + i + '_' + (j + 1) + '">';
		htmlValue += '<c:if test="' + result.memoryScenarioAnswerList + ' != null}">';
		htmlValue += '<option></option>';

		var listOption = result.memoryScenarioAnswerList[i][j];

		for (var k = 0; k < listOption.length; k++) {
			htmlValue += '<option value="' + listOption[k] + '">' + listOption[k] + '</option>';
		}

		htmlValue += '</select>';

	} else if (memoryWritingConversation == 'writing') {
	    
		htmlNumber = '<div class="numberPractice">'+indexPracticeWriting+'/'+pageLimit+'</div>';
		
		htmlValue += '<div class="IndexQuestion">' + (j + 1) + '.</div>';
		htmlValue += '<div class="InforQuestion wall" id="indexQuestion_0_' + i + '_' + (j + 1) + '">' + result.scenarioShowList[i][j] + '</div>';
		htmlValue += '<input type="hidden" class="listPageQuestion_0_' + i + ' question_0_' + i + '_' + (j + 1) + '" value="' + result.scenarioList[i][j] + '" />';
		htmlValue += '<input class="" type="hidden" value="' + result.scenarioRubyList[i][j] + '" />';
		htmlValue += '<input type="hidden" class="element" value="' + result.element[i][j] + '" />';
		htmlValue += '<div class="IndexAnswer">' + ( j + 1 ) + '.</div>';
		htmlValue += '<input type="hidden" class="no" value="' + ( j + 1 ) + '">';
		htmlValue += '<div class="InforAnswer" id="inforAnswer_0_' + i + '_' + ( j + 1 ) + '">';

		for (var k = 1; k <= result.element[i][j]; k++) {
			htmlValue += '<input class="listPageAnswer_0_' + i + ' textBox answer_0_' + i + '_' + (j + 1) + '_' + k + '" type="text" style="width: 12%; margin-left: 1%;"/>';
		}

	} else {

		htmlNumber = '<div class="numberPractice">'+indexPracticeConversation+'/'+pageLimit+'</div>';

		htmlValue += '<div class="IndexQuestion" style="padding-top: 10px;" >' + ( j + 1 ) + '.</div>';
		htmlValue += '<div class="InforQuestion wall rubyResult " style="line-height: 35px;" id="indexQuestion_0_' + i + '_' + ( j + 1 ) + '">' + result.scenarioList[i][j] + '</div>';
		htmlValue += '<input type="hidden" id="inputQuestionScenarioSpeaker_' + i + '_' + j + '" class="listPageQuestion_0_' + i + ' question_0_' + i + '_' + ( j + 1 ) + '" value="' + result.scenarioList[i][j] + '" />';
		htmlValue += '<input class="rubyWord" type="hidden" value="' + result.scenarioRubyList[i][j] + '" />';
		htmlValue += '<div class="invisible" id="inputQuestionJSonScenarioSpeaker_' + i + '_' + j + '">' + result.scenarioJSonList[i][j] + '</div>';
		htmlValue += '<div class="IndexAnswer" style="padding-top: 10px;">' + ( j + 1 ) + '.</div>';
		htmlValue += '<input type="hidden" class="no" value="' + ( j + 1 ) + '">';
		htmlValue += '<div class="InforAnswer" style="padding-top: 8px;" id="inforAnswer_0_' + i + '_' + ( j + 1 ) + '">';
		htmlValue += '<img onclick="speakerAnswer(\'ScenarioSpeaker_'
				+ i
				+ '_'
				+ j
				+ '\');" src="' + url + '/resources/images/speaker.gif" width="20" height="20" style="margin-right: 10%;" />';

		htmlValue += '<input class="inputAnswerScenarioSpeaker_'
				+ i + '_' + j + ' textBox answer_0_' + i + '_'
				+ (j + 1) + ' listPageAnswer_0_' + i
				+ '" type="text" readonly="readonly"/>';
	}
	var listResult = {};
	listResult.htmlValue = htmlValue;
	listResult.htmlNumber = htmlNumber;
	return listResult;
}

/**
 * create Content Html Practice Test Vocabulary
 * 
 * @param result
 * @param memoryWritingConversation
 * @param i
 * @param j
 * @returns listResult
 */
function createContentHtmlPracticeTestVocabulary(result, memoryWritingConversation, i, j) {
	var indexPracticeMemoryVocabulary = result.pageIndexPracticeMemoryVocabulary;
	var indexPracticeWritingVocabulary = result.pageIndexPracticeWritingVocabulary;
	var indexPracticeConversationVocabulary = result.pageIndexPracticeConversationVocabulary;
	var memoryWritingConversation = result.memoryWritingConversation;
	if (result.practiceTest != 'practice') {
		indexPracticeMemoryVocabulary = result.pageIndex;
		indexPracticeWritingVocabulary = result.pageIndex;
		indexPracticeConversationVocabulary = result.pageIndex;
	}
	var pageLimit = result.pageLimit;
	var htmlNumberVocabulary = '';
	var htmlVocabulary = '';
	if (memoryWritingConversation == 'memory'){

		htmlNumberVocabulary = '<div class="numberPractice">'+indexPracticeMemoryVocabulary+'/'+pageLimit+'</div>';
		htmlVocabulary += '<div class="IndexVocabularyQuestion">' + ( j + 1 ) + '.</div>';
		htmlVocabulary += '<div class="InforVocabularyQuestion wall">' + result.vocabularyShowList[i][j] + '</div>';
		htmlVocabulary += '<input type="hidden" class="listPageVocabularyQuestion_0_' + i + ' question_0_' + i + '_' + ( j + 1 ) + '" value="' + result.vocabularyList[i][j] + '" />';
		htmlVocabulary += '<input class="" type="hidden" value="' + result.vocabularyRubyList[i][j] + '" />';
		htmlVocabulary += '<div class="IndexVocabularyAnswer">' + ( j + 1 ) + '.</div>';
		htmlVocabulary += '<input type="hidden" class="no" value="' + ( j + 1 ) + '">';
		htmlVocabulary += '<div class="InforVocabularyAnswer" id="inforAnswerVoca_0_' + i + '_' + (j+1) + '">';
		htmlVocabulary += '<select class="listPageVocabularyAnswer_0_' + i + ' selectBox answer_0_' + i + '_' + ( j + 1 ) + '">';
		htmlVocabulary += '<c:if test="' + result.memoryVocabularyAnswerList + ' != null}">';
		htmlVocabulary += '<option></option>';

		var listOption = result.memoryVocabularyAnswerList[i][j];

		for (var k = 0; k < listOption.length; k++) {
			htmlVocabulary += '<option value="' + listOption[k] + '">' + listOption[k] + '</option>';
		}

		htmlVocabulary += '</select>';

	} else if(memoryWritingConversation == 'writing'){
		
		htmlNumberVocabulary = '<div class="numberPractice">'+indexPracticeWritingVocabulary+'/'+pageLimit+'</div>';
		htmlVocabulary += '<div class="IndexVocabularyQuestion">' + ( j + 1 ) + '.</div>';
		htmlVocabulary += '<div class="InforVocabularyQuestion wall">' + result.vocabularyShowList[i][j] + '</div>';
		htmlVocabulary += '<input type="hidden" class="listPageVocabularyQuestion_0_' + i + ' question_0_' + i + '_' + ( j + 1 ) + '" value="' + result.vocabularyList[i][j] + '" />';
		htmlVocabulary += '<input class="" type="hidden" value="' + result.vocabularyRubyList[i][j] + '" />';
		htmlVocabulary += '<div class="IndexVocabularyAnswer">' + ( j + 1 ) + '.</div>';
		htmlVocabulary += '<input type="hidden" class="no" value="' + ( j + 1 ) + '">';
		htmlVocabulary += '<div class="InforVocabularyAnswer" id="inforAnswerVoca_0_' + i + '_' + ( j + 1 ) + '">';
		htmlVocabulary += '<input class="listPageVocabularyAnswer_0_'+i+' textBox" type="text"/>';

	} else {

		htmlNumberVocabulary = '<div class="numberPractice">'+indexPracticeConversationVocabulary+'/'+pageLimit+'</div>';
		htmlVocabulary += '<div class="IndexVocabularyQuestion" style="padding-top: 10px;">' + ( j + 1 ) + '.</div>';
		htmlVocabulary += '<div class="InforVocabularyQuestion wall rubyResult" style="line-height: 35px;">' + result.vocabularyList[i][j] + '</div>';
		htmlVocabulary += '<input type="hidden" id="inputQuestionVocabularySpeaker_' + i + '_' + j + '" class="listPageVocabularyQuestion_0_' + i + ' question_0_' + i + '_' + ( j + 1 ) + '" value="' + result.vocabularyList[i][j] + '" />';
		htmlVocabulary += '<div class="invisible" id="inputQuestionJSonVocabularySpeaker_' + i + '_' + j + '">' + result.vocabularyJSonList[i][j] + '</div>';
		htmlVocabulary += '<input class="rubyWord" type="hidden" value="' + result.vocabularyRubyList[i][j] + '" />';
		htmlVocabulary += '<div class="IndexVocabularyAnswer" style="padding-top: 10px;">' + ( j + 1 ) + '.</div>';
		htmlVocabulary += '<input type="hidden" class="no" value="' + ( j + 1 ) + '">';
		htmlVocabulary += '<div class="InforVocabularyAnswer" style="padding-top: 8px;" id="inforAnswerVoca_0_' + i + '_' + ( j + 1 ) + '">';
		htmlVocabulary += '<img onclick="speakerAnswer(\'VocabularySpeaker_' + i + '_' + j + '\');" src="' + url + '/resources/images/speaker.gif" width="20" height="20" style="margin-right: 10%;" />';
		htmlVocabulary += '<input class="inputAnswerVocabularySpeaker_'+i+'_'+j+' listPageVocabularyAnswer_0_'+i+' textBox" type="text" readonly="readonly"/>';
	}
	var listResult = {};
	listResult.htmlVocabulary = htmlVocabulary;
	listResult.htmlNumberVocabulary = htmlNumberVocabulary;
	return listResult;
}