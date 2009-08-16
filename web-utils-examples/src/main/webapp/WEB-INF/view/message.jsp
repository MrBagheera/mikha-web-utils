<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="wu" uri="http://mikha.org/web-utils" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<my:header title="Message"/>

<wu:msg/><br>

<c:if test="${!empty OkUrl}">
<wu:form url="${OkUrl}">
    <input type="submit" value="OK"/>
</wu:form>
</c:if>

<c:if test="${!empty AbortUrl}">
<wu:form url="${AbortUrl}" method="GET">
    <input type="submit" value="Abort"/>
    <wu:param name="agent" value="web-utils-examples"/>
</wu:form>
</c:if>

</body>
</html>