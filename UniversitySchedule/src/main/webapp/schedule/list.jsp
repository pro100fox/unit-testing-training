<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Simple CRUD</title>
</head>
<body>
	<a href="/UniversitySchedule">Вернуться к списку объектов</a> <br />
	<h1>Расписание</h1>
	<table class="item-table">
		<tr>
			<th>Ид</th>
			<th>Время</th>
			<th>Лектор</th>
			<th>Группа</th>
			<th>Аудитория</th>
			<th>Предмет</th>
		</tr>
		
		<c:forEach items="${scheduleItems}" var="scheduleItem">
			<tr>
				<td>${scheduleItem.id}</td>
				<td>${scheduleItem.dateTime}</td>
				<td>${scheduleItem.lecturer}</td>
				<td>${scheduleItem.group}</td>
				<td>${scheduleItem.audience}</td>
				<td>${scheduleItem.subject}</td>
				<td><a href="/UniversitySchedule/schedule/edit/${scheduleItem.id}">Редактировать</a></td>
				<td><a href="/UniversitySchedule/schedule/delete/${scheduleItem.id}">Удалить</a></td>
			</tr>
		</c:forEach>
	
	</table>
	<a href="/UniversitySchedule/schedule/new">Добавить занятие</a> <br />
	<a href="/UniversitySchedule/schedule/deleteAll">Удалить все</a>
</body>
</html>