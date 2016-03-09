<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="wrapper-scenario">

	<c:forEach items="${lessonInfoDtos}" var="lessonInfoDto" varStatus="status">
		<div class="thescenario lessonScenario_${status.index}">
			<div class="Lesson">
				<div id="lessonInforArea">
					<div id="lessonTitle">
						<c:if test="${lessonDto.learningType == '1' }">シナリオ学習:</c:if>
						<c:if test="${lessonDto.learningType == '2' }">安全事例:</c:if>
					</div>
					<div class="scenarioNameText">
						<span>${lessonInfoDto.scenarioName}</span>
						<c:if test="${lessonDto.learningType == '1' }">
							<span>（<spring:eval expression="@EN.getProperty('lbl.scenarioName')" />:${lessonInfoDto.scenarioNameEn}）</span>
							<c:if test="${languageId != 'JA' and languageId != 'EN'}">
								<span>（<spring:eval expression="@${languageId}.getProperty('lbl.scenarioName')" />:${lessonInfoDto.scenarioNameLn}）</span>
							</c:if>
						</c:if>
						<c:if test="${lessonDto.learningType == '2' }">
							<span>（<spring:eval expression="@EN.getProperty('lbl.safetyExample')" />:${lessonInfoDto.scenarioNameEn}）</span>
							<c:if test="${languageId != 'JA' and languageId != 'EN'}">
								<span>（<spring:eval expression="@${languageId}.getProperty('lbl.safetyExample')" />:${lessonInfoDto.scenarioNameLn}）</span>
							</c:if>
						</c:if>
					</div>
					<input id="lessonNameHidden" type="hidden" name="lessonInfoForms[${status.index}].scenarioName" value="${lessonInfoDto.scenarioName }" />
				</div>
				<%-- <c:if test="${modeScenario != null}"> --%>
				<div class="modeScenarioNotNull invisible">
					<div id="practiceInforArea">
							<div id="practiceScenarioClientTitleBefor">
							<div>&nbsp;
								<a class="score_scenario modeScore invisible" onclick="checkBeforScore(${status.index}, ${lessonDto.lessonType}, 'scenario')">Score</a>
								<a class="score_scenario_test_memory modeScore invisible" onclick="checkBeforScore(${status.index}, ${lessonDto.lessonType}, 'scenario')">Score</a>
								<a class="score_scenario_test_writing modeScore invisible" onclick="checkBeforScore(${status.index}, ${lessonDto.lessonType}, 'scenario')">Score</a>
								<a class="score_scenario_test_speech modeScore invisible" onclick="checkBeforScore(${status.index}, ${lessonDto.lessonType}, 'scenario')">Score</a>
							</div>
							<div>
								<c:if test="${practice_test == null or practice_test == 'practice'}">練習：</c:if>
								<c:if test="${practice_test == 'test'}">テスト：</c:if>
							</div>
							</div>
						<div class="numberPracticeMemory invisible"></div>
						<div class="numberPracticeWriting invisible"></div>
						<div class="numberPracticeConversation invisible"></div>
						<div class="numberTestMemory invisible"></div>
						<div class="numberTestWriting invisible"></div>
						<div class="numberTestConversation invisible"></div>
						<%-- <div class="numberPractice">${practiceNo}<c:if test="${modeScenario != null}">/</c:if>${pageLimit}</div> --%>
						<input type="hidden" id="pageLimitClient" value="${pageLimit}">
						<input type="hidden" id="pageIndexClient" value="${pageIndex}">
						<div id="practiceTitleAfter">回</div>
					</div>
				<%-- </c:if> --%>
				</div>
			</div>
			<input type="hidden" id="mode" name="modeKey" value="${modeScenario}" /> <input type="hidden" id="typeScenario" value="${modeScenario}">

			<div class="scenarioImg invisible">
				<img alt="" onclick="onClickScenarioImg();" src="${pageContext.request.contextPath}/resources/images/Scenario.jpg"  width="300">
			</div>
			<div class="contenInfoSenario invisible">
				<div class="LearningSyntax">
					<div id="syntaxTitle">学習構文:</div>
					<div id="syntaxText">${lessonInfoDto.scenarioSyntax }</div>
					<input id="syntaxTextHidden" type="hidden" name="lessonInfoForms[${status.index}].scenarioSyntax" value="${lessonInfoDto.scenarioSyntax }" />
				</div>
				<div class="modeScenarioNull invisible">
					<div class="LanguageScenario">
						<div class="LanguageQuestion">
							<div>問題</div>
							<div>(<spring:eval expression="@EN.getProperty('lbl.question')" />)</div>
							<c:if test="${languageId != 'JA' and languageId != 'EN'}">
								<div>(<spring:eval expression="@${languageId}.getProperty('lbl.question')" />)</div>
							</c:if>
						</div>
						<div class="LanguageAnswer">
							<div>解説</div>
							<div>(<spring:eval expression="@EN.getProperty('lbl.explanation')" />)</div>
							<c:if test="${languageId != 'JA' and languageId != 'EN'}">
								<div>(<spring:eval expression="@${languageId}.getProperty('lbl.explanation')" />)</div>
							</c:if>
						</div>
					</div>
				</div>
				<div class="modeScenarioNotNull invisible">
					<div class="LanguageScenario">
						<div class="LanguageQuestion">
							<div>問題</div>
							<div>(<spring:eval expression="@EN.getProperty('lbl.question')" />)</div>
							<c:if test="${languageId != 'JA' and languageId != 'EN'}">
								<div>(<spring:eval expression="@${languageId}.getProperty('lbl.question')" />)</div>
							</c:if>
						</div>
						<div class="LanguageAnswer">
							<div>回答</div>
							<div>(<spring:eval expression="@EN.getProperty('lbl.answer')" />)</div>
							<c:if test="${languageId != 'JA' and languageId != 'EN'}">
								<div>(<spring:eval expression="@${languageId}.getProperty('lbl.answer')" />)</div>
							</c:if>
						</div>
					</div>
				</div>
				<div class="modeScenarioNull invisible">
					<div class="NameScenario">やまだ</div>
					<div class="ContenWho">
						<div class="ContenWhoLable">山田さん</div>
						<c:forEach items="${lessonInfoDto.scenarioDtos}" var="scenarioDto" varStatus="sttScenario">
						<div class="row">
						<div class="ContenWhoLableJp">
							<c:if test="${0 == scenarioDto.scenarioPart}">
								<div class="textscenario wall">${scenarioDto.scenarioOrder}.<span class="rubyResult" style="line-height: 35px;">${scenarioDto.scenario}</span></div>
								<input class="rubyWord" type="hidden" value="${scenarioDto.rubyWord}" />
							</c:if>
						</div>
						<div class="ContenWhoLableLn">
							<c:if test="${0 == scenarioDto.scenarioPart}">
								<div class="textscenario_${sttScenario.index} wall" style="padding-top: 9px;">${scenarioDto.scenarioOrder}.${scenarioDto.scenarioLn}</div>
							</c:if>
						</div>
						</div>
						</c:forEach>
					</div>
					<div class="ContenYou">
						<div class="ContenYouLable">あなた</div>
						<c:forEach items="${lessonInfoDto.scenarioDtos}" var="scenarioDto" varStatus="sttScenario">
						<div class="row">
							<div class="ContenYouLableJp">
								<c:if test="${1 == scenarioDto.scenarioPart}">
									<div class="textscenario_${sttScenario.index} wall">${scenarioDto.scenarioOrder}.<span class="rubyResult" style="line-height: 35px;">${scenarioDto.scenario}</span></div>
									<input class="rubyWord" type="hidden" value="${scenarioDto.rubyWord}" />
								</c:if>
							</div>
							<div class="ContenYouLableLn">
								<c:if test="${1 == scenarioDto.scenarioPart}">
									<div class="textscenario_${sttScenario.index} wall" style="padding-top: 9px;">${scenarioDto.scenarioOrder}.${scenarioDto.scenarioLn}</div>
								</c:if>
							</div>
						</div>
						</c:forEach>
					</div>
				</div>

				<div class="Information modeScenarioNotNull invisible">
				<div class="practiceMemory invisible"></div>
				<div class="practiceWriting invisible"></div>
				<div class="practiceConversation invisible"></div>
				<div class="testMemory invisible"></div>
				<div class="testWriting invisible"></div>
				<div class="testConversation invisible"></div>
					<c:if test="${scenarioList != null}">
						<c:if test="${modeScenario == 1}">
							<c:forEach items="${scenarioList}" var="listPage" varStatus="statuslistPage">
								<div class="memory memoryPage_${statuslistPage.index}">
									<c:forEach items="${listPage}" var="question" varStatus="statusQuestion">
									<div class="row">
										<div class="IndexQuestion">${statusQuestion.index + 1}.</div>
										<div class="InforQuestion wall" id="indexQuestion_${status.index}_${statuslistPage.index}_${statusQuestion.index + 1}">${scenarioShowList[statuslistPage.index][statusQuestion.index]}</div>
										<input type="hidden" class="listPageQuestion_${status.index}_${statuslistPage.index} question_${status.index}_${statuslistPage.index}_${statusQuestion.index + 1}" value="${question}" />
										<input class="" type="hidden" value="${scenarioRubyList[statuslistPage.index][statusQuestion.index]}" />
										<div class="IndexAnswer">${statusQuestion.index + 1}.</div>
										<input type="hidden" class="no" value="${statusQuestion.index + 1}">
										<div class="InforAnswer" id="inforAnswer_${status.index}_${statuslistPage.index}_${statusQuestion.index + 1}">
											<select class="listPageAnswer_${status.index}_${statuslistPage.index} selectBox answer_${status.index}_${statuslistPage.index}_${statusQuestion.index + 1}">
												<c:if test="${memoryScenarioAnswerList != null}">
													<option></option>
													<c:forEach items="${memoryScenarioAnswerList[statuslistPage.index][statusQuestion.index]}" var="answer" varStatus="statusAnswer">
														<option value="${answer}">${answer}</option>
													</c:forEach>
												</c:if>
											</select>
										</div>
									</div>
									</c:forEach>
								</div>
							</c:forEach>
						</c:if>
						<c:if test="${modeScenario == 2}">
							<div class="input_type_lesson_${lessonInfoDto.lessonNo}" style="margin-left: 50%;color: #444;opacity: 0.5;">
							</div>
								<script type="text/javascript">
									$(function() {
										setTextIputType("input_type_lesson_${lessonInfoDto.lessonNo}",
												getMethodName(getModeLanguage(parseInt(${lessonInfoDto.lessonNo}))));
									});
								</script>
							<c:forEach items="${scenarioList}" var="listPage" varStatus="statuslistPage">
								<div class="memory memoryPage_${statuslistPage.index}">
									<c:forEach items="${listPage}" var="question" varStatus="statusQuestion">
									<div class="row">
										<div class="IndexQuestion">${statusQuestion.index + 1}.</div>
										<div class="InforQuestion wall" id="indexQuestion_${status.index}_${statuslistPage.index}_${statusQuestion.index + 1}">${scenarioShowList[statuslistPage.index][statusQuestion.index]}</div>
										<input type="hidden" class="listPageQuestion_${status.index}_${statuslistPage.index} question_${status.index}_${statuslistPage.index}_${statusQuestion.index + 1}" value="${question}" />
										<input class="" type="hidden" value="${scenarioRubyList[statuslistPage.index][statusQuestion.index]}" />
										<input type="hidden" class="element" value="${element[statuslistPage.index][statusQuestion.index]}" />
										<div class="IndexAnswer">${statusQuestion.index + 1}.</div>
										<input type="hidden" class="no" value="${statusQuestion.index + 1}">
										<div class="InforAnswer" id="inforAnswer_${status.index}_${statuslistPage.index}_${statusQuestion.index + 1}">
										<c:forEach var="i" begin="1" end="${element[statuslistPage.index][statusQuestion.index]}">
											<input class="listPageAnswer_${status.index}_${statuslistPage.index} textBox answer_${status.index}_${statuslistPage.index}_${statusQuestion.index + 1}_${i}" type="text" style="width: 13%;"/>
										</c:forEach>
										</div>
									</div>
									</c:forEach>
								</div>
							</c:forEach>
						</c:if>
						<c:if test="${modeScenario == 3}">
							<c:forEach items="${scenarioList}" var="listPage" varStatus="statuslistPage">
								<div class="memory memoryPage_${statuslistPage.index}">
									<c:forEach items="${listPage}" var="question" varStatus="statusQuestion">
									<div class="row">
										<div class="IndexQuestion" style="padding-top: 10px;" >${statusQuestion.index + 1}.</div>
										<div class="InforQuestion wall rubyResult " style="line-height: 35px;" id="indexQuestion_${status.index}_${statuslistPage.index}_${statusQuestion.index + 1}">${question }</div>
										<input type="hidden" id="inputQuestionScenarioSpeaker_${statuslistPage.index}_${statusQuestion.index}" class="listPageQuestion_${status.index}_${statuslistPage.index} question_${status.index}_${statuslistPage.index}_${statusQuestion.index + 1}" value="${question}" />
										<input class="rubyWord" type="hidden" value="${scenarioRubyList[statuslistPage.index][statusQuestion.index]}" />
										<div class="invisible" id="inputQuestionJSonScenarioSpeaker_${statuslistPage.index}_${statusQuestion.index}">${scenarioJSonList[statuslistPage.index][statusQuestion.index]}</div>
										<div class="IndexAnswer" style="padding-top: 10px;">${statusQuestion.index + 1}.</div>
										<input type="hidden" class="no" value="${statusQuestion.index + 1}">
										<div class="InforAnswer" style="padding-top: 8px;" id="inforAnswer_${status.index}_${statuslistPage.index}_${statusQuestion.index + 1}">
											<img onclick="speakerAnswer('ScenarioSpeaker_${statuslistPage.index}_${statusQuestion.index}');" src="${pageContext.request.contextPath}/resources/images/speaker.gif" width="20" height="20" style="margin-right: 10%;" />
											<input class="inputAnswerScenarioSpeaker_${statuslistPage.index}_${statusQuestion.index} textBox answer_${status.index}_${statuslistPage.index}_${statusQuestion.index + 1} listPageAnswer_${status.index}_${statuslistPage.index}" type="text" readonly="readonly"/>
										</div>
									</div>
									</c:forEach>
								</div>
							</c:forEach>
						</c:if>
					</c:if>
				</div>
			</div>
		</div>
		<span>${listScenario}</span>
	</c:forEach>
</div>