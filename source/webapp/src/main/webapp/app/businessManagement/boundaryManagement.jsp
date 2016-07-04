<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-cn" style="background: #fff;">
<head>
    <meta charset="utf-8">
    <title>工程管理</title>    
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <jsp:include page="/commonJSP/comcss.jsp"></jsp:include>
	<script src="<%=request.getContextPath()%>/js/app/common.js"></script>
	
	<style type="text/css">
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
	<script src="<%=request.getContextPath()%>/js/app/businessManage/boundaryManagement.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/utils/md5.js"></script>
<div id="main1" role="main">
    <!-- MAIN CONTENT -->
    <div id="content">
        <div style="position: relative;">
				<ol class="breadcrumb">
					<li>业务管理</li>
					<li>边线管理</li>
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
	                                                <label for="divlinename">边线名称：</label>
	                                               	<input placeholder="请输入边线名称" type="text" id="divlinename" style="height:25px"/>
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
		                                                 <th style="width:4%;"></th>
		                                                 <th style="vertical-align: middle;width:5%;" data-class="expand" >序号</th>                                                        	
		                                                 <th style="vertical-align: middle;"data-class="phone">边线名称</th>
		                                                 <th style="vertical-align: middle;"data-hide="phone" >标段</th>
		                                                 <th style="vertical-align: middle;"data-hide="phone" >线型</th>
		                                                 <th style="vertical-align: middle;"data-hide="phone" >父路线</th>
		                                                 <th style="vertical-align: middle;"data-hide="phone" >中心路线</th>
		                                                 <th style="vertical-align: middle;"data-hide="phone" >中线坐标X</th>
		                                                 <th style="vertical-align: middle;"data-hide="phone" >中线坐标Y</th>
		                                                 <th style="vertical-align: middle;"data-hide="phone" >中线坐标Z</th>
		                                                 <th style="vertical-align: middle;"data-hide="phone" >半径</th>
		                                                 <th style="vertical-align: middle;"data-hide="phone" >经度</th>
		                                                 <th style="vertical-align: middle;"data-hide="phone" >纬度</th>
		                                                 <th style="vertical-align: middle;"data-hide="phone" >高程</th>
		                                                 <th style="vertical-align: middle;"data-hide="phone" >桩号</th>
		                                                 <th style="vertical-align: middle" data-hide="phone,tablet">添加时间</th>                                                                                                                       
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
            	<p style="padding:1% 0 0 2%;height:2em;width:50%;float:left;font-size:1.3em;">添加边线信息</p>
			<span class="glyphicon glyphicon-remove close closeAdd " style="float:right;margin:1% 1% 1% 0;cursor:pointer" id=""></span>				
		</div>

		<div id="contianerDetailAdd" style="width:90%;margin:8% auto;padding-top:2em;">
			<ul>
			    <li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">边线名称：</span>
						<input id="linename" style="float:left;width:52%" type="text" />
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">标段：</span>
						<select style="float:left;width:52%" id="sid">
						</select>
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">线型：</span>
						<select style="float:left;width:52%" id="lineType">
						</select>
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">父路线：</span>
						<select style="float:left;width:52%" id="parentId">
						</select>
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">中心线路线：</span>
						<select style="float:left;width:52%" id="lid">
						</select>
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">中线坐标X：</span>
						<input id="mLineX" style="float:left;width:52%" type="text" />
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">中线坐标Y：</span>
						<input id="mLineY" style="float:left;width:52%" type="text" />
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">中线坐标Z：</span>
						<input id="mLineZ" style="float:left;width:52%" type="text" />
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">半径：</span>
						<input id="radius" style="float:left;width:52%" type="text" />
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">经度：</span>
						<input id="longitude" style="float:left;width:52%" type="text" />
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">纬度：</span>
						<input id="latitude" style="float:left;width:52%" type="text" />
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">高程：</span>
						<input id="elevation" style="float:left;width:52%" type="text" />
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">桩号：</span>
						<input id="stNum" style="float:left;width:52%" type="text" />
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
            	<p style="padding:1% 0 0 2%;height:2em;width:50%;float:left;font-size:1.3em;">修改边线信息</p>
			<span class="glyphicon glyphicon-remove close closeRevise" style="float:right;margin:1% 1% 1% 0;cursor:pointer" id=""></span>				
		</div>

		<div id="contianerDetailRevise" style="width:90%;margin:8% auto;padding-top:2em">
			<ul>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">边线名称：</span>
						<input id="linename" style="float:left;width:52%" type="text" />
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">标段：</span>
						<select style="float:left;width:52%" id="sid">
						</select>
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">线型：</span>
						<select style="float:left;width:52%" id="lineType">
						</select>
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">父路线：</span>
						<select style="float:left;width:52%" id="parentId">
						</select>
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">中心线路线：</span>
						<select style="float:left;width:52%" id="lid">
						</select>
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">中线坐标X：</span>
						<input id="mLineX" style="float:left;width:52%" type="text" />
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">中线坐标Y：</span>
						<input id="mLineY" style="float:left;width:52%" type="text" />
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">中线坐标Z：</span>
						<input id="mLineZ" style="float:left;width:52%" type="text" />
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">半径：</span>
						<input id="radius" style="float:left;width:52%" type="text" />
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">经度：</span>
						<input id="longitude" style="float:left;width:52%" type="text" />
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">纬度：</span>
						<input id="latitude" style="float:left;width:52%" type="text" />
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">高程：</span>
						<input id="elevation" style="float:left;width:52%" type="text" />
					</p>
				</li>
				<li  style="min-height:25px;margin-bottom:10px">
					<p style="overflow:hidden;width:100%;">
						<span style="float:left;width:18%;text-align:right">桩号：</span>
						<input id="stNum" style="float:left;width:52%" type="text" />
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
    });
 
$(".closeRevise").click(function(){
	$("#proDiolagRevise").hide()
})

$("#cancleRevise").click(function(){
	$("#proDiolagRevise").hide()
})


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
 $(function(){
	 initManager();
 });

</script>
</body>
</html>