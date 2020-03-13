/**
 * 固定浮动表头列头
 */
//固定和滚动
var right_div2 = document.getElementById("right_div2");
right_div2.onscroll = function(){
    var right_div2_top = this.scrollTop;
    var right_div2_left = this.scrollLeft;
    document.getElementById("left_div2").scrollTop = right_div2_top;
    document.getElementById("right_div1").scrollLeft = right_div2_left;
}
//设置右边div宽度
document.getElementById("right_div").style.width=""+document.documentElement.clientWidth-130+"px";
setInterval(function() {
    document.getElementById("right_div").style.width=""+document.documentElement.clientWidth-130+"px";
}, 0);



/**
 * JS文档加载完成调用
 */
$(document).ready(function(){
    sessionStorage.moduleType='/e_SKUEarlyWarn.htm';

    $('#searchBtn').unbind('click').click(function(){
        showData();
    })

    showData();

	initCombobox('ProductLine');

});




/**
 * 默认展示页面数据
 */
function showData(){

	var myjson = {};
	myjson.ProductLine=$('#ProductLine').val();

	showLoading();

    // $('.productlineskutable thead').html('');
    // $('.productlineskutable tbody').html('');

    $('#left_table1').html('');
    $('#left_table2').html('');
    $('#right_table1').html('');
    $('#right_table2').html('');

	$.ajax({
		type : 'get',
		url : path + '/sku/Amazon_InventoryAnalysis_Forecast_ProductLineSKU.htm',
		timeout : 100000,
		data : myjson,
		dataType : 'json',
		success : function(data) {

			closeLoading();

			if (data.status == 1) {

				//开始循环处理数据
				var totalLow=0;

				$.each(data.data,function(){
					totalLow++;

					// 计算LA库存预警
					// ⑴	当（MFN+FBA+Inbound）/（未来20周delta*7的平均值）大于20时需预警，提示“LA库存过多”
					// ⑵	当KO列小于12时需预警“LA库存不足，请及时下单”（此情况在该SKU的lead time为30时生效，超过30时每多30则此值+4）
					//（30对应12周正常运转时间，60对应16周正常运转时间，90对应20周正常运转时间）
					// LAWarningFlg: LA预警标志 0-无需预警  1-库存过多 2-库存不足
					// PrimeOfferWarningFlg: Prime预警标志 0-无需预警  1-库存过多 2-库存不足
					// eventtype 1-LA库存过多 2-LA库存不足 3-Prime库存不足
					var tdmfn="";
					if (this.LAWarningFlg == "2") {
						tdmfn = ' <td  bgcolor="#FF0000" onmouseenter="javascript:showMessage(event,2)"  onmouseleave="hiddenMessage(event,2)" >'+this.MFNInv+'</td>';
					} else if (this.LAWarningFlg == "1") {
						tdmfn = ' <td  bgcolor="#FFC125" onmouseenter="javascript:showMessage(event,1)"  onmouseleave="hiddenMessage(event,1)">'+this.MFNInv+'</td>';
					} else {
						tdmfn = ' <td>'+this.MFNInv+'</td>';
					}

					var tdprime="";
					if (this.PrimeOfferWarningFlg == "2") {
						tdprime = ' <td  bgcolor="#FF0000" onmouseenter="javascript:showMessage(event,3)"  onmouseleave="hiddenMessage(event,3)">'+this.PrimeInvTime+'</td>';
					}  else {
						tdprime = ' <td>'+this.PrimeInvTime+'</td>';
					}

					if (this.ProductLine == null ) {
						this.ProductLine="";
					}

                    var buyBoxListHtmlStr="";
                    if (this.BuyBoxPercentage != null ) {
                        var buyBoxListSort=buyBoxListSortFunc(this.BuyBoxPercentage);
                        var allWeekList=this.AllWeekStr.split('|');
                        var index=0;
                        for (;index<allWeekList.length;index++) {
                            var indexReal=0;
                            var hasSame=false;
                        	while (indexReal<buyBoxListSort.length) {
								if ( buyBoxListSort[indexReal].indexOf(allWeekList[index])!=-1) { // 存在相等的周
                                    var strList=buyBoxListSort[indexReal].split('-');
                                    if (strList.length>1) {
                                        buyBoxListHtmlStr+=strList[1]+"|";
                                    } else {
                                        buyBoxListHtmlStr+="-|";
                                    }
                                    hasSame=true;
                                    break;
								}
								indexReal++;
							}
							if (hasSame==false) {
                                buyBoxListHtmlStr+="-|";
							}
                        }
                    }

					if (totalLow==1) { // 取首行数据设置行首内容
                        $('#left_table1').append('<tr>'
                            +' <th>ProductLine</th>'
                            +' <th >ASIN</th>'
                            +' <th >SKU</th>'
                            +'</tr>');
                        $('#right_table1').append('<tr>'
                            +' <th >Buy Box %<br>'+this.AllWeekStr+'</th>'
                            +' <th >LA WH<br>'+this.LAWareHouseDate+'</th>'
                            +' <th >FBA<br>'+this.FBAInventoryDate+'</th>'
                            +' <th >FBA In<br>'+this.FBAInboundDate+'</th>'
                            +' <th >AMZV<br>'+this.VendorRetailDate+'</th>'
                            +' <th >Vendor openPO<br>'+this.VendorOpenPODate+'</th>'
                            +' <th >Ave ePos<br>'+this.EPosDate+'</th>'
                            +' <th >Prime Weeks<br>Inventory Outstanding</th>'
                            +' <th >FBA Weeks<br>Inventory Outstanding</th>'
                            +' <th >Total Weeks<br>Inventory Outstanding</th>'
                            +'</tr>');
					}


					$('#left_table2').append('<tr >'
						+' <td>'+this.ProductLine+'</td>'
						+' <td>'+'<a href="https://www.amazon.com/dp/'+this.asin+'" target="_blank" class="blue">'+this.asin.toString()+'</a>'+'</td>'
						+' <td>'+this.ourCode+'</td>'
						+'</tr>');

                    $('#right_table2').append('<tr >'
                        +' <td>'+buyBoxListHtmlStr+'</td>'
                        + tdmfn
                        +' <td>'+this.FBAInv+'</td>'
                        +' <td>'+this.FBAInbound+'</td>'
                        +' <td>'+this.VendorInv+'</td>'
                        +' <td>'+this.VendorOpenPO+'</td>'
                        +' <td>'+this.AmazonWeekSalesNum+'</td>'
                        + tdprime
                        +' <td>'+this.FBAInvTime+'</td>'
                        +' <td>'+this.TotalTime+'</td>'
                        +'</tr>');
				});

			}

			// 合并单元格
			mergeCell('left_table2', 0, totalLow-1, 0);

		},
		error : function() {
			console.log('数据请求失败')
		}
	});

}


/**
 * 合并单元格(如果结束行传0代表合并所有行)
 * @param table1    表格的ID
 * @param startRow  起始行
 * @param endRow    结束行
 * @param col   合并的列号，对第几列进行合并(从0开始)。第一行从0开始
 */
function mergeCell(table1, startRow, endRow, col) {
	var tb = document.getElementById(table1);
	if(!tb || !tb.rows || tb.rows.length <= 0) {
		return;
	}
	if(col >= tb.rows[0].cells.length || (startRow >= endRow && endRow != 0)) {
		return;
	}
	if(endRow == 0) {
		endRow = tb.rows.length - 1;
	}
	for(var i = startRow; i < endRow; i++) {
		if(tb.rows[startRow].cells[col].innerHTML == tb.rows[i + 1].cells[col].innerHTML) { //如果相等就合并单元格,合并之后跳过下一行
			tb.rows[i + 1].removeChild(tb.rows[i + 1].cells[col]);
			tb.rows[startRow].cells[col].rowSpan = (tb.rows[startRow].cells[col].rowSpan) + 1;
		} else {
			mergeCell(table1, i + 1, endRow, col);
			break;
		}
	}
}


//参数：id  控件id
function initCombobox(id){
	//加载下拉框复选框
	$('#'+id).combobox({
		url: path + '/sku/Amazon_InventoryAnalysis_Forecast_ProductLineList.htm', //后台获取下拉框数据的url
		method:'post',
		panelHeight:200,//设置为固定高度，combobox出现竖直滚动条
		valueField:'ProductLine',
		textField:'ProductLine',
		multiple:true,
		formatter: function (row) { //formatter方法就是实现了在每个下拉选项前面增加checkbox框的方法
			var opts = $(this).combobox('options');
			return '<input type="checkbox" class="combobox-checkbox">' + row[opts.textField]
		},
		onLoadSuccess: function () {  //下拉框数据加载成功调用
			var opts = $(this).combobox('options');
			var target = this;
			var values = $(target).combobox('getValues');//获取选中的值的values
			$.map(values, function (value) {
				var el = opts.finder.getEl(target, value);
				el.find('input.combobox-checkbox')._propAttr('checked', true);
			})
		},
		onSelect: function (row) { //选中一个选项时调用
			var opts = $(this).combobox('options');
			//获取选中的值的values
			$("#"+id).val($(this).combobox('getValues'));

			//设置选中值所对应的复选框为选中状态
			var el = opts.finder.getEl(this, row[opts.valueField]);
			el.find('input.combobox-checkbox')._propAttr('checked', true);
		},
		onUnselect: function (row) {//不选中一个选项时调用
			var opts = $(this).combobox('options');
			//获取选中的值的values
			$("#"+id).val($(this).combobox('getValues'));

			var el = opts.finder.getEl(this, row[opts.valueField]);
			el.find('input.combobox-checkbox')._propAttr('checked', false);
		}
	});
}

/**
 * 显示悬浮层
 * @param event
 * @param eventtype 1-LA库存过多 2-LA库存不足 3-Prime库存不足
 */
function showMessage(event,eventtype){
	var x=event.clientX;
	var y=event.clientY;
	var id="";
	if (eventtype==1) {
		id="alertLAExessInventory";
	} else if (eventtype==2) {
		id="alertLALessInventory";
	} else if (eventtype==3) {
		id="alertPrimeLessInventory";
	} else {
		return;
	}
	document.getElementById(id).style.left=x+'px';
	document.getElementById(id).style.top=y+'px';
	document.getElementById(id).style.display='block';
	// document.getElementById("inform").css("display","block");

}

/**
 * 隐藏悬浮层
 * @param event
 * @param eventtype 1-LA库存过多 2-LA库存不足 3-Prime库存不足
 */
function hiddenMessage(event,eventtype){
	var id="";
	if (eventtype==1) {
		id="alertLAExessInventory";
	} else if (eventtype==2) {
		id="alertLALessInventory";
	} else if (eventtype==3) {
		id="alertPrimeLessInventory";
	} else {
		return;
	}

	var informDiv = document.getElementById(id);
	var x=event.clientX;
	var y=event.clientY;
	var divx1 = informDiv.offsetLeft;
	var divy1 = informDiv.offsetTop;
	var divx2 = informDiv.offsetLeft + informDiv.offsetWidth;
	var divy2 = informDiv.offsetTop + informDiv.offsetHeight;
	if( x < divx1 || x > divx2 || y < divy1 || y > divy2){
		document.getElementById(id).style.display='none';
	}

}

/**
 * 处理BuyBox数据并进行排序
 */
function buyBoxListSortFunc(buyBoxStr) {
    var BuyBoxPercentageList=buyBoxStr.split(',');
	return BuyBoxPercentageList.sort().reverse();
}
