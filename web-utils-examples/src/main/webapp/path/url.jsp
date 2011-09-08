<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="wu" uri="http://mikha.org/web-utils"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<my:header title="&lt;url&gt; tag demo" />

<c:if test="${param['url'] != null}">
	<wu:url var="url" value="${param['url']}">
		<wu:param name="param1" value="${param['param1']}" />
		<wu:param name="param2" value="${param['param2']}" />
	</wu:url>
	<a href="${url}">Here is your link.</a>
</c:if>

<form action="url.jsp" method="get">
<table>
	<my:input name="url" value="${param[url]}" desc="Base part of URL" />
	<my:input name="param1" value="${param['param1']}"
		desc="Value of first parameter" />
	<my:input name="param2" value="${param['param2']}"
		desc="Value of seconds parameter" />
</table>
<input type="submit" name="submit" value="Submit"></form>
</body>
</html>