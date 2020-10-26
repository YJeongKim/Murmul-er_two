$(document).ready(function() {
    $.initSelectDate();
    $.calcSelectDay();
    $('#cancel').clickCancelBtn();
    $('#lastBtn').clickRegisterBtn();
    $.numberUnit();
    $.setRadioType('register');
})


$.calcSelectDay = function(){
    $('select.s1').change(changeDate);
    $('select.s2').change(changeDate);
}


$.initSelectDate = function(){
    $.settingYear();
    $.settingMonth();
}

$.settingYear = function() {
    let today = new Date();
    let year = today.getFullYear();
    let GAP = 5;

    for(let i = year; i < year+GAP; i++){
        let o1 = new Option(i+"", i);
        let o2 = new Option(i+"", i);

        let idx = document.contractForm.fromYearS.options.length;

        document.contractForm.fromYearS.options[idx] = o1;
        document.contractForm.toYearS.options[idx] = o2;
    }
}
$.settingMonth = function() {
    for(let i = 1; i <= 12; i++){
        let o1 = new Option(i+'', i.zf(2));
        let o2 = new Option(i+'', i.zf(2));

        let idx = document.contractForm.fromMonthS.options.length;

        document.contractForm.fromMonthS.options[idx] = o1;
        document.contractForm.toMonthS.options[idx] = o2;
    }
}
var readFile = function(){
    $('#uploadFile').trigger('click');
}

var readName = function(input){
    if (input.files && input.files[0]) {
        let imgName = input.files[0].name;
        let fileExt = imgName.slice(imgName.indexOf(".") + 1).toLowerCase();
        if(fileExt != "jpg" && fileExt != "png" && fileExt != "jpeg" && fileExt != "PNG"){
            Swal.fire('', '파일 첨부는 이미지 파일(jpg, jpeg, png)만 등록이 가능합니다,', 'warning');
            return;
        }
        let reader = new FileReader();
        reader.onload = function () {
            $('#fileName').text(imgName);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
$.fn.clickRegisterBtn = function() {
    $(this).click(function(){
        if(!$(this).checkValid())
            return ;
        Swal.fire({
            text: "이대로 등록하시겠습니까?",
            type: "question",
            showCancelButton: true,
            confirmButtonClass: 'btn-success',
            confirmButtonText: '확인',
            cancelButtonClass: 'btn-info',
            cancelButtonText: '취소'
        }).then(result => {
            if (result.value) {
                $.uploadImage();
            }
        })
    });
}

$.uploadImage = function () {
    let formData = new FormData();

    let inputFile = $("input[name='uploadFile']");
    let files = inputFile[0].files;

    let leaseDeposit = $('#deposit').val();
    let leaseFee = $('#monthlyCost').val();
    let fromYearS = $('#fromYearS').val();
    let fromMonthS = $('#fromMonthS').val();
    let fromDayS = $('#fromDayS').val();
    let toYearS = $('#toYearS').val();
    let toMonthS = $('#toMonthS').val();
    let toDayS = $('#toDayS').val();
    let roomId = $('input[name=roomId]').val();
    let sublessor = $('input[name=sublessor]').val();
    let sublessee = $('input[name=sublessee]').val();


    let from = fromYearS + "-" + fromMonthS + "-" + fromDayS;
    let to = toYearS + "-" + toMonthS + "-" + toDayS;

    let lease;
    let checkLease = $('input:radio[name="lease"]:checked').val();
    switch (checkLease) {
        case '1':
            lease = "전세";
            break;
        case '2':
            lease = "월세";
            break;
        case '3':
            lease = "보증금 있는 월세";
            break;
    }

    console.log(lease);

    formData.append("contractForm", files[0]);
    formData.append("leaseDeposit", leaseDeposit);
    formData.append("leaseFee", leaseFee);
    formData.append("stayFrom", from);
    formData.append("stayTo", to);
    formData.append("sublessor", sublessor);
    formData.append("sublessee", sublessee);
    formData.append("roomId", roomId);
    formData.append("lease", lease);

    console.log(formData);

    $.ajax({
        url: '/api/contracts/register',
        processData: false,
        contentType: false,
        data: formData,
        enctype: 'multipart/form-data',
        dataType: 'json',
        type: 'POST'
    }).then(function (data, status) {
        if (status === "success" && data.status === "SUCCESS") {
            Swal.fire('등록 완료', '계약서 등록이 완료되었습니다.', "success")
                .then(function () {
                    Swal.fire('', '계약서는 마이페이지의 계약서 관리 탭에서 확인 가능합니다. 확인을 누르시면 창을 종료합니다.', 'info')
                        .then(function () {
                            let customWindow = window.open('', '_blank', '');
                            customWindow.close();
                        })
                });
        } else {
            Swal.fire('등록 실패', '잠시후 다시 시도해주세요.', 'error');
        }
    });
}

$.fn.checkValid = function() {
    if($('input[name="lease"]:checked').val() === undefined){
        Swal.fire("임대 유형을 선택해주세요");
        return false;
    }
    if(!$('#deposit').isBlank("보증금")) return false;
    if(!$('#deposit').isNum("보증금")) return false;
    if(!$('#monthlyCost').isBlank("월세"))  return false;
    if(!$('#monthlyCost').isNum("월세")) return false;

    if(!$('#fromYearS').isChecked()) return false;
    if(!$('#fromMonthS').isChecked()) return false;
    if(!$('#fromDayS').isChecked()) return false;
    if(!$('#toYearS').isChecked()) return false;
    if(!$('#toMonthS').isChecked()) return false;
    if(!$('#toDayS').isChecked()) return false;

    if($('#fileName').text() === '') {
        Swal.fire('계약서 스캔본을 첨부해주세요.');
        return false;
    }

    return true;
}

$.fn.isChecked = function() {
    if($(this).val() === ""){
        Swal.fire('날짜를 모두 선택해주세요');
        return false;
    }
    return true;
}
$.fn.isBlank = function(type) {
    if($(this).val() === "")
    {
        Swal.fire(type+" 항목을 입력하세요");
        $(this).focus();
        return false;
    }
    return true;
}
$.fn.isNum = function(type) {
    let num = $(this).val();
    num += '';
    num = num.replace(/^\s*|\s*$/g, '');
    if (num == '' || isNaN(num)) {
        Swal.fire(type+' 항목은 숫자만 입력하세요');
        $(this).val('');
        $(this).focus();
        return false;
    }
    return true;
}
