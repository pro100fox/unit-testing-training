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
	<h1>Студенты</h1>
	<table class="item-table">
		<tr>
			<th>Ид</th>
			<th>Имя</th>
		</tr>
		
		<c:forEach items="${students}" var="student">
			<tr>
				<td>${student.id}</td>
				<td>${student.name}</td>
				<td><a href="/UniversitySchedule/student/edit/${student.id}">Редактировать</a></td>
				<td><a href="/UniversitySchedule/student/delete/${student.id}">Удалить</a></td>
				<td><a href="/UniversitySchedule/schedule/listByPerson/${student.id}">Расписание</a></td>
			</tr>
		</c:forEach>
	
	</table>
	<a href="/UniversitySchedule/student/new">Добавить студента</a> <br />
	<a href="/UniversitySchedule/student/deleteAll">Удалить все</a>
</body>
</html>