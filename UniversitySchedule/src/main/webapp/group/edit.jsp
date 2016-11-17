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
	<a href="/UniversitySchedule/group/list">Вернуться к списку</a> <br />
	<a href="/UniversitySchedule/schedule/listByPerson/${student.id}">Расписание</a>
	<h1>Группа ${group.name}</h1>
	<form method="post">
		<input type="hidden" name = "action" value="editGroup">
		<input type="submit" value="Сохранить и перейти к списку" /> <br /> <br />
		Id: <input type="text" name="id" value="${group.id}" readonly />
		<br />
		Name: <input type="text" name="name" value="${group.name}" />
	</form>
	
	<h3>Студенты</h3>
	<table class="item-table">
		<tr>
			<th>Ид</th>
			<th>Имя</th>
		</tr>
		
		<c:forEach items="${students}" var="student">
			<tr>
				<td>${student.id}</td>
				<td>${student.name}</td>
			</tr>
		</c:forEach>
	
	</table>
	<br />
	<br />
	
	<form method="post">
		<input type="hidden" name = "action" value="addStudent">
		Студент:
		<select name="student">
			<c:forEach items="${persons}" var="person">				
		    	<option value="${person.id}">${person.name}</option>
			</c:forEach>
		</select>
		<input type="submit" value="Добавить студента" />
	</form>
</body>
</html>