var flagCheckShowConversation = false;
$(document).ready(function() {
	setClientRuby();
	onLoadScenarioMode();
});
function setClientRuby() {
	$('.row').each(function(index){
		var rubyResult = $(this).find('.rubyResult');
		var rubyWord = $(this).find('.rubyWord').val();
		if (rubyWord != undefined && rubyWord != '' && rubyResult.html() != undefined && rubyResult.html() != '') {
			rubyResult.html(setRubyWord(rubyResult.html(), rubyWord));
		}
	});
}

function onLoadScenarioMode() {
	var modeScenario = $('#modeScenario').val();
	if (modeScenario != "") {
		$('.modeScenarioNotNull').removeClass('invisible');
	} else {
		$('.modeScenarioNull').removeClass('invisible');
	}
}

function showConversation() {
	flagCheckShowConversation = true;
	var modeScenario = $('#modeScenario').val();
	if (modeScenario != "") {
		$('.modeScenarioNull').removeClass('invisible');
		$('.modeScenarioNotNull').addClass('invisible');
		$('#backtoScenario').val("false");
	} else {
		$('.modeScenarioNull').removeClass('invisible');
		$('.modeScenarioNotNull').addClass('invisible');
	}
}