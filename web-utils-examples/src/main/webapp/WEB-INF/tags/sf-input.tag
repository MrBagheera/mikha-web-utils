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
<wu:escapeXml var="v"><wu:set name="${name}" value="${value}" /></wu:escapeXml>
<wu:iferror name="${name}">
    <input name="${name}" type="text" style="background-color: red"
        value="${v}" />
</wu:iferror>
<wu:ifnoerror name="${name}">
    <input name="${name}" type="text" value="${v}" />
</wu:ifnoerror>
</td>
</tr>