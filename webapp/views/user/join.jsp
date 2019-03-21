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
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript">
$(function(){
	
	$("#join-form").submit(function(){
		
		//1. 이메일 중복체크 유무
		if($("#img-checkid").is(":visible") == false){
			alert("아이디 중복 체크를 해야 합니다.");
			return false;
		}

		//2. 약관동의
		if($("#agree-prov").is(":checked") == false){
			alert("약관 동의를 해야 합니다.");
			return false;
		}
		
		return true;
	});
	
	//아이디 내용 수정시
	$("#blog-id").change(function(){
		$("#btn-checkid").show();
		$("#img-checkid").hide();
	})
	
	//아이디 중복체크 여부
	$("#btn-checkid").click(function(){
		var id = $("#blog-id").val();
		
		if(id == ""){
			return;
		}
		
		$.ajax({
			url: "${pageContext.servletContext.contextPath }/user/api/checkid?id=" + id,
			type: "get",
			dataType: "json",
			data: "",
			success: function(response){
				if(response.result == "fail"){
					
					console.error(response.message);
					return;
				}
				
				if(response.data == true){
					alert("이미 존재하는 아이디입니다. 다른 아이디를 사용해 주세요.");
					$("#blog-id")
					.val("")
					.focus();
				}else{
					$("#btn-checkid").hide();
					$("#img-checkid").show();
				}
			},
			error: function(xhr, status, e){
				console.error(status + ":" + e);
			}
		});
	});
})	
</script>
</head>
<body>
	<div class="center-content">
		<h1 class="logo">JBlog</h1>
		<c:import url ="/views/includes/menu.jsp"/>
		<form:form
			modelAttribute="userVo" 
			class="join-form" 
			id="join-form" 
			method="post" 
			action="${pageContext.servletContext.contextPath }/user/join">
			<label class="block-label" for="name">이름</label>
			<input id="name"name="name" type="text" value="${userVo.name }">
			<p style="margin: 0; padding: 0; font-weight:bold; color: red; text-align: left;">
				<form:errors path="name" />
			</p>
			
			<label class="block-label" for="blog-id">아이디</label>
			<input id="blog-id" name="id" type="text" value="${userVo.id }"> 
			<input id="btn-checkid" type="button" value="id 중복체크">
			<img id="img-checkid" style="display: none;" src="${pageContext.request.contextPath}/assets/images/check.png">
			<p style="margin: 0; padding: 0; font-weight:bold; color: red; text-align: left;">
				<form:errors path="id" />
			</p>

			<label class="block-label" for="password">패스워드</label>
			<input id="password" name="password" type="password" />
			<p style="margin: 0; padding: 0; font-weight:bold; color: red; text-align: left;">
				<form:errors path="password" />
			</p>

			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" value="가입하기">

		</form:form>
	</div>
</body>
</html>
