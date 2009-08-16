<%@ attribute name="name" required="true"%>
<%@ attribute name="desc" required="true"%>
<%@ attribute name="value" required="true" type="java.lang.Object"%>
<%@ attribute name="options" required="true" type="java.lang.Object"%>
<%@ attribute name="comment" required="false"%>
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
<select name="${name}">
<wu:set var="v" name="${name}" value="${value}" />
<c:forEach var="o" varStatus="i" items="${options}">
	<c:choose>
		<c:when test="${i.index == v}">
			<option value="${i.index}" selected="true"><wu:safeHtml value="${o.value}" /></option>
		</c:when>
		<c:otherwise>
			<option value="${i.index}"><wu:safeHtml value="${o.value}" /></option>
		</c:otherwise>
	</c:choose>
</c:forEach>
</select>
</td>
</tr>
