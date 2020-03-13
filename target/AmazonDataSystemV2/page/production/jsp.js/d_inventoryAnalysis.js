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
	
//	showItemInfo();
//	showData();
	
})


function showd(){
//	showItemInfo();
//	showData();
}

/**
 * 获取商品数据
 */
function showItemInfo(){
	if($('#asin').val()==''){
		return;
	}
	
	$('.productDetail').find('div').html('');
	$('.productInfo').find('img').attr('src','')
	
	$.ajax({
		url:path+'/sku/Amazon_SKUAnalysis_SkuSearch_Details.htm?asin='+$('#asin').val(),//60601--洛杉矶,10001 --纽约,90001 --芝加哥
		type:'get',
		success:function(value){
			var data=JSON.parse(value);
			if(data.status=='1'){
				if(data.data[0]==undefined){
					return false;
				}
				$("#updateTime").text(data.data[0].year_month_day)
				$('.productDetail').find('div').html('<a class="blue" href="https://www.amazon.com/dp/'+$('#asin').val()+'" target="_blank">'+data.data[0].sku_productTitle+'</a>');
				$('.productInfo').find('img').attr('src',data.data[0].sku_imageUrl)
			}else{
				
			}
		}
	
	})
}


/**
 * 查询后台数据
 * @param status
 */


var realData=[];
function showData(){
	
	
	if($('#asin').val()==''){
		return;
	}
	
	//清空
	$('.inventoryTable tbody').html('');
	
	//执行查询
	var myjson = {};
	myjson.asin=$('#asin').text();
	$.ajax({
		type : 'post',
		url : path + '/sku/Amazon_InventoryAnalysis_InventoryManagement2.htm',
		timeout : 100000,
		data : myjson,
		dataType : 'json',
		success : function(data) {
			if (data.status == 1) {
				
				//初始化fba数据
				initRealData(data.data);
				
				var Vender_in=0;
				$.each(realData,function(){
					//Vender当天进货数
					Vender_in=Vender_in+parseInt(this.Vendor_IPOCF)+parseInt(this.Vendor_IPOWT);
					
					//开始渲染数据
					$('.inventoryTable tbody').append(
							
							' <tr year_month_day="'+this.year_month_day+'">'+
					             ' <td class="time">'+
					                '<div>'+
					                 ' <b>'+this.year_month_day+'</b>'+
					                '</div>'+
					              '</td>'+
					              '<td class="Vender" Vender_in='+Vender_in+'>'+
					               ' <span class="inventoryNum">'+this.Vendor_kucun+'</span>'+
					               ' <span class="sale">'+this.VendorFBA_sales+'</span>'+
					                '<span class="stockNum Vendor_IPOCF">'+this.Vendor_IPOCF+'</span>'+
					               ' <span class="stockNum Vendor_IPOWT">'+this.Vendor_IPOWT+'</span>'+
		
					             ' </td>'+
					             ' <td></td>'+
					             ' <td class="FBA" FBA_weidao="'+this.FBA_weidao+'">'+
					               ' <span class="inventoryNum">'+this.FBA_kucun+'</span>'+
					                '<span class="sale">'+this.VendorFBA_sales+'</span>'+
					                '<span class="stockNum FBA_Addstock">'+this.FBA_Addstock+'</span>'+
					             ' </td>'+
					             ' <td></td>'+
					             ' <td class="US">'+
					                '<span class="inventoryNum">'+this.US_kucun+'</span>'+
					               ' <span class="sale">'+this.US_sales+'</span>'+
					               ' <span class="stockNum US_addstock">'+this.US_addstock+'</span>'+
					                '<span class="shipmentsNum"></span>'+
					              '</td>'+
				            '</tr>'
				            
					);
					
				});
				
				
				
				
				//开始顺序计算数据
				
				
				
				
			}
		},
		error : function() {
			console.log('数据请求失败')
		}
	});
}



function initRealData(resultdata){
	var sumFBA_Addstock=0;
	$.each(resultdata,function(index){
		sumFBA_Addstock=sumFBA_Addstock+this.FBA_Addstock;
	})
	
	var FBA_weidao=resultdata[0].FBA_weidao;
	
	console.log('FBA_weidao----:'+FBA_weidao+'sumFBA_Addstock-----:'+sumFBA_Addstock)
	
	//把FBA_weidao和sumFBA_Addstock做对比 
	if(FBA_weidao>=sumFBA_Addstock){
		//什么都不用做处理  50 >= 10
		console.log('什么都不做处理');
		realData=data.data;
	}else{
		//50 < 70
		var chazhi = FBA_weidao-sumFBA_Addstock;  //-20
		$.each(resultdata,function(index){
			
			if(chazhi<0){
				if(this.FBA_Addstock==0){
					realData.push(this);
				}else{
					//判断FBA_Addstock和这个差值的大小
					var a = this.FBA_Addstock+parseInt(chazhi);  //10+(-20)  30+(-10)
					if(a<=0){
						this.FBA_Addstock=0;
					}else{
						this.FBA_Addstock=a;
					}
					realData.push(this);
					chazhi=a;
				}
				
			}else{
				realData.push(this);
			}
			
		})
	}
}

/**
 * 计算Vender的库存
 */
function execVenderKucun(){
	
	var trs = $('.inventoryTable  tbody tr');
	
	for (var i = 0; i < trs.length; i++) {
		//库存=原来库存+Vender_in
		var currenttr = trs[i];
		var Vender_in = $(currenttr).find('.Vender:eq(0)').attr('Vender_in');
		var Vender_kucun = $(currenttr).find('.Vender:eq(0)').find('.inventoryNum').text();
		var newkucun = parseFloat(Vender_kucun)+parseFloat(Vender_in)
		 $(currenttr).find('.Vender:eq(0)').find('.inventoryNum').text(newkucun.toFixed(2));
	}
}


/**
 * 计算FBA的库存
 */
function execFBAKucun(){
	//要准备计算FBA的库存了
	
}






















//---------------------------------------以下是工具函数

/**
 * 获取今天日期
 */
function getToday() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}

/**
 * 计算天数差的函数，通用 
 */ 
function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2002-12-18格式  
    var  aDate,  oDate1,  oDate2,  iDays  
    aDate  =  sDate1.split("-")  
    oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //转换为12-18-2002格式  
    aDate  =  sDate2.split("-")  
    oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])  
    iDays  =  parseInt((oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数  
    return  iDays  
}

/**
 * 推算某一个天前N天
 */
function daybefore(day,bef){
	var datt = day.split('-');//这边给定一个特定时间
	var newDate = new Date(datt[0], datt[1]-1, datt[2]);
	var befminuts = newDate.getTime() + 1000 * 60 * 60 * 24 * parseInt(bef);//计算前几天用减，计算后几天用加，最后一个就是多少天的数量
	var beforeDat = new Date;
	beforeDat.setTime(befminuts);
	var befMonth = beforeDat.getMonth()+1;
	var mon = befMonth >= 10 ? befMonth : '0' + befMonth;
	var befDate = beforeDat.getDate();
	var da = befDate >= 10 ? befDate : '0' + befDate;
	var newDate = beforeDat.getFullYear() + '-' + mon + '-' + da;
	return newDate;
}
