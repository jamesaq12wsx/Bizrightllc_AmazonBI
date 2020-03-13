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
var asin=sessionStorage.asin;//B00NN2RYHY
var sku=sessionStorage.sku_fx;
var _city=[{value:60601,text:"芝加哥"},{value:90001,text:"洛杉矶"},{value:10001,text:"纽约"}];

$("#checkbox1").parent().parent().find('input').click(function(){
	choosecity()
})

function choosecity(){
	asin=sessionStorage.asin;
	if($("#checkbox1").prop('checked')){
		_city_90001()
		 $.each($(".xz"),function(index){
			 asin=$(this).attr("name");
			 _city_90001("triangle")
		 })
	}
	if($("#checkbox2").prop('checked')){
		_city_10001()
		 $.each($(".xz"),function(index){
			 asin=$(this).attr("name");
			 _city_10001("triangle")
		 })
	}
	if($("#checkbox3").prop('checked')){
		_city_60601()
		 $.each($(".xz"),function(index){
			 asin=$(this).attr("name");
			 _city_60601("triangle")
		 })
	}
}

var offer_option_10001={
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:[]
	    },
	    grid: {
	        left: '10px',
	        right: '20px',
	        top: '40px',
	        bottom: '10px',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'category',
	        boundaryGap: ['20%','20%'],
	        data: []
	    },
	    yAxis: {
	        type: 'value',
	        minInterval: 1,
	        name:"offer总数"
	    },
	    series: []
};

var prime_option_10001={
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:[]
	    },
	    grid: {
	        left: '10px',
	        right: '20px',
	        top: '40px',
	        bottom: '10px',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'category',
	        boundaryGap: ['20%','20%'],
	        data: []
	    },
	    yAxis: {
	        type: 'value',
	        minInterval: 1,
	        name:"prime总数"
	    },
	    series: []
};
function _city_10001(){
	showLoading();
	var echart2 = echarts.init(document.getElementById('echart2'),'shine');  // 基于准备好的dom，初始化echarts实例
	var echart22 = echarts.init(document.getElementById('echart22'),'shine');
	var dataStr = $('#reportrange span').text();
	var d = dataStr.split('至');
	var startTime = d[0].trim();
	var endTime = d[1].trim();
	$.ajax({
		url:path+'/sku/Amazon_SKUAnalysis_Operation_offerTotal.htm',
		data:{
			asin:asin,
			cityCode:"10001",
			startTime:startTime,
			endTime:endTime
		},
		async:false,
		type:'post',
		success:function(value){
			var data=JSON.parse(value);
			closeLoading();
			if(data.status=='1'){
				if(data.data.length>0){
					var xAxis_data=[];
					var series_offer_data=[];
					var series_prime_data=[];
					$.each(data.data,function(index){
						xAxis_data[index]=this.year_month_day;
						series_offer_data[index]=this.sumoff=='-'?0:this.sumoff;
						series_prime_data[index]=this.isprimeoff=='-'?0:this.isprimeoff;
					})
					let b=true;
					$.each(offer_option_10001.legend.data,function(index){
						if(this==asin){
							b=false;
						}
					})
					if(b){
						offer_option_10001.legend.data.push(asin);
					}
					let c=true;
					$.each(offer_option_10001.series,function(index){
						if(this.name==asin){
							offer_option_10001.series[index].data=series_offer_data;
							offer_option_10001.xAxis.data=xAxis_data;
							prime_option_10001.series[index].data=series_prime_data;
							prime_option_10001.xAxis.data=xAxis_data;
							c=false;
						}
					})
					if(c){
						offer_option_10001.series.push({
				            name:asin,
				            type:'line',
				            //stack: '总量',
				            data:series_offer_data
				        });
						offer_option_10001.xAxis={
					        type: 'category',
					        boundaryGap: ['20%','20%'],
					        data: xAxis_data
					    }
						prime_option_10001.legend.data.push(asin);
						prime_option_10001.series.push({
				            name:asin,
				            type:'line',
				            //stack: '总量',
				            data:series_prime_data
				        });
				        prime_option_10001.xAxis={
					        type: 'category',
					        boundaryGap: ['20%','20%'],
					        data: xAxis_data
					    }
					}
					echart2.setOption(offer_option_10001,true);
					echart22.setOption(prime_option_10001,true);
				}
			}else{
			}
		}
	})
}

var offer_option_90001={
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:[]
	    },
	    grid: {
	        left: '10px',
	        right: '20px',
	        top: '40px',
	        bottom: '10px',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'category',
	        boundaryGap: ['20%','20%'],
	        data: []
	    },
	    yAxis: {
	        type: 'value',
	        minInterval: 1,
	        name:"offer总数"
	    },
	    series: []
};

var prime_option_90001={
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:[]
	    },
	    grid: {
	        left: '10px',
	        right: '20px',
	        top: '40px',
	        bottom: '10px',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'category',
	        boundaryGap: ['20%','20%'],
	        data: []
	    },
	    yAxis: {
	        type: 'value',
	        minInterval: 1,
	        name:"prime总数"
	    },
	    series: []
};
function _city_90001(symbol){
	showLoading();
	var echart2 = echarts.init(document.getElementById('echart2'),'shine');  // 基于准备好的dom，初始化echarts实例
	var echart22 = echarts.init(document.getElementById('echart22'),'shine');
	var dataStr = $('#reportrange span').text();
	var d = dataStr.split('至');
	var startTime = d[0].trim();
	var endTime = d[1].trim();
	$.ajax({
		url:path+'/sku/Amazon_SKUAnalysis_Operation_offerTotal.htm',
		data:{
			asin:asin,
			cityCode:"90001",
			startTime:startTime,
			endTime:endTime
		},
		async:false,
		type:'post',
		success:function(value){
			var data=JSON.parse(value);
			closeLoading();
			if(data.status=='1'){
				if(data.data.length>0){
					var xAxis_data=[];
					var series_offer_data=[];
					var series_prime_data=[];
					$.each(data.data,function(index){
						xAxis_data[index]=this.year_month_day;
						series_offer_data[index]=this.sumoff=='-'?0:this.sumoff;
						series_prime_data[index]=this.isprimeoff=='-'?0:this.isprimeoff;
					})
					let b=true;
					$.each(offer_option_90001.legend.data,function(index){
						if(this==asin){
							b=false;
						}
					})
					if(b){
						offer_option_90001.legend.data.push(asin);
					}
					let c=true;
					$.each(offer_option_90001.series,function(index){
						if(this.name==asin){
							offer_option_90001.series[index].data=series_offer_data;
							offer_option_90001.xAxis.data=xAxis_data;
							prime_option_90001.series[index].data=series_prime_data;
							prime_option_90001.xAxis.data=xAxis_data;
							c=false;
						}
					})
					if(c){
						offer_option_90001.series.push({
				            name:asin,
				            type:'line',
				            //stack: '总量',
				            data:series_offer_data,
				            symbol:symbol!=undefined?symbol:""
				        });
						offer_option_90001.xAxis={
					        type: 'category',
					        boundaryGap: ['20%','20%'],
					        data: xAxis_data
					    }
						prime_option_90001.legend.data.push(asin);
						prime_option_90001.series.push({
				            name:asin,
				            type:'line',
				            //stack: '总量',
				            data:series_prime_data,
				            symbol:symbol!=undefined?symbol:""
				        });
				        prime_option_90001.xAxis={
					        type: 'category',
					        boundaryGap: ['20%','20%'],
					        data: xAxis_data
					    }
						
					}
					
					
					echart2.setOption(offer_option_90001,true);
					
					echart22.setOption(prime_option_90001,true);
				}
			}else{
			}
		}
	})
}

var offer_option_60601={
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:[]
	    },
	    grid: {
	        left: '10px',
	        right: '20px',
	        top: '40px',
	        bottom: '10px',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'category',
	        boundaryGap: ['20%','20%'],
	        data: []
	    },
	    yAxis: {
	        type: 'value',
	        minInterval: 1,
	        name:"offer总数"
	    },
	    series: []
};

var prime_option_60601={
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:[]
	    },
	    grid: {
	        left: '10px',
	        right: '20px',
	        top: '40px',
	        bottom: '10px',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'category',
	        boundaryGap: ['20%','20%'],
	        data: []
	    },
	    yAxis: {
	        type: 'value',
	        minInterval: 1,
	        name:"prime总数"
	    },
	    series: []
};
function _city_60601(){
	showLoading();
	var echart2 = echarts.init(document.getElementById('echart2'),'shine');  // 基于准备好的dom，初始化echarts实例
	var echart22 = echarts.init(document.getElementById('echart22'),'shine');
	var dataStr = $('#reportrange span').text();
	var d = dataStr.split('至');
	var startTime = d[0].trim();
	var endTime = d[1].trim();
	$.ajax({
		url:path+'/sku/Amazon_SKUAnalysis_Operation_offerTotal.htm',
		data:{
			asin:asin,
			cityCode:"60601",
			startTime:startTime,
			endTime:endTime
		},
		async:false,
		type:'post',
		success:function(value){
			var data=JSON.parse(value);
			closeLoading();
			if(data.status=='1'){
				if(data.data.length>0){
					var xAxis_data=[];
					var series_offer_data=[];
					var series_prime_data=[];
					$.each(data.data,function(index){
						xAxis_data[index]=this.year_month_day;
						series_offer_data[index]=this.sumoff=='-'?0:this.sumoff;
						series_prime_data[index]=this.isprimeoff=='-'?0:this.isprimeoff;
					})
					let b=true;
					$.each(offer_option_60601.legend.data,function(index){
						if(this==asin){
							b=false;
						}
					})
					if(b){
						offer_option_60601.legend.data.push(asin);
					}
					let c=true;
					$.each(offer_option_60601.series,function(index){
						if(this.name==asin){
							offer_option_60601.series[index].data=series_offer_data;
							offer_option_60601.xAxis.data=xAxis_data;
							prime_option_60601.series[index].data=series_prime_data;
							prime_option_60601.xAxis.data=xAxis_data;
							c=false;
						}
					})
					if(c){
						offer_option_60601.series.push({
				            name:asin,
				            type:'line',
				            //stack: '总量',
				            data:series_offer_data
				        });
						offer_option_60601.xAxis={
					        type: 'category',
					        boundaryGap: ['20%','20%'],
					        data: xAxis_data
					    }
						prime_option_60601.legend.data.push(asin);
						prime_option_60601.series.push({
				            name:asin,
				            type:'line',
				            //stack: '总量',
				            data:series_prime_data
				        });
				        prime_option_60601.xAxis={
					        type: 'category',
					        boundaryGap: ['20%','20%'],
					        data: xAxis_data
					    }
						
					}
					echart2.setOption(offer_option_60601,true);
					echart22.setOption(prime_option_60601,true);
				}
			}else{
			}
		}
	})
}

function getoffernum(){
	$(".overView").html('');
	$.each(_city,function(index){
		var val=this.value;
		$(".overView").append('<div class="overViewP"> <div> <p id="totalNum_'+this.value+'"></p> <span>offer</span> </div> <div> <span style="font-size: 16px;">'+this.text+'</span><br> <span id="primeNum_'+this.value+'"></span></span> </div> </div>');
		$.ajax({
			url:path+'/sku/Amazon_SKUAnalysis_Operation_offernum.htm',
			data:{
				asin:asin,
				cityCode:this.value
			},
			type:'post',
			success:function(value){
				var data=JSON.parse(value);
				if(data.status=='1'){
					if(data.data.length>0){
						$("#totalNum_"+val).html(data.data[0].sumoff);
						$("#primeNum_"+val).append(data.data[0].isprimeoff+'<i class="a-icon a-icon-prime"></i>');
					}
				}else{
					
				}
			}
		
		})
	})
}

var offerTotal_option = {
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:[]
	    },
	    grid: {
	        left: '10px',
	        right: '20px',
	        top: '40px',
	        bottom: '10px',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'category',
	        boundaryGap: ['20%','20%'],
	        data: []
	    },
	    yAxis: {
	        type: 'value',
	        minInterval: 1,
	        name:"offer总数"
	    },
	    series: []
	};
var primeTotal_option = {
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:[]
	    },
	    grid: {
	        left: '10px',
	        right: '20px',
	        top: '40px',
	        bottom: '10px',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'category',
	        boundaryGap: ['20%','20%'],
	        data: []
	    },
	    yAxis: {
	        type: 'value',
	        minInterval: 1,
	        name:"prime总数"
	    },
	    series: []
	};
function getofferTotal(){return ;
	showLoading();
	var echart2 = echarts.init(document.getElementById('echart2'),'shine');  // 基于准备好的dom，初始化echarts实例
	var echart22 = echarts.init(document.getElementById('echart22'),'shine');
	var dataStr = $('#reportrange span').text();
	var d = dataStr.split('至');
	var startTime = d[0].trim();
	var endTime = d[1].trim();
	offerTotal_option.series.splice(0,offerTotal_option.series.length);
	primeTotal_option.series.splice(0,primeTotal_option.series.length);
	$.each(_city,function(index){
		var val=this.value;
		var txt=this.text;
		$.ajax({
			url:path+'/sku/Amazon_SKUAnalysis_Operation_offerTotal.htm',
			data:{
				asin:asin,
				cityCode:this.value,
				startTime:startTime,
				endTime:endTime
			},
			type:'post',
			success:function(value){
				var data=JSON.parse(value);
				closeLoading();
				if(data.status=='1'){
					if(data.data.length>0){
						var xAxis_data=[];
						var series_offer_data=[];
						var series_prime_data=[];
						$.each(data.data,function(index){
							xAxis_data[index]=this.year_month_day;
							series_offer_data[index]=this.sumoff=='-'?0:this.sumoff;
							series_prime_data[index]=this.isprimeoff=='-'?0:this.isprimeoff;
						})
						offerTotal_option.legend.data.push(txt);
						offerTotal_option.series.push({
					            name:txt,
					            type:'line',
					            //stack: '总量',
					            data:series_offer_data
					        })
					    offerTotal_option.xAxis={
						        type: 'category',
						        boundaryGap: ['20%','20%'],
						        data: xAxis_data
						    }
						echart2.setOption(offerTotal_option,true);
						primeTotal_option.legend.data.push(txt);
						primeTotal_option.series.push({
				            name:txt,
				            type:'line',
				            //stack: '总量',
				            data:series_prime_data
				        })
				        primeTotal_option.xAxis={
					        type: 'category',
					        boundaryGap: ['20%','20%'],
					        data: xAxis_data
					    }
						echart22.setOption(primeTotal_option,true);
					}
				}else{
				}
			}
		})
	})
}
function checkStar(val){
	var x=parseFloat(val==""?0:val);
	if(Math.round(x*2)%2>0){
		return ' <i class="a-icon a-icon-star a-star-'+parseInt(Math.round(x*2)/2)+'-'+5+'"></i>'
	}else{
		return ' <i class="a-icon a-icon-star a-star-'+parseInt(Math.round(x*2)/2)+'"></i>'
	}
	
}
function checkprime(val){
	if(val==1 || val=='prime' || val=='buybox'){
		return '<i class="a-icon a-icon-prime"></i>'
	}else{
		return ''
	}
}
var cityCode=90001;
function getofferChange(cityCode){
	showLoading();
	cityCode=cityCode;
	var dataStr = $('#reportrange span').text();
	var d = dataStr.split('至');
	var startTime = d[0].trim();
	var endTime = d[1].trim();
	$.ajax({
		url:path+'/sku/Amazon_SKUAnalysis_Operation_offerChange.htm',
		data:{
			asin:asin,
			cityCode:cityCode,
			startTime:startTime,
			endTime:endTime
		},
		type:'post',
		success:function(value){
			closeLoading();
			var data=JSON.parse(value);
			if(data.status=='1'){
				if(data.data.length>0){
				$("#offerChange tbody").html('');
				var dataTime;
				$.each(data.data,function(index){
					if(index==0){
						dataTime=this.inserttime;
						$("#offerChange tbody").append('<tr> <td style="vertical-align: top;" id="add_'+this.inserttime+'" value="'+this.inserttime+'"> </td> <td style="vertical-align: top;" id="del_'+this.inserttime+'" value="'+this.inserttime+'"> </td> <td>'+this.inserttime+'</td> </tr>');
					}
					if(dataTime!=this.inserttime){
						var time=this.inserttime;
						var zt=true;
						$.each($("#offerChange tbody tr"),function(index){
							//alert($(this).find('td:last').text())
							if($(this).find('td:last').text()==time){
								zt = false;
							}
						})
						if(zt){
							dataTime=this.inserttime;
							$("#offerChange tbody").append('<tr> <td style="vertical-align: top;" id="add_'+this.inserttime+'" value="'+this.inserttime+'"> </td> <td style="vertical-align: top;" id="del_'+this.inserttime+'" value="'+this.inserttime+'"> </td> <td>'+this.inserttime+'</td> </tr>');
						}
					}
					if(this.shangxiajia==1){
						$("#add_"+this.inserttime).append('<div class="offerList"> <div> <i class="a-icon a-icon-plus"></i> <a>'+this.sellerName+'</a> '+checkprime(this.isPrime)+' <span class="red pull-right">$'+this.price+'</span> </div> <div>  '+checkStar(this.star)+'<span>'+(this.star==""?0:parseInt(this.star)*20)+'% <span>('+(this.sellerRwNum==""?0:this.sellerRwNum)+')</span></span> </div> </div>')
					}else{
						$("#del_"+this.inserttime).append('<div class="offerList"> <div> <i class="a-icon a-icon-reduce"></i> <a>'+this.sellerName+'</a> '+checkprime(this.isPrime)+' <span class="red pull-right">$'+this.price+'</span> </div> <div>'+checkStar(this.star)+'  <span>'+(this.star==""?0:parseInt(this.star)*20)+'% <span>('+(this.sellerRwNum==""?0:this.sellerRwNum)+')</span></span> </div> </div>')
					}
				})
			}else{
				$("#offerChange tbody").html('<tr><td colspan="4">暂无数据</td></tr>');
			}
			}else{
			}
		}
	
	})
}

$("#reportrange").change(function(){
	choosecity();
	getofferChange(cityCode);
})
function showData(){
	$("head").append('<title>'+sku+'</title>')
	Amazon_SKUAnalysis_SkuSearch_Details(asin)
	SKU_Jingpin(asin)
	Amazon_MonitoringOverview_SKU_SimilarProducts(asin)
	getoffernum()
	choosecity()
}

function showJINPINData1(a){
	asin=a;
	Amazon_SKUAnalysis_SkuSearch_Details(asin)
	SKU_Jingpin(asin)
	Amazon_MonitoringOverview_SKU_SimilarProducts(asin)
	getoffernum()
	choosecity()
}

function showJINPINData(a,obj){
	if(hasClass(obj, "xz")){
		 $.each($(".xz"),function(index){
			 let a=$(this).attr("name");
				$.each(offer_option_10001.series,function(index){
					if(this.name==a){
						offer_option_10001.series.splice(index,1);
						return ;
					}
				})
				$.each(prime_option_10001.series,function(index){
					if(this.name==a){
						prime_option_10001.series.splice(index,1);
						return ;
					}
				})
				$.each(offer_option_90001.series,function(index){
					if(this.name==a){
						offer_option_90001.series.splice(index,1);
						return ;
					}
				})
				$.each(prime_option_90001.series,function(index){
					if(this.name==a){
						prime_option_90001.series.splice(index,1);
						return ;
					}
				})
				$.each(offer_option_60601.series,function(index){
					if(this.name==a){
						offer_option_60601.series.splice(index,1);
						return ;
					}
				})
				$.each(prime_option_60601.series,function(index){
					if(this.name==a){
						prime_option_60601.series.splice(index,1);
						return ;
					}
				})
		 })
		$(obj).removeClass("xz");
		if($("#checkbox1").prop('checked')){
			let echart2 = echarts.init(document.getElementById('echart2'),'shine');  // 基于准备好的dom，初始化echarts实例
			let echart22 = echarts.init(document.getElementById('echart22'),'shine');
			echart2.clear();
			echart22.clear();
			echart2.setOption(offer_option_90001,true);
			echart22.setOption(prime_option_90001,true);
		}
		if($("#checkbox2").prop('checked')){
			let echart2 = echarts.init(document.getElementById('echart2'),'shine');  // 基于准备好的dom，初始化echarts实例
			let echart22 = echarts.init(document.getElementById('echart22'),'shine');
			echart2.clear();
			echart22.clear();
			echart2.setOption(offer_option_10001,true);
			echart22.setOption(prime_option_10001,true);
		}
		if($("#checkbox3").prop('checked')){
			let echart2 = echarts.init(document.getElementById('echart2'),'shine');  // 基于准备好的dom，初始化echarts实例
			let echart22 = echarts.init(document.getElementById('echart22'),'shine');
			echart2.clear();
			echart22.clear();
			echart2.setOption(offer_option_60601,true);
			echart22.setOption(prime_option_60601,true);
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
	 choosecity()
}
{
	showData()
}