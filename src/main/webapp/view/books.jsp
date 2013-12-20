<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false" %>
<%@ include file="/view/includes.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css" />
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/addbook.js"></script>
<title>Books</title>
	<script type="text/javascript">
   function toggle_visibility(id) {
       var e = document.getElementById(id);
       if(e.style.display == 'block')
          e.style.display = 'none';
       else
          e.style.display = 'block';
   }
</script>
</head>
<body>
<%@ include file="/view/top.jsp" %>
<%@ include file="/view/left.jsp" %>
<div id = "content">
	<div id = "linksblock">
		<a href="<c:url value="/books?show=all"/>">All</a>
		<a href="<c:url value="/books?show=issuetd"/>">To issue today</a>
		<a href="<c:url value="/books?show=issueph"/>">To issue per hour</a>
		<a href="<c:url value="/books?show=return"/>">To return</a>
		<a href="<c:url value="/books?show=returntd"/>">All</a>
	</div>
		<div class = "TableBooks">
		<table>
				<tr>
					<td>#<a href="<c:url value="/books?orderby=id"/>">^v</a></td>
					<td>Title<a href="<c:url value="/books?orderby=title"/>">^v</a></td>
					<td>Authors<a href="<c:url value="/books?orderby=authors"/>">^v</a></td>
					<td>Publication<a href="<c:url value="/books?orderby=publ"/>">^v</a></td>
					<td>Year<a href="<c:url value="/books?orderby=year"/>">^v</a></td>
					<td>Pages<a href="<c:url value="/books?orderby=pages"/>">^v</a></td>
					<td>Bookcase<a href="<c:url value="/books?orderby=bc"/>">^v</a></td>
					<td>Shelf<a href="<c:url value="/books?orderby=shelf"/>">^v</a></td>
					<td>Genre<a href="<c:url value="/books?orderby=genre"/>">^v</a></td>
					<td></td>
					<td></td>
				</tr>
			<c:forEach items="${books}" var="book">
				<tr>
					<td>${book.id}</td>
					<td><c:out value="${book.title}" escapeXml="true"/></td>
					<td><c:out value="${book.authors}" escapeXml="true"/></td>
					<td><c:out value="${book.publication}" escapeXml="true"/></td>
					<td>${book.year}</td>
					<td>${book.pages}</td>
					<td>${book.bookcase}</td>
					<td>${book.shelf}</td>
					<td><c:out value="${book.genre}" escapeXml="true"/></td>
					
					<td><a href="<c:url value="/editbook?id=${book.id}"/>">Edit</a></td>
					<td><a href="<c:url value="/deletebook?id=${book.id}"/>">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
		</div>
		<a href="#" id="newbookbutton" onclick="toggle_visibility('newbook')">New Book</a>
		
		<div id = "newbook">
			<form:form method="POST" commandName="book">
		<form:label path="id"/>
		<table>
			<tr>
				<td>Title</td>
				<td><form:input path="title"/></td>
			</tr>
			<tr>
				<td>Authors</td>
				<td><form:input path="authors"/></td>
			</tr>
			<tr>
				<td>Year</td>
				<td><form:input path="year"/></td>
			</tr>
			<tr>
				<td>Publication</td>
				<td><form:input path="publication"/></td>
			</tr>
			<tr>
				<td>Pages</td>
				<td><form:input path="pages"/></td>
			</tr>
			<tr>
				<td>Description</td>
				<td><form:input path="description"/></td>
			</tr>
			<tr>
				<td>Term</td>
				<td><form:input path="term"/></td>
			</tr>
			<tr>
				<td>Bookcase</td>
				<td><form:input path="bookcase"/></td>
			</tr>
			<tr>
				<td>Shelf</td>
				<td><form:input path="shelf"/></td>
			</tr>
			<tr>
				<td>Genre</td>
				<td>
					<form:select path="genre" id="genre" items="${genre}" itemValue = "id" itemLabel="name" />
				</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save"/></td>
				<td colspan="2"><input type="button" value="Cancel"/></td>
			</tr>
		</table>
	</form:form>
		</div>
		
		<!--  <a href="<c:url value="/addbook"/>">New Book</a> -->
</div>
</body>
</html>