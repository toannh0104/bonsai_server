<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/listLesson.js"></script>
<div class="listLesson">
	<form:form modelAttribute="lessonForm" id="lessonForm">
		<input type="hidden" id="url" value="${pageContext.request.contextPath}">
		<span>Language: </span>
			<form:select path="languageId" name="languageId" items="${languageMap}">
				<form:options items="${languageMap}" />
			</form:select>
		<br/>
		<table>
			<tr>
				<th>Lesson Name</th>
				<th>Lesson Type</th>
				<th>Lesson Level</th>
			</tr>
			<c:forEach items="${lstLessonDto}" var="lessonDto" varStatus="status">
				<tr>
					<td><a href="#" onclick="clientSecurity(${lessonDto.id });">${lessonDto.lessonName }</a></td>
					<td><c:if test="${lessonDto.lessonType == 0}">
							conversation
						</c:if> <c:if test="${lessonDto.lessonType == 1}">
							vocabulary
						</c:if></td>
					<td>N${lessonDto.level }</td>
				</tr>
			</c:forEach>
		</table>
	</form:form>
</div>