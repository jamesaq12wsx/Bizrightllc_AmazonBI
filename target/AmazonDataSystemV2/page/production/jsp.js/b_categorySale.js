var option = {
	    tooltip : {
	        trigger: 'axis',
	        formatter: function (params) {
	        	var res='<div><p>时间：'+params[0].name+'</p></div>' 
	        	for(var i=0;i<params.length;i++){
	        		for(var x=0;x<params.length-1;x++){
	        			if(params[x].data<params[x+1].data){
	                        var temp=params[x];
	                        params[x]=params[x+1];
	                        params[x+1]=temp;
	                    }
	        		}
	        		
	        	}
	        	$.each(params,function(index){
	        		res+='<p>'+this.seriesName+':'+this.data+'</p>'
	        	})
	        	return res;
	        },
	    },
	    legend: {
	    	scrollDataIndex:0,
	        type:'scroll',
	        selected: {},
	        data:[]
	    },
	    grid: {
	        top:'40px',
	        left: '10px',
	        right: '40px',
	        bottom: '10px',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            data : []
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : []
};
 
/*------------------------------------------------------------------------------------------------------*/

var echart1_boolean=true;
var category1_name="";
var category2_name="";
function getCategorySelect(){
	
	echart1 = echarts.init(document.getElementById('echart1'),'shine');
	if(echart1_boolean){
	echart1.on('legendselectchanged', function (obj){
    	var selected = obj.selected;
        var legend = obj.name;
        if(selected[legend]){
        	addCategory(legend)
        }else{
        	option.legend.selected[legend]=false;
        }
    });
	echart1_boolean=false;
	}
	echart1.setOption(option);
	var dataStr = $('#reportrange span').text();
	var d = dataStr.split('至');
	var startTime = d[0].trim();
	var endTime = d[1].trim();
	$.ajax({
		url : path + '/sku/Amazon_SalesAnalysis_CategorySelect.htm',
				type : 'post',
				data : {
					startTime : startTime,
					endTime : endTime
				},
				success:function(value){
					var op=echart1.getOption(option);
					op.series=[];
					var data = JSON.parse(value);
					if (data.status == '1') {
						var json={};
						var legend_data=[];
						$.each(data.category,function(index){
							if(index==1)category1_name=this.category;
							if(index==2)category2_name=this.category;
							var series_data={
						            name:this.category,
						            type:'line',
						            data:[]
						        }
							if(index==0){
								json[this.category]=true;
							}else{
								json[this.category]=false;
							}
							legend_data[index]=this.category;
							op.series.push(series_data);
						})
						
						var date=[];
						var series=[];
						var series_data={
					            name:"",
					            type:'line',
					            data:[]
					        }
						$.each(data.data,function(index){
							series_data.name=this.categoryname;
							date[index]=this.datatime;
							series_data.data[index]=this.xiaoliang;
						})
						op.series[0]=series_data;
						op.legend={
								scrollDataIndex:0,
								type:'scroll',
								selected: json,
								data:legend_data
						}
						
						op.xAxis[0].data=date;
						
						echart1.setOption(op,true);
						option= op;
						addCategory(category1_name,0)
						addCategory(category2_name,0)
					}
				}
	})
}

function addCategory(name,count){
	console.log(option)
	var echart1 = echarts.init(document.getElementById('echart1'),'shine');
	var dataStr = $('#reportrange span').text();
	var d = dataStr.split('至');
	var startTime = d[0].trim();
	var endTime = d[1].trim();
	$.ajax({
		type:'post',
		url:path + '/sku/Amazon_SalesAnalysis_CategorySalesAnalysisDate.htm',
		data : {
			startTime : startTime,
			endTime : endTime,
			category: name
		},
		success:function(value){
			var data = JSON.parse(value);
			if (data.status == '1') {
				var series_data=[];
				$.each(data.data,function(index){
					series_data[index]=this.xiaoliang
				})
				
				
				$.each(option.series,function(index){
					if(this.name==name){
						this.data=series_data;
						if(count==0){
							option.legend.scrollDataIndex=0;
						}else{
							if(index>5){
								option.legend.scrollDataIndex=index-2;
							}else{
								option.legend.scrollDataIndex=index
							}
							
						}
						
					}
				})
				option.legend.selected[name]=true;
			    echart1.setOption(option,true);
			    
			}
		}
	})
}

var page=1;
function getCategorySalesAnalysis(){
	var dataStr = $('#reportrange span').text();
	var d = dataStr.split('至');
	var startTime = d[0].trim();
	var endTime = d[1].trim();
	$.ajax({
		type:'post',
		url:path + '/sku/Amazon_SalesAnalysis_CategorySalesAnalysis.htm',
		data : {
			startTime : startTime,
			endTime : endTime,
			page: page
		},
		success:function(value){//alert(value)
			var data = JSON.parse(value);
			if (data.status == '1') {
				$("#hotRank tbody").html("")
				if(data.data.length>0){
					$("#page").show();
					$("#page").paging({
					      pageNo:page,
					      totalPage: Math.ceil(data.total[0].num/10),
					      totalSize: data.total[0].num,
					      callback:function(value){
					    	  page=value;
					    	  getCategorySalesAnalysis();
					      }
					    }) 
					    $.each(data.data,function(index){
					    	$("#hotRank tbody").append('<tr> <td style="text-align: center;">'+(((page-1)*10)+index+1)+'</td> <td style="text-align: left;">'+this.category+'</td> <td>'+this.xiaoliang+'</td> <td> <div class="progress"> <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: '+this.allxiaoliang_zhanbi+';"> </div> </div> <span>'+this.allxiaoliang_zhanbi+'</span> </td> </tr>')
					    })
					    
				}else{
					$("#page").hide()
					$("#hotRank tbody").append('<tr><td colspan="4">暂无数据！</td></tr>');
				}
				
			}
		}
	})
}
function getBrandSalesAnalysisDate(){
	 var echart3 = echarts.init(document.getElementById('echart3'),'shine');  // 基于准备好的dom，初始化echarts实例
	 // 使用刚指定的配置项和数据显示图表。
	
	
	var dataStr = $('#reportrange1 span').text();
	var d = dataStr.split('至');
	var startTime = d[0].trim();
	var endTime = d[1].trim();
	$.ajax({
		type:'post',
		data : {
			startTime : startTime,
			endTime : endTime
		},
		url:path + '/sku/Amazon_SalesAnalysis_BrandSalesAnalysisDate.htm',
		success:function(value){
			var data = JSON.parse(value);
			if (data.status == '1') {
				var legend_data=[];
			var date=[];
			var series=[]
				$.each(data.data,function(index){
					var series_data = {
				            name:'',
				            type:'line',
				            data:[]
				        };
					if ($.inArray(this.datatime,
							date) == -1) {
						date[date.length] = this.datatime;
					}
					if ($.inArray(this.BrandName,legend_data) == -1) {
						legend_data[legend_data.length] = this.BrandName;
						series_data.name=this.BrandName;
						series_data.data[$.inArray(this.datatime,date)]=this.xiaoliang;
						series.push(series_data);
					}else{
						series[$.inArray(this.BrandName,legend_data)].data[$.inArray(this.datatime,date)]=this.xiaoliang;
					}
					
					
					
					
				})
				
				echart3.setOption({
				    tooltip : {
				        trigger: 'axis',
				    },
				    legend: {
				    	type:'scroll',
				        data:legend_data
				    },
				    grid: {
				        top:'40px',
				        left: '10px',
				        right: '40px',
				        bottom: '10px',
				        containLabel: true
				    },
				    xAxis : [
				        {
				            type : 'category',
				            boundaryGap : false,
				            data : date
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value'
				        }
				    ],
				    series : series
},true);
			}
		}
	})
}
function getBrandSalesAnalysis(){
	var echart4 = echarts.init(document.getElementById('echart4'),'shine');  // 基于准备好的dom，初始化echarts实例
	 // 使用刚指定的配置项和数据显示图表。
	
	var dataStr = $('#reportrange1 span').text();
	var d = dataStr.split('至');
	var startTime = d[0].trim();
	var endTime = d[1].trim();
	$.ajax({
		type:'post',
		data : {
			startTime : startTime,
			endTime : endTime
		},
		url:path + '/sku/Amazon_SalesAnalysis_BrandSalesAnalysis.htm',
		success:function(value){
			var data = JSON.parse(value);
			if (data.status == '1') {
				var ec_data=[];
				$.each(data.data,function(index){
					ec_data.push({value:this.xiaoliang,name:this.BrandName})
				})
				
				echart4.setOption({
				    tooltip: {
				        trigger: 'item',
				        formatter: "{b}: {d}%"
				    },
				    series: [
				        {
				            type:'pie',
				            radius: ['30%', '70%'],
				            avoidLabelOverlap: false,
				            label: {
				                normal: {
				                    show: false,
				                    position: 'center'
				                },
				                emphasis: {
				                    show: true,
				                    textStyle: {
				                        fontSize: '30',
				                        fontWeight: 'bold'
				                    }
				                }
				            },
				            labelLine: {
				                normal: {
				                    show: false
				                }
				            },
				            data:ec_data
				        }
				    ]
},true);
			}
		}
	})
}
{
	getCategorySelect();
	getCategorySalesAnalysis();
	getBrandSalesAnalysisDate();
	getBrandSalesAnalysis();
}
$('#reportrange').change(function(){
	getCategorySelect()
	getCategorySalesAnalysis()
})
$('#reportrange1').change(function(){
	getBrandSalesAnalysisDate();
	getBrandSalesAnalysis()
})