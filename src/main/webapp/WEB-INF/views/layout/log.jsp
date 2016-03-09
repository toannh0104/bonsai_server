<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Admin Log</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/dataTables.bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/log.css" rel="stylesheet">

<script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/moment.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootbox.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dataTables.bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/log.js"></script>
</head>
<body>

	<div class="container">
		<h3>ログイン・ログデータ管理画面</h3>
		<ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
			<li id="log_login"><a href="#login" data-toggle="tab">ログイン管理</a></li>
			<li id="log_data"><a href="#data" data-toggle="tab">ログデータ</a></li>
		</ul>
		<div id="my-tab-content" class="tab-content">

			<input id="url" type="hidden" value="${pageContext.request.contextPath}">
			<input id="modeLog" type="hidden" value="${modeLog }">

			<div class="tab-pane" id="login">
				<form:form modelAttribute="logForm" id="logFormLogin">
					<div class="areaSeach f13" style="margin-top: 10px;">
						<div class="group1">
							<div class="form-group">
								<div class="log-title">
									<label for="terminal">端末：</label>
								</div>
								<div class="log-select">
									<select class="form-control" id="terminal">
										<c:forEach items="${terminal}" var="terminalValue">
											<c:choose>
												<c:when test="${logForm.terminal == terminalValue}">
													<option selected>${terminalValue}</option>
												</c:when>
												<c:otherwise>
													<option>${terminalValue}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<input type="hidden" name="terminal" class="terminal">
								</div>

							</div>
							<div class="form-group">
								<div class="log-title">
									<label for="userIdLogin">ユーザ名：</label>
								</div>
								<div class="log-input">
									<input type="text" class="form-control" id="userNameLogin" value="${logForm.userNameLogin}" onblur="checkValidateUserNameLogin()">
								</div>
								<input type="hidden" name="userNameLogin" class="userNameLogin">
							</div>
							<div class="form-group">
								<div class="log-title">
									<label for="location">ロケーション：</label>
								</div>
								<div class="log-input">
									<input type="text" class="form-control" id="location" value="${logForm.location}" onblur="checkValidateLocation()">
								</div>
								<input type="hidden" name="location" class="location">
							</div>
						</div>
						<div class="group2">

							<div class="form-group">
								<div class="log-from">
									<label for="loginPeriodFrom">ログイン期間： From</label>
								</div>
								<div class='input-group date log-date' id='datetimepickerFrom'>
									<input type='text' class="form-control f13" id="loginPeriodFrom" value="${logForm.loginPeriodFrom}"/>
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
									<input type="hidden" name="loginPeriodFrom" class="loginPeriodFrom">
								</div>
							</div>
							<div class="form-group">
								<div class="log-from">
									<label for="loginPeriodTo">To</label>
								</div>
								<div class='input-group date log-date' id='datetimepickerTo'>
									<input type='text' class="form-control f13" id="loginPeriodTo" value="${logForm.loginPeriodTo}"/>
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
								<input type="hidden" name="loginPeriodTo" class="loginPeriodTo">
							</div>
						</div>
						<div class="group3">
							<button type="button" class="btn btn-primary f13" onclick="searchLog(${modeLogin })">検索</button>
						</div>
					</div>

					<table id="logLogin" class="table table-striped table-bordered tableLogLogin">
						<thead>
							<tr class="f13">
								<th class="col_no center">連番</th>
								<th class="col_user_name center">ユーザ名</th>
								<th class="col_device_name center">端末</th>
								<th class="col_device_version center">バージョン</th>
								<th class="col_location center">ロケーション</th>
								<th class="col_start_time center">ログイン日時</th>
								<th class="col_end_time center">ログアウト日時</th>
								<th class="col_time center">経過時間（分）</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${listLogLogin}" var="logins" varStatus="loginLoop">
								<tr class="f12">
									<td class="col_no center">${loginLoop.index + 1}</td>
									<td class="col_user_name">
										<c:choose>
											<c:when test="${logins.userName != null}">${logins.userName }</c:when>
											<c:otherwise>&nbsp</c:otherwise>
										</c:choose>
									</td>
									<td class="col_device_name center">
										<c:choose>
											<c:when test="${logins.deviceName != null}">${logins.deviceName }</c:when>
											<c:otherwise>&nbsp</c:otherwise>
										</c:choose>
									</td>
									<td class="col_device_version center">
										<c:choose>
											<c:when test="${logins.deviceVersion != null}">${logins.deviceVersion }</c:when>
											<c:otherwise>&nbsp</c:otherwise>
										</c:choose>
									</td>
									<td class="col_location">
										<c:choose>
											<c:when test="${logins.location != null}">${logins.location }</c:when>
											<c:otherwise>&nbsp</c:otherwise>
										</c:choose>
									</td>
									<td class="col_start_time">
										<c:choose>
											<c:when test="${logins.startTime != null}">${logins.startTime }</c:when>
											<c:otherwise>&nbsp</c:otherwise>
										</c:choose>
									</td>
									<td class="col_end_time">
										<c:choose>
											<c:when test="${logins.endTime != null}">${logins.endTime }</c:when>
											<c:otherwise>&nbsp</c:otherwise>
										</c:choose>
									</td>
									<td class="col_time center">
										<c:choose>
											<c:when test="${logins.time != null}">${logins.time }</c:when>
											<c:otherwise>&nbsp</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</form:form>
			</div>
			<div class="tab-pane" id="data">
				<form:form modelAttribute="logForm" id="logFormData">
					<div class="areaSeach" style="margin-top: 10px;">
						<div class="group1">
							<div class="form-group">
								<div class="log-title">
									<label for="userIdData">ユーザ名：</label>
								</div>
								<div class="log-input">
									<input type="text" class="form-control" id="userNameData" value="${logForm.userNameData}" onblur="checkValidateUserNameData()">
								</div>
								<input type="hidden" name="userNameData" class="userNameData">
							</div>
							<div class="form-group">
								<div class="log-title">
									<label for="modeLearning">学習モード：</label>
								</div>
								<div class="log-select">
									<select class="form-control" id="modeLearning">
										<c:forEach items="${modeLearning}" var="modeLearningValue">
											<c:choose>
												<c:when test="${logForm.modeLearning == modeLearningValue}">
													<option selected>${modeLearningValue}</option>
												</c:when>
												<c:otherwise>
													<option>${modeLearningValue}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
								<input type="hidden" name="modeLearning" class="modeLearning">
							</div>
							<div class="form-group">
								<div class="log-title">
									<label for="lessonNo">レッスン番号：</label>
								</div>
								<div class="log-input">
									<input type="text" class="form-control" id="lessonNo" value="${logForm.lessonNo}" onblur="checkValidateLessonNo()">
								</div>
								<input type="hidden" name="lessonNo" class="lessonNo">
							</div>
						</div>
						<div class="group2">
							<div class="form-group">
								<div class="log-title">
									<label for="course">コース：</label>
								</div>
								<div class="log-input">
									<input type="text" class="form-control" id="course" value="${logForm.course}" onblur="checkValidateCourse()">
								</div>
								<input type="hidden" name="course" class="course">
							</div>
							<div class="form-group">
								<div class="log-title">
									<label for="modePracticeOrTest">練習・テスト：</label>
								</div>
								<div class="log-select">
									<select class="form-control" id="modePracticeOrTest">
										<c:forEach items="${modePracticeOrTest}" var="modePracticeOrTestValue">
											<c:choose>
												<c:when test="${logForm.modePracticeOrTest == modePracticeOrTestValue}">
													<option selected>${modePracticeOrTestValue}</option>
												</c:when>
												<c:otherwise>
													<option>${modePracticeOrTestValue}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
								<input type="hidden" name="modePracticeOrTest" class="modePracticeOrTest">
							</div>
							<div class="form-group">
								<div class="log-title">
									<label for="modeSkill">タイプ：</label>
								</div>
								<div class="log-select">
									<select class="form-control" id="modeSkill">
										<c:forEach items="${modeSkill}" var="modeSkillValue">
											<c:choose>
												<c:when test="${logForm.modeSkill == modeSkillValue})">
													<option selected>${modeSkillValue}</option>
												</c:when>
												<c:otherwise>
													<option>${modeSkillValue}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
								<input type="hidden" name="modeSkill" class="modeSkill">
							</div>
						</div>
						<div class="group3">
							<button type="button" class="btn btn-primary" onclick="searchLog(${modeData })">検索</button>
						</div>
					</div>
					<table id="logData" class="table table-striped table-bordered tableLogData">
						<thead>
							<tr class="f13">
								<th class="col_no center">連番</th>
								<th class="col_user_name center">ユーザ名</th>
								<th class="col_course center">コース</th>
								<th class="col_mode_learning center">学習モード</th>
								<th class="col_lesson_no center">レッスン番号</th>
								<th class="col_scenario_or_word center">シナリオ単語</th>
								<th class="col_start_time center">作成日時</th>
								<th class="col_end_time center">更新日時</th>
								<th class="col_time center">学習時間(分）</th>
								<th class="col_detail center no-sort">詳細</th>
							</tr>
						</thead>
						<tbody class="tbodyLogData f12">
							<c:forEach items="${listLogData}" var="datas" varStatus="dataLoop">
								<tr>
									<td class="col_no center">${dataLoop.index + 1}</td>
									<td class="col_user_name">
										<c:choose>
											<c:when test="${datas.userName != null}">${datas.userName }</c:when>
											<c:otherwise>&nbsp</c:otherwise>
										</c:choose>
									</td>
									<td class="col_course">
										<c:choose>
											<c:when test="${datas.course != null}">${datas.course }</c:when>
											<c:otherwise>&nbsp</c:otherwise>
										</c:choose>
									</td>
									<td class="col_mode_learning center">
										<c:choose>
											<c:when test="${datas.modeLearning != null}">${datas.modeLearning }</c:when>
											<c:otherwise>&nbsp</c:otherwise>
										</c:choose>
									</td>
									<td class="col_lesson_no center">
										<c:choose>
											<c:when test="${datas.lessonNo != null}">${datas.lessonNo }</c:when>
											<c:otherwise>&nbsp</c:otherwise>
										</c:choose>
									</td>
									<td class="col_scenario_or_word">
										<c:choose>
											<c:when test="${datas.scenarioOrWord != null}">${datas.scenarioOrWord }</c:when>
											<c:otherwise>&nbsp</c:otherwise>
										</c:choose>
									</td>
									<td class="col_start_time">
										<c:choose>
											<c:when test="${datas.startTime != null}">${datas.startTime }</c:when>
											<c:otherwise>&nbsp</c:otherwise>
										</c:choose>
									</td>
									<td class="col_end_time">
										<c:choose>
											<c:when test="${datas.endTime != null}">${datas.endTime }</c:when>
											<c:otherwise>&nbsp</c:otherwise>
										</c:choose>
									</td>
									<td class="col_time center">
										<c:choose>
											<c:when test="${datas.time != null}">${datas.time }</c:when>
											<c:otherwise>&nbsp</c:otherwise>
										</c:choose>
									</td>
									<td class="col_detail center"><a onclick="showDetailDialog('${datas.userName }',
																			'${datas.course }',
																			'${datas.modeLearning }',
																			'${datas.lessonNo }',
																			'${datas.scenarioOrWord }',
																			'${datas.modePracticeOrTest }',
																			'${datas.modeSkill }',
																			'${datas.practiceTime }',
																			'${datas.score }',
																			'${datas.answer}',
																			'${datas.startTime }',
																			'${datas.endTime }',
																			'${datas.time }');">詳細</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>
