function showData(){
	Amazon_MonitoringOverview_NewReview(getParameter())
}

function Amazon_MonitoringOverview_NewReview(v){
	showLoading();
	if ($('#table').hasClass('dataTable')) {
		let  dttable = $('#table').dataTable();
		 dttable.fnClearTable(); //清空一下table
		 dttable.fnDestroy(); //还原初始化了的datatable
    }
	$("#table tbody").html('<tr><td colspan="100">loading....</td></tr>');
	$.ajax({
		url:path+'/sku/Amazon_MonitoringOverview_NewReview.htm',
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
				$("#table tbody").html('');
				if(data.data.length>0){
					$("#inserttime").text(data.data[0].date);
					$.each(data.data,function(index,value){
						$("#table tbody").append(`<tr>
		                <td><img src="${value.sku_imageUrl}" alt=""></td>
		                <td>${value.SKU}</td>
		                <td style="text-align: left;color: #0066c0;">
		                <a href="${path}/sku_buyBoxOffer.htm?asin=${value.asin}&sku=${value.SKU}" target="_blank" class="blue">${value.sku_productTitle}</a> 
		                </td>
		                <td>${value.NewComments}</td>
		                <td class="asin">${value.asin}</td>
		                <td>
		                  <a class="btn amazonBtn" onclick="newRview(this,'${value.asin}')" style="width:80px;">新增评论 <b class="caret"></b></a>
		                  <a class="btn amazonBtn" style="width:74px;" href="${path}/sku_reviewOverview.htm?asin=${value.asin}&sku=${value.SKU}" target="_blank">所有评论</a>
		                </td>
		              </tr>`)
		              if(index == data.data.length-1 ){
			  				if ( $.fn.dataTable.isDataTable( '#table' ) ) {
							     $('#table').DataTable();
							}else{
								$('#table').on('page.dt',function() {}).dataTable({
									bLengthChange: false,
									"destroy": true,
									searching:false, 
									"aLengthMenu": [50],
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
					$("#table tbody").html('');
					$("#table tbody").append('<tr><td colspan="100">暂无数据</td></tr>');
				}
			}
		}
	})
}
$("#dataStr").change(function(){
	showData();
})

function newRview(obj,v){
	if($(obj).parent().parent().next().attr("class")!="downTr"){
		$.ajax({
			url:path+'/sku/Amazon_Monitoring_Intelligence_NewComments_Trigger.htm',
			data:{
				asin:v,
				date:$("#dataStr").val()
				},
				type:"post",
				success:function(value){
					var data=JSON.parse(value);
					if(data.status=='1'){
						if(data.data.length>0){
							let str=`<tr class="downTr" >
			                <td colspan="6" style="text-align: left;padding:0">`;
							$.each(data.data,function(index,value){
								str=str+`
				                  <div class="commentList">
			                    <div class="commentList-a">
			                      <i class="a-icon a-icon-user"></i>
			                      <span class="userName">${value.customerName}</span>
			                      <i class="a-icon a-icon-help"></i>
			                      <span class="userHelp">有帮助：${value.helpefulNum}</span>
			                    </div>
			                    <div class="commentList-b">
			                    ${value.star}
			                      <i class="a-icon a-icon-star a-star-${parseInt(value.star)}"></i>
			                      <span class="userDsc">${value.reviewTitle}</span>
			                    </div>
			                    <div class="commentList-c">
			                      <span class="commentTime">${value.insertTime}</span>
			                      <i class="a-icon a-icon-label a-label-VerifiedPurchase"></i>
			                    </div>
			                    <div class="commentList-d">
			                      <span>Update: ${value.reviewContent}</span>
			                    </div>
			                    <div class="commentList-e">
			                      <img src="${value.reviewImageUrl}" alt="">
			                      </div>
			                  </div>`;
							})
							$(obj).parent().parent().after(str+`</td></tr>`)
						}else{
						}
					}else{
					}
				}
		})
		
	}else if($(obj).parent().parent().next().attr("class")=="downTr"){
		if($(obj).parent().parent().next().css("display")=="none"){
			$(obj).parent().parent().next().show();
	    }else{
	    	$(obj).parent().parent().next().hide();
	    }
		
	}
}

{
	loadTag();
	getBrand();
	showData()	
}