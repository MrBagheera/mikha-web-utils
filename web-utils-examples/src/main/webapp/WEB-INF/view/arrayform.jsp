<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="wu" uri="http://mikha.org/web-utils"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<my:header title="Parsing Array Parameters"/>

<form action="array" method="post">
<h2><wu:iferrors>There are errors!</wu:iferrors><wu:ifnoerrors>There are no errors!</wu:ifnoerrors></h2>
<table border="1">
	<tr>
        <td>Index</td>
		<td>Current Value</td>
		<td>Current Error</td>
		<td>Input</td>
	</tr>
	<tr>
		<td colspan="4" align="center">Integer Array</td>
	</tr>
	<c:forEach var="i" items="${ints}" varStatus="status">
        <my:sf-input name="ints" description="${status.index}" value="${i}"/>
	</c:forEach>
	<tr>
		<td colspan="4" align="center">String Array</td>
	</tr>
	<c:forEach var="i" items="${strs}" varStatus="status">
        <my:sf-input name="strs" description="${status.index}" value="${i}"/>
	</c:forEach>
	<tr>
		<td colspan="4" align="center">Boolean Array</td>
	</tr>
	<c:forEach var="i" items="${bools}" varStatus="status">
        <my:sf-input name="bools" description="${status.index}" value="${i}"/>
	</c:forEach>
</table>
<input type="submit" name="submit" value="Submit"></form>
</body>
</html>