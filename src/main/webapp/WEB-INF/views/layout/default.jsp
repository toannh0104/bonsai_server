<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="java.io.*" %>
<%@page import="java.util.ResourceBundle"%>

<html>
<head>
<title><tiles:insertAttribute name="title" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<tiles:insertAttribute name="css" />
<tiles:insertAttribute name="js" />
</head>
<body>
	<div class="wrapper">
		<form:form action="admin/saveLesson" modelAttribute="lessonForm" id="lessonForm">
			<input type="hidden" value="${pageContext.request.contextPath}" id="url">
			<input type="hidden" value="${isNew}" id="isNew">
			<form:errors path="*" cssClass="error"/>
			<c:if test="${lessonDto.learningType == '1'}">
				<a href="${pageContext.request.contextPath}/admin/lesson/list" style="color: blue; text-decoration: underline;"> Back to lesson list</a>
			</c:if>
			<c:if test="${lessonDto.learningType == '2'}">
				<a href="${pageContext.request.contextPath}/admin/security/list" style="color: blue; text-decoration: underline;"> Back to lesson list</a>
			</c:if>
			<div id="header">
				<tiles:insertAttribute name="header" />
			</div>
			<div id="info-lesson">
				<tiles:insertAttribute name="infolesson" />
			</div>
			<c:choose>
				<c:when test="${lessonDto.lessonType == 1}">
					<div id="scenario" class="invisible">
						<tiles:insertAttribute name="scenario" />
					</div>
					<div id="vocabulary">
						<tiles:insertAttribute name="vocabulary" />
					</div>
				</c:when>
				<c:otherwise>
					<div id="scenario">
						<tiles:insertAttribute name="scenario" />
					</div>
					<div id="vocabulary">
						<tiles:insertAttribute name="vocabulary" />
					</div>
				</c:otherwise>
			</c:choose>
		</form:form>
	</div>
	<div id="loader" style="display: none;"> <div class="wrapper-loading"><img class="loading" src="${pageContext.request.contextPath}/resources/images/loading.png"></div> </div>
</body>
</html>





