<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="wrapper-scenario">

	<c:if test="${not empty lessonDto.lessonInfoDtos}">
		<c:forEach items="${lessonDto.lessonInfoDtos}" var="lessonInfoDto" varStatus="status">
			<div class="thescenario lessonScenario_${status.index}">
				<div class="Lesson">
					<div id="lessonInforArea">
						<c:choose>
							<c:when test="${isNew == true}">
								<div id="lessonTitle" onclick="showLessonTitle();">
									<c:if test="${lessonDto.learningType == '2' }">
										安全事例:
									</c:if>
									<c:if test="${lessonDto.learningType != '2' }">
										シナリオ学習:
									</c:if>
								</div>
								<div class="scenarioNameText">${lessonInfoDto.scenarioName}</div>
							</c:when>
							<c:otherwise>
								<div id="lessonTitle">
									<c:if test="${lessonDto.learningType == '2' }">
										安全事例:
									</c:if>
									<c:if test="${lessonDto.learningType != '2' }">
										シナリオ学習:
									</c:if>
								</div>
								<div class="scenarioNameText">
									<span>${lessonInfoDto.scenarioName}</span>
									<c:if test="${lessonDto.learningType == '2' }">
										<span>（<spring:eval expression="@EN.getProperty('lbl.safetyExample')" />:${lessonInfoDto.scenarioNameEn}）     </span>
										<c:if test="${languageId != 'EN'}">
											<span>（<spring:eval expression="@${languageId}.getProperty('lbl.safetyExample')" />:${lessonInfoDto.scenarioNameLn}）</span>
										</c:if>
									</c:if>
									<c:if test="${lessonDto.learningType != '2' }">
										<span>（<spring:eval expression="@EN.getProperty('lbl.scenarioName')" />:${lessonInfoDto.scenarioNameEn}）     </span>
										<c:if test="${languageId != 'EN'}">
											<span>（<spring:eval expression="@${languageId}.getProperty('lbl.scenarioName')" />:${lessonInfoDto.scenarioNameLn}）</span>
										</c:if>
									</c:if>
								</div>
								<a href="#" style="color: blue; text-decoration: underline;" onclick="showLessonTitle();">Edit</a>
							</c:otherwise>
						</c:choose>
						<input class="scenarioNameHidden" type="hidden" name="lessonInfoForms[${status.index}].scenarioName" value="${lessonInfoDto.scenarioName }" />
						<div id="lessonNameEn"></div>
						<div id="lessonNamelanguage"></div>
					</div>
					<div id="practiceInforArea">
						<c:choose>
							<c:when test="${isNew == true}">
								<div id="practiceTitleBefor" onclick="showPracticeTitleBefor();">練習：</div>
							</c:when>
							<c:otherwise>
								<a href="#" style="color: blue; text-decoration: underline; float: left;" onclick="showPracticeTitleBefor();">Edit</a>
								<div id="practiceTitleBefor">練習：</div>
							</c:otherwise>
						</c:choose>
						<div class="practiceLimitText">${lessonInfoDto.practiceLimit}</div>
						<div id="practiceTitleAfter">回</div>
					</div>
				</div>
				<div class="LearningSyntax">
					<c:choose>
						<c:when test="${isNew == true}">
							<c:if test="${lessonDto.learningType != '2' }">
								<div id="syntaxTitle" onclick="showSyntaxTitleDialog('${lessonDto.id}');">学習構文:</div>
								<div class="syntaxText">${lessonInfoDto.scenarioSyntax }</div>
							</c:if>
						</c:when>
						<c:otherwise>
							<c:if test="${lessonDto.learningType != '2' }">
								<div id="syntaxTitle">学習構文:</div>
								<div class="syntaxText">${lessonInfoDto.scenarioSyntax }</div>
								<a href="#" style="color: blue; text-decoration: underline; float: left;" onclick="showSyntaxTitleDialog(${lessonDto.id});">Edit</a>
							</c:if>
						</c:otherwise>
					</c:choose>
					<input class="syntaxTextHidden" type="hidden" name="lessonInfoForms[${status.index}].scenarioSyntax" value="${lessonInfoDto.scenarioSyntax }" />
				</div>
				<div class="LanguageScenario">
					<c:choose>
						<c:when test="${isNew == true}">
							<div class="LanguageAdmin">
							<c:if test="${lessonDto.learningType == '2' }">
								<div  onclick="showTheAreaScenario(this, '${status.index}', '${lessonInfoDto.id}');">事例</div>
							</c:if>
							<c:if test="${lessonDto.learningType != '2' }">
								<div  onclick="showTheAreaScenario(this, '${status.index}', '${lessonInfoDto.id}');">問題</div>
							</c:if>
						<div>（<spring:eval expression="@EN.getProperty('lbl.question')" />）</div>
						<c:if test="${languageId != 'EN'}">
							<div>（<spring:eval expression="@${languageId}.getProperty('lbl.question')" />）</div>
						</c:if>
					</div>
				</div>
				<div class="theAreaScenario">
						</c:when>
						<c:otherwise>
							<div class="LanguageAdmin">
							<c:if test="${lessonDto.learningType == '2' }">
								<div>事例<a href="#" style="color: blue; text-decoration: underline;" onclick="showTheAreaScenario(this, '${status.index}', '${lessonInfoDto.id}');">Edit</a></div>
							</c:if>
							<c:if test="${lessonDto.learningType != '2' }">
								<div>問題<a href="#" style="color: blue; text-decoration: underline;" onclick="showTheAreaScenario(this, '${status.index}', '${lessonInfoDto.id}');">Edit</a></div>
							</c:if>
						<div>（<spring:eval expression="@EN.getProperty('lbl.question')" />）</div>
						<c:if test="${languageId != 'EN'}">
							<div>（<spring:eval expression="@${languageId}.getProperty('lbl.question')" />）</div>
						</c:if>
					</div>
				</div>
				<div class="theAreaScenario">
						</c:otherwise>
					</c:choose>
					<div class="NameScenario"></div>
					<div class="ContenWho">
						<div class="ContenWhoLable">
							<c:choose>
								<c:when test="${isNew == true}">
									<c:if test="${lessonDto.learningType == '2' }">
										&nbsp;
									</c:if>
									<c:if test="${lessonDto.learningType != '2' }">
										山田さん
									</c:if>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${not empty lessonInfoDto.speakerOneName}">
											<c:if test="${lessonDto.learningType == '2' }">
												&nbsp;
											</c:if>
											<c:if test="${lessonDto.learningType != '2' }">
												${lessonInfoDto.speakerOneName}
											</c:if>
										</c:when>
										<c:otherwise>
											<c:if test="${lessonDto.learningType == '2' }">
												&nbsp;
											</c:if>
											<c:if test="${lessonDto.learningType != '2' }">
												山田さん
											</c:if>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="ContenWhoLableAdmin">
							<c:choose>
								<c:when test="${isNew == true}">
									<input class="speakerOneName" type="hidden" name="lessonInfoForms[${status.index}].speakerOneName" value="山田さん" />
									<input class="speakerTwoName" type="hidden" name="lessonInfoForms[${status.index}].speakerTwoName" value="あなた" />
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${not empty lessonInfoDto.speakerOneName}">
											<input class="speakerOneName" type="hidden" name="lessonInfoForms[${status.index}].speakerOneName" value="${lessonInfoDto.speakerOneName}" />
											<input class="speakerTwoName" type="hidden" name="lessonInfoForms[${status.index}].speakerTwoName" value="${lessonInfoDto.speakerTwoName}" />
										</c:when>
										<c:otherwise>
											<input class="speakerOneName" type="hidden" name="lessonInfoForms[${status.index}].speakerOneName" value="山田さん" />
											<input class="speakerTwoName" type="hidden" name="lessonInfoForms[${status.index}].speakerTwoName" value="あなた" />
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
							<c:forEach items="${lessonInfoDto.scenarioDtos}" var="scenarioDto" varStatus="sttScenario">
								<c:if test="${0 == scenarioDto.scenarioPart}">
									<div class="whorow">
										<div class="textscenario">${scenarioDto.scenarioOrder}.${scenarioDto.scenario}</div>
										<input class="rubyWord" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].rubyWord" value="${scenarioDto.rubyWord}" />
										<input class="calljWord" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].calljWord" value="${scenarioDto.calljWord}" />
										<input class="categoryWord" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].categoryWord" value="${scenarioDto.categoryWord}" />
										<input class="calljLessonNo" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].calljLessonNo" value="${scenarioDto.calljLessonNo}" />
										<input class="calljQuestionName" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].calljQuestionName" value="${scenarioDto.calljQuestionName}" />
										<input class="calljConceptName" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].calljConceptName" value="${scenarioDto.calljConceptName}" />
										<input class="scenario" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].scenario" value="${scenarioDto.scenario}" />
										<input class="scenarioOrder" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].scenarioOrder" value="${scenarioDto.scenarioOrder}" />
										<input class="scenarioId" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].id" value="${scenarioDto.id}" />
										<input class="scenariolessonInfoId" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].lessonInfoId" value="${lessonInfoDto.id}" />
										<input class="scenarioPart" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].scenarioPart" value="${scenarioDto.scenarioPart}" />
										<input class="userInputFlg" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].userInputFlg" value="${scenarioDto.userInputFlg}" />
									</div>
								</c:if>
							</c:forEach>
						</div>
					</div>
					<div class="ContenYou">
						<div class="ContenYouLable">
							<c:choose>
								<c:when test="${isNew == true}">
									<c:if test="${lessonDto.learningType == '2' }">
										&nbsp;
									</c:if>
									<c:if test="${lessonDto.learningType != '2' }">
										あなた
									</c:if>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${not empty lessonInfoDto.speakerTwoName}">
											<c:if test="${lessonDto.learningType == '2' }">
												&nbsp;
											</c:if>
											<c:if test="${lessonDto.learningType != '2' }">
												${lessonInfoDto.speakerTwoName}
											</c:if>
										</c:when>
										<c:otherwise>
											<c:if test="${lessonDto.learningType == '2' }">
												&nbsp;
											</c:if>
											<c:if test="${lessonDto.learningType != '2' }">
												あなた
											</c:if>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="ContenYouLableAdmin">
							<c:forEach items="${lessonInfoDto.scenarioDtos}" var="scenarioDto" varStatus="sttScenario">
								<c:if test="${1 == scenarioDto.scenarioPart}">
									<div class="yourow">
										<div class="textscenario">${scenarioDto.scenarioOrder}.${scenarioDto.scenario}</div>
										<input class="rubyWord" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].rubyWord" value="${scenarioDto.rubyWord}" />
										<input class="calljWord" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].calljWord" value="${scenarioDto.calljWord}" />
										<input class="categoryWord" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].categoryWord" value="${scenarioDto.categoryWord}" />
										<input class="calljLessonNo" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].calljLessonNo" value="${scenarioDto.calljLessonNo}" />
										<input class="calljQuestionName" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].calljQuestionName" value="${scenarioDto.calljQuestionName}" />
										<input class="calljConceptName" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].calljConceptName" value="${scenarioDto.calljConceptName}" />
										<input class="scenario" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].scenario" value="${scenarioDto.scenario}" />
										<input class="scenarioOrder" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].scenarioOrder" value="${scenarioDto.scenarioOrder}" />
										<input class="scenarioId" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].id" value="${scenarioDto.id}" />
										<input class="scenariolessonInfoId" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].lessonInfoId" value="${lessonInfoDto.id}" />
										<input class="scenarioPart" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].scenarioPart" value="${scenarioDto.scenarioPart}" />
										<input class="userInputFlg" type="hidden" name="lessonInfoForms[${status.index}].scenarioForms[${sttScenario.index}].userInputFlg" value="${scenarioDto.userInputFlg}" />
									</div>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</c:if>
	<div class="template_whorow" style="display: none">
		<div class="textscenario"></div>
		<input class="rubyWord" type="hidden" name="template" value="" />
		<input class="calljWord" type="hidden" name="template" value="" />
		<input class="categoryWord" type="hidden" name="template" value="" />
		<input class="calljLessonNo" type="hidden" name="template" value="" />
		<input class="calljQuestionName" type="hidden" name="template" value="" />
		<input class="calljConceptName" type="hidden" name="template" value="" />
		<input class="scenario" type="hidden" name="template" value="" />
		<input class="scenarioOrder" type="hidden" name="template" value="" />
		<input class="scenarioId" type="hidden" name="template" value="" />
		<input class="scenariolessonInfoId" type="hidden" name="template" value="" />
		<input class="scenarioPart" type="hidden" name="template" value="" />
		<input class="userInputFlg" type="hidden" name="template" value="" />
	</div>

	<div id="template_areaScenario" style="display: none;">
		<div class="areaScenario whorow">
			<div><input value="相手パート" class="personname"/></div>
			<div class="row mar-bot5" row="0">
				<div class="colhalf">
					<p>シナリオ</p>
					<input part="0" order="" class="scenarioInputed" onblur="createNewInput(this)" disabled>
				</div>
				<div class="colhalf">
					<a class="loadAllScenario" href="#" onclick="loadAllScenario(this);">DB呼出</a>
					<a class="inputScenario" onclick="inputScenario(this);" href="#">任意設定</a>
				</div>
			</div>
		</div>
		<div class="areaScenario yourow">
			<div><input value="自分パート" class="personname"/></div>
			<div class="row mar-bot5" row="0">
				<div class="colhalf">
					<p>シナリオ</p>
					<input part="1" order="" class="scenarioInputed" onblur="createNewInput(this)" disabled>
				</div>
				<div class="colhalf">
					<a href="#" onclick="loadAllScenario(this);">DB呼出</a>
					<a class="inputScenario" onclick="inputScenario(this);" href="#">任意設定</a>
				</div>
			</div>
		</div>
	</div>
</div>
