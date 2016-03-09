<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="wrapper-info-lesson">

	<c:forEach items="${lessonInfoDtos}" var="lessonInfoDto" varStatus="status">
		<div class="col5 lesson_info lesson_info_${status.index}">
			<div class="row">
				<div class="col2">
					<p>
						<a href="#" class="lessonId">Lesson #${lessonInfoDto.lessonNo}</a>
						<input type="hidden" id="lessonInfoId" name="lessonInfoForms[${status.index}].id" value="${lessonInfoDto.id}" />
						<input type="hidden" id="lessonNo" name="lessonInfoForms[${status.index}].lessonNo" value="${lessonInfoDto.lessonNo}" />
						<input type="hidden" name="lessonInfoForms[${status.index}].lessonId" value="${lessonDto.id}">
						<input type="hidden" name="lessonInfoForms[${status.index}].modeLanguage" value="${modeLanguage}">
					</p>
					<span class="mar-left-10"> 
					<span onclick="gotoLessonInfo(false)"><img src="${pageContext.request.contextPath}/resources/images/previous.png" width="20" height="20" /></span> 
					<span onclick="gotoLessonInfo(true)"><img src="${pageContext.request.contextPath}/resources/images/next.png" width="20" height="20" /></span>
					</span>
				</div>
				<div class="col1">
					<p class="pos-right">
						<a href="#" class="lbpractice">Practice</a>
					</p>
				</div>
				<div class="col1 pad-left5">
					<p>
						<a href="#" class="lbtest">Test</a>
					</p>
				</div>
				<div class="col1">
					<p>ID ： ${userId}</p>
				</div>
			</div>
			<div class="row">
				<div class="col3">
					<p class="pos-right">
						<c:if test="${lessonDto.learningType == '2' }">
							<span class="mar-left"><a href="#" class="mode-memory">基本</a></span>
							<span class="mar-left">/</span>
						</c:if>
						<c:choose>
							<c:when test="${languageId != 'JA' and languageId != 'EN'}">
								<span class="width-10 mar-left"><a href="#" class="mode-memory">記憶力</a></span>
							</c:when>
							<c:otherwise>
								<span class="width-10 mar-left mar-left-23-per"><a href="#" class="mode-memory">記憶力</a></span>
							</c:otherwise>
						</c:choose>
						<span class="width-30 mar-left">(<spring:eval expression="@EN.getProperty('lbl.memory')" />)</span>
						<span class="width-30 mar-left"><c:if test="${languageId != 'JA' and languageId != 'EN'}">(<spring:eval expression="@${languageId}.getProperty('lbl.memory')" />)</c:if></span>
						<span class="width-10 mar-left">Score：</span>
						<c:choose>
							<c:when test="${lessonInfoDto.practiceMemoryScore == null}">
								<c:set var="practiceMemoryScore" scope="session" value="0" />
							</c:when>
							<c:otherwise>
								<fmt:parseNumber parseLocale="JA" var="practiceMemoryScore" integerOnly="true" type="number" value="${lessonInfoDto.practiceMemoryScore}" />
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${lessonInfoDto.practiceMemoryCondition == null}">
								<c:set var="practiceMemoryCondition" scope="session" value="0" />
							</c:when>
							<c:otherwise>
								<fmt:parseNumber parseLocale="JA" var="practiceMemoryCondition" integerOnly="true" type="number" value="${lessonInfoDto.practiceMemoryCondition}" />
							</c:otherwise>
						</c:choose>
						<span class="pra-memory-per mar-right"><c:out value="${practiceMemoryScore}" />%(<c:out value="${practiceMemoryCondition}" />%)</span>
						<input type="hidden" name="lessonInfoForms[${status.index}].practiceMemoryCondition" class="practiceMemoryConditionHidden" value="${practiceMemoryCondition}" />
					</p>
				</div>
				<div class="col1 pad-left5">
					<p class="mar-bot5">
						<c:choose>
							<c:when test="${lessonInfoDto.testMemoryScore == null}">
								<c:set var="testMemoryScore" scope="session" value="0" />
							</c:when>
							<c:otherwise>
								<fmt:parseNumber parseLocale="JA" var="testMemoryScore" integerOnly="true" type="number" value="${lessonInfoDto.testMemoryScore}" />
							</c:otherwise>
						</c:choose>
						<fmt:parseNumber parseLocale="JA" var="testMemoryCondition" integerOnly="true" type="number" value="${lessonInfoDto.testMemoryCondition}" />
						<span class="test-memory-per"><c:out value="${testMemoryScore}" />%（<c:out value="${testMemoryCondition}" />%）</span>
						<input class="testMemoryCondition" type="hidden" name="lessonInfoForms[${status.index}].testMemoryCondition" value="${testMemoryCondition}" />
					</p>
				</div>
				<div class="col1">
					<p>Name : ${userName}</p>
				</div>
			</div>
			<div class="row">
				<div class="col3">
					<p class="pos-right">
						<c:if test="${lessonDto.learningType == '2' }">
							<span class="mar-left"><a href="#" class="mode-write">応用1</a></span>
							<span class="mar-left">/</span>
						</c:if>
						<c:choose>
							<c:when test="${languageId != 'JA' and languageId != 'EN'}">
								<span class="width-10 mar-left"><a href="#" class="mode-write">筆記力</a></span>
							</c:when>
							<c:otherwise>
								<span class="width-10 mar-left mar-left-23-per"><a href="#" class="mode-write">筆記力</a></span>
							</c:otherwise>
						</c:choose>
						<span class="width-30 mar-left">(<spring:eval expression="@EN.getProperty('lbl.writting')" />)</span>
						<span class="width-30 mar-left"><c:if test="${languageId != 'JA' and languageId != 'EN'}">(<spring:eval expression="@${languageId}.getProperty('lbl.writting')" />)</c:if></span>
						<span class="width-10 mar-left">Score：</span>
						<c:choose>
							<c:when test="${lessonInfoDto.practiceWritingScore == null}">
								<c:set var="practiceWritingScore" scope="session" value="0" />
							</c:when>
							<c:otherwise>
								<fmt:parseNumber parseLocale="JA" var="practiceWritingScore" integerOnly="true" type="number" value="${lessonInfoDto.practiceWritingScore}" />
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${lessonInfoDto.practiceWritingCondition == null}">
								<c:set var="practiceWritingCondition" scope="session" value="0" />
							</c:when>
							<c:otherwise>
								<fmt:parseNumber parseLocale="JA" var="practiceWritingCondition" integerOnly="true" type="number" value="${lessonInfoDto.practiceWritingCondition}" />
							</c:otherwise>
						</c:choose>
						<span class="pra-write-per mar-right"><c:out value="${practiceWritingScore}" />%(<c:out value="${practiceWritingCondition}" />%)</span>
						<input type="hidden" name="lessonInfoForms[${status.index}].practiceWritingCondition" class="practiceWritingConditionHidden" value="${practiceWritingCondition}" />
					</p>
				</div>
				<div class="col1 pad-left5">
					<p class="mar-bot5">
						<c:choose>
							<c:when test="${lessonInfoDto.testWritingScore == null}">
								<c:set var="testWritingScore" scope="session" value="0" />
							</c:when>
							<c:otherwise>
								<fmt:parseNumber parseLocale="JA" var="testWritingScore" integerOnly="true" type="number" value="${lessonInfoDto.testWritingScore}" />
							</c:otherwise>
						</c:choose>
						<fmt:parseNumber parseLocale="JA" var="testWritingCondition" integerOnly="true" type="number" value="${lessonInfoDto.testWritingCondition}" />
						<span class="test-write-per"><c:out value="${testWritingScore}" />%（<c:out value="${testWritingCondition}" />%）</span>
						<input class="testWritingCondition" type="hidden" name="lessonInfoForms[${status.index}].testWritingCondition" value="${testWritingCondition}" />
					</p>
				</div>
				<div class="col1">
					<p>Message ##</p>
				</div>
			</div>
			<div class="row">
				<div class="col3">
					<p class="pos-right">
						<c:if test="${lessonDto.learningType == '2' }">
							<span class="mar-left"><a href="#" class="mode-speech">応用2</a></span>
							<span class="mar-left">/</span>
						</c:if>
						<c:choose>
							<c:when test="${languageId != 'JA' and languageId != 'EN'}">
								<span class="width-10 mar-left"><a href="#" class="mode-speech">会話力</a></span>
							</c:when>
							<c:otherwise>
								<span class="width-10 mar-left mar-left-23-per"><a href="#" class="mode-speech">会話力</a></span>
							</c:otherwise>
						</c:choose>
						<span class="width-30 mar-left">(<spring:eval expression="@EN.getProperty('lbl.conversation')" />)</span>
						<span class="width-30 mar-left"><c:if test="${languageId != 'JA' and languageId != 'EN'}">(<spring:eval expression="@${languageId}.getProperty('lbl.conversation')" />)</c:if></span>
						<span class="width-10 mar-left">Score：</span>
						<c:choose>
							<c:when test="${lessonInfoDto.practiceConversationScore == null}">
								<c:set var="practiceConversationScore" scope="session" value="0" />
							</c:when>
							<c:otherwise>
								<fmt:parseNumber parseLocale="JA" var="practiceConversationScore" integerOnly="true" type="number" value="${lessonInfoDto.practiceConversationScore}" />
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${lessonInfoDto.practiceConversationCondition == null}">
								<c:set var="practiceConversationCondition" scope="session" value="0" />
							</c:when>
							<c:otherwise>
								<fmt:parseNumber parseLocale="JA" var="practiceConversationCondition" integerOnly="true" type="number" value="${lessonInfoDto.practiceConversationCondition}" />
							</c:otherwise>
						</c:choose>
						<span class="pra-conver-per mar-right"><c:out value="${practiceConversationScore}" />%(<c:out value="${practiceConversationCondition}" />%)</span>
						<input type="hidden" name="lessonInfoForms[${status.index}].practiceConversationCondition" class="practiceConversationConditionHidden"
							value="${practiceConversationCondition}" />
					</p>
				</div>
				<div class="col1 pad-left5">
					<p class="mar-bot5">
						<c:choose>
							<c:when test="${lessonInfoDto.testConversationScore == null}">
								<c:set var="testConversationScore" scope="session" value="0" />
							</c:when>
							<c:otherwise>
								<fmt:parseNumber parseLocale="JA" var="testConversationScore" integerOnly="true" type="number" value="${lessonInfoDto.testConversationScore}" />
							</c:otherwise>
						</c:choose>
						<fmt:parseNumber parseLocale="JA" var="testConversationCondition" integerOnly="true" type="number" value="${lessonInfoDto.testConversationCondition}" />
						<span class="test-conver-per"><c:out value="${testConversationScore}" />%（<c:out value="${testConversationCondition}" />%）</span> 
						<input class="testConversationCondition" type="hidden" name="lessonInfoForms[${status.index}].testConversationCondition" value="${testConversationCondition}" />
					</p>
				</div>
				<div class="col1">
					<p onclick="allLessonInfo(this);">All Lesson Info</p>
				</div>
			</div>
		</div>
	</c:forEach>

</div>