
function insertData(id,path){
    var i = 0;
	var setinterval = setInterval(function () {
		 if(i>=(sateArray[id].length)){
			  clearInterval(setinterval);
			  setTimeout(function(){
				  $.ajax({
			             type: "POST",
			             url: path+"/cache/setDevDataEmp.do",
			             data: {deviceId:id,dataList:""},
			             dataType: "json",
			             success: function(data){
			            	 
			             },error:function(){
			            	 alert("系统错误，请稍后再试!");
			             }
		            });
			  },1000);
		  }
		  //模拟5条数据
		  var emp = sateArray[id][i++].join(",")+";"+sateArray[id][i++].join(",")+";"+sateArray[id][i++].join(",")+";"+sateArray[id][i++].join(",")+";"+sateArray[id][i++].join(",")+";";
		  $.ajax({
	             type: "POST",
	             url: path+"/cache/setDevDataEmp.do",
	             data: {deviceId:id,dataList:emp},
	             dataType: "json",
	             success: function(data){
	            	 
	             },error:function(){
	            	 alert("系统错误，请稍后再试!");
	             }
         });
	},1000);
}