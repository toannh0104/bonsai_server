/**
 * event for link TheAreaScenario
 */
function showTheAreaVocabulary(target, lessonInfoIndex, lessonInfoId) {
	var whoRow = $('.lessonScenario_' + currentLessonIndex).find('.theAreaScenario').find('.whorow');
	var lessonType = $('#lessonType').val();
	// if who row exist
	if (whoRow.length == 0 && lessonType == 0) {
		bootbox.alert(getMessage([ currentLessonNo ], MSG.E0048));
	} else {
		// Get template for dialog
		var contentDialog = $('#template_areaVocabulary').clone();
		// Display block
		contentDialog.show();
		contentDialog.css({
			'overflow' : 'auto',
			'max-height' : '70%'
		});
		// Remove id - prevent duplicate
		contentDialog.removeAttr('id');

		// Get data
		var dataRows = $(target).parents('.theLessonVocabulary').find('.Conten')
		.find('.row');

		var vocaRowsDialog = $(contentDialog).children('.rowVocabulary');
		// Clone new row
		var newVocaRow = vocaRowsDialog.children('.row').clone();
		// Remove row before insert
		vocaRowsDialog.children('.row').remove();

		// Read data for dataRows
		setVocabularyFromPageToDialog(dataRows, vocaRowsDialog, newVocaRow,
				lessonInfoId);

		var settings = {
				callbackSuccess : function() {
					// Save Vocabulary
					saveDialogVocabularyToPage(lessonInfoIndex);
				}
		};

		var dialog = createBootboxDialog('Vocabulary', contentDialog, settings);

		dialog.css('z-index', 1050).next().css('z-index', 1049);
		dialog.find('.modal-dialog').css('width', '900px');
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
function setVocabularyFromPageToDialog(rows, rowsDialog, newRow, lessonInfoId) {

	var indexWhoRow = 1;

	// Iterate rows
	$(rows).each(function() {
		var vocabularyVal = $(this).find('.vocabulary').val();
		var vocabularyOrderVal = $(this).find('.vocabularyOrder').val();
		var vocabularyId = $(this).find('.vocabularyId').val();
		var isUserInput = $(this).find('.vocabularyUserInputFlg').val();
		var rubyWord = $(this).find('.rubyWord').val();
		var vocaEnglish = $(this).find('.vocaEnglish').val();
		var vocaHiragana = $(this).find('.vocaHiragana').val();
		var vocaCategory = $(this).find('.vocaCategory').val();

		var row = newRow.clone();
		// Set label
		row.find('p').html('#' + indexWhoRow);
		var input = row.find('.vocabularyInputed');

		// Set data
		input.attr('order', vocabularyOrderVal);
		input.attr('lessonInfoId', lessonInfoId);
		input.attr('vocabularyId', vocabularyId);
		input.attr('userInputFlg', isUserInput);
		input.attr('rubyWord', rubyWord);
		input.removeAttr('onblur');
		input.val(vocabularyVal);

		var vocaCategoryInput = row.find('.vocaCategory');
		vocaCategoryInput.val(vocaCategory);
		vocaCategoryInput.removeAttr('onblur');

		var vocaHiraganaInput = row.find('.vocaHiragana');
		vocaHiraganaInput.val(vocaHiragana);
		vocaHiraganaInput.removeAttr('onblur');

		var vocaEnglishInput = row.find('.vocaEnglish');
		vocaEnglishInput.val(vocaEnglish);
		vocaEnglishInput.removeAttr('onblur');

		rowsDialog.append(row);

		indexWhoRow++;
	});

	// Add an empty row at the end
	// Set label
	newRow.find('p').html('#' + indexWhoRow);
	var vocabularyInputed = newRow.find('.vocabularyInputed');
	// Set lessonInfoId
	vocabularyInputed.attr('lessonInfoId', lessonInfoId);
	vocabularyInputed.attr('order', indexWhoRow);
	vocabularyInputed.attr('isNew', true);
	rowsDialog.append(newRow);
}

/**
 * Save scenario dialog to page
 *
 * @param lessonInfoIndex
 */
function saveDialogVocabularyToPage(lessonInfoIndex) {

	var vocaRowDialog = $('.bootbox-body').find('.rowVocabulary');

	// Get whoRow container
	var vocaRowContainer = $('.lessonVocabulary_' + lessonInfoIndex).find(
			'.Conten');
	// Remove all rows before append
	vocaRowContainer.find('.row').remove();

	// Create new row
	var newVocaRow = $('.wrapper-vocabulary').find('.template_rowVocabulary')
			.clone();
	newVocaRow.removeClass('template_rowVocabulary');
	newVocaRow.attr('class', 'row');

	// Set data for VocaRow
	setVocabularyFromDialogToPage(vocaRowDialog, newVocaRow, lessonInfoIndex,
			vocaRowContainer);
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
function setVocabularyFromDialogToPage(rowDialog, newRow, lessonInfoIndex,
		rowContainer) {

	// New row display block
	newRow.show();

	rowDialog.find('input.vocabularyInputed').each(
			function(indexRow) {
				var vocabularyOrder = indexRow + 1;
				var vocabularyVal = $(this).val();
				var vocabularyId = $(this).attr('vocabularyId');
				var lessonInfoId = $(this).attr('lessonInfoId');
				var isUserInput = $(this).attr('userinputflg');
				var rubyWord = $(this).attr('rubyWord');
				var vocaEnglish = $(this).closest('.row').find('.vocaEnglish').val();
				var vocaHiragana = $(this).closest('.row').find('.vocaHiragana').val();
				var vocaCategory = $(this).closest('.row').find('.vocaCategory').val();

				if (checkInput(vocabularyVal, vocaEnglish, vocaHiragana)) {
					var vocabularyValRuby = '';
					if ($.trim(vocabularyVal) !== '') {
						var row = newRow.clone();

						if ($.trim(rubyWord) !== '') {
							vocabularyValRuby = setRubyWord(vocabularyVal, rubyWord);
						} else {
							vocabularyValRuby = vocabularyVal;
							row.find('.col-hafl').addClass('non-ruby');
						}

						// Set text
						row.find('.col-hafl').html(
								vocabularyOrder + '.' + vocabularyValRuby);

						var nameAttr = 'lessonInfoForms[' + lessonInfoIndex
						+ '].vocabularyForms[' + indexRow + ']';

						// Set scenario name
						row.find('.vocabulary').attr('name',
								nameAttr + '.vocabulary');
						row.find('.vocabulary').val(vocabularyVal);

						// Set scenario order
						row.find('.vocabularyOrder').attr('name',
								nameAttr + '.vocabularyOrder');
						row.find('.vocabularyOrder').val(vocabularyOrder);

						// Set scenario id
						row.find('.vocabularyId').attr('name', nameAttr + '.id');
						row.find('.vocabularyId').val(vocabularyId);

						// Set scenariolessonInfoId
						row.find('.vocabularylessonInfoId').attr('name',
								nameAttr + '.lessonInfoId');
						row.find('.vocabularylessonInfoId').val(lessonInfoId);

						// Set userinputflg
						row.find('.vocabularyUserInputFlg').attr('name',
								nameAttr + '.userInputFlg');
						row.find('.vocabularyUserInputFlg').val(isUserInput);

						// Set rubyWord
						row.find('.rubyWord').attr('name',
								nameAttr + '.rubyWord');
						row.find('.rubyWord').val(rubyWord);

						// Set vocaHiragana
						row.find('.vocaHiragana').attr('name',
								nameAttr + '.vocabularyKanaName');
						row.find('.vocaHiragana').val(vocaHiragana);

						// Set vocaCategory
						row.find('.vocaCategory').attr('name',
								nameAttr + '.vocabularyCategories');
						row.find('.vocaCategory').val(vocaCategory);

						// Set vocaEnglish
						row.find('.vocaEnglish').attr('name',
								nameAttr + '.vocabularyEnglishName');
						row.find('.vocaEnglish').val(vocaEnglish);

						// Append whoRow/youRow
						rowContainer.append(row);
					}
				} else {
					bootbox.alert(MSG.E0049);
				}
			});
}
/**
 * enable for input vocabulary
 *
 * @param target
 */
function inputVocabulary(target) {
	var userinputflg = $(target).parents('.row').find('.vocabularyInputed')
			.attr('userinputflg');
	if (userinputflg == 1) {

		var settings = {
			callbackSuccess : function() {
				// Set false for input when ok btn click
				$(target).closest('.row').find('.vocaEnglish').prop(
						'disabled', false);
				$(target).closest('.row').find('.vocaCategory').prop(
						'disabled', false);
				$(target).closest('.row').find('.vocaHiragana').prop(
						'disabled', false);
				$(target).closest('.row').find('.vocabularyInputed').prop(
						'disabled', false);
				$(target).closest('.row').find('.vocabularyInputed').attr(
						'userinputflg', 0);
				$(target).closest('.row').find('.vocabularyInputed').removeAttr('isNew');
				$(target).closest('.row').find('.vocabularyInputed').removeAttr('eng');
			},
			callbackCancel : function() {
				// Set true for input when ok btn click
				$(target).closest('.row').find('.vocabularyInputed').prop(
						'disabled', true);
				$(target).closest('.row').find('.vocaEnglish').prop(
						'disabled', true);
				$(target).closest('.row').find('.vocaCategory').prop(
						'disabled', true);
				$(target).closest('.row').find('.vocaHiragana').prop(
						'disabled', true);
			}
		};

		createBootboxDialog('All Vocabulary',
				'Are you sure change input type?', settings);

	} else {
		$(target).closest('.row').find('.vocabularyInputed').prop('disabled',
				false);
		$(target).closest('.row').find('.vocaEnglish').prop('disabled',
				false);
		$(target).closest('.row').find('.vocaCategory').prop('disabled',
				false);
		$(target).closest('.row').find('.vocaHiragana').prop('disabled',
				false);
		$(target).closest('.row').find('.vocabularyInputed').attr(
				'userinputflg', 0);
	}
}
/**
 * create new line for input
 *
 * @param target
 */
function createNewInputVocabulary(target) {
	if ($(target).val().length > 0) {

		var row = $(target).closest('.row').clone();
		var vocabularyInputed = $(row).find('.vocabularyInputed');
		var vocaEnglish = $(row).find('.vocaEnglish');
		var vocaCategory = $(row).find('.vocaCategory');
		var vocaHiragana = $(row).find('.vocaHiragana');

		if (vocabularyInputed.val().length > 0 && vocaEnglish.val().length > 0
				&& vocaHiragana.val().length > 0) {
			var count = $(target).closest('.areaVocabulary').children('.row').length;

			resetNewInputElement(vocaEnglish, count);
			resetNewInputElement(vocaCategory, count);
			resetNewInputElement(vocabularyInputed, count);
			resetNewInputElement(vocaHiragana, count);

			$(row).find('p').html('#' + (count + 1));
			$(target).closest('.row').find('.vocabularyInputed').removeAttr('onblur').removeAttr('isNew');
			$(target).closest('.row').find('.vocaCategory').removeAttr('onblur').removeAttr('isNew');
			$(target).closest('.row').find('.vocaEnglish').removeAttr('onblur').removeAttr('isNew');
			$(target).closest('.row').find('.vocaHiragana').removeAttr('onblur').removeAttr('isNew');
			$(target).closest('.areaVocabulary').append(row);
		}
	}
}

/**
 * create new line for input
 *
 * @param target
 */
function resetNewInputElement(target, count) {
	target.prop('disabled', true);
	target.val('');
	target.attr('onblur', 'createNewInputVocabulary(this)');
	target.attr('order', (count + 1));
	target.attr('isNew', true);
	target.removeAttr('userinputflg');
	target.removeAttr('eng');

}

/**
 * load all vocabulary
 *
 * @param target
 */
function loadAllVocabulary(target, status) {
	var userinputflg = $(target).parents('.row').find('.vocabularyInputed')
			.attr('userinputflg');
	if (userinputflg == 0 && status == 'vocabulary') {

		var settings = {
			callbackSuccess : function() {
				// Set false for input when ok btn click
				getAllVocabulary(target, 0, status);
			}
		};

		createBootboxDialog('All Scenario', MSG.E0046, settings);

	} else {
		getAllVocabulary(target, 0, status);
	}

}

var allVocabulary = {};
/**
 * get all vocabulary for server
 *
 * @param target
 * @param conceptIndex
 */
function getAllVocabulary(target, conceptIndex, status) {
	// get all vocabulary for server
	var lessonMethod = getModeLanguage(parseInt(currentLessonNo));

	var vocabulariesResult = new Array();

	// Get all vocabularies by concepts (start with concept index 0)
	var learningType = $('#learningType').val();
	if (learningType == 2) {
		getAllVocabulariesBySecurityMode(target, vocabulariesResult);
	} else {
		getAllVocabulariesByConcept(0, vocabulariesResult, lessonMethod, target, status);
	}
}

/**
 * Get all vocabularies by concepts (from first to last)
 *
 * @param conceptIndex
 * @param vocabulariesResult
 * @param lessonMethod
 * @param target
 */
function getAllVocabulariesByConcept(conceptIndex, vocabulariesResult,
		lessonMethod, target, status) {
	var listConceptName = '';

	if ($('#lessonType').val() == 0) {
		listConceptName = getAllConceptName().toString();
	}

	$.ajax({
				url : url + "/admin/allVocabulary",
				data : {
					"listConceptName" : listConceptName,
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

						// Save all vocabularies from current concepts
						vocabulariesResult = vocabulariesResult
								.concat(response.result);

						if (status == 'vocabulary') {
							// generate all vocabulary
							genAllVocabulary(target, vocabulariesResult);
						} else {
							// generate all category
							genAllCategory(target, response.listCategory);
						}
					} else {
						bootbox.alert(response.message);
					}
				}
			});
}

/**
 * generate html for selection
 *
 * @param target
 * @param result
 */
function genAllVocabulary(target, result) {
	var htmlInner = '学習構文 <select id="selAllSentences">';
	// Get input object
	var inputObject = $(target).parents('.row').find('.vocabularyInputed');
	// generate html select box
	for (var i = 0; i < result.length; i++) {
		var rubyWord = '';
		var value = '';
		value += result[i].kanji;

		// get rubyword by learning type
		if ($('#learningType').val() == 2) {
			rubyWord = result[i].rubyWords;
		} else {
			rubyWord = getRubyWord(result[i]);
		}

		if (inputObject.val() == value) {
			htmlInner += '<option rubyWord="' + rubyWord + '" kanaWord="' + result[i].kana + '"categoriesWord="' + result[i].categoryList + '"eng="' + result[i].english + '" value="' + value + '" selected>' + value + '</option>';
		} else {
			htmlInner += '<option rubyWord="' + rubyWord + '" kanaWord="' + result[i].kana + '"categoriesWord="' + result[i].categoryList + '"eng="' + result[i].english + '" value="' + value + '">' + value + '</option>';
		}

	}
	htmlInner += '</select>';
	try {

		var settings = {
			callbackSuccess : function() {
				// Get input object
				var inputKanaWordObject = $(target).parents('.row').find('.vocaHiragana');
				var inputCategoriesWordObject = $(target).parents('.row').find('.vocaCategory');
				var inputEngWordObject = $(target).parents('.row').find('.vocaEnglish');
				// Set value
				var value = $('#selAllSentences').val();
				var engValue = $('#selAllSentences option:selected').attr('eng');
				var rubyWord = $('#selAllSentences option:selected').attr('rubyWord');
				var kanaWord = $('#selAllSentences option:selected').attr('kanaWord');
				var categoriesWord = $('#selAllSentences option:selected').attr('categoriesWord');
				inputObject.attr('userinputflg', 1);
				inputObject.prop('disabled', true);
				inputObject.val(value);
				inputObject.attr('rubyWord', rubyWord);
				inputKanaWordObject.val(kanaWord);
				inputCategoriesWordObject.val(categoriesWord);
				inputEngWordObject.val(engValue);
				inputKanaWordObject.prop('disabled', true);
				inputCategoriesWordObject.prop('disabled', true);
				inputEngWordObject.prop('disabled', true);

				// If last row was chosen, add new empty row
				if (inputObject.attr('isNew') == "true") {
					// Enable
					inputObject.removeAttr('isNew');
					// Create new empty row
					createNewInputVocabulary(inputObject);
				}
			}
		};

		createBootboxDialog('All Scenario', htmlInner, settings);

	} catch (e) {
	}
}

/**
 * generate category for selection
 *
 * @param target
 * @param result
 */
function genAllCategory(target, result) {
	var htmlInner = '<div class="col-hafl">カテゴリ</div><select multiple id="selAllCategory" class="col-hafl">';
	// Get input object
	var inputObject = $(target).parents('.row').find('.vocaCategory');

	// generate html select box
	for (var i = 0; i < result.length; i++) {
		var value = result[i];
		if (inputObject.val() == value) {
			htmlInner += '<option value="' + value + '" selected>' + value + '</option>';
		} else {
			htmlInner += '<option value="' + value + '">' + value + '</option>';
		}

	}
	htmlInner += '</select>';
	try {

		var settings = {
			callbackSuccess : function() {
				// Set value
				var value = $('#selAllCategory').val();
				inputObject.attr('userinputflg', 1);
				inputObject.prop('disabled', false);
				inputObject.val(value);

				// If last row was chosen, add new empty row
				if (inputObject.attr('isNew') == "true") {
					// Enable
					inputObject.removeAttr('isNew');
					// Create new empty row
					createNewInputVocabulary(inputObject, status);
				}
			}
		};

		createBootboxDialog('All Scenario', htmlInner, settings);

	} catch (e) {
	}
}

/**
 * Get all vocabularies by learning type (1 : Japaneses or 2 : Security)
 *
 * @param conceptIndex
 */
function getAllVocabulariesBySecurityMode(target, vocabulariesResult) {
	$.ajax({
				url : url + "/admin/allVocabularyByLearningType",
				data : {
					"learningType" : 1,
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

						// Save all vocabularies from current concepts
						vocabulariesResult = vocabulariesResult
								.concat(response.result);

						// generate all vocabulary
						genAllVocabulary(target, vocabulariesResult);
					} else {
						bootbox.alert(response.message);
					}
				}
			});
}