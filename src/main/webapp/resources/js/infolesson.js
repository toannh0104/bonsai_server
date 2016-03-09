// Start with lesson info index 0
var currentLessonIndex = 0;
var currentLessonNo = 1;
var lessonInfoArray = new Array();

/**
 * Start lesson info
 */
$(document).ready(
		function() {

			// Toggle lesson
			toggleLesson();

			// Check lesson range on load
			var lessonNo = $('#lessonRange').val();
			if (checkNotEmpty(lessonNo)) {
				var lessonMethodRomaji = $('#lessonMethodRomaji').val();
				var lessonMethodHiragana = $('#lessonMethodHiragana').val();
				var lessonMethodKanji = $('#lessonMethodKanji').val();
				// Get lesson range
				getLessonRange(lessonNo, lessonMethodRomaji,
						lessonMethodHiragana, lessonMethodKanji);
			}
		});

/**
 * User click button yes on dialog
 */
function actionApcept(callbackEvent) {
	// Flag for closing dialog
	var isClosedLessonRange = true;
	var lessonNo = $('#txtLessonId').val();
	var lessonMethodRomaji = $('#romanjiInput').val();
	var lessonMethodHiragana = $('#hiraganaInput').val();
	var lessonMethodKanji = $('#kanjiInput').val();

	var containerLessonInfo = $('.lesson_info_' + currentLessonIndex);

	if (checkNotEmpty(lessonNo)) {

		// If lesson range have '.'
		if (lessonNo.indexOf('.') > 0) {
			bootbox.alert(MSG.E0040, function(result) {
				callbackEvent();
			});
			return false;
		}

		// If lesson range have '.'
		if (lessonMethodRomaji.indexOf('.') > 0) {
			bootbox.alert(MSG.E0041, function(result) {
				callbackEvent();
			});
			return false;
		}

		// If lesson range have '.'
		if (lessonMethodHiragana.indexOf('.') > 0) {
			bootbox.alert(MSG.E0042, function(result) {
				callbackEvent();
			});
			return false;
		}

		// If lesson range have '.'
		if (lessonMethodKanji.indexOf('.') > 0) {
			bootbox.alert(MSG.E0043, function(result) {
				callbackEvent();
			});
			return false;
		}

		var oldList = lessonInfoArray;

		// Get lesson range
		isClosedLessonRange = getLessonRange(lessonNo, lessonMethodRomaji,
				lessonMethodHiragana, lessonMethodKanji, callbackEvent);

		// Lesson range is ok
		// Check if need to re-create lessonInfo
		if (isClosedLessonRange
				&& oldList.toString() != lessonInfoArray.toString()) {

			// If lesson range not start from 1
			if (lessonInfoArray[0] != 1) {
				bootbox.confirm(
						"lesson not start at lesson 1. Do you want continue ?",
						function(result) {
							if (result) {
								return result;
							} else {
								callbackEvent();
							}
						});
			}

			var keepedArray = getOldLessonArray(oldList);

			// Create lesson info if not exist
			createLessonInfoIfNotExist(keepedArray);
		}
	}

	// Pass validation
	if (isClosedLessonRange) {
		// set lesson method romaji to page
		var lessonRomanji = $('#romanjiInput').val();
		if (checkNotEmpty(lessonNo)) {
			$('#lessonMethodRomaji').val(lessonRomanji);
		}

		// set lesson method kana to page
		var lessonHiragana = $('#hiraganaInput').val();
		if (checkNotEmpty(lessonNo)) {
			$('#lessonMethodHiragana').val(lessonHiragana);
		}

		// set lesson method kanji to page
		var lessonKanji = $('#kanjiInput').val();
		if (checkNotEmpty(lessonNo)) {
			$('#lessonMethodKanji').val(lessonKanji);
		}
	}

	var setValueCondition = function(value, htmlName, hiddenName) {
		if (checkNotEmpty(value)) {
			containerLessonInfo.find('.' + htmlName).html('0%（' + value + '%）');
			containerLessonInfo.find('.' + hiddenName).val(value);
		}
	};

	var checkPercent = function(value, msg) {
		if (!validPercent(value)) {
			bootbox.alert(msg, function(result) {
				callbackEvent();
			});
			return false;
		} else {
			return true;
		}
	};

	var pramemory = $('#pra-memory').val();
	if (checkNotEmpty(pramemory)) {
		var pramemoryFlag = checkPercent(pramemory, getMessage([ '練習終了条件（記憶）', '100', '0' ], MSG.E0015));
		if (pramemoryFlag) {
			setValueCondition(pramemory, 'pra-memory-per', 'practiceMemoryConditionHidden');
		} else {
			return false;
		}
	}

	var prawriting = $('#pra-writing').val();
	if (checkNotEmpty(prawriting)) {
		var prawritingFlag = checkPercent(prawriting, getMessage([ '練習終了条件（筆記）', '100', '0' ], MSG.E0015));
		if (prawritingFlag) {
			setValueCondition(prawriting, 'pra-write-per', 'practiceWritingConditionHidden');
		} else {
			return false;
		}
	}

	var praconversation = $('#pra-conversation').val();
	if (checkNotEmpty(praconversation)) {
		var praconversationFlag = checkPercent(praconversation, getMessage([ '練習終了条件（会話）',
						'100', '0' ], MSG.E0015));
		if (praconversationFlag) {
			setValueCondition(praconversation, 'pra-conver-per', 'practiceConversationConditionHidden');
		} else {
			return false;
		}
	}

	var testmemory = $('#test-memory').val();
	if (checkNotEmpty(testmemory)) {
		var testmemoryFlag = checkPercent(testmemory, getMessage([ 'テスト合格条件（記憶）', '100', '0' ], MSG.E0015));
		if (testmemoryFlag) {
			setValueCondition(testmemory, 'test-memory-per', 'testMemoryConditionHidden');
		} else {
			return false;
		}
	}

	var testwriting = $('#test-writing').val();
	if (checkNotEmpty(testwriting)) {
		var testwritingFlag = checkPercent(testwriting, getMessage([ 'テスト合格条件（筆記）', '100', '0' ], MSG.E0015));
		if (testwritingFlag) {
			setValueCondition(testwriting, 'test-write-per', 'testWritingConditionHidden');
		} else {
			return false;
		}
	}

	var testconversation = $('#test-conversation').val();
	if (checkNotEmpty(testconversation)) {
		var testconversationFlag = checkPercent(testconversation, getMessage([ 'テスト合格条件（会話）',
						'100', '0' ], MSG.E0015));
		if (testconversationFlag) {
			setValueCondition(testconversation, 'test-conver-per', 'testConversationConditionHidden');
		} else {
			return false;
		}
	}
};

/**
 * allLessonInfo
 */
function allLessonInfo() {
	var lessonNoHidden = $('.lessonNoHidden');
	var html = '<table class="table table-striped"><thead><tr><th>Lesson No</th><th>Lesson Name</th><th>Lesson Method</th></tr></thead><tbody>';
	lessonNoHidden.each(function(index) {
		var indexLesson = lessonInfoArray.indexOf(parseInt($(this).val()));
		var lessonInfoForms = '';
		if (checkNotEmpty($(
				'input[name="lessonInfoForms[' + indexLesson
						+ '].scenarioName"]').val())) {
			lessonInfoForms = $(
					'input[name="lessonInfoForms[' + indexLesson
							+ '].scenarioName"]').val();
		}

		// declare lesson method
		var lessonMethod = '';
		// get method name
		if (getModeLanguage(parseInt($(this).val())) == 0) {
			lessonMethod = 'romanji';
		} else if (getModeLanguage(parseInt($(this).val())) == 1) {
			lessonMethod = 'kana';
		} else {
			lessonMethod = 'kanji';
		}
		// generate html
		html += '<tr><td>' + $(this).val() + '</td><td>' + lessonInfoForms
				+ '</td><td>' + lessonMethod + '</td></tr>';
	});
	html += '</tbody></table>';

	var settings = {
			hideClass : "btn-cancel"
		};

	createBootboxDialog("All Lesson Info", html, settings);

}

/**
 * Go to specified lesson info
 *
 * @param isNext
 */
function gotoLessonInfo(isNext, lessonId) {

	// Check lesson range was set
	if (lessonInfoArray.length === 0) {
		// Show message error
		bootbox.alert(MSG.E0014);
		return;
	}

	// Change lessonNo
	changeLessonNo(isNext);

	// Check if lesson info was exist, if not, load from server
	var lessonInfo = $('.lesson_info_' + currentLessonIndex);

	if (lessonInfo.length) {

		// Toggle lessonInfo again
		toggleLesson();

	} else {
		// Clone object before load
		var lessonInfoObject = $('.lesson_info_0').clone();
		var lessonScenarioObject = $('.lessonScenario_0').clone();
		var lessonVocabularyObject = $('.lessonVocabulary_0').clone();

		var lessonInfo = {};
		// Init lesson info
		lessonInfo.practiceLimit = '';
		lessonInfo.practiceMemoryCondition = '';
		lessonInfo.practiceWritingCondition = '';
		lessonInfo.practiceConversationCondition = '';
		lessonInfo.testMemoryCondition = '';
		lessonInfo.testWritingCondition = '';
		lessonInfo.testConversationCondition = '';
		lessonInfo.scenarioName = '';
		lessonInfo.scenarioSyntax = '';
		lessonInfo.vocabularyName = '';

		// Load lesson info
		loadLessonInfo(lessonInfoObject, lessonScenarioObject,
				lessonVocabularyObject, lessonInfo, currentLessonNo,
				currentLessonIndex);

		// Toggle lessonInfo
		toggleLesson();
	}
}

/**
 * showDialogLesson
 */
function showDialogLesson() {

	// Get value from hidden
	var lessonRange = $('#lessonRange').val();
	var lessonMethodRomaji = $('#lessonMethodRomaji').val();
	var lessonMethodHiragana = $('#lessonMethodHiragana').val();
	var lessonMethodKanji = $('#lessonMethodKanji').val();

	var message = '<div class="lessonNoDialog">'
				+ '<span><p>Lesson No.: </p><input id="txtLessonId" value="'
				+ lessonRange
				+ '"/></span>'
				+ '<span><p>レッスン方法（ローマ字）： </p><input id="romanjiInput" value="'
				+ lessonMethodRomaji
				+ '"/></span>'
				+ '<span><p>レッスン方法（ひらかな）： </p><input id="hiraganaInput" value="'
				+ lessonMethodHiragana
				+ '"/></span>'
				+ '<span><p>レッスン方法（漢字）： </p><input id="kanjiInput" value="'
				+ lessonMethodKanji + '"/></span>' + '</div>';

	var settings = {
			callbackSuccess : function() {
				actionApcept(showDialogLesson);
			}
		};

	createBootboxDialog('Lesson Info', message, settings);

}

/**
 * Show dialog practice
 */
function showDialogPractice() {

	// Get value from hidden
	var practiceMemoryCondtion = $(
			'input[name="lessonInfoForms[' + currentLessonIndex
					+ '].practiceMemoryCondition"]').val();
	var practiceWritingCondtion = $(
			'input[name="lessonInfoForms[' + currentLessonIndex
					+ '].practiceWritingCondition"]').val();
	var practiceConversationCondtion = $(
			'input[name="lessonInfoForms[' + currentLessonIndex
					+ '].practiceConversationCondition"]').val();

	var message = '<div class="practice">';
	if (learningType == '2') {
		message += '<span><p>練習終了条件（基本）： </p><p class="float-right">%</p><input id="pra-basic" value="'
				+ 0
				+ '" onkeypress="return event.charCode >= 48 && event.charCode <= 57"/></span>'
				+ '<span><p>練習終了条件（応用1）： </p><p class="float-right">%</p><input id="pra-application1" value="'
				+ 0
				+ '" onkeypress="return event.charCode >= 48 && event.charCode <= 57"/></span>'
				+ '<span><p>練習終了条件（応用2）： </p><p class="float-right">%</p><input id="pra-application2" value="'
				+ 0
				+ '" onkeypress="return event.charCode >= 48 && event.charCode <= 57"/></span>';
	}
	message += '<span><p>練習終了条件（記憶）： </p><p class="float-right">%</p><input id="pra-memory" value="'
			+ practiceMemoryCondtion
			+ '" onkeypress="return event.charCode >= 48 && event.charCode <= 57"/></span>'
			+ '<span><p>練習終了条件（筆記）： </p><p class="float-right">%</p><input id="pra-writing" value="'
			+ practiceWritingCondtion
			+ '" onkeypress="return event.charCode >= 48 && event.charCode <= 57"/></span>'
			+ '<span><p>練習終了条件（会話）： <p class="float-right">%</p></p><input id="pra-conversation" value="'
			+ practiceConversationCondtion
			+ '" onkeypress="return event.charCode >= 48 && event.charCode <= 57"/></span>'
			+ '</div>';

	var settings = {
			callbackSuccess : function() {
				actionApcept(showDialogPractice);
			}
		};

	createBootboxDialog($('.lbpractice').first().text(), message, settings);

}

/**
 * showDialogTest
 */
function showDialogTest() {

	// Get value from hidden
	var testMemoryCondtion = $(
			'input[name="lessonInfoForms[' + currentLessonIndex
					+ '].testMemoryCondition"]').val();
	var testWritingCondtion = $(
			'input[name="lessonInfoForms[' + currentLessonIndex
					+ '].testWritingCondition"]').val();
	var testConversationCondtion = $(
			'input[name="lessonInfoForms[' + currentLessonIndex
					+ '].testConversationCondition"]').val();

	var settings = {
			callbackSuccess : function() {
				actionApcept(showDialogTest);
			}
		};

	var message = '<div class="test">';
	if (learningType == '2') {
		message += '<span><p>テスト合格条件（基本）：<p class="float-right">%</p> </p><input id="test-basic" value="'
				+ 0
				+ '" onkeypress="return event.charCode >= 48 && event.charCode <= 57"/></span>'
				+ '<span><p>テスト合格条件（応用1）：<p class="float-right">%</p> </p><input id="test-application1" value="'
				+ 0
				+ '" onkeypress="return event.charCode >= 48 && event.charCode <= 57"/></span>'
				+ '<span><p>テスト合格条件（応用2）：<p class="float-right">%</p> </p><input id="test-application2" value="'
				+ 0
				+ '" onkeypress="return event.charCode >= 48 && event.charCode <= 57"/></span>';
	}
	message += '<span><p>テスト合格条件（筆記）：<p class="float-right">%</p> </p><input id="test-memory" value="'
				+ testMemoryCondtion
				+ '" onkeypress="return event.charCode >= 48 && event.charCode <= 57"/></span>'
				+ '<span><p>テスト合格条件（筆記）：<p class="float-right">%</p> </p><input id="test-writing" value="'
				+ testWritingCondtion
				+ '" onkeypress="return event.charCode >= 48 && event.charCode <= 57"/></span>'
				+ '<span><p>テスト合格条件（会話）： <p class="float-right">%</p></p><input id="test-conversation" value="'
				+ testConversationCondtion
				+ '" onkeypress="return event.charCode >= 48 && event.charCode <= 57"/></span>'

				+ '</div>';

	createBootboxDialog($('.lbtest').first().text(), message, settings);

}

/**
 * @param value
 * @returns {String}
 */
function checkPercent(value) {
	if (checkNotEmpty(value)) {
		return '0%（' + value + '%）';
	} else {
		return '0%（0%）';
	}
}

/**
 * @param lessonScenario
 * @param list
 */
function genarateScenario(lessonScenario, list) {
	var contentWho = $(lessonScenario).find('.ContenWhoLableAdmin');
	var contentYou = $(lessonScenario).find('.ContenYouLableAdmin');
	if (list != null) {
		for (var i = 0; i < list.length; i++) {
			console.log($(list[i]).val());
			if ($(list[i]).attr('part') == 0) {
				$(contentWho).find('.textscenario_' + i).html(
						$(list[i]).attr('order') + '. ' + $(list[i]).val());
				$(contentWho).find('.scenario_' + i).val($(list[i]).val());
				$(contentWho).find('.scenarioOrder_' + i).val(
						$(list[i]).attr('order'));
			} else {
				$(contentYou).find('.textscenario_' + i).html(
						$(list[i]).attr('order') + '. ' + $(list[i]).val());
				$(contentYou).find('.scenario_' + i).val($(list[i]).val());
				$(contentYou).find('.scenarioOrder_' + i).val(
						$(list[i]).attr('order'));
			}
		}
	}
}

/**
 * Load lesson info
 *
 * @param lessonInfo
 */
function loadLessonInfo(lessonInfoObject, lessonScenarioObject,
		lessonVocabularyObject, lessonInfo, lessonNo, lessonIndex) {

	// Remove if lessonInfo exist
	$('.lesson_info_' + lessonIndex).remove();
	$('.lessonScenario_' + lessonIndex).remove();
	$('.lessonVocabulary_' + lessonIndex).remove();

	// Update class name
	$(lessonInfoObject).removeClass('lesson_info_0');
	$(lessonInfoObject).addClass('lesson_info_' + lessonIndex);

	$(lessonScenarioObject).removeClass('lessonScenario_0');
	$(lessonScenarioObject).addClass('lessonScenario_' + lessonIndex);

	$(lessonVocabularyObject).removeClass('lessonVocabulary_0');
	$(lessonVocabularyObject).addClass('lessonVocabulary_' + lessonIndex);

	// Update js function
	var linkEditScenarioObject = $(lessonScenarioObject).find(
			'.LanguageAdmin a');
	if (linkEditScenarioObject.length > 0) {
		linkEditScenarioObject.attr('onclick', 'showTheAreaScenario(this, '
				+ lessonIndex + ' , "");');
	} else {
		$(lessonScenarioObject).find('.LanguageAdmin div:first').attr('onclick',
				'showTheAreaScenario(this, ' + lessonIndex + ' , "");');
	}

	var linkEditVocabularyObject = $(lessonVocabularyObject).find(
			'.LanguageVocabularyAdmin a');
	if (linkEditVocabularyObject.length > 0) {
		linkEditVocabularyObject.attr('onclick', 'showTheAreaVocabulary(this, '
				+ lessonIndex + ' , "");');
	} else {
		$(lessonVocabularyObject).find('.Conten').attr('onclick',
				'showTheAreaVocabulary(this, ' + lessonIndex + ' , "");');
	}

	// == UPDATE LESSON INFO START ==
	// Update lesson id text
	$(lessonInfoObject).find('.lessonIdText').html('Lesson #' + lessonNo);

	// Update lesson no hidden
	var lessonNoObject = $(lessonInfoObject).find('.lessonNoHidden');
	lessonNoObject
			.attr('name', 'lessonInfoForms[' + lessonIndex + '].lessonNo');
	lessonNoObject.val(lessonNo);

	// Update lesson id hidden
	$(lessonInfoObject).find('.lessonIdHidden').attr('name',
			'lessonInfoForms[' + lessonIndex + '].lessonId');

	// Update lesson info id hidden
	$(lessonInfoObject).find('.lessonInfoIdHidden').attr('name',
			'lessonInfoForms[' + lessonIndex + '].id');
	// Clear old value
	$(lessonInfoObject).find('.lessonInfoIdHidden').val('');

	// Update practice limit
	$(lessonInfoObject).find('.practiceLimitHidden').attr('name',
			'lessonInfoForms[' + lessonIndex + '].practiceLimit');
	$(lessonInfoObject).find('.practiceLimitHidden').val(
			lessonInfo.practiceLimit);
	// Set practice text
	$(lessonScenarioObject).find('.practiceLimitText').html(
			lessonInfo.practiceLimit);
	$(lessonVocabularyObject).find('.practiceLimitText').html(
			lessonInfo.practiceLimit);

	$(lessonInfoObject).find('.pra-memory-per').html(
			checkPercent(lessonInfo.practiceConversationCondition));
	// Update name and set value
	var practiceMemoryCondition = $(lessonInfoObject).find(
			'.practiceMemoryConditionHidden');
	practiceMemoryCondition.attr('name', 'lessonInfoForms[' + lessonIndex
			+ '].practiceMemoryCondition');
	practiceMemoryCondition.val(lessonInfo.practiceConversationCondition);

	$(lessonInfoObject).find('.pra-write-per').html(
			checkPercent(lessonInfo.practiceWritingCondition));
	// Update name and set value
	var practiceWritingCondition = $(lessonInfoObject).find(
			'.practiceWritingConditionHidden');
	practiceWritingCondition.attr('name', 'lessonInfoForms[' + lessonIndex
			+ '].practiceWritingCondition');
	practiceWritingCondition.val(lessonInfo.practiceWritingCondition);

	$(lessonInfoObject).find('.pra-conver-per').html(
			checkPercent(lessonInfo.practiceConversationCondition));
	// Update name and set value
	var practiceConversationCondition = $(lessonInfoObject).find(
			'.practiceConversationConditionHidden');
	practiceConversationCondition.attr('name', 'lessonInfoForms[' + lessonIndex
			+ '].practiceConversationCondition');
	practiceConversationCondition.val(lessonInfo.practiceConversationCondition);

	$(lessonInfoObject).find('.test-memory-per').html(
			checkPercent(lessonInfo.testMemoryCondition));
	// Update name and set value
	var testMemoryCondition = $(lessonInfoObject).find(
			'.testMemoryConditionHidden');
	testMemoryCondition.attr('name', 'lessonInfoForms[' + lessonIndex
			+ '].testMemoryCondition');
	testMemoryCondition.val(lessonInfo.testMemoryCondition);

	$(lessonInfoObject).find('.test-write-per').html(
			checkPercent(lessonInfo.testWritingCondition));
	// Update name and set value
	var testWritingCondition = $(lessonInfoObject).find(
			'.testWritingConditionHidden');
	testWritingCondition.attr('name', 'lessonInfoForms[' + lessonIndex
			+ '].testWritingCondition');
	testWritingCondition.val(lessonInfo.testWritingCondition);

	$(lessonInfoObject).find('.test-conver-per').html(
			checkPercent(lessonInfo.testConversationCondition));
	// Update name and set value
	var testConversationCondition = $(lessonInfoObject).find(
			'.testConversationConditionHidden');
	testConversationCondition.attr('name', 'lessonInfoForms[' + lessonIndex
			+ '].testConversationCondition');
	testConversationCondition.val(lessonInfo.testConversationCondition);
	// == UPDATE LESSON INFO END ==

	// == UPDATE SCENARIO START ==
	// Set text for lesson name
	$(lessonScenarioObject).find('.scenarioNameText').html(
			lessonInfo.scenarioName);
	// Set value for hidden
	$(lessonScenarioObject).find('.scenarioNameHidden').attr('name',
			'lessonInfoForms[' + lessonIndex + '].scenarioName');
	$(lessonScenarioObject).find('.scenarioNameHidden').val(
			lessonInfo.scenarioName);
	// Set text for syntax
	$(lessonScenarioObject).find('.syntaxText').html(lessonInfo.scenarioSyntax);
	// Set value for hidden
	$(lessonScenarioObject).find('.syntaxTextHidden').attr('name',
			'lessonInfoForms[' + lessonIndex + '].scenarioSyntax');
	$(lessonScenarioObject).find('.syntaxTextHidden').val(
			lessonInfo.scenarioSyntax);

	// clone content who or you
	var cloneContent = function(target) {
		$(target).find('.textscenario_' + i).html(
				lessonInfo.scenarioDtos[i].scenarioOrder + '. '
						+ lessonInfo.scenarioDtos[i].scenario);
		$(target).find('.scenario_' + i).attr(
				'name',
				'lessonInfoForms[' + lessonIndex + '].scenarioForms[' + i
						+ '].scenario');
		$(target).find('.scenario_' + i).val(
				lessonInfo.scenarioDtos[i].scenario);
		$(target).find('.scenarioOrder_' + i).attr(
				'name',
				'lessonInfoForms[' + lessonIndex + '].scenarioForms[' + i
						+ '].scenarioOrder');
		$(target).find('.scenarioOrder_' + i).val(
				lessonInfo.scenarioDtos[i].scenarioOrder);
	};

	var contentWho = $(lessonScenarioObject).find('.ContenWhoLableAdmin');
	var contentYou = $(lessonScenarioObject).find('.ContenYouLableAdmin');
	if (lessonInfo.scenarioDtos != null) {
		for (var i = 0; i < lessonInfo.scenarioDtos.length; i++) {
			if (lessonInfo.scenarioDtos[i].scenarioPart == 0) {
				cloneContent(contentWho);
			} else {
				cloneContent(contentYou);
			}
		}
	} else {
		// remove all whorow
		$(lessonScenarioObject).find('.ContenWhoLableAdmin').find('.whorow')
				.remove();
		$(lessonScenarioObject).find('.ContenYouLableAdmin').html('');

		// init name and value for hidden
		$(lessonScenarioObject).find('.ContenWhoLableAdmin').find(
				'.speakerOneName').attr('name',
				'lessonInfoForms[' + lessonIndex + '].speakerOneName');

		$(lessonScenarioObject).find('.ContenWhoLableAdmin').find(
				'.speakerOneName').val('山田さん');

		$(lessonScenarioObject).find('.ContenWhoLableAdmin').find(
				'.speakerTwoName').val('あなた');

		$(lessonScenarioObject).find('.ContenWhoLableAdmin').find(
				'.speakerTwoName').attr('name',
				'lessonInfoForms[' + lessonIndex + '].speakerTwoName');

		// show speaker name to page
		$(lessonScenarioObject).find('.ContenWhoLable').html('山田さん');

		$(lessonScenarioObject).find('.ContenYouLable').html('あなた');
	}

	// update LessonVocabulary
	$(lessonVocabularyObject).find('#lessonVocabularyName').html('');
	$(lessonVocabularyObject).find('.lessonVocabularyNameHidden').attr('name',
			'lessonInfoForms[' + lessonIndex + '].vocabularyName');
	$(lessonVocabularyObject).find('.lessonVocabularyNameHidden').val('');

	$(lessonVocabularyObject).find('.Conten').html('');

	if (lessonInfo.scenarioDtos != null) {
		for (var i = 0; i < lessonInfo.vocabularyDtos.length; i++) {
			var html = '<div class="row"><div class="col-hafl">'
					+ lessonInfo.vocabularyDtos[i].vocabularyOrder + '. '
					+ lessonInfo.vocabularyDtos[i].vocabulary + '</div>'
					+ '<input class="vocabulary_' + i
					+ '" type="hidden" name="lessonInfoForms[' + lessonIndex
					+ '].vocabularyForms[' + i + '].vocabulary" value="'
					+ lessonInfo.vocabularyDtos[i].vocabulary + '">'
					+ '<input class="vocabularyOrder_' + i
					+ '" type="hidden" name="lessonInfoForms[' + lessonIndex
					+ '].vocabularyForms[' + i + '].vocabularyOrder" value="'
					+ lessonInfo.vocabularyDtos[i].vocabularyOrder + '"></div>';
			$(lessonVocabularyObject).find('.Conten').append(html);
		}
	}

	// Append to last
	$('.wrapper-info-lesson').append(lessonInfoObject);
	$('.wrapper-scenario').append(lessonScenarioObject);
	$('.wrapper-vocabulary').append(lessonVocabularyObject);
}

/**
 * Get lesson range
 *
 * @param range
 */
function getLessonRange(lessonNo, lessonMethodRomaji, lessonMethodHiragana,
		lessonMethodKanji, callbackEvent) {

	// Split by space " "
	var arrLessonRange = lessonNo.split(" ");
	var arrLessonRomajiRange = lessonMethodRomaji.split(" ");
	var arrLessonKanaRange = lessonMethodHiragana.split(" ");
	var arrLessonKanjiRange = lessonMethodKanji.split(" ");

	var lessonInfoRange = new Array();
	var lessonRomajiRange = new Array();
	var lessonKanaRange = new Array();
	var lessonKanjiRange = new Array();
	var result = true;

	var showAlertForLessonRange = function(msg) {
		bootbox.alert(msg, function(result) {
			if (callbackEvent) {
				callbackEvent();
			}
		});
		// error
		return false;
	};

	// Lesson range
	try {
		// Create array from lesson range
		lessonInfoRange = createArrayLessonFromString(arrLessonRange);
    } catch (err) {
    	showAlertForLessonRange(MSG.E0019);
    	return false;
    }

    // Lesson Romaji
    try {
    	if (checkNotEmpty(lessonMethodRomaji)) {
    		// Create array from lesson romaji
    		lessonRomajiRange = createArrayLessonFromString(arrLessonRomajiRange);
		}
    } catch (err) {
    	showAlertForLessonRange(MSG.E0020);
    	return false;
    }

    // Lesson Kana
    try {
    	if (checkNotEmpty(lessonMethodHiragana)) {
    		// Create array from lesson Kana
            lessonKanaRange = createArrayLessonFromString(arrLessonKanaRange);
		}
    } catch (err) {
    	showAlertForLessonRange(MSG.E0021);
    	return false;
    }

    // Lesson Kanji
    try {
    	if (checkNotEmpty(lessonMethodKanji)) {
    		// Create array from lesson Kana
            lessonKanjiRange = createArrayLessonFromString(arrLessonKanjiRange);
		}
    } catch (err) {
    	showAlertForLessonRange(MSG.E0022);
    	return false;
    }

    // Check lesson romaji in range
    for (var i = 0; i < lessonRomajiRange.length; i++) {
        var value = lessonRomajiRange[i];
        if ($.inArray(value, lessonInfoRange) === -1) {
        	showAlertForLessonRange(MSG.E0023);
        	return false;
        }
    }

    // Check lesson Kana in range
    for (var i = 0; i < lessonKanaRange.length; i++) {
        var value = lessonKanaRange[i];
        if ($.inArray(value, lessonInfoRange) === -1) {
        	showAlertForLessonRange(MSG.E0024);
        	return false;
        }
    }

    // Check lesson kanji in range
    for (var i = 0; i < lessonKanjiRange.length; i++) {
        var value = lessonKanjiRange[i];
        if ($.inArray(value, lessonInfoRange) === -1) {
        	showAlertForLessonRange(MSG.E0025);
        	return false;
        }
    }

    // Check if romaji duplicate with kana & kanji
    for (var i = 0; i < lessonRomajiRange.length; i++) {
        var value = lessonRomajiRange[i];
        if ($.inArray(value, lessonKanaRange) !== -1) {
        	showAlertForLessonRange(MSG.E0026);
        	return false;
        } else if ($.inArray(value, lessonKanjiRange) !== -1) {
        	showAlertForLessonRange(MSG.E0027);
        	return false;
        }
    }

    // Check if kana duplicate with kanji
    for (var i = 0; i < lessonKanaRange.length; i++) {
        var value = lessonKanaRange[i];
        if ($.inArray(value, lessonKanjiRange) !== -1) {
        	showAlertForLessonRange(MSG.E0028);
        	return false;
        }
    }

	// Set to lessonInfoArray
	lessonInfoArray = lessonInfoRange;

	// Set to range
	$('#lessonRange').val(lessonNo);

	// Reset lesson info index to 0
	currentLessonIndex = 0;
	currentLessonNo = lessonInfoArray[currentLessonIndex];

	return result;
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
 * Change lesson no
 *
 * @param isNext
 */
function changeLessonNo(isNext) {

	var max = lessonInfoArray.length;
    // Check if lesson info was exist, if not, load from server
    var lessonInfo = $('.lesson_info_' + (max - 1));
    if (isNext) {
        // Go next
        if (currentLessonIndex == max - 1) {
            currentLessonIndex = 0;
        } else {
            currentLessonIndex++;
        }
    } else {
    	// if lesson last lesson info has exist
    	if (lessonInfo.length != 0 || currentLessonIndex != 0 ) {
    		// Go previous
    		if (currentLessonIndex == 0) {
    			currentLessonIndex = max - 1;
    		} else {
    			currentLessonIndex--;
    		}
		}
    }

	// Get lesson no
	currentLessonNo = lessonInfoArray[currentLessonIndex];
}

/**
 * After setting lesson range, update all lesson info
 */
function createLessonInfoIfNotExist(keepedArray) {

	// Init index and lesson no
	currentLessonIndex = 0;
	currentLessonNo = lessonInfoArray[currentLessonIndex];

	// Clone object before load
	var lessonInfoObject = $('.lesson_info_0').clone();
	var lessonScenarioObject = $('.lessonScenario_0').clone();
	var lessonVocabularyObject = $('.lessonVocabulary_0').clone();

	// Remove old lessonInfo except index = 0
	var lessonInfos = $('.lesson_info');
	var lessonScenarios = $('.thescenario');
	var lessonVocabularys = $('.theLessonVocabulary');

	// Remove all lesson info before create new
	lessonInfos.each(function() {
		$(this).remove();
	});
	lessonScenarios.each(function() {
		$(this).remove();
	});
	lessonVocabularys.each(function() {
		$(this).remove();
	});

	var firstLessonExist = false;

	// Update keeped lesson to new index
	if (keepedArray.length > 0) {
		for (var i = 0; i < keepedArray.length; i++) {
			var object = keepedArray[i];
			// Clone lessonInfo from old
			cloneLessonInfoFromOld(object.lessonInfoObject,
					object.lessonScenarioObject, object.lessonVocabulary,
					object.oldIndex, object.lessonNo, object.newIndex);

			// If first lessonInfo also exist
			if (object.lessonNo == currentLessonNo) {
				firstLessonExist = true;
			}
		}
	}

	// If first lessonInfo not exist, load first lessonInfo with empty data
	if (!firstLessonExist) {

		var lessonInfo = {};
		// Init lesson info
		lessonInfo.practiceLimit = '';
		lessonInfo.practiceMemoryCondition = '';
		lessonInfo.practiceWritingCondition = '';
		lessonInfo.practiceConversationCondition = '';
		lessonInfo.testMemoryCondition = '';
		lessonInfo.testWritingCondition = '';
		lessonInfo.testConversationCondition = '';
		lessonInfo.scenarioName = '';
		lessonInfo.scenarioSyntax = '';
		lessonInfo.vocabularyName = '';

		// Load lesson info
		loadLessonInfo(lessonInfoObject, lessonScenarioObject,
				lessonVocabularyObject, lessonInfo, currentLessonNo,
				currentLessonIndex);
	}

	// Toggle lesson
	toggleLesson();
}

/**
 * Get changed lesson
 *
 * @param oldList
 */
function getOldLessonArray(oldList) {

	var newList = lessonInfoArray;
	var keepedArray = new Array();

	// Iterate old list to get
	for (var i = 0; i < oldList.length; i++) {

		var index = $.inArray(oldList[i], newList);

		// Check if lesson_no still exist it new list
		if (index !== -1) {
			var objectLesson = {};

			// Store old lessonInfo 's data
			objectLesson.oldIndex = i;
			objectLesson.newIndex = index;
			objectLesson.lessonNo = oldList[i];
			objectLesson.lessonInfoObject = $('.lesson_info_' + i).clone();
			objectLesson.lessonScenarioObject = $('.lessonScenario_' + i)
					.clone();
			objectLesson.lessonVocabulary = $('.lessonVocabulary_' + i).clone();
			// Push to array
			keepedArray.push(objectLesson);
		}
	}

	return keepedArray;
}

/**
 * Clone lessonInfo from old lessonInfo
 *
 * @param lessonInfoObject
 * @param lessonScenarioObject
 * @param lessonVocabularyObject
 * @param oldLessonIndex
 * @param lessonNo
 * @param newLessonIndex
 */
function cloneLessonInfoFromOld(lessonInfoObject, lessonScenarioObject,
		lessonVocabularyObject, oldLessonIndex, lessonNo, newLessonIndex) {

	// Update class name
	$(lessonInfoObject).removeClass('lesson_info_' + oldLessonIndex);
	$(lessonInfoObject).addClass('lesson_info_' + newLessonIndex);

	$(lessonScenarioObject).removeClass('lessonScenario_' + oldLessonIndex);
	$(lessonScenarioObject).addClass('lessonScenario_' + newLessonIndex);

	$(lessonVocabularyObject).removeClass('lessonVocabulary_' + oldLessonIndex);
	$(lessonVocabularyObject).addClass('lessonVocabulary_' + newLessonIndex);

	// Update js function
	var linkEditScenarioObject = $(lessonScenarioObject).find(
			'.LanguageAdmin a');
	if (linkEditScenarioObject.length > 0) {
		linkEditScenarioObject.attr('onclick', 'showTheAreaScenario(this, '
				+ newLessonIndex + ' , "");');
	} else {
		$(lessonScenarioObject).find('.LanguageAdmin div:first').attr('onclick',
				'showTheAreaScenario(this, ' + newLessonIndex + ' , "");');
	}

	var linkEditVocabularyObject = $(lessonVocabularyObject).find(
			'.LanguageVocabularyAdmin a');
	if (linkEditVocabularyObject.length > 0) {
		linkEditVocabularyObject.attr('onclick', 'showTheAreaVocabulary(this, '
				+ newLessonIndex + ' , "");');
	} else {
		$(lessonVocabularyObject).find('.Conten').attr('onclick',
				'showTheAreaVocabulary(this, ' + newLessonIndex + ' , "");');
	}

	// == UPDATE LESSON INFO START ==

	$(lessonInfoObject).find('input').each(
			function() {
				var name = $(this).attr('name');
				// Replace new index
				name = name.replace('lessonInfoForms[' + oldLessonIndex + ']',
						'lessonInfoForms[' + newLessonIndex + ']');
				$(this).attr('name', name);
			});

	// == UPDATE LESSON INFO END ==

	// == UPDATE SCENARIO START ==

	$(lessonScenarioObject).find('input').each(
			function() {
				var name = $(this).attr('name');
				// Replace new index
				name = name.replace('lessonInfoForms[' + oldLessonIndex + ']',
						'lessonInfoForms[' + newLessonIndex + ']');
				$(this).attr('name', name);
			});

	// == UPDATE SCENARIO END ==

	var contentWho = $(lessonScenarioObject).find('.ContenWhoLableAdmin');
	var contentYou = $(lessonScenarioObject).find('.ContenYouLableAdmin');

	contentWho.find('input').each(
			function() {
				var name = $(this).attr('name');
				// Replace new index
				name = name.replace('lessonInfoForms[' + oldLessonIndex + ']',
						'lessonInfoForms[' + newLessonIndex + ']');
				$(this).attr('name', name);
			});
	contentYou.find('input').each(
			function() {
				var name = $(this).attr('name');
				// Replace new index
				name = name.replace('lessonInfoForms[' + oldLessonIndex + ']',
						'lessonInfoForms[' + newLessonIndex + ']');
				$(this).attr('name', name);
			});

	// == UPDATE VOCABULARY START ==

	$(lessonVocabularyObject).find('input').each(
			function() {
				var name = $(this).attr('name');
				// Replace new index
				name = name.replace('lessonInfoForms[' + oldLessonIndex + ']',
						'lessonInfoForms[' + newLessonIndex + ']');
				$(this).attr('name', name);
			});

	$(lessonVocabularyObject).find('.Conten').find('input').each(
			function() {
				var name = $(this).attr('name');
				// Replace new index
				name = name.replace('lessonInfoForms[' + oldLessonIndex + ']',
						'lessonInfoForms[' + newLessonIndex + ']');
				$(this).attr('name', name);
			});
	// == UPDATE VOCABULARY END ==

	// Append to last
	$('.wrapper-info-lesson').append(lessonInfoObject);
	$('.wrapper-scenario').append(lessonScenarioObject);
	$('.wrapper-vocabulary').append(lessonVocabularyObject);
}