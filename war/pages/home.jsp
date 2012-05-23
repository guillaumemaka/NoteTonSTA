<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.supinfo.notetonsta.dao.jpa.DaoFactory"%>
<%@ page import="com.supinfo.notetonsta.entity.Campus"%>
<%@ page import="java.util.List"%>
<html>
<head>
<title>Note Ton STA | Home</title>

<script type="text/javascript">
	$(function() {
		$("#campus_select")
				.change(
						function() {
							window.location.href = "${ pageContext.request.contextPath }/interventions?campusId="
									+ $(this).val();
						});
	});
</script>

</head>
<body>
	<h1>Welcome to Note Ton STA</h1>

	<p class="clearfix">
		This website propose you to evalutate intervention of SUPINFO
		Speakers.<br /> You can also see statistics by speakers or campus
	</p>
	<p class="clearfix">
	<form class="clearfix">
		<div class="clearfix">
			<label class="span4" for="campus">Please select your campus:
			</label>

			<div class="input">
				<select id="campus_select" class="large" name="campus">
					<%
						List<Campus> campus = DaoFactory.campusDao().findAll();
					%>
					<option>-- CAMPUS --</option>

					<c:forEach items="<%=campus%>" var="camp">
						<option value="${ camp.key.id }">${ camp.name }</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</form>
	</p>

	<p class="clearfix">
		If you are a speaker and have already an account, please <a
			href="${ pageContext.request.contextPath }/login">authenticate
			you</a> to manage your interventions.<br /> If you don't have en
		account, please <a
			href="${ pageContext.request.contextPath }/register">register you
		</a> !
	</p>
</body>
</html>