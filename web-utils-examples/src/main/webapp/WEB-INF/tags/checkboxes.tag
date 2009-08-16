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
	<br/>
	<sub>${comment}</sub>
</c:if>
</td>
<td>
<wu:set var="v" name="${name}" value="${value}"/>
<c:forEach var="o" varStatus="i" items="${options}">
	<c:choose>
		<c:when test="${wu:inArray(v, i.index)}">
			<input name="${name}" type="checkbox" value="${i.index}" checked="true" />
		</c:when>
		<c:otherwise>
			<input name="${name}" type="checkbox" value="${i.index}" />
		</c:otherwise>
	</c:choose>
    <wu:safeHtml value="${o.value}" /><br/>
</c:forEach>
</select>
</td>
</tr>
