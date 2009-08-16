<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="wu" uri="http://mikha.org/web-utils"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<my:header title="Error Handling Demo"/>

<form action="demo" enctype="multipart/form-data" method="post">
<p>Fields, marked with star (*) are mandatory</p>
<wu:iferrors>
    <strong>Please, correct all errors (marked red)!</strong>
</wu:iferrors>
<table>
	<my:input name="first" value="${first}" desc="First Name: *" />
	<my:input name="family" value="${family}" desc="Family Name: *" />
	<my:select options="${SALUTATIONS}" name="salut" value="${salut}" desc="Salutation:" />
	<my:input name="email" value="${email}" desc="Email: *" />
	<my:input name="age" value="${age}" desc="Age (full years): *" comment="Must be between 18 and 120" />
	<my:checkboxes options="${SPAM_TOPICS}" value="${spam}" name="spam"
		desc="I wish to receive spam for following topics:" />
	<my:textarea value="${comments}" name="comments" rows="5" cols="30" desc="Comments: (max 100 symbols)" />
	<tr>
		<td>Picture:</td>
		<td><input type="file" name="picture"></td>
	</tr>
</table>
<input type="submit" name="submit" value="Submit"></form>
</body>
</html>