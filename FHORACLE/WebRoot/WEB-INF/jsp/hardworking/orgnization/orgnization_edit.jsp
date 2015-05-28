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
			if($("#ORGCODE").val()==""){
			$("#ORGCODE").tips({
				side:3,
	            msg:'请输入机构编码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ORGCODE").focus();
			return false;
		}
		if($("#ORGNAME").val()==""){
			$("#ORGNAME").tips({
				side:3,
	            msg:'请输入机构名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ORGNAME").focus();
			return false;
		}
		if($("#CREATEDATE").val()==""){
			$("#CREATEDATE").tips({
				side:3,
	            msg:'请输入创建日期',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CREATEDATE").focus();
			return false;
		}
		if($("#STATUS").val()==""){
			$("#STATUS").tips({
				side:3,
	            msg:'请输入状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#STATUS").focus();
			return false;
		}
		if($("#RANK").val()==""){
			$("#RANK").tips({
				side:3,
	            msg:'请输入等级',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#RANK").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="orgnization/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ORGNIZATION_ID" id="ORGNIZATION_ID" value="${pd.ORGNIZATION_ID}"/>
		<div id="zhongxin">
		<table>
			<tr>
				<td><input type="text" name="ORGCODE" id="ORGCODE" value="${pd.ORGCODE}" maxlength="32" placeholder="这里输入机构编码" title="机构编码"/></td>
			</tr>
			<tr>
				<td><input type="text" name="ORGNAME" id="ORGNAME" value="${pd.ORGNAME}" maxlength="32" placeholder="这里输入机构名称" title="机构名称"/></td>
			</tr>
			<tr>
				<td><input class="span10 date-picker" name="CREATEDATE" id="CREATEDATE" value="${pd.CREATEDATE}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="创建日期" title="创建日期"/></td>
			</tr>
			<tr>
<!-- 				<td><input type="text" name="STATUS" id="STATUS" value="${pd.STATUS}" maxlength="32" placeholder="这里输入状态" title="状态"/></td> -->
				
				<td>
					<select name="STATUS" title="状态">
					<option value="1" <c:if test="${pd.STATUS == '1' }">selected</c:if> >可用</option>
					<option value="0" <c:if test="${pd.STATUS == '0' }">selected</c:if> >不可用</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><input type="text" name="RANK" id="RANK" value="${pd.RANK}" maxlength="32" placeholder="这里输入等级" title="等级"/></td>
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