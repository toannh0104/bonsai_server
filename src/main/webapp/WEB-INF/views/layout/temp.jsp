<%@ page pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<title><tiles:insertAttribute name="title" /></title>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

</head>
<body>
	<div class="wrapper">
<%-- 		<form:form action="admin/saveLesson" modelAttribute="lessonForm" id="lessonForm"> --%>
			<div>
				<tiles:insertAttribute name="body" />
			</div>
<%-- 		</form:form> --%>
	</div>
</body>
</html>





