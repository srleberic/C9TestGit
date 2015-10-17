<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLibs.jsp" %>

<tiles:importAttribute name="title"/>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="${title}"/></title>
		<link href="<c:url value="/css/screen.css"/>" rel="stylesheet"
			type="text/css" />
		<link href="http://www.levi9.com/wp-content/themes/levi9/favicon.ico"
			rel="shortcut icon" type="image/x-icon" />
		<script src="<c:url value="/js/lib/jquery/jquery.min.js"/>"></script>
		<script src="<c:url value="/js/main.js"/>"></script>
	</head>
	
	<body>
		<header>
			<div class="top darkNoise">
				<a href="http://www.levi9.com" target="_blank">Levi9 IT Services</a>
				<a href="<c:url value="/home?lang=sr" />"><fmt:message key="common.header.language.sr"/></a>
				<a href="<c:url value="/home?lang=en" />"><fmt:message key="common.header.language.en"/></a>
			</div>
			<h1>Taster</h1>
			<ul id="mainMenu">
				<li><a class="dashBoard" 
					href="<c:url value="/home"/>"><fmt:message key="common.menu.dashboard"/></a></li>
				<li><a class="categories" 
					href="<c:url value="/categories"/>"><fmt:message key="common.menu.categories"/></a></a></li>
				<li><a class="questions" 
					href="<c:url value="/questions"/>"><fmt:message key="common.menu.questions"/></a></a></li>
			</ul>
		</header>
		<section id="mainContent">
			<tiles:insertAttribute name="content"/>
		</section>
		<footer>
			<div class="darkNoise"><fmt:message key="common.footer.copyright"/></div>
		</footer>
	</body>
</html>