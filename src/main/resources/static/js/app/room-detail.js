let optionList = [];
let hashTagList = [];
let maintenanceOptionList = [];
let imageList = [];
let salesPostId = 0;

$(document).ready(function () {
    let options = $('#tbOption').attr('value');
    options = options.substring(1, options.length - 1); // 양 옆 괄호 제거
    optionList = options.split(", "); // 콤마 제거

    let hashTags = $('.hashTag').attr('value');
    hashTags = hashTags.substring(1, hashTags.length - 1); // 양 옆 괄호 제거
    hashTagList = hashTags.split(", "); // 콤마 제거
    $.setHashTag();

    let maintenanceOptions = $('#maintenance').attr('value');
    if(maintenanceOptions !== "[]")
        $(maintenanceOptions).append($('#maintenance'));

    let images = $('.slider').attr('value');
    salesPostId = images[0];
    images = images.substring(3, images.length - 1); // 양 옆 괄호 제거
    imageList = images.split(", "); // 콤마 제거
    $.setImage();

    let area = $('#area').attr('value');
    let pyeong = Math.round((area / 3.3));
    $('#area').text(area + "m² / " + pyeong + "평");

    $.footerControl();

    $("#map").css('width', '100%');

    $.setSubOption();
    $.setOption();

    $('.slider').bxSlider({
        auto: false, // 자동 애니메이션
        speed: 500, // 애니메이션 속도
        pause: 5000, // 애니메이션 유지 시간(1000 = 1초)
        mode: 'horizontal', // 슬라이드 모드('fade', 'vertical', 'horizontal')
        autoControls: false, // 시작,중지 버튼 유/무
        pager: true, // 페이지 보여짐
        captions: true // 이미지위에 텍스트 넣기
    });
})

$.setImage = function () {
    let len = imageList.length;

    for (let i = 0; i < len; i++) {
        let fileName = imageList[i];
        let src = '/files/download?id=' + encodeURI(salesPostId) + '&image=' + encodeURI(fileName);
        let content = '<li>'
            + '<img class="roomImg" id="preview-' + (i + 1) + '" width="700px" height="480px"'
            + 'src=' + src + '/></li>';

        $(content).appendTo($('.slider'));
    }
}
$.setHashTag = function () {
    let len = hashTagList.length;
    let hashTag = "";

    for (let i = 0; i < len; i++) {
        hashTag += "#" + hashTagList[i];
        if (i != len - 1)
            hashTag += "  ";
    }
    $('.hashTag').text(hashTag);
}
$.setOption = function () {
    let len = optionList.length;
    let tr;
    for (let i = 0; i < len; i++) {
        let op_name;
        if (i % 5 === 0) {
            tr = $('<tr />').appendTo($('#tbOption'));
        }
        switch (optionList[i]) {
            case "냉장고":
                op_name = "refrigerator.svg";
                break;
            case "에어컨":
                op_name = "aircondition.svg";
                break;
            case "세탁기":
                op_name = "laundry.svg";
                break;
            case "TV":
                op_name = "tv.svg";
                break;
            case "가스레인지":
                op_name = "gas.svg";
                break;
            case "전자레인지":
                op_name = "microoven.svg";
                break;
            case "인덕션":
                op_name = "induction.svg";
                break;
            case "옷장":
                op_name = "closet.svg";
                break;
            case "책상":
                op_name = "desk.svg";
                break;
            case "침대":
                op_name = "bed.svg";
                break;
            case "비데":
                op_name = "bidet.svg";
                break;
            case "신발장":
                op_name = "shoes.svg";
                break;
            case "현관문안전장치":
                op_name = "safetydoor.png";
                break;
            case "디지털도어락":
                op_name = "doorlock.svg";
                break;
            default:
                op_name = "noimg.png";
                break;
        }
        let content = '<td><img src="/img/option/' + op_name + '" width="40px" height="40px"><div>'
            + optionList[i] + '</div></td>';
        $(content).appendTo(tr);
    }
}
$.setSubOption = function () {
    let idx = optionList.indexOf("주차장");
    if (idx >= 0) {
        $('#parking').text("있음");
        optionList.splice(idx, 1);
    } else {
        $('#parking').text("없음");
    }

    idx = optionList.indexOf("엘리베이터");
    if (idx >= 0) {
        $('#elevator').text("있음");
        optionList.splice(idx, 1);
    } else {
        $('#elevator').text("없음");
    }

    idx = optionList.indexOf("반려동물 수용");
    if (idx >= 0) {
        $('#pet').text("가능");
        optionList.splice(idx, 1);
    } else {
        $('#pet').text("불가능");
    }
}
$(window).resize(function () {
    $.footerControl();
});
$.footerControl = function () {
    if ($(window).width() > 1070) {
        let width = $(window).width();
        let ph = 1070;
        let left = (width - ph) / 2;
        $('.footer').css('left', left);
    }
}