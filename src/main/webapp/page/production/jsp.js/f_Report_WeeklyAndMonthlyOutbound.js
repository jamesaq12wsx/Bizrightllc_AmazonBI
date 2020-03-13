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
    sessionStorage.moduleType='/f_Report_WeeklyAndMonthlyOutbound.htm';

    $('#searchBtn').unbind('click').click(function(){
        showData();
    })

	initCombobox('ourCode');// 初始化sku列表选择
	changeSelect();// 初始化二级联动下拉框
    showData();


});

//
// /**
//  * 默认展示页面数据
//  */
// function showData(){
//
// 	/**
// 	 * exam:
// 	 * var counttime = document.getElementById("counttime"); //定位id
// 	 * var counttimeindex = counttime.selectedIndex; // 选中索引
// 	 * var counttimetext = counttime.options[index].text; // 选中文本
// 	 * var counttimevalue = counttime.options[index].value; // 选中值
//      */
//
//
// 	var myjson = {};
//
// 	var counttypevalue= $("input[name='counttype']:checked").val();
// 	myjson.counttype=counttypevalue;
// 	var counttime = document.getElementById("counttime"); //定位id
// 	var counttimevalue = counttime.options[counttime.selectedIndex].value; // 选中值
// 	myjson.counttime=counttimevalue;
// 	var countnum = document.getElementById("countnum"); //定位id
// 	var countnumvalue = countnum.options[countnum.selectedIndex].value; // 选中值
// 	myjson.countnum=countnumvalue;
// 	myjson.ourCode=$('#ourCode').val();
//
// 	showLoading();
//
// 	$('.WeeklyAndMonthlyOutboundReport thead').html('');
// 	$('.WeeklyAndMonthlyOutboundReport tbody').html('');
//
// 	$.ajax({
// 		type : 'POST',
// 		url : path + '/report/Amazon_Report_WeeklyAndMonthlyOutbound.htm',
// 		timeout : 100000,
// 		data : myjson,
// 		dataType : 'json',
// 		success : function(data) {
// 			closeLoading();
//
// 			// 新建数组,存储计算值
// 			var arrayhead=new Array();
// 			var arraylast=new Array();
// 			var arraynow=new Array();
// 			var arraytmp=new Array();
// 			var calflg="NO";
// 			if (data.status == 1) {
//
// 				//开始循环处理数据
// 				var totalLow=0;
// 				var listtot=0;
// 				$.each(data.data,function(){
// 					totalLow++;
// 					listtot=this.length;
//
// 					if (totalLow==1) { // 等于1,先拼接列头
// 						var tablehead='<tr>';
//
// 						for (var listindex=0;listindex< listtot;listindex++) {
// 							if (arrayhead[listindex]== "Channel" ) {
// 								tablehead+=' <th >'+this[listindex]+'</th>';
// 							} else {
// 								tablehead+=' <th >'+this[listindex]+'</th>';
// 							}
// 							arrayhead.push(this[listindex]);
// 						}
// 						tablehead+='</tr>';
// 						$('.WeeklyAndMonthlyOutboundReport thead').append(tablehead);
// 					}
//
// 					if (totalLow!=1) { // 不等于1
// 						var tablebody='<tr>';
// 						for (var listindex=0;listindex< listtot;listindex++) {
// 							if (arrayhead[listindex]== "Channel" ) {
// 								tablebody+=' <td >'+this[listindex]+'</td>';
// 							} else if (arrayhead[listindex]== "Year" || arrayhead[listindex]== "LastYear") {
// 								tablebody+=' <td >'+this[listindex]+'</td>';
// 							} else {
// 								tablebody+=' <td >'+Math.round(this[listindex])+'</td>';
// 							}
// 							if ( totalLow%2==0 ) { // 求模为0,则是上年数据
// 								arraylast.push(this[listindex]);
// 							} else {// 求模不为0,则是当年数据
// 								arraynow.push(this[listindex]);
// 								calflg="YES";
// 							}
// 						}
// 						tablebody+='</tr>';
// 						$('.WeeklyAndMonthlyOutboundReport tbody').append(tablebody);
//
// 					}
//
// 					if  (calflg=="YES") {
// 						// 开始计算比例列
// 						arraytmp.splice(0,arraytmp.length);
// 						for (var listindex=0;listindex< listtot;listindex++) {
// 							if (arrayhead[listindex]== "Channel"  ) {
// 								arraytmp.push(arraynow[listindex]);
// 							} else if ( arrayhead[listindex]== "LastYear" || arrayhead[listindex]== "Year" ) {
// 								arraytmp.push("增长率(%)");
// 							} else {
// 								var num1=arraylast[listindex];
// 								var num2=arraynow[listindex];
// 								var percent=num1!=0? (num2-num1)/num1: "∞";
// 								percent=(percent=="∞")? "∞": Math.round((percent*100.00))+"%";
// 								arraytmp.push(percent);
// 							}
// 						}
// 						var tablebody='<tr class="report" >';
// 						for (var listindex=0;listindex< listtot;listindex++ ){
// 							if (arrayhead[listindex]== "Channel" ) {
// 								tablebody+=' <td >'+arraytmp[listindex]+'</td>';
// 							} else {
// 								tablebody+=' <td >'+arraytmp[listindex]+'</td>';
// 							}
// 						}
// 						tablebody+='</tr>';
// 						$('.WeeklyAndMonthlyOutboundReport tbody').append(tablebody);
//
// 						// 清除缓存数组
// 						arraylast.splice(0,arraylast.length);
// 						arraynow.splice(0,arraynow.length);
//
// 						// 计算标志还原
// 						calflg="NO";
//
// 					}
// 				});
//
// 				// 合并单元格
// 				// mergeCell('WeeklyAndMonthlyOutboundReport', 0, totalLow+((totalLow-1)/2), 0);
// 				mergeCell('WeeklyAndMonthlyOutboundReport', 1, 0, 0);
// 			}
//
// 		},
// 		error : function() {
// 			console.log('数据请求失败')
// 		}
// 	});
//
// }



/**
 * 默认展示页面数据
 */
function showData(){

	/**
	 * exam:
	 * var counttime = document.getElementById("counttime"); //定位id
	 * var counttimeindex = counttime.selectedIndex; // 选中索引
	 * var counttimetext = counttime.options[index].text; // 选中文本
	 * var counttimevalue = counttime.options[index].value; // 选中值
     */


	var myjson = {};

	var counttypevalue= $("input[name='counttype']:checked").val();
	myjson.counttype=counttypevalue;
	var counttime = document.getElementById("counttime"); //定位id
	var counttimevalue = counttime.options[counttime.selectedIndex].value; // 选中值
	myjson.counttime=counttimevalue;
	var countnum = document.getElementById("countnum"); //定位id
	var countnumvalue = countnum.options[countnum.selectedIndex].value; // 选中值
	myjson.countnum=countnumvalue;
	myjson.ourCode=$('#ourCode').val();

	showLoading();

	$('#left_table1').html('');
	$('#left_table2').html('');
	$('#right_table1').html('');
	$('#right_table2').html('');


	$.ajax({
		type : 'POST',
		url : path + '/report/Amazon_Report_WeeklyAndMonthlyOutbound.htm',
		timeout : 100000,
		data : myjson,
		dataType : 'json',
		success : function(data) {
			closeLoading();

			// 新建数组,存储计算值
			var arrayhead=new Array();
			var arraylast=new Array();
			var arraynow=new Array();
			var arraytmp=new Array();
			var calflg="NO";
			if (data.status == 1) {

				//开始循环处理数据
				var totalLow=0;
				var listtot=0;
				$.each(data.data,function(){
					totalLow++;
					listtot=this.length;

					if (totalLow==1) { // 等于1,先拼接列头
						var lefttable1_head='<tr><th hidden="hidden"></th>';
						var righttable1_head='<tr>';
						for (var listindex=0;listindex< listtot;listindex++) {
							if (this[listindex]== "Channel" ) {
								lefttable1_head+=' <th >'+this[listindex]+'</th>';
							} else {
								righttable1_head+=' <th >'+this[listindex]+'</th>';
							}
							arrayhead.push(this[listindex]);
						}
						lefttable1_head+='</tr>';
						righttable1_head+='</tr>';
						$('#left_table1').append(lefttable1_head);
						$('#right_table1').append(righttable1_head);
					}

					if (totalLow!=1) { // 不等于1
						var lefttable2_body='<tr><td style="width: 0px"></td>';
						var righttable2_body='<tr>';
						for (var listindex=0;listindex< listtot;listindex++) {
							if (arrayhead[listindex]== "Channel" ) {
								lefttable2_body+=' <td >'+this[listindex]+'</td>';
							} else if (arrayhead[listindex]== "Year" || arrayhead[listindex]== "LastYear") {
								righttable2_body+=' <td >'+this[listindex]+'</td>';
							} else {
								righttable2_body+=' <td >'+Math.round(this[listindex])+'</td>';
							}
							if ( totalLow%2==0 ) { // 求模为0,则是上年数据
								arraylast.push(this[listindex]);
							} else {// 求模不为0,则是当年数据
								arraynow.push(this[listindex]);
								calflg="YES";
							}
						}
						lefttable2_body+='</tr>';
						righttable2_body+='</tr>';
						$('#left_table2').append(lefttable2_body);
						$('#right_table2').append(righttable2_body);

					}

					if  (calflg=="YES") {
						// 开始计算比例列
						arraytmp.splice(0,arraytmp.length);
						for (var listindex=0;listindex< listtot;listindex++) {
							if (arrayhead[listindex]== "Channel"  ) {
								arraytmp.push(arraynow[listindex]);
							} else if ( arrayhead[listindex]== "LastYear" || arrayhead[listindex]== "Year" ) {
								arraytmp.push("增长率(%)");
							} else {
								var num1=arraylast[listindex];
								var num2=arraynow[listindex];
								var percent=num1!=0? (num2-num1)/num1: "∞";
								percent=(percent=="∞")? "∞": Math.round((percent*100.00))+"%";
								arraytmp.push(percent);
							}
						}
						var lefttable2_body='<tr><td style="width: 0px"></td>';
						var righttable2_body='<tr>';
						for (var listindex=0;listindex< listtot;listindex++ ){
							if (arrayhead[listindex]== "Channel" ) {
								lefttable2_body+=' <td >'+arraytmp[listindex]+'</td>';
							} else {
								righttable2_body+=' <td >'+arraytmp[listindex]+'</td>';
							}
						}
						lefttable2_body+='</tr>';
						righttable2_body+='</tr>';
						$('#left_table2').append(lefttable2_body);
						$('#right_table2').append(righttable2_body);

						// 清除缓存数组
						arraylast.splice(0,arraylast.length);
						arraynow.splice(0,arraynow.length);

						// 计算标志还原
						calflg="NO";

					}
				});

				// 合并单元格
				// mergeCell('WeeklyAndMonthlyOutboundReport', 0, totalLow+((totalLow-1)/2), 0);
				mergeCell('left_table2', 0, 0, 1);
			}

		},
		error : function() {
			console.log('数据请求失败')
		}
	});

}




/**
 * 下拉框二级联动
 */
function changeSelect()
{
	// 父选择项
	var parent = document.getElementById("counttime");
	var p_value = parent.value;

	// 子选择项的长度，长度大于0，即将所有选择项清空
	var child = document.getElementById("countnum");
	var c_length = child.options.length;
	if (c_length > 0) {
		for (var i = 0; i < c_length; i++) {
			child.options.remove(0);
		}
	}
	// 重创建子选择项
	var countnums = [
		['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25',
			'26', '27', '28', '29', '30', '31', '32', '33', '34', '35', '36', '37', '38', '39', '40', '41', '42', '43', '44', '45', '46', '47', '48', '49', '50', '51', '52'],   // 对应Weekly
		['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],                         // 对应monthly
	];
	if(p_value == "weekly") {
		for(var i = 0; i < countnums[0].length; i++) {
			addOption(countnums[0][i]);
		}
	} else if (p_value == "monthly") {
		for(var i = 0; i < countnums[1].length; i++) {
			addOption(countnums[1][i]);
		}
	} else {
		alert("未选择统计时间单位");
	}
}

function addOption(txt) {
	var opt = document.createElement("option"); // 创建一个对象
	opt.text = txt;
	opt.value = txt;
	countnum.options.add(opt);
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
		url: path + '/report/Amazon_Report_ProductSkuList.htm', //后台获取下拉框数据的url
		method:'post',
		panelHeight:200,//设置为固定高度，combobox出现竖直滚动条
		valueField:'ourCode',
		textField:'ourCode',
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


