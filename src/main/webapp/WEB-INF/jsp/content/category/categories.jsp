<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLibs.jsp" %>

<h2>Categories</h2>
<div class="dataTable">
	<table>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th></th>
			<th></th>
		</tr>
		<c:forEach items="${categories}" var="category">
			<tr>
				<td><c:out value="${category.id}"/></td>
				<td><c:out value="${category.name}"/></td>
				<td class="highlightcolorBlack">
					<a href="<c:url value="/categories/edit/${category.id}" />">Edit</a></td>
				<td class="highlightcolorBlack">
					<a href="<c:url value="/categories/remove/${category.id}" />">Remove</a></td>
			</tr>
		</c:forEach>
	</table>
</div>

<div class="highlightcolorBlack">
	<a href="<c:url value="/categories/new" />">Create New Category</a>
</div>