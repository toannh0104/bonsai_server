<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="lessionname">
	<div class="wrapper-header">
		<div class="group1">
			<c:choose>
				<c:when test="${isNew == true}">
					<div id="editlessonName" class="name pos-right">&nbsp;</div>
				</c:when>
				<c:otherwise>
					<div class="name pos-right special">${lessonDto.lessonName}</div>
					<a href="#" style="color: blue; text-decoration: underline;" id="editlessonName">Edit</a>
				</c:otherwise>
			</c:choose>
			<input type="hidden" id="lessonName" name="lessonName" value="${lessonDto.lessonName}" />
			<div>
				（${lessonDto.lessonNameEn}）
				<c:if test="${languageId != 'EN'}">
				（${lessonDto.lessonNameLn}）
			</c:if>
			</div>
			<input type="hidden" id="lessonNameEn" name="lessonNameEn" value="${lessonDto.lessonNameEn}"> <input type="hidden" id="lessonNameLn" name="lessonNameLn"
				value="${lessonDto.lessonNameLn}"> <input type="hidden" id="lessonRange" name="lessonRange" value="${lessonDto.lessonRange}"> <input type="hidden"
				id="lessonMethodRomaji" name="lessonMethodRomaji" value="${lessonDto.lessonMethodRomaji}"> <input type="hidden" id="lessonMethodHiragana" name="lessonMethodHiragana"
				value="${lessonDto.lessonMethodHiragana}"> <input type="hidden" id="lessonMethodKanji" name="lessonMethodKanji" value="${lessonDto.lessonMethodKanji}"> <input
				type="hidden" id="id" name="id" value="${lessonDto.id}"> <input type="hidden" id="learningType" name="learningType" value="${lessonDto.learningType}">
		</div>
		<div class="separator">|</div>
		<div class="group2">
			<c:choose>
				<c:when test="${isNew == true}">
					<div id="editMethod" class="method">&nbsp;</div>
				</c:when>
				<c:otherwise>
					<div class="method special">
						<c:if test="${lessonDto.lessonType == 0}">
							会話学習
							<div>
								（
								<spring:eval expression="@EN.getProperty('lbl.scentence')" />
								）
								<c:if test="${languageId != 'EN'}">
								（<spring:eval expression="@${languageId}.getProperty('lbl.scentence')" />）
							</c:if>
							</div>
						</c:if>
						<c:if test="${lessonDto.lessonType == 1}">
							単語学習
							<div>
								（
								<spring:eval expression="@EN.getProperty('lbl.vocabulary')" />
								）
								<c:if test="${languageId != 'EN'}">
								（<spring:eval expression="@${languageId}.getProperty('lbl.vocabulary')" />）
							</c:if>
							</div>
						</c:if>
					</div>
					<a href="#" style="color: blue; text-decoration: underline;" id="editMethod">Edit</a>
				</c:otherwise>
			</c:choose>
			<input type="hidden" id="lessonType" name="lessonType" value="${lessonDto.lessonType}">
		</div>
		<div class="separator">|</div>
		<div class="group3">
			<div class="group3-col-half">
				<c:choose>
					<c:when test="${isNew == true}">
						<div class="group3-row mar-top-12">
							<span>LEVEL：</span> <span id="editLevel" class="level">N5</span>
						</div>
					</c:when>
					<c:otherwise>
						<div class="group3-row mar-top-12">
							<span>LEVEL：</span> <span class="level">N${lessonDto.level}</span> <a href="#" style="color: blue; text-decoration: underline;" id="editLevel">Edit</a>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="group3-col-half">
				<div class="group3-row">
					<div class="group3-col-30 comp_ave">
						<span>comp.</span>
					</div>
					<div class="group3-col-30 comp_ave">
						<span>ave.</span>
					</div>
				</div>
				<div class="group3-row">
					<div class="group3-col-35">
						<span>（0%</span>
					</div>
					<div class="group3-col-half bor-left">
						<span style="padding-left: 3px;">0%）</span>
					</div>
				</div>
			</div>
			<c:choose>
				<c:when test="${isNew == true}">
					<input type="hidden" id="level" name="level" value="5">
				</c:when>
				<c:otherwise>
					<input type="hidden" id="level" name="level" value="${lessonDto.level}">
				</c:otherwise>
			</c:choose>
			<input type="hidden" id="percentComplete" name="percentComplete" value=""> <input type="hidden" id="averageScore" name="averageScore" value="">
		</div>
		<div class="group4">
			<div class="administrator">管理者</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	MSG.E0001 = '<fmt:message key="E0001"></fmt:message>';
	MSG.E0002 = '<fmt:message key="E0002"></fmt:message>';
	MSG.E0003 = '<fmt:message key="E0003"></fmt:message>';
	MSG.E0004 = '<fmt:message key="E0004"></fmt:message>';
	MSG.E0005 = '<fmt:message key="E0005"></fmt:message>';
	MSG.E0006 = '<fmt:message key="E0006"></fmt:message>';
	MSG.E0007 = '<fmt:message key="E0007"></fmt:message>';
	MSG.E0008 = '<fmt:message key="E0008"></fmt:message>';
	MSG.E0009 = '<fmt:message key="E0009"></fmt:message>';
	MSG.E0010 = '<fmt:message key="E0010"></fmt:message>';
	MSG.E0011 = '<fmt:message key="E0011"></fmt:message>';
	MSG.E0012 = '<fmt:message key="E0012"></fmt:message>';
	MSG.E0013 = '<fmt:message key="E0013"></fmt:message>';
	MSG.E0014 = '<fmt:message key="E0014"></fmt:message>';
	MSG.E0015 = '<fmt:message key="E0015"></fmt:message>';
	MSG.E0016 = '<fmt:message key="E0016"></fmt:message>';
	MSG.E0017 = '<fmt:message key="E0017"></fmt:message>';
	MSG.E0018 = '<fmt:message key="E0018"></fmt:message>';
	MSG.E0019 = '<fmt:message key="E0019"></fmt:message>';
	MSG.E0020 = '<fmt:message key="E0020"></fmt:message>';
	MSG.E0021 = '<fmt:message key="E0021"></fmt:message>';
	MSG.E0022 = '<fmt:message key="E0022"></fmt:message>';
	MSG.E0023 = '<fmt:message key="E0023"></fmt:message>';
	MSG.E0024 = '<fmt:message key="E0024"></fmt:message>';
	MSG.E0025 = '<fmt:message key="E0025"></fmt:message>';
	MSG.E0026 = '<fmt:message key="E0026"></fmt:message>';
	MSG.E0027 = '<fmt:message key="E0027"></fmt:message>';
	MSG.E0028 = '<fmt:message key="E0028"></fmt:message>';
	MSG.E0040 = '<fmt:message key="E0040"></fmt:message>';
	MSG.E0041 = '<fmt:message key="E0041"></fmt:message>';
	MSG.E0042 = '<fmt:message key="E0042"></fmt:message>';
	MSG.E0043 = '<fmt:message key="E0043"></fmt:message>';
	MSG.E0044 = '<fmt:message key="E0044"></fmt:message>';
	MSG.E0045 = '<fmt:message key="E0045"></fmt:message>';
	MSG.E0046 = '<fmt:message key="E0046"></fmt:message>';
	MSG.E0047 = '<fmt:message key="E0047"></fmt:message>';
	MSG.E0048 = '<fmt:message key="E0048"></fmt:message>';
	MSG.E0049 = '<fmt:message key="E0049"></fmt:message>';
</script>
