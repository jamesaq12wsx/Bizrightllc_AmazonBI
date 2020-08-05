let vcPromotions = [];
let promotionSettings = [];
let checkingPromotionSetting = null;

const checkStatus = {
    NO_SETTING: 'summer',
    NEED_ATTENTION: 'winter',
    SAFE: 'safe',
}

// fetch vc promotion and promotion setting
function searchVcPromotionOnClick() {
    let searchCriteria = getSearchCriteria();

    console.log('Search Vc Promotion', searchCriteria);

    vcPromotions = [];
    promotionSettings = [];

    showLoading()

    fetchVcPromotion(searchCriteria,
        function (value) {
            closeLoading()
            let data = JSON.parse(value);

            console.log('return data', data);

            if (data.status == '1') {

                // console.log('Search promotion setting', data.data.map(p => p.promotionId));

                vcPromotions = data.data.filter(p => p.promotionId !== null);

                let promotionIdsStr = vcPromotions.filter(p => p.promotionId !== null).map(p => p.promotionId).join();

                // Fetch promotion setting record
                fetchSettings(vcPromotions.map(p => p.promotionId),
                    (response) => {
                        let settingResponse = JSON.parse(response);
                        console.log('Promotion setting', response);
                        if (settingResponse.status == '1') {
                            // console.log(response)
                            promotionSettings = settingResponse.data;
                        } else {
                            promotionSettings = [];
                        }
                    }, () => {
                        console.log("Fetch Promotion setting error");
                        promotionSettings = [];
                    }, () => {
                        closeLoading();
                        reDraw();
                    });

            } else {
                vcPromotions = [];
                promotionSettings = [];

                alert("Get Vc Promotion Data Failed");
                console.error(data);
            }
        }, (err) => {
            console.error("Fetch vc promotion failed", err);
            closeLoading();

            vcPromotions = [];
            promotionSettings = [];
        }, function () {
            closeLoading();
        });

}

function fetchVcPromotion(criteria, successCb, errorCb, completeCb) {
    if (criteria) {
        $.ajax({
            url: path + '/promotion/vc.htm',
            type: "POST",
            data: criteria,
            success: successCb,
            error: errorCb,
            completeCb: completeCb
        });
    }
}

function fetchSettings(promotionIds, successCb, errorCb, completeCb) {
    if (promotionIds && promotionIds.length > 0) {

        const promotionIdsStr = promotionIds.join();

        $.ajax({
            url: path + '/promotion/setting/search.htm',
            type: 'POST',
            data: {
                promotionIdsStr: `${promotionIdsStr}`,
                // promotionId: promotionIds[0],
            },
            success: successCb ? successCb : function (response) {
                let settingResponse = JSON.parse(response);
                console.log('Promotion setting', response);
                if (settingResponse.status == '1') {
                    // console.log(response)
                    promotionSettings = settingResponse.data;
                } else {
                    promotionSettings = [];
                }
            },
            error: errorCb ? errorCb : function () {
                console.log("Fetch Promotion setting error");
                promotionSettings = [];
            },
            complete: completeCb
        });
    } else {
        promotionSettings = [];
    }
}

//Redraw table
function reDraw() {

    vcPromotions.forEach(p => {
        if (promotionSettings.filter(s => s.promotionId.toString() === p.promotionId.toString()).length > 0) {

            let settings = promotionSettings.filter(s => s.promotionId.toString() === p.promotionId.toString());

            let needAttention = false;

            // check price and funding for each product
            $.each(p.products, function (index, product) {

                let s = settings.find(s => s.asin.toString() === product.asin.toString());

                if (s){
                    if ((product.websitePrice - product.funding) > (s.price - s.funding)){
                        p.needAttention = checkStatus.NEED_ATTENTION;
                    }else{
                        p.needAttention = checkStatus.SAFE;
                    }
                }else{
                    p.needAttention = checkStatus.NO_SETTING;
                }

            });

        }else{
            p.needAttention = checkStatus.NO_SETTING;
        }
    });

    $("#table").dataTable().fnDestroy();

    let table = $('#table').DataTable({
        pageLength: 20,
        data: vcPromotions,
        order: [[ 1, "desc" ]],
        rowCallback: function (nRow, rowData, index) {

            if(rowData.needAttention === checkStatus.NEED_ATTENTION){
                $('td', nRow).css('background-color', 'Pink');
            }

        },
        columns: [
            {
                "className": 'details-control',
                "orderable": false,
                "data": null,
                "defaultContent": '<span class="addIcon"><i class="iconfont icon-jia"></i></i></span>',
            },
            {
                data: 'needAttention',
                render: function (data, type, row, meta) {
                    // return data ? '<span class="starIcon glyphicon glyphicon-star">Check</span>' : '<span class="checkIcon glyphicon glyphicon-check"></span>';
                    switch (data) {
                        case checkStatus.NO_SETTING:
                            return '';
                        case checkStatus.NEED_ATTENTION:
                            return '<span class="starIcon glyphicon glyphicon-star"></span>Check';
                        case checkStatus.SAFE:
                            return '<span class="checkIcon glyphicon glyphicon-check"></span>Safe';
                        default:
                            return '';
                    }
                }
            },
            {"data": "promotionId"},
            {
                data: 'products',
                render: function (data, type, row, meta) {
                    // console.log(data);
                    let productSkusStr = data.map(p => p.asinSkuMap ? p.asinSkuMap.sku : '').filter(sku => sku !== '').join(',');

                    return productSkusStr.length > 20 ?
                        '<span title="'+productSkusStr+'">'+productSkusStr.substr( 0, 20 )+'...</span>' :
                        productSkusStr;
                }
            },
            {"data": "status"},
            {"data": "type"},
            {"data": "vendorCode"},
            {"data": "marketPlace"},
            {"data": "createdOn"},
            {"data": "startDate"},
            {"data": "endDate"},
            {"data": "insertAt"},
        ],
        // "order": [[1, 'asc']]
    });

    // Apply the search
    // TODO:
    // table.columns().every( function () {
    //     var that = this;
    //
    //     $( 'input', this.footer() ).on( 'keyup change', function () {
    //         if ( that.search() !== this.value ) {
    //             that
    //                 .search( this.value )
    //                 .draw();
    //         }
    //     } );
    // } );

    $('#table tbody').on('click', 'td.details-control', function () {
        let tr = $(this).closest('tr');
        let row = table.row(tr);

        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        } else {
            // Open this row
            row.child(formatDetail(row.data())).show();
            tr.addClass('shown');
        }
    });
}

// On modal close
$('#promotionModal').on('hidden.bs.modal', function () {
    // do something…
    checkingPromotionSetting = null;
    clearModalInput();
});

function clearModalInput() {
    $('#idInput').val(null);
    $('#promotionIdInput').val(null);
    $('#asinInput').val(null);
    $('#priceInput').val(null);
    $('#fundingInput').val(null);
}

function modalClickHandler(e) {
    // console.log(e);

    let settingId = $(e).data('setting');
    let promotionId = $(e).data('promotionid');
    let asin = $(e).data('asin');

    //exist setting
    if (settingId) {
        checkingPromotionSetting = promotionSettings.find(s => s.id === settingId);
    } else {
        // new setting
        checkingPromotionSetting = {};
        if (promotionId) {
            checkingPromotionSetting.promotionId = promotionId;
        }
        if (asin) {
            checkingPromotionSetting.asin = asin;
        }
    }

    openPromotionSettingModal();
}

//open setting modal
function openPromotionSettingModal() {
    $('#promotionModal').modal('show');

    // set the value to the modal
    if (checkingPromotionSetting) {

        $('#deleteSetting').show()

        $('#idInput').val(checkingPromotionSetting.id);
        $('#promotionIdInput').val(checkingPromotionSetting.promotionId);
        $('#asinInput').val(checkingPromotionSetting.asin);
        $('#priceInput').val(checkingPromotionSetting.price);
        $('#fundingInput').val(checkingPromotionSetting.funding);

    } else {
        $('#deleteSetting').hide();
    }
}

//close setting modal
function closePromotionSettingModal() {
    $('#promotionModal').modal('hide')

    checkingPromotionSetting = false;
}

function formatDetail(promotion) {

    // console.log('format detail', promotion);

    var table = '<table style="width: 100%">';

    table += '<thead>\n' +
        '            <tr>\n' +
        '                <th>Asin</th>\n' +
        '                <th>Amazon Price</th>\n' +
        '                <th>Web Price</th>\n' +
        '                <th>Funding</th>\n' +
        '                <th>LikelyPrice</th>\n' +
        '                <th>Amount Spent</th>\n' +
        '                <th>Revenue</th>\n' +
        '                <th>Units Sold</th>\n' +
        '                <th>Setting</th>\n' +
        '            </tr>\n' +
        '        </thead>';

    if (promotion.products) {
        table += '<tbody>';

        $.each(promotion.products, function (index, data) {

            // console.log('check promotionSettings', promotionSettings);

            const promotionId = promotion.promotionId.toString();
            const asin = data.asin.toString();

            const findSetting = promotionSettings.find(s => s.promotionId.toString() === promotionId && s.asin.toString() === asin);

            // console.log(findSetting);

            const settingBtn = findSetting ?
                `<button class="btn btn-primary btn-sm checkPromotionSettingBtn" data-setting="${findSetting.id}" onclick="modalClickHandler(this)">Check</button>` :
                `<button class="btn btn-info btn-sm addPromotionSettingBtn" data-setting="${null}" data-promotionId="${promotionId}" data-asin="${asin}" onclick="modalClickHandler(this)">Add</button>`;

            // check crawl data and setting data
            const needAttention = findSetting ?
                (data.websitePrice - data.funding) > (findSetting.price - findSetting.funding) :
                false;

            console.log('Promotion product need attention', needAttention);

            table += `<tr style="${needAttention ? 'background-color: pink' : ''}">` +
                `<td style="display: none">${promotion.promotionId}</td>` +
                `<td>${data.asin}</td>` +
                `<td>${data.amazonPrice}</td>` +
                `<td>${data.websitePrice}</td>` +
                `<td>${data.funding}</td>` +
                `<td>${data.likelyPrice}</td>` +
                `<td>${(data.amountSpent && data.amountSpent === null) ? 0 : data.amountSpent}</td>` +
                `<td>${data.revenue}</td>` +
                `<td>${data.unitsSold}</td>` +
                `<td>${settingBtn}</td>` +
                `</tr>`;
        });

        table += '</tbody>';
    }

    table += '</table>';

    return table;
}

function getAllPromotionSetting() {

    console.log('Fetch promotion setting');

    $.ajax({
        url: path + '/promotion/setting/all.htm',
        type: 'GET',
        success: function (value) {
            let data = JSON.parse(value);

            console.log('return data', data);
        },
        fail: function (err) {
            console.log('fetch promotion setting failed', err);
        }
    })
}

function getPromotionSetting(promotionId) {

}

function getSearchCriteria() {
    let promotionId = $('#promotionId').val().trim();
    let asin = $('#asin').val().trim();
    let status = $('#status').val().trim();

    let createdOnFrom = $('#createdOnFrom').val().trim();
    let createdOnTo = $('#createdOnTo').val().trim();

    let startDateFrom = $('#startDateFrom').val().trim();
    let startDateTo = $('#startDateTo').val().trim();

    let endDateFrom = $('#endDateFrom').val().trim();
    let endDateTo = $('#endDateTo').val().trim();

    let returnObj = {};

    if (promotionId && promotionId !== '') {
        returnObj.promotionId = promotionId;
    }

    if (asin && asin !== '') {
        returnObj.asin = asin;
    }

    if (status && status !== '') {
        returnObj.status = status;
    }

    if (createdOnFrom && createdOnFrom !== '') {
        returnObj.createdOnFrom = createdOnFrom;
    }

    if (createdOnTo && createdOnTo !== '') {
        returnObj.createdOnTo = createdOnTo;
    }

    if (startDateFrom && startDateFrom !== '') {
        returnObj.startDateFrom = startDateFrom;
    }

    if (startDateTo && startDateTo !== '') {
        returnObj.startDateTo = startDateTo;
    }

    if (endDateFrom && endDateFrom !== '') {
        returnObj.endDateFrom = endDateFrom;
    }

    if (endDateTo && endDateTo !== '') {
        returnObj.endDateTo = endDateTo;
    }

    return returnObj;

}

function savePromotionSetting() {

    let input = getPromotionSettingInput();

    if (checkSetting(input)) {
        // TODO: save setting
        if (isNewSetting(input)) {
            //TODO: add new setting

            addSetting(input);

        } else {
            //TODO: update setting

            updateSetting(input);
        }
    }
}

function checkSetting(setting) {
    if (!setting.promotionId) {
        alert("Promotion id cannot not be empty");
        return false;
    }
    if (!setting.asin) {
        alert("Promotion id cannot not be empty");
        return false;
    }
    if (!setting.price) {
        alert("Promotion id cannot not be empty");
        return false;
    }
    if (!setting.funding) {
        alert("Promotion id cannot not be empty");
        return false;
    }

    return true;
}

function isNewSetting(setting) {
    if (setting.id) {
        return false;
    }

    return true;
}

function getPromotionSettingInput() {
    let id = $('#idInput').val();
    let promotionId = $('#promotionIdInput').val().trim();
    let asin = $('#asinInput').val().trim();
    let price = $('#priceInput').val().trim();
    let funding = $('#fundingInput').val().trim();

    let formInput = {};

    if (id) {
        formInput.id = id;
    }

    if (promotionId) {
        formInput.promotionId = promotionId;
    }

    if (asin) {
        formInput.asin = asin;
    }

    if (price) {
        formInput.price = price;
    }

    if (funding) {
        formInput.funding = funding;
    }

    console.log(formInput);

    return formInput;
}

function deleteSettingClick() {
    if (checkingPromotionSetting) {

        if (confirm("Are you sure to delete this setting?")) {
            deleteSetting({id: checkingPromotionSetting.id});
        }

    } else {
        alert("No promotion setting to delete");
    }
}

function addSetting(requestBody) {
    $.ajax({
        url: path + '/promotion/setting/add.htm',
        type: "POST",
        data: requestBody,
        success: addSettingSuccessHandler,
        error: addSettingErrorHandler,
    });
}

function addSettingSuccessHandler(value) {
    let data = JSON.parse(value);

    console.log('return data', data);

    if (data.status == '1') {

        closePromotionSettingModal();

        // Refetch the data
        reFetchSetting();

    } else {
        alert('Add new setting fail');
        console.error("Add new setting fail", data);
    }
}

function addSettingErrorHandler(err) {
    alert("Add new setting fail");

    console.error(err);
}

function updateSetting(requestBody, successCb, errorCb) {
    $.ajax({
        url: path + '/promotion/setting/update.htm',
        type: "POST",
        data: requestBody,
        success: successCb ? successCb : updateSettingSuccessHandler,
        error: errorCb ? errorCb : updateSettingErrorHandler,
    })
}

function updateSettingSuccessHandler(value) {
    let data = JSON.parse(value);

    console.log('return data', data);

    if (data.status == '1') {

        closePromotionSettingModal();

        // Refetch the data
        reFetchSetting();

    } else {
        alert('Update new setting fail');
        console.error("Update new setting fail", data);
    }
}

function updateSettingErrorHandler(err) {
    alert("Update new setting fail");

    console.error(err);
}

function deleteSetting(requestBody) {
    $.ajax({
        url: path + '/promotion/setting/delete.htm',
        type: "POST",
        data: requestBody,
        success: deleteSettingSuccessHandler,
        error: deleteSettingErrorHandler,
    });
}

function deleteSettingSuccessHandler(value) {
    let data = JSON.parse(value);

    console.log('return data', data);

    if (data.status == '1') {

        closePromotionSettingModal();

        // Refetch the data
        reFetchSetting();

    } else {
        alert('Delete new setting fail');
        console.error("Delete new setting fail", data);
    }
}

function deleteSettingErrorHandler(err) {
    alert("Delete new setting fail");

    console.error(err);
}

function reFetchSetting() {
    showLoading();
    fetchSettings(vcPromotions.filter(p => p.promotionId !== null).map(p => p.promotionId),
        (value) => {
            let settingResponse = JSON.parse(value);
            console.log('Promotion setting', value);
            if (settingResponse.status == '1') {
                // console.log(response)
                promotionSettings = settingResponse.data;

                reDraw();

            } else {
                promotionSettings = [];
            }
        }, (err) => {
            console.log("Fetch Promotion setting error");
            promotionSettings = [];
        }, () => {
            closeLoading();
        });
}

function newSettingBtnOnClick(){
    checkingPromotionSetting = {};

    openPromotionSettingModal();
}

function init() {
    $("#priceInput").TouchSpin({
        min: 0,
        max: 10000,
        step: 0.01,
        decimals: 2,
        prefix: '$',
        booster: true,
        boostat: 10,
        maxboostedstep: 10,
    });

    $("#fundingInput").TouchSpin({
        min: 0,
        max: 10000,
        step: 0.01,
        decimals: 2,
        prefix: '$',
        booster: true,
        boostat: 10,
        maxboostedstep: 10,
    });

    $('#newSettingBtn').click(newSettingBtnOnClick);
}

$(document).ready(init);