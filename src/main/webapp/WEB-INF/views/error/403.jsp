<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/error.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/error.css" rel="stylesheet">
<div class="wrapper">
	<form:form class="form-signin">
	<input type="hidden" value="${pageContext.request.contextPath}" id="contextPath">
		<div class="form-signin-heading">
			<h3>
				<b class="err-403">Session Expired</b>
			</h3>
			<h4>
				<p>あなたの会期が無効または期限切れ。再びログインしてください</p>
			</h4>
		</div>
		<input onclick="returnLogin()" class="btn btn-lg btn-primary btn-block" type="button" value="Login"/>
	</form:form>
</div>