<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="wu" uri="http://mikha.org/web-utils" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<my:header title="Internationalization"/>

<fmt:setBundle basename="org.mikha.utils.web.examples.I18nResources"/>

<table>
<tr>
<td>Raw error:</td>
<td><wu:error name="param1" defaultText="No custom error message was defined"/></td>
</tr>
<tr>
<td>Localized error:</td>
<td>
<wu:iferror name="param1" var="error"><fmt:message key="error.param1"/></wu:iferror>
</td>
</tr>
<tr>
<td>Raw message:</td>
<td><wu:msg/></td>
</tr>
<tr>
<td>Localized message:</td>
<td><wu:msg i18n="true"/></td>
</tr>
</table>
</body>
</html>