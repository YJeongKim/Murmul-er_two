var divLeft = [];

$(document).ready(function(){
    $.setNumberUnit();
    $.checkType();
    $.initGetLeft();
    // $.setBounds();
    // $.resizeEvent();
    $.setSpecialProvision();
    $('#btnBack').clickEvent('back');
    $('#btnToImage').downloadImage();
    $('#btnExit').clickEvent('close');
});

$.fn.clickEvent = function (flag) {
    let title, text, type;
    switch (flag) {
        case 'back':
            title = "";
            text = "다시 수정하시겠습니까?";
            type = "question";
            break;
        case 'close':
            title = "창 닫기";
            text = "내용은 저장되지 않습니다. 정말로 창을 닫으시겠습니까?";
            type = "warning";
            break;
    }
    $(this).click(function(){
        Swal.fire({
            title: title,
            text: text,
            type: type,
            showCancelButton: true,
            confirmButtonClass: 'btn-success',
            confirmButtonText: '확인',
            cancelButtonClass: 'btn-info',
            cancelButtonText: '취소'
        }).then(result => {
            if (result.value) {
                switch (flag) {
                    case 'back':
                        history.back(); break;
                    case 'close':
                        window.close();
                }
            }
        })
    })
}

$.fn.downloadImage = function () {
    $(this).click(function () {
        $('.contract-body > div').css('color', 'black');
        $('.check').attr('src', '/img/etc/check_black.png');
        html2canvas($('.contract-body')[0]).then(function (canvas) {
            let myImage = canvas.toDataURL("image/png");
            let link = document.createElement("a");
            link.download = "contract.png";
            link.href = myImage;
            document.body.appendChild(link);
            link.click();
        })
        $('.contract-body > div').css('color', 'blue');
        $('.check').attr('src', '/img/etc/check_blue.png');
    })
}

$.checkType = function(){
    let buildingType = $('#buildingType').attr('value');
    let lease =  $('#lease').attr('value');
    let mcType = $('#mcType').attr('value');

    $('#buildingType').attr('class', 'building-type'+buildingType);
    $('#lease').attr('class', 'lease'+lease);
    $('#mcType').attr('class', 'jeondae-monthly-type'+mcType);
}

$.initGetLeft = function(){
    let div = $('.contract-body > div');
    for(let i = 0; i < div.length; i++) {
        let leftpx = div.eq(i).css('left');
        let left = leftpx.substr(0, leftpx.length - 2) * 1;
        divLeft.push(left-7);
    }
}
// $.resizeEvent = function() {
//     $(window).resize(function(){
//         $.setBounds();
//     });
// }
// $.setBounds = function(){
//     let width = $(window).width();
//     if(width < 1000) return;
//     let div = $('.contract-body > div');
//     for(let i = 0; i < divLeft.length; i++) {
//         let plus =  (width - 1000) / 2;
//         div.eq(i).css('left', (divLeft[i]+plus));
//     }
// }
$.setNumberUnit = function() {
    $(".jeondae-deposit").addMan();
    $(".jeondae-contract-cost").addMan();
    $(".jeondae-middle-cost").addMan();
    $(".jeondae-balance").addMan();
    $(".jeondae-monthly-cost").addMan();
    $(".imdae-deposit").addMan();
    $(".imdae-monthly-cost").addMan();
    $(".jeondae-deposit-num").addComma();
    $(".imdae-deposit-num").addComma();
    $(".imdae-monthly-num").addComma();
}
$.setSpecialProvision = function () {
    let manageCost = defend($('.manage-cost').text());
    let manages = defend($('.manage-cost-item').text());
    let options = defend($('.option-item').text());

    if(manageCost === '0') {
        $('.manage-cost').text('');
        $('.manage-cost-item').text('');
    } else {
        $('.manage-cost').text('관리비 : ' + manageCost);
        $('.manage-cost-item').text('(포함항목 : ' + manages.substr(1, manages.length - 2) + ')');
    }
    if(options){
        $('.option-item').text('옵션 : ' + options.substr(1, options.length - 2));
    }
}

$.fn.addMan = function(){
    let text = defend($(this).text());
    if(text === '')
        return;
    if(text.length > 4) {
        let uk = text.substr(0, text.length - 4);
        let man = parseInt(text.substr(-4), 10);
        text = uk + '억 ' + man + '만';
    }
    else
        text = text + '만';
    $(this).text(text);
}

$.fn.addComma = function () {
    let number = defend($(this).text())+'0000';
    let commaNum = number.format();
    $(this).text(commaNum);
}

// 숫자 타입에서 쓸 수 있도록 format() 함수 추가
Number.prototype.format = function(){
    if(this == 0) return '';

    let reg = /(^[+-]?\d+)(\d{3})/;
    let n = (this + '');

    while (reg.test(n)) n = n.replace(reg, '$1' + ',' + '$2');

    return n;
};

// 문자열 타입에서 쓸 수 있도록 format() 함수 추가
String.prototype.format = function(){
    var num = parseFloat(this);
    if( isNaN(num) ) return "";

    return num.format();
};

// 스크립트 방어
function defend(value) {
    value = value + "";
    value = value.trim();
    value = value.replace(/</gi, "&lt;").replace(/>/gi, "&gt;");
    // value = value.replace(/\\(/gi, "& #40;").replace(/\\)/gi, "& #41;");
    value = value.replace(/'/gi, "&#39;");
    value = value.replace(/eval\\((.*)\\)/gi, "");
    value = value.replace(/[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']/gi, "\"\"");
    value = value.replace(/<script>/gi, "");
    value = value.replace(/<\/script>/gi, "");
    return value;
}