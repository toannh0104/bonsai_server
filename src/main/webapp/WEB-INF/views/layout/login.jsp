<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/login.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet">
<div class="wrapper">
	<form:form class="form-signin" action="${pageContext.request.contextPath}/checkLogin" modelAttribute="userDto">
		<div class="form-signin-heading">
			<h2>
				<b>BONSAi</b>
			</h2>
			<h4>
				<ins>B</ins>usiness
				<ins>O</ins>riented
				<ins>N</ins>ihongo
				<ins>S</ins>ocial
				<ins>A</ins>pplication
				<ins>I</ins>ntelligence
			</h4>
			<div id="messageError" class="loginError">
				<form:errors path="*"/>
			</div>
		</div>
		<form:input path="userName" class="form-control" id="username" placeholder="Email Address" required="required" cssErrorClass="form-control text error"/>
		<form:password path="password" class="form-control password" id="password" placeholder="Password" required="required"/>
		<input type="hidden" id="uuid" name="uuid" value="${userDto.uuid}">
		<input type="hidden" id="device_name" name="device_name">
		<input type="hidden" id="device_version" name="device_version">
		<input type="hidden" id="location" name="location">
		<button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
	</form:form>
</div>