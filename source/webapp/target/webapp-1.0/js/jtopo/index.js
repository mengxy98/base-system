			
				var content = $('#content').empty();
				var canvas = $('<canvas width="1024" height="500">').attr('id', 'canvas').appendTo(content);
				canvas.css({
					'background-color': '#EEEEEE',
					'border': '1px solid #444'
				});
// 				  $("#myButtons1 .btn").click(function(){
// 				         $(this).button('toggle');
// 				      });
				function getRandom(n){
	                return Math.floor(Math.random()*n+1)
	        }
				$("#zoomOutButton  .btn").click(function() {
					alert(1);
					stage.zoomOut();
				});
				$('#zoomInButton  .btn').click(function() {
					stage.zoomIn();
				});
				function setNodeImg(node,deviceTypeId){
					//交换机
					if(deviceTypeId=='dataKeyId:1465:device' || deviceTypeId=='dataKeyId:1466:device' || deviceTypeId=='dataKeyId:1468:device'|| deviceTypeId=='dataKeyId:1469:device' ){
				    	node.setSize(73, 13); // 同时设置大小及位置
				    	node.setImage('js/jtopo/img/pstn/router2.png');
				    }
					//路由
				    if(deviceTypeId=='dataKeyId:1467:device'  || deviceTypeId=='dataKeyId:1470:device'){
				    	node.setSize(21, 21); // 同时设置大小及位置
				    	node.setImage('js/jtopo/img/statistics/gather.png');
				    }
				    if(deviceTypeId=='dataKeyId:1466:device'){
				    	node.setSize(21, 51); // 同时设置大小及位置
				    	node.setImage('js/jtopo/img/statistics/server.png');
				    }
				    if(deviceTypeId=='dataKeyId:1473:device' || deviceTypeId=='dataKeyId:1474:device'){
				    	node.setSize(31, 38); // 同时设置大小及位置
				    	node.setImage('js/jtopo/img/pstn/cartridge_system.png');
				    }
				    return node;
				}
				 var canvas = document.getElementById('canvas');
				    var stage = new JTopo.Stage(canvas);
				    var scene = new JTopo.Scene(stage); 
			       scene.setBackground("js/jtopo/img/bg.jpg");
						var nodeFromArray = new Array();
						var nodeToArray = new Array();
						
						//查找根节点
						for (x = 0; x < root.length; x++) {
							var from = root[x].from;
							var to = root[x].to;
							var deviceId = root[x].deviceId;
							var deviceTypeId = root[x].deviceTypeId;
							 var cloudNode = new JTopo.Node(to);
							 cloudNode.setSize(85, 42); // 同时设置大小及位置
							 cloudNode.setImage('js/jtopo/img/pstn/cloud.png');
							scene.add(cloudNode);

							for (i = 0; i < rootsub.length; i++) {
								var from_rootsub = rootsub[i].from;
								var to_rootsub = rootsub[i].to;
								var deviceId_rootsub = rootsub[i].deviceId;
								var deviceTypeId_rootsub = rootsub[i].deviceTypeId;
								if (to == to_rootsub) {
									if (i % 2 == 0) {
										var node = new JTopo.Node(from_rootsub);
									} else {
										var node = new JTopo.Node();
										node.alarm = from_rootsub;
									}
									setNodeImg(node, deviceTypeId_rootsub);
									scene.add(node);
									var link = new JTopo.FlexionalLink(
											cloudNode, node);
									scene.add(link);
									for (j = 0; j < rootThird.length; j++) {

										var from_rootThird = rootThird[j].from;
										var to_rootThird = rootThird[j].to;
										var deviceId_rootThird = rootThird[j].deviceId;
										var deviceTypeId_rootThird = rootThird[j].deviceTypeId;
										if (to_rootThird == from_rootsub) {
											if (j % 2 == 0) {
												var vmNode = new JTopo.Node(
														from_rootThird);
											} else {
												var vmNode = new JTopo.Node();
												vmNode.alarm = from_rootThird;
											}

											setNodeImg(vmNode,
													deviceTypeId_rootThird);
											scene.add(vmNode);
											scene.add(new JTopo.FlexionalLink(
													node, vmNode));
											for (k = 0; k < rootFour.length; k++) {
												var from_rootFour = rootFour[k].from;
												var to_rootFour = rootFour[k].to;
												var deviceId_rootFour = rootFour[k].deviceId;
												var deviceTypeId_rootFour = rootFour[k].deviceTypeId;
												if (from_rootFour == from_rootThird) {
													if (k % 2 == 0) {
														var vmFourNode = new JTopo.Node(
																to_rootFour);
													} else {
														var vmFourNode = new JTopo.Node();
														vmFourNode.alarm = to_rootFour;
													}
													setNodeImg(vmFourNode,
															deviceTypeId_rootFour);
													scene.add(vmFourNode);
													scene
															.add(new JTopo.FlexionalLink(
																	vmNode,
																	vmFourNode));
												} else {
													// 										console.log("equal :rootFour:" + from_rootFour + "### " + to_rootFour
													// 												+ "#### " + deviceId_rootFour + "###" + deviceTypeId_rootFour
													// 												+ "###");
												}

											}

										} else {
											// 									console.log("notequal:"+to_rootThird+"!="+from_rootsub+" rootThird:" + from_rootThird + "### " + to_rootThird
											// 											+ "#### " + deviceId_rootThird + "###" + deviceTypeId_rootThird
											// 											+ "###");
										}
									}

								}
							}
						}

						scene.doLayout(JTopo.layout.TreeLayout('down', 120,80));

