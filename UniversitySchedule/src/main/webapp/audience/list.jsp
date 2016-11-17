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
	<h1>Аудитории</h1>
	<table class="item-table">
		<tr>
			<th>Ид</th>
			<th>Аудитория</th>
			<th></th>
			<th></th>
		</tr>
		
		<c:forEach items="${audiences}" var="audience">
			<tr>
				<td>${audience.id}</td>
				<td>${audience.name}</td>
				<td><a href="/UniversitySchedule/audience/edit/${audience.id}">Редактировать</a></td>
				<td><a href="/UniversitySchedule/audience/delete/${audience.id}">Удалить</a></td>
			</tr>
		</c:forEach>
	
	</table>
	<a href="/UniversitySchedule/audience/new">Добавить аудиторию</a> <br />
	<a href="/UniversitySchedule/audience/deleteAll">Удалить все</a>
</body>
</html>