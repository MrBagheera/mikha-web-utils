<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="wu" uri="http://mikha.org/web-utils"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<my:header title="Parsing Scalar Parameters"/>

<form action="scalar" method="post">
<h2>
<wu:iferrors>There are errors!</wu:iferrors>
<wu:ifnoerrors>There are no errors!</wu:ifnoerrors>
</h2>
<table border="1">
	<tr>
		<td>Name and Type</td>
		<td>Current Value</td>
		<td>Current Error</td>
		<td>Input</td>
	</tr>
	<tr>
		<td colspan="4" align="center">Integer Parameters</td>
	</tr>
	<my:sf-input name="int1" description="int1 - mandatory" value="${int1}"/>
    <my:sf-input name="int2" description="int2 - optional, default to 100" value="${int2}"/>
	<tr>
		<td colspan="4" align="center">String Parameters</td>
	</tr>
    <my:sf-input name="str1" description="str1 - mandatory" value="${str1}"/>
    <my:sf-input name="str2" description="str2 - optional" value="${str2}"/>
    <my:sf-input name="str3" description="str3 - mandatory, must be valid email address" value="${str3}"/>
	<tr>
		<td colspan="4" align="center">Boolean Parameters</td>
	</tr>
    <my:sf-checkbox name="bool1" description="bool1 - default true" value="true"/>
    <my:sf-checkbox name="bool2" description="bool2 - default false" value="false"/>
</table>
<input type="submit" name="submit" value="Submit">
</form>
</body>
</html>