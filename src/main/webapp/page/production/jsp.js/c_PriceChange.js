function showData(){
	Amazon_MonitoringOverview_PriceChange(getParameter())
}

function Amazon_MonitoringOverview_PriceChange(v){
	showLoading();
	if ($('#table').hasClass('dataTable')) {
		let  dttable = $('#table').dataTable();
		 dttable.fnClearTable(); //清空一下table
		 dttable.fnDestroy(); //还原初始化了的datatable
    }
	$.ajax({
		url:path+'/sku/Amazon_MonitoringOverview_PriceChange.htm',
		type:"POST",
		data:{
			top:v._top,
			keyword:v.input_value,
			brand:v.brand,
			SubCategory:v.category,
			tag:v.tag,
			cityCode:$("#cityCode").val(),
			date:$("#dataStr").val()
		},
		success:function(value){
			closeLoading();
			var data = JSON.parse(value);
			if (data.status == '1') {
				$("#table tbody").html("");
				if(data.data.length>0){
					$("#inserttime").text(data.data[0].inserttime);
					$.each(data.data,function(index,value){
						$("#table tbody").append(`<tr>
                <td><img src="${value.sku_imageUrl}" alt=""></td>
                <td style="text-align: left;">
                  <span>${value.SKU}</span><br>
                  <span class="asin"><a href="https://www.amazon.com/dp/${value.asin}" target="_blank">${value.asin}</a></span>
                </td>
                <td style="text-align: left;">
                </td>
                <td style="text-align: left;color: #0066c0;">
                <a href="${path}/sku_buyBoxOffer.htm?asin=${value.asin}&sku=${value.SKU}" target="_blank" class="blue">${value.sku_productTitle}</a> 
                </td>
                <td style="text-align: left;">
                  <div class="oldBuyBox">
                    <span>${value.yestday_sku_soldBy}</span>
                    <span>$${value.yestday_price}</span>
                  </div>
                  <div>
                    <span class="buyBox">${value.yestday_sku_soldBy} ${value.Prime==1?'<i class="a-icon a-icon-prime" style="margin:0"></i>':""} </span>
                    <span>$${value.new_price==""?"-":value.new_price}</span>
                    ${(value.new_price-value.yestday_price)<0?'<span class="green"><i class="iconfont icon-xiajiang"></i>':'<span class="red"><i class="iconfont icon-shangsheng "></i>'}$${(value.new_price-value.yestday_price).toFixed(2)}</span>
                    
                  </div>
                </td>
                <td>${(value.price_status_Percentage==1?'价格不变':value.price_status=="价格上升"?'<span class="red"><i class="iconfont icon-shangsheng red"></i>'+(value.price_status_Percentage*100).toFixed(2)+'%':value.price_status=="价格下降"?'<span class="green"><i class="iconfont icon-xiajiang green"></i>'+(value.price_status_Percentage*100).toFixed(2)+'%':'')}</td>
                <td>
                  <a href="${path}/sku_buyBoxOffer.htm?asin=${value.asin}&sku=${value.SKU}" target="_blank" class="btn amazonBtn">查看</a>
                </td>
              </tr>`)
              if(index == data.data.length-1 ){
					/*flushTabel()*/
  				getPrice()
  				if ( $.fn.dataTable.isDataTable( '#table' ) ) {
				     $('#table').DataTable();
				}else{
					$('#table').on('page.dt',function() {}).dataTable({
						bLengthChange: false,
						"destroy": true,
						searching:false, 
						"drawCallback": function( settings ) {
							let h=$("body").height();
							let h2=$(window).height()
							if(h>h2){
							  $(".nav,.navSKU,.compareBox").height(h);
							}else{
							  $(".nav,.navSKU,.compareBox").height(h2);
							}
							}
					});
				}
				}
					})
				}else{
					$("#table tbody").append('<tr><td colspan="100">暂无数据</td></tr>');
				}
			}
		}
	})
}

function getPrice(){
	$.each($('#table').find('tbody tr'),function(index){
	$.ajax({
		type : 'post',
		url : path + '/sku/getSkuCompareInfoByAsin.htm',
		data : {
			asin : $(this).find('td:eq(1)').find('a').text().trim()
		},
		success:function(value){
			var data=JSON.parse(value);
			if(data.status==1){
				$('#table').find('tbody tr:eq('+index+')').find('td:eq(2)').text('');
				if(data.data.length>0){
				var str='';
				$.each(data.data,function(count){
					str=str+'<span style="color:#666;display:block;">'+(this.compareOurAsin)+'</span>'
				})
				if(data.data.length<3){
					str='<div class="asinList" >'+str+'</div>'
					$('#table').find('tbody tr:eq('+index+')').find('td:eq(2)').append(str)
				}else{
					str='<div class="asinList" >'+str+'<b style="cursor: pointer;display:block" onclick="more(this)">显示更多<b></div>'
					$('#table').find('tbody tr:eq('+index+')').find('td:eq(2)').append(str)
				}
				$('#table').find('tbody tr:eq('+index+')').find('td:eq(2)').find("span:gt(2)").hide();
				}else{
					$('#table').find('tbody tr:eq('+index+')').find('td:eq(2)').append("-")
				}
			}
		}
	} )
})
}
$("#dataStr").change(function(){
	showData();
})
{
	loadTag();
	getBrand();
	showData()	
}