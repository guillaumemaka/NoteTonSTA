<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<title>Note Ton STA | Campus: ${ requestScope.campus.name }</title>
<script type="text/javascript"
	src="${ pageContext.request.contextPath }/assets/js/delete.js"></script>
</head>
<body>

	<div id="dialog-confirm" title="Delete intervention?">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>These intervention
			will be permanently deleted and cannot be recovered. Are you sure?
		</p>
	</div>

	<h1 class="small">
		Interventions on <em> ${ requestScope.campus.name }</em> campus
	</h1>
	<table class="zebra-striped">
		<thead>
			<tr>
				<th>Subject</th>
				<th>Begin</th>
				<th>End</th>
				<th>Status</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${ fn:length( requestScope.interventions ) == 0 }">
				<tr>
					<td colspan="4"><em><strong>No intervention
								found</strong></em></td>
				</tr>
			</c:if>
			<c:forEach items="${requestScope.interventions }" var="intervention">
				<tr>
					<td><a
						href="${pageContext.request.contextPath }/interventions/show?id=${intervention.key.id }">
							${intervention.subject } </a></td>
					<td>${intervention.startDate }</td>
					<td>${intervention.endDate }</td>
					<td>${intervention.status }</td>
					<c:if
						test="${ sessionScope.loggedIn == true and intervention.speaker.id == sessionScope.user_id }">
						<td><a class="delete btn"
							href="${pageContext.request.contextPath }/interventions/delete?id=${intervention.key.id}">Delete</a>
							<a delete class="btn"
							href="${pageContext.request.contextPath }/interventions/edit?id=${intervention.key.id}">Edit</a>
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<c:if test="${ sessionScope.loggedIn == true }">
		<div class="clearfix">
			<a class="btn"
				href="${ pageContext.request.contextPath }/interventions/create">Add
				intervention</a>
		</div>
	</c:if>
</body>
</html>