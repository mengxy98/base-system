<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-cn" style="background: #fff;">
<head>
<meta charset="utf-8">
<title>角色管理</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<jsp:include page="/commonJSP/comcss.jsp"></jsp:include>
<script src="<%=request.getContextPath()%>/js/app/common.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/zTreeStyle/zTreeStyle.css" />
<style>
li {
	list-style: none;
}
/* 遮罩层 */
.loadContainer {
	display: none;
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: 100;
}

.loadingMask {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: #ddd;
	opacity: 0.6;
}

.loadingContainer {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
}

.loading {
	margin-bottom: 0;
	width: 30%;
	height: 20px;
	top: 40%;
	left: 30%;
}

.progress-bar {
	background-color: #337ab7;
	background-image: linear-gradient(45deg, rgba(255, 255, 255, .15) 25%,
		transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%,
		rgba(255, 255, 255, .15) 75%, transparent 75%, transparent);
}

small {
	font-size: 1.5em;
}

a:hover {
	text-decoration: underline;
}
/*说明文字*/
.directionTh {
	position: relative;
}

.directionDiv {
	display: none;
	position: absolute;
	right: 0;
	bottom: 100%;
	width: 200%;
	height: 300%;
	background: #fafafa;
	border: 1px solid #ddd;
	font-weight: normal;
}

div.content_wrap {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: RGBA(0, 0, 0 0.5);
	background-color: rgba(115, 115, 115, 0.701961);
}

div.content_wrap div.left {
	width: 50%;
	height: 60%;
	margin: 10% 25% 0 25%;
	background: #fff;
	padding-bottom: 1%;
	overflow: auto;
}

div.content_wrap div .zTreeDemoBackground {
	width: 65%;
	height: 70%;
	margin: 10% 17.5% 0 17.5%;
	background: #fff;
	padding-bottom: 1%;
	overflow: auto;
}

#treeDemo {
	background: #f0f6e4;
	width: 80%;
	height: auto;
	background: #fff;
	margin: 6% auto;
	padding-top: 2.5em;
}

ul.ztree {
	overflow-y: auto;
	overflow-x: auto;
	border: none;
	padding-top: 2.5em;
}
</style>
</head>
<body style="background: #fff;">
	<jsp:include page="/commonJSP/headerAndNav.jsp"></jsp:include>
	<script src="<%=request.getContextPath()%>/js/cookie_util.js"></script>

	<script src="<%=request.getContextPath()%>/js/libs/jquery-2.1.1.min.js"
		type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.form.js"
		type="text/javascript"></script>
	<script
		src="<%=request.getContextPath()%>/js/app/userAndRole/roleManagement.js"
		type="text/javascript"></script>
	<div id="main1" role="main">
		<!-- MAIN CONTENT -->
		<div id="content">
			<div style="position: relative;">
				<ol class="breadcrumb">
					<li>权限管理</li>
					<li>角色管理</li>
				</ol>
			</div>
			
			<section id="widget-grid">
				<div class="row">
					<article class="col-sm-12">
						<div class="jarviswidget" id="wid-id-2"
							data-widget-togglebutton="false" data-widget-editbutton="false"
							data-widget-fullscreenbutton="false"
							data-widget-colorbutton="false" data-widget-deletebutton="false">

							<div class="no-padding">
								<!-- 正在加载数据遮罩层  开始 -->
								<div class="loadContainer" id="loadingRoleManagent">
									<div class="loadingMask"></div>
									<div class="loadingContainer">
										<div class="progress loading">
											<div class="progress-bar progress-bar-striped active"
												role="progressbar" style="width: 100%">正在加载中...</div>
										</div>
									</div>
								</div>
								<!-- 正在加载数据遮罩层  结束 -->
								<div class="widget-body">
									<div id="myQualityContent" class="tab-content container-fluid">
										<div class="row"
											style="padding-top: 1%; padding-left: 1%; padding-bottom: 0.5%;">
											<form class="form-inline">
												<div class="form-group" style="margin-right: 1.5em">
													<label for="role">角色：</label> <input placeholder="请输入角色名称"
														type="text" id="role" style="height: 25px" />
												</div>
												<div class="form-group">
													<button type="button" class="btn btn-primary" id="search">
														<span class="glyphicon glyphicon-search"></span> 查询
													</button>
												</div>
											</form>
										</div>
										<div style="padding-top: 1%">
											<div class="widget-body no-padding">
												<table id="datatable_col_reorder_roleManagement"
													class="table table-striped table-bordered table-hover">
													<thead>
														<tr>
															<th style="width: 5%;"></th>
															<th data-class="expand"
																style="vertical-align: middle; width: 7%;">序号</th>
															<th style="text-align: center; vertical-align: middle"
																data-hide="phone,tablet">角色ID
															</th>
															<th data-class="phone" style="vertical-align: middle;">角色名</th>
															<th data-hide="phone" style="vertical-align: middle;">角色含义</th>
															<th style="vertical-align: middle;"
																data-hide="phone,tablet;">权限类型</th>
															<th style="text-align: center; vertical-align: middle"
																data-hide="phone,tablet">启用日期
																</th>
															<th style="vertical-align: middle;"
																data-hide="phone,tablet;">失效日期</th>
															<th style="vertical-align: middle;"
																data-hide="phone,tablet;">备注</th>
															<th style="text-align: center; vertical-align: middle"
																data-hide="phone,tablet">操作人
																</th>
																<th style="text-align: center; vertical-align: middle"
																data-hide="phone,tablet">默认主页
																</th>
															<th style="text-align: center; vertical-align: middle"
																data-hide="phone,tablet">添加时间
															</th>
															<th style="text-align: center; vertical-align: middle"
																data-hide="phone,tablet">更新时间
															</th>
														</tr>
													</thead>
													<tbody>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</article>
				</div>
			</section>
		</div>
	</div>
	<!-- 修改项 -->
	<div id="proDiolagRevise"
		style="z-index:100;display: none; position: fixed; width: 100%; height: 100%; top: 0; left: 0; background: rgba(0, 0, 0, 0.5);">
		<div class="containerAdd1" style="background: #fff; width: 50%; margin: 7% 25% 2% 25%; padding-bottom: 1%; height: 65%; overflow: auto;">
			<div style="position: fixed; top: 0; left: 0; z-index: 1000; width: 50%; margin: 7% 25%; background: #fff; border-bottom: 1px solid #ddd;">
				<p style="padding: 1% 0 0 2%; height: 2em; width: 50%; float: left; font-size: 1.3em;">修改角色信息</p>
				<span class="glyphicon glyphicon-remove close closeRevise"
					style="float: right; margin: 1% 1% 1% 0; cursor: pointer" id=""></span>
			</div>
			<div id="contianerDetailRevise" style="width: 90%; margin: 8% auto; padding-top: 2em">
				<ul>
					<li style="min-height: 25px; margin-bottom: 10px">
						<p style="overflow: hidden; width: 100%;">
							<span style="float: left; width: 15%; text-align: right">角色名：</span>
							<input id="roleNameUpdata" style="float: left; width: 52%"
								type="text" value=""  />
								 <span style="float: left; color: red; font-size: large">*</span>
								<span id="roleNameUpdataTxt" style="float: left; width: 30%; margin-left:1%; font-size: .75em"></span>
						</p>
					</li>
					<li style="min-height: 25px; margin-bottom: 10px">
						<p style="overflow: hidden; width: 100%;">
							<span style="float: left; width: 15%; text-align: right">角色含义：</span>
							<input id="roleAliasUpdata" style="float: left; width: 52%"
								type="text" value="" /> <span
								style="float: left; color: red; font-size: large">*</span><span
								style="float: left; width: 30%; margin-left:1%; font-size: .75em"></span>
						</p>
					</li>
					<li style="min-height: 25px; margin-bottom: 10px">
						<p style="overflow: hidden; width: 100%;">
							<span style="float: left; width: 15%; text-align: right">权限类型：</span>
							<select id="authorityTypeUpdata" style="float: left; width: 52%">
								<option value="0">查看</option>
								<option value="1">操作</option>
							</select> <span
								style="float: left; width: 30%; margin-left:1%; font-size: .75em"></span>
						</p>
					</li>
							<li style="min-height: 25px; margin-bottom: 10px">
						<p style="overflow: hidden; width: 100%;">
							<span style="float: left; width: 15%; text-align: right">启用日期：</span>
							<input style="float: left; width: 52%; padding-left: .2em"
								class="Wdate time" type="text"
								onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'#F{$dp.$D(\'falseTime-0\',{m:1});}'})"
								id="startTime-0"> <span
								style="float: left; color: red; font-size: large">*</span><span
								style="float: left; width: 30%; margin-left: 1%; font-size: .75em"></span>

						</p>
					</li>
					<li style="min-height: 25px; margin-bottom: 10px">
						<p style="overflow: hidden; width: 100%;">
							<span style="float: left; width: 15%; text-align: right">失效日期：</span>
							<input style="float: left; width: 52%; padding-left: .2em"
								class="Wdate time" type="text"
								onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'startTime-0\',{m:1});}'})"
								id="falseTime-0"> <span
								style="float: left; color: red; font-size: large">*</span><span
								style="float: left; width: 30%; margin-left: 1%; font-size: .75em"></span>

						</p>
					</li>
						<li style="min-height: 25px; margin-bottom: 10px">
						<p style="overflow: hidden; width: 100%;">
							<span style="float: left; width: 15%; text-align: right">默认主页：</span>
							<select id="defaultPageUpdata" style="float: left; width: 52%">
								<option  seleced value="" >请选择</option>
									<input id="inputUpdata" type="text" style="float: left;width: 52%;display:none"  value="" />
							<button id="inputManualUpdata" style="margin-left:1%">手动输入</button>
							</select>
						</p>
					</li>
					<li>
						<p style="overflow: hidden; width: 100%;">
							<span style="float: left; width: 15%; text-align: right">备注：</span>
							<textarea id="remarkUpdata"
								style="float: left; width: 52%; overflow: hidden; min-height: 160px">
						
						</textarea>
							<span
								style="float: left; width: 30%; margin-left:1%; font-size: .75em"></span>
						</p>
					</li>

				</ul>

			</div>
			<div style="position: fixed; top:75%; width: 48%; z-index: 1000; height: 45px; background: #fff; left: 25%;">
				<div style="width: 48%; margin-top: 9px; margin-left: 25%;">
					<p
						style="overflow: hidden; width: 60%; text-align: center; margin-left: 20%;">
						<button id="saveRevise" type="button" class="btn btn-primary"
							style="float: left">
							<span class="glyphicon glyphicon-saved"> </span> 保存
						</button>
						<button id="cancleRevise" type="button" class="btn btn-primary"
							style="float: right">
							<span class="glyphicon glyphicon-remove-circle"> </span> 取消
						</button>
					</p>
				</div>
			</div>
		</div>
	</div>
	<!-- 添加项 -->
	<div id="proDiolagAdd" style="z-index:100;display: none; position: fixed; width: 100%; height: 100%; top: 0; left: 0; background: rgba(0, 0, 0, 0.5);">
		<div class="containerAdd1" style="background: #fff; width: 50%; margin: 7% 25% 2% 25%; padding-bottom: 1%; height: 65%; overflow: auto;">
			<div style="position: fixed; top: 0; left: 0; z-index: 1000; width: 50%; margin: 7% 25%; background: #fff; border-bottom: 1px solid #ddd;">
				<p style="padding: 1% 0 0 2%; height: 2em; width: 50%; float: left; font-size: 1.3em;">添加角色信息</p>
				<span class="glyphicon glyphicon-remove close closeAdd "
					style="float: right; margin: 1% 1% 1% 0; cursor: pointer" id=""></span>
			</div>

			<div id="contianerDetailAdd" style="width: 90%; margin: 8% auto; padding-top: 2em">
				<ul>
					<li style="min-height: 25px; margin-bottom: 10px">
						<p style="overflow: hidden; width: 100%;">
							<span style="float: left; width: 15%; text-align: right">角色名：</span>
							<input id="roleNameAdd" style="float: left; width: 52%"
								type="text" value="" /><span
								style="float: left; color: red; font-size: large">*</span>
							<span id="roleNameAddTxt" style="float: left; width: 30%; margin-left: 1%; font-size: .75em"></span>
						</p>
					</li>
					<li style="min-height: 25px; margin-bottom: 10px">
						<p style="overflow: hidden; width: 100%;">
							<span style="float: left; width: 15%; text-align: right">角色含义：</span>
							<input id="roleAliasAdd" style="float: left; width: 52%"
								type="text" value="" /><span
								style="float: left; color: red; font-size: large">*</span> <span
								style="float: left; width: 30%; margin-left: 1%; font-size: .75em"></span>
						</p>
					</li>
					<li style="min-height: 25px; margin-bottom: 10px">
						<p style="overflow: hidden; width: 100%;">
							<span style="float: left; width: 15%; text-align: right">权限类型：</span>
							<select id="authorityTypeAdd" style="float: left; width: 52%">
								<option value="0">查看</option>
								<option value="1">操作</option>
							</select> <span
								style="float: left; width: 30%; margin-left: 1%; font-size: .75em"></span>
						</p>
					</li>
					<li style="min-height: 25px; margin-bottom: 10px">
						<p style="overflow: hidden; width: 100%;">
							<span style="float: left; width: 15%; text-align: right">启用日期：</span>
							<input style="float: left; width: 52%; padding-left: .2em"
								class="Wdate time" type="text"
								onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'#F{$dp.$D(\'falseTime-1\',{m:1});}'})"
								id="startTime-1"> <span
								style="float: left; color: red; font-size: large">*</span><span
								style="float: left; width: 30%; margin-left: 1%; font-size: .75em"></span>

						</p>
					</li>
					<li style="min-height: 25px; margin-bottom: 10px">
						<p style="overflow: hidden; width: 100%;">
							<span style="float: left; width: 15%; text-align: right">失效日期：</span>
							<input style="float: left; width: 52%; padding-left: .2em"
								class="Wdate time" type="text"
								onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'startTime-1\',{m:1});}'})"
								id="falseTime-1"> <span
								style="float: left; color: red; font-size: large">*</span><span
								style="float: left; width: 30%; margin-left: 1%; font-size: .75em"></span>

						</p>
					</li>
					<li style="min-height: 25px; margin-bottom: 10px">
						<p style="overflow: hidden; width: 100%;">
							<span style="float: left; width: 15%; text-align: right">默认主页：</span>
							<select id="defaultPageAdd" style="float: left; width: 52%">
							<option seleced value="">请选择</option>
							</select>
							<input id="inputAdd" type="text" style="float: left;width: 52%;display:none"  value="" />
							<button id="inputManualAdd" style="margin-left:1%">手动输入</button>
						</p>
							
					</li>
					<li>
						<p style="overflow: hidden; width: 100%;">
							<span style="float: left; width: 15%; text-align: right">备注：</span>
							<textarea id="remarkAdd"
								style="float: left; width: 52%; overflow: hidden; min-height: 160px">
						
						</textarea>
							<span
								style="float: left; width: 30%; margin-left: 1%; font-size: .75em"></span>
						</p>
					</li>

				</ul>

			</div>
			<div style="position: fixed; top: 75%; width: 48%; height: 45px; z-index: 1000; background: #fff; left: 25%;">
				<div style="width: 48%; margin-top: 9px; margin-left: 25%;">
					<p
						style="overflow: hidden; width: 60%; text-align: center; margin-left: 20%;">
						<button id="saveAdd" type="button" class="btn btn-primary"
							style="float: left">
							<span class="glyphicon glyphicon-saved"> </span> 保存
						</button>
						<button id="cancleAdd" type="button" class="btn btn-primary"
							style="float: right">
							<span class="glyphicon glyphicon-remove-circle"> </span> 取消
						</button>
					</p>
				</div>
			</div>
		</div>
	</div>
	<!-- 角色菜单配置 -->
	<div id="roleCont" class="content_wrap" style="display: none">
		<div class="zTreeDemoBackground left">
			<div style="position: fixed; top: 0; left: 0; z-index: 1000; width: 50%; margin: 7% 25%; background: #fff; border-bottom: 1px solid #ddd;">
				<p style="padding: 1% 0 0 2%; height: 2em; width: 50%; float: left; font-size: 1.3em;">角色菜单配置</p>
				<span class="glyphicon glyphicon-remove close closeRole "
					style="float: right; margin: 1% 1% 1% 0; cursor: pointer" id=""></span>
			</div>
			<ul id="treeDemo" class="ztree"></ul>

			<div style="position: fixed; top: 80%; width:50%; height: 50px; left: 0; background: #fff; border-top: 1px solid #ddd; margin-left: 25%;">
				<div style="width: 48%; margin-top: 9px; margin-left: 25%;">
					<p style="overflow: hidden; width: 60%; text-align: center; margin-left: 20%;">
						<button id="saveRole" type="button" class="btn btn-primary"
							style="float: left">
							<span class="glyphicon glyphicon-saved"> </span> 保存
						</button>
						<button id="cancleRole" type="button" class="btn btn-primary"
							style="float: right">
							<span class="glyphicon glyphicon-remove-circle"> </span> 取消
						</button>
					</p>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="/commonJSP/commonjs.jsp"></jsp:include>
	<script>
	$("#roleManagement").addClass("active");
	 var userName="<%=session.getAttribute("userName")%>"; 
//鼠标悬停在表头时的提示文字
	$(".directionTh").each(function(){
        $(this).mouseover(function(){
            $(this).children().eq(1).show();                    
        })
    }) 
    $(".directionTh").each(function(){
        $(this).mouseout(function(){
            $(this).children().eq(1).hide();
        })
    }) 
    
 
    /* 遮罩层 */
	$("#search").click(function(){
		$(".loadContainer").css("display","block")
	}) 


$(".closeRevise").click(function(){
	$("#proDiolagRevise").hide()
})
$("#cancleRevise").click(function(){
	$("#proDiolagRevise").hide()
})

/*添加  */
$(".closeAdd").click(function(){
	$("#proDiolagAdd").hide()
})
$("#cancleAdd").click(function(){
	$("#proDiolagAdd").hide()
})
/* 角色配置 */
$(".closeRole").click(function(){
	$("#roleCont").hide()
})

//角色配置的保存按钮
$("#saveRole").click(function(){
	if(confirm("真的要修改配置项吗？")){
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getCheckedNodes(true);
	if(nodes.length>0){
		//获取所有选中菜单的id
		var menuId=[];
	          for(var i=0;i<nodes.length;i++){
	        	  menuId.push(nodes[i].id);
	           }
	          menuId=menuId.join(",")
	}
	$.ajax({
		url:Main.contextPath+"/roleManagementController/updateMenuState.do",
		type : "post",
		data : {
			"ROLE_ID" : $('#datatable_col_reorder_roleManagement input[type="checkbox"]:checked').val(),
			"menuId":menuId
		},
		dataType : "json",
		success : function(result) {
			window.parent.existsUser(result);//判断用户是否已失效
			if(result[0].state==0){
			}else{
				alert("修改数据失败");
			}
		}		
	});
	$("#roleCont").hide();
	}
})


$("#cancleRole").click(function(){
	$("#roleCont").hide()
})

 function getDefaultTime(){
		
		var now = new Date(new Date().getTime());	
		var defaultTime = now.format("yyyy-mm-dd")+' '+'00:00';
		return defaultTime;
	}
	
	function getDefaultTime2(){
		
		var now = new Date(new Date().getTime());
		var defaultTime = now.format("yyyy-mm-dd' 'HH:MM");
		return defaultTime;
	}
	
</script>



	<%-- <script src="<%=request.getContextPath()%>/js/zTree/jquery-1.4.4.min.js" type="text/javascript"></script> --%>
	<script
		src="<%=request.getContextPath()%>/js/zTree/jquery.ztree.core-3.5.js"
		type="text/javascript"></script>
	<script
		src="<%=request.getContextPath()%>/js/zTree/jquery.ztree.excheck-3.5.js"
		type="text/javascript"></script>

</body>
</html>