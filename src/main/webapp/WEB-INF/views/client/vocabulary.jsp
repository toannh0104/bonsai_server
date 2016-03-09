<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="wrapper-vocabulary">
	<c:forEach items="${lessonInfoDtos}" var="lessonInfoDto" varStatus="status">
		<div class="theLessonVocabulary lessonVocabulary_${status.index}">
			<div class="LessonVocabulary">
				<div id="lessonVocabularyInforArea">
					<div id="lessonVocabularyTitle">単語学習:</div>
					<div id="lessonVocabularyName" class="vocabularyName">
						<span>${lessonInfoDto.scenarioName}</span>
						<span>（<spring:eval expression="@EN.getProperty('lbl.vocabularyName')" />:${lessonInfoDto.scenarioNameEn}）</span>
						<c:if test="${languageId != 'JA' and languageId != 'EN'}">
							<span>（<spring:eval expression="@${languageId}.getProperty('lbl.vocabularyName')" />:${lessonInfoDto.scenarioNameLn}）</span>
						</c:if>
					</div>
					<input id="lessonVocabularyNameHidden" type="hidden" name="lessonInfoForms[${status.index}].vocabularyName" value="${lessonInfoDto.scenarioName }" />
					<div id="lessonVocabularyNameEn"></div>
					<div id="lessonVocabularyNamelanguage"></div>
				</div>
				<%-- <c:if test="${modeScenario != null}"> --%>
				<div class="modeScenarioNotNull invisible">
					<div id="practiceVocabularyInforArea">
						<div id="practiceVocabularyClientTitleBefor">
							<div>&nbsp;
								<%-- <c:if test="${modeVocabulary != null}"> --%>
									<a class="score_vocabulary modeScore invisible" onclick="checkBeforScore(${status.index}, ${lessonDto.lessonType}, 'vocabulary')">Score</a>
									<a class="score_vocabulary_test_memory modeScore invisible" onclick="checkBeforScore(${status.index}, ${lessonDto.lessonType}, 'vocabulary')">Score</a>
									<a class="score_vocabulary_test_writing modeScore invisible" onclick="checkBeforScore(${status.index}, ${lessonDto.lessonType}, 'vocabulary')">Score</a>
									<a class="score_vocabulary_test_speech modeScore invisible" onclick="checkBeforScore(${status.index}, ${lessonDto.lessonType}, 'vocabulary')">Score</a>
								<%-- </c:if> --%>
							</div>
							<div>
								<c:if test="${practice_test == null or practice_test == 'practice'}">練習：</c:if>
								<c:if test="${practice_test == 'test'}">テスト：</c:if>
							</div>
						</div>
						<div class="numberPracticeMemoryVocabulary numberVocabulary invisible"></div>
						<div class="numberPracticeWritingVocabulary numberVocabulary invisible"></div>
						<div class="numberPracticeConversationVocabulary numberVocabulary invisible"></div>
						<div class="numberTestMemoryVocabulary numberVocabulary invisible"></div>
						<div class="numberTestWritingVocabulary numberVocabulary invisible"></div>
						<div class="numberTestConversationVocabulary numberVocabulary invisible"></div>
						<%-- <div class="numberPractice">${practiceNo}<c:if test="${modeVocabulary != null}">/</c:if>${pageLimit}</div> --%>
						<input type="hidden" id="pageLimitClient" value="${pageLimit}">
						<input type="hidden" id="pageIndexClient" value="${pageIndex}">
						<div id="practiceVocabularyTitleAfter">回</div>
					</div>
				<%-- </c:if> --%>
				</div>
			</div>
			<input type="hidden" id="typeVocabulary" value="${modeVocabulary}">

			<div class="vocabularyImg invisible">
				<img alt="" onclick="onClickVocabularyImg();" src="${pageContext.request.contextPath}/resources/images/Vocabulary.jpg" width="300">
			</div>
			<div class="contenInforVocabulary invisible">
				<div class="modeScenarioNull invisible">
					<div class="LanguageVocabulary">
						<div class="LanguageVocabularyQuestion">
							<div>問題</div>
							<div>(<spring:eval expression="@EN.getProperty('lbl.question')" />)</div>
							<c:if test="${languageId != 'JA' and languageId != 'EN'}">
								<div>(<spring:eval expression="@${languageId}.getProperty('lbl.question')" />)</div>
							</c:if>
						</div>
						<div class="LanguageVocabularyAnswer">
							<div>解説</div>
							<div>(<spring:eval expression="@EN.getProperty('lbl.explanation')" />)</div>
							<c:if test="${languageId != 'JA' and languageId != 'EN'}">
								<div>(<spring:eval expression="@${languageId}.getProperty('lbl.explanation')" />)</div>
							</c:if>
						</div>
					</div>
				</div>
				<div class="modeScenarioNotNull invisible">
					<div class="LanguageVocabulary">
						<div class="LanguageVocabularyQuestion">
							<div>問題</div>
							<div>(<spring:eval expression="@EN.getProperty('lbl.question')" />)</div>
							<c:if test="${languageId != 'JA' and languageId != 'EN'}">
								<div>(<spring:eval expression="@${languageId}.getProperty('lbl.question')" />)</div>
							</c:if>
						</div>
						<div class="LanguageVocabularyAnswer">
							<div>回答</div>
							<div>(<spring:eval expression="@EN.getProperty('lbl.answer')" />)</div>
							<c:if test="${languageId != 'JA' and languageId != 'EN'}">
								<div>(<spring:eval expression="@${languageId}.getProperty('lbl.answer')" />)</div>
							</c:if>
						</div>
					</div>
				</div>
				<div class="ContenClient">
					<div class="modeScenarioNull invisible">
						<c:forEach items="${lessonInfoDto.vocabularyDtos}" var="vocabularyDto" varStatus="sttVocabulary">
							<div class="row">
								<div class="ContenClientQuestion wall">${vocabularyDto.vocabularyOrder}.<span class="rubyResult" style="line-height: 35px;">${vocabularyDto.vocabulary}</span></div>
								<input class="rubyWord" type="hidden" value="${vocabularyDto.rubyWord}" />
								<div class="ContenClientAnswer wall" style="padding-top: 9px;">${vocabularyDto.vocabularyOrder}.${vocabularyDto.vocabularyLn}</div>
							</div>
						</c:forEach>
					</div>
					<%-- <c:if test="${modeVocabulary != null}"> --%>
					<div class="modeScenarioNotNull invisible">
					<div class="practiceMemoryVocabulary invisible"></div>
					<div class="practiceWritingVocabulary invisible"></div>
					<div class="practiceConversationVocabulary invisible"></div>
					<div class="testMemoryVocabulary invisible"></div>
					<div class="testWritingVocabulary invisible"></div>
					<div class="testConversationVocabulary invisible"></div>
						<c:if test="${vocabularyList != null}">
							<c:if test="${modeVocabulary == 1}">
								<c:forEach items="${vocabularyList}" var="listPage" varStatus="statuslistPage">
									<div class="memoryVocabulary memoryVocabularyPage_${statuslistPage.index}">
										<c:forEach items="${listPage}" var="question" varStatus="statusQuestion">
										<div class="row">
											<div class="IndexVocabularyQuestion">${statusQuestion.index + 1}.</div>
											<div class="InforVocabularyQuestion wall">${vocabularyShowList[statuslistPage.index][statusQuestion.index]}</div>
											<input type="hidden" class="listPageVocabularyQuestion_${status.index}_${statuslistPage.index} question_${status.index}_${statuslistPage.index}_${statusQuestion.index + 1}" value="${question}" />
											<input class="" type="hidden" value="${vocabularyRubyList[statuslistPage.index][statusQuestion.index]}" />
											<div class="IndexVocabularyAnswer">${statusQuestion.index + 1}.</div>
											<input type="hidden" class="no" value="${statusQuestion.index + 1}">
											<div class="InforVocabularyAnswer" id="inforAnswerVoca_${status.index}_${statuslistPage.index}_${statusQuestion.index + 1}">
												<select class="listPageVocabularyAnswer_${status.index}_${statuslistPage.index} selectBox answer_${status.index}_${statuslistPage.index}_${statusQuestion.index + 1}">
													<c:if test="${memoryVocabularyAnswerList != null}">
														<option></option>
														<c:forEach items="${memoryVocabularyAnswerList[statuslistPage.index][statusQuestion.index]}" var="answer" varStatus="statusAnswer">
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
							<c:if test="${modeVocabulary == 2}">
								<div class="input_type_voca_${lessonInfoDto.lessonNo}" style="margin-left: 60%;color: #444;opacity: 0.5;">
								</div>
								<script type="text/javascript">
									$(function() {
										setTextIputType("input_type_voca_${lessonInfoDto.lessonNo}",
												getMethodName(getModeLanguage(parseInt(${lessonInfoDto.lessonNo}))));
									});
								</script>
								<c:forEach items="${vocabularyList}" var="listPage" varStatus="statuslistPage">
								<div class="memoryVocabulary memoryVocabularyPage_${statuslistPage.index}">
									<c:forEach items="${listPage}" var="question" varStatus="statusQuestion">
									<div class="row">
										<div class="IndexVocabularyQuestion">${statusQuestion.index + 1}.</div>
										<div class="InforVocabularyQuestion wall">${vocabularyShowList[statuslistPage.index][statusQuestion.index]}</div>
										<input type="hidden" class="listPageVocabularyQuestion_${status.index}_${statuslistPage.index} question_${status.index}_${statuslistPage.index}_${statusQuestion.index + 1}" value="${question}" />
										<input class="" type="hidden" value="${vocabularyRubyList[statuslistPage.index][statusQuestion.index]}" />
										<div class="IndexVocabularyAnswer">${statusQuestion.index + 1}.</div>
										<input type="hidden" class="no" value="${statusQuestion.index + 1}">
										<div class="InforVocabularyAnswer" id="inforAnswerVoca_${status.index}_${statuslistPage.index}_${statusQuestion.index + 1}">
											<input class="listPageVocabularyAnswer_${status.index}_${statuslistPage.index} textBox" type="text"/>
										</div>
									</div>
									</c:forEach>
								</div>
							</c:forEach>
							</c:if>
							<c:if test="${modeVocabulary == 3}">
								<c:forEach items="${vocabularyList}" var="listPage" varStatus="statuslistPage">
								<div class="memoryVocabulary memoryVocabularyPage_${statuslistPage.index}">
									<c:forEach items="${listPage}" var="question" varStatus="statusQuestion">
									<div class="row">
										<div class="IndexVocabularyQuestion" style="padding-top: 10px;">${statusQuestion.index + 1}.</div>
										<div class="InforVocabularyQuestion wall rubyResult" style="line-height: 35px;">${question}</div>
										<input type="hidden" id="inputQuestionVocabularySpeaker_${statuslistPage.index}_${statusQuestion.index}" class="listPageVocabularyQuestion_${status.index}_${statuslistPage.index} question_${status.index}_${statuslistPage.index}_${statusQuestion.index + 1}" value="${question}" />
										<div class="invisible" id="inputQuestionJSonVocabularySpeaker_${statuslistPage.index}_${statusQuestion.index}">${vocabularyJSonList[statuslistPage.index][statusQuestion.index]}</div>
										<input class="rubyWord" type="hidden" value="${vocabularyRubyList[statuslistPage.index][statusQuestion.index]}" />
										<div class="IndexVocabularyAnswer" style="padding-top: 10px;">${statusQuestion.index + 1}.</div>
										<input type="hidden" class="no" value="${statusQuestion.index + 1}">
										<div class="InforVocabularyAnswer" style="padding-top: 8px;" id="inforAnswerVoca_${status.index}_${statuslistPage.index}_${statusQuestion.index + 1}">
											<img onclick="speakerAnswer('VocabularySpeaker_${statuslistPage.index}_${statusQuestion.index}');" src="${pageContext.request.contextPath}/resources/images/speaker.gif" width="20" height="20" style="margin-right: 10%;" />
											<input class="inputAnswerVocabularySpeaker_${statuslistPage.index}_${statusQuestion.index} listPageVocabularyAnswer_${status.index}_${statuslistPage.index} textBox" type="text" readonly="readonly"/>
										</div>
									</div>
									</c:forEach>
								</div>
							</c:forEach>
							</c:if>
						</c:if>
					</div>
					<%-- </c:if> --%>
				</div>
			</div>
		</div>
	</c:forEach>
</div>