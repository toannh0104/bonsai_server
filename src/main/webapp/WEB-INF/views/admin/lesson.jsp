<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="wrapper-info-lesson">

	<c:if test="${not empty lessonDto.lessonInfoDtos}">
		<c:forEach items="${lessonDto.lessonInfoDtos}" var="lessonInfoDto" varStatus="status">
			<div class="col5 lesson_info lesson_info_${status.index}">
				<div class="row">
					<div class="col2">
						<p>
						<c:choose>
							<c:when test="${isNew == true}">
								<a class="lessonIdText" onclick="showDialogLesson();">Lesson #${lessonInfoDto.lessonNo}</a>
							</c:when>
							<c:otherwise>
								<a class="lessonIdText">Lesson #${lessonInfoDto.lessonNo}</a>
								<a href="#" style="color: blue; text-decoration: underline;" onclick="showDialogLesson();">Edit</a>
							</c:otherwise>
						</c:choose>
							<input type="hidden" class="lessonNoHidden" name="lessonInfoForms[${status.index}].lessonNo" value="${lessonInfoDto.lessonNo}" />
							<input type="hidden" class="lessonIdHidden" name="lessonInfoForms[${status.index}].lessonId" value="${lessonDto.id}">
							<input type="hidden" class="lessonInfoIdHidden" name="lessonInfoForms[${status.index}].id" value="${lessonInfoDto.id}">
							<input class="practiceLimitHidden" type="hidden" name="lessonInfoForms[${status.index}].practiceLimit" value="${lessonInfoDto.practiceLimit}">
						</p>
						<span class="mar-left-10"> <span onclick="gotoLessonInfo(false, '${lessonDto.id}')"><img src="${pageContext.request.contextPath}/resources/images/previous.png" width="20"
								height="20" /></span> <span onclick="gotoLessonInfo(true, '${lessonDto.id}')"><img src="${pageContext.request.contextPath}/resources/images/next.png" width="20"
								height="20" /></span>
						</span>
					</div>
					<div class="col1">
						<p class="pos-right">
						<c:choose>
							<c:when test="${isNew == true}">
								<a class="lbpractice" onclick="showDialogPractice();">Practice</a>
							</c:when>
							<c:otherwise>
								<a href="#" style="color: blue; text-decoration: underline;" onclick="showDialogPractice();">Edit</a>
								<a class="lbpractice">Practice</a>
							</c:otherwise>
						</c:choose>
						</p>
					</div>
					<div class="col1 pad-left5">
						<p>
						<c:choose>
							<c:when test="${isNew == true}">
								<a class="lbtest"  onclick="showDialogTest();">Test</a>
							</c:when>
							<c:otherwise>
								<a class="lbtest">Test</a>
								<a href="#" style="color: blue; text-decoration: underline;" onclick="showDialogTest();">Edit</a>
							</c:otherwise>
						</c:choose>
						</p>
					</div>
					<div class="col1">
						<p>ID： ${userId}</p>
					</div>
				</div>
				<div class="row">
					<div class="col3">
						<p class="pos-right">
							<c:if test="${lessonDto.learningType == '2' }">
								<span class="width-7 mar-left">基本</span>
								<span class="mar-left">/</span>
							</c:if>
							<span class="width-7 mar-left">記憶力</span>
							<span class="width-30 mar-left">(<spring:eval expression="@EN.getProperty('lbl.memory')" />)</span>
							<c:if test="${languageId != 'EN'}">
								<span class="width-30 mar-left">(<spring:eval expression="@${languageId}.getProperty('lbl.memory')" />)</span>
							</c:if>
							<c:if test="${languageId == 'EN'}">
								<span class="width-30 mar-left">&nbsp;</span>
							</c:if>
							<span class="width-10 mar-left">Score：</span>
							<fmt:parseNumber parseLocale="JA" var="practiceMemoryCondition" type="number" value="${lessonInfoDto.practiceMemoryCondition}" />
							<c:if test="${empty lessonInfoDto.practiceMemoryCondition}">
								<span class="pra-memory-per mar-right">0%（0%）</span>
							</c:if>
							<c:if test="${not empty lessonInfoDto.practiceMemoryCondition}">
								<span class="pra-memory-per mar-right">0%（${practiceMemoryCondition}%）</span>
							</c:if>
							<input type="hidden" name="lessonInfoForms[${status.index}].practiceMemoryCondition" class="practiceMemoryConditionHidden" value="${practiceMemoryCondition}" />
						</p>
					</div>
					<div class="col1 pad-left5">
						<p class="mar-bot5">
							<fmt:parseNumber parseLocale="JA" var="testMemoryCondition" type="number" value="${lessonInfoDto.testMemoryCondition}" />
							<c:if test="${empty lessonInfoDto.testMemoryCondition}">
								<span class="test-memory-per">0%（0%）</span>
							</c:if>
							<c:if test="${not empty lessonInfoDto.testMemoryCondition}">
								<span class="test-memory-per">0%（${testMemoryCondition}%）</span>
							</c:if>
							<input type="hidden" name="lessonInfoForms[${status.index}].testMemoryCondition" class="testMemoryConditionHidden" value="${testMemoryCondition}" />
						</p>
					</div>
					<div class="col1">
						<p>Name: ${userName}</p>
					</div>
				</div>
				<div class="row">
					<div class="col3">
						<p class="pos-right">
							<c:if test="${lessonDto.learningType == '2' }">
								<span class="width-7 mar-left">応用1</span>
								<span class="mar-left">/</span>
							</c:if>
							<span class="width-7 mar-left">筆記力</span>
							<span class="width-30 mar-left">(<spring:eval expression="@EN.getProperty('lbl.writting')" />)</span>
							<c:if test="${languageId != 'EN'}">
								<span class="width-30 mar-left">(<spring:eval expression="@${languageId}.getProperty('lbl.writting')" />)</span>
							</c:if>
							<c:if test="${languageId == 'EN'}">
								<span class="width-30 mar-left">&nbsp;</span>
							</c:if>
							<span class="width-10 mar-left">Score：</span>
							<fmt:parseNumber parseLocale="JA" var="practiceWritingCondition" type="number" value="${lessonInfoDto.practiceWritingCondition}" />
							<c:if test="${empty lessonInfoDto.practiceWritingCondition}">
								<span class="pra-write-per mar-right">0%（0%）</span>
							</c:if>
							<c:if test="${not empty lessonInfoDto.practiceWritingCondition}">
								<span class="pra-write-per mar-right">0%（${practiceWritingCondition}%）</span>
							</c:if>
							<input type="hidden" name="lessonInfoForms[${status.index}].practiceWritingCondition" class="practiceWritingConditionHidden" value="${practiceWritingCondition}" />
						</p>
					</div>
					<div class="col1 pad-left5">
						<p class="mar-bot5">
							<fmt:parseNumber parseLocale="JA" var="testWritingCondition" type="number" value="${lessonInfoDto.testWritingCondition}" />
							<c:if test="${empty lessonInfoDto.testWritingCondition}">
								<span class="test-write-per">0%（0%）</span>
							</c:if>
							<c:if test="${not empty lessonInfoDto.testWritingCondition}">
								<span class="test-write-per">0%（${testWritingCondition}%）</span>
							</c:if>
							<input type="hidden" name="lessonInfoForms[${status.index}].testWritingCondition" class="testWritingConditionHidden" value="${testWritingCondition}" />
						</p>
					</div>
					<div class="col1">
						<p>Message</p>
					</div>
				</div>
				<div class="row">
					<div class="col3">
						<p class="pos-right">
							<c:if test="${lessonDto.learningType == '2' }">
								<span class="width-7 mar-left">応用2</span>
								<span class="mar-left">/</span>
							</c:if>
							<span class="width-7 mar-left">会話力</span>
							<span class="width-30 mar-left">(<spring:eval expression="@EN.getProperty('lbl.conversation')" />)</span>
							<c:if test="${languageId != 'EN'}">
								<span class="width-30 mar-left">(<spring:eval expression="@${languageId}.getProperty('lbl.conversation')" />)</span>
							</c:if>
							<c:if test="${languageId == 'EN'}">
								<span class="width-30 mar-left">&nbsp;</span>
							</c:if>
							<span class="width-10 mar-left">Score：</span>
							<fmt:parseNumber parseLocale="JA" var="practiceConversationCondition" type="number" value="${lessonInfoDto.practiceConversationCondition}" />
							<c:if test="${empty lessonInfoDto.practiceConversationCondition}">
								<span class="pra-conver-per mar-right">0%（0%）</span>
							</c:if>
							<c:if test="${not empty lessonInfoDto.practiceConversationCondition}">
								<span class="pra-conver-per mar-right">0%（${practiceConversationCondition}%）</span>
							</c:if>
							<input type="hidden" name="lessonInfoForms[${status.index}].practiceConversationCondition" class="practiceConversationConditionHidden"
								value="${practiceConversationCondition}" />
						</p>
					</div>
					<div class="col1 pad-left5">
						<p class="mar-bot5">
							<fmt:parseNumber parseLocale="JA" var="testConversationCondition" type="number" value="${lessonInfoDto.testConversationCondition}" />
							<c:if test="${empty lessonInfoDto.testConversationCondition}">
								<span class="test-conver-per">0%（0%）</span>
							</c:if>
							<c:if test="${not empty lessonInfoDto.testConversationCondition}">
								<span class="test-conver-per">0%（${testConversationCondition}%）</span>
							</c:if>
							<input type="hidden" name="lessonInfoForms[${status.index}].testConversationCondition" class="testConversationConditionHidden"
								value="${testConversationCondition}" />
						</p>
					</div>
					<div class="col1">
						<p onclick="allLessonInfo(this);">All Lesson Info</p>
					</div>
				</div>
			</div>
		</c:forEach>
	</c:if>

</div>

<div id="overlay"></div>
<div id="modal_dialog">
	<div id="modal_dialog_title"></div>
	<div id="content-dialog"></div>
	<div id="dialog-action">
		<input type='button' value='OK' id='btnYes' />
		<input type='button' value='キャンセル' id='btnNo' />
	</div>
</div>