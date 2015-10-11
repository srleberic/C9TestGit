<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="com.levi9.code9.model.Category"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>Categories</title>
</head>
<body>
	<div id="categories">
		<table>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th></th>
			</tr>
			<c:forEach items="${categories}" var="category">
				<tr>
					<td><c:out value="${category.id}"/></td>
					<td><c:out value="${category.name}"/></td>
					<td><a href="<c:url value="/categories/edit/${category.id}" />">Edit</a></td>
					<td><a href="<c:url value="/categories/remove/${category.id}" />">Remove</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<div id="newCategory">
		<a href="<c:url value="/categories/new" />">Create New Category</a>
	</div>
</body>
</html>