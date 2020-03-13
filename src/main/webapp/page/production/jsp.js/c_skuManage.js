 $('.addTag').click(function(){
 	$('.tagModel').show();
 })

var asin="";
function _init_Iconclick(){
$(".addTag").unbind();
$('.addTag').click(function(){
	$('.tagModel').show();
	$(".tagModelList").find("span").siblings().removeClass("on");
	asin=$(this).parent().parent().find("td:eq(3)").text();
	$(this).parent().find("span").map(function(){
		// noinspection JSAnnotator
        let tag=$(this).text();
		$(".tagModelList").find("span").map(function(index){
		if($(this).text()==tag){
			$(this).addClass("on")
		}
		})
	})
	
})
$(".comfireBtn").unbind();
$(".comfireBtn").click(function(){
	showData()
	$('.tagModel').hide();
})
$(".deleteBtn").unbind();
$(".deleteBtn").click(function(){
	$('.tagModel').hide();
})
$(".tagModelList span").unbind();
$(".tagModelList span").click(function(){
	if($(this).attr("class")=="on"){
		productdelLable($(this));
	}else{
		productAddLable($(this));
	}
})
$(".addIcon").unbind();
$(".addIcon").click(function(){
	var flag=$(this).attr("data-value");
	if(flag==0){
		if($(this).parent().parent().next().attr("class")!="compareTr"){
			Amazon_MonitoringOverview_SKU_Jingpin($(this),$(this).attr("name"));
		}else{
			$("."+$(this).attr("name")).show();
		}
		$(this).html('<i class="iconfont icon-jian"></i>');
		$(this).attr("data-value","1");
	}else{
		$("."+$(this).attr("name")).hide();
		$(this).html('<i class="iconfont icon-jia"></i>');
		$(this).attr("data-value","0");
	}
})
$(".icon-more").unbind();
$(".icon-more").click(function(){
	if($(this).attr("data-value")==0){
		$(this).parent().find("span").show();
		//$(".qil:gt(2)").show();
		$(this).attr("data-value","1");
	}else{
		$(this).parent().find("span").hide();
		$(this).parent().find("span:eq(0)").show()
		$(this).parent().find("span:eq(1)").show()
		$(this).parent().find("span:last-child").show()
		//$(".qil:gt(2)").hide();
		$(this).attr("data-value","0");
	}
})
}
function productAddLable(obj){
	$.ajax({
		url:path+'/sku/productAddLable.htm',
		type:"POST",
		data:{
			asin:asin,
			labelName:obj.text()
		},
		success:function(value){
			var data = JSON.parse(value);
			if (data.status == '1') {
				obj.addClass("on")
			}
		}
	})
}
function productdelLable(obj){
	$.ajax({
		url:path+'/sku/productdelLable.htm',
		type:"POST",
		data:{
			asin:asin,
			labelName:obj.text()
		},
		success:function(value){
			var data = JSON.parse(value);
			if (data.status == '1') {
				obj.removeClass()
			}
		}
	})
}
function Amazon_MonitoringOverview_SKU_Jingpin(obj,asin){
	$.ajax({
		url:path+'/sku/Amazon_MonitoringOverview_SKU_Jingpin.htm',
		type:"POST",
		data:{asin:asin},
		success:function(value){
			var data = JSON.parse(value);
			if (data.status == '1') {
				if(data.data.length>0){
				$(".tip").remove();
				$.each(data.data,function(index){
					obj.parent().parent().after('<tr class="compareTr '+asin+'">'+
					'<td></td>'+
	                '<td>'+
	                  '<img src="'+this.sku_imageUrl+'" alt="">'+
	                '</td>'+ 
	                '<td style="text-align: left;">'+ 
	                   '<a href="'+path+'/sku_buyBoxOffer.htm?asin='+this.asin+'" target="_blank" class="blue">'+this.sku_productTitle+'</a>'+  
	                '</td>'+  
	                '<td>'+this.asin+'</td>'+ 
	                '<td>'+this.sku+'</td>'+ 
	                '<td>'+this.sku_price+'</td>'+ 
	                '<td>'+ this.main_rwNum+'</td>'+
	                '<td>'+this.sku_increasedRwNum+'</td>'+
	                '<td>'+this.sku_soldBy+'</td>'+ 
	                '<td>'+(this.sku_isPrime==1?"是":"否")+' </td>'+  
	                '<td>'+
	                  '<span class="tagItem addTag">+</span>'+ 
	                '</td>'+
					'</tr>');
					if(index == data.data.length-1 ){
						loadLabelName()
					}
				})
				}else{
					obj.parent().parent().after('<tr class="tip '+asin+'"><td colspan="100">没有竞品</td></tr>');
				}
			}
		}
	})
}
function showData(){
	 getSkuInfo(getParameter())
}

 
function getSkuInfo(v){
	showLoading()
	if ($('#table').hasClass('dataTable')) {
		let  dttable = $('#table').dataTable();
		 dttable.fnClearTable(); //清空一下table
		 dttable.fnDestroy(); //还原初始化了的datatable
    }
	$.ajax({
		url:path+'/sku/Amazon_MonitoringOverview_SKU.htm',
		type:"POST",
		data:{
			top:v._top,
			keyword:v.input_value,
			brand:v.brand,
			SubCategory:v.category,
			tag:v.tag,
		},
		success:function(value){
			closeLoading()
			var data = JSON.parse(value);
			if (data.status == '1') {
				$("#table tbody").html('');
				if(data.data.length>0){
					$("#inserttime").text(data.data[0].inserttime);
				$.each(data.data,function(index,value){
					$("#table tbody").append(`<tr> 
	                <td>
	                  <span class="addIcon" data-value="0" name="${value.asin}"><i class="iconfont icon-jia"></i></i></span>
	                </td>
	                <td>
	                  <img src="${value.sku_imageUrl}" alt="">
	                </td> 
	                <td style="text-align: left;">
	                   <a href="${path}/sku_buyBoxOffer.htm?asin=${value.asin}&sku=${value.sku}" target="_blank" class="blue">${value.sku_productTitle}</a> 
	                </td> 
	                <td>${value.asin}</td>
	                <td>${value.sku}</td>
	                <td>${value.sku_price}</td>
	                <td>${value.main_avaStar}/${value.main_rwNum}</td>
	                <td><a href="${path}/sku_reviewDetail.htm?asin=${value.asin}&sku=${value.sku}" target="_blank" class="blue">${value.sku_increasedRwNum}</a></td>
	                <td>${value.sku_soldBy}</td>
	                <td>${value.sku_isPrime==1?"是":"否"}</td> 
	                <td> 
	                  <span class="tagItem addTag">+</span>
	                </td>
	              </tr>`);
					if(index == data.data.length-1 ){
						loadLabelName()
					}
				});
				if ( $.fn.dataTable.isDataTable( '#table' ) ) {
				     $('#table').DataTable();
				}else{
					$('#table').on('page.dt',function() {}).dataTable({
						bLengthChange: false,
						"destroy": true,
						searching:false,
						iDisplayLength:50,
						"drawCallback": function( settings ) {
							_init_Iconclick();
							loadLabelName();
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
				}else{
					$("#table tbody").html('');
					$("#table tbody").html('<tr><td colspan="100">暂无数据！</td></tr>');
				}
				
			}
		}
	})
}

function loadLabelName(){
	$.each($("#table tbody>tr"),function(index){
		    let asin=$(this).find("td:eq(3)").text();
		    if(asin=="")return;
			$.ajax({
				type:'post',
				url:path+'/sku/loadLabel.htm',
				data:{
					asin:asin
				},
				success:function(value){
					var data=JSON.parse(value);
					if(data.status==1){
						if(data.data.length>0){
						$('#table').find('tbody tr:eq('+index+')').find('td:last-child').text('');
						let str="";
						$.each(data.data,function(count){
							if(count<2){
								str=str+'<span class="tagItem">'+this.labelName+'</span>';
							}else{
								str=str+'<span class="tagItem" style="display: none;">'+this.labelName+'</span>';
							}
						})
						str=str+'<i class="iconfont icon-more" data-value="0"></i>';
						$('#table').find('tbody tr:eq('+index+')').find('td:last-child').append(str+`<span class="tagItem addTag">+</span>`)
						}else{
							$('#table').find('tbody tr:eq('+index+')').find('td:last-child').text('');
							$('#table').find('tbody tr:eq('+index+')').find('td:last-child').append('<span class="tagItem addTag">+</span>');
						}
					}
					_init_Iconclick();
				}
			})
		 

	})
}



{
	$('.tagModel').hide();
	loadTag();
	getBrand();
	showData();
}