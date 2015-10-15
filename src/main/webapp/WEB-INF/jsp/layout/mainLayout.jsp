<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLibs.jsp" %>

<tiles:importAttribute name="title"/>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>${title}</title>
	</head>
	
	<body>
		<header>
			<div>
				<a href="http://www.levi9.com" target="_blank">Levi9 IT Services</a>
			</div>
			<h1>Taster</h1>
			<ul>
				<li><a href="<c:url value="/home"/>">Dashboard</a></li>
				<li><a href="<c:url value="/categories"/>">Categories</a></li>
				<li><a href="<c:url value="/questions"/>">Questions</a></li>
			</ul>
		</header>
		<section id="mainContent">
			<tiles:insertAttribute name="content"/>
		</section>
		<footer>
			<div>&copy; 2015. Levi9 IT Services</div>
		</footer>
	</body>
</html>