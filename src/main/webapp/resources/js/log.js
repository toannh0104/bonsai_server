var url = '';
var currentPageData = 1;
var cureentPageLogin = 1;
var totalPageLogin = "";
var totalPageData = "";
var options = {};

/**
 * log login when next page
 *
 * @param page
 */
function pageLog(modeLog) {

	url = $('#url').val();

	var dataForm = "";
	if (modeLog == 0) {
		dataForm = "logFormLogin";
	} else {
		dataForm = "logFormData";
	}
	var form = document.forms[dataForm];
	form.action = url + "/admin/logLogin/modeLog/" + modeLog;
	form.submit();
}

/**
 * set data value
 *
 * @param value
 * @returns "" or value
 */
function setDataValue(value) {
	if (value == null) {
		return "";
	}
	return value;
}

/**
 * show dialog
 *
 * @param answer
 */
function showDialog(userName, course, modeLearning, lessonNo,
			scenarioOrWord, modePracticeOrTest, modeSkill, practiceTime,
			score, answer, startTime, endTime, time) {
	var answerList = answer.split(";");
	var htmlContent = "";
	htmlContent += '<table class="table table-striped">';
	htmlContent += '<tbody>';
	for (var int = 0; int < answerList.length; int++) {
		if (answerList[int] == " ") {
			break;
		}
		var value = answerList[int].split("-");
		var modeAnswer = value[0];

		htmlContent += '<tr>';
		htmlContent += '<td class="f13"><b>' + (int + 1) + '</b></td>';
		htmlContent += '<td  class="f13"><b>Model Answer:</b></td>';
		htmlContent += '<td  class="f12">' + modeAnswer + '</td>';
		htmlContent += '</tr>';

		var yourAnswer = value[1];
		htmlContent += '<td></td>';
		htmlContent += '<td class="f13"><b>Your Answer:</b></td>';
		if (modeAnswer.trim() == yourAnswer.trim()) {
			htmlContent += '<td class="f12">' + yourAnswer + '</td>';
		} else {
			htmlContent += '<td class="f12"><span class="red">' + yourAnswer
					+ '</span></td>';
		}
		htmlContent += '</tr>';
	}
	htmlContent += '</tbody></table>';

	// init setting option
	var settings = {
		closeButton : false,
		classBtnCancel : "btn-cancel",
		classBtnSuccess : "btn-success",
		hideClass : "btn-cancel",
		callbackSuccess : function() {
			showDetailDialog(userName, course, modeLearning, lessonNo,
					scenarioOrWord, modePracticeOrTest, modeSkill, practiceTime,
					score, answer, startTime, endTime, time);
		}
	};

	bootbox.hideAll();
	createBootboxDialog("回答一覧", htmlContent, settings);
}

/**
 * check validate user id login
 *
 * @returns {Boolean}
 */
function checkValidateUserNameLogin() {
	var userNameLogin = $('#userNameLogin').val();
	if (userNameLogin.length > 11) {

		bootbox.alert("Limit length is 11 ! Please input again");
		return false;
	}
}

/**
 * check validate user id data
 *
 * @returns {Boolean}
 */
function checkValidateUserNameData() {
	var userNameData = $('#userNameData').val();
	if (userNameData.length > 11) {

		bootbox.alert("Limit length is 11 ! Please input again");
		return false;
	}
}

/**
 * check validate location
 *
 * @returns {Boolean}
 */
function checkValidateLocation() {
	var location = $('#location').val();
	if (location.length > 100) {
		bootbox.alert("Limit length is 100 ! Please input again");
		return false;
	}
}

/**
 * check validate lesson no
 *
 * @returns {Boolean}
 */
function checkValidateLessonNo() {
	var lessonNo = $('#lessonNo').val();
	if (lessonNo.length > 10) {
		bootbox.alert("Limit length is 10 ! Please input again");
		return false;
	}
}

/**
 * check validate course
 *
 * @returns {Boolean}
 */
function checkValidateCourse() {
	var course = $('#course').val();
	if (course.length > 200) {
		bootbox.alert("Limit length is 200 ! Please input again");
		return false;
	}
}

$(document).ready(function() {

	var modeLog = $('#modeLog').val();
	if (modeLog == 0) {
		$('#log_login').addClass('active');
		$('#log_data').removeClass('active');
		$('#login').addClass('active');
		$('#data').removeClass('active');
	} else {
		$('#log_login').removeClass('active');
		$('#log_data').addClass('active');
		$('#data').addClass('active');
		$('#login').removeClass('active');
	}
	var optionDataTable = {
		searching : false,
		"lengthMenu" : [ [ 5, 10, 20, 50, -1 ], [ 5, 10, 25, 50, "すべて" ] ],
		"columnDefs" : [ {
			"targets" : 'no-sort',
			"orderable" : false,
			"order" : []
		} ],
		"language" : {
			"paginate" : {
				"first" : "←",
				"previous" : "前",
				"next" : "次",
				"last" : "→"
			},
			"info" : "表示 _START_ に _END_ の _TOTAL_ エントリー",
			"lengthMenu" : "表示 _MENU_ レコード",
			"zeroRecords": "テーブル内のデータなし",
			"infoEmpty": "レコードが存在されない"
		},
		"pagingType" : "full_numbers"
	};

	$('.tableLogLogin').DataTable(optionDataTable);
	$('.tableLogData').DataTable(optionDataTable);

});

/**
 * init date from calender
 */
$(function() {
	$('#datetimepickerFrom').datetimepicker({
		format : 'YYYY-MM-DD',
		locale : 'ja'
	});
});

/**
 * init date to calender
 */
$(function() {
	$('#datetimepickerTo').datetimepicker({
		format : 'YYYY-MM-DD',
		locale : 'ja'
	});
});

/**
 * search log in page
 *
 * @param modeLog
 * @param page
 */
function searchLog(modeLog, page) {
	// update value in LogForm
	if (modeLog == '0') {
		var userNameLogin = $('#userNameLogin').val();
		var loginPeriodTo = $('#loginPeriodTo').val();
		var loginPeriodFrom = $('#loginPeriodFrom').val();
		var location = $('#location').val();
		var terminal = $('#terminal').val();

		$('.userNameLogin').val(userNameLogin);
		$('.loginPeriodTo').val(loginPeriodTo);
		$('.loginPeriodFrom').val(loginPeriodFrom);
		$('.location').val(location);
		$('.terminal').val(terminal);
	} else {
		var userNameData = $('#userNameData').val();
		var modeLearning = $('#modeLearning').val();
		var lessonNo = $('#lessonNo').val();
		var course = $('#course').val();
		var modePracticeOrTest = $('#modePracticeOrTest').val();
		var modeSkill = $('#modeSkill').val();

		$('.userNameData').val(userNameData);
		$('.modeLearning').val(modeLearning);
		$('.lessonNo').val(lessonNo);
		$('.course').val(course);
		$('.modePracticeOrTest').val(modePracticeOrTest);
		$('.modeSkill').val(modeSkill);
	}

	// search in db
	pageLog(modeLog);
}

/**
 * show detail dialog
 *
 * @param userName
 * @param course
 * @param modeLearning
 * @param lessonNo
 * @param scenarioOrWord
 * @param modePracticeOrTest
 * @param modeSkill
 * @param practiceTime
 * @param score
 * @param answer
 * @param startTime
 * @param endTime
 * @param time
 */
function showDetailDialog(userName, course, modeLearning, lessonNo,
		scenarioOrWord, modePracticeOrTest, modeSkill, practiceTime, score,
		answer, startTime, endTime, time) {

	var htmlContent = "";
	htmlContent += '<table class="table table-striped">';
	htmlContent += '<tbody>';

	htmlContent += '<tr>';
	htmlContent += '<td class="f12"><b>ユーザ名:</b></td>';
	htmlContent += '<td class="f12">' + userName + '</td>';
	htmlContent += '<td class="f12"><b>練習回数:</b></td>';
	htmlContent += '<td class="f12">' + practiceTime + '</td>';
	htmlContent += '</tr>';

	htmlContent += '<tr>';
	htmlContent += '<td class="f12"><b>コース:</b></td>';
	htmlContent += '<td class="f12">' + course + '</td>';
	htmlContent += '<td class="f12"><b>スコア:</b></td>';
	htmlContent += '<td class="f12">' + setIntegerValue(score) + '</td>';
	htmlContent += '</tr>';

	htmlContent += '<tr>';
	htmlContent += '<td class="f12"><b>学習モード:</b></td>';
	htmlContent += '<td class="f12">' + modeLearning + '</td>';
	htmlContent += '<td class="f12"><b>回答閲覧:</b></td>';
	htmlContent += '<td class="f12"><a onclick="showDialog('
			+ setStringValue(userName) + ', '
			+ setStringValue(course) + ', '
			+ setStringValue(modeLearning) + ', '
			+ setStringValue(lessonNo) + ', '
			+ setStringValue(scenarioOrWord) + ', '
			+ setStringValue(modePracticeOrTest) + ', '
			+ setStringValue(modeSkill) + ', '
			+ setStringValue(practiceTime) + ', '
			+ setStringValue(setIntegerValue(score)) + ', '
			+ setStringValue(answer) + ', '
			+ setStringValue(startTime) + ', '
			+ setStringValue(endTime) + ', '
			+ setStringValue(time)
			+ ');">回答閲覧</a></td>';
	htmlContent += '</tr>';

	htmlContent += '<tr>';
	htmlContent += '<td class="f12"><b>レッスン番号:</b></td>';
	htmlContent += '<td class="f12">' + lessonNo + '</td>';
	htmlContent += '<td class="f12"><b>作成日時:</b></td>';
	htmlContent += '<td class="f12">' + startTime + '</td>';
	htmlContent += '</tr>';

	htmlContent += '<tr>';
	htmlContent += '<td class="f12"><b>シナリオ単語:</b></td>';
	htmlContent += '<td class="f12">' + scenarioOrWord + '</td>';
	htmlContent += '<td class="f12"><b>更新日時:</b></td>';
	htmlContent += '<td class="f12">' + endTime + '</td>';
	htmlContent += '</tr>';

	htmlContent += '<tr>';
	htmlContent += '<td class="f12"><b>練習テスト:</b></td>';
	htmlContent += '<td class="f12">' + modePracticeOrTest + '</td>';
	htmlContent += '<td class="f12"><b>学習時間(分）:</b></td>';
	htmlContent += '<td class="f12">' + time + '</td>';
	htmlContent += '</tr>';

	htmlContent += '<tr>';
	htmlContent += '<td class="f12"><b>タイプ:</b></td>';
	htmlContent += '<td class="f12">' + modeSkill + '</td>';
	htmlContent += '<td class="f12"></td><td></td>';
	htmlContent += '</tr>';

	// init setting option
	var settings = {
		closeButton : false,
		classBtnCancel : "btn-cancel",
		classBtnSuccess : "btn-success",
		hideClass : "btn-cancel",
	};

	createBootboxDialog("詳細", htmlContent, settings);
}

/**
 * set string value
 *
 * @param value
 * @returns {String}
 */
function setStringValue(value) {
	if (value == '' || value == null || value == 'null') {
		return "''";
	}
	return "'" + value + "'";
}

/**
 * set integer value
 *
 * @param value
 * @returns {String}
 */
function setIntegerValue(value) {
	if (value == '' || value == null) {
		return "0%";
	}
	return parseInt(value) + "%";
}
