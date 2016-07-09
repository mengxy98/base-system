/**
* @Author: 杜绍彬
* @Date:   2016-05-11:05-26-12
* @Email:  shaobin.du@zymobi.com
* @Project: Rope
* @Last modified by:   杜绍彬
* @Last modified time: 2016-05-20:02-35-42
*/

/*
    服务端给定的数据格式，以数组的形式：

    20.5：CMV,
    3.4：振动频率
    0,遍数（需要自己计算的）
    2016/3/30 14:34:23 : 时间
    RTKfixed : 卫星定位壮态
    2.32:速度
    137.301 : 高程（海拔）
    3219200.01709464:纬度（Y）
    634607.41813379:经度（X）

    [0(CMV),1(振动频率),2(遍数),3(时间),4(卫星定位壮态),5(速度),6(高程),7(纬度),8(经度)]

*/


(function(global){
	
	var tmpCanvas = null;
	
    //是否支持canvas
    function isCanvasSupported(){
        var elem = document.createElement('canvas');
        return !!(elem.getContext && elem.getContext('2d'));
    }

    //床架你舞台
    function createStage(){
        if(isCanvasSupported()){
            return document.createElement('canvas');
        }else{
            throw new Error('当前浏览器不支持canvas');
        }
    }

    //判断对象是否是字符串
    function isString(obj){
        return Object.prototype.toString.call(obj) === '[object String]';
    }

    //判断对象是否是函数
    function isFunction(obj){
        return Object.prototype.toString.call(obj) === '[object Function]';
    }

    //判断对象是否是数组
    function isArray(obj){
        return Object.prototype.toString.call(obj) === '[object Array]';
    }

    //获取元素信息
    function getElementInfo(element){
        if(!element){
            return {};
        }
        var positionInfo = element.getBoundingClientRect();
        return positionInfo;
    }
	
	
	function getTempCanvas(){
		if(!tmpCanvas){
			tmpCanvas = document.createElement('canvas');
		}
		return tmpCanvas;
	}
	

    //获取元素信息 HSL 颜色值 转化为 RGB 颜色值
    function hslToRgb(h, s, l){
        var r, g, b;
        if(s == 0){
            r = g = b = l; // achromatic
        }else{
            var hue2rgb = function hue2rgb(p, q, t){
                if(t < 0) t += 1;
                if(t > 1) t -= 1;
                if(t < 1/6) return p + (q - p) * 6 * t;
                if(t < 1/2) return q;
                if(t < 2/3) return p + (q - p) * (2/3 - t) * 6;
                return p;
            }

            var q = l < 0.5 ? l * (1 + s) : l + s - l * s;
            var p = 2 * l - q;
            r = hue2rgb(p, q, h + 1/3);
            g = hue2rgb(p, q, h);
            b = hue2rgb(p, q, h - 1/3);
        }

        return [Math.round(r * 255), Math.round(g * 255), Math.round(b * 255)];
    }


    //RGB颜色值转化为HSL颜色值
    function rgbToHsl(r, g, b){
        r /= 255, g /= 255, b /= 255;
        var max = Math.max(r, g, b), min = Math.min(r, g, b);
        var h, s, l = (max + min) / 2;

        if(max == min){
            h = s = 0; // achromatic
        }else{
            var d = max - min;
            s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
            switch(max){
                case r: h = (g - b) / d + (g < b ? 6 : 0); break;
                case g: h = (b - r) / d + 2; break;
                case b: h = (r - g) / d + 4; break;
            }
            h /= 6;
        }
        return {
            val:[h*360, s, l],
            str:'hsl('+[h*360, s*100+'%', l*100+'%'].join(',')+')'
        }
    }


    /**
    * 使用分割轴理论判断两个凸多边形是否相交，理论上点越小越精确
    *
    *
    * @param a an array of connected points [{x:, y:}, {x:, y:},...] that form a closed polygon
    * @param b an array of connected points [{x:, y:}, {x:, y:},...] that form a closed polygon
    * @return true if there is any intersection between the 2 polygons, false otherwise
    */

    //两个凸多边形是否相交
    function doPolygonsIntersect (a, b) {
        var polygons = [a, b];
        var minA, maxA, projected, i, i1, j, minB, maxB;
        function isUndefined(obj){
            return obj === void 0;
        }
        for (i = 0; i < polygons.length; i++) {
            //循环每条边是否分割两个多边形
            var polygon = polygons[i];
            for (i1 = 0; i1 < polygon.length; i1++) {
                //两个顶点创建边
                var i2 = (i1 + 1) % polygon.length;
                var p1 = polygon[i1];
                var p2 = polygon[i2];
                // 找到边的垂线
                var normal = { x: p2.y - p1.y, y: p1.x - p2.x };
                minA = maxA = undefined;
                // 第一个多边形的顶点按照垂线映射到边上并且记录最小值和最大值
                for (j = 0; j < a.length; j++) {
                    projected = normal.x * a[j].x + normal.y * a[j].y;
                    if (isUndefined(minA) || projected < minA) {
                        minA = projected;
                    }
                    if (isUndefined(maxA) || projected > maxA) {
                        maxA = projected;
                    }
                }
                // 第二个多边形的顶点按照垂线映射到边上并且记录最小值和最大值
                minB = maxB = undefined;
                for (j = 0; j < b.length; j++) {
                    projected = normal.x * b[j].x + normal.y * b[j].y;
                    if (isUndefined(minB) || projected < minB) {
                        minB = projected;
                    }
                    if (isUndefined(maxB) || projected > maxB) {
                        maxB = projected;
                    }
                }
                if (maxA < minB || maxB < minA) {
                    return false;
                }
            }
        }
        return true;
    }


    //初始化类
    function Satellite(options){
        options = options || {};
        this.configs = {
            panel:options.panel,
            size:{
                width:options.width,
                height:options.height,
				scaleRate:options.scaleRate === undefined ? 4 : options.scaleRate
            },
            container:{
                className:options.className === undefined ? '' : options.className,
            },
			borderRange:options.borderRange === undefined ? 200 : options.borderRange,
            isAutoPlay:options.isAutoPlay === undefined ? false : !!options.isAutoPlay,
            statusChange:isFunction(options.statusChange) ? options.statusChange : function(){},
			pointIn:isFunction(options.pointIn) ? options.pointIn : function(){},
            intervalTime:options.intervalTime === undefined ? 200 : parseInt(options.intervalTime),
            colorList: options.colorList ? options.colorList : [[255,0,0],[0,255,0],[0,0,255],[0,255,255],[255,255,0],[255,224,0],[255,0,0],[42,255,0]],//rgb颜色
            rate : options.rate === undefined ? 4 : options.rate,//1像素点对应多少厘米，也就是说5cm*5cm填充一个像素点 10*10 填充 2*2 两像素
            precision: options.precision === undefined? 4 : options.precision,//多少单位厘米
            machine:{
                width: options.machineWidth === undefined ? 200 : options.machineWidth,//单位cm
            }
        };
        this.panel = isString(this.configs.panel) ? document.querySelector(this.configs.panel) : this.configs.panel;

        this.container = null;
        this.stage = null;
        this.stageSize = {
            width:0,
            height:0
        };
        this.stageCtx = null;

        this.satelliteData = [];
        this.interval = null;

        //当前处理后的状态数据
        this.satelliteStat = {
            status:[],
            prev:[],
            curr:[],
            next:[]
        };

        //当前舞台的状态
        this.stageStatus = {
            scale:1,
            translate:{
                x:0,
                y:0
				
            }
        };
		
		this.panelInfo = {};

        this.relativePos = {
            px:0, //第一个点 x
            py:0, //第一个点 y
            cx:0, //画布中心位置 x
            cy:0  //画布中心位置 y
        };


        //初始化操作
        this.init();
    }


    //初始化
    Satellite.prototype = {
        constructor:Satellite,
        init:function(){
            if(!this.panel){
                throw new Error('panel是必须的字段');
            }

            this.initContainer();

            this.initStage();

            this.panel.appendChild(this.container);

            if(this.configs.isAutoPlay){
                this.play();
            }

            this.initDrag();
        },

        //初始化舞台
        initStage:function(){
            var panelInfo = getElementInfo(this.panel);
            this.stage = createStage();
            this.stageCtx = this.stage.getContext('2d');

            this.stage.style.position = 'absolute';

            this.stageSize.width = 2 * (this.configs.size.width === undefined ? panelInfo.width : this.configs.size.width);
            this.stageSize.height = 2 * (this.configs.size.height === undefined ? panelInfo.height : this.configs.size.height);

            this.stage.width = this.stageSize.width;
            this.stage.height = this.stageSize.height;

            this.stage.style.left = -(this.stageSize.width - panelInfo.width) / 2 + 'px';
            this.stage.style.top = -(this.stageSize.height - panelInfo.height) / 2 + 'px';

            this.container.appendChild(this.stage);
			
			this.panelInfo = panelInfo;

        },

        //初始化容器
        initContainer:function(){

            this.container = document.createElement('div');
            //设置container样式
            this.container.style.position = 'relative';
            this.container.style.width = '100%';
            this.container.style.height = '100%;'
            if(this.configs.className){
                this.container.setAttribute('class',this.configs.className);
            }
        },

        //初始化数据
        initData:function(data){
            if(!isArray(data)){
                throw new Error('数据格式有误');
            }
            this.satelliteData = data.reverse();
        },

        //初始化拖拽
        initDrag:function(){
            var that = this;
            var mouse = {
                down:false,
                up:false,
                move:false,
                startPos:{
                	x:0,
					y:0
                },
                endPos:{},
                trans:{
                    x:this.stageStatus.translate.x,
                    y:this.stageStatus.translate.y
                }
            };
            var tmp = {x:0,y:0};

            document.addEventListener('mousedown',function(evt){
                mouse.startPos = {
                    x:evt.clientX,
                    y:evt.clientY,
                };
				
				//reset 
				mouse.trans.x = that.stageStatus.translate.x;
				mouse.trans.y = that.stageStatus.translate.y;
				
                mouse.down = true;

            },false);

            document.addEventListener('mouseup',function(evt){
                mouse.down = false;

                mouse.trans = {
                    x:tmp.x ,
                    y:tmp.y
                };

            },false);

            document.addEventListener('mousemove',function(evt){
                if(!mouse.down || evt.target !== that.stage){
                    return;
                }
                var x = evt.clientX - mouse.startPos.x;
                var y = evt.clientY - mouse.startPos.y;

                tmp.x = (mouse.trans.x + x);
                tmp.y = (mouse.trans.y + y);

                //that.stage.style.transform = 'translate('+ (tmp.x) +'px,'+ (tmp.y) +'px)';

                that.stageStatus.translate = {
                    x:tmp.x,
                    y:tmp.y
                }

                that.updateStageTransform();

            },false);

        },

        //放大缩小
        scale:function(val){
            val = parseFloat(val);
            if(isNaN(val)){
                return;
            }
            this.stageStatus.scale = parseFloat(val);
            this.updateStageTransform();
        },

        //更新舞台的stage
        updateStageTransform:function(){
            var transform = this.stageStatus;
            this.stage.style.transform = 'translate('+ (transform.translate.x) +'px,'+ (transform.translate.y) +'px) scale('+transform.scale+')';
        },

        //调整大小
        resize:function(){


        },
		
		//扩展舞台大小
        extendStage:function(rate){
            if(rate < 1){
                rate = 1;
            }
			
            rate = parseFloat(rate);
            var newWidth = this.stageSize.width * rate;
            var newHeight = this.stageSize.height * rate;

            //更新position
            var appendLeft = (newWidth - this.stageSize.width)/2;
            var appendTop = (newHeight - this.stageSize.height)/2;

            this.stage.style.left = (parseFloat(this.stage.style.left) - appendLeft) + 'px';
            this.stage.style.top = (parseFloat(this.stage.style.top) - appendTop) + 'px';

            //更新大小
            //this.stage.width = newWidth;
            //this.stage.height = newHeight;

            this.stageSize.width = newWidth;
            this.stageSize.height = newHeight;
			
			this.resizeCanvas({
				width:newWidth,
				height:newHeight,
				top:appendTop,
				left:appendLeft
			});
			

        },
		
		//重置canvas 大小，由于resize重置会导致canvas 内存中数据丢失，resize会严重影响性能
		resizeCanvas:function(size){
			var tmpCanvas = getTempCanvas();
			var tmpCtx = tmpCanvas.getContext('2d');
			
			tmpCanvas.width = this.stage.width;
			tmpCanvas.height = this.stage.height;
			
			tmpCtx.drawImage(this.stage, 0, 0);
			
			//tmpCtx = this.stageCtx;
			
			this.stage.width = size.width;
			this.stage.height = size.height;
			
			this.stageCtx.drawImage(tmpCanvas,size.left,size.top);
			
		},

        //清空
        clear:function(){
            this.stageCtx.clearRect(0,0,this.stageSize.width,this.stageSize.height);
        },

        //顺时针角度
        getOffsetAngel:function(f,c,t){
            return Math.atan2(t.y - c.y, t.x - c.x) - Math.atan2(f.y - c.y, f.x - c.x);
        },

        //计算方向
        getDirection:function(){
            var prev = this.satelliteStat.prev;
            var curr = this.satelliteStat.curr;
            var next = this.satelliteStat.next;

            if(!curr || !next){
                return;
            }
            // S 0 < N.y-P.y > 0 N
            // W 0 < N.x-P.x > 0 E
            var dire = {
                n:0,
                s:0,
                e:0,
                w:0,
                radian:0,
                angle:0,
                offsetRadian:0,//偏离方向弧度
                offsetAngel:0,//偏离方向角度
            };

            var yv = next[7] - curr[7];
            var xv = next[8] - curr[8];

            if(yv > 0){
                dire.n = 1;
            }else if(yv < 0){
                dire.s = 1;
            }

            if(xv > 0){
                dire.e = 1;
            }else if(xv < 0){
                dire.w = 1;
            }

            //计算下一个点的弧度
            dire.radian = Math.atan(yv/xv);
            //计算下一个点的角度
            dire.angle = dire.radian*180/Math.PI;

            dire.offsetRadian = this.getOffsetAngel({
                x:prev[8],
                y:prev[7]
            },{
                x:curr[8],
                y:curr[7]
            },{
                x:next[8],
                y:next[7]
            });

            dire.offsetAngel = dire.offsetRadian * (180 / Math.PI);

            return dire;

        },

        fillRect:function(data){
            var ctx = this.stageCtx;
            if(data.fillStyle){
                ctx.fillStyle = data.fillStyle;
            }
            ctx.fillRect(data.x,data.y,data.width,data.height);
        },

        //处理数据
        processData:function(){
            var curr = this.satelliteData.pop();
            $("#testDat").val(this.satelliteData.length);
            if(!curr){
                return false;
            }

            this.satelliteStat.prev = this.satelliteStat.curr;
            this.satelliteStat.curr = curr;
            this.satelliteStat.next = this.satelliteData[this.satelliteData.length - 1];

            var dire = this.getDirection();

            //回调数据出来
            this.configs.statusChange({
                speed: curr[5] || '0',//速度
                direction : dire,//方向：东、南、西、北 和角度
                satelliteStatus: curr[4] || '',//卫星状态
                cood:{//当前坐标
                    x: curr[8] || '0',
                    y: curr[7] || '0',
                    h: curr[6] || '0',//高度
                },
                satelliteTime: curr[3] || '',//卫星时间
                times: curr[2] || 0,//变数
                CMV : curr[0] || 0,//cmv
                frequency:curr[1] || 0,//频率
            });

            this.draw();

        },

        //获取填充样式
        getFillStyle:function(cmv,level){
            var color = this.configs.colorList[level];
            if(!color){
                color = this.configs.colorList[0];
            }
            var hsl = rgbToHsl(color[0],color[1],color[2]);

            hsl.val[1] = (hsl.val[1] * 100) +'%';

            hsl.val[2] = hsl.val[2]*100 +'%';

            return 'hsl('+hsl.val.join(',')+')'

        },
		
        //卫星数据
        draw:function(){
            var that = this;
            var renderList = this.updateSatelliteStatus();

            if(!isArray(renderList)){
                return;
            }
			
			//分割任务分别绘制解决卡的问题
            renderList.forEach(function(v,i){
				setTimeout(function(){
	                that.fillRect({
	                    x:v.x,
	                    y:v.y,
	                    width:v.gap,
	                    height:v.gap,
	                    fillStyle:that.getFillStyle(v.cmv,v.level)
	                });
				},0)
            });

            //this.stageCtx.draw();

        },

        //获取相对中心点的位置
        getRelativePos:function(curr){
            //var curr = this.satelliteStat.curr;
            var resPos = {};
            if(!curr){
                return;
            }

            if(this.relativePos.px === 0){
                this.relativePos.px = parseFloat(curr[8]);
            }
            if(this.relativePos.py === 0){
                this.relativePos.py = parseFloat(curr[7]);
            }

            this.relativePos.cx = parseFloat(this.stageSize.width) / 2;
            this.relativePos.cy = parseFloat(this.stageSize.height) / 2;

            var rx = parseFloat(curr[8]) - this.relativePos.px;//相对移动了多少单位:m
            var ry = parseFloat(curr[7]) - this.relativePos.py;//相对移动了多少单位:m


            rx = parseFloat(rx * 100);//转化为cm
            ry = parseFloat(ry * 100);//转化为cm

            //转化为像素点
            resPos.x = Math.round(rx / this.configs.rate);
            resPos.y = Math.round(-ry / this.configs.rate);

            return resPos;
        },

        drawRect:function(rect){
            var pos = this.relativePos;
            rect = [{
                x:rect.x+pos.cx,
                y:rect.y+pos.cy
            },{
                x:rect.x1+pos.cx,
                y:rect.y1+pos.cy
            },{
                x:rect.x2+pos.cx,
                y:rect.y2+pos.cy
            },{
                x:rect.x3+pos.cx,
                y:rect.y3+pos.cy
            }];


            var ctx = this.stageCtx;
            ctx.beginPath();
            for(var i=0,len=rect.length;i<=len;i++){
                if(i == 0){
                    ctx.moveTo(rect[i].x, rect[i].y);
                }else if(i == len){
                    ctx.lineTo(rect[0].x, rect[0].y);
                }else{
                    ctx.lineTo(rect[i].x, rect[i].y);
                    ctx.moveTo(rect[i].x, rect[i].y);
                }
            }
            ctx.stroke();

        },

        //更新状态并返回要渲染列表
        updateSatelliteStatus:function(){
            var currStatus = this.satelliteStat.status;
            var prev = this.satelliteStat.prev;
            var curr = this.satelliteStat.curr;

            if(prev.length < 1){
                return;
            }

            var prevPos = this.getRelativePos(prev);//像素
            var currPos = this.getRelativePos(curr);//像素

            var gap = this.configs.precision / this.configs.rate;//像素
            var width = this.configs.machine.width / this.configs.rate;//宽多少像素
				
			this.adjustStage(currPos);
			
            var rect = {
                x:0,
                y:0,
                x1:0,
                y1:0,
            };

            if(prevPos.x == currPos.x){
                //沿着y进行
                rect = {
                    x:prevPos.x - width/2,
                    y:prevPos.y,

                    x1:prevPos.x + width/2,
                    y1:prevPos.y,

                    x2:currPos.x - width/2,
                    y2:currPos.y,

                    x3:currPos.x + width/2,
                    y3:currPos.y
                };

            }else if(prevPos.y === currPos.y){
                //沿着x进行
                rect = {
                    x:prevPos.x,
                    y:prevPos.y - width/2,

                    x1:prevPos.x,
                    y1:prevPos.y + width/2,

                    x2:currPos.x,
                    y2:currPos.y - width/2,

                    x3:currPos.x,
                    y3:currPos.y + width/2,
                };
            }else{
                //根据两点得到斜率
                var a = (prevPos.y - currPos.y)/(prevPos.x - currPos.x);
                //垂线的斜率
                var va = -1/a;

                //根据点prevPos、currPos分别求出经过两点的直线方程
                var b = prevPos.y- va * prevPos.x;//第一个点
                var b1 = currPos.y - va * currPos.x;//第二个点

                //根据直线方程分别求出矩形的四个点
                var dx = Math.pow(Math.pow(width/2,2)/(Math.pow(va,2) + 1), 0.5);

                //var y1 = (prevPos.x - dx) * va + b;
                //var y2 = (prevPos.x + dx) * va + b;

                rect = {
                    x:Math.round(prevPos.x - dx),
                    y:Math.round((prevPos.x - dx) * va + b),

                    x1:Math.round(prevPos.x + dx),
                    y1:Math.round((prevPos.x + dx) * va + b),

                    x2:Math.round(currPos.x - dx),
                    y2:Math.round((currPos.x - dx) * va + b1),

                    x3:Math.round(currPos.x + dx),
                    y3:Math.round((currPos.x + dx) * va + b1),

                }

            }

            var mostData = {
                left:Math.round(Math.min(rect.x,rect.x1,rect.x2,rect.x3)),
                right:Math.round(Math.max(rect.x,rect.x1,rect.x2,rect.x3)),
                top:Math.round(Math.max(rect.y,rect.y1,rect.y2,rect.y3)),
                bottom:Math.round(Math.min(rect.y,rect.y1,rect.y2,rect.y3)),
            };
			
			
			
            var renderList = [];

            for(var i = Math.round(mostData.left/gap),width = Math.round(mostData.right/gap); i <= width; i++){
                for(var j= Math.round(mostData.bottom/gap),height = Math.round(mostData.top/gap); j <= height; j++){
                    if(this.checkIntersect(i,j,gap,rect)){//判断两个矩形是否重合
                        var renderData = this.updateStatus(i,j,gap,curr[0]);
                        var offsetX = this.stageSize.width/2;
                        var offsetY = this.stageSize.height/2;
                        renderData.x = renderData.x + offsetX;
                        renderData.y = renderData.y + offsetY;
                        renderList.push(renderData);
                    }
                }
            }

            return renderList;
        },
		
		//适应舞台显示的内容
		adjustStage:function(rect){
			//中心点的位置
			var originCenter = {
				x:this.stageSize.width/2,
				y:this.stageSize.height/2
			};
			
			//偏移的距离
			var currCenter = {
				x:parseFloat(this.stageStatus.translate.x),
				y:parseFloat(this.stageStatus.translate.y)
			};
			
			//舞台的宽高
			var left = -this.panelInfo.width / 2;
			var right = this.panelInfo.width / 2;
			
			var top = -this.panelInfo.height / 2;
			var bottom = this.panelInfo.height / 2;
			
			
			if(rect.x + currCenter.x <= left + this.configs.borderRange){
				this.stageStatus.translate.x = this.stageStatus.translate.x + this.configs.borderRange;
				this.updateStageTransform();
			}else if(rect.x + currCenter.x >= right - this.configs.borderRange){
				this.stageStatus.translate.x = this.stageStatus.translate.x - this.configs.borderRange;
				this.updateStageTransform();
			}else if(rect.y + currCenter.y <= top + this.configs.borderRange){
	            this.stageStatus.translate.y = this.stageStatus.translate.y + this.configs.borderRange;
				this.updateStageTransform();
			}else if(rect.y + currCenter.y >= bottom - this.configs.borderRange){
	            this.stageStatus.translate.y = this.stageStatus.translate.y - this.configs.borderRange;
				this.updateStageTransform();
			}
			
			this.configs.pointIn(rect);
            
			
		},
		
        updateStatus:function(i,j,gap,cmv){
            //var beforeList = this.satelliteStat.status;
            var res = null;
            var tmp = null;

            if(!isArray(this.satelliteStat.status)){
                this.satelliteStat.status = [];
            }

            var idx = this.satelliteStat.status.findIndex(function(v,index){
                if(v.x == i && v.y == j){
                    return true;
                }
            });

            if(idx > -1){
                tmp = this.satelliteStat.status[idx];
                tmp.level += 1;
                tmp.gap = gap;

                this.satelliteStat.status.slice(idx,1,tmp);

                res = {
                    x:tmp.x,
                    y:tmp.y,
                    gap:gap,
                    cmv:cmv,
                    level:tmp.level
                }

            }else{
                this.satelliteStat.status.push({
                    level:0,
                    x:i,
                    y:j,
                    cmv:cmv,
                    gap:gap
                });

                res = {
                    level:0,
                    x:i,
                    y:j,
                    cmv:cmv,
                    gap:gap
                }
            }

            return res;
        },


        //监测两个四边形的关系
        checkIntersect:function(i,j,gap,rect){
            //return true;//如果全部返回true
            var first = [{
                x:i*gap,
                y:j*gap
            },{
                x:(i+1)*gap,
                y:j*gap
            },{
                x:(i+1)*gap,
                y:(j+1)*gap
            },{
                x:i*gap,
                y:(j+1)*gap
            }];

            var realRect = [{
                x:rect.x,
                y:rect.y
            },{
                x:rect.x1,
                y:rect.y1
            },{
                x:rect.x2,
                y:rect.y2
            },{
                x:rect.x3,
                y:rect.y3
            }];

            return doPolygonsIntersect(first,realRect);
        },

        //开始播放
        play : function(){
        	 var that = this;
             this.interval = setInterval(function () {
                 if(that.processData() === false){
                    // that.stop();
                 	if(!drawFlag){
                 		that.stop();
                 	}
                 }
             },this.configs.intervalTime);

        },

        stop:function(){
            if(this.interval){
                clearInterval(this.interval);
                this.interval = null;
            }
            console.log('数据回放完毕!');
        },
		
		//重置
		reset:function(){
			
		},
		
        //返回最终渲染的状态可以直接显示出来
        getFinalState:function(){
            return this.satelliteStat.status;
        },

        //加载初始化状态{}
        loadSatelliteStat:function(status){


        },

        //推入新数据
        pushData : function(data){
            if(!isArray(data)){
                return;
            }
            this.satelliteData.unshift(data);
        },

        pushDatas:function(datas){
            if(!isArray(datas)){
                return;
            }
            datas.reverse();
            this.satelliteData = datas.concat(this.satelliteData);
        },
        //更新配置
        config : function(){


        },


    }


    global.Satellite = Satellite ;


})(this)
