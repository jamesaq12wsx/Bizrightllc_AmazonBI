
/**
 * 文档加载完成
 */
$(document).ready(function(){
	sessionStorage.moduleType='/d_inventoryAnalysis.htm';
	
	if(sessionStorage.skuData!=undefined){
//		$("#asin").val(JSON.parse(sessionStorage.skuData).asin)
	}
	
	
	if($('#asin').val()==''){
		return;
	}
	
	showData();
});


//var dttable;
function showData(){
	
	
	var myjson = {};
	
//	if ($('#listTable').hasClass('dataTable')) {
//        dttable = $('#listTable').dataTable();
//        dttable.fnClearTable(); //清空一下table
//        dttable.fnDestroy(); //还原初始化了的datatable
//    }
	
	$('#listTable tbody').html('');
	$.ajax({
		type : 'get',
		url : path + '/sku/select_sku_CNLeadTime_map.htm',
		timeout : 100000,
		data : myjson,
		dataType : 'json',
		success : function(data) {
			if (data.status == 1) {
				
				$.each(data.data,function(index){
					
					var inventory_buffer = this.inventory_buffer;
					var restock_frequency=this.restock_frequency;
					var lead_time=this.lead_time;
					
					$('#listTable tbody').append(
							
							'<tr>'+
		                   '<td>'+this.sku+'</td>'+
		                   ' <td sku="'+this.sku+'">'+
		                        '<div class="item " id="inv'+index+'">'+
		                            '<span val="15" >15</span>'+
		                            '<span val="30">30</span>'+
		                            '<span val="45">45</span>'+
		                            '<span val="60">60</span>'+
		                        '</div>'+
		                   ' </td>'+
		                    '<td sku="'+this.sku+'">'+
		                        '<div class="item" id="res'+index+'">'+
		                            '<span val="35">15</span>'+
		                           ' <span val="30">30</span>'+
		                           ' <span val="60">60</span>'+
		                        '</div>'+
		                    '</td>'+
		                   ' <td sku="'+this.sku+'">'+
		                        '<div class="item" id="lead'+index+'">'+
		                            '<span val="35">35</span>'+
		                            '<span val="45">45</span>'+
		                           ' <span val="60">60</span>'+
		                            '<span val="90">90</span>'+
		                        '</div>'+
		                    '</td>'+
		                '</tr>'
					
					);
					
					
					$('#listTable tbody #inv'+index+' span[val="'+inventory_buffer+'"]').addClass('current');
					$('#listTable tbody #res'+index+' span[val="'+restock_frequency+'"]').addClass('current');
					$('#listTable tbody #lead'+index+' span[val="'+lead_time+'"]').addClass('current');
					
					
					
				});
				
				
//				 $('#listTable').dataTable();
				
				
				
				$('#listTable tbody span').each(function(){
					
					$(this).click(function(){
						var sku = $(this).parent().parent().attr('sku');
						var id = $(this).parent().attr('id');
						var m='';
						if(id.indexOf('inv')!=-1){
							m='inv';
						}else if(id.indexOf('res')!=-1){
							m='res';
						}else if(id.indexOf('lead')!=-1){
							m='lead';
						}
						var val = $(this).attr('val');
						update_sku_CNLeadTime_map(sku,m,val);
						
						$(this).parent().find('span').removeClass('current');
						$(this).addClass('current');
						
						
					})
				})
				
				
			}
		},
		error : function() {
			console.log('数据请求失败')
		}
	});
	
}



function update_sku_CNLeadTime_map(sku,m,val){
	
	var myjson = {};
	myjson.sku=sku;
	if(m=='inv'){
		myjson.inventory_buffer=val;
	}else if(m=='res'){
		myjson.restock_frequency=val;
	}else if(m=='lead'){
		myjson.lead_time=val;
	}
	
	$.ajax({
		type : 'post',
		url : path + '/sku/update_sku_CNLeadTime_map.htm',
		timeout : 100000,
		data : myjson,
		dataType : 'json',
		success : function(data) {
			if (data.status == 1) {
				
				
			}
		},
		error : function() {
			console.log('数据请求失败')
		}
	});
	
	
}