var currentDate;// 当前操作的日历控件 全局变量

function init_daterangepicker() {

	if (typeof ($.fn.daterangepicker) === 'undefined') {
		return;
	}

	var optionSet1 = {
		startDate : moment().subtract(7, 'days'),
		endDate : moment(),
		minDate : '01/01/2012',
		maxDate : moment().subtract(1, 'days'),
		dateLimit : {
			days : 365
		},
		showDropdowns : true,
		showWeekNumbers : false,
		timePicker : false,
		timePickerIncrement : 1,
		timePicker12Hour : true,
		ranges : {
			'昨天' : [ moment().subtract(1, 'days'), moment().subtract(1, 'days') ],
			'近7天' : [ moment().subtract(7, 'days'), moment().subtract(1, 'days') ],
			'近14天' : [ moment().subtract(14, 'days'), moment().subtract(1, 'days') ],
			'近30天' : [ moment().subtract(30, 'days'), moment().subtract(1, 'days') ]
		},
		opens : 'right',
		buttonClasses : [ 'btn btn-default' ],
		applyClass : 'btn-small btn-primary',
		cancelClass : 'btn-small',
		format : 'YYYY-MM-DD',
		separator : ' to ',
		locale : {
			applyLabel : '提交',
			cancelLabel : '关闭',
			fromLabel : 'From',
			toLabel : 'To',
			customRangeLabel : '自定义',
			daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
			monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月',
					'九月', '十月', '十一月', '十二月' ],
			firstDay : 1
		}
	};
	
	
	var optionSet1Today = {
			startDate : moment().subtract(6, 'days'),
			endDate : moment().subtract(0, 'days'),
			minDate : '01/01/2012',
			maxDate : moment().subtract(0, 'days'),
			dateLimit : {
				days : 365
			},
			showDropdowns : true,
			showWeekNumbers : false,
			timePicker : false,
			timePickerIncrement : 1,
			timePicker12Hour : true,
			ranges : {
				'今天' : [ moment().subtract(0, 'days'), moment().subtract(0, 'days') ],
				'近7天' : [ moment().subtract(6, 'days'), moment().subtract(0, 'days') ],
				'近14天' : [ moment().subtract(13, 'days'), moment().subtract(0, 'days') ],
				'近30天' : [ moment().subtract(29, 'days'), moment().subtract(0, 'days') ]
			},
			opens : 'right',
			buttonClasses : [ 'btn btn-default' ],
			applyClass : 'btn-small btn-primary',
			cancelClass : 'btn-small',
			format : 'YYYY-MM-DD',
			separator : ' to ',
			locale : {
				applyLabel : '提交',
				cancelLabel : '关闭',
				fromLabel : 'From',
				toLabel : 'To',
				customRangeLabel : '自定义',
				daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
				monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月',
						'九月', '十月', '十一月', '十二月' ],
				firstDay : 1
			}
		};

	var cb = function(start, end, label) {
		$(currentDate).find('span').html(
				start.format('YYYY-MM-DD') + ' 至 ' + end.format('YYYY-MM-DD'));
	};

	//这是所有双日历初始化的组件-------------------------------
	$('.mydate').each(function(){
		var obj= $(this);
		$(obj).find('span').html(
				moment().subtract(7, 'days').format('YYYY-MM-DD') + ' 至 '
						+ moment().subtract(1, 'days').format('YYYY-MM-DD'));
		$(obj).daterangepicker(optionSet1, cb);
		$(obj).on('show.daterangepicker', function() {
			currentDate = $(this);
		});
		$(obj).on('hide.daterangepicker', function() {
			currentDate = $(this);
			$(this).change();
		});
		$(obj).on('apply.daterangepicker', function(ev, picker) {
		});
		$(obj).on('cancel.daterangepicker', function(ev, picker) {
		});
		$('#options1').click(function() {
			$(obj).data('daterangepicker').setOptions(optionSet1, cb);
		});
		$('#options2').click(function() {
			$(obj).data('daterangepicker').setOptions(optionSet2, cb);
		});
		$('#destroy').click(function() {
			$(obj).data('daterangepicker').remove();
		});
	});
	
	
	
	$('.mydateToday').each(function(){
		var obj= $(this);
		$(obj).find('span').html(
				moment().subtract(6, 'days').format('YYYY-MM-DD') + ' 至 '
						+ moment().subtract(0, 'days').format('YYYY-MM-DD'));
		$(obj).daterangepicker(optionSet1Today, cb);
		$(obj).on('show.daterangepicker', function() {
			currentDate = $(this);
		});
		$(obj).on('hide.daterangepicker', function() {
			currentDate = $(this);
			$(this).change();
		});
		$(obj).on('apply.daterangepicker', function(ev, picker) {
		});
		$(obj).on('cancel.daterangepicker', function(ev, picker) {
		});
		$('#options1').click(function() {
			$(obj).data('daterangepicker').setOptions(optionSet1, cb);
		});
		$('#options2').click(function() {
			$(obj).data('daterangepicker').setOptions(optionSet2, cb);
		});
		$('#destroy').click(function() {
			$(obj).data('daterangepicker').remove();
		});
	})

}

function init_daterangepicker_right() {

	if (typeof ($.fn.daterangepicker) === 'undefined') {
		return;
	}
	console.log('init_daterangepicker_right');

	var cb = function(start, end, label) {
		console.log(start.toISOString(), end.toISOString(), label);
		$('#reportrange_right span').html(
				start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD'));
	};

	var optionSet1 = {
		startDate : moment().subtract(7, 'days'),
		endDate : moment(),
		minDate : '01/01/2012',
		maxDate : '12/31/2020',
		dateLimit : {
			days : 365
		},
		showDropdowns : true,
		showWeekNumbers : false,
		timePicker : false,
		timePickerIncrement : 1,
		timePicker12Hour : true,
		ranges : {
			'昨天' : [ moment().subtract(1, 'days'), moment().subtract(1, 'days') ],
			'近7天' : [ moment().subtract(7, 'days'), moment().subtract(1, 'days') ],
			'近14天' : [ moment().subtract(14, 'days'), moment().subtract(1, 'days') ],
			'近30天' : [ moment().subtract(30, 'days'), moment().subtract(1, 'days') ]
		},
		opens : 'right',
		buttonClasses : [ 'btn btn-default' ],
		applyClass : 'btn-small btn-primary',
		cancelClass : 'btn-small',
		format : 'YYYY-MM-DD',
		separator : ' to ',
		locale : {
			applyLabel : '提交',
			cancelLabel : '关闭',
			fromLabel : 'From',
			toLabel : 'To',
			customRangeLabel : '自定义',
			daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
			monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月',
					'九月', '十月', '十一月', '十二月' ],
			firstDay : 1
		}
	};

	$('#reportrange_right span').html(
			moment().subtract(29, 'days').format('YYYY-MM-DD') + ' - '
					+ moment().format('YYYY-MM-DD'));

	$('#reportrange_right').daterangepicker(optionSet1, cb);

	$('#reportrange_right').on('show.daterangepicker', function() {
		console.log("show event fired");
	});
	$('#reportrange_right').on('hide.daterangepicker', function() {
		console.log("hide event fired");
	});
	$('#reportrange_right').on(
			'apply.daterangepicker',
			function(ev, picker) {
				console.log("apply event fired, start/end dates are "
						+ picker.startDate.format('YYYY-MM-DD') + " to "
						+ picker.endDate.format('YYYY-MM-DD'));
			});
	$('#reportrange_right').on('cancel.daterangepicker', function(ev, picker) {
		console.log("cancel event fired");
	});

	$('#options1').click(
			function() {
				$('#reportrange_right').data('daterangepicker').setOptions(
						optionSet1, cb);
			});

	$('#options2').click(
			function() {
				$('#reportrange_right').data('daterangepicker').setOptions(
						optionSet2, cb);
			});

	$('#destroy').click(function() {
		$('#reportrange_right').data('daterangepicker').remove();
	});

}

/**
 * 单日历
 */
function init_daterangepicker_single_call() {

	if (typeof ($.fn.daterangepicker) === 'undefined') {
		return;
	}
	
	$('.mydate_single').each(function(){
		var obj = $(this);
		
		$(obj).daterangepicker({
			singleDatePicker : true,
			singleClasses : "",
			maxDate : moment(),
		}, function(start, end, label) {
			$(this).change();
		});
		
		$(obj).val(moment().subtract(1, 'days').format('MM/DD/YYYY'));//默认昨天日期	
		
	})

}

function init_daterangepicker_reservation() {

	if (typeof ($.fn.daterangepicker) === 'undefined') {
		return;
	}
	console.log('init_daterangepicker_reservation');

	$('#reservation').daterangepicker(null, function(start, end, label) {
		console.log(start.toISOString(), end.toISOString(), label);
	});

	$('#reservation-time').daterangepicker({
		timePicker : true,
		timePickerIncrement : 30,
		locale : {
			format : 'YYYY-MM-DD h:mm A'
		}
	});

}


/**
 * 月日历
 */
function init_daterangepicker_single_Month() {
	$('.monthDate').each(function(){
		var obj = $(this);
		$(obj).datetimepicker({
            language:'zh-CN',
            format: 'yyyy-mm',
            startView: 3,  //这里就设置了默认视图为年视图
            minView: 3,  //设置最小视图为年视图
            maxView:3,
            pickerPosition:"bottom-right",
        });
        $('.monthDate').on('changeDate', function(ev){
          $(".datetimepicker").hide();
        });
	})
}
/**
 * 月日历
 */
function init_daterangepicker_single_day() {
	$('.dayDate').each(function(){
		var obj = $(this);
		$(obj).datetimepicker({
            language:'zh-CN',
            format: 'yyyy-mm-dd',
            startView: 2,  //这里就设置了默认视图为年视图
            minView: 2,  //设置最小视图为年视图
            maxView:2,
            pickerPosition:"bottom-right",
        });
        $('.dayDate').on('changeDate', function(ev){
          $(".datetimepicker").hide();
        });
	})
}