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
 var seriesJsonArray_90001=[];
 var seriesJsonArray_10001=[];
 var seriesJsonArray_60601=[];

 var name_90001=[];
 var name_10001=[];
 var name_60601=[];
 function clearbuyBoxOffer(){
	 seriesJsonArray_90001=[];
	 seriesJsonArray_10001=[];
	 seriesJsonArray_60601=[];

	 name_90001=[];
	 name_10001=[];
	 name_60601=[];
	 seriesJsonArray_leimu=[];
	 _min=0;
	 _max=0;
	 Rank_name=[];
 }
 var _min_90001=0;
 var _min_60601=0;
 var _min_10001=0;
 function buyBoxOffer(cityCode,ec){//洛杉矶
	 let legend_data_90001=new Object();
	 let legend_data_10001=new Object();
	 let legend_data_60601=new Object();
	 	let echart1 = echarts.init(document.getElementById(ec),'shine');  // 基于准备好的dom，初始化echarts实例
		let dateTime=$('#datetimepicker').val().split('-')[0]+$('#datetimepicker').val().split('-')[1]+'01';
		$.ajax({
			url:path+'/sku/Amazon_SKUAnalysis_Operation_buyboxofferAnalysis.htm',
			type:'post',
			data:{
				asin:asin,
				cityCode:cityCode,
				dateTime:dateTime
			},
			success:function(value){
				let data=JSON.parse(value);
				if(data.status=='1'){
					let _min_buyBoxOffer=0;
					if(cityCode==90001){
						$.each(data.data,function(index){
							if(index==0 &&_min_90001==0){
								_min_90001=parseInt(this.sku_price);
							}else{
								if(parseInt(this.sku_price)<=parseInt(_min_90001)){
									_min_90001=parseInt(this.sku_price)
								}
							}
						})
						_min_buyBoxOffer=_min_90001;
					}
					if(cityCode==10001){
						$.each(data.data,function(index){
							if(index==0 &&_min_10001==0){
								_min_10001=parseInt(this.sku_price);
							}else{
								if(parseInt(this.sku_price)<=parseInt(_min_10001)){
									_min_10001=parseInt(this.sku_price)
								}
							}
						})
						_min_buyBoxOffer=_min_10001;
					}
					if(cityCode==60601){
						$.each(data.data,function(index){
							if(index==0 &&_min_60601==0){
								_min_60601=parseInt(this.sku_price);
							}else{
								if(parseInt(this.sku_price)<=parseInt(_min_60601)){
									_min_60601=parseInt(this.sku_price)
								}
							}
						})
						_min_buyBoxOffer=_min_60601;
					}
					//option.yAxis.min=min;
					
					let name="";
					$.each(data.data,function(index){
						if(this.sku_soldBy!='-'){
							if(name.indexOf(this.sku_soldBy) == -1 ){
								name=name+'@'+this.sku_soldBy;
							}
							
						}
					})
					let legend_data=name.split('@');
					legend_data.splice(0, 1);
					let series_data=[];
					let seriesJson={
					        name:name,
					        symbolSize: 10,
					        data: clear_arr_trim(series_data),
					        type: 'scatter'
					}
					$.each(legend_data,function(index){
						$.each(data.data,function(index){
							if(this.sku_soldBy!='-'){
								series_data[index]=[this.year_month_day.split('-')[2], this.sku_price,this.sku_soldBy,asin];
							}
						})
					})
					$.each(legend_data,function(index){
						let lsr_data=[];
						$.each(series_data,function(i){
							if(series_data[i]!=undefined){
								if(series_data[i][2]==legend_data[index]){
									lsr_data[i]=series_data[i];
								}
							}
						})
					x={
					        name:legend_data[index],
					        ASIN:asin,
					        symbolSize: 10,
					        data: lsr_data,
					        type: 'scatter'
					    }
						if(cityCode==90001){
							 let b=true;
							 let c;
							 $.each(seriesJsonArray_90001,function(index){
								 if(seriesJsonArray_90001[index].ASIN==asin&& seriesJsonArray_90001[index].name==x.name){
									 seriesJsonArray_90001[index].data=lsr_data;
									 c=index;
									 b=false;
									 return ;
								 }
							 })
							 if(b){
								 seriesJsonArray_90001.push(x); 
							 }else{
								 //seriesJsonArray_90001.splice(index, 1);
							 }
						}
						if(cityCode==10001){
							let b=true;
							let c;
							 $.each(seriesJsonArray_10001,function(index){
								 if(seriesJsonArray_10001[index].ASIN==asin && seriesJsonArray_10001[index].name==x.name){
									 seriesJsonArray_10001[index].data=lsr_data;
									 c=index;
									 b=false;
									 return ;
								 }
							 })
							 if(b){
								 seriesJsonArray_10001.push(x);
							 }else{
								 //seriesJsonArray_10001.splice(index, 1);
							 }
						}
						if(cityCode==60601){
							let b=true;
							let c;
							 $.each(seriesJsonArray_60601,function(index){
								 if(seriesJsonArray_60601[index].ASIN==asin&& seriesJsonArray_60601[index].name==x.name){
									 seriesJsonArray_60601[index].data=lsr_data;
									 c=index;
									 b=false;
									 return ;
								 }
							 })
							 if(b){
								 seriesJsonArray_60601.push(x);
							 }else{
								 //seriesJsonArray_60601.splice(index, 1);
							 }
						}
					})
					
					if(cityCode==90001){
						legend_data_90001.name=legend_data;
						legend_data_90001.asin=asin;
						name_90001.push(legend_data_90001);
					}
					if(cityCode==10001){
						legend_data_10001.name=legend_data;
						legend_data_10001.asin=asin;
						name_10001.push(legend_data_10001);
					}
					if(cityCode==60601){
						legend_data_60601.name=legend_data;
						legend_data_60601.asin=asin;
						name_60601.push(legend_data_60601);
					}
					option={
						    grid: {
						        left: '10px',
						        right: '20px',
						        top: '40px',
						        bottom: '10px',
						        containLabel: true
						    },
						    tooltip: {
						        trigger: 'item',
						        formatter:function (params) {
						        	/*let tip="";
						        	$.each(params,function(index){
						        		tip=tip+'<span>'+params[index].data[0]+"日-"+params[index].data[2]+"-"+params[index].data[3]+"-"+params[index].data[1]+'</span><br>'
						        	})
						        	return tip;*/
						        	return '<span>'+params.data[0]+"日-"+params.data[3]+"-"+params.data[1]+'</span><br>'
						      }
						  },
						    xAxis : [
						        {   
						            //name:'日期',
						            type: 'value',
						            scale: true,                   //值为false会显示x会从0轴显示
						            splitNumber: 31,//getLastDay($('#datetimepicker').val().split('-')[0],$('#datetimepicker').val().split('-')[1])
						            min:1,
						            max:31,//getLastDay($('#datetimepicker').val().split('-')[0],$('#datetimepicker').val().split('-')[1])
						            splitArea:{                    //控制背景呈现条纹状,竖向在xAxis里设置
						                show:false,
						            },
						        }
						    ],
						    yAxis: {
						    	name:(cityCode==90001?"洛杉矶":cityCode==10001?"纽约":"芝加哥"),
						    	min:_min_buyBoxOffer,
						    },
						    legend: {
						        itemGap:30,
						        itemWidth:12,
						        data:legend_data_name(cityCode)
						    },
						    series: cityCode==90001?seriesJsonArray_90001:cityCode==10001?seriesJsonArray_10001:seriesJsonArray_60601
						}
					
					echart1.setOption(option,true);
				}else{
					echart1.setOption({},true);
				}
			}
		
		})
	}
 
function legend_data_name(cityCode){
	 	let name=[];
	 	if(cityCode==90001){
			$.each(name_90001,function(index){
				name.push.apply(name,this.name);
			})
		}
		if(cityCode==10001){
			$.each(name_10001,function(index){
				name.push.apply(name,this.name);
			})
		}
		if(cityCode==60601){
			$.each(name_60601,function(index){
				name.push.apply(name,this.name);
			})
		}
		return name;
 }
 
 $('#datetimepicker').change(function(){
	 var seriesJsonArray_90001=[];
	 var seriesJsonArray_10001=[];
	 var seriesJsonArray_60601=[];

	 var name_90001=[];
	 var name_10001=[];
	 var name_60601=[];
	 var seriesJsonArray_leimu=[];
	 var _min=0;
	 var _max=0;
	 	asin=sessionStorage.asin;
		buyBoxOffer(90001,'echart1')
		buyBoxOffer(10001,'echart13')
		buyBoxOffer(60601,'echart14')
		getCategoryRank();
		buyBoxOfferChange(90001)
	 $.each($(".xz"),function(index){
		asin=$(this).attr("name")
		buyBoxOffer(90001,'echart1')
		buyBoxOffer(10001,'echart13')
		buyBoxOffer(60601,'echart14')
		getCategoryRank();
		buyBoxOfferChange(90001)
	 })
		
 })
 $("#checkbox1").parent().parent().find('input').click(function(){
		if(this.checked){
			asin=sessionStorage.asin;
			if($(this).next().text()=="洛杉矶"){
				$("#echart1").show(500);//,buyBoxOffer(90001,'echart1')
				buyBoxOffer(90001,'echart1')
				$.each($(".xz"),function(index){
					asin=$(this).attr("name")
					buyBoxOffer(90001,'echart1')
					getCategoryRank();
				})
			}
			if($(this).next().text()=="纽约"){
				$("#echart13").show(500);//,buyBoxOffer(10001,'echart13')
				buyBoxOffer(10001,'echart13')
				$.each($(".xz"),function(index){
					asin=$(this).attr("name")
					buyBoxOffer(10001,'echart13')
					getCategoryRank();
				})
			}
			if($(this).next().text()=="芝加哥"){
				$("#echart14").show(500);//,buyBoxOffer(60601,'echart14')
				buyBoxOffer(60601,'echart14')
				$.each($(".xz"),function(index){
					asin=$(this).attr("name")
					buyBoxOffer(60601,'echart14')
					getCategoryRank();
				})
			}
		}else{
			if($(this).next().text()=="洛杉矶"){
				$("#echart1").hide(500)
			}
			if($(this).next().text()=="纽约"){
				$("#echart13").hide(500)
			}
			if($(this).next().text()=="芝加哥"){
				$("#echart14").hide(500)
			}
		}
	})
var seriesJsonArray_leimu=[];
var option_e12;
var _min=0;
var _max=0;
var Rank_name=[];
function getCategoryRank(){
	showLoading();
	var echart12 = echarts.init(document.getElementById('echart12'),'shine');  // 基于准备好的dom，初始化echarts实例
	var dateTime=$('#datetimepicker').val().split('-')[0]+$('#datetimepicker').val().split('-')[1]+'01';
	$.ajax({
		url:path+'/sku/Amazon_SKUAnalysis_Operation_buyboxofferAnalysis_CategoryRank.htm',
		data:{
			asin:asin,
			dateTime:dateTime
		},
		type:'post',
		success:function(value){
			var data=JSON.parse(value);
			closeLoading();
			if(data.status=='1'){
				if(data.data.length>0){
					var xAxis_data=[];
					var series_offer_data=[];
					var min=0;
					var max=0;
					var pd=true;
					$.each(data.data,function(index){
						if(this.rank!='-' && pd){
							min=parseInt(this.rank);
							max=parseInt(this.rank);
							pd=false;
						}
						xAxis_data[index]=this.year_month_day.split('-')[2];
						series_offer_data[index]=this.rank;//=='-'?0:this.rank;
						if(parseInt(this.rank)<parseInt(min) && this.rank!='-'){
							min=parseInt(this.rank)
						}
						if(parseInt(this.rank)>parseInt(max) && this.rank!='-'){
							max=parseInt(this.rank)
						}
						if(this.categoryName!='-'){
							$("#leimuName").html(this.categoryName);
						}
					})
					let b=true;
					$.each(seriesJsonArray_leimu,function(index){
							if(seriesJsonArray_leimu[index].name==asin){
								seriesJsonArray_leimu[index].data=series_offer_data;
								b=false;
								return;
							}
					})
					if(b){
						seriesJsonArray_leimu.push({
							name:asin,
					        data: series_offer_data,
					        type: 'line',
					        smooth: true
					    });
					}
					if(_min==0)_min=min;
					if(_max==0)_max=max;
					_min=_min>=min?min:_min;
					_max=_max>=max?_max:max;
					Rank_name.push(asin);
					option_e12={
						    grid: {
						        left: '10px',
						        right: '120px',
						        top: '40px',
						        bottom: '10px',
						        containLabel: true
						    },
						    tooltip: {
						        trigger: 'axis',
						        formatter:function (params) {
						        	console.log(params)
						        	let tip="";
						        	$.each(params,function(index){
						        		tip=tip+'<span>'+params[index].seriesName+"-"+params[index].axisValue+'日-第'+params[index].data+'名</span><br>'
						        	})
						        	return tip;

						      }
						  },
						  legend:{
						        data:Rank_name
						    },
						    xAxis: {
						        type: 'category',
						        name:'日期',
						        axisTick: {
						              show: true,
						              inside:true
						          },
						          axisLine: {onZero: false},
						        data: xAxis_data
						    },
						    yAxis: {
						        type: 'value',
						        inverse:true,
						        scale:true,
						        min:_min,
						        max:_max
						    },
						    series: seriesJsonArray_leimu
						    }
					echart12.setOption(option_e12,true);	
				}else{

				}
			}else{
				
			}
		}
	
	})
}
 
 $("#cityCode_checkbox1").parent().parent().find('input').click(function(){
		if(this.checked){
			if($(this).next().text()=="洛杉矶"){
				buyBoxOfferChange(90001)
			}
			if($(this).next().text()=="纽约"){
				buyBoxOfferChange(10001)
			}
			if($(this).next().text()=="芝加哥"){
				buyBoxOfferChange(60601)
			}
		}
	})
 
function buyBoxOfferChange(cityCode){
		let dateTime=$('#datetimepicker').val().split('-')[0]+$('#datetimepicker').val().split('-')[1]+'01';
		$.ajax({
			url:path+'/sku/Amazon_SKUAnalysis_Operation_buyboxofferChange.htm',
			data:{
				asin:asin,
				cityCode:cityCode,
				dateTime:dateTime
			},
			type:'post',
			success:function(value){
				var data=JSON.parse(value);
				if(data.status=='1'){
					$("#buyBoxOfferChange tbody").html('');
					if(data.data.length>0){
						$.each(data.data,function(index){
							$("#buyBoxOfferChange tbody").append('<tr> <td>'+
									this.insertTime+'</td> <td style="text-align: left;"> <div class="oldName">'+
									(this.yesterday_sku_soldBy==""?"/":this.yesterday_sku_soldBy)+'</div> <div class="newName"><a href="">'+
									(this.sku_soldBy==""?"/":this.sku_soldBy)+'</a></div> </td> <td> <div class="todayPriceO"> <span>$'+
									this.sku_soldBy_price2+' </span> </div> <div class="todayPriceN"><i class="a-icon a-icon-label a-label-yellow"></i>  <span>$'+
									this.sku_soldBy_price+' </span> </div> </td> <td> <div class="prePriceO"><i class="a-icon a-icon-label a-label-gray"></i>  <span>$'+
									this.yesterday_sku_price2+' </span> </div> <div class="prePriceN"> <span>$'+
									this.yesterday_sku_price+' </span> <div> <p></p> </div></div></td> </tr>');
						})
					}else{
						$("#buyBoxOfferChange tbody").html('<tr><td colspan="400">暂无数据</td></tr>');
					}
					
				}else{
					
				}
			}
		
		})
	}
 function showData(){
	 	if(getURLParameter('asin')!=null){
			sessionStorage.asin=getURLParameter('asin');
			asin=sessionStorage.asin;
			sessionStorage.sku_fx=getURLParameter('sku');
			sku=sessionStorage.sku_fx;
		}
	  	$("head").append('<title>'+sku+'</title>')
	 	Amazon_SKUAnalysis_SkuSearch_Details(asin)
		SKU_Jingpin(asin)
		Amazon_MonitoringOverview_SKU_SimilarProducts(asin)
		buyBoxOffer(90001,'echart1')
		buyBoxOffer(10001,'echart13')
		buyBoxOffer(60601,'echart14')
		getCategoryRank();
		buyBoxOfferChange(90001)
 }
 function showJINPINData1(a){
	 	asin=a;
	 	Amazon_SKUAnalysis_SkuSearch_Details(asin)
		SKU_Jingpin(asin)
		Amazon_MonitoringOverview_SKU_SimilarProducts(asin)
		buyBoxOffer(90001,'echart1')
		buyBoxOffer(10001,'echart13')
		buyBoxOffer(60601,'echart14')
		getCategoryRank();
		buyBoxOfferChange(90001)
}

 function showJINPINData(a,obj){
	 asin=sessionStorage.asin;
	 	if(hasClass(obj, "xz")){
			$(obj).removeClass("xz");
			clearbuyBoxOffer();
			option.yAxis.name="洛杉矶";
			option.legend.data=legend_data_name(90001);
			echarts.init(document.getElementById("echart1"),'shine').clear();
			option.yAxis.name="纽约";
			option.legend.data=legend_data_name(10001);
			echarts.init(document.getElementById("echart13"),'shine').clear();
			option.yAxis.name="芝加哥";
			option.legend.data=legend_data_name(60601);
			echarts.init(document.getElementById("echart14"),'shine').clear();
			echarts.init(document.getElementById("echart12"),'shine').clear();
			buyBoxOffer(90001,'echart1')
			buyBoxOffer(10001,'echart13')
			buyBoxOffer(60601,'echart14')
			getCategoryRank();
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
		buyBoxOffer(90001,'echart1')
		buyBoxOffer(10001,'echart13')
		buyBoxOffer(60601,'echart14')
		getCategoryRank();
		buyBoxOfferChange(90001)
 }
 {
	
	showData()
 }