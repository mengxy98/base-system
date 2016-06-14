<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% String path = request.getContextPath(); %>

<script src="<%=path%>/js/libs/jquery-2.1.1.min.js"></script>
<script type="text/javascript" >
	//环境变量
	var Main = {};
	var M=Main;
	Main.contextPath = '<%=path%>';
	url:Main.hchartsExPath=Main.contextPath + '/plugin/export.do'; // 配置导出路径
</script>
<!-- 模糊查询 -->
 <script src="<%=path%>/js/jquery.multiple.select.js"></script> <!--引入select-->
<!-- IMPORTANT: APP CONFIG -->
<script src="<%=path%>/js/app.config.js"></script>


<!-- BOOTSTRAP JS -->
<script src="<%=path%>/js/bootstrap/bootstrap.min.js"></script>

<!-- CUSTOM NOTIFICATION -->
<script src="<%=path%>/js/notification/SmartNotification.min.js"></script>

<!-- JQUERY VALIDATE -->
<script src="<%=path%>/js/plugin/jquery-validate/jquery.validate.min.js"></script>

<!-- JQUERY UI -->
<script type="text/javascript" src="<%=path%>/js/plugin/jquery.ui/jquery.ui.widget.js"></script>

<!-- JQUERY FILEUPLOAD -->
<script src="<%=path%>/js/plugin/jquery.fileupload/jquery.fileupload.js" type="text/javascript"></script>
<script src="<%=path%>/js/plugin/jquery.fileupload/jquery.iframe-transport.js" type="text/javascript"></script>

<!-- JQUERY SELECT2 INPUT -->
<script src="<%=path%>/js/plugin/select2/select2.min.js"></script>

<!-- JQUERY UI + Bootstrap Slider -->
<script src="<%=path%>/js/plugin/bootstrap-slider/bootstrap-slider.min.js"></script>

<script src="<%=path%>/js/plugin/bootstrap-filestyle/bootstrap-filestyle.js" type="text/javascript"></script>

<!-- browser msie issue fix -->
<script src="<%=path%>/js/plugin/msie-fix/jquery.mb.browser.min.js"></script>

<!-- FastClick: For mobile devices: you can disable this in app.js -->
<script src="<%=path%>/js/plugin/fastclick/fastclick.min.js"></script>

<!--[if IE 8]>
<h1>请更换浏览器</h1>
<![endif]-->



<!-- MAIN APP JS FILE -->
<script src="<%=path%>/js/app.min.js"></script>

<!-- ENHANCEMENT PLUGINS : NOT A REQUIREMENT -->
<!-- Voice command : plugin -->
<script src="<%=path%>/js/speech/voicecommand.min.js"></script>


<!-- PAGE RELATED PLUGIN(S)
<script src="..."></script>-->
<script src="<%=path%>/js/plugin/chartjs/chart.min.js"></script>

<!-- PAGE RELATED PLUGIN(S) -->
<script src="<%=path%>/js/plugin/datatables/jquery.dataTables.js"></script>
<script src="<%=path%>/js/plugin/datatables/dataTables.select.js"></script>
<script src="<%=path%>/js/plugin/datatables/dataTables.colVis.min.js"></script>
<script src="<%=path%>/js/plugin/datatables/dataTables.tableTools.min.js"></script>
<script src="<%=path%>/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
<script src="<%=path%>/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
<script src="<%=path%>/js/jquery.easyui.min.js"></script>

<!--日历-->
<script src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<!-- PAGE RELATED PLUGIN(S)
		<script src="..."></script>-->
<script src="<%=path%>/js/plugin/jqgrid/jquery.jqGrid.min.js"></script>
<script src="<%=path%>/js/plugin/jqgrid/grid.locale-en.min.js"></script>

<!-- ------模糊查询引用JS----- -->
<script type="text/javascript" src='<%=path%>/js/plugin/page/page.js'></script>
<script type="text/javascript" src='<%=path%>/js/a2z.js'></script>
<!-- ------设置时间格式----- -->	
<script type="text/javascript" src='<%=path%>/js/dateFormate.js'></script>
<!-- JQUERY BOOTSTRAP VALIDATE -->
<script
	src="<%=path%>/js/plugin/bootstrapvalidator/bootstrapValidator.min.js"></script>
<script type="text/javascript" >
	$(function(){
		//获取服务器时间的时间
		var now;
		$.ajax({
			url:Main.contextPath+"/userController/getServiceTime.do",
			type:"post",
			dataType:"json",
			async: false,
			success:function(result){
				now = result;
			}
		});
		setInterval(function(){
			now += 1000; 
		},1000); 
		setInterval(function(){
			var newTime = new Date();
			newTime.setTime(now); 
			var nowStr = newTime.format("yyyy-mm-dd' 'HH:MM:ss");
			$("#loginUserTime").html(nowStr);
		},10);
		//获取登陆星期
		 var week;
		 var newTime1 = new Date();
		 newTime1.setTime(now);
		 switch (newTime1.getDay()){
		 	case 1: week="星期一"; break;
		 	case 2: week="星期二"; break;
		 	case 3: week="星期三"; break;
		 	case 4: week="星期四"; break;
		 	case 5: week="星期五"; break;
		 	case 6: week="星期六"; break;
		 	default: week="星期天";
		 }
		 $("#loginWeek").html(week);
    });
	//自动生成UUID
	function UUID() {
            this.id = this.createUUID();
        }

        // When asked what this Object is, lie and return it's value
        UUID.prototype.valueOf = function () {
            return this.id;
        };
        UUID.prototype.toString = function () {
            return this.id;
        };

        //
        // INSTANCE SPECIFIC METHODS
        //
        UUID.prototype.createUUID = function () {
            //
            // Loose interpretation of the specification DCE 1.1: Remote Procedure Call
            // since JavaScript doesn't allow access to internal systems, the last 48 bits
            // of the node section is made up using a series of random numbers (6 octets long).
            //
            var dg = new Date(1582, 10, 15, 0, 0, 0, 0);
            var dc = new Date();
            var t = dc.getTime() - dg.getTime();
            var tl = UUID.getIntegerBits(t, 0, 31);
            var tm = UUID.getIntegerBits(t, 32, 47);
            var thv = UUID.getIntegerBits(t, 48, 59) + '1'; // version 1, security version is 2
            var csar = UUID.getIntegerBits(UUID.rand(4095), 0, 7);
            var csl = UUID.getIntegerBits(UUID.rand(4095), 0, 7);
            // since detection of anything about the machine/browser is far to buggy,
            // include some more random numbers here
            // if NIC or an IP can be obtained reliably, that should be put in
            // here instead.
            var n = UUID.getIntegerBits(UUID.rand(8191), 0, 7) +
                    UUID.getIntegerBits(UUID.rand(8191), 8, 15) +
                    UUID.getIntegerBits(UUID.rand(8191), 0, 7) +
                    UUID.getIntegerBits(UUID.rand(8191), 8, 15) +
                    UUID.getIntegerBits(UUID.rand(8191), 0, 15); // this last number is two octets long
            return tl + tm + thv + csar + csl + n;
        };

        //Pull out only certain bits from a very large integer, used to get the time
        //code information for the first part of a UUID. Will return zero's if there
        //aren't enough bits to shift where it needs to.
        UUID.getIntegerBits = function (val, start, end) {
            var base16 = UUID.returnBase(val, 16);
            var quadArray = new Array();
            var quadString = '';
            var i = 0;
            for (i = 0; i < base16.length; i++) {
                quadArray.push(base16.substring(i, i + 1));
            }
            for (i = Math.floor(start / 4); i <= Math.floor(end / 4); i++) {
                if (!quadArray[i] || quadArray[i] == '') quadString += '0';
                else quadString += quadArray[i];
            }
            return quadString;
        };

        //Replaced from the original function to leverage the built in methods in
        //JavaScript. Thanks to Robert Kieffer for pointing this one out
        UUID.returnBase = function (number, base) {
            return (number).toString(base).toUpperCase();
        };

        //pick a random number within a range of numbers
        //int b rand(int a); where 0 <= b <= a
        UUID.rand = function (max) {
            return Math.floor(Math.random() * (max + 1));
        };

</script>	

<script src="<%=path%>/js/utils/funs.js"></script>



