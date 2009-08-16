<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="wu" uri="http://mikha.org/web-utils"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<my:header title="Error Handling Demo"/>

Dear <wu:safeHtml>${salut.value} ${first} ${family}</wu:safeHtml>! Thank you for applying to receive our
great spam.
<br>
Following are your details:
<table>
	<tr>
		<td>Email:</td>
		<td><wu:safeHtml>${email}</wu:safeHtml></td>
	</tr>
	<tr>
		<td>Age (full years):</td>
		<td><wu:safeHtml>${age}</wu:safeHtml></td>
	</tr>
	<tr>
		<td>I wish to receive spam for following topics:</td>
		<td>
		<c:forEach var="t" items="${spam}">
        <wu:safeHtml>${t.value}</wu:safeHtml><br>
		</c:forEach>
		</td>
	</tr>
	<tr>
		<td>Comments:</td>
		<td><wu:safeHtml>${comments}</wu:safeHtml></td>
	</tr>
	<tr>
		<td>Picture:</td>
		<td><c:if test="${!empty picture}">Name: ${picture.name}<br>
		Type: ${picture.contentType}<br>
		Size: ${picture.size}</c:if></td>
	</tr>
</table>
</body>
</html>