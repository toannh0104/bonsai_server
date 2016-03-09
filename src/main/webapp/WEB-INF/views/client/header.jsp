<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="lessionname">
	<div class="wrapper-header">
		<div class="group1">
			<div class="name pos-right">${lessonDto.lessonName}</div>
			<div>
				（${lessonDto.lessonNameEn}）
				<c:if test="${languageId != 'JA' and languageId != 'EN'}">（${lessonDto.lessonNameLn}）</c:if>
			</div>
			<input type="hidden" id="lessonName" name="lessonName" value="${lessonDto.lessonName}" /> <input type="hidden" id="lessonType" name="lessonType" value="${lessonDto.lessonType}" />
			<input type="hidden" id="lessonNameEn" name="lessonNameEn" value="${lessonDto.lessonNameEn}"> <input type="hidden" id="lessonNameLn" name="lessonNameLn"
				value="${lessonDto.lessonNameLn}"> <input type="hidden" id="lessonRange" name="lessonRange" value="${lessonDto.lessonRange}"> <input type="hidden"
				id="lessonMethodRomaji" name="lessonMethodRomaji" value="${lessonDto.lessonMethodRomaji}"> <input type="hidden" id="lessonMethodHiragana" name="lessonMethodHiragana"
				value="${lessonDto.lessonMethodHiragana}"> <input type="hidden" id="lessonMethodKanji" name="lessonMethodKanji" value="${lessonDto.lessonMethodKanji}"> <input
				type="hidden" id="id" name="id" value="${lessonDto.id}"> <input type="hidden" id="deleteFlg" name="deleteFlg" value="${lessonDto.deleteFlg}"> <input
				type="hidden" id="learningType" name="learningType" value="${lessonDto.learningType}"> <input type="hidden" id="languageId" name="languageId" value="${languageId}">
		</div>
		<div class="separator">|</div>
		<div class="group2">
			<div class="method">
				<c:if test="${lessonDto.lessonType == 0}">
					会話学習
					<div>
						（
						<spring:eval expression="@EN.getProperty('lbl.scentence')" />
						）
						<c:if test="${languageId != 'JA' and languageId != 'EN'}">
							（<spring:eval expression="@${languageId}.getProperty('lbl.scentence')" />）
						</c:if>
					</div>
				</c:if>
				<c:if test="${lessonDto.lessonType == 1}">
					単語学習
					<div>
						（
						<spring:eval expression="@EN.getProperty('lbl.vocabulary')" />
						）
						<c:if test="${languageId != 'JA' and languageId != 'EN'}">
							（<spring:eval expression="@${languageId}.getProperty('lbl.vocabulary')" />）
						</c:if>
					</div>
				</c:if>
			</div>
			<input type="hidden" id="lessonType" name="lessonType" value="${lessonDto.lessonType}">
		</div>
		<div class="separator">|</div>
		<div class="group3">
			<div class="group3-col-40">
				<div class="group3-row"></div>
				<c:if test="${empty lessonDto.level}">
					<div class="group3-row">
						<span>LEVEL：</span> <span class="level">N5</span>
					</div>
				</c:if>
				<c:if test="${not empty lessonDto.level}">
					<div class="group3-row">
						<span>LEVEL：</span> <span class="level">N${lessonDto.level}</span>
					</div>
				</c:if>
			</div>
			<div class="group3-col-60">
				<div class="group3-row">
					<div class="group3-col-35 comp_ave">
						<span>comp.</span>
					</div>
					<div class="group3-col-35 comp_ave">
						<span>ave.</span>
					</div>
				</div>
				<div class="group3-row">
					<c:if test="${ empty lessonDto.percentComplete}">
						<div class="group3-col-half">
							<span>（0%</span>
						</div>
					</c:if>
					<c:if test="${not empty lessonDto.percentComplete}">
						<div class="group3-col-half">
							<fmt:parseNumber parseLocale="JA" var="percentComplete" integerOnly="true" type="number" value="${lessonDto.percentComplete}" />
							<span>（<c:out value="${percentComplete}" />%
							</span>
						</div>
					</c:if>
					<c:if test="${ empty lessonDto.averageScore}">
						<div class="group3-col-half bor-left">
							<span style="padding-left: 3px;">0%）</span>
						</div>
					</c:if>
					<c:if test="${not empty lessonDto.averageScore}">
						<div class="group3-col-half bor-left">
							<fmt:parseNumber parseLocale="JA" var="averageScore" integerOnly="true" type="number" value="${lessonDto.averageScore}" />
							<span style="padding-left: 3px;"><c:out value="${averageScore}" />%）</span>
						</div>
					</c:if>
				</div>
			</div>
			<c:if test="${empty lessonDto.level}">
				<input type="hidden" id="level" name="level" value="5">
			</c:if>
			<c:if test="${not empty lessonDto.level}">
				<input type="hidden" id="level" name="level" value="${lessonDto.level}">
			</c:if>
			<input type="hidden" id="percentComplete" name="percentComplete" value="${lessonDto.percentComplete}"> <input type="hidden" id="averageScore" name="averageScore"
				value="${lessonDto.averageScore}">
		</div>
		<div class="group4">
			<div class="administrator"></div>
		</div>
	</div>
</div>
<script type="text/javascript">
	MSG.E0029 = "<fmt:message key='E0029'></fmt:message>";
	MSG.E0030 = "<fmt:message key='E0030'></fmt:message>";
	MSG.E0031 = "<fmt:message key='E0031'></fmt:message>";
	MSG.E0032 = "<fmt:message key='E0032'></fmt:message>";
	MSG.E0033 = "<fmt:message key='E0033'></fmt:message>";
	MSG.E0034 = "<fmt:message key='E0034'></fmt:message>";
	MSG.E0035 = "<fmt:message key='E0035'></fmt:message>";
	MSG.E0036 = "<fmt:message key='E0036'></fmt:message>";
	MSG.E0037 = "<fmt:message key='E0037'></fmt:message>";
	MSG.E0038 = "<fmt:message key='E0038'></fmt:message>";
	MSG.E0039 = "<fmt:message key='E0039'></fmt:message>";
</script>

<input type="hidden" id="currentLessonInfoIndex" name="currentLessonInfoIndex" value="${currentLessonInfoIndex}">
<input type="hidden" id="scenario_vocabulary" name="scenarioVocabulary" value="${scena_voca}" />
<input type="hidden" id="practice_test" name="practiceTest" value="${practice_test}" />
<input type="hidden" id="memo_writ_spee" name="memoryWritingSpeaking" value="${memory_writing_speech}" />
<input type="hidden" id="clientUserId" value="${userId}" />
<input type="hidden" id="iScore" value="false" />
<input type="hidden" id="startTime" value="${startTime}" />
<input type="hidden" id="practiceNo" name="practiceNo" value="${practiceNo}" />
<input type="hidden" id="modeScenario" value="${modeScenario}" />
<input type="hidden" id="backtoScenario" value="false" />
<div class="allLessonInfo invisible">
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Lesson No</th>
				<th>Lesson Name</th>
				<th>Lesson Method</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${lessonDto.lessonInfoDtos}" var="lessonInfo" varStatus="lessonInfoStatus">
				<tr>
					<td class="lessonNoHidden">${lessonInfo.lessonNo}</td>
					<td>${lessonInfo.scenarioName }</td>
					<td class="lessonMethodHidden"></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
