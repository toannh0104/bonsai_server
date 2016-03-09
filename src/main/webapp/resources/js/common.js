var MAX_PRACTICE_TIME = 20;
var MIN_PRACTICE_TIME = 1;
var RESULT_STATUS_OK = "OK";
var RESULT_STATUS_NG = "NG";
var KATAKANATABLE = [ "ア", "イ", "ウ", "エ", "オ", "カ", "キ", "ク", "ケ", "コ", "ガ",
		"ギ", "グ", "ゲ", "ゴ", "ハ", "ヒ", "フ", "ヘ", "ホ", "バ", "ビ", "ブ", "ベ", "ボ",
		"パ", "ピ", "プ", "ペ", "ポ", "サ", "シ", "ス", "セ", "ソ", "ザ", "ジ", "ズ", "ゼ",
		"ゾ", "タ", "チ", "ツ", "テ", "ト", "ダ", "ヂ", "ヅ", "デ", "ド", "ラ", "リ", "ル",
		"レ", "ロ", "ナ", "ニ", "ヌ", "ネ", "ノ", "マ", "ミ", "ム", "メ", "モ", "ヤ", "ユ",
		"ヨ", "ワ", "ヲ", "ン", "ー", "？", "。", "、", "！" ];
var HIRAGANATABLE = [ "あ", "い", "う", "え", "お", "か", "き", "く", "け", "こ", "が",
		"ぎ", "ぐ", "げ", "ご", "は", "ひ", "ふ", "へ", "ほ", "ば", "び", "ぶ", "べ", "ぼ",
		"ぱ", "ぴ", "ぷ", "ぺ", "ぽ", "さ", "し", "す", "せ", "そ", "ざ", "じ", "ず", "ぜ",
		"ぞ", "た", "ち", "つ", "て", "と", "だ", "ぢ", "づ", "で", "ど", "ら", "り", "る",
		"れ", "ろ", "な", "に", "ぬ", "ね", "の", "ま", "み", "む", "め", "も", "や", "ゆ",
		"よ", "わ", "を", "ん", "ー", "？", "。", "、", "！" ];
/**
 * Create array lesson from String
 */
function createArrayLessonFromString(arr) {

    var resultArray = new Array();

    // Iterate through expression
    for (var i = 0; i < arr.length; i++) {
        // Case of comma
        if (arr[i].indexOf(',') != -1) {
            var values = arr[i].split(',');

            for (var j = 0; j < values.length; j++) {

                var intValue = parseInt(values[j]);

                // Check validation
                if (checkLessonRangeValid(values[j], resultArray)) {
                    resultArray.push(intValue);
                } else {
                    throw "Error";
                }
            }
        } else if (arr[i].indexOf('-') != -1 && arr[i].split('-').length == 2) {
            // Case of '-'
            var values = arr[i].split('-');

            // Get min & max
            var min = parseInt(values[0]);
            var max = parseInt(values[1]);

            // Check validation
            if (!checkLessonRangeValid(values[0], resultArray)
                    || !checkLessonRangeValid(values[1], resultArray)) {
                throw "Error";
            } else {

                // Iterate from min to max
                for (var l = min; l <= max; l++) {
                    // Check exist in array, if exist, return error
                    if ($.inArray(l, resultArray) === -1) {
                        resultArray.push(l);
                    } else {
                        throw "Error";
                    }
                }
            }

        } else if (checkLessonRangeValid(arr[i], resultArray)) {
            // If single number
            resultArray.push(parseInt(arr[i]));
        } else {
            throw "Error";
        }
    }

    // Check array length
    if (resultArray.length === 0) {
        throw "Error";
    }

    // Check lessonInfo ascending
    for (var i = 0; i < resultArray.length - 1; i++) {
        if (resultArray[i] > resultArray[i + 1]) {
            throw "Error";
        }
    }

    return resultArray;
}

/**
 * Check lesson range valid
 *
 * @param value
 * @param lessonInfoRange
 * @returns
 */
function checkLessonRangeValid(value, lessonInfoRange) {

    var result = true;
    var intValue = parseInt(value);

    if (!$.isNumeric(value) || $.inArray(intValue, lessonInfoRange) !== -1
            || intValue < 1 || intValue > 30) {
        result = false;
    }

    return result;
}

/**
 * Get key message
 *
 * @param key
 * @param mess
 * @returns
 */
function getMessage(key, mess) {
    for (var i = 0; i < key.length; i++) {
        mess = mess.replace("{" + i + "}", key[i]);
    }
    return mess;
}

/**
 * Get mode language of current lesson.
 */
function getModeLanguage(lessonNo) {
    var result = 0;
    try {
        // Check if lesson's language is romaji
        var methodRomaji = $('#lessonMethodRomaji').val();
        if (methodRomaji != '') {
            var lessonMethodRomaji = createArrayLessonFromString(methodRomaji.split(" "));
            if ($.inArray(lessonNo, lessonMethodRomaji) != -1) {
                result = 0;
            }
        }

        // Check if lesson's language is hiragana
        var methodHiragana = $('#lessonMethodHiragana').val();
        if (methodHiragana != '') {
            var lessonMethodHiragana = createArrayLessonFromString(methodHiragana.split(" "));
            if ($.inArray(lessonNo, lessonMethodHiragana) != -1) {
                result = 1;
            }
        }

        // Check if lesson's language is kanji
        var methodKanji = $('#lessonMethodKanji').val();
        if (methodKanji != '') {
            var lessonMethodKanji = createArrayLessonFromString(methodKanji.split(" "));
            if ($.inArray(lessonNo, lessonMethodKanji) != -1) {
                result = 2;
            }
        }

    } catch (err) {
        console.log(err);
    }
    return result;
}

/**
 * get method name by method mode
 * @param methodType
 */
function getMethodName (methodMode) {
	if (methodMode == 0) {
		return 'romaji';
	} else if (methodMode == 1) {
		return 'hiragana';
	} else {
		return 'kanji';
	}
}

/**
 * set text for input type
 * @param methodMode
 */
function setTextIputType(methodMode) {
    return('※ Please input by type ' + getMethodName(methodMode));
}

/**
 * method check string not empty
 */
var checkNotEmpty = function(value) {
    if (value == void (0)) {
        return false;
    }
    if (value == "") {
        return false;
    }
    if (value == null) {
        return false;
    }
    return true;
};

/**
 * get all concepts name
 * @returns {Array}
 */
function getAllConceptName() {
    var result = [];
    $('.lessonScenario_' + currentLessonIndex).find('.theAreaScenario').find('.calljConceptName').each(function(index) {
        if (result.indexOf($(this).val()) == -1 && $(this).val() != "") {
            result.push($(this).val());
        }
    });
    return result;
}

/**
 * Method check valid percent
 *
 * @param value
 */
function validPercent(value) {
    if (value <= 100 && value >= 0) {
        return true;
    }
    return false;
}

/**
 * get word by method
 * @param lessonMethod
 * @param sentence
 * @returns
 */
function getWordByMethod(lessonMethod, sentence) {
    if (lessonMethod == 0) {
        return sentence.romaji + ' ';
    } else if (lessonMethod == 1) {
        return sentence.kana;
    } else {
        return sentence.kanji;
    }
}

function getRubyWord(sentence) {
	var kana = sentence.kana;
	var kanji = sentence.kanji;
	if (kana != kanji) {
		var result = '';
		var listKana = kana.split('');
		var listKanji = kanji.split('');
		var kanjiTemp = '';
		for (var i = 0; i < listKanji.length; i++) {
			//check exist each word in HIRAGANATABLE and KATAKANATABLE
			if (HIRAGANATABLE.indexOf(listKanji[i]) != -1 || KATAKANATABLE.indexOf(listKanji[i]) != -1) {
				for (var j = 0; j < listKana.length; j++) {
					// if kana equal kanji and is first index
					if (listKanji[i] == listKana[j] && i == 0) {
						// remove kana and kanji
						kana = kana.replace(listKana[j], '');
						kanji = kanji.replace(listKanji[i], '');
						break;
					} else if (listKanji[i] == listKana[j] && i != 0) {// if kana equal kanji and isn't first index
						// check kanjiTemp not blank
						if (checkNotEmpty(kanjiTemp)) {
							// append ruby word to result
							if (j >= i) {
								result += kanjiTemp + '-' + kana.substring(0,j) + ';';
								// remove kana and kanji
								kana = kana.substring(j + 1,kana.length);
								kanji = kanji.replace(listKanji[i], '');
								// clear kanjiTemp after set to result
								kanjiTemp = '';
								break;
							}
						} else {// when kanjiTemp is blank
							// remove kana and kanji
							kana = kana.replace(listKana[j], '');
							kanji = kanji.replace(listKanji[i], '');
							break;
						}
					}
				}
			} else {
				// set kanjiTemp if word not in HIRAGANATABLE and KATAKANATABLE
				kanjiTemp += listKanji[i];
				kanji = kanji.replace(listKanji[i], '');
			}
		}
		// if kanji word is last index
		if (checkNotEmpty(kanjiTemp) && checkNotEmpty(kana)) {
			result += kanjiTemp + '-' + kana +';';
		}
		return result;
	}
	return '';
}

function setRubyWord(sentence, rubyWords){
	// remove last ';'
	rubyWords = rubyWords.substring(0,rubyWords.length-1);
	// split ruby word by ';'
	var rubys = rubyWords.split(';');

	// check each ruby
	$.each(rubys, function(index, value) {
		if (value != '') {
			// split ruby word by '-'
			var ruby = value.split('-');
			// add ruby word
			sentence = sentence.replaceLast(ruby[0], "<ruby>" + ruby[0] + '<rt>' + ruby[1] + '</rt>' + "</ruby>");
		}
	});
	return sentence;
}
String.prototype.replaceLast = function(find, replace) {
	var index = this.lastIndexOf(find);

	if (index >= 0) {
		return this.substring(0, index) + replace
				+ this.substring(index + find.length);
	}

	return this.toString();
};
/**
 * get callj word form page
 *
 * @returns
 */
function getCallJWordFormpage() {
    var allCallJWord = '';
    $('.lessonScenario_' + currentLessonIndex).find('.theAreaScenario').find('.calljWord').each(function(index) {
        var value = $(this).val();
    	if (value != "" && allCallJWord.indexOf(value) < 0) {
            allCallJWord += value;
        }
    });
    allCallJWord = allCallJWord.substring(0,allCallJWord.length-1);
    var res = allCallJWord.split(";");

    // TODO : Replace 'san' or 'さん'
    for (var i = 0; i < res.length; i++) {
        // Check if endwith 'san' or 'さん'
        if (res[i].match(/san$/) || res[i].match(/さん$/)) {
            res[i] = res[i].replace('san', '');
            res[i] = res[i].replace('さん', '');
        }
    }

    return res;
}

/**
 * check input vocabulary
 * @returns {Boolean}
 */
function checkInputVoca() {
    var arrCalljWord = getCallJWordFormpage();
    var arrVoca = [];

    // get all vocabulary if english value have exist

    $('.lessonVocabulary_' + currentLessonIndex).find('.Conten').find('.vocabulary').each(function(index) {
        if ($(this).val() != "" && $(this).closest(".row").find('.english').val() != '') {
            arrVoca.push($(this).val());
        }
    });

    //check callj  word has in array
    for (var i = 0; i < arrCalljWord.length; i++) {
        if (arrVoca.indexOf(arrCalljWord[i]) == -1 && arrCalljWord[i] != "") {
            return false;
        }
    }

    return true;
}

/**
 * Create dialog with default settings
 *
 * @param title
 * @param msg
 * @param settings including the following attributes:
 *            closeButton
 *            lblSuccess
 *            classBtnSuccess
 *            callbackSuccess
 *            lblCancel
 *            classBtnCancel
 *            callbackCancel
 *            hideClass
 * @return dialog
 */
function createBootboxDialog(title, msg, settings) {

    /** sample setting
    var settings = {
            lblSuccess : "Aceptar",
            lblCancel : "Cancelar",
            classBtnSuccess : "btn-success",
            classBtnCancel : "btn-cancel",
            callbackSuccess : function() {
                alert("Sin callback ok");
            },
            callbackCancel : function() {
                alert("Sin callback cancel");
            },
            closeButton : false,
            hideClass : "btn-success"
        };
    */

    var dialog = null;
    try {
        dialog = bootbox.dialog({
                title : title,
                message : msg,
                show : true,
                closeButton : checkNotEmpty(settings.closeButton) ? settings.closeButton : true,
                buttons : {
                    success : {
                        label : checkNotEmpty(settings.lblSuccess) ? settings.lblSuccess
                                : "OK",
                        className : checkNotEmpty(settings.classBtnSuccess) ? settings.classBtnSuccess
                                : "btn-success",
                        callback : checkNotEmpty(settings.callbackSuccess) ? settings.callbackSuccess
                                : function() {
                                }
                    },
                    cancel : {
                        label : checkNotEmpty(settings.lblCancel) ? settings.lblCancel
                                : "キャンセル",
                        className : checkNotEmpty(settings.classBtnCancel) ? settings.classBtnCancel
                                : "btn-primary",
                        callback : checkNotEmpty(settings.callbackCancel) ? settings.callbackCancel
                                : function() {
                                }

                    }
                }
            });

        if (checkNotEmpty(settings.hideClass)) {
            $("." + settings.hideClass).hide();
        }
    } catch (e) {
    }

    return dialog;
};

function innitRuby(){
	 $('.lessonScenario_' + currentLessonIndex).find('.theAreaScenario').find('.whorow').each(function(index) {
		 	var rubyWord = $(this).find('.rubyWord').val();
		 	var scenarioVal = $(this).find('.scenario').val();
		 	var categoryWord = $(this).find('.categoryWord').val();
		 	var calljWord = $(this).find('.calljWord').val();
		 	var textscenrioNew = setCategoryPopup(categoryWord, calljWord, scenarioVal);
		 	textscenrioNew = setRubyWord(textscenrioNew, rubyWord);
		 	var scenarioOrder = $(this).find('.scenarioOrder').val();
		 	$(this).find('.textscenario').html(scenarioOrder + "." + textscenrioNew);
	    });
	 $('.lessonScenario_' + currentLessonIndex).find('.theAreaScenario').find('.yourow').each(function(index) {
		 	var rubyWord = $(this).find('.rubyWord').val();
		 	var scenarioVal = $(this).find('.scenario').val();
		 	var categoryWord = $(this).find('.categoryWord').val();
		 	var calljWord = $(this).find('.calljWord').val();
		 	var textscenrioNew = setCategoryPopup(categoryWord, calljWord, scenarioVal);
		 	textscenrioNew = setRubyWord(textscenrioNew, rubyWord);
		 	var scenarioOrder = $(this).find('.scenarioOrder').val();
		 	$(this).find('.textscenario').html(scenarioOrder + "." + textscenrioNew);
	    });
	 $('.lessonVocabulary_' + currentLessonIndex).find('.Conten').find('.row').each(function(index) {
		 	var rubyWord = $(this).find('.rubyWord').val();
		 	var vocabularyVal = $(this).find('.vocabulary').val();
		 	var textVocabularyNew = setRubyWord(vocabularyVal, rubyWord);
		 	var vocabularyOrder = $(this).find('.vocabularyOrder').val();
		 	if (rubyWord == '') {
		 		$(this).find('.col-hafl').addClass('non-ruby');
			}
		 	$(this).find('.col-hafl').html(vocabularyOrder + "." + textVocabularyNew);
	    });
}

function checkInput(vocaKanji, vocaEnglish, vocaHiragana){
	if (!checkNotEmpty(vocaKanji)  && !checkNotEmpty(vocaEnglish) && !checkNotEmpty(vocaHiragana)) {
		return true;
	}
	if (checkNotEmpty(vocaKanji)  && checkNotEmpty(vocaEnglish) && checkNotEmpty(vocaHiragana)) {
		return true;
	}
	return false;
}
/**
 * set Category Popup
 *
 * @param categoryPopUp
 * @param calljWord
 * @param textContent
 * @returns
 */
function setCategoryPopup(categoryPopUp, calljWord, textContent) {
	// split callj word by ";"
	var calljWordArr = calljWord.split(";");
	// split category word by ";"
	var categoryPopUpArr = categoryPopUp.split(";");
	if (categoryPopUpArr.length > 0) {
		var popupLength = calljWordArr.length - 1;
		for (var i = 0; i < popupLength; i++) {
			// if category Word exist
			if (categoryPopUpArr[i] != undefined && categoryPopUpArr[i].length > 0) {
				var popupString = calljWordArr[i];
				var categoryWordArr = categoryPopUpArr[i].split('@');
				var categoryArr = categoryWordArr[0].split(',');
				var wordArr = categoryWordArr[1].split(',');
				// Set title
				var categoryTitle = "Categories";
				// Set content string
				var contentString = "<span>";
				// Check category list exits data then show category
				if (categoryArr.length > 1 || categoryArr[0] != '') {
					contentString += "<div class=\"popover-content-custom\">";
					for (var j = 0; j < categoryArr.length; j++) {
						if (categoryArr[j] != '') {
							contentString += categoryArr[j] + "&lt;br/&gt;";
						}
					}
					contentString += "</div>";
				}
				// Check word list exits data then show word
				if (wordArr.length > 1 || wordArr[0] != '') {
					// If category list exits data then set word title
					if (categoryArr.length > 1 || categoryArr[0] != '') {
						contentString += "<h3 class=\"popover-title\">Word</h3>";
					} else {
						categoryTitle = "Word";
					}
					contentString += "<div class=\"popover-content-custom\">";
					for (var j = 0; j < wordArr.length; j++) {
						if (wordArr[j] != '') {
							contentString += wordArr[j] + "&lt;br/&gt;";
						}
					}
					contentString += "</div>";
				}
				contentString += "</span>";
				// Set pop up link
				var popup = "<a href='javascript:void(0)' data-html='true' class='categoryPopup' data-placement='bottom' data-toggle='popover' data-trigger='click' title='"+ categoryTitle +"' data-content='" + contentString + "'>"+ popupString + "</a>";
				// Replace calljWord with pop up string
				textContent = textContent.replace(popupString, popup);
			}
		}
	}
	return textContent;
}