<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="wu" uri="http://mikha.org/web-utils" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<my:header title="Log4J Configuration"/>

<form action="" enctype="multipart/form-data" method="post">
<table>
    <my:textarea value="${levels}" name="levels" rows="10" cols="64" desc="Log levels: logger=level" />
</table>
<input type="submit" name="submit" value="Submit"></form>
</form>

</body>
</html>