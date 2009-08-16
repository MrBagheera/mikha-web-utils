<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="wu" uri="http://mikha.org/web-utils"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<my:header title="File Upload"/>

<form action="file" enctype="multipart/form-data" method="post">
<wu:iferror name="file">Error: ${error}<br></wu:iferror>
Upload an image: <input type="file" name="file">
<input type="submit" name="submit" value="Submit"></form>
</body>
</html>