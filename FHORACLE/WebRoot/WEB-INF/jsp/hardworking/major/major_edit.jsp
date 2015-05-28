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
			if($("#ZYH").val()==""){
			$("#ZYH").tips({
				side:3,
	            msg:'请输入专业号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ZYH").focus();
			return false;
		}
		if($("#ZYMC").val()==""){
			$("#ZYMC").tips({
				side:3,
	            msg:'请输入专业名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ZYMC").focus();
			return false;
		}
		if($("#ZYJC").val()==""){
			$("#ZYJC").tips({
				side:3,
	            msg:'请输入专业简称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ZYJC").focus();
			return false;
		}
		if($("#ZYYWMC").val()==""){
			$("#ZYYWMC").tips({
				side:3,
	            msg:'请输入专业英文名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ZYYWMC").focus();
			return false;
		}
		if($("#ORGNIZATION_ID").val()==""){
			$("#ORGNIZATION_ID").tips({
				side:3,
	            msg:'请输入所属学院',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ORGNIZATION_ID").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="major/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="MAJOR_ID" id="MAJOR_ID" value="${pd.MAJOR_ID}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="ZYH" id="ZYH" value="${pd.ZYH}" maxlength="32" placeholder="这里输入专业号" title="专业号"/></td>
			</tr>
			<tr>
				<td><input type="text" name="ZYMC" id="ZYMC" value="${pd.ZYMC}" maxlength="32" placeholder="这里输入专业名称" title="专业名称"/></td>
			</tr>
			<tr>
				<td><input type="text" name="ZYJC" id="ZYJC" value="${pd.ZYJC}" maxlength="32" placeholder="这里输入专业简称" title="专业简称"/></td>
			</tr>
			<tr>
				<td><input type="text" name="ZYYWMC" id="ZYYWMC" value="${pd.ZYYWMC}" maxlength="32" placeholder="这里输入专业英文名称" title="专业英文名称"/></td>
			</tr>
			<tr>
				<td style="vertical-align:top;"> 
				 	<select class="chzn-select" name="ORGNIZATION_ID" id="ORGNIZATION_ID" data-placeholder="请选择学院" style="vertical-align:top;width: 220px;">
					<option value=""></option>
					<c:forEach items="${orgnizationList}" var="orgnization">
						<option value="${orgnization.ORGNIZATION_ID }" <c:if test="${pd.ORGNIZATION_ID==orgnization.ORGNIZATION_ID}">selected</c:if>>${orgnization.ORGNAME }</option>
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
		
		</script>
</body>
</html>