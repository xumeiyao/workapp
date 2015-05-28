<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/chosen.css" />
		
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		
		<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
<script type="text/javascript">
	
	
	//保存
	function save(){
			if($("#TXMC").val()==""){
			$("#TXMC").tips({
				side:3,
	            msg:'请输入体系名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TXMC").focus();
			return false;
		}
		if($("#ZD_ID").val()==""){
			$("#ZD_ID").tips({
				side:3,
	            msg:'请输入课程类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ZD_ID").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="evaluatesystem/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="EVALUATESYSTEM_ID" id="EVALUATESYSTEM_ID" value="${pd.EVALUATESYSTEM_ID}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="TXMC" id="TXMC" value="${pd.TXMC}" maxlength="32" placeholder="这里输入体系名称" title="体系名称"/></td>
			</tr>
			<tr>
				<td style="vertical-align:top;"> 
				 	<select class="chzn-select" name="Edict" id="Edict" data-placeholder="请选择课程类型" style="vertical-align:top;width: 220px;">
					<option value=""></option>
					<c:forEach items="${dictionariesList }" var="dictionaries">
						<option value="${dictionaries.ZD_ID }" <c:if test="${pd.Edict==dictionaries.ZD_ID}">selected</c:if>>${dictionaries.NAME }</option>
					</c:forEach>
				  	</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		$("#dict").change(function(){
			alert('123');
		});
		
		</script>
</body>
</html>