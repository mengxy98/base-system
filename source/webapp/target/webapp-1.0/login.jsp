<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>压实联合作业管理平台</title>
<link rel="shortcut icon" href="<%=path %>/images/favicon.ico">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/base.css" rel="stylesheet">
<!-- 判断是否子页面过期,直接跳转到登录页面 -->
<script type="text/javascript">
  if(null != parent.document.getElementById("iframePage")) {
	  top.location.href = "<%=path%>/login.jsp";
   }
</script>
<script src="<%=request.getContextPath()%>/js/app/sysconfig/login.js"></script>
<script src="<%=request.getContextPath()%>/js/cookie_util.js"></script>
<script type="text/javascript" src="<%=path%>/js/utils/md5.js"></script>
<!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
</head>
<body onkeydown="keyLogin(event);">
	<div class="page-container">
		<div class="bg">
			<div class="title" style="margin-top:2%;margin-left:1%;color:#409BD8;font-size:240%;font-weight: bold;">压实联合作业管理平台</div>
		</div>
		
		<div class="container-fluid login_box" style="visibility:hidden;">
			<div class="row">
				<div class="col-sm-offset-3 col-sm-6 form-bg col-xs-12"></div>
			</div>
			<form class="form-horizontal">
				<div class="form-group has-success">
					<label for="userName"
						class="col-sm-offset-2 col-sm-2 control-label">用户名：</label>
					<div class="col-sm-3">
						<input type="text" class="form-control" id="userName"
							placeholder="请输入用户名" style="padding-left:30px;background:url(images/1.png) no-repeat #fff;">
					</div>
					<div class="col-sm-2">
                		<span  style="font-weight:normal;color:red;display:none" id="empty_userName">*用户名不能为空</span>
               		</div>
				</div>
				<div class="form-group has-success">
					<label for="userPasswd"
						class="col-sm-offset-2 col-sm-2 control-label">密码：</label>
					<div class="col-sm-3">
						<input type="password" class="form-control" id="userPasswd"
							placeholder="请输入密码" style="padding-left:30px;background:url(images/2.png) no-repeat #fff;">
					</div>
					<div class="col-sm-2 ">
                		<span  style="font-weight:normal;color:red;display:none" id="empty_password">*密码不能为空</span>
                	</div>
				</div>
				<div class="form-group has-success">
					<label for="checkNum"
						class="col-sm-offset-2 col-sm-2 control-label col-xs-12">验证码：</label>
					<div class="col-sm-2 col-xs-7">
						<input type="text" class="form-control" id="checkNum"
							placeholder="请输入验证码">
					</div>
					<div class="col-sm-1 col-xs-5">
						<img src="captcha.do"
							onclick="this.src='captcha.do?d='+new Date().getTime()" />
					</div>
					<div class="col-sm-2 col-xs-2">
                		 <!-- <span  style="font-weight:normal;color:red;" display:none" id="wrong_code">*验证码错误</span> -->
                		<span  style="font-weight:normal;color:red;display:none" id="empty_code">*验证码不能为空</span>
                	</div>
				</div>
				<div  class="form-group">
             	 	<div class=" col-sm-offset-4 col-sm-8  col-xs-12">
             			<span  style="font-weight:normal;color:red;display:none" id="msgcode">*用户名或密码错误</span>
             			<!-- <span  style="font-weight:normal;color:red;display:none" id="userNotMenu">*该用户没有权限登录</span> -->
             		</div>
             	</div>
				<div class="form-group">
					<div class="col-sm-offset-4 col-sm-3" style="padding-left:5%;">
						<div class="checkbox" style="display:inline-block;">
							<label> <input type="checkbox" id="remember">记住我</label>
						</div>
						<button type="button" class="btn btn-success" id="btn-login" style="display:inline-block;margin-left:5%;width:30%;">登录</button>
					</div>
				</div>
<!-- 				<div class="form-group"> -->
<!-- 					<div class="col-sm-offset-4 col-sm-3"> -->
<!-- 						<button type="button" class="btn btn-success" id="btn-login">登录</button> -->
<!-- 					</div> -->
<!-- 				</div> -->
			</form>
		</div>
	</div>
	
<footer style="position:absolute;bottom:15%;left:35%;">技术支持：国家 IT工程发展有限公司</footer>
</body>
<jsp:include page="commonJSP/commonjs.jsp"></jsp:include>
</html>