<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-cn" style="background: #fff;">
<head>
    <meta charset="utf-8">
    <title>角色管理</title>    
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <jsp:include page="/commonJSP/comcss.jsp"></jsp:include>
    <script src="<%=request.getContextPath()%>/js/app/common.js"></script>
    <style>
   
    li{
    	list-style:none;
    }
    /* 遮罩层 */
    .loadContainer{
           	display:none;   
        	position: absolute;
            top: 0;
            left: 0;
            width:100%;
            height: 100%;
            z-index: 100;
        }
        .loadingMask{
            position: absolute;
            top: 0;
            left: 0;
            width:100%;
            height: 100%;
            background: #ddd;
            opacity: 0.6;
        }
        .loadingContainer{
        	position: absolute;
            top: 0;
            left: 0;
            width:100%;
            height: 100%;
        }
        .loading{
            margin-bottom: 0;
            width: 30%;
            height: 20px;
            top: 40%;
            left: 30%;
        }
        .progress-bar{
            background-color: #337ab7;
            background-image: linear-gradient(45deg,rgba(255,255,255,.15)25%,transparent 25%,transparent 50%,rgba(255,255,255,.15)50%,rgba(255,255,255,.15)75%,transparent 75%,transparent);
        }
            small{
        	font-size:1.5em;
        }
        
        a:hover{
        	text-decoration:underline;
        }
    	 /*说明文字*/
        .directionTh{
        	position:relative;
        }
        .directionDiv{
        	display:none;
        	position:absolute;
        	right:0;
        	bottom:100%;
        	width:200%;
        	height:300%;
        	background:#fafafa;
        	border:1px solid #ddd;
        	font-weight:normal;
        }
        
    </style>
</head>
<body style="background: #fff;">
	<script src="<%=request.getContextPath()%>/js/cookie_util.js"></script>
	<script src="<%=request.getContextPath()%>/js/libs/jquery-2.1.1.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.form.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/app/userAndRole/userManagement.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/utils/md5.js"></script>
<div id="main1" role="main">
    <!-- MAIN CONTENT -->
    <div id="content">
        <div style="position: relative;">          
            <ol class="breadcrumb">
                <li>权限管理</li>
                <li>用户管理</li>               
            </ol>           
        </div>
        
        <section id="widget-grid" class="">
			<div class="row">
				<article class="col-sm-12">                          
                    <div class="jarviswidget" id="wid-id-2" data-widget-togglebutton="false" data-widget-editbutton="false" data-widget-fullscreenbutton="false" data-widget-colorbutton="false" data-widget-deletebutton="false">
  		
		        		<div class="no-padding">
		        		<!-- 正在加载数据遮罩层  开始 -->
              				<div class="loadContainer"  id="loadingRoleManagent">
	               				<div class="loadingMask"></div>
	               					<div class="loadingContainer">
	               						<div class="progress loading">
	                           			<div class="progress-bar progress-bar-striped active" role="progressbar"  style="width: 100%">
	                           				正在加载中...
	                           			</div>
	                       			</div>
	               				</div>
               				</div> 
               			<!-- 正在加载数据遮罩层  结束 -->
		        			<div class="widget-body">
		        				<div id="myQualityContent" class="tab-content container-fluid">                                           
                                    <div class="row" style="padding-top: 1%;padding-left:1%;padding-bottom:0.5%;">                                               
                                        <form class="form-inline">
                                              <div class="form-group"  style="margin-right:1.5em">
	                                                <label for="organizationSelect">机构：</label>
	                                                <select id="organizationSelect" style="min-width:8em;height:25px">
	                                                </select>
	                                          </div>   
	                                          <div class="form-group"  style="margin-right:1.5em">
	                                                <label for="role">角色：</label>
	                                               	<input placeholder="请输入角色名称" type="text" id="role" style="height:25px"/>
	                                          </div>   
	                                          <div class="form-group"  style="margin-right:1.5em">
	                                                <label for="user">用户：</label>
	                                               	<input placeholder="请输入用户名称" type="text" id="user" style="height:25px"/>
	                                          </div>   
	                                          <div class="form-group">
	                                                <button type="button" class="btn btn-primary" id="search">
														<span class="glyphicon glyphicon-search"></span>                                                   	
                                                        	查询
													</button>
	                                          </div>   
				        				</form>
				        			</div>
                                    <div style="padding-top: 1%">	                                               
	                                        <div class="widget-body no-padding">
	                                            <table  id="datatable_col_reorder_roleManagement" class="table table-striped table-bordered table-hover">
	                                                <thead>
		                                                <tr>
		                                                 <th style="width:5%;"></th>
		                                                 <th data-class="expand" style="vertical-align: middle;width:3%;">序号</th>                                                        	
		                                                 <th style="text-align: center;vertical-align: middle" data-hide="phone,tablet">
			                                                	用户ID
		                                                                                                     	
		                                                	</th>
		                                                    <th data-class="phone" style="vertical-align: middle;">用户名</th>
		                                                    <th data-hide="phone" style="vertical-align: middle;">用户含义</th>
<!-- 		                                                <th style="vertical-align: middle;"  data-hide="phone,tablet;">密码</th>  -->
		                                                    <th style="vertical-align: middle;"  data-hide="phone,tablet;">角色</th>                                                                                                                       
		                                                    <th style="vertical-align: middle;"  data-hide="phone,tablet;">固定电话</th>                                                                                                                       
		                                                    <th style="vertical-align: middle;"  data-hide="phone,tablet;">移动电话</th>                                                                                                                       
		                                                    <th  style="text-align: center;vertical-align: middle" data-hide="phone,tablet">启用日期</th>                                                                                                                       
		                                                    <th style="vertical-align: middle;"  data-hide="phone,tablet;">失效日期</th>                                                                                                                       
		                                                    <th style="vertical-align: middle;"  data-hide="phone,tablet;">备注</th>                                                                                                                       
		                                                    <th  style="text-align: center;vertical-align: middle" data-hide="phone,tablet">
		                                                    	操作人
		                                                                                                       
		                                                    </th>                                                                                                                       
		                                                    <th style="text-align: center;vertical-align: middle" data-hide="phone,tablet">
		                                                    	添加时间
		                                                                                                         
		                                                    </th>                                                                                                                       
		                                                    <th style="text-align: center;vertical-align: middle" data-hide="phone,tablet">
		                                                    	更新时间
		                                                                                                          
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

<!-- 添加项 -->	
<div id="proDiolagAdd" style="z-index:100;display:none;position:fixed;width:100%;height:100%;top:0;left:0;background:rgba(0,0,0,0.5);">
	<div class="containerAdd1" style="background:#fff;width:50%;margin:7%  25% 2% 25%;padding-bottom:1%;height:65%;overflow:auto;">
		<div style="position: fixed;top: 0;left: 0;z-index:1000;width:50%;margin:7%  25%;background: #fff;border-bottom:1px solid #ddd;">
            	<p style="padding:1% 0 0 2%;height:2em;width:50%;float:left;font-size:1.3em;">添加用户信息</p>
			<span class="glyphicon glyphicon-remove close closeAdd " style="float:right;margin:1% 1% 1% 0;cursor:pointer" id=""></span>				
		</div>

		<div id="contianerDetailAdd" style="width:90%;margin:8% auto;padding-top:2em">
			<ul>
			<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">角色：</span>
						<select style="float:left;width:52%" id="alertRoleName">
						</select>
						<span class="txt" style="float:left;width:30%;margin-left:1%;font-size:.75em"></span>
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">用户名：</span>
						<input id="ueserNameAdd" style="float:left;width:52%" type="text" />

						<span style ="float:left;color:red;font-size:large" >*</span><span class="txt" id="ueserNameAddTxt" style="float:left;width:30%;margin-left:1%;font-size:.75em"> 由英文字母、数字、下划线组成</span>

					</p>
				</li>
				<li style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">用户含义：</span>
						<input id="userAlias" style="float:left;width:52%" type="text" value=""/>
						<span class="txt" style="float:left;width:30%;margin-left:1%;font-size:.75em"></span>
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">机构：</span>
						<select style="float:left;width:52%" id="alertOrg">
						</select>
						<span class="txt" style="float:left;width:30%;margin-left:1%;font-size:.75em"></span>
					</p>
				</li>
				<li style="min-height:25px;margin-bottom:10px">
					<div style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">密码：</span>
						
							<input  style="float:left;width:52%" id="paddwordRegAdd" type="password" value=""/>
												

						<span style ="float:left;color:red;font-size:large" >*</span><span  class="txt" id="paddAddTxt" style="float:left;width:30%;margin-left:1%;font-size:.75em">由英文字母、数字、下划线组成，不能少于6个字符。</span>

					</div>
				</li>
				<li style="min-height:25px;margin-bottom:10px">
					<div style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">确认密码：</span>
						
						<input id="passAgainAdd"  style="float:left;width:52%" type="password" value=""/>
							
						
						<span style ="float:left;color:red;font-size:large" >*</span><span  class="txt" id="passAgainAddTxt" style="float:left;width:30%;margin-left:1%;font-size:.75em"></span>
					</div>
				</li>
				<li style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">固定电话：</span>
						<input id="telAdd" style="float:left;width:52%" type="text" value=""/>
						<span class="txt" id="telAddTxt" style="float:left;width:30%;margin-left:1%;font-size:.75em"></span>
					</p>
				</li>
				<li style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">移动电话：</span>
						<input id="phoneAdd" style="float:left;width:52%" type="text" value=""/>
						<span class="txt" id="phoneAddTxt" style="float:left;width:30%;margin-left:1%;font-size:.75em"></span>
					</p>
				</li>
				<li style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">E-mail：</span>
						<input id="emailAdd" style="float:left;width:52%" type="text" value=""/>
						<span class="txt" id="emailAddTxt" style="float:left;width:30%;margin-left:1%;font-size:.75em"></span>
					</p>
				</li>
				<li style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">启用日期：</span>
						<input style="float:left;width:52%;padding-left:.2em" class="Wdate time" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'#F{$dp.$D(\'falseTime-0\',{m:1});}'})" id="startTime-0">

						<span style ="float:left;color:red;font-size:large" >*</span><span style="float:left;width:30%;margin-left:1%;font-size:.75em"></span>

					</p>
				</li>
				<li style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">失效日期：</span>
						<input style="float:left;width:52%;padding-left:.2em" class="Wdate time" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'startTime-0\',{m:1});}'})" id="falseTime-0">

						<span style ="float:left;color:red;font-size:large" >*</span><span style="float:left;width:30%;margin-left:1%;font-size:.75em"></span>

					</p>
				</li>
				<li>
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">备注：</span>
						<textarea style="float:left;width:52%;overflow:hidden;min-height:160px" id="remarkContent"></textarea>
						<span class="txt" style="float:left;width:30%;margin-left:1%;font-size:.75em"></span>
					</p>
				</li>
				
				
			</ul>
		
		</div>
		<div style="position:fixed;top: 75%;width: 48%;height:45px;z-index:1000;background: #fff;left: 25%;">
			<div style="width:60%;margin-top:9px;margin-left: 20%;">
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
				
	</div>
</div>
<!-- 修改 -->
<div id="proDiolagRevise" style="z-index:100;display:none;position:fixed;width:100%;height:100%;top:0;left:0;background:rgba(0,0,0,0.5);">
	<div class="containerAdd1" style="position:relative;background:#fff;width:50%;margin:7%  25% 2% 25%;padding-bottom:1%;height:65%;overflow:auto;">
		<div style="position: fixed;top: 0;left: 0;z-index:1000;width:50%;margin:7%  25%;background: #fff;border-bottom:1px solid #ddd;">
            	<p style="padding:1% 0 0 2%;height:2em;width:50%;float:left;font-size:1.3em;">修改用户信息</p>
			<span class="glyphicon glyphicon-remove close closeRevise" style="float:right;margin:1% 1% 1% 0;cursor:pointer" id=""></span>				
		</div>

		<div id="contianerDetailRevise" style="width:90%;margin:8% auto;padding-top:2em">
			<ul>
				<li style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">角色：</span>
						<select style="float:left;width:52%" id="modifyAlertOrg">
						</select>
						<span class="txt" style="float:left;width:30%;margin-left:1%;font-size:.75em"></span>
					</p>
				</li>
				<li style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">用户名：</span>
						<input id="ueserName" style="float:left;width:52%" type="text" value=""/>

						<span style ="float:left;color:red;font-size:large" >*</span><span  class="txt" id="ueserNameTxt" style="float:left;width:30%;margin-left:1%;font-size:.75em">由英文字母、数字、下划线组成</span>

					</p>
				</li>
				<li style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">用户含义：</span>
						<input style="float:left;width:52%" type="text" value="" id="modifyUserAlisa"/>
						<span class="txt" style="float:left;width:30%;margin-left:1%;font-size:.75em"></span>
					</p>
				</li>
				<li style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">机构：</span>
						<select style="float:left;width:52%" id="modifyOrg">
						</select>
						<span class="txt" style="float:left;width:30%;margin-left:1%;font-size:.75em"></span>
					</p>
				</li>
				<li style="min-height:25px;margin-bottom:10px">
					<div style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">密码：</span>
						
						<input id="paddwordReg" disabled=true style="float:left;width:52%;opacity:0.7" type="password" value=""/>
							
						<button id="btnRevise" style="float:left;margin-left:1%;width:10%;text-align:center;font-size:1em">修改密码</button>						


						<span style ="float:left;color:red;font-size:large" >*</span><span  class="txt" id="paddTxt" style="float:left;width:30%;margin-left:1%;font-size:.75em">

						</span>
					</div>
				</li>
				<li id="againContainer" style="min-height:25px;margin-bottom:10px;display:none">
					<div style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">确认密码：</span>
						
						<input id="passAgain"  style="float:left;width:52%" type="password" value=""/>
						
						<span class="txt"  id="passAgainTxt" style="float:left;width:30%;margin-left:1%;font-size:.75em"></span>
					
					</div>
				</li>
				<li style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">固定电话：</span>
						<input id="tel" style="float:left;width:52%" type="text" value=""/>
						<span class="txt" id="telTxt" style="float:left;width:30%;margin-left:1%;font-size:.75em"></span>
					</p>
				</li>
				<li style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">移动电话：</span>
						<input id="phone" style="float:left;width:52%" type="text" value=""/>
						<span class="txt" id="phoneTxt" style="float:left;width:30%;margin-left:1%;font-size:.75em"></span>
					</p>
				</li>
				<li style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">E-mail：</span>
						<input id="email" style="float:left;width:52%" type="text" value=""/>
						<span class="txt" id="emailTxt" style="float:left;width:30%;margin-left:1%;font-size:.75em"></span>
					</p>
				</li>
				<li style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">启用日期：</span>
						<input style="float:left;width:52%;padding-left:.2em" class="Wdate time" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'#F{$dp.$D(\'falseTime-1\',{m:1});}'})" id="startTime-1">

						<span style ="float:left;color:red;font-size:large" >*</span><span style="float:left;width:30%;margin-left:1%;font-size:.75em"></span>

					</p>
				</li>
				<li style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">失效日期：</span>
						<input style="float:left;width:52%;padding-left:.2em" class="Wdate time" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'startTime-1\',{m:1});}'})" id="falseTime-1">

					<span style ="float:left;color:red;font-size:large" >*</span><span style="float:left;width:30%;margin-left:1%;font-size:.75em"></span>

					</p>
				</li>
				<li>
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:15%;text-align:right">备注：</span>
						<textarea style="float:left;width:52%;overflow:hidden;min-height:160px" id="modifyRemark"></textarea>
						<span class="txt" style="float:left;width:30%;margin-left:1%;font-size:.75em"></span>
					</p>
				</li>
				
			</ul>
		
		</div>
		<div style="position:fixed;top: 75%;width: 48%;height:45px;left:0;z-index:1000;background: #fff;left: 25%;">
			<div style="width:60%;margin-top:9px;margin-left: 20%;">
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
	</div>
</div>
<jsp:include page="/commonJSP/commonjs.jsp"></jsp:include>


<script>
    var operator="<%=session.getAttribute("userName")%>"; 
	$("#roleManagement").addClass("active");
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
    

/* 修改 */
$("#revise").unbind('click').click(function(){
	 $(".txt").text("");
	 $("#paddAddTxt").text("不能少于6个字符(只能包含英文字母、数字、下划线)。").css("color","#333");
	 $("#ueserNameAddTxt").text("由英文字母、数字、下划线组成。").css("color","#333");
	
	 $("#ueserNameTxt").text("由英文字母、数字、下划线组成。").css("color","#333");
	$("#againContainer").hide();
		if($('input[type="checkbox"]:checked').length==0){
			alert("您没有选中任何选项！请选择...");
		}
		else if($('input[type="checkbox"]:checked').length>1){
			alert("你选择的选项过多，请您选择需要修改的一项");
		}else{			
			$("#proDiolagRevise").show();
			$("#proDiolagAdd").hide();			
		}
	});
    

$(".closeRevise").click(function(){
	$("#proDiolagRevise").hide()
})

$("#cancleRevise").click(function(){
	$("#proDiolagRevise").hide()
})

/*添加  */
//控制添加的格式错误
var addSure = true;
var addMimaSure = true;

$(".closeAdd").click(function(){
	$("#proDiolagAdd").hide();
	$("#proDiolagAdd").find("input").val("");
})
$("#cancleAdd").click(function(){
	$("#proDiolagAdd").hide();
	$("#proDiolagAdd").find("input").val("");
})
/*添加验证  */
/*密码验证  */ 
$("#paddwordRegAdd").blur(function(){
	var val=$("#paddwordRegAdd").val()
	var reg = /^\w{6,}$/;
	if(!reg.test(val)){
		$("#paddAddTxt").text("格式有误").css("color","red");
		addMimaSure = false;
	}else{
		$("#paddAddTxt").text("验证通过").css("color","green");
		addMimaSure = true;
	}
})
$("#paddwordRegAdd").focus(function(){
	$("#paddAddTxt").text("").css("color","#333");
})
/* 确认密码 */
 
 $("#passAgainAdd").blur(function(){
		var val=$("#passAgainAdd").val()
		var reg = $("#paddwordRegAdd").val();
		if(val!=reg){
			$("#passAgainAddTxt").text("两次输入不一致").css("color","red");
			addSure = false;
		}else if(val==""){
			$("#passAgainAddTxt").text("格式有误").css("color","red")
			addSure = false;
		}else{
			$("#passAgainAddTxt").text("密码正确").css("color","green");
			addSure = true;
		}
	})
	$("#passAgainAdd").focus(function(){
		$("#passAgainAddTxt").text("").css("color","#333");
	})
	
/* 用户名验证 */
$("#ueserNameAdd").focus(function(){
	$("#ueserNameAddTxt").text("").css("color","#333");
})
/* 固定电话验证 */ 
 $("#telAdd").blur(function(){
		var val=$("#telAdd").val()
		var reg2 = /^($$\d{3,4}-)|(\d{3,4}-)\d{7,8}$/;
		if(val ==""){
			$("#telAddTxt").text("验证通过").css("color","green");
			addSure = true;
		}else{	
			if(!reg2.test(val)){
				$("#telAddTxt").text("格式有误").css("color","red");
				addSure = false;
			}else{
				$("#telAddTxt").text("验证通过").css("color","green");
				addSure = true;
			}
		}
	})
	$("#telAdd").focus(function(){
		$("#telAddTxt").text("").css("color","#333");
	})
/* 移动电话验证 */	
$("#phoneAdd").blur(function(){
		var val=$("#phoneAdd").val()
		var reg3 = /^1[3,4,5,7,8]\d{9}$/;
		if(val ==""){
			$("#phoneAddTxt").text("验证通过").css("color","green");
			addSure = true;
		}else{
			if(!reg3.test(val)){
				$("#phoneAddTxt").text("格式有误").css("color","red");
				addSure = false;
			}else{
				$("#phoneAddTxt").text("验证通过").css("color","green");
				addSure = true;
			}
		}
		
	})
	$("#phoneAdd").focus(function(){
		$("#phoneAddTxt").text("").css("color","#333");
	})	
/*E-mail验证  */	
$("#emailAdd").blur(function(){
		var val=$("#emailAdd").val()
		var reg4 = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		if(val ==""){
			$("#emailAddTxt").text("验证通过").css("color","green");
			addSure = true;
		}else{
			if(!reg4.test(val)){
				$("#emailAddTxt").text("格式有误").css("color","red");
				addSure = false;
			}else{
				$("#emailAddTxt").text("验证通过").css("color","green");
				addSure = true;
			}
		}
		
	})
	$("#emailAdd").focus(function(){
		$("#emailAddTxt").text("").css("color","#333");
	})	

/*修改验证 */
/*密码验证  */ 
var modifySure = true;
var modifyMimaSure = true;
$("#paddwordReg").blur(function(){
	var val=$("#paddwordReg").val()
	var reg = /^\w{6,}$/;
	if(!reg.test(val)){
		$("#paddTxt").text("格式有误").css("color","red");
		modifyMimaSure = false;
	}else{
		$("#paddTxt").text("验证通过").css("color","green");
		modifyMimaSure = true;
	}
})
$("#paddwordReg").focus(function(){
	$("#paddTxt").text("").css("color","#333");
})
/* 确认密码 */
 
 $("#passAgain").blur(function(){
		var val=$("#passAgain").val()
		var reg = $("#paddwordReg").val();
		if(val!=reg){
			$("#passAgainTxt").text("两次输入不一致").css("color","red");
			modifySure = false;
		}else if(val==""){
			$("#passAgainTxt").text("格式有误").css("color","red")
			modifySure = false;
		}else{
			$("#passAgainTxt").text("密码正确").css("color","green");
			modifySure = true;
		}
	})
	$("#passAgain").focus(function(){
		$("#passAgainTxt").text("").css("color","#333");
	})
$("#btnRevise").click(function(){
	$("#paddwordReg").attr("disabled",false);
	$("#againContainer").show();
})	
/* 用户名验证 */
$("#ueserName").focus(function(){
	$("#ueserNameTxt").text("").css("color","#333");
})
/* 固定电话验证 */ 
 $("#tel").blur(function(){
		var val=$("#tel").val()
		var reg2 = /^($$\d{3,4}-)|(\d{3,4}-)\d{7,8}$/;
		if(val ==""){
			$("#telTxt").text("验证通过").css("color","green");
			modifySure = true;
		}else{
			if(!reg2.test(val)){
				$("#telTxt").text("格式有误").css("color","red");
				modifySure = false;
			}else{
				$("#telTxt").text("验证通过").css("color","green");
				modifySure = true;
			}
		}
	})
	$("#tel").focus(function(){
		$("#telTxt").text("").css("color","#333");
	})
/* 移动电话验证 */	
$("#phone").blur(function(){
		var val=$("#phone").val()
		var reg3 = /^1[3,4,5,7,8]\d{9}$/;
		if(val == ""){
			$("#phoneTxt").text("验证通过").css("color","green");
			modifySure = true;
		}else{
			if(!reg3.test(val)){
				$("#phoneTxt").text("格式有误").css("color","red");
				modifySure = false;
			}else{
				$("#phoneTxt").text("验证通过").css("color","green");
				modifySure = true;
			}
		}
	})
	$("#phone").focus(function(){
		$("#phoneTxt").text("").css("color","#333");
	})	
/*E-mail验证  */	

$("#email").blur(function(){
		var val=$("#email").val()
		var reg4 = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		if(val == ""){
			$("#emailTxt").text("验证通过").css("color","green");
			modifySure = true;
		}else{
			if(!reg4.test(val)){
				$("#emailTxt").text("格式有误").css("color","red");
				modifySure = false;
			}else{
				$("#emailTxt").text("验证通过").css("color","green");
				modifySure = true;
			}
		}
	})
	$("#email").focus(function(){
		$("#emailTxt").text("").css("color","#333");
	})	
 $(function(){
	 userManager();
 });	
/* 时间 
var time = new Date();
var nowStr = time.format("yyyy-mm-dd' 'HH:MM:ss");
document.getElementById("startTime-0").value=nowStr;
*/
	    
</script>
<%-- <script src="<%=request.getContextPath()%>/js/jquery-2.0.2.min.js" type="text/javascript"></script>
<script>

function showPassAdd(){
	$("#paddwordRegAdd").attr("type","text");		
}
function hidePassAdd(){
	$("#paddwordRegAdd").attr("type","password");		
}
function showPassAddAgain(){
	$("#passAgainAdd").attr("type","text");		
}
function hidePassAddAgain(){
	$("#passAgainAdd").attr("type","password");		
}
function showPass(){
	$("#paddwordReg").attr("type","text");	
}
function hidePass(){
	$("#paddwordReg").attr("type","password");	
}
function showPassAgain(){
	$("#passAgain").attr("type","text");	
}
function hidePassAgain(){
	$("#passAgain").attr("type","password");	
}

var c1Add=0;
 $("#showPassAdd").click(function(){
	 c1Add+=1;
	 if(c1Add%2==0){
		 hidePassAdd()
	 }else{
		 showPassAdd()
	 }	
 })
var c1AddAgain=0;
 $("#showPassAddAgain").click(function(){
	 c1AddAgain+=1;
	 if(c1AddAgain%2==0){
		 hidePassAddAgain()
	 }else{
		 showPassAddAgain()
	 }	
 })
var c1=0;
 $("#showPass").click(function(){
	 c1+=1;
	 if(c1%2==0){
		 hidePass()
	 }else{
		 showPass()
	 }	
 })
var c1Again=0;
 $("#showPassAgain").click(function(){
	 c1Again+=1;
	 if(c1Again%2==0){
		 hidePassAgain()
	 }else{
		 showPassAgain()
	 }	
 })
 
</script>
 --%>
</body>
</html>