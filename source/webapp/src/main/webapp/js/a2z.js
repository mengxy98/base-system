var $a2zCollection = {};
(function(){
	var template = '<div class="a2z-dropdown_where" >\n'+
				   '     <div id="tabpanel" class="a2z-well" >\n'+
				   '        <div class="a2z-close">\n'+
					'	        &times;\n'+
					'	    </div>\n'+
					'	    <!-- Nav tabs -->\n'+
					'	    <ul class="a2z-nav a2z-nav-tabs">\n'+
					'	        <li style="width:15%;height: 40px;"><a href="#panel0">全部</a></li>\n'+
					'	        <li style="width:15%;height: 40px;"><a href="#panel1">A-E</a></li>\n'+
					'	        <li style="width:15%;height: 40px;"><a href="#panel2">F-J</a></li>\n'+
					'	        <li style="width:15%;height: 40px;"><a href="#panel3">K-O</a></li>\n'+
					'	        <li style="width:15%;height: 40px;"><a href="#panel4">P-T</a></li>\n'+
					'	        <li style="width:15%;height: 40px;"><a href="#panel5">U-Z</a></li>\n'+
					'	    </ul>\n'+
					'	    <!--Tab panes -->\n'+
					'	    <div class="a2z-tab-content">\n'+
					'	        <div class="a2z-tab-panel" data-index="panel0">\n'+
					'	            <div class="a2z-pagination-data" >\n'+
					'	            </div>\n'+
					'	            <ul class="a2z-pagination"> </ul>\n'+
					'	        </div>\n'+
					'	        <div class="a2z-tab-panel"  data-index="panel1">\n'+
					'	            <div class="a2z-pagination-data"></div>\n'+
					'	            <ul class="a2z-pagination "> </ul>\n'+
					'	        </div>\n'+
					'	        <div class="a2z-tab-panel" data-index="panel2">\n'+
					'	            <div class="a2z-pagination-data"></div>\n'+
					'	            <ul class="a2z-pagination">\n'+
					'	            </ul>\n'+
					'	        </div>\n'+
					'	        <div class="a2z-tab-panel" data-index="panel3" >\n'+
					'	            <div class="a2z-pagination-data"></div>\n'+
					'	            <ul class="a2z-pagination">\n'+
					'	            </ul>\n'+
					'	        </div>\n'+
					'	        <div class="a2z-tab-panel" data-index="panel4" >\n'+
					'	            <div class="a2z-pagination-data"></div>\n'+
					'	            <ul class="a2z-pagination">\n'+
					'	            </ul>\n'+
					'	        </div>\n'+
					'	        <div class="a2z-tab-panel" data-index="panel5" >\n'+
					'	            <div class="a2z-pagination-data"></div>\n'+
					'	            <ul class="a2z-pagination">\n'+
					'	            </ul>\n'+
					'	        </div>\n'+
					'	    </div>\n'+
					'	</div>\n'+
					'</div>';
	$.fn.a2z = function(options) {
		return this.each(function(){
			var defaults = {
				jsonUrl: '',
				method: 'get',
				direction: 'left'
			};
			var $opts = $.extend({}, defaults, options);
			var $self = $(this);
			var id = $self.attr('id');
	//存放所有导航数据
			var allData = {
				ALL:["全部","sgsr"],
				AE: [{//存放A-E按钮里对应的数据
					name: "aaaaeeeee"
				}],
				FJ: [{//存放F-J按钮里对应的数据
					name: "FJFJFJFJFJ"
				}],
				KO: [{//存放K-O按钮里对应的数据
					name: "KOKOKOKOKOKO"
				}],
				PT: [{//存放P-T按钮里对应的数据
					name: "PTPTPTPTPT"
				}],
				UZ: [{//存放U-Z按钮里对应的数据
					name: "UZUZUZUZUZUZ"
				}]

			};
			//数值的默认变量
			var pageSize = 20;
			var pageIndex = 0;
			var pageCount = 0;
			var tabIndex = 0;
			var allTemData;

			var data = {};
			
			var $dropdown = $(template);
			var $tab = $dropdown.find('.a2z-nav-tabs');
			var $tab_lis = $tab.find('li');
			var $tab_content = $dropdown.find('.a2z-tab-content');
			var $tab_panels = $tab_content.find('.a2z-tab-panel');
			var $close = $dropdown.find('.a2z-close');
			var $pagination_datas = $dropdown.find('.a2z-pagination-data');
			var $paginations = $dropdown.find('.a2z-pagination');
			$('body').append($dropdown);
			
		
			//获得焦点
			$self.click(function(event) {				 				
				if($self.attr('completeshow') == 1)
					return;
				
				$dropdown.css({
					top : $self.offset().top + $self.height() + 2
				});
				if($opts.direction === 'right'){
					$dropdown.css({
						left : $self.offset().left + $self.width() - $dropdown.width()
					});
				}else if($opts.direction === 'left')
				{
					$dropdown.css({
						left : $self.offset().left 
					});
				}
				$dropdown.animate({
						opacity: 'show'
					},
					200);			   
			});
			
			$self.on('keydown',function(){
				$dropdown.hide();
			});
			//tab
			$tab_lis.click(function(e) {
				$tab_lis.each(function(i, l) {
					$(l).removeClass('a2z-active');
				});
				$(this).addClass('a2z-active');
				e.preventDefault();
				$tab_panels.hide();
				var $tab = $tab_content.find('div[data-index="' + $(this).children('a').attr('href').replace(/^#/, '')+'"]');
				$tab.show();
				tabIndex = $(this).index();
				InitTable(0);

				var p = allTemData == null ? 1 : allTemData.length / pageSize;
				//分页，PageCount是总条目数，这是必选参数，其它参数都是可选
				$paginations.pagination(allTemData.length, {
					callback: PageCallback,
					//PageCallback() 为翻页调用次函数。
					prev_text: "上一页",
					next_text: "下一页",
					items_per_page: 20,
					num_edge_entries: 2,
					//两侧首尾分页条目数
					num_display_entries: 5,
					//连续分页主体部分分页条目数
					current_page: 0,
					//当前页索引
				});
			});
			//close
			$close.click(function(e) {
				$dropdown.stop(true, true).hide();
			});
//阻止冒泡
			$(window).click(function(){
				$dropdown.css("display","none")
			})	
			$tab_lis.click(function(event){
				 event.stopPropagation();
			})
			$paginations.click(function(event){
				 event.stopPropagation();
			})
			$pagination_datas.click(function(event){
				 event.stopPropagation();
			})
//			阻止冒泡结束
			function load(d) {
				data = d;
				getJsonData();
				InitTable(0);
				$tab_lis.first().trigger('click');
			};

			function getJsonData() {
				// 读取json生成标签
				$.ajax({
					type: $opts.method,
					async: false,
					data: data,
					url: $opts.jsonUrl,
					dataType: 'text',
					success: function(data) {
						var dataObj = eval("(" + data + ")"); //转换为json对象 
						allData = dataObj;
						reload();
					}
				});
			};

			//翻页调用   
			function PageCallback(index, jq) {
				InitTable(index);
			};
			//请求数据   
			function InitTable(page) {
				var appendHtml = "";
				var curCount = page * pageSize;
				if(tabIndex=="0"){
					allTemData = allData.ALL;
				}
				else if (tabIndex == "1") {
					allTemData = allData.AE;
				} else if (tabIndex == "2") {
					allTemData = allData.FJ;
				} else if (tabIndex == "3") {
					allTemData = allData.KO;
				} else if (tabIndex == "4") {
					allTemData = allData.PT;
				} else if (tabIndex == "5") {
					allTemData = allData.UZ;
				}
				for (var i = 0; i < pageSize; i++) {
					if ((curCount + i) > allTemData.length || typeof(allTemData[curCount + i]) == "undefined") break;
					else {
						appendHtml += '<a class="a2z-btn a2z-btn-default" href="javascript:void(0)">' + unescape(allTemData[curCount + i].name) + '</a>';
					}
				}
				var $paginationData = $pagination_datas.eq(tabIndex);
				$paginationData.html("");
				$paginationData.html(appendHtml);
				//button
				$paginationData.find('.a2z-btn-default').click(function() {
					$self.val($(this).text());
					//当或得点击事件时，dropdown_where隐藏
					$dropdown.css("display","none")
				});
			};

			//reload

			function reload() {
				//分页，PageCount是总条目数，这是必选参数，其它参数都是可选
				$paginations.eq(tabIndex).pagination(allData.AE.length, {
					callback: PageCallback,
					//PageCallback() 为翻页调用次函数。
					prev_text: "上一页",
					next_text: "下一页",
					items_per_page: 20,
					num_edge_entries: 2,
					//两侧首尾分页条目数
					num_display_entries: 5,
					//连续分页主体部分分页条目数
					current_page: 0,
					//当前页索引
				});
			};
			$a2zCollection[id]= {
				load: load
			};
		});
	};
	

	
	
})(jQuery);