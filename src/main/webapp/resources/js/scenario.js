$(document).ready(function() {
	innitRuby();
	$('[data-toggle="popover"]').popover();

	$(document).on('click touchstart', function (e) {
	    $('[data-toggle="popover"]').each(function () {
	        //the 'is' for buttons that trigger popups
	        //the 'has' for icons within a button that triggers a popup
	        if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
	            $(this).popover('hide');
	            return;
	        }
	    });
	});
});
/**
 * event for link LessonTitle
 */
function showLessonTitle() {

	// Get data from hidden
	var scenarioName = $(
			'input[name="lessonInfoForms[' + currentLessonIndex
					+ '].scenarioName"]').val();

	var message = '<div class="wrap-for-dialog"><p>シナリオ名称：</p> <span><input id="textLesson" value="'
			+ scenarioName + '" /></span></div>';

	var settings = {
		callbackSuccess : function() {
			var lessonScenario = $('.lessonScenario_' + currentLessonIndex);
			var lessonVocabulary = $('.lessonVocabulary_' + currentLessonIndex);
			// Save value from dialog to page
			lessonScenario.find('.scenarioNameText').html(
					$('#textLesson').val());
			lessonVocabulary.find('#lessonVocabularyName').html(
					$('#textLesson').val());
			// save value from dialog to hidden
			lessonScenario.find('.scenarioNameHidden').val(
					$('#textLesson').val());
			lessonVocabulary.find('.lessonVocabularyNameHidden').val(
					$('#textLesson').val());
		}
	};

	createBootboxDialog('Scenario name', message, settings);

}

/**
 * event for link SyntaxTitleDialog
 */
function showSyntaxTitleDialog(id) {

	// Get data from hidden
	var scenarioSyntax = $(
			'input[name="lessonInfoForms[' + currentLessonIndex
					+ '].scenarioSyntax"]').val();

	if (allSentences) {
		genSyntaxForDialog(scenarioSyntax, allSentences);
	} else {
		// Get all sentences form example
		getAllSentencesByConcept(genSyntaxForDialog, scenarioSyntax);
	}
}

/**
 * Get all sentences by concepts (from first to last)
 *
 * @param sentencesResult
 * @param scenarioSyntax
 */
function getAllSentencesByConcept(callback, parameter) {

	// Send ajax request for get all sentences
	var url = $('#url').val();
	$.ajax({
		url : url + "/admin/allScenario",
		data : {
			"lessonId" : currentLessonNo,
			"learningType" : $('#learningType').val()
		},
		type : "GET",
		contentType : "application/json; charset=utf-8",
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(response) {

			// Check if status OK
			if (response.status === RESULT_STATUS_OK) {

				// save scenario
				allSentences = response.result;

				// Excute callback after finish
				callback(parameter, allSentences);
			} else {
				bootbox.alert(response.message);
			}
		}
	});

}

/**
 * generate html for syntax dialog
 *
 * @param scenarioSyntax
 * @param result
 */
function genSyntaxForDialog(scenarioSyntax, result) {
	var htmlInner = '<div class="wrap-for-dialog"><p>学習構文</p> <select id="textSyntax">';

	var lessonIdTemp = 1;
	var count = 0;
	for (var i = 0; i < result.length; i++) {
		var value = '';
		var sentence = result[i].sentence;
		// if lesson id is same
		if (lessonIdTemp == result[i].lessonId) {
			count++;
			lessonIdTemp = result[i].lessonId;
		} else {
			count = 1;
			lessonIdTemp = result[i].lessonId;
		}
		for (var j = 0; j < sentence.length; j++) {
			value += sentence[j].kanji;
		}
		if (value == scenarioSyntax) {
			htmlInner += '<option calljLessonNo="' + result[i].lessonId
					+ '" questionName="' + result[i].nameQuestion
					+ '" conceptName="' + result[i].nameConcept + '" value="'
					+ value + '" selected>' + result[i].lessonId + '-' + count
					+ '.' + value + '</option>';
		} else {
			htmlInner += '<option calljLessonNo="' + result[i].lessonId
					+ '" questionName="' + result[i].nameQuestion
					+ '" conceptName="' + result[i].nameConcept + '" value="'
					+ value + '">' + result[i].lessonId + '-' + count + '.'
					+ value + '</option>';
		}
	}
	htmlInner += '</select></div>';

	var settings = {
		callbackSuccess : function() {
			var lessonScenario = $('.lessonScenario_' + currentLessonIndex);
			// Save value from dialog to page
			lessonScenario.find('.syntaxText').html($('#textSyntax').val());
			// Save value from dialog to hidden
			lessonScenario.find('.syntaxTextHidden')
					.val($('#textSyntax').val());
		}
	};

	createBootboxDialog('Syntax Title', htmlInner, settings);
}

/**
 * event for link PracticeTitleBefor
 */
function showPracticeTitleBefor() {

	var containerLessonInfo = $('.lesson_info_' + currentLessonIndex);
	var containerScenario = $('.lessonScenario_' + currentLessonIndex);
	var containerVocabulary = $('.lessonVocabulary_' + currentLessonIndex);

	var practiceLimitObject = containerLessonInfo.find('.practiceLimitHidden');
	// Get data from hidden
	var practiceLimit = practiceLimitObject.val();

	var message = '<span><p>練習： </p><p class="float-right">回</p> <input onkeypress="return event.charCode >= 48 && event.charCode <= 57" id="practiceLimit" value="'
			+ practiceLimit + '" /></span>';

	var settings = {
		callbackSuccess : function() {
			// check practice limit
			checkPractiseLimit(containerScenario, containerVocabulary,
					practiceLimitObject, $('#practiceLimit'));
		}
	};

	createBootboxDialog('Practice Limit', message, settings);

}
/**
 * Method check practice limit
 *
 * @param containerScenario
 * @param containerVocabulary
 * @param practiceLimitObject
 * @param target
 */
function checkPractiseLimit(containerScenario, containerVocabulary,
		practiceLimitObject, target) {

	if (parseInt(target.val()) > MAX_PRACTICE_TIME
			|| parseInt(target.val()) < MIN_PRACTICE_TIME) {

		var settings = {
			callbackSuccess : function() {
				showPracticeTitleBefor();
			},
			callbackCancel : function() {
				showPracticeTitleBefor();
			}
		};

		createBootboxDialog('Practice Limit', MSG.E0045, settings);

	} else {
		// Save value from dialog to page
		containerScenario.find('.practiceLimitText').html(target.val());
		containerVocabulary.find('.practiceLimitText').html(target.val());
		// Save value from dialog to hidden
		practiceLimitObject.val(target.val());
	}
}

/**
 * Save scenario from page to dialog
 *
 * @param target
 * @param lessonInfoIndex
 * @param lessonInfoId
 */
function showTheAreaScenario(target, lessonInfoIndex, lessonInfoId) {

	// Get template for dialog
	var contentDialog = $('#template_areaScenario').clone();
	// Display block
	contentDialog.show();
	contentDialog.css({
		'overflow' : 'auto',
		'max-height' : '70%'
	});
	// Remove id - prevent duplicate
	contentDialog.removeAttr('id');

	// Get data
	var whoRows = $(target).parents('.thescenario').find('.theAreaScenario')
			.find('.whorow');
	var youRows = $(target).parents('.thescenario').find('.theAreaScenario')
			.find('.yourow');

	var whoRowsDialog = $(contentDialog).children('.whorow');
	var youRowsDialog = $(contentDialog).children('.yourow');
	// Clone new row
	var newWhoRow = whoRowsDialog.children('.row').clone();
	var newYouRow = youRowsDialog.children('.row').clone();
	// Remove row before insert
	whoRowsDialog.children('.row').remove();
	youRowsDialog.children('.row').remove();

	// Read data for whorow
	setScenarioFromPageToDialog(whoRows, whoRowsDialog, newWhoRow, lessonInfoId);

	// Read data for yowrow
	setScenarioFromPageToDialog(youRows, youRowsDialog, newYouRow, lessonInfoId);
	var speakerOneName = $(target).parents('.thescenario').find(
			'.theAreaScenario').find('.speakerOneName').val();
	var speakerTwoName = $(target).parents('.thescenario').find(
			'.theAreaScenario').find('.speakerTwoName').val();
	contentDialog.find('.whorow').find('.personname').val(speakerOneName);
	contentDialog.find('.yourow').find('.personname').val(speakerTwoName);

	var settings = {
		callbackSuccess : function() {
			// Save scenario
			saveDialogScenerioToPage(lessonInfoIndex);
			$('[data-toggle="popover"]').popover();
		}
	};
	var dialog = createBootboxDialog('Scenario', contentDialog, settings);
	dialog.css('z-index', 1050).next().css('z-index', 1049);
}

/**
 * load all scenario
 *
 * @param target
 */
function loadAllScenario(target) {
	var userinputflg = $(target).parents('.row').find('.scenarioInputed').attr(
			'userinputflg');
	if (userinputflg == 0) {

		var settings = {
			callbackSuccess : function() {
				// Set false for input when ok btn click
				getAllScenario(target);
			}
		};

		createBootboxDialog('All Scenario', MSG.E0046, settings);

	} else {
		getAllScenario(target);
	}

}

var allSentences;
/**
 * get all scenario form server
 *
 * @param target
 */
function getAllScenario(target) {
	// generate all scenario if exist
	if (allSentences) {
		genSelectionScenario(target, allSentences);
	} else {
		// Get all sentences by concepts (start with concept index 0)
		// Pass callback function : genSelectionScenario
		getAllSentencesByConcept(genSelectionScenario, target);
	}
}

/**
 * method generate html select box
 *
 * @param target
 * @param result
 */
function genSelectionScenario(target, result) {
	var htmlInner = '学習構文 <select id="selAllSentences">';
	// Get input object
	var inputObject = $(target).parents('.row').find('.scenarioInputed');
	// generate html for select box
	var lessonIdTemp = 1;
	var count = 0;
	for (var i = 0; i < result.length; i++) {
		var value = '';
		var calljWord = '';
		var categoryWord = '';
		var rubyWord = '';
		var listSlotName = result[i].listSlotName;
		// if lesson id is same
		if (lessonIdTemp == result[i].lessonId) {
			count++;
			lessonIdTemp = result[i].lessonId;
		} else {
			count = 1;
			lessonIdTemp = result[i].lessonId;
		}
		if ($('#learningType').val() == 2) {
			value = result[i].fullSentence;
			rubyWord = result[i].rubyWords;
			if (inputObject.val() == value) {
				htmlInner += '<option rubyWord="' + rubyWord + '" calljWord="' + result[i].calljWord + '" calljLessonNo="' + result[i].lessonId + '" questionName="' + result[i].nameQuestion + '"categoryWord="' + result[i].categoryWord + '" conceptName="' + result[i].nameConcept + '" value="' + value + '" selected>'
				+ value + '</option>';
			} else {
				htmlInner += '<option rubyWord="' + rubyWord + '" calljWord="' + result[i].calljWord + '" calljLessonNo="' + result[i].lessonId + '" questionName="' + result[i].nameQuestion + '"categoryWord="' + result[i].categoryWord + '" conceptName="' + result[i].nameConcept + '" value="' + value + '">'
				+ value + '</option>';
			}
		} else {
			var sentence =  result[i].sentence;
			for (var j = 0; j < sentence.length; j++) {
				var wordType = sentence[j].wordType;
				var typeSlot = sentence[j].typeSlot;
				var romaji = sentence[j].romaji;
				value += sentence[j].kanji;
				rubyWord += getRubyWord(sentence[j]);
				if ( checkSlotName(typeSlot, listSlotName) && wordType != 'Particle'
						&& ((wordType == 'Verb' && romaji != 'desu') || wordType != 'Verb')) {
					calljWord += sentence[j].kanji + ';';
					var category = sentence[j].categoryOrWord.category;
					var word = sentence[j].categoryOrWord.word;

					if (category != null && word == null) {
						categoryWord += sentence[j].categoryOrWord.category + "@";
					} else if(category == null && word != null){
						categoryWord += sentence[j].categoryOrWord.word;
					}else if (category != null && word != null) {
						categoryWord += sentence[j].categoryOrWord.category + "@";
						categoryWord += sentence[j].categoryOrWord.word;
					}
					categoryWord += ';';
				}
			}
			if (inputObject.val() == value) {
				htmlInner += '<option rubyWord="' + rubyWord + '" calljWord="'
						+ calljWord + '" categoryWord="' + categoryWord
						+ '" calljLessonNo="' + result[i].lessonId
						+ '" questionName="' + result[i].nameQuestion
						+ '" conceptName="' + result[i].nameConcept + '" value="'
						+ value + '" selected>' + result[i].lessonId + '-' + count
						+ '.' + value + '</option>';
			} else {
				htmlInner += '<option rubyWord="' + rubyWord + '" calljWord="'
						+ calljWord + '" categoryWord="' + categoryWord
						+ '" calljLessonNo="' + result[i].lessonId
						+ '" questionName="' + result[i].nameQuestion
						+ '" conceptName="' + result[i].nameConcept + '" value="'
						+ value + '">' + result[i].lessonId + '-' + count + '.'
						+ value + '</option>';
			}
		}

	}
	htmlInner += '</select>';
	try {

		var settings = {
			callbackSuccess : function() {
				// Set value
				var value = $('#selAllSentences').val();
				var conceptName = $('#selAllSentences option:selected').attr(
						'conceptName');
				var calljLessonNo = $('#selAllSentences option:selected').attr(
						'calljLessonNo');
				var questionName = $('#selAllSentences option:selected').attr(
						'questionName');
				var contentWords = $('#selAllSentences option:selected').attr(
						'calljWord');
				var categoryWords = $('#selAllSentences option:selected').attr(
					'categoryWord');
				var rubyWords = $('#selAllSentences option:selected').attr(
						'rubyWord');
				inputObject.val(value);
				inputObject.attr('conceptName', conceptName);
				inputObject.attr('calljLessonNo', calljLessonNo);
				inputObject.attr('questionName', questionName);
				inputObject.attr('calljWord', contentWords);
				inputObject.attr('categoryWord', categoryWords);
				inputObject.attr('rubyWord', rubyWords);
				inputObject.attr('userinputflg', 1);
				inputObject.prop('disabled', true);
				// If last row was chosen, add new empty row
				if (inputObject.attr('isNew') == "true") {
					// Enable
					inputObject.removeAttr('isNew');
					// Create new empty row
					createNewInput(inputObject);
				}
			}
		};

		createBootboxDialog('All Scenario', htmlInner, settings);

	} catch (e) {
	}
}

/**
 * Set scenario data from page to dialog
 *
 * @param rows
 * @param rowsDialog
 * @param newRow
 * @param lessonInfoId
 */
function setScenarioFromPageToDialog(rows, rowsDialog, newRow, lessonInfoId) {

	var indexWhoRow = 1;

	// Iterate rows
	$(rows).each(function() {
		var scenarioVal = $(this).find('.scenario').val();
		var scenarioOrderVal = $(this).find('.scenarioOrder').val();
		var scenarioId = $(this).find('.scenarioId').val();
		var part = $(this).find('.scenarioPart').val();
		var isUserInput = $(this).find('.userInputFlg').val();
		var calljLessonNo = $(this).find('.calljLessonNo').val();
		var calljQuestionName = $(this).find('.calljQuestionName').val();
		var calljConceptName = $(this).find('.calljConceptName').val();
		var calljWord = $(this).find('.calljWord').val();
		var categoryWord = $(this).find('.categoryWord').val();
		var rubyWord = $(this).find('.rubyWord').val();

		var row = newRow.clone();
		// Set label
		row.find('p').html('シナリオ' + indexWhoRow);
		var input = row.find('.scenarioInputed');

		// Set data
		input.attr('part', part);
		input.attr('order', scenarioOrderVal);
		input.attr('lessonInfoId', lessonInfoId);
		input.attr('scenarioId', scenarioId);
		input.attr('userInputFlg', isUserInput);
		input.attr('calljLessonNo', calljLessonNo);
		input.attr('questionName', calljQuestionName);
		input.attr('conceptName', calljConceptName);
		input.attr('calljWord', calljWord);
		input.attr('categoryWord', categoryWord);
		input.attr('rubyWord', rubyWord);
		input.removeAttr('onblur');
		// input.removeAttr('disabled');
		input.val(scenarioVal);

		rowsDialog.append(row);

		indexWhoRow++;
	});

	// Add an empty row at the end
	// Set label
	newRow.find('p').html('シナリオ' + indexWhoRow);
	// Set lessonInfoId
	newRow.find('.scenarioInputed').attr('lessonInfoId', lessonInfoId);
	newRow.find('.scenarioInputed').attr('order', indexWhoRow);
	newRow.find('.scenarioInputed').attr('isNew', true);
	rowsDialog.append(newRow);
}

/**
 * Save scenario dialog to page
 *
 * @param lessonInfoIndex
 */
function saveDialogScenerioToPage(lessonInfoIndex) {

	var whoRowDialog = $('.bootbox-body').find('.whorow');
	var youRowDialog = $('.bootbox-body').find('.yourow');

	// Get whoRow container
	var whoRowContainer = $('.lessonScenario_' + lessonInfoIndex).find(
			'.ContenWhoLableAdmin');
	// Get youRow container
	var youRowContainer = $('.lessonScenario_' + lessonInfoIndex).find(
			'.ContenYouLableAdmin');
	// Remove all rows before append
	whoRowContainer.find('.whorow').remove();
	youRowContainer.find('.yourow').remove();

	// Create new row
	var newWhoRow = $('.wrapper-scenario').find('.template_whorow').clone();
	newWhoRow.removeClass('template_whorow');

	var indexRow = 0;

	// Set data for whorow
	indexRow = setScenarioFromDialogToPage(whoRowDialog, newWhoRow,
			lessonInfoIndex, whoRowContainer, true, indexRow);

	// Set data for yourow
	setScenarioFromDialogToPage(youRowDialog, newWhoRow, lessonInfoIndex,
			youRowContainer, false, indexRow);

	// set data for speakers
	whoRowContainer.find('.speakerOneName').val(
			whoRowDialog.find('.personname').val());
	whoRowContainer.find('.speakerTwoName').val(
			youRowDialog.find('.personname').val());
	$('.lessonScenario_' + lessonInfoIndex).find('.ContenWhoLable').html(
			whoRowDialog.find('.personname').val());
	$('.lessonScenario_' + lessonInfoIndex).find('.ContenYouLable').html(
			youRowDialog.find('.personname').val());
}

/**
 * Set scenario data from dialog to page
 *
 * @param rowDialog
 * @param newRow
 * @param lessonInfoIndex
 * @param rowContainer
 * @param isWhoRow
 */
function setScenarioFromDialogToPage(rowDialog, newRow, lessonInfoIndex,
		rowContainer, isWhoRow, indexRow) {

	// Order index
	var orderIndex = 1;
	// Rename class
	if (isWhoRow) {
		newRow.attr('class', 'whorow');
		orderIndex = 1;
	} else {
		newRow.attr('class', 'yourow');
		orderIndex = 1;
	}
	newRow.show();

	rowDialog.find('input.scenarioInputed').each(
			function() {
				var scenarioOrder = orderIndex;
				var scenarioPart = $(this).attr('part');
				var scenarioVal = $(this).val();
				var scenarioId = $(this).attr('scenarioId');
				var lessonInfoId = $(this).attr('lessonInfoId');
				var isUserInput = $(this).attr('userinputflg');
				var calljLessonNo = $(this).attr('calljLessonNo');
				var calljQuestionName = $(this).attr('questionName');
				var calljConceptName = $(this).attr('conceptName');
				var calljWord = $(this).attr('calljWord');
				var categoryWord = $(this).attr('categoryWord');
				var rubyWord = $(this).attr('rubyWord');
				var scenarioValRuby = '';

				if ($.trim(scenarioVal) !== '') {
					var row = newRow.clone();

					if ($.trim(categoryWord) !== '') {
						scenarioValRuby = setCategoryPopup(categoryWord, calljWord, scenarioVal);
					} else {
						scenarioValRuby = scenarioVal;
					}
					if ($.trim(rubyWord) !== '') {
						scenarioValRuby = setRubyWord(scenarioValRuby, rubyWord);
					}

					// Set text
					row.find('.textscenario').html(
							scenarioOrder + '.' + scenarioValRuby);

					var nameAttr = 'lessonInfoForms[' + lessonInfoIndex
							+ '].scenarioForms[' + indexRow + ']';

					// Set scenario name
					row.find('.scenario').attr('name', nameAttr + '.scenario');
					row.find('.scenario').val(scenarioVal);

					// Set scenario order
					row.find('.scenarioOrder').attr('name',
							nameAttr + '.scenarioOrder');
					row.find('.scenarioOrder').val(scenarioOrder);

					// Set scenario id
					row.find('.scenarioId').attr('name', nameAttr + '.id');
					row.find('.scenarioId').val(scenarioId);

					// Set scenariolessonInfoId
					row.find('.scenariolessonInfoId').attr('name',
							nameAttr + '.lessonInfoId');
					row.find('.scenariolessonInfoId').val(lessonInfoId);

					// Set scenario part
					row.find('.scenarioPart').attr('name',
							nameAttr + '.scenarioPart');
					row.find('.scenarioPart').val(scenarioPart);

					// Set userinputflg
					row.find('.userInputFlg').attr('name',
							nameAttr + '.userInputFlg');
					row.find('.userInputFlg').val(isUserInput);

					// Set concept name
					row.find('.calljConceptName').attr('name',
							nameAttr + '.calljConceptName');
					row.find('.calljConceptName').val(calljConceptName);

					// Set calljQuestionName
					row.find('.calljQuestionName').attr('name',
							nameAttr + '.calljQuestionName');
					row.find('.calljQuestionName').val(calljQuestionName);

					// Set calljLessonNo
					row.find('.calljLessonNo').attr('name',
							nameAttr + '.calljLessonNo');
					row.find('.calljLessonNo').val(calljLessonNo);

					// Set calljWord
					row.find('.calljWord')
							.attr('name', nameAttr + '.calljWord');
					row.find('.calljWord').val(calljWord);

					// Set categoryWord
					row.find('.categoryWord')
							.attr('name', nameAttr + '.categoryWord');
					row.find('.categoryWord').val(categoryWord);

					// Set rubyWord
					row.find('.rubyWord').attr('name', nameAttr + '.rubyWord');
					row.find('.rubyWord').val(rubyWord);

					// Append whoRow/youRow
					rowContainer.append(row);
					orderIndex++;
					indexRow++;
				}
			});

	return indexRow;
}

/**
 * method enable for input scenario
 *
 * @param target
 */
function inputScenario(target) {
	var userinputflg = $(target).parents('.row').find('.scenarioInputed').attr(
			'userinputflg');
	if (userinputflg == 1) {

		var settings = {
			callbackSuccess : function() {
				// Set false for input when ok btn click
				$(target).closest('.row').find('.scenarioInputed').prop(
						'disabled', false);
				$(target).closest('.row').find('.scenarioInputed').attr(
						'userinputflg', 0);
				$(target).closest('.row').find('.scenarioInputed').removeAttr(
						'isNew');
				$(target).closest('.row').find('.scenarioInputed').removeAttr(
						'calljWord');
				$(target).closest('.row').find('.scenarioInputed').removeAttr(
				'categoryWord');
				$(target).closest('.row').find('.scenarioInputed').removeAttr(
						'rubyWord');
			},
			callbackCancel : function() {
				// Set true for input when ok btn click
				$(target).closest('.row').find('.scenarioInputed').prop(
						'disabled', true);
			}
		};

		createBootboxDialog('All Scenario', MSG.E0046, settings);

	} else {
		$(target).closest('.row').find('.scenarioInputed').prop('disabled',
				false);
		$(target).closest('.row').find('.scenarioInputed').attr('userinputflg',
				0);
	}
}

/**
 * Create new row on dialog scenario
 *
 * @param target
 */
function createNewInput(target) {
	$(target).removeAttr('isNew');
	if ($(target).val().length > 0) {

		var areaScenario = $(target).closest('.areaScenario');
		var conceptname = $(target).attr('conceptname');
		var calljLessonNo = $(target).attr('calljLessonNo');
		var questionName = $(target).attr('questionName');
		var calljWord = $(target).attr('calljWord');
		var categoryWord = $(target).attr('categoryWord');
		var rubyWord = $(target).attr('rubyWord');
		var count = areaScenario.children('.row').length;
		var isWhoRow = false;

		// Check whorow or yourow
		if (areaScenario.hasClass('whorow')) {
			isWhoRow = true;
		}

		var row = $(target).closest('.row').clone();
		var scenarioInputed = $(row).find('.scenarioInputed');
		scenarioInputed.prop('disabled', true);
		scenarioInputed.val('');
		scenarioInputed.attr('onblur', 'createNewInput(this)');
		scenarioInputed.attr('order', (count + 1));
		scenarioInputed.attr('isNew', true);
		scenarioInputed.removeAttr('userinputflg');
		scenarioInputed.removeAttr('conceptname');
		scenarioInputed.removeAttr('calljLessonNo');
		scenarioInputed.removeAttr('questionName');
		scenarioInputed.removeAttr('calljWord');
		scenarioInputed.removeAttr('categoryWord');
		scenarioInputed.removeAttr('rubyWord');
		$(row).find('p').html('シナリオ' + (count + 1));
		$(target).removeAttr('onblur');
		$(target).closest('.areaScenario').append(row);

		// If row was whorow, copy to yourow if not exist
		if (isWhoRow) {
			var copyValue = $(target).val();
			var copyUserInput = $(target).attr('userinputflg');
			var youRowContainer = areaScenario.next('.yourow');
			var youRowObjects = youRowContainer.children('.row');
			var youRowClone = $(youRowObjects[0]).clone();

			if (count == youRowObjects.length) {
				var rowObject = $(youRowObjects[count - 1]);

				// If not have data, copy from above
				if (rowObject.find('.scenarioInputed').val() === '') {
					// Edit yourow
					rowObject.find('.scenarioInputed').attr('userinputflg',
							copyUserInput);
					rowObject.find('.scenarioInputed').attr('conceptname',
							conceptname);
					rowObject.find('.scenarioInputed').attr('calljLessonNo',
							calljLessonNo);
					rowObject.find('.scenarioInputed').attr('questionName',
							questionName);
					rowObject.find('.scenarioInputed').attr('calljWord',
							calljWord);
					rowObject.find('.scenarioInputed').attr('categoryWord',
							categoryWord);
					rowObject.find('.scenarioInputed').attr('rubyWord',
							rubyWord);
					rowObject.find('.scenarioInputed').prop('disabled', true);
					rowObject.find('.scenarioInputed').val(copyValue);
					rowObject.find('.scenarioInputed').removeAttr('onblur');
				}

				// Check last yourow
				var lastYouRow = $(youRowObjects[youRowObjects.length - 1]);

				if (lastYouRow.find('.scenarioInputed').attr('isNew') == "true") {
					// Append empty new row
					rowObject.find('.scenarioInputed').removeAttr('isNew');
					youRowClone.find('.scenarioInputed').prop('disabled', true);
					youRowClone.find('.scenarioInputed').attr('isNew', true);
					youRowClone.find('.scenarioInputed').val('');
					youRowClone.find('.scenarioInputed').attr('onblur',
							'createNewInput(this)');
					youRowClone.find('.scenarioInputed').attr('order',
							(youRowObjects.length + 1));
					youRowClone.find('.scenarioInputed').removeAttr(
							'userinputflg');
					youRowClone.find('.scenarioInputed').removeAttr(
							'conceptname');
					youRowClone.find('.scenarioInputed').removeAttr(
							'calljLessonNo');
					youRowClone.find('.scenarioInputed').removeAttr(
							'questionName');
					youRowClone.find('.scenarioInputed')
							.removeAttr('calljWord');
					youRowClone.find('.scenarioInputed')
							.removeAttr('categoryWord');
					youRowClone.find('.scenarioInputed').removeAttr('rubyWord');
					youRowClone.find('p').html(
							'シナリオ' + (youRowObjects.length + 1));
					youRowContainer.append(youRowClone);
				}
			}
		}
	}
}

/**
 * Check exist in list slot name
 *
 * @param wordType
 * @param listSlotName
 * @returns boolean
 */
function checkSlotName(typeSlot, listSlotName) {
	if (checkNotEmpty(typeSlot)) {
		for (var i = 0; i < listSlotName.length; i++) {
			if (listSlotName[i] == typeSlot) {
				return true;
			}
		}
	}
	return false;
}