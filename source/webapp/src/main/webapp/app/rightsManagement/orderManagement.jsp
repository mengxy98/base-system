<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-cn" style="background: #fff;">
<head>
    <meta charset="utf-8">
    <title>菜单管理</title>    
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <jsp:include page="/commonJSP/comcss.jsp"></jsp:include>
    <script src="<%=request.getContextPath()%>/js/app/common.js"></script>
   
    <link rel="stylesheet"  type="text/css" href="<%=request.getContextPath()%>/css/zTreeStyle/zTreeStyle.css"/>
  <style>
  	li{
  	list-style:none;
  	}
  </style>
</head>
<body style="background:#fff">
	<jsp:include page="/commonJSP/headerAndNav.jsp"></jsp:include>
	<script src="<%=request.getContextPath()%>/js/cookie_util.js"></script>
	<script src="<%=request.getContextPath()%>/js/libs/jquery-2.1.1.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.form.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/app/userAndRole/menuManagement.js"type="text/javascript"></script>
	
	
	
<div id="main1" role="main">
    <!-- MAIN CONTENT -->
    <div id="content">
        <div style="position: relative;">          
            <ol class="breadcrumb">
                <li>权限管理</li>
                <li>菜单管理</li>               
            </ol>           
        </div>
          <section id="widget-grid" class="">
			<div class="row">
				<article class="col-sm-12">                          
                  
		        		<div class="no-padding">		        		 
	        			<div class="row" style="padding: 1%;border-top:1px solid #aaa;">                                                
                               <form class="form-inline">
                                	<div class="form-group">
                                       <button type="button" class="btn btn-primary" id="add">
                                       	<span class="fa fa-plus"> </span>
                                       	添加
                                       </button>
                                   </div>
                                   <div class="form-group">
                                       <button type="button" class="btn btn-primary" id="revise">
                                            <span class="glyphicon glyphicon-pencil"> </span>
                                             	修改
                                        </button>
                                   </div>                                              
                                   <div class="form-group">
                                       <button type="button" class="btn btn-primary" id="remove">
                                       	<span class="fa fa-trash-o"> </span>
                                       	删除
                                       </button>
                                   </div>
                                   <div class="form-group">
                                       <button type="button" class="btn btn-primary" id="refresh">
                                       	<span class="glyphicon glyphicon-refresh"> </span>
                                       	刷新
                                       </button>
                                   </div>
                                                                           											                                                    
                               </form>
                          </div>                               		        			
		        		</div>		        			        	
		        </article>
		        <div id="orderCont" class="content_wrap" style="margin-left:1%;">
					<div class="zTreeDemoBackground left">
						
						<ul id="treeDemo" class="ztree" style="margin-top: 7%;"></ul>
						
					</div>
				</div>
	        </div>
        </section>
      </div>
</div>
<!-- 添加权限 -->	
<div id="proDiolagAdd" style="z-index:100;display:none;position:fixed;width:100%;height:100%;top:0;left:0;background:rgba(0,0,0,0.5);">
	<div class="containerAdd" style="background:#fff;width:50%;margin:7%  25% 2% 25%;padding-bottom:1%;height:65%;overflow:auto;">
		<div style="position: fixed;top: 0;left: 0;z-index:1000;width:50%;margin:7%  25%;background: #fff;border-bottom:1px solid #ddd;">
            	<p style="padding:1% 0 0 2%;height:2em;width:50%;float:left;font-size:1.3em;">添加</p>
			<span class="glyphicon glyphicon-remove close closeAdd" style="float:right;margin:1% 1% 1% 0;cursor:pointer" id=""></span>				
		</div>
		<form style="width:80%;margin:10% auto">
			<ul style="width:100%">
				<li class="row">
					<label class="col-md-3 col-sm-3 control-label" style="text-align:right;padding-top:7px">父菜单ID:</label>
					<input id="parentIdAdd" class="col-md-8 col-sm-8" type="text" readonly="readonly"  value="" /> 
				</li>
				<li class="row">
					<label class="col-md-3 col-sm-3 control-label" style="text-align:right;padding-top:7px">菜单名称:</label>
					<input id="menuNameAdd" class="col-md-8 col-sm-8" type="text" value=""/>
				</li>
				<li class="row">
					<label class="col-md-3 col-sm-3 control-label" style="text-align:right;padding-top:7px">是否叶子结点:</label>
					<select  id="leafAdd" class="col-md-8 col-sm-8">
						<option value="false">否</option>
						<option value="true">是</option>
					</select>
				</li>
				<li class="row">
					<label class="col-md-3 col-sm-3 control-label" style="text-align:right;padding-top:7px">禁用:</label>
					<select id="disabledAdd"  class="col-md-8 col-sm-8">
						<option value="false">否</option>
						<option value="true">是</option>
						
					</select>
				</li>
				<li class="row">
					<label class="col-md-3 col-sm-3 control-label" style="text-align:right;padding-top:7px">连接目标:</label>
					<input id="hrefAdd" class="col-md-8 col-sm-8" type="text" value=""/>
				</li>
				<li class="row">
					<label class="col-md-3 col-sm-3 control-label" style="text-align:right;padding-top:7px">可见:</label>
					<select id="visibilityAdd" class="col-md-8 col-sm-8">
				     	<option  value="true">是</option> 
						<option value="false">否</option>
					</select>
				</li>
				<li class="row">
					<label class="col-md-3 col-sm-3 control-label" style="text-align:right;padding-top:7px">排序:</label>
					<input id="sortAdd" class="col-md-8 col-sm-8" type="text" value=""/>
				</li>
				<li class="row">
					<label class="col-md-3 col-sm-3 control-label" style="text-align:right;padding-top:7px">菜单类型:</label>
					<input id="typeAdd" class="col-md-8 col-sm-8" type="text" readonly="readonly" value="tree"/>
				</li>				
			</ul>
								
			<div style="position:fixed;top: 75%;width: 48%;z-index:1000;height:45px;background: #fff;left: 25%;">
			<div style="width:48%;margin-top:9px;margin-left: 25%;">
				<p style="overflow:hidden;width: 60%;text-align:center;margin-left: 20%;">
				<button id="saveAdd" type="button" class="btn btn-primary"  style="float:left">
					<span class="glyphicon glyphicon-saved"> </span>
					保存
				</button>
				<button id="cancleAdd" type="button" class="btn btn-primary"  style="float:right">
					<span class="glyphicon glyphicon-remove-circle"> </span>
					取消
				</button>
				</p>
			</div>
		</div>						
		</form>
	</div>
</div>
	
<!-- 修改权限 -->	
<div id="proDiolagRevise" style="z-index:100;display:none;position:fixed;width:100%;height:100%;top:0;left:0;background:rgba(0,0,0,0.5);">
	<div class="containerRevise" style="background:#fff;width:50%;margin:7%  25% 2% 25%;padding-bottom:1%;height:65%;overflow:auto;">
		<div style="position: fixed;top: 0;left: 0;z-index:1000;width:50%;margin:7%  25%;background: #fff;border-bottom:1px solid #ddd;">
            	<p style="padding:1% 0 0 2%;height:2em;width:50%;float:left;font-size:1.3em;">修改</p>
			<span class="glyphicon glyphicon-remove close closeRevise" style="float:right;margin:1% 1% 1% 0;cursor:pointer" id=""></span>				
		</div>
		<form style="width:80%;margin:10% auto">
			<ul style="width:100%">
				<li class="row">
					<label class="col-md-3 col-sm-3 control-label" style="text-align:right;padding-top:7px">父菜单I:</label>
				<input id="parentIdUpdate"  class="col-md-8 col-sm-8" type="text" readonly="readonly"  value="" /> 
				</li>
				<li class="row">
					<label class="col-md-3 col-sm-3 control-label" style="text-align:right;padding-top:7px">菜单名称:</label>
					<input id="menuNameUpdate" class="col-md-8 col-sm-8" type="text" value=""/>
				</li>
				<li class="row">
					<label class="col-md-3 col-sm-3 control-label" style="text-align:right;padding-top:7px">是否叶子结点:</label>
					<select id="leafUpdate"  class="col-md-8 col-sm-8">
						<option value="false">否</option>
						<option value="true">是</option>
						
					</select>
				</li>
				<li class="row">
					<label class="col-md-3 col-sm-3 control-label" style="text-align:right;padding-top:7px">禁用:</label>
					<select id="disabledUpdate"  class="col-md-8 col-sm-8">
						<option value="false">否</option>
						<option value="true">是</option>
						
					</select>
				</li>
				<li class="row">
					<label class="col-md-3 col-sm-3 control-label" style="text-align:right;padding-top:7px">连接目标:</label>
					<input  id="hrefUpdate" class="col-md-8 col-sm-8" type="text" value=""/>
				</li>
				<li class="row">
					<label class="col-md-3 col-sm-3 control-label" style="text-align:right;padding-top:7px">可见:</label>
					<select id="visibilityUpdate"  class="col-md-8 col-sm-8">
						<option value="false">否</option>
						<option value="true">是</option>
						
					</select>
				</li>
				<li class="row">
					<label class="col-md-3 col-sm-3 control-label" style="text-align:right;padding-top:7px">排序:</label>
					<input id="sortUpdate" class="col-md-8 col-sm-8" type="text" value=""/>
				</li>
				<li class="row">
					<label class="col-md-3 col-sm-3 control-label" style="text-align:right;padding-top:7px">菜单类型:</label>
					<input  id="typeUpdate" class="col-md-8 col-sm-8" type="text" readonly="readonly" value="tree"/>
				</li>				
			</ul>
								
			<div style="position:fixed;top: 75%;width: 48%;z-index:1000;height:45px;background: #fff;left: 25%;">
			<div style="width:48%;margin-top:9px;margin-left: 25%;">
				<p style="overflow:hidden;width: 60%;text-align:center;margin-left: 20%;">
				<button id="saveRevise" type="button" class="btn btn-primary"  style="float:left">
					<span class="glyphicon glyphicon-saved"> </span>
					保存
				</button>
				<button id="cancleRevise" type="button" class="btn btn-primary"  style="float:right">
					<span class="glyphicon glyphicon-remove-circle"> </span>
					取消
				</button>
				</p>
			</div>
		</div>						
		</form>
	</div>
</div>	
</body>
<jsp:include page="/commonJSP/commonjs.jsp"></jsp:include>
<script>
$("#orderManagement").addClass("active");



$("#add").click(function(){
	$("#menuNameAdd").val("");
	$("#hrefAdd").val("");
	$("#leafAdd option").eq(0).attr("selected",true);
	$("#disabledAdd option").eq(0).attr("selected",true);
	$("#visibilityAdd option").eq(0).attr("selected",true);
	
	 if($(".level1 .curSelectedNode").length==0||$(".level1 .curSelectedNode").length===null){
		 $("#parentIdAdd").val("菜单")
		  $("#parentIdAdd").data("pid",4);
			$("#proDiolagAdd").show();
			$("#proDiolagRevise").hide();
	}else  if($(".level1 .curSelectedNode").length>1){
		alert("您选择的选项过多");
	}else{
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getSelectedNodes()[0];
		 $("#parentIdAdd").val(nodes.name);
		 $("#parentIdAdd").data("pid",nodes.id);
		 $("#proDiolagAdd").show();
		 $("#proDiolagRevise").hide();
	} 

});



$(".closeAdd").click(function(){
	$("#proDiolagAdd").hide();
})
$(".closeRevise").click(function(){
	$("#proDiolagRevise").hide();
})

$("#cancleRevise").click(function(){
	$("#proDiolagRevise").hide();
})

$("#cancleAdd").click(function(){
	$("#proDiolagAdd").hide();
})
</script>
<script src="<%=request.getContextPath()%>/js/zTree/jquery.ztree.core-3.5.js" type="text/javascript"></script>
</html>