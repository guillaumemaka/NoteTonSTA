<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<title>Note Ton STA | ${ requestScope.intervention.subject }</title>

<script type="text/javascript"
	src="${ pageContext.request.contextPath }/assets/js/delete.js"></script>
<script type="text/javascript"
	src="${ pageContext.request.contextPath }/assets/js/evaluate.js"></script>
<script type="text/javascript">
	$(function() {
		$("a[rel=twipsy]").twipsy({
			live : true
		})
	})
</script>
</head>
<body>
	<div id="dialog-confirm" title="Delete intervention?">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>These intervention
			will be permanently deleted and cannot be recovered. Are you sure?
		</p>
	</div>

	<div class="row">
		<div class="span4">
			<a href="${requestScope.back_url }">Back</a>
		</div>
		<div class="right span8 offset4">
			<c:if
				test="${ sessionScope.loggedIn == true and requestScope.intervention.speaker.id == sessionScope.user_id }">
				<a id="edit" class="btn"
					href="${pageContext.request.contextPath }/interventions/edit?id=${requestScope.intervention.key.id}">Edit</a>
				<a id="delete" class="btn"
					href="${pageContext.request.contextPath }/interventions/delete?id=${requestScope.intervention.key.id}">Delete</a>
			</c:if>
		</div>
	</div>
	<div class="row">
		<div class="center span16">
			<strong>${ requestScope.intervention.subject }</strong>
		</div>
	</div>
	<div class="row">
		<div class="center span-one-third">
			<strong>Campus: </strong>${ requestScope.campus.name }
		</div>
		<div class="center span-one-third">
			<strong>From <a href="#" data-placement="below" rel='twipsy'
				title='Date format: (MM/DD/YYYY)'>(?)</a>:
			</strong>${ requestScope.intervention.startDate }
		</div>
		<div class="center span-one-third">
			<strong>To <a href="#" data-placement="below" rel='twipsy'
				title='Date format: (MM/DD/YYYY)'>(?)</a>:
			</strong>${ requestScope.intervention.endDate }
		</div>
	</div>
	<div class="row">
		<div class="span16">${ requestScope.intervention.description }</div>
	</div>
	<div class="row">
		<div class="span16">Presented by ${
			requestScope.speaker.firstname } ${ requestScope.speaker.lastname }</div>
	</div>
	<div class="row">
		<div class="span-16">
			<ul>
				<li><strong>Number of mark: </strong>${fn:length(requestScope.intervention.marks)
					}</li>
				<li><strong>Speaker mark: </strong>${requestScope.marks_avg.avgSpeaker}
					/ 5</li>
				<li><strong>Slides mark: </strong>${
					requestScope.marks_avg.avgSlide } / 5</li>
				<li><strong>Global event mark: </strong>${requestScope.marks_avg.globalAvg}
					/ 5</li>
			</ul>
		</div>
	</div>
	<c:choose>
		<c:when test="${fn:length(requestScope.intervention.marks) gt 0}">
			<div class="row">
				<div class="center clearfix span16">
					<div id="chart_container">Loading chart...</div>
					<script type="text/javascript">
						$(function() {
							$
									.getJSON(
											"${ pageContext.request.contextPath }/rest/mark/chartsdata?id=${ requestScope.intervention.key.id}",
											function(data) {
												var myChart = new JSChart(
														'chart_container',
														'pie', '');

												myChart.setDataArray([ data ]);
												myChart.colorize([ '#F8C600',
														'#F8A300', '#F88000',
														'#F85D00', '#F84600',
														'#F7F200' ]);
												myChart.setSize(600, 350);
												myChart.setPieRadius(130);
												myChart.setTitleColor('#000');
												myChart
														.setPieUnitsColor('#000');
												myChart.setPieUnitsFontSize(11);
												myChart
														.setPieValuesFontSize(11);
												myChart
														.setPieValuesColor('#000');
												myChart.set3D(true);
												myChart.setPieAngle(60);
												myChart.setPieDepth(30);
												myChart.draw();
											});
						});
					</script>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="row">
				<div class="center clearfix span16">
					<em><strong>Not enough mark to display the chart</strong></em>
				</div>
			</div>
		</c:otherwise>
	</c:choose>


	<!-- 
	
	
	
	Evaluation Form
	
	
	
	 -->

	<a id="evaluate" href="#">Evaluate</a>
	<div id="modal" class="modal hide fade span6">
		<div class="modal-header">
			<a href="#" class="close">×</a>
		</div>
		<div class="modal-body">
			<form id="evaluate_form" class="clearfix"
				action="${pageContext.request.contextPath }/interventions/evaluate"
				method="post">
				<input type="hidden" name="action" value="evaluate" /> <input
					type="hidden" name="intervention_id"
					value="${requestScope.intervention.key.id }" />
				<div class="clearfix center">
					<label for="idbooster">ID Bosster:</label>
					<div class="input">
						<input type="text" name="idbooster" />
					</div>
				</div>
				<table class="bordered-table">
					<caption>
						<h2>About the Speaker<
					</caption>
					<thead>
						<tr>
							<th></th>
							<th>1</th>
							<th>2</th>
							<th>3</th>
							<th>4</th>
							<th>5</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>His knowledge of the subject:</td>
							<td><input type="radio" name="q1" value="1" /></td>
							<td><input type="radio" name="q1" value="2" /></td>
							<td><input type="radio" name="q1" value="3" /></td>
							<td><input type="radio" name="q1" value="4" /></td>
							<td><input type="radio" name="q1" value="5" /></td>
						</tr>
						<tr>
							<td>His knowledge teaching abilities:</td>
							<td><input type="radio" name="q2" value="1" /></td>
							<td><input type="radio" name="q2" value="2" /></td>
							<td><input type="radio" name="q2" value="3" /></td>
							<td><input type="radio" name="q2" value="4" /></td>
							<td><input type="radio" name="q2" value="5" /></td>
						</tr>
						<tr>
							<td>The quality of answers:</td>
							<td><input type="radio" name="q3" value="1" /></td>
							<td><input type="radio" name="q3" value="2" /></td>
							<td><input type="radio" name="q3" value="3" /></td>
							<td><input type="radio" name="q3" value="4" /></td>
							<td><input type="radio" name="q3" value="5" /></td>
						</tr>
					</tbody>
				</table>
				<table class="bordered-table">
					<caption>
						<h2>About the Slides</h2>
					</caption>
					<thead>
						<tr>
							<th></th>
							<th>1</th>
							<th>2</th>
							<th>3</th>
							<th>4</th>
							<th>5</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>The richness of the content :</td>
							<td><input type="radio" name="q4" value="1" /></td>
							<td><input type="radio" name="q4" value="2" /></td>
							<td><input type="radio" name="q4" value="3" /></td>
							<td><input type="radio" name="q4" value="4" /></td>
							<td><input type="radio" name="q4" value="5" /></td>
						</tr>
						<tr>
							<td>The format / layout:</td>
							<td><input type="radio" name="q5" value="1" /></td>
							<td><input type="radio" name="q5" value="2" /></td>
							<td><input type="radio" name="q5" value="3" /></td>
							<td><input type="radio" name="q5" value="4" /></td>
							<td><input type="radio" name="q5" value="5" /></td>
						</tr>
						<tr>
							<td>The examples:</td>
							<td><input type="radio" name="q6" value="1" /></td>
							<td><input type="radio" name="q6" value="2" /></td>
							<td><input type="radio" name="q6" value="3" /></td>
							<td><input type="radio" name="q6" value="4" /></td>
							<td><input type="radio" name="q6" value="5" /></td>
						</tr>
					</tbody>
				</table>
				<div class="clearfix center">
					<label for="coment">Coment:</label>
					<div class="input">
						<textarea name="coment" rows="4">
					</textarea>
					</div>
				</div>
				<div class="actions">
					<input type="submit" class="btn primary" value="Mark">&nbsp;
				</div>
			</form>
		</div>
	</div>
</body>
</html>