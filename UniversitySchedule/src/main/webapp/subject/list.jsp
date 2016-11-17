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
	<h1>Предметы</h1>
	<table class="item-table">
		<tr>
			<th>Ид</th>
			<th>Название</th>
		</tr>
		
		<c:forEach items="${subjects}" var="subject">
			<tr>
				<td>${subject.id}</td>
				<td>${subject.title}</td>
				<td><a href="/UniversitySchedule/subject/edit/${subject.id}">Редактировать</a></td>
				<td><a href="/UniversitySchedule/subject/delete/${subject.id}">Удалить</a></td>
			</tr>
		</c:forEach>
	
	</table>
	<a href="/UniversitySchedule/subject/new">Добавить предмет</a> <br />
	<a href="/UniversitySchedule/subject/deleteAll">Удалить все</a>
</body>
</html>