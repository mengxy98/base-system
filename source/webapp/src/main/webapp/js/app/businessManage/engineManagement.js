function initManager(){
	getDataList();
	$("#search").click(function(){
		getDataList();
	})
	
	$("div[class='col-xs-12 col-sm-6']").html($("#addAlterDelete",window.parent.document).html());
	
	
	$("#saveAdd").click(function(){
		addData();
	})
	
	$("#add").click(function(){
		 $(".txt").text("");
		 $("#proDiolagAdd").show();	
		 $("#proDiolagRevise").hide();
		 $("#proDiolagAdd").find("input").val("");
		 $("#contianerDetailAdd #remarkContent").val("");
		 $("#contianerDetailAdd #winContractorDes").val("");
 	})
	$("#remove").click(function(){
		deleteData();
	})
	$("#revise").click(function(){
		modifyData();
	})
	$("#saveRevise").click(function(){
		saveModify();
	})
	
}
/* 表格部分代码 */
var userManagerment_1, responsiveHelper_datatable_col_reorder, breakpointDefinition = {
		tablet : 1024,
		phone : 480
	};
function getDataList() {
	$("#loadingRoleManagent").css("display", "block");
	if (userManagerment_1) {
		userManagerment_1.ajax.reload();
	} else {
		userManagerment_1 = $('#datatable_col_reorder_roleManagement').dataTable({
							"searching" : false,
							"ordering" : true,// 关闭排序功能
							"pageLength" : pageSize,// 每页显示数据条数
							"sPaginationType" : 'full_numbers',// 分页格式
							"oLanguage" : oLanguage,
							/*"aaSorting" : [ [ 3, "asc" ] ],*/
							serverSide : true,
							"autoWidth" : true,
							"ajax" : {
								url : Main.contextPath+ "/engineManagermentController/getDataList.do",
								data : function(d) {
									return $.extend({},d,{
										"engineName" : $("#engineName").val().trim()
										});
								},
								dataSrc : function(result) {// 回调函数
									window.parent.existsUser(result);//判断用户是否已失效
									$("#loadingRoleManagent").css("display", "none");
									/*setTimeout(function(){
										userManagerment_1.column( 1 ).visible( false );
									},0);*/
									return result.data;
								}
							},
							columnDefs : [ { // 列定义，定义checkbox在哪个列显示
								orderable : false,
								className : 'select-checkbox',
								targets : 0
							} ],
							select : {
								style : 'os',
								selector : 'td:first-child input' // 定义点击第一列，触发行选中事件
							},
							columns : [{
										"data" : "id",
										"orderable" : false,
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
												return "<input style='margin-left:30%' id='ddddddee' type='checkbox' value="+data+">";}
									},{
										"data" : "number",
										"orderable" : false,
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:center'>"+ data + "</p>";
										}
									},{
										"data" : "name",
										"render" : function(data, type, full,
												meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "contractor",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "cdescribe",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "remark",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									},{
										"data" : "createTime",
										"render" : function(data, type, full,meta) {// 渲染，修改数据的展现形式
											return "<p style='text-align:left'>"+ data + "</p>";
										}
									}
							],
							"aoColumnDefs" : [ {
								"bSortable" : false,
								"aTargets" : [ 0 ]
							} ],
							"sDom" : "<'dt-toolbar'<'col-xs-12 col-sm-6'f><'col-sm-6 col-xs-6 hidden-xs'C>r>"
									+ "t"
									+ "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p>>",
							"autoWidth" : true,
							"preDrawCallback" : function() {
								if (!responsiveHelper_datatable_col_reorder) {
									responsiveHelper_datatable_col_reorder = new ResponsiveDatatablesHelper(
											$('#datatable_col_reorder_roleManagement'),
											breakpointDefinition);
								}
							},
							"rowCallback" : function(nRow) {
								responsiveHelper_datatable_col_reorder.createExpandIcon(nRow);
							},
							"drawCallback" : function(oSettings) {
								responsiveHelper_datatable_col_reorder
										.respond();
							}

						}).api();

			// 监听表格行的选中事件
			userManagerment_1.on('select',function(e, dt, type, indexes) {
					// 选中的是行
					if (type === 'row') {
						// 令行中的第一个单元格内的checkbox被选中
						$(dt.rows(indexes).nodes().to$()[0]).find('td:first-child').find('input[type="checkbox"]').attr({checked : true});
					}
			}).on('deselect',function(e, dt, type, indexes) { // 监听表格行的非选中事件
					if (type === 'row') {
						// 令行中的第一个单元格内的checkbox不被选中
						$(dt.rows(indexes).nodes().to$()[0]).find('td:first-child').find('input[type="checkbox"]').attr({checked : false});
					}
			});

			// 监听checkbox,令表格行是否被选中
			userManagerment_1.on('click', 'tbody tr td input[type="checkbox"]',
			function(e) {
				var $checkbox = $(this);
				var tr = $checkbox.parent().parent()[0];
				if (userManagerment_1.row(tr, {selected:true}).any()) {
					userManagerment_1.row(tr).deselect();
				} else {
					userManagerment_1.row(tr).select();
				}
		});
		$("tbody tr td:nth-child(0)").css("vertical-align","middle").css("background","red")
	}
}

function addData(){
	var name = $("#contianerDetailAdd #winEngineName").val().trim();
	var winContractor = $("#contianerDetailAdd  #winContractor").val();
	var winContractorDes = $("#contianerDetailAdd  #winContractorDes").val().trim();
	var remarkContent = $("#contianerDetailAdd  #remarkContent").val().trim();
	var params = JSON.stringify([{
		"name":name,
		"winContractor":winContractor,
		"winContractorDes":winContractorDes,
		"remarkContent":remarkContent
	}]);
   if(name == ""){
	   alert("标段名称不能为空!");
   }else if(winContractor == ""){
	   alert("承包人不能为空!");
   }else{	 
	   $.ajax({
			url : Main.contextPath+"/engineManagermentController/addData.do",
			type : "post",
			data:{params:params},
			dataType : "json",
			success : function(result) {
				window.parent.existsUser(result);//判断用户是否已失效
				getDataList();
				$("#proDiolagAdd").hide();
				alert(result.msg);
			}
		});
   }
}

//删除
function deleteData(){
	var datas = userManagerment_1.rows({
		selected : true
	}).data();
	if(datas.length == '1'){
		if(confirm('确定要删除该标段吗?')){
			var id = datas[0].id;
			$.ajax({                     
				url : Main.contextPath+"/engineManagermentController/deleteData.do",
				type : "post",
				data:{id:id},
				dataType : "json",
				success : function(result) {
					window.parent.existsUser(result);//判断用户是否已失效
					getDataList();
					alert(result.msg);
				}
			});
		} 
	}else{
		alert("请选择一条记录");
	}
}

//修改设备
var modifyId=0;
function modifyData(){
	var datas = userManagerment_1.rows({selected : true}).data();
	//将用户数据放上去
	if(datas.length == '1'){
		modifyId=datas[0].id;
		$("#contianerDetailRevise #winEngineName").val(datas[0].name);
		$("#contianerDetailRevise #winContractor").val(datas[0].contractor);
		$("#contianerDetailRevise #winContractorDes").val(datas[0].cdescribe);
		$("#contianerDetailRevise #remarkContent").val(datas[0].remark);
		
		$("#proDiolagAdd").hide();
		$("#proDiolagRevise").show();
	}else{
		alert("请选择一条记录");
	}
}
//保存修改的设备
function saveModify(){
	var name = $("#contianerDetailRevise #winEngineName").val().trim();
	var winContractor = $("#contianerDetailRevise  #winContractor").val();
	var winContractorDes = $("#contianerDetailRevise  #winContractorDes").val().trim();
	var remarkContent = $("#contianerDetailRevise  #remarkContent").val().trim();
	var params = JSON.stringify([{
		"id":modifyId,
		"name":name,
		"winContractor":winContractor,
		"winContractorDes":winContractorDes,
		"remarkContent":remarkContent
	}]);
   if(name == ""){
	   alert("标段名称不能为空!");
   }else if(winContractor == ""){
	   alert("承包人不能为空!");
   }else{	 
	   $.ajax({
			url : Main.contextPath+"/engineManagermentController/modifyData.do",
			type : "post",
			data:{params:params},
			dataType : "json",
			success : function(result) {
				window.parent.existsUser(result);//判断用户是否已失效
				getDataList();
				alert(result.msg);
				$("#proDiolagRevise").hide();
			}
		});
	}
}
