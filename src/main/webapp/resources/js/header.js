var url;
var learningType;

/**
 * Set up ajax loading
 */
$(document).ajaxStart(function() {
	$('#loader').show();
}).ajaxStop(function() {
	$('#loader').hide();
});
/**
 * Binding event for ids
 */
$(document)
		.ready(
				function() {
					url = $('#url').val();
					learningType = $('#learningType').val();

					$('#editlessonName')
							.click(
									function() {
										// Get data from page
										var lessonName = $("#lessonName").val();
										var htmlContent = '<div class="namedag"><span><p>名称： </p><input id="nametext" value="'
												+ lessonName
												+ '"/></span></div>';

										var settings = {
												callbackSuccess : function() {
													btnOk();
												},
											};

										createBootboxDialog("Book Name", htmlContent, settings);

									});
					$('#editMethod')
							.click(
									function() {
										var content = [ {
											"id" : "0",
											"value" : "会話学習"
										}, {
											"id" : "1",
											"value" : "単語学習"
										} ];
										// Get data from page
										var lessonType = $("#lessonType").val();
										var htmlContent = '<div class="methoddag"><span><p>学習方式： </p>';
										var selectList = "<select id='methodtext'>";
										for ( var c in content) {
											if (lessonType == content[c].id) {
												selectList += "<option value='"
														+ content[c].id
														+ "' selected>"
														+ content[c].value
														+ "</option>";
											} else {
												selectList += "<option value='"
														+ content[c].id + "'>"
														+ content[c].value
														+ "</option>";
											}
										}
										selectList += "</select>";
										htmlContent += selectList
												+ '</span></div>';

										var settings = {
												callbackSuccess : function() {
													confirmLessonType();
												},
											};

										createBootboxDialog("Book Type", htmlContent, settings);

									});
					$('#editLevel')
							.click(
									function() {
										var content = [ {
											"id" : "1",
											"value" : "N1"
										}, {
											"id" : "2",
											"value" : "N2"
										}, {
											"id" : "3",
											"value" : "N3"
										}, {
											"id" : "4",
											"value" : "N4"
										}, {
											"id" : "5",
											"value" : "N5"
										} ];
										var level = $("#level").val();
										var htmlContent = '<div class="leveldag"><span><p>学習レベル： </p>';
										var selectList = "<select id='leveltext'>";
										for ( var c in content) {
											if (level == content[c].id) {
												selectList += "<option value='"
														+ content[c].id
														+ "' selected>"
														+ content[c].value
														+ "</option>";
											} else {
												selectList += "<option value='"
														+ content[c].id + "'>"
														+ content[c].value
														+ "</option>";
											}
										}
										selectList += "</select>";
										htmlContent += selectList
												+ '</span></div>';

										var settings = {
												callbackSuccess : function() {
													btnOk();
												},
											};

										createBootboxDialog("Book Level", htmlContent, settings);

									});
					$('.administrator')
							.click(
									function() {
										var content = [ {
											"id" : "save_as",
											"value" : "新規登録 "
										}, {
											"id" : "save",
											"value" : "上書登録"
										}, {
											"id" : "delete",
											"value" : "削除 "
										} ];
										var htmlContent = '<div class="administratordag">';
										var isNew = $('#isNew').val();
										for ( var c in content) {
											if (isNew == 'true') {
												if (content[c].id == 'save' || content[c].id == 'delete') {
													htmlContent += "<input type='button' class='btn btn-primary btn-block' value="
														+ content[c].value
														+ " id="
														+ content[c].id
														+ " onclick='"
														+ content[c].id
														+ "_lesson();' disabled />";
												} else {
													htmlContent += "<input type='button' class='btn btn-primary btn-block' value="
														+ content[c].value
														+ " id="
														+ content[c].id
														+ " onclick='"
														+ content[c].id
														+ "_lesson();'/>";
												}
											} else {
												htmlContent += "<input type='button' class='btn btn-primary btn-block' value="
													+ content[c].value
													+ " id="
													+ content[c].id
													+ " onclick='"
													+ content[c].id
													+ "_lesson();'/>";
											}
										}

										var settings = {
												closeButton : false,
												hideClass : "btn-success"
											};

										createBootboxDialog("Administrator", htmlContent, settings);

									});

					var error = $('.error').text();
					if (error != '') {
						$('.error').hide();
						bootbox.alert(error);
					}
				});

/**
 * Overwrite lesson
 */
function save_as_lesson() {

	// Pass validation
	if (checkValidationLessonHeader() && checkValidationForSave()) {

		// Remove lessonInfo form (if not yet set lesson range)
		removeUnnecessaryElementFromSubmitForm();
		$.ajax({
			type : "POST",
			url : url + "/admin/saveAs",
			data : $("#lessonForm").serialize(), // serializes the form's
			// elements.
			success : function(data) {
				var resultValue = [];
				var result = data.lessonResult;
				if (result.status == 'OK') {
					var form = document.forms["lessonForm"];
					var type = 'lesson';
					if (learningType == '2') {
						type = 'security';
					}
					form.action = url + "/admin/" + type + "/" + result.message;
					form.submit();
				} else {
					if (result.message == 'E0016') {
						resultValue.push($('.name').html());
						bootbox.hideAll();
						bootbox.alert(getMessage(resultValue, MSG.E0016));
					} else if (result.message == 'E0017') {
						bootbox.hideAll();
						resultValue.push('新規登録');
						bootbox.alert(getMessage(resultValue, MSG.E0017));
					} else {
						bootbox.alert(result.message);
					}
				}
			}
		});
	}
}

/**
 * Save lesson (insert/update)
 */
function save_lesson() {

	// Pass validation
	if (checkValidationLessonHeader() && checkValidationForSave()) {

		// Remove lessonInfo form (if not yet set lesson range)
		removeUnnecessaryElementFromSubmitForm();

		$.ajax({
			type : "POST",
			url : url + "/admin/save",
			data : $("#lessonForm").serialize(), // serializes the form's
			// elements.
			success : function(data) {
				var resultValue = null;
				var result = data.lessonResult;
				if (result.status == 'OK') {
					var form = document.forms["lessonForm"];
					var type = 'lesson';
					if (learningType == '2') {
						type = 'security';
					}
					form.action = url + "/admin/" + type + "/" + result.message;
					form.submit();
				} else {
					if (result.message == 'E0016') {
						bootbox.hideAll();
						resultValue = '[' + $('.name').html() + ']';
						bootbox.alert(getMessageValue(resultValue, MSG.E0016));
						return false;
					} else if (result.message == 'E0017') {
						bootbox.hideAll();
						resultValue = '[新規登録]';
						bootbox.alert(getMessage(resultValue, MSG.E0017));
						return false;
					} else {
						bootbox.alert(result.message);
					}
				}
			}
		});
	}
}

/**
 * Delete lesson
 */
function delete_lesson() {
	var form = document.forms["lessonForm"];
	form.action = url + "/admin/delete";
	form.submit();
}

/**
 * Remove lessonInfo form (if not yet set lesson range)
 */
function removeUnnecessaryElementFromSubmitForm() {

	// Remove lessonInfo form (if not yet set lesson range)
	if (lessonInfoArray.length === 0) {
		$('.lesson_info').remove();
		$('.thescenario').remove();
		$('.theLessonVocabulary').remove();
	}

}

/**
 * Common function for get current information of lesson
 */
function getLessonData(index) {

	// Init lesson object
	var lessonData = {};

	var containerLessonInfo = $('.lesson_info_' + index);
	var containerScenario = $('.lessonScenario_' + index);
	var containerVocabulary = $('.lessonVocabulary_' + index);

	// Get data
	lessonData.practiceLimit = containerLessonInfo.find('.practiceLimitHidden')
			.val();
	lessonData.practiceMemoryCondition = containerLessonInfo.find(
			'.practiceMemoryConditionHidden').val();
	lessonData.practiceWritingCondition = containerLessonInfo.find(
			'.practiceWritingConditionHidden').val();
	lessonData.practiceConversationCondition = containerLessonInfo.find(
			'.practiceConversationConditionHidden').val();
	lessonData.testMemoryCondition = containerLessonInfo.find(
			'.testMemoryConditionHidden').val();
	lessonData.testWritingCondition = containerLessonInfo.find(
			'.testWritingConditionHidden').val();
	lessonData.testConversationCondition = containerLessonInfo.find(
			'.testConversationConditionHidden').val();

	lessonData.scenarioName = containerScenario.find('.scenarioNameHidden')
			.val();
	lessonData.scenarioSyntax = containerScenario.find('.syntaxTextHidden')
			.val();

	lessonData.vocabularyName = containerVocabulary.find(
			'.lessonVocabularyNameHidden').val();

	return lessonData;
}

// confirm lesson type
function confirmLessonType() {
	var methodtext = $('#methodtext').val();
	var methodtextOld = $('#lessonType').val();
	// lesson type has change
	if (methodtextOld !== methodtext && methodtextOld !== '') {

		var settings = {
				callbackSuccess : function() {
					// check method is change
					if (checkNotEmpty(methodtext)) {
						if (methodtext == '0') {
							$('.method').html("会話学習");
							$('#scenario').removeClass('invisible');
						} else {
							$('.method').html("単語学習");
							$('#scenario').addClass('invisible');
						}
						$('#lessonType').val(methodtext);
					}
				},
				callbackCancel : function() {
					// if cancel callback editMethod click
					$("#editMethod").trigger("click");
				},
				closeButton : false
		};
		createBootboxDialog('Confirm Lesson type', MSG.E0044, settings);

	} else {
		// lesson type not change
		btnOk();
	}
}

function btnOk() {

	// check name is change
	var nametext = $('#nametext').val();
	if (checkNotEmpty(nametext)) {
		$('.name').html(nametext);
		$('#lessonName').val(nametext);
	}

	// check method is change
	var methodtext = $('#methodtext').val();
	if (checkNotEmpty(methodtext)) {
		if (methodtext == '0') {
			$('.method').html("会話学習");
			$('#scenario').removeClass('invisible');
		} else {
			$('.method').html("単語学習");
			$('#scenario').addClass('invisible');
		}
		$('#lessonType').val(methodtext);
	}

	// check level is change
	var leveltext = $('#leveltext').val();
	var levelEle = $('.level');
	if (checkNotEmpty(leveltext)) {
		switch (leveltext) {
		case "1":
			levelEle.html("N1");
			break;
		case "2":
			levelEle.html("N2");
			break;
		case "3":
			levelEle.html("N3");
			break;
		case "4":
			levelEle.html("N4");
			break;
		case "5":
			levelEle.html("N5");
			break;
		default:
			break;
		}
		$('#level').val(leveltext);
	}
}

/**
 * =============== CHECK VALIDATION ===============
 */

function checkValidationLessonHeader() {

	var result = true;
	// Check require information
	var lessonName = $('#lessonName').val();
	var lessonType = $('#lessonType').val();
	var japaneseLevel = $('#level').val();

	if ($.trim(lessonName) === '') {
		result = false;
		bootbox.hideAll();
		bootbox.alert(MSG.E0001);
	} else if ($.trim(lessonType) === '') {
		result = false;
		bootbox.hideAll();
		bootbox.alert(MSG.E0002);
	} else if ($.trim(japaneseLevel) === '') {
		result = false;
		bootbox.hideAll();
		bootbox.alert(MSG.E0003);
	}

	return result;
}

/**
 * Check validation for save
 */
function checkValidationForSave() {

	// Check validation for all lesson info before save
	var length = lessonInfoArray.length;

	for (var i = 0; i < length; i++) {

		var containerLessonInfo = $('.lesson_info_' + i);

		if (containerLessonInfo.length > 0) {
			// Get lesson data
			var lessonData = getLessonData(i);

			if (!checkValidationForLessonInfo(lessonData, i)) {
				return false;
				break;
			}
		}
	}

	return true;
}

var btnErrorOkClick = function(target) {
	$(target).parents('#dialogerror').remove();
	return true;
};

var btnErrorCancelClick = function(target) {
	$(target).parents('#dialogerror').remove();
	return false;
};
// Ngan edit
/**
 * Check validation on lesson info
 *
 * @returns {Boolean}
 */
function checkValidationForLessonInfo(lessonData, index) {
	var alertBox = function(msg) {
		bootbox.hideAll();
		bootbox.alert(msg);
		return false;
	};

	var result = true;
	var lessonNo = lessonInfoArray[index];

	// message error must defined in messages.js
	if ($.trim(lessonData.practiceLimit) === '') {
		result = alertBox(getMessage([ lessonNo ], MSG.E0004));
	} else if ($.trim(lessonData.practiceMemoryCondition) === '') {
		result = alertBox(getMessage([ lessonNo ], MSG.E0005));
	} else if ($.trim(lessonData.practiceWritingCondition) === '') {
		result = alertBox(getMessage([ lessonNo ], MSG.E0006));
	} else if ($.trim(lessonData.practiceConversationCondition) === '') {
		result = alertBox(getMessage([ lessonNo ], MSG.E0007));
	} else if ($.trim(lessonData.testMemoryCondition) === '') {
		result = alertBox(getMessage([ lessonNo ], MSG.E0008));
	} else if ($.trim(lessonData.testWritingCondition) === '') {
		result = alertBox(getMessage([ lessonNo ], MSG.E0009));
	} else if ($.trim(lessonData.testConversationCondition) === '') {
		result = alertBox(getMessage([ lessonNo ], MSG.E0010));
	} else if ($.trim(lessonData.scenarioName) === '') {
		result = alertBox(getMessage([ lessonNo ], MSG.E0011));
	} else if ($.trim(lessonData.scenarioSyntax) === '' && $('#lessonType').val() == 0 && $('#learningType').val() == 1) {
		result = alertBox(getMessage([ lessonNo ], MSG.E0012));
	} else if ($.trim(lessonData.vocabularyName) === '') {
		result = alertBox(getMessage([ lessonNo ], MSG.E0013));
	} else if (!checkInputVoca() && $('#learningType').val() == 1) {
		result = alertBox(getMessage([ lessonNo ], MSG.E0047));
	} else if (lessonInfoArray.length === 0) {
		// Lesson range not set
		result = alertBox(MSG.E0014);
	}

	return result;
}
