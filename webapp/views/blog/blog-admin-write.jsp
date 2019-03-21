<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<c:import url ="/views/includes/header.jsp"/>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<c:import url="/views/includes/admin-menu.jsp">
					<c:param name="menu" value="write" />
				</c:import>
				<form:form 
					modelAttribute="postVo" 
					action="${pageContext.servletContext.contextPath }/${authUser.id}/admin/write" 
					method="post">
			      	<table class="admin-cat-write">
			      		<tr>
			      			<td class="t">제목</td>
			      			<td>
			      				<form:input size="60" path="title"/>
			      				<!-- <input type="text" size="60" name="title" value="${postVo.title }">  -->
				      			<select name="categoryNo">
				      				<c:forEach items="${list }" var="vo" varStatus="status">
				      					<option value="${vo.no }">${vo.name }</option>
									</c:forEach>
				      			</select>
				      			<p style="margin: 0; padding: 0; font-weight:bold; color: red; text-align: left;">
									<form:errors path="title" />
								</p>
				      		</td>
				      		
			      		</tr>
			      		<tr>
			      			<td class="t">내용</td>
			      			<td><form:textarea path="content"/>
			      			<p style="margin: 0; padding: 0; font-weight:bold; color: red; text-align: left;">
								<form:errors path="content" />
							</p> 
			      			</td>
			      		</tr>
			      		<tr>
			      			<td>&nbsp;</td>
			      			<td class="s"><input type="submit" value="포스트하기"></td>
			      		</tr>
			      	</table>
				</form:form>
			</div>
		</div>
		<c:import url ="/views/includes/footer.jsp"/>
	</div>
</body>
</html>