<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="tilesx" uri="http://tiles.apache.org/tags-tiles-extras"%>
<div class="span10">
	<div class="row-fluid">
		<!-- Button group -->
		<div class="span8">
			<div class="btn-group">
				<a href="<c:url value="/books"/>" class="btn btn-primary"><spring:message
						code="book.all" /></a> <a href="<c:url value="/books?show=issuetd"/>"
					class="btn btn-primary"><spring:message code="book.issuetd" /></a>
				<a href="<c:url value="/books?show=issueph"/>"
					class="btn btn-primary"><spring:message code="book.issueph" /></a>
				<a href="<c:url value="/books?show=return"/>"
					class="btn btn-primary"><spring:message code="book.return" /></a> <a
					href="<c:url value="/books?show=returntd"/>"
					class="btn btn-primary"><spring:message code="book.returntd" /></a>
			</div>
		</div>

		<div class="span4">
			<form class="formi" method="POST"
				action="${pageContext.request.contextPath}/books">
				<div class="input-prepend input-append row">
					<select name="field" class="add-on my">
						<option value="all"><spring:message code="book.searchall" /></option>
						<option value="title"><spring:message code="book.title" /></option>
						<option value="author"><spring:message
								code="book.authors" /></option>
						<option value="publication"><spring:message
								code="book.publication" /></option>
						<option value="year"><spring:message code="book.year" /></option>
						<option value="genre.name"><spring:message
								code="book.genre" /></option>
					</select> 
					<input name="search" type="text" class="span6">
					<button type="submit" class="btn btn-primary add-on my">
						<spring:message code="book.search" />
					</button>
				</div>
			</form>
		</div>
	</div>

	<!-- Alert -->
	<div class="alert alert-error" style="display: none">
		<button type="button" class="close">&times;</button>
		<h4>
			<spring:message code="message.error" />
		</h4>
		<spring:message code="book.errordel" />
	</div>

	<!-- Books table -->
	<div class="TableBooks">
		<table>
			<thead>
				<tr>
					<th hidden="true"></th>
					<th><spring:message code="book.title" /></th>
					<th><spring:message code="book.authors" /></th>
					<th><spring:message code="book.publication" /></th>
					<th><spring:message code="book.year" /></th>
					<th><spring:message code="book.pages" /></th>
					<th><spring:message code="book.bookcase" /></th>
					<th><spring:message code="book.shelf" /></th>
					<th><spring:message code="book.genre" /></th>
					<th><spring:message code="book.term" /></th>
					<th><spring:message code="book.count" /></th>
					<th></th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${books}" var="book">
					<tr id="books${book.id}" class="table${book.id}">
						<td hidden="true" class="id${book.id}">${book.id}</td>
						<td class="title${book.id}"><c:out value="${book.title}"
								escapeXml="true" /></td>
						<td class="authors${book.id}"><c:out value="${book.authors}"
								escapeXml="true" /></td>
						<td class="publication${book.id}"><c:out
								value="${book.publication}" escapeXml="true" /></td>
						<td class="year${book.id}">${book.year}</td>
						<td class="pages${book.id}">${book.pages}</td>
						<td class="bookcase${book.id}">${book.bookcase}</td>
						<td class="shelf${book.id}">${book.shelf}</td>
						<td><c:out value="${book.genre}" escapeXml="true" />
							<p class="genre${book.id}" hidden="true">${book.genre.id}</p></td>
						<td class="term${book.id}">${book.term}</td>
						<td class="desc${book.id}" hidden="true">${book.description}</td>
						<td class="count${book.id}">${book.available}/${book.count}</td>

						<td>
						<div class="btn-group">
							<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
								Action
    							<span class="caret"></span>
    						</button>
    						<ul class="dropdown-menu">
    							<li>
    								<a href="<c:url value="/bookusers/book/${book.id}"/>">
    									<spring:message code="book.users" />
    								</a>
    							</li>
    							<li>
    								<a href="<c:url value="/orders/book/${book.id}"/>">
    									<spring:message code="book.orders" />
    								</a>
    							</li>
    						</ul>
    					</div>						
						</td>
							
						<td><a href="#" id="editbook${book.id}"
							class="btn btn-warning"><spring:message code="button.edit" /></a></td>
						<td><a href="#" id="deletebook${book.id}"
							class="btn btn-danger"><spring:message code="button.delete" /></a></td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<!-- New book button -->
	<a href="#" id="newbookbutton" class="btn"><spring:message
			code="book.newbook" /></a>

	<!-- Delete popup -->
	<div id="popup" class="modal hide fade" role="dialog"
		aria-labelledby="deleteLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3 id="deleteLabel">
				<spring:message code="book.deletebook" />
			</h3>
		</div>
		<div class="modal-body">
			<span><spring:message code="message.delete" /></span> <span id="name"></span>
		</div>
		<div class="modal-footer">
			<a id="deleteLink" data-dismiss="modal"
				href="${pageContext.request.contextPath}/book/delete"
				class="btn btn-danger"><spring:message code="button.delete" /></a> <a
				id="canceldelete" href="#" class="btn" data-dismiss="modal"
				aria-hidden="true" value="Cancel"><spring:message
					code="button.cancel" /></a>
		</div>
	</div>

	<!-- Edit popup -->
	<div id="action_popup" class="modal hide fade" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3 class="myModalLabelEdit hide">
				<spring:message code="button.edit" />
			</h3>
			<h3 class="myModalLabelNew hide">
				<spring:message code="book.newbook" />
			</h3>
		</div>
		<div class="modal-body">
			<form:form id="editbook" class="form-horizontal" method="POST"
				commandName="book" enctype="multipart/form-data"
				action="${pageContext.request.contextPath}/book/update">
				<form:hidden path="id" id="id" disabled="disabled" />

				<div class="control-group">
					<label class="control-label" for="title"><spring:message
							code="book.title" /></label>
					<div class="controls">
						<form:input path="title" type="text" id="title"
							placeholder="Title" />
						<form:label id="errortitle" path="title" cssClass="error" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="authors"><spring:message
							code="book.authors" /></label>
					<div class="controls">
						<form:input path="authors" type="text" id="authors"
							placeholder="Authors" />
						<form:label id="errorauthors" path="authors" cssClass="error" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="year"><spring:message
							code="book.year" /></label>
					<div class="controls">
						<form:input path="year" type="text" id="year" placeholder="Year" />
						<form:label id="erroryear" path="year" cssClass="error" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="publication"><spring:message
							code="book.publication" /></label>
					<div class="controls">
						<form:input path="publication" type="text" id="publication"
							placeholder="Publication" />
						<form:label id="errorpublication" path="publication" cssClass="error" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="pages"><spring:message
							code="book.pages" /></label>
					<div class="controls">
						<form:input path="pages" type="text" id="pages"
							placeholder="Pages" />
						<form:label id="errorpages" path="pages" cssClass="error" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="pages"><spring:message
							code="book.description" /></label>
					<div class="controls">
						<form:input path="description" type="text" id="description"
							placeholder="Description" />
						<form:label id="errordescription" path="description" cssClass="error" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="term"><spring:message
							code="book.term" /></label>
					<div class="controls">
						<form:input path="term" type="text" id="term" placeholder="Term" />
						<form:label id="errorterm" path="term" cssClass="error" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="bookcase"><spring:message
							code="book.bookcase" /></label>
					<div class="controls">
						<form:input path="bookcase" type="text" id="bookcase"
							placeholder="Bookcase" />
						<form:label id="errorbookcase" path="bookcase" cssClass="error" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="shelf"><spring:message
							code="book.shelf" /></label>
					<div class="controls">
						<form:input path="shelf" type="text" id="shelf"
							placeholder="Shelf" />
						<form:label id="errorshelf" path="shelf" cssClass="error" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="genre"><spring:message
							code="book.genre" /></label>
					<div class="controls">
						<form:select path="genre" id="genre" items="${genre}"
							itemValue="id" itemLabel="name" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="count"><spring:message
							code="book.count" /></label>
					<div class="controls">
						<form:input path="count" type="text" id="count"
							placeholder="count" />
						<form:label id="errorcount" path="count" cssClass="error" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="available"><spring:message
							code="book.available" /></label>
					<div class="controls">
						<form:input path="available" type="text" id="available"
							placeholder="available" />
						<form:label id="erroravailable" path="available" cssClass="error" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="image"><spring:message
							code="book.available" /></label>
					<div class="controls">
						<form:input path="image" type="file" id="image"
							placeholder="image" />
					</div>
				</div>

				<div class="form-actions">
					<button type="submit" class="btn btn-primary">
						<spring:message code="button.save" />
					</button>
					<button id="cancel" type="button" class="btn" data-dismiss="modal"
						aria-hidden="true">
						<spring:message code="button.cancel" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>