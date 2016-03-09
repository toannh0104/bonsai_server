<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="wrapper-vocabulary">
	<c:if test="${not empty lessonDto.lessonInfoDtos}">
		<c:forEach items="${lessonDto.lessonInfoDtos}" var="lessonInfoDto" varStatus="status">
			<div class="theLessonVocabulary lessonVocabulary_${status.index}">
				<div class="LessonVocabulary">
					<div id="lessonVocabularyInforArea">
						<c:choose>
							<c:when test="${isNew == true}">
								<div id="lessonVocabularyTitle" onclick="showLessonTitle();">単語学習:</div>
							</c:when>
							<c:otherwise>
								<div id="lessonVocabularyTitle">単語学習:</div>
								<a href="#" style="color: blue; text-decoration: underline;" onclick="showLessonTitle();">Edit</a>
							</c:otherwise>
						</c:choose>
						<div id="lessonVocabularyName" class="vocabularyName">
							<span>${lessonInfoDto.scenarioName}</span>
							<span>   （<spring:eval expression="@EN.getProperty('lbl.vocabularyName')" />:${lessonInfoDto.scenarioNameEn}）     </span>
							<c:if test="${languageId != 'EN'}">
								<span>（<spring:eval expression="@${languageId}.getProperty('lbl.vocabularyName')" />:${lessonInfoDto.scenarioNameLn}）</span>
							</c:if>
						</div>
						<input class="lessonVocabularyNameHidden" type="hidden" name="lessonInfoForms[${status.index}].vocabularyName" value="${lessonInfoDto.scenarioName }" />
						<div id="lessonVocabularyNameEn"></div>
						<div id="lessonVocabularyNamelanguage"></div>
					</div>
					<div id="practiceVocabularyInforArea">
						<c:choose>
							<c:when test="${isNew == true}">
								<div id="practiceVocabularyTitleBefor" onclick="showPracticeTitleBefor();">練習：</div>
							</c:when>
							<c:otherwise>
								<a href="#" style="color: blue; text-decoration: underline; float: left;" onclick="showPracticeTitleBefor();">Edit</a>
								<div id="practiceVocabularyTitleBefor">練習：</div>
							</c:otherwise>
						</c:choose>
						<div class="practiceLimitText">${lessonInfoDto.practiceLimit}</div>
						<div id="practiceVocabularyTitleAfter">回</div>
					</div>
				</div>
				<div class="LanguageVocabulary">
					<div class="LanguageVocabularyAdmin">
						<c:choose>
							<c:when test="${isNew == true}">
								<div>問題</div>
								<div>（<spring:eval expression="@EN.getProperty('lbl.question')" />）</div>
								<c:if test="${languageId != 'EN'}">
									<div>（<spring:eval expression="@${languageId}.getProperty('lbl.question')" />）</div>
								</c:if>
					</div>
				</div>
				<div class="Conten" style="margin-top: 1%" onclick="showTheAreaVocabulary(this, '${status.index}', '${lessonInfoDto.id}');">
							</c:when>
							<c:otherwise>
								<div>問題<a href="#" style="color: blue; text-decoration: underline;" onclick="showTheAreaVocabulary(this, '${status.index}', '${lessonInfoDto.id}');">Edit</a>
								</div>
								<div>（<spring:eval expression="@EN.getProperty('lbl.question')" />）</div>
								<c:if test="${languageId != 'EN'}">
									<div>（<spring:eval expression="@${languageId}.getProperty('lbl.question')" />）</div>
								</c:if>
				</div>
				</div>
				<div class="Conten" style="margin-top: 1%">
							</c:otherwise>
						</c:choose>
					<c:forEach items="${lessonInfoDto.vocabularyDtos}" var="vocabularyDto" varStatus="sttVocabulary">
						<div class="row">
							<div class="col-hafl">${vocabularyDto.vocabularyOrder}.${vocabularyDto.vocabulary}</div>
							<input class="rubyWord" type="hidden" name="lessonInfoForms[${status.index}].vocabularyForms[${sttVocabulary.index}].rubyWord" value="${vocabularyDto.rubyWord}" />
							<input class="vocabulary" type="hidden" name="lessonInfoForms[${status.index}].vocabularyForms[${sttVocabulary.index}].vocabulary" value="${vocabularyDto.vocabulary}" />
							<input class="vocaHiragana" type="hidden" name="lessonInfoForms[${status.index}].vocabularyForms[${sttVocabulary.index}].vocabularyKanaName" value="${vocabularyDto.vocabularyKanaName}" />
							<input class="vocaCategory" type="hidden" name="lessonInfoForms[${status.index}].vocabularyForms[${sttVocabulary.index}].vocabularyCategories" value="${vocabularyDto.vocabularyCategories}" />
							<input class="vocaEnglish" type="hidden" name="lessonInfoForms[${status.index}].vocabularyForms[${sttVocabulary.index}].vocabularyEnglishName" value="${vocabularyDto.vocabularyEnglishName}" />
							<input class="vocabularyOrder" type="hidden" name="lessonInfoForms[${status.index}].vocabularyForms[${sttVocabulary.index}].vocabularyOrder"
								value="${vocabularyDto.vocabularyOrder}" />
							<input class="vocabularyId" type="hidden" name="lessonInfoForms[${status.index}].vocabularyForms[${sttVocabulary.index}].id" value="${vocabularyDto.id}" />
							<input class="vocabularylessonInfoId" type="hidden" name="lessonInfoForms[${status.index}].vocabularyForms[${sttVocabulary.index}].lessonInfoId" value="${lessonInfoDto.id}" />
							<input class="vocabularyUserInputFlg" type="hidden" name="lessonInfoForms[${status.index}].vocabularyForms[${sttVocabulary.index}].userInputFlg" value="${vocabularyDto.userInputFlg}" />
						</div>
					</c:forEach>
				</div>
			</div>
		</c:forEach>
	</c:if>
	<div class="template_rowVocabulary" style="display: none">
		<div class="col-hafl"></div>
		<input class="rubyWord" type="hidden" name="template" value="" />
		<input class="vocaEnglish" type="hidden" name="template" value="" />
		<input class="vocabulary" type="hidden" name="template" value="" />
		<input class="vocaHiragana" type="hidden" name="template" value="" />
		<input class="vocaCategory" type="hidden" name="template" value="" />
		<input class="vocabularyOrder" type="hidden" name="template" value="" />
		<input class="vocabularyId" type="hidden" name="template" value="" />
		<input class="vocabularylessonInfoId" type="hidden" name="template" value="" />
		<input class="vocabularyUserInputFlg" type="hidden" name="template" value="" />
	</div>

	<div id="template_areaVocabulary" style="display: none;">
		<div class="areaVocabulary rowVocabulary">
			<div>学習コンテンツ設定</div>
			<p class="vocabulary-column-title">
				<span>単語</span>
				<span class="mar-left-2-per">（ひらかな）</span>
				<span class="mar-left-8-per">（漢字）</span>
				<span class="mar-left-14-per">（カテゴリ）</span>
				<span class="mar-left-10-per">(英語)</span>
			</p>
			<div class="row mar-bot5" index="0">
				<div class="col-three-quaters">
					<p></p>
					<input order="0" class="vocaEnglish" onblur="createNewInputVocabulary(this)" disabled>
					<input order="0" onclick="loadAllVocabulary(this, 'category');" class="vocaCategory" onblur="createNewInputVocabulary(this)" disabled>
					<input order="0" class="vocaKanji vocabularyInputed" onblur="createNewInputVocabulary(this)" disabled>
					<input order="0" class="vocaHiragana" onblur="createNewInputVocabulary(this)" disabled>
				</div>
				<div class="col-quater">
					<a class="loadAllVocabulary" href="#" onclick="loadAllVocabulary(this, 'vocabulary');">DB呼出</a> <a class="inputVocabulary" onclick="inputVocabulary(this);" href="#">任意設定</a>
				</div>
			</div>
		</div>
	</div>
</div>