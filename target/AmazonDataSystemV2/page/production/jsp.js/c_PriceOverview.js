$(".competitive:contains('显示更多')").show();
      function showALL(obj){
        var flag=$(obj).attr("data-value");
        if(flag==1){
          $(obj).parent().parent().siblings(".competitive").show();
          $(obj).attr("data-value","0")
        }else{
          $(obj).parent().parent().siblings(".competitive").hide();
          $(obj).attr("data-value","1")
        }
      }
      function showList(obj){
        $(obj).children("ul").show();
      }
      
      function showData(){
    	  page=1;
    	  Amazon_MonitoringOverview_PriceOverview(getParameter())
    	}

    	function Amazon_MonitoringOverview_PriceOverview(v){
    		showLoading();
    		$("#table tbody").html('<tr><td colspan="100">loading....</td></tr>');
    		$.ajax({
    			url:path+'/sku/Amazon_MonitoringOverview_PriceOverview.htm',
    			type:"POST",
    			data:{
    				top:v._top,
    				keyword:v.input_value,
    				brand:v.brand,
    				SubCategory:v.category,
    				tag:v.tag,
    				date:$("#dataStr").val()
    			},
    			success:function(value){
    				closeLoading();
    				var data = JSON.parse(value);
    				if (data.status == '1') {$("#tableList").html("")
						var competitive_status=1;
					var table_str='';
					var m=false;
					var n=false;
					$.each(data.data,function(count) {
						if(this.isMain==1){
							if(m){
								table_str=table_str+'</tbody></table></div>';
								$("#tableList").append(table_str);
								n=false
							}
							m=true;
							table_str='<div class="tableBox"> <table class="table"> <tbody> <colgroup> <col style="width:100px;"> <col style="width:60px;"> <col style="width:100px;"> <col style="width:100px;"> <col style="width:100px;"> <col style="width:300px;"> <col style="width:100px;"> <col style="width:100px;"> <col style="width:100px;"> </colgroup>';
							table_str=table_str+'<tr> <td rowspan="1000" style="border-bottom:none;border-right:1px solid #ddd;text-align:left">'+this.asin+'</td> <td><span class="zubie">'+"我的"+'</span></td> <td style="text-align: left;">'+this.asin+'</td><td style="text-align: left;">'+this.sku_productTitle+'</td> <td style="text-align: left;">'+this.brand+'</td> <td style="text-align: left;" class="productName"> <span>'+(this.sku_soldBy!=""?this.sku_soldBy:'-')+'</span> '+(this.Prime == 1 ? '<i class="a-icon a-icon-prime"></i>'+'<i class="a-icon a-icon-label a-label-yellowS"></i>':'')+'<td>'+(this.sku_price!=""?this.sku_price:'-')+'</td> <td>'+"-"+'</td> <td> </td> </tr>';
						}else{
							if(n){
								table_str=table_str+'<tr class="competitive"> <td><span class="zubie">'+"竞品"+'</span></td> <td style="text-align: left;">'+this.asin+'</td><td style="text-align: left;">'+this.sku_productTitle+'</td> <td style="text-align: left;">'+this.brand+'</td> <td style="text-align: left;" class="productName"> <span>'+(this.sku_soldBy!=""?this.sku_soldBy:'-')+'</span> '+(this.Prime == 1 ? '<i class="a-icon a-icon-prime"></i>'+'<i class="a-icon a-icon-label a-label-yellowS"></i>':'')+ '</td> <td>'+(this.sku_price!=""?this.sku_price:'')+'</td> <td>'+"-"+'</td> <td></td> </tr>';
							}else{
							n=true;
							table_str=table_str+'<tr > <td><span class="zubie">'+"竞品"+'</span></td> <td style="text-align: left;">'+this.asin+'</td><td style="text-align: left;">'+this.sku_productTitle+'</td> <td style="text-align: left;">'+this.brand+'</td> <td style="text-align: left;" class="productName"> <span>'+(this.sku_soldBy!=""?this.sku_soldBy:'-')+'</span> '+ (this.Prime == 1 ? '<i class="a-icon a-icon-prime"></i>'+'<i class="a-icon a-icon-label a-label-yellowS"></i>':'')+ '</td> <td>'+(this.sku_price!=""?this.sku_price:'-')+'</td> <td>'+"-"+'</td> <td> <span class="btn priceTrend" data-value="1" onclick="showALL(this)">显示更多 <b class="caret"></b></span> </td> </tr>';
							}
							}
						
					})
					table_str=table_str+'</tbody></table></div>';
					$("#tableList").append(table_str);
					showTable(1);
					}
    			}
    		})
    	}
    	$("#dataStr").change(function(){
    		showData();
    	})
var page=1;
function showTable(page){
    		$("#page").paging({
			      pageNo:page,
			      totalPage: Math.ceil($("#tableList .tableBox").length/20),
			      totalSize: $("#tableList .tableBox").length,
			      callback:function(v){
			    	  page=v;
			    	  showTable(page);
			      }
			    })
    		$("#tableList .tableBox").hide();
    		for(let i=0;i<20;i++){
    			$($("#tableList .tableBox")[i+(page-1)*20]).show();
    		}
    		let h=$("body").height();
			let h2=$(window).height()
			if(h>h2){
			  $(".nav,.navSKU,.compareBox").height(h);
			}else{
			  $(".nav,.navSKU,.compareBox").height(h2);
			}
}
{
	loadTag();
	getBrand();
    showData()	
}