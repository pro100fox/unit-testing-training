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
	<a href="/UniversitySchedule/subject/list">Вернуться к списку</a> <br />
	<h1>Предмет ${subject.title}</h1>
	<form method="post">
		<input type="submit" value="Сохранить и перейти к списку" /> <br /> <br />
		Id: <input type="text" name="id" value="${subject.id}" readonly />
		<br />
		Title: <input type="text" name="title" value="${subject.title}" />
	</form>
</body>
</html>