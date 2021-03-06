<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	<%@ page session="true"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="tilesx" uri="http://tiles.apache.org/tags-tiles-extras"%>
<c:set var="asc" value="" />
<c:if test="${isAsc == null || isAsc == false}">
	<c:set var="asc" value="&isasc=true" />
</c:if>
<div style="overflow-y: scroll">
		<table id="utable" class="table table-striped table-condensed table-hover">
			<thead>
				<tr>
					<th hidden="true"></th>
					<th><nobr><spring:message code="person.firstname" /><a href="<c:url value="?sort=name${asc}"/>"> <i class="icon-chevron-down"/> </a></nobr></th>
					<th><nobr><spring:message code="person.lastname" /><a href="<c:url value="?sort=surname${asc}"/>"> <i class="icon-chevron-down"/> </a></nobr></th>
					<th><nobr><spring:message code="person.mail" /><a href="<c:url value="?sort=email${asc}"/>"> <i class="icon-chevron-down"/> </a></nobr></th>
					<th><nobr><spring:message code="person.mobile" /><a href="<c:url value="?sort=cellphone${asc}"/>"> <i class="icon-chevron-down"/> </a></nobr></th>
					<th><nobr><spring:message code="person.multibookallowed" /><a href="<c:url value="?sort=multibookAllowed${asc}"/>"> <i class="icon-chevron-down"/> </a></nobr></th>
					<th><nobr><spring:message code="person.untimelly" /><a href="<c:url value="?sort=untimelyReturns${asc}"/>"> <i class="icon-chevron-down"/> </a></nobr></th>
					<th><nobr><spring:message code="person.timelly" /><a href="<c:url value="?sort=timelyReturns${asc}"/>"> <i class="icon-chevron-down"/> </a></nobr></th>
					<th><nobr><spring:message code="person.failed" /><a href="<c:url value="?sort=failedOrders${asc}"/>"> <i class="icon-chevron-down"/> </a></nobr></th>
					<th><nobr><spring:message code="person.rating" /><a href="<c:url value="?sort=rating${asc}"/>"> <i class="icon-chevron-down"/> </a></nobr></th>
					<th><nobr><spring:message code="person.confirmed" /><a href="<c:url value="?sort=confirm${asc}"/>"> <i class="icon-chevron-down"/> </a></nobr></th>
					<th><nobr><spring:message code="person.role" /><a href="<c:url value="?sort=role${asc}"/>"> <i class="icon-chevron-down"/> </a></nobr></th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${persons}" var="person">
					<tr id="person${person.id}" class="table${person.id}">
						<td hidden="true" class="id${person.id}">${person.id}</td>
						<td class="uname${person.id}"><c:out value="${person.name}"
								escapeXml="true" /></td>
						<td class="surname${person.id}"><c:out
								value="${person.surname}" escapeXml="true" /></td>
						<td class="email${person.id}"><c:out value="${person.email}"
								escapeXml="true" /></td>
						<td class="cellphone${person.id}"><c:out
								value="${person.cellphone}" escapeXml="true" /></td>
						<td class="multibookAllowed${person.id}">${person.multibookAllowed}</td>
						<td class="untimelyReturns${person.id}">${person.untimelyReturns}</td>
						<td class="timelyReturns${person.id}">${person.timelyReturns}</td>
						<td class="failedOrders${person.id}">${person.failedOrders}</td>
						<td class="rating${person.id}">${person.rating}</td>
						<td><input class="confirm${person.id}" type="checkbox"
							name="confirm" value="confirm"
							${person.confirm == true ? 'checked' : ''} disabled></td>
						<td class="role${person.id}">${person.role}</td>	

						<td><a href="#" id="edituser${person.id}"
							class="btn btn-warning"><spring:message code="button.edit" /></a></td>
						<td>
								<a href="#" id="deleteuser${person.id}"
								class="btn btn-danger"><spring:message code="button.delete" /></a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
		<!-- New user button -->
	<a href="#" id="newuserbutton" class="btn"><spring:message
			code="person.new" /></a>
			
		<!-- Pagination -->
	<div class="pagination pagination-centered">
		<ul>
			<c:if test="${page == 1}">
				<li class = "disabled">
					<a >«</a>
				</li>
			</c:if>
			<c:if test="${page > 1}">
				<li>
					<a href="<c:url value="/admin?page=${page-1}"/>">«</a>
				</li>
			</c:if>
			
			<c:forEach var="i" begin="1" end="${pages}">
   				<li>
					<a href="<c:url value="/admin?page=${i}"/>"><c:out value="${i}"/></a>
				</li>
			</c:forEach>
			<c:if test="${page == pages}">
				<li class = "disabled">
					<a >»</a>
				</li>
			</c:if>
			<c:if test="${page < pages}">
				<li>
					<a href="<c:url value="/admin?page=${page+1}"/>">»</a>
				</li>
			</c:if>
		</ul>
	</div>
	
	<!-- Delete user -->
	<div id="popup" class="modal hide fade" role="dialog"
		aria-labelledby="deleteLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3 id="deleteLabel">
				<spring:message code="person.delete" />
			</h3>
		</div>
		<div class="modal-body">
			<span><spring:message code="message.delete" /></span> <span class="hide" id="name"></span>
			<!-- Alert -->
			<div class="alert alert-error" style="display: none">
				<h4>
					<spring:message code="message.error" />
				</h4>
				<spring:message code="book.errordel" />
				You must <a href="${pageContext.request.contextPath}/user/delete" class="delete_all_orders">DELETE</a> all orders of this user first.<span class="book_id_remove hide"></span>
			</div>
		</div>
		<div class="modal-footer">
			<a id="deleteLink" href="${pageContext.request.contextPath}/user/delete"
				class="btn btn-danger"><spring:message code="button.delete" /></a> <a
				id="canceldelete" href="#" class="btn" data-dismiss="modal"
				aria-hidden="true"><spring:message code="button.cancel" /></a>
		</div>
	</div>

	<!-- Edit user -->
	<div id="action_popup" class="modal hide fade" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
			<h3 class="myModalLabelEdit hide">
				<spring:message code="button.edit" />
				<span class="pull-right" id="rating"><spring:message code="person.rating" />:</span>
			</h3>
			<h3 class="myModalLabelNew hide">
				<spring:message code="person.new" />
			</h3>
			<span id="errorperson" class="error"></span>
		</div>
		<div class="modal-body">
			<form:form id="addedituseradm" class="form-horizontal" method="POST"
				commandName="person"
				action="${pageContext.request.contextPath}/admin/update">
				<form:input path="id" id="id" class="hide" disabled="disabled" />

				<div class="control-group">
					<label class="control-label" for="uname"><spring:message
							code="person.firstname" /></label>
					<div class="controls">
						<form:input path="name" type="text" id="uname" placeholder="Name" />
						<form:label id="erroruname" path="name" cssClass="error" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="surname"><spring:message
							code="person.lastname" /></label>
					<div class="controls">
						<form:input path="surname" type="text" id="surname"
							placeholder="Second Name" />
						<form:label id="erroruname" path="surname" cssClass="error" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="email"><spring:message
							code="person.mail" /></label>
					<div class="controls">
						<form:input path="email" type="email" id="email" />
						<form:label id="erroremail" path="email" cssClass="error" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="cellphone"><spring:message
							code="person.mobile" /></label>
					<div class="controls">
						<form:input path="cellphone" type="text" id="cellphone"
							placeholder="Mobile" />
						<form:label id="errorcellphone" path="cellphone" cssClass="error" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="multibookAllowed"><spring:message
							code="person.multibookallowed" /></label>
					<div class="controls">
						<form:input path="multibookAllowed" type="number"
							id="multibookAllowed"  />
						<form:label id="errormultibookAllowed" path="multibookAllowed" cssClass="error" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="untimelyReturns"><spring:message
							code="person.untimelly" /></label>
					<div class="controls">
						<form:input path="untimelyReturns" id="untimelyReturns"
							placeholder="0" />
						<form:label id="errormuntimelyReturns" path="untimelyReturns" cssClass="error" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="timelyReturns"><spring:message
							code="person.timelly" /></label>
					<div class="controls">
						<form:input path="timelyReturns" id="timelyReturns"
							placeholder="0" />
						<form:label id="errortimelyReturns" path="timelyReturns" cssClass="error" />
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="role">Role</label>
					<div class="controls">
						<form:select path="role" id="role" items="${role}" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="failedOrders"><spring:message
							code="person.failed" /></label>
					<div class="controls">
						<form:input path="failedOrders" id="failedOrders" placeholder="0" />
						<form:label id="errorfailedOrders" path="failedOrders" cssClass="error" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="confirm"><spring:message
							code="person.confirmed" /></label>
					<div class="controls">
						<form:checkbox path="confirm" id="confirm" />
						<form:label id="errorconfirm" path="confirm" cssClass="error" />
					</div>
				</div>
				<div class="form-actions">
					<input type="submit" value="Save" class="btn btn-primary" /> <input
						id="cancel" type="button" class="btn" data-dismiss="modal"
						aria-hidden="true" value="Cancel" />
				</div>
			</form:form>
		</div>
	</div>