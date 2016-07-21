function testData11(){
			    var i = 0;
				var setinterval = setInterval(function () {
					 if(i>=(sate11.length)){
						  clearInterval(setinterval);
						  setTimeout(function(){
							  $.ajax({
						             type: "POST",
						             url: "<%=request.getContextPath()%>/cache/setDevDataEmp.do",
						             data: {deviceId:11,dataList:""},
						             dataType: "json",
						             success: function(data){
						            	 
						             },error:function(){
						            	 alert("系统错误，请稍后再试!");
						             }
					            });
						  },1000);
					  }
					  //模拟5条数据
					  var emp = sate11[i++].join(",")+";"+sate11[i++].join(",")+";"+sate11[i++].join(",")+";"+sate11[i++].join(",")+";"+sate11[i++].join(",")+";";
					  $.ajax({
				             type: "POST",
				             url: "<%=request.getContextPath()%>/cache/setDevDataEmp.do",
				             data: {deviceId:11,dataList:emp},
				             dataType: "json",
				             success: function(data){
				            	 
				             },error:function(){
				            	 alert("系统错误，请稍后再试!");
				             }
			         });
				},1000);
			}



function testData12(){
    var i = 0;
	var setinterval = setInterval(function () {
		 if(i>=(sate12.length)){
			  clearInterval(setinterval);
			  setTimeout(function(){
				  $.ajax({
			             type: "POST",
			             url: "<%=request.getContextPath()%>/cache/setDevDataEmp.do",
			             data: {deviceId:12,dataList:""},
			             dataType: "json",
			             success: function(data){
			            	 
			             },error:function(){
			            	 alert("系统错误，请稍后再试!");
			             }
		            });
			  },1000);
		  }
		  //模拟5条数据
		  var emp = sate12[i++].join(",")+";"+sate12[i++].join(",")+";"+sate12[i++].join(",")+";"+sate12[i++].join(",")+";"+sate12[i++].join(",")+";";
		  $.ajax({
	             type: "POST",
	             url: "<%=request.getContextPath()%>/cache/setDevDataEmp.do",
	             data: {deviceId:12,dataList:emp},
	             dataType: "json",
	             success: function(data){
	            	 
	             },error:function(){
	            	 alert("系统错误，请稍后再试!");
	             }
         });
	},1000);
}