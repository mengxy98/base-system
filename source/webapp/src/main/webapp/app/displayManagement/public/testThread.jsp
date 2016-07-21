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
	<script src="<%=request.getContextPath()%>/js/libs/jquery-2.1.1.min.js" type="text/javascript"></script>
	<style>
            #panel{
                width:650px;
                height:600px;
            }
            #statusPanel{
                width: 150px;
                float: right;
                height: 500px;
                background: #5F81D6;
                color:#FFF;
            }
            .status{
                padding:7px 7px;
            }
        </style>
        
        <script>
        	window.onload=function(){
        		//getData();
        	}
        </script>
    </head>
    <body>
     	<div>
            <input type="button" id="start" value="开始">
            <input type="button" id="stop" value="停止">
            <input type="button" id="enlarge" value="放大">
            <input type="button" id="lessen" value="缩小">
            <input type="button" id="reset" value="重置">
            <input type="button" id="estage" value="扩大舞台大小">
            <input type="button" onclick="testData()" value="初始化测试数据">
            <input type="button" id="testDat" value="***">
        </div>
        <div style="position:relative;width:100%;height:100%;overflow:hidden;">
            <div id="statusPanel">
                <div class="status">速度：<span id="speed">0</span> km/h</div>
                <div class="status">CMV：<span id="cmv">0</span></div>
                <div class="status">卫星状态: <span id="sst">_</span></div>
                <div class="status">坐标： <span id="cood"></span></div>
                <div class="status">遍数:<span id="times">0</span></div>
                <div class="status">频率: <span id="frequency">0</span></div>
                <div class="status">卫星时间: <span id="stime">0</span></div>
                <div class="status">方向信息: <span id="dire">0</span></div>
            </div>
            <div id="panel">
            </div>
        </div>
       
        <script src="<%=request.getContextPath()%>/app/displayManagement/public/js/satelliteNew.js"></script>
        <script src="<%=request.getContextPath()%>/app/displayManagement/public/js/data.js"></script>
        <script src="<%=request.getContextPath()%>/app/displayManagement/public/js/Concurrent.Thread.js"></script>
       
        <script>
            var satellite = new Satellite({
                panel:'#panel',
                statusChange:function(status){
                    document.getElementById('speed').innerHTML = status.speed;
                    document.getElementById('cmv').innerHTML = status.CMV;
                    document.getElementById('sst').innerHTML = status.satelliteStatus;
                    document.getElementById('cood').innerHTML = 'x : '+status.cood.x+'<br>y : '+status.cood.y+'<br>h : '+status.cood.h;
                    document.getElementById('times').innerHTML = status.times;
                    document.getElementById('frequency').innerHTML = status.frequency;
                    document.getElementById('stime').innerHTML = status.satelliteTime;
                    if(status.direction){
                        document.getElementById('dire').innerHTML = '北:'+status.direction.n + '<br> 南：' + status.direction.s+
                                                                '<br>西:'+status.direction.s + '<br> 东：' + status.direction.e+
                                                                '<br>角度: '+parseInt(status.direction.angle) + '<br> 弧度： ' +
                                                                parseInt(status.direction.radian*1000)/1000 +
                                                                '<br>偏离角度: '+parseInt(status.direction.offsetAngel) + '<br> 偏离弧度： '+
                                                                parseInt(status.direction.offsetRadian*1000)/1000;
                    }
                }
            });
			
			document.getElementById('start').onclick = function(){
				getData();
				flag = true;
				//satellite.play();
			}
			
			document.getElementById('stop').onclick = function(){
				clearInterval(getinterval);
   			    drawFlag=false;
   			 	satellite.satelliteData=[];
			}
			
			document.getElementById('enlarge').onclick = function(){
				var scl = satellite.stageStatus.scale + 0.1;
				satellite.scale(scl);
				
			}
            
			document.getElementById('lessen').onclick = function(){
				var scl = satellite.stageStatus.scale * 0.9;
				satellite.scale(scl);
			}
			
			document.getElementById('reset').onclick = function(){
				//var scl = satellite.stageStatus.scale * 0.9;
				satellite.scale(1);
			}
			
			document.getElementById('estage').onclick = function(){
				satellite.extendStage(2);
				
			}
			var sateData=new Array();
			var olddata="";
			var getinterval;
			var flag=true;
			var globalCount=0;
			var drawFlag=true;
			function getData(){
		          getinterval = setInterval(function () {
					 $.ajax({
			             type: "GET",
			             url: "<%=request.getContextPath()%>/cache/getDevData.do",
			             data: {deviceId:11},
			             dataType: "text",
			             success: function(data){
			            	 if(data.length==0){
			            		 clearInterval(getinterval);
		            			 drawFlag=false;
			            	 }else{
			            		 if(olddata!=data){
				            		 olddata = data;
				            		 //解析data成需要的数组
				            	     var allData = data.split(";");
				            		 for(var i=0;i<allData.length;i++){
				            			// satellite.pushData(allData[i].split(","));
				            			var empArray=allData[i].split(",");
				            			var arrayData=new Array(empArray[13],empArray[15],0,empArray[9],empArray[11],empArray[8],empArray[4],empArray[6],empArray[5]);
				            			satellite.pushData(arrayData);//新格式数据
				            		 }
						             if(flag){
						            	 satellite.play();
						            	 flag = false;
						             }
				            	 }
			            	 }
			             },error:function(){
			            	 alert("系统错误，请稍后再试!");
			             }
		            });
				 },500);    
				// satellite.pushDatas(sate);satellite.play();
        		/*for(var i=0;i<sate.length;i++){
        			var arrayData=new Array();
        			arrayData[0]=sate[i][13];
        			arrayData[1]=sate[i][15];
        			arrayData[2]=0;
        			arrayData[3]=sate[i][9];
        			arrayData[4]=sate[i][11];
        			arrayData[5]=sate[i][8];
        			arrayData[6]=sate[i][4];
        			arrayData[7]=sate[i][6];
        			arrayData[8]=sate[i][5];
        			satellite.pushData(arrayData);//新格式数据
        		 }
        		 satellite.play();  */  
			}
        </script>
    </body>
</html>
