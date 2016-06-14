$(function(){
	queryData();
	//添加里的保存按钮
	$("#saveAdd").click(function(){
		addData();
		$("#proDiolagAdd").hide();
	})
	
	//修改按钮
	$("#revise").click(function(){
		reviseData();
	});
	
	//修改的保存按钮
	$("#saveRevise").click(function(){
		updateData();
	})
    //删除按钮
	$("#remove").click(function(){
		delectData();
     })
     
     //刷新按钮
     $("#refresh").click(queryData);
});


//查询菜单
function queryData(){
	$.ajax({
		url : Main.contextPath + "/menuManagementController/queryData.do",
		type : "post",
		dataType : "json",
		success : function(result) {
			window.parent.existsUser(result);// 判断用户是否已失效
			var dataArr=[];
			if(result.length>0){
				for(var i=0;i<result.length;i++){
					if(result[i].id==4){
						var zogn={"id":result[i].id,"pId":result[i].parentID,"name":result[i].text,"open":true};
					}else{
						var zogn={"id":result[i].id,"pId":result[i].parentID,"name":result[i].text};
					}
					dataArr.push(zogn);
				}
				menuConfig(dataArr);
			}
		}
	});
}

//生成树状菜单
function menuConfig(data){
	 var setting = {
				view: {
					selectedMulti: true
				},
				check: {
					enable: true
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onCheck: onCheck
				},
				checkable : false,
				
			};
			var clearFlag = false;
			function onCheck(e, treeId, treeNode) {
				count();
				if (clearFlag) {
					clearCheckedOldNodes();
				}
			}
			function clearCheckedOldNodes() {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
						nodes = zTree.getChangeCheckedNodes();
				for (var i=0, l=nodes.length; i<l; i++) {
					nodes[i].checkedOld = nodes[i].checked;
				}
			}
			 function createTree() {
				$.fn.zTree.init($("#treeDemo"), setting, data);
				clearFlag = $("#last").attr("checked");
			} 
			  
			 $(document).ready(function(){
				 createTree()
				$("#init").bind("change", createTree);
				$("#last").bind("change", createTree);
			}); 
}

//保存配置菜单数据
function addData(){
	var params = JSON.stringify([ {
		"PARENT_ID" : $("#parentIdAdd").data("pid"),
		"TEXT" : $("#menuNameAdd").val(),
		"LEAF" : $("#leafAdd").val(),
		"DISABLED" : $("#disabledAdd").val(),
		"HREF_TARGET" : $("#hrefAdd").val(),
		"VISIBILITY" : $("#visibilityAdd").val(),
		"SORT" : $("#sortAdd").val(),
		"TYPE":$('#typeAdd').val()
	} ]);
	$.ajax({
		url : Main.contextPath + "/menuManagementController/addData.do",
		type : "post",
		dataType : "json",
		data : {
			"params" : params
		},
		success : function(result) {
			window.parent.existsUser(result);// 判断用户是否已失效
			if (result[0].state == 0) {
				queryData();
			} else {
				alert("添加菜单失败");
			}
		}
	});
}

//修改按钮
function reviseData(){
	 if($(".level1 .curSelectedNode").length==0||$(".level1 .curSelectedNode").length===null){
		 alert("您没有选中任何选项！请选择...");
	}else  if($(".level1 .curSelectedNode").length>1){
		alert("你选择的选项过多，请您选择需要修改的一项");
	}else{
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getSelectedNodes()[0];
		$.ajax({
			url : Main.contextPath + "/menuManagementController/queryOldData.do",
			type : "post",
			dataType : "json",
			data : {
				"menuId" : nodes.id
			},
			success : function(result) {
				window.parent.existsUser(result);// 判断用户是否已失效
				if(result.length>0){
					var nodes =  treeObj.getNodeByParam("id", result[0].PARENT_ID, null);
					$("#parentIdUpdate").val(nodes.name);
					$("#parentIdUpdate").data("pid",result[0].PARENT_ID);
					$("#menuNameUpdate").val(result[0].TEXT);
					$("#hrefUpdate").val(result[0].HREF_TARGET);
					$("#sortUpdate").val(result[0].SORT);
					$("#typeUpdate").val(result[0].TYPE);
					$("#leafUpdate option[value="+result[0].LEAF+"]").attr('selected',true);
					$("#disabledUpdate option[value="+result[0].DISABLED+"]").attr('selected',true);
					$("#visibilityUpdate option[value="+result[0].VISIBILITY+"]").attr('selected',true);
					
				}
				$("#proDiolagRevise").show();
				$("#proDiolagAdd").hide();
			}
		});
	
	} 
	
}

//修改保存
function updateData(){
	if(confirm("真的要修改选中项吗？")){
		$("#proDiolagRevise").hide();
		$("#proDiolagAdd").hide()
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getSelectedNodes()[0];
		var params = JSON.stringify([ {
			"menuId" :nodes.id,
			"TEXT" : $("#menuNameUpdate").val(),
			"LEAF" : $("#leafUpdate").val(),
			"DISABLED" : $("#disabledUpdate").val(),
			"HREF_TARGET" : $("#hrefUpdate").val(),
			"VISIBILITY" : $("#visibilityUpdate").val(),
			"SORT" : $("#sortUpdate").val(),
			"TYPE":$('#typeUpdate').val()
		} ]);
		$.ajax({
			url : Main.contextPath + "/menuManagementController/updateData.do",
			type : "post",
			dataType : "json",
			data : {
				"params" : params
			},
			success : function(result) {
				window.parent.existsUser(result);// 判断用户是否已失效
				if (result[0].state == 0) {
					queryData();
				} else {
					alert("添加菜单失败");
				}
			}
		});
	}else{
		$("#proDiolagRevise").hide();
	}
	
}


function delectData(){
	
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getSelectedNodes();
	 if(nodes.length==0||nodes.length===null){
		 alert("您没有选中任何选项！请选择...");
	} else{
		if(confirm("真的要删除选中项吗？")){
		var menuIdArr=[];
		     for(var i=0;i<nodes.length;i++){
		    	 if(nodes[i].name=="菜单"){
		    		 alert("菜单根目录不可删除!")
		    		 return;
		    	 }else if(treeObj.getNodeByParam("pId",nodes[i].id,null)!=null){
		    		 alert(nodes[i].name+"目录下仍有菜单，需先行删除菜单")
		    		 return;
		    	 }else{
		    		 menuIdArr.push(nodes[i].id)
		    	 }
		     }
		     menuIdArr=menuIdArr.join(",")
		 	$.ajax({
				url : Main.contextPath + "/menuManagementController/delectData.do",
				type : "post",
				dataType : "json",
				data : {
					"menuIdArr" : menuIdArr
				},
				success : function(result) {
					window.parent.existsUser(result);// 判断用户是否已失效
					if (result[0].state == 0) {
						var wrong="";
						if(result[0].fail>0){
							wrong=",失败"+result[0].fail+"个,菜单被占用!"
						}
						alert("成功删除"+result[0].success+"个菜单"+wrong);
						queryData();
					} else {
						alert("删除菜单失败");
					}
				}
			});
		     
		     
	}
	}
}