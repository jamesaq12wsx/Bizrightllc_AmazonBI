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
var page=1;
function getComprehensiveAnalysis(){ d
	showLoading();
	var dataStr = $('#reportrange span').text();
	var d = dataStr.split('至');
	var startTime = d[0].trim();
	var endTime = d[1].trim();
	$.ajax({
		url:path+'/sku/Amazon_SKUAnalysis_Operation_ComprehensiveAnalysis.htm',
		data:{
			asin:asin,
			cityCode:$("#cityCode").val(),
			startTime:startTime,
			endTime:endTime,
			page:page
		},
		type:'post',
		success:function(value){//alert(value)
			closeLoading();
			var data=JSON.parse(value);
			if(data.status=='1'){
				$('#offerListTable tbody').html('')
				if(data.data.length>0){
					$("#page_offerList").paging({
					      pageNo:page,
					      totalPage: Math.ceil(data.total[0].num/20),
					      totalSize: data.total[0].num,
					      callback:function(value){
					    	  page=value;
					    	  getComprehensiveAnalysis();
					      }
					    }) 
					$.each(data.data,function(index){
						$('#offerListTable tbody').append('<tr class="" id="bg_'+index+'">  <td> <div class="buyBox"> '+checkbuybox(this.buybox)+' <a href="">'+this.sku_soldBy+'</a> '+checkprime(this.isPrime)+' </div> </td> <td> '+checkOfferStar(this.star,this.sellerRwNum)+'</td><td><span class="red">$'+this.sku_price+'</span></td><td> <span class="btn priceTrend" onclick="showTrendEchart(this,'+index+',\''+this.sku_soldBy+'\')">价格趋势 <b class="caret"></b></span> </td> </tr>')
					})
				}else{
					$('#offerListTable tbody').append('<tr><td colspan="400">暂无数据！</td></tr>');
				}
			}else{
				
			}
		}
	
	})
}
function showTrendEchart(obj,index,sellerName){
	var status=0;
	var dw=index;
	var nextTR=$(obj).parent().parent().next(".dropDownTable");
	var objTR=$(obj).parent().parent();
	if($('#bg_'+index).next(".dropDownTable")[0]==undefined){
		$(".dropDownTable").hide();
		$('#bg_'+dw).after('<tr class="dropDownTable"> <td colspan="5"> <div class="calendar"> <div id="reportrange_'+dw+'" class="mydate" onchange="getPriceTrend('+dw+',\''+sellerName+'\')"> <span>2018-05-29 至 2018-06-04</span> <b class="caret"></b> </div> </div> <div class="priceShow"> <span>最高价: </span> <span class="red" id="max_price_'+index+'">$0</span> </div> <div class="priceShow"> <span>最低价: </span> <span class="green" id="min_price_'+index+'">$0</span> </div> <div class="priceShow"> <span>平均价: </span> <span class="gray" id="avg_price_'+index+'">$0</span> </div> <div id="echart_'+index+'" style="height: 240px;"></div> </td> </tr>');
		status=1;
	}else{
		if(nextTR.css("display")=="none"){
			$(".dropDownTable").hide();
			nextTR.show()
			status=1;
		}else{
			nextTR.hide()
		}
		
	}
	init_daterangepicker();
	if(status==1){
		getPriceTrend(dw,sellerName)
	}
	
}
function getPriceTrend(count,sellerName){
	 var echart= echarts.init(document.getElementById('echart_'+count),'shine');  // 基于准备好的dom，初始化echarts实例
	 // 使用刚指定的配置项和数据显示图表。
	var dataStr = $('#reportrange_'+count+' span').text();
	var d = dataStr.split('至');
	var startTime = d[0].trim();
	var endTime = d[1].trim();
	
	$.ajax({
		url:path+'/sku/Amazon_SKUAnalysis_Operation_ComprehensiveAnalysis_PriceTrend.htm',
		data:{
			asin:asin,
			cityCode:$("#cityCode").val(),
			startTime:startTime,
			endTime:endTime,
			sellerName:sellerName
		},
		type:'post',
		success:function(value){//alert(value)
			var data=JSON.parse(value);
			if(data.status=='1'){
				if(data.data.length>0){
					var xAxis_data=[];
					var series_data=[];
					$.each(data.data,function(index){
						if(index==0){
							$("#max_price_"+count).text(this.max_price);
							$("#min_price_"+count).text(this.min_price);
							$("#avg_price_"+count).text(this.avg_price);
						}
						xAxis_data[index]=this.insertttime;
						series_data[index]=this.price;
					})
					
					echart.setOption({
					    tooltip: {
					        trigger: 'axis'
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
					        type: 'value'
					    },
					    series: [
					        {
					            name:'价格',
					            type:'line',
					            data:series_data,
					            areaStyle: {
					                normal: {
					                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
					                        offset: 0,
					                        color: 'rgba(0, 66, 255,.5)'
					                    }, {
					                        offset: 1,
					                        color: 'rgba(0, 204, 255,.5)'
					                    }])
					                }
					            },
					        }
					    ]

					},true);
				}
			}else{
				
			}
		}
	
	})
}
function checkprime(val){
	if(val==1 || val=='prime' || val=='buybox'){
		return '<i class="a-icon a-icon-prime"></i>'
	}else{
		return ''
	}
}
function checkbuybox(val){
	if(val=='buybox'){
		return '<i class="a-icon a-icon-label a-label-yellowS"></i>'
	}else{
		return ''
	}
}
function checkOfferStar(val,val1){
	if(val1==""){
		return '/'
	}else{
		var x=parseFloat(val);
		if(Math.round(x*2)%2>0){
			return ' <i class="a-icon a-icon-star a-star-'+parseInt(Math.round(x*2)/2)+'-'+5+'"></i><span>'+(val==""?'/':parseInt(val)*20)+'% <span>('+(val1==""?'/':val1)+')</span></span>'
		}else{
			return ' <i class="a-icon a-icon-star a-star-'+parseInt(Math.round(x*2)/2)+'"></i></i><span>'+(val==""?'/':parseInt(val)*20)+'% <span>('+(val1==""?'/':val1)+')</span></span>'
		}
	}
}
function showData(){
	$("head").append('<title>'+sku+'</title>')
	Amazon_SKUAnalysis_SkuSearch_Details(asin)
	SKU_Jingpin(asin)
	Amazon_MonitoringOverview_SKU_SimilarProducts(asin)
	getComprehensiveAnalysis()
}

function showJINPINData1(a){
	asin=a;
	Amazon_SKUAnalysis_SkuSearch_Details(asin);
	SKU_Jingpin(asin);
	Amazon_MonitoringOverview_SKU_SimilarProducts(asin);
	getComprehensiveAnalysis();
}

function showJINPINData(a,obj){
	if(hasClass(obj, "xz")){
		$(obj).removeClass("xz");
		return ;
	}
	if($(".xz").size()>0){
 		//alert("至多选择两个！");
 		return ;
 	}else{
 		$(obj).addClass("xz");
 	}
	 asin=a;
	 Amazon_SKUAnalysis_SkuSearch_Details(asin)
	 getComprehensiveAnalysis()
}
{
	showData()
}