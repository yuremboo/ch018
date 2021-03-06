<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<link href="${pageContext.request.contextPath}/resources/css/jquery.rating.css"
	    rel="stylesheet" type="text/css"/>
<!-- Center -->
<div class="span8">
 <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.rating-2.0.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/rating.js"></script>
 <input type="hidden" id="href" value="${pageContext.request.contextPath}/wishlist" />
 <input type="hidden" id="hrefOrder" value="${pageContext.request.contextPath}/prepareorder" />
     <input type="hidden" id="hrefNewOrder" value="${pageContext.request.contextPath}/order" />
	<div class="row-fluid">
		<div class="thumbnail media">
		
			<img src="<c:url value="${book.image}"/>" class="img-rounded pull-left large">
			<div class="media-width">
				<h4 class="media-heading">${book.title}</h4>
				<p><strong><spring:message code="book.authors" />: </strong>${book.authors}</p>
				<p><strong><spring:message code="book.year" />: </strong>${book.year}</p>
				<p><strong><spring:message code="book.publication" />: </strong>${book.publication}</p>
				<p><strong><spring:message code="book.description" />: </strong>${book.description}</p>
				
					<c:choose>
						<c:when test="${book.available==0}"><span class="label label-important">Not Available</span></c:when>
						<c:otherwise><span class="label label-success">Available</span></c:otherwise>
					</c:choose>
					<div class="rateTop5">
						<input name="val" value="${book.rating}" type="hidden">
					</div>
					<span class="vote"><spring:message code="person.rating" />:
						${book.rating}</span>&nbsp;&nbsp;&nbsp;&nbsp;
		            <span class="vote"><spring:message code="votes" />:
						${book.numberOfEvaluations}</span>
				<div></div>
				<sec:authorize access="hasRole('ROLE_USER')">
				<div class="btn-group btn-group-vertical">
					<a id="addToWish${book.id}" class="btn btn-success btn-mini"><spring:message code="message.cart" /></a>
						<input type="hidden" value="${book.id}"/>
					    <a id="orderNow${book.id}"
							class="btn btn-warning btn-mini">  <spring:message code="message.ordernow" />  
						</a>
					 
				</div>
				 <div id="inOrder${book.id}" class="modal hide fade">
                                           <div class="modal-header">Message</div>
                                           <div class="modal-body">${book.title} already in your orders!</div>
                        </div>
                         <div id="inUse${book.id}" class="modal hide fade">
                                           <div class="modal-header">Message</div>
                                           <div class="modal-body">${book.title} is now in your hands</div>
                        </div>
                         <div id="fail${book.id}" class="modal hide fade">
                                           <div class="modal-header">Message</div>
                                           <div class="modal-body"><spring:message code="message.failorder"/></div>
                        </div> 
                        <div id="successAdd${book.id}" class="modal hide fade">
                                           <div class="modal-header">Message</div>
                                           <div class="modal-body">${book.title} successfully added to wish list</div>
                </div>
                <div id="errorAdd${book.id}" class="modal hide fade">
                                           <div class="modal-header">Message</div>
                                           <div class="modal-body">You already have ${book.title} in your wish list</div>
                </div>
				</sec:authorize>
		
		</div>
		</div>
		<div class="thumbnail">
			<h4>Most rated for this genre</h4>
			<div class="row-fluid">
			<c:forEach items="${mostrated}" var="book">
					<div class="span2" style="text-align: center;"><a href="<c:url value="/book/${book.id}"/>">
						<img src="${pageContext.request.contextPath}${book.image}" class="img-rounded img-top5"></a>
						<div class="caption">
							<div class = "booktitle">${book.title} <br>
								${book.authors}
							</div>
						</div>
					</div>
			</c:forEach>
			</div>
		</div>
		<c:if test="${not empty book.histories}">
		<div class="thumbnail">
			<h4>Comments:</h4>
			<c:forEach items="${book.histories}" var="history">
				<div class="alert alert-info"> <h6>${history.person.email}:</h6> ${history.comment}</div>
			</c:forEach>
		</div>
		</c:if>
	</div>
</div>