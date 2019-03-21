<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">

var isEnd = false;
var page = 0;

var messageBox = function(title, message){
	$("#dialog-message").attr("title", title);
	$("#dialog-message p").text(message);
	$("#dialog-message").dialog({
		modal: true,
		buttons: {
			"확인": function(){
				$(this).dialog("close");
			}
		}
	});
}

var render = function(vo, mode){
	//template library
	//ex) ejs, underscore, mustache

	var htmls = 
				"<tr>" +
				"<td>"+ vo.number +"</td>" + 
				"<td>"+ vo.name +"</td>" + 
				"<td>"+ vo.postCount +"</td>" +
				"<td>"+ vo.description +"</td>" +
				"<td><img src='${pageContext.request.contextPath}/assets/images/delete.jpg'> " +
				"<input type='hidden' id='hidden-no' value='"+ vo.no +"'></td>" +
				"</tr>";
	
				 
	if(mode == true){
		$("#table-header").after(htmls);
	} else {
		$(".admin-cat").append(htmls);
	}
	
}

var fetchList = function(){
	
	if(isEnd == true){
		return;
	}
	
	++page;
	$.ajax({
		url: "${pageContext.servletContext.contextPath }/${authUser.id}/admin/category/ajaxList?p="+ page,
		type: "get",
		dataType: "json",
		data:"",
		success: function(response){

			if(response.result == "fail"){
				console.warn(response.data);
				return;
			}
			
			if(response.data.length < 5){
				isEnd = true;
				$("#btn-next").prop("disabled", true);
			}
			//rendering
			$.each(response.data, function(index, vo){
				render(vo, false);
			});
		},
		error: function(xhr, status, e){
			console.log(status + ":" + e);
		}
		
	});
	
}

$(function(){
	
	$(".admin-cat").on("click", "img", function(){
		
		console.log("ajax 삭제 작업");
		var $delParent = $(this).parent().parent();
		//$(this).parent().parent().css("border", "4px solid #f00");
		var $no = $(this).next().attr("value");
		//$(this).next().html()
		/*
		console.log($("#hidden-no").val());
		
		var $passwordDelete = $("#password-delete");
		var $hiddenNo = $("#hidden-no");
		if($passwordDelete.val() == ""){
			
			$(".validateTipsNormal").hide();
			$(".validateTipsError").show();
			return;
		}
		
		*/
		$.ajax({
			url: "${pageContext.servletContext.contextPath }/${authUser.id}/admin/category/ajaxDelete",
			type: "post",
			dataType: "json",
			data: "no=" + $no,
			success: function(response){
				
				if(response.result == "fail"){
					//$(".validateTipsNormal").hide();
					//$(".validateTipsError").show();
					console.warn(response.data);
					return;
				}
				
				if(response.result == "success"){
					
					//rendering
					$delParent.remove();
					//$("#list-guestbook li[data-no="+response.data+"]").remove();
					//dialogDelete.dialog("close");
				}
				
			},
			error: function(xhr, status, e){
				console.log(status + ":" + e);
			}
			
		});
		
	});
	
	
	
	//메세지 등록 폼 submit 이벤트
	$("#add-form").submit(function(event){
		//submit의 기본동작(post)
		//막아야 한다.
		event.preventDefault();
		//validate form data
		$inputName = $("#input-name");
		$inputDesc = $("#input-desc");
		
		var name = $inputName.val();
		if(name == ""){
			messageBox("글남기기", "카테고리명은 필수 입력 항목입니다.");
			return;
		}
		
		var description = $inputDesc.val();
		
		if(description == ""){
			messageBox("글남기기", "설명은 필수 입력 항목입니다.");
			return;
		}
		
		$.ajax({
			url: "${pageContext.servletContext.contextPath }/${authUser.id}/admin/category/ajaxInsert",
			type: "post",
			dataType: "json",
			data: "name=" + name
			     + "&description=" + description,
			success: function(response){

				if(response.result == "fail"){
					console.warn(response.data);
					return;
				}
				
				if(response.result == "success"){
					//rendering
					render(response.data, true);
					$inputName.val("");
					$inputDesc.val("");
				}
				
			},
			error: function(xhr, status, e){
				console.log(status + ":" + e);
			}
			
		});
	});
	
	//최초 리스트 가져오기
	fetchList();
});
</script>
</head>
<body>
	<div id="container">
		<c:import url ="/views/includes/header.jsp"/>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<c:import url="/views/includes/admin-menu.jsp">
					<c:param name="menu" value="category" />
				</c:import>
		      	<table class="admin-cat">
		      		<tr id="table-header">
		      			<th>번호</th>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>			
		      		</tr>
		      		<!-- 
					<tr>
						<td>3</td>
						<td>미분류</td>
						<td>10</td>
						<td>카테고리를 지정하지 않은 경우</td>
						<td><img src="${pageContext.request.contextPath}/assets/images/delete.jpg"></td>
					</tr>  
					<tr>
						<td>2</td>
						<td>스프링 스터디</td>
						<td>20</td>
						<td>어쩌구 저쩌구</td>
						<td><img src="${pageContext.request.contextPath}/assets/images/delete.jpg"></td>
					</tr>
					<tr>
						<td>1</td>
						<td>스프링 프로젝트</td>
						<td>15</td>
						<td>어쩌구 저쩌구</td>
						<td><img src="${pageContext.request.contextPath}/assets/images/delete.jpg"></td>
					</tr>
					 -->					  
				</table>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
      			<form id="add-form" action="" method="post">
			      	<table id="admin-cat-add">
			      		<tr>
			      			<td class="t">카테고리명</td>
			      			<td><input type="text" name="name" id="input-name"></td>
			      		</tr>
			      		<tr>
			      			<td class="t">설명</td>
			      			<td><input type="text" name="desc" id="input-desc"></td>
			      		</tr>
			      		<tr>
			      			<td class="s">&nbsp;</td>
			      			<td><input type="submit" value="카테고리 추가"></td>
			      		</tr>      		      		
			      	</table>
		      	</form> 
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p style="padding: 30px 0;"></p>
			</div>
		</div>
		<c:import url ="/views/includes/footer.jsp"/>
	</div>
</body>
</html>