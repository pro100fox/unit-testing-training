<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Simple CRUD</title>
</head>
<body>
	<a href="/UniversitySchedule/schedule/list">Вернуться к списку</a> <br />
	<h1>Занятие</h1>
	<form method="post">
		<input type="submit" value="Сохранить и перейти к списку" /> <br /> <br />
		Id: <input type="text" name="id" value="${scheduleItem.id}" readonly />
		<br />
		DateTime: <input type="text" name="dateTime" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${scheduleItem.dateTime}" />" />
		<br />
		Lecturer:
		<select name="lecturer">
			<c:forEach items="${persons}" var="lecturer">				
		    	<option value="${lecturer.id}" <c:if test="${lecturer.id.equals(scheduleItem.lecturer.id)}">selected</c:if>>${lecturer.name}</option>
			</c:forEach>
		</select>
		<br />
		Group:
		<select name="group">
			<c:forEach items="${groups}" var="group">				
		    	<option value="${group.id}" <c:if test="${group.id.equals(scheduleItem.group.id)}">selected</c:if>>${group.name}</option>
			</c:forEach>
		</select>
		<br />
		Audience:
		<select name="audience">
			<c:forEach items="${audiences}" var="audience">				
		    	<option value="${audience.id}" <c:if test="${audience.id.equals(scheduleItem.audience.id)}">selected</c:if>>${audience.name}</option>
			</c:forEach>
		</select>
		<br />
		Subject:
		<select name="subject">
			<c:forEach items="${subjects}" var="subject">				
		    	<option value="${subject.id}" <c:if test="${subject.id.equals(scheduleItem.subject.id)}">selected</c:if>>${subject.title}</option>
			</c:forEach>
		</select>
	</form>
</body>
</html>