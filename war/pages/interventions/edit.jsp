<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Edit Intervention</title>
<script type="text/javascript"
	src="${ pageContext.request.contextPath}/assets/js/intervention_form_validation.js"></script>
</head>
<body>
	<h1>Edit Intervention</h1>
	<form id="new_intervention" class="clearfix"
		action="${ pageContext.request.contextPath }/interventions/edit"
		method="post">
		<input type="hidden" name="intervention_id"
			value="${ requestScope.intervention.key.id }" />
		<div class="clearfix">
			<label for="subject">Subject:</label>
			<div class="input">
				<input type="text" name="subject"
					value="${ requestScope.intervention.subject }" />
			</div>
		</div>
		<div class="clearfix">
			<label for="campus_id">Campus:</label>
			<div class="input">
				<select name="campus_id">
					<option value="">-- SELECT A CAMPUS --</option>
					<c:forEach items="${ requestScope.campus }" var="c">
						<c:choose>
							<c:when test="${ requestScope.intervention.campus.id == c.key.id }">
								<option value="${ c.key.id }" selected>${ c.name }</option>
							</c:when>
							<c:otherwise>
								<option value="${ c.key.id }">${ c.name }</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="clearfix">
			<label for="from">From:</label>
			<div class="input">
				<input id="from" type="text" name="from"
					value="${ requestScope.intervention.startDate }" />
			</div>
		</div>
		<div class="clearfix">
			<label for="to">To:</label>
			<div class="input">
				<input id="to" type="text" name="to"
					value="${ requestScope.intervention.endDate }" />
			</div>
		</div>
		<div class="clearfix">
			<label for="description">Description:</label>
			<div class="input">
				<textarea id="description" class="xxlarge" name="description"
					rows="7">
					${ requestScope.intervention.description }
				</textarea>
				<span class="help-block"> Write a description about the
					intervention </span>
			</div>
		</div>

		<div class="actions">
			<input type="submit" class="btn primary" value="Save changes">&nbsp;<a
				href="${ pageContext.request.contextPath }/interventions/mine"
				class="btn">Cancel</a>
		</div>
	</form>
</body>
</html>