$(".tab>li").click(function(){
      var index=$(this).index();
      $(this).addClass("on").siblings("li").removeClass("on");
      $(".tabCt:eq("+index+")").show().siblings(".tabCt").hide();
      if($(".tabCt:eq("+index+")").height()+103>$(".navSKU").height()){
          $(".nav,.navSKU,.compareBox").height($(".navSKU").height());
          $(".compareBox .tabCt").height(($(".navSKU").height()-200));
          $(".compareBox .tabCt").css({"width":"190px","overflow-y":"auto"});
      }
});
/*------------------------------------------------------------------------------------------------------------------------------*/
 var asin=sessionStorage.asin;//B00NN2RYHY
 var sku=sessionStorage.sku_fx;
 function getRealtimeAnalysis(){
		$.ajax({
			url:path+'/sku/Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis.htm',
			data:{
				asin:asin
			},
			type:'post',
			success:function(value){//alert(value)
				var data=JSON.parse(value);
				if(data.status=='1'){
					$("#main_avastar").html('');
					$("#main_rwNum").html('');
					$("#verifiedPurchase_num").html('');
					$("#main_fiveStarNum").text(0);
					$("#five_zhanbi").text('0%');
					$("#main_fourStarNum").text(0);
					$("#four_zhanbi").text('0%');
					$("#main_threeStarNum").text(0);
					$("#three_zhanbi").text('0%');
					$("#main_twoStarNum").text(0);
					$("#two_zhanbi").text('0%');
					$("#main_oneStarNum").text(0);
					$("#one_zhanbi").text('0%');
					if(data.data.length>0){
						$("#main_avastar").append('平均分：<span>'+data.data[0].main_avastar+'</span>'+checkStar(data.data[0].main_avastar)+'');
						$("#main_rwNum").append('总评论数：<span>'+data.data[0].main_rwNum+'</span>');
						$("#verifiedPurchase_num").append('verified purchase：<span>'+data.data[0].verifiedPurchase_num+'</span>');
						$("#main_fiveStarNum").text(data.data[0].main_fiveStarNum);
						$("#five_zhanbi").text(data.data[0].five_zhanbi);
						$("#five_zhanbi_span").attr("style","width:"+data.data[0].five_zhanbi.split('%')[0]+"px;");
						$("#main_fourStarNum").text(data.data[0].main_fourStarNum);
						$("#four_zhanbi").text(data.data[0].four_zhanbi);
						$("#four_zhanbi_span").attr("style","width:"+data.data[0].four_zhanbi.split('%')[0]+"px;");
						$("#main_threeStarNum").text(data.data[0].main_threeStarNum);
						$("#three_zhanbi").text(data.data[0].three_zhanbi);
						$("#three_zhanbi_span").attr("style","width:"+data.data[0].three_zhanbi.split('%')[0]+"px;");
						$("#main_twoStarNum").text(data.data[0].main_twoStarNum);
						$("#two_zhanbi").text(data.data[0].two_zhanbi);
						$("#two_zhanbi_span").attr("style","width:"+data.data[0].two_zhanbi.split('%')[0]+"px;");
						$("#main_oneStarNum").text(data.data[0].main_oneStarNum);
						$("#one_zhanbi").text(data.data[0].one_zhanbi);
						$("#one_zhanbi_span").attr("style","width:"+data.data[0].one_zhanbi.split('%')[0]+"px;");
						$("#five_span").attr('style','width:'+data.data[0].five_zhanbi);
						$("#four_span").attr('style','width:'+data.data[0].four_zhanbi);
						$("#three_span").attr('style','width:'+data.data[0].three_zhanbi);
						$("#two_span").attr('style','width:'+data.data[0].two_zhanbi);
						$("#one_span").attr('style','width:'+data.data[0].one_zhanbi);
					}
				}else{

					$("#main_avastar").append('平均分：<span>'+0+'</span>'+checkStar(0)+'');
					$("#main_rwNum").append('总评论数：<span>'+0+'</span>');
					$("#verifiedPurchase_num").append('verified purchase：<span>'+0+'</span>');
					$("#main_fiveStarNum").text(0);
					$("#five_zhanbi").text(data.data[0].five_zhanbi);
					$("#five_zhanbi_span").attr("style","width:"+0+"px;");
					$("#main_fourStarNum").text(0);
					$("#four_zhanbi").text(data.data[0].four_zhanbi);
					$("#four_zhanbi_span").attr("style","width:"+0+"px;");
					$("#main_threeStarNum").text(0);
					$("#three_zhanbi").text(0);
					$("#three_zhanbi_span").attr("style","width:"+0+"px;");
					$("#main_twoStarNum").text(0);
					$("#two_zhanbi").text(0);
					$("#two_zhanbi_span").attr("style","width:"+0+"px;");
					$("#main_oneStarNum").text(0);
					$("#one_zhanbi").text(0);
					$("#one_zhanbi_span").attr("style","width:"+0+"px;");
					$("#five_span").attr('style','width:'+0);
					$("#four_span").attr('style','width:'+0);
					$("#three_span").attr('style','width:'+0);
					$("#two_span").attr('style','width:'+0);
					$("#one_span").attr('style','width:'+0);
				
				}
			}
		
		})
	}
 
 var _series=[];
 var _asin_str=[];
 var _option={};
 function getavaStar(){
		var echart2 = echarts.init(document.getElementById('echart2'),'shine');
		var dataStr = $('#reportrange span').text();
		var d = dataStr.split('至');
		var startTime = d[0].trim();
		var endTime = d[1].trim();
		$.ajax({
			url:path+'/sku/Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_avaStar.htm',
			data:{
				asin:asin,
				startTime:startTime,
				endTime:endTime
			},
			type:'post',
			success:function(value){
				var data=JSON.parse(value);
				if(data.status=='1'){
					_asin_str.push(asin);
					if(data.data.length>0){
						var xAxis_data=[];
						var series_data=[];

						$.each(data.data,function(index){
							xAxis_data[index]=this.year_month_day;
							series_data[index]=((parseInt(this.main_oneStarNum)*1+parseInt(this.main_twoStarNum)*2+parseInt(this.main_threeStarNum)*3+parseInt(this.main_fourStarNum)*4+parseInt(this.main_fiveStarNum)*5)/(parseInt(this.main_oneStarNum)+parseInt(this.main_twoStarNum)+parseInt(this.main_threeStarNum)+parseInt(this.main_fourStarNum)+parseInt(this.main_fiveStarNum))).toFixed(2);

						})
						 x={
						            name:asin,
						            type:'line',
						            data:series_data,
						            /*areaStyle: {
						                normal: {
						                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						                        offset: 0,
						                        color: 'rgba(0, 66, 255,.5)'
						                    }, {
						                        offset: 1,
						                        color: 'rgba(0, 204, 255,.5)'
						                    }])
						                }
						            },*/
						        }
						let b=true;
						$.each(_series,function(index){
							if(this.name==asin){
								_series[index]=x;
								b=false;
								return ;
							}
						})
						if(b){
							_series.push(x);
						}
						
						_option={
							    tooltip: {
							        trigger: 'axis'
							    },
							    legend: {
							        data: _asin_str
							    },
							    grid: {
							        left: '10px',
							        right: '120px',
							        top: '40px',
							        bottom: '10px',
							        containLabel: true
							    },
							    xAxis: {
							        type: 'category',
							        boundaryGap: ['20%','20%'],
							        data: xAxis_data
							    },
							    yAxis: {
							    	name:'总得分',
							        type: 'value'
							    },
							    series: _series
		}
						echart2.setOption(_option,true);
					}
				}else{
					
				}
			}
		
		})
	}
 
 $("#checkbox1").parent().parent().find('input').click(function(){
		if(this.checked){
			if($(this).next().text()=="总评论"){
				let echart1 = echarts.init(document.getElementById('echart1'),'shine');
				_option.series=series1;
				echart1.setOption(_option,true)
			}
			if($(this).next().text()=="新增评论"){
				let echart1 = echarts.init(document.getElementById('echart1'),'shine');
				_option.series=series2;
				echart1.setOption(_option,true)
			}
		}
	})
var series1=[];
var series2=[];
var _option1;
var legend_name=[];
 function getpinglun(){
		var echart1 = echarts.init(document.getElementById('echart1'),'shine'); 
		var dataStr = $('#reportrange span').text();
		var d = dataStr.split('至');
		var startTime = d[0].trim();
		var endTime = d[1].trim();
		$.ajax({
			url:path+'/sku/Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_pinglun.htm',
			data:{
				asin:asin,
				startTime:startTime,
				endTime:endTime
			},
			type:'post',
			success:function(value){//alert(value)
				var data=JSON.parse(value);
				if(data.status=='1'){
					if(data.data.length>0){
						let xAxis_data=[];
						let series_data_1=[];
						let series_data_2=[];
						$.each(data.data,function(index){
							xAxis_data[index]=this.year_month_day;
							series_data_1[index]=this.main_rwNum;
							series_data_2[index]=this.day_main_rwNum;
						})
						let x={
				            name: asin,
				            type: 'line',
				            data: series_data_1,
				        }
						let x1={
				            name: asin,
				            type: 'line',
				            data: series_data_2,
				        }
						let b=true;
						$.each(legend_name,function(index){
							if(this==asin){
								b=false;
								return ;
							}
						})
						if(b){
							legend_name.push(asin);
						}
						let c=true;
						$.each(series1,function(index){
							if(this.name==asin){
								series1[index]=x;
								series2[index]=x1;
								c=false;
								return ;
							}
						})
						if(c){
							series1.push(x);
							series2.push(x1);
						}
						
						let series;
						if($("#checkbox1").prop('checked')){
							series=series1;
						}else{
							series=series2;
						}
						
						echart1.setOption(_option1={

						    tooltip: {
						        trigger: 'axis',
						    },
						    legend: {
						        data: legend_name
						    },
						    grid: {
						        left: '10px',
						        right: '120px',
						        top: '40px',
						        bottom: '10px',
						        containLabel: true
						    },
						    xAxis: [{
						        type: 'category',
						        splitLine: {    //网格线
						            show: true,

						        }, 
						        data: xAxis_data
						    }],
						    yAxis: [{
						        type : 'value',minInterval: 1,
						        scale: true,
						        splitArea:{//控制背景呈现条纹状,竖向在xAxis里设置
						            show:false
						        },
						        splitLine: {    //网格线
						            show: false,

						        },
						        name : '评论',
						        min:0            //设置Y轴的最小值为0 就不会出现负轴数据，max设置最大值
						    }],

						    series: series

						},true);
					}
				}else{
					
				}
			}
		
		})
	}
 function getInsertavaStar(){
	showLoading();
	var echart3 = echarts.init(document.getElementById('echart3'),'shine');
	var dataStr = $('#reportrange span').text();
	var d = dataStr.split('至');
	var startTime = d[0].trim();
	var endTime = d[1].trim();
	$.ajax({
		url:path+'/sku/Amazon_SKUAnalysis_ReviewAnalysis_RealtimeAnalysis_InsertavaStar.htm',
		data:{
			asin:asin,
			startTime:startTime,
			endTime:endTime
		},
		type:'post',
		success:function(value){//alert(value)
			closeLoading();
			var data=JSON.parse(value);
			if(data.status=='1'){
				if(data.data.length>0){
					var xAxis_data=[];
					var series_data_5=[];
					var series_data_4=[];
					var series_data_3=[];
					var series_data_2=[];
					var series_data_1=[];
					$.each(data.data,function(index){
						xAxis_data[index]=this.year_month_day;
						series_data_1[index]=this.sku_oneStarNum;
						series_data_2[index]=this.sku_twoStarNum;
						series_data_3[index]=this.sku_threeStarNum;
						series_data_4[index]=this.sku_fourStarNum;
						series_data_5[index]=this.sku_fiveStarNum;
					})
					
					 // 使用刚指定的配置项和数据显示图表。
					echart3.setOption({

					    tooltip : {
					        trigger: 'axis',
					    },
					    legend: {
					        data: ['5星', '4星','3星','2星','1星']
					    },
					    grid: {
					        left: '10px',
					        right: '120px',
					        top: '40px',
					        bottom: '10px',
					        containLabel: true
					    },
					    xAxis: {
					        type: 'category',
					        boundaryGap: ['20%','20%'],
					        data: xAxis_data
					    },
					    yAxis: {
					        type: 'value',minInterval: 1,
					    },
					    series: [
					        {
					            name: '5星',
					            type: 'bar',
					            barMaxWidth:40,
					            stack: '总量',
					            data: series_data_5
					        },
					        {
					            name: '4星',
					            type: 'bar',
					            barMaxWidth:40,
					            stack: '总量',
					            data: series_data_4
					        },
					        {
					            name: '3星',
					            type: 'bar',
					            barMaxWidth:40,
					            stack: '总量',
					            data:series_data_3
					        },
					        {
					            name: '2星',
					            type: 'bar',
					            barMaxWidth:40,
					            stack: '总量',
					            data: series_data_2
					        },
					        {
					            name: '1星',
					            type: 'bar',
					            barMaxWidth:40,
					            stack: '总量',
					            data: series_data_1
					        }
					    ]

					},true);
				}
			}else{
				
			}
		}
	
	})
}
 function checkStar(val){
		var x=parseFloat(val);
		if(Math.round(x*2)%2>0){
			return ' <i class="a-icon a-icon-star a-star-'+parseInt(Math.round(x*2)/2)+'-'+5+'"></i>'
		}else{
			return ' <i class="a-icon a-icon-star a-star-'+parseInt(Math.round(x*2)/2)+'"></i>'
		}
		
	}
$('#reportrange').change(function(){
	 getpinglun();
	 getavaStar();
	 getInsertavaStar();
})

function clearoption(a){
	 
	 let e1=echarts.init(document.getElementById('echart1'),'shine');
		
		let e2=echarts.init(document.getElementById('echart2'),'shine');
		$.each(_asin_str,function(index){
			if(this==a){
				_asin_str.splice(index,1);
				return ;
			}
		})
		$.each(_series,function(index){
			if(this.name==a){
				_series.splice(index,1);
				return ;
			}
		})
		e1.setOption(_option,true);
		$.each(series1,function(index){
			if(this.name==a){
				series1.splice(index,1);
				return ;
			}
		})
		$.each(series2,function(index){
			if(this.name==a){
				series2.splice(index,1);
				return ;
			}
		})
		$.each(legend_name,function(index){
			if(this==a){
				legend_name.splice(index,1);
				return ;
			}
		})
		e1.clear();
		e1.setOption(_option1,true);
		e2.clear();
		e2.setOption(_option,true);
	 
 }

function showData(){
	 if(getURLParameter('asin')!=null){
			sessionStorage.asin=getURLParameter('asin');
			asin=sessionStorage.asin;
		}
	 $("head").append('<title>'+sku+'</title>')
	 Amazon_SKUAnalysis_SkuSearch_Details(asin)
	 SKU_Jingpin(asin)
	 Amazon_MonitoringOverview_SKU_SimilarProducts(asin)
	getRealtimeAnalysis();
	getpinglun()
	getavaStar()
	getInsertavaStar()
}
 function showJINPINData(a,obj){
		if(hasClass(obj, "xz")){
			$(obj).removeClass("xz");
			let e1=echarts.init(document.getElementById('echart1'),'shine');
			
			let e2=echarts.init(document.getElementById('echart2'),'shine');
			$.each(_asin_str,function(index){
				if(this==a){
					_asin_str.splice(index,1);
					return ;
				}
			})
			$.each(_series,function(index){
				if(this.name==a){
					_series.splice(index,1);
					return ;
				}
			})
			e1.setOption(_option,true);
			$.each(series1,function(index){
				if(this.name==a){
					series1.splice(index,1);
					return ;
				}
			})
			$.each(series2,function(index){
				if(this.name==a){
					series2.splice(index,1);
					return ;
				}
			})
			$.each(legend_name,function(index){
				if(this==a){
					legend_name.splice(index,1);
					return ;
				}
			})
			e1.clear();
			e1.setOption(_option1,true);
			e2.clear();
			e2.setOption(_option,true);

			if($("#checkbox1").prop('checked')){
				$("#checkbox1").click();
			}else{
				$("#checkbox2").click();
			}
			return ;
		}
		if($(".xz").size()>0){
	 		//alert("至多选择两个！");
	 		return ;
	 	}else{
	 		$(obj).addClass("xz");
	 	}
	asin=a;
	//Amazon_SKUAnalysis_SkuSearch_Details(asin)
	//getRealtimeAnalysis();
	getpinglun()
	getavaStar()
	//getInsertavaStar()
}
 
function showJINPINData1(a){
	asin=a;
	Amazon_SKUAnalysis_SkuSearch_Details(asin)
	getRealtimeAnalysis();
	getpinglun()
	getavaStar()
	getInsertavaStar()
}
 
 {
	 showData()
 }