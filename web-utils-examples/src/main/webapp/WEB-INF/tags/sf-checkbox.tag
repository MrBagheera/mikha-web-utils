<%@ attribute name="name" required="true"%>
<%@ attribute name="value" required="true"%>
<%@ attribute name="description" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="wu" uri="http://mikha.org/web-utils"%>
<tr>
<td>
${description}
</td>
<td>
${value}
</td>
<td>
<wu:error name="${name}" />
</td>
<td>
<c:choose>
    <c:when test="${wu:param(pageContext,name,value)}">
<input name="${name}" type="checkbox" value="true" checked="true" />    
    </c:when>
    <c:otherwise>
<input name="${name}" type="checkbox" value="true"/>    
    </c:otherwise>
</c:choose>
</td>
</tr>