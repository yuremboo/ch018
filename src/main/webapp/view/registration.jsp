<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/view/includes.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/resources/css/style.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/scripts.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.tablesorter.js"></script>
<title>Sign in page</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span3">
			</div>
			<div class="span5">

				<div class="well" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-header">
    					<h3 id="myModalLabel">Registration</h3>
  					</div>
  					<div class="modal-body">
					<form:form id="register" class="form-horizontal" method="POST" commandName="registration"
						action="${pageContext.request.contextPath}/registration">
						
						<div class="control-group">
    						<label class="control-label" for="inputEmail">Email</label>
    						<div class="controls">
      							<form:input path="email" type="text" id="inputEmail" required="true"/>
    						</div>
  						</div>
  						
  						<div class="control-group">
    						<label class="control-label" for="inputPassword" >Password</label>
    						<div class="controls">
      							<form:input path="password" type="password" id="inputPassword" placeholder="Pass" required="true" />
    						</div>
  						</div>
  						
  						<div class="control-group">
    						<label class="control-label" for="confirmPassword">Confirm Password</label>
    						<div class="controls">
      							<form:input path="confirmpassword" type="password" id="confirmPassword" placeholder="Pass" required="true" />
    						</div>
  						</div>

  						<div class="form-actions">
							<input type="submit" value="Register" class="btn btn-primary"  />
							<input id="cancel" type="button" class="btn" value="Cancel" />
						</div>
					</form:form>
					</div>
				</div>
				
			</div>
			<div class="span4">
			</div>
		</div>
	</div>
</body>
</html>