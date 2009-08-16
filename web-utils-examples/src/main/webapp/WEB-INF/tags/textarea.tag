<%@ attribute name="name" required="true"%>
<%@ attribute name="desc" required="true"%>
<%@ attribute name="value" required="true"%>
<%@ attribute name="comment" required="false"%>
<%@ attribute name="rows" required="true"%>
<%@ attribute name="cols" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="wu" uri="http://mikha.org/web-utils"%>
<tr>
<td>
${desc}
<c:if test="${! empty comment}">
	<br>
	<sub>${comment}</sub>
</c:if>
</td>
<td>
<wu:set var="v" name="${name}" value="${value}" />
<c:choose>
	<c:when test="${wu:hasError(pageContext,name)}">
		<textarea name="${name}" rows="${rows}" cols="${cols}"
			style="background-color: red"><wu:safeHtml value="${v}" /></textarea>
	</c:when>
	<c:otherwise>
		<textarea name="${name}" rows="${rows}" cols="${cols}"><wu:safeHtml value="${v}" /></textarea>
	</c:otherwise>
</c:choose>
</td>
</tr>
