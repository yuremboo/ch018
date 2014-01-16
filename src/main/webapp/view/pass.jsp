<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<!-- Center -->
<div class="span8">
	<br>
	<br>
	<br>
	<br>
	<form:form method="POST" commandName="password">
		<table>
			<tr>
				<td><spring:message code="pass.old" /></td>
				<td><form:password path="password" /></td>
			</tr>
			<tr>
				<td><spring:message code="pass.new" /></td>
				<td><form:password path="newPassword" /></td>
			</tr>
			<tr>
				<td><spring:message code="pass.confirm" /></td>
				<td><form:password path="confirmPassword" /></td>
			</tr>
			<tr>
				<td><input type="submit"
					value="<spring:message code="button.changepass"/>" class="btn" /></td>
			</tr>
		</table>
	</form:form>
</div>
