let allAddress = {};
let maintenanceOptionList = [];
let optionList = [];
let hashTagList = [];

let selectedRoomType = 0;
let selectedLease = 0;
let selectedMaintenanceFee = 0;
let selectedHeating = 0;
let selectedPetAcceptance = 0;
let selectedParkingLot = 0;
let selectedElevator = 0;

let x = 'x';
let cnt = 0;

const selectedColor = 'rgb(100, 149, 237)';
const maxMoney = 210000;

// 이미지를 전송할 폼 데이터
let formData = new FormData();

// 장소 검색 객체 생성
var ps = new kakao.maps.services.Places();

var room_save = {
    init : function () {
        let _this = this;

        $('#btnSaveRoom').css('border-bottom', '7px solid #93B5F2');

        $('#btn-save').on('click', function () {
            $.checkInput();
        });
        $('#btn-cancel').on('click', function () {
            _this.cancel();
        });
        $('#btnImg').on('click', function () {
            $('#uploadImages').trigger('click');
        });
    }, cancel : function () {
        Swal.fire({
            title: "취소",
            text: "방 등록을 취소하시겠습니까?",
            type: "question",
            showCancelButton: true,
            confirmButtonClass: 'btn-success',
            confirmButtonText: '확인',
            cancelButtonClass: 'btn-info',
            cancelButtonText: '취소'
        }).then(result => {
            if (result.value) {
                window.location.href = '/rooms';
            }
        })
    }
}

room_save.init();

$(document).ready(function () {
    $.clickButtonEvent();
})

$.clickButtonEvent = function () {

    $('#btnRoomType-1').clickRoomType();
    $('#btnRoomType-2').clickRoomType();
    $('#btnRoomType-3').clickRoomType();
    $('#btnRoomType-4').clickRoomType();
    $('#btnRoomType-5').clickRoomType();

    $('#btnSearchAddress').on('click', function () {
        getAddress();
    });

    $('#btnLease-1').clickLease();
    $('#btnLease-2').clickLease();
    $('#btnLease-3').clickLease();

    $('#btnMaintenanceFee-1').clickMaintenanceFee();
    $('#btnMaintenanceFee-2').clickMaintenanceFee();

    $('#btnMaintenanceOptions-1').clickMaintenanceOptions();
    $('#btnMaintenanceOptions-2').clickMaintenanceOptions();
    $('#btnMaintenanceOptions-3').clickMaintenanceOptions();
    $('#btnMaintenanceOptions-4').clickMaintenanceOptions();
    $('#btnMaintenanceOptions-5').clickMaintenanceOptions();
    $('#btnMaintenanceOptions-6').clickMaintenanceOptions();

    $('#btnHeating-1').clickHeating();
    $('#btnHeating-2').clickHeating();
    $('#btnHeating-3').clickHeating();

    $('#btnPetAcceptance-1').clickPetAcceptance();
    $('#btnPetAcceptance-2').clickPetAcceptance();

    $('#btnParkingLot-1').clickParkingLot();
    $('#btnParkingLot-2').clickParkingLot();

    $('#btnElevator-1').clickElevator();
    $('#btnElevator-2').clickElevator();

    $('#btnOptions-1').clickOptions();
    $('#btnOptions-2').clickOptions();
    $('#btnOptions-3').clickOptions();
    $('#btnOptions-4').clickOptions();
    $('#btnOptions-5').clickOptions();
    $('#btnOptions-6').clickOptions();
    $('#btnOptions-7').clickOptions();
    $('#btnOptions-8').clickOptions();
    $('#btnOptions-9').clickOptions();
    $('#btnOptions-10').clickOptions();
    $('#btnOptions-11').clickOptions();
    $('#btnOptions-12').clickOptions();
    $('#btnOptions-13').clickOptions();
    $('#btnOptions-14').clickOptions();
}

let getAddress = function () {
    new daum.Postcode({
        oncomplete: function (data) {
            allAddress = data;
            $('#inputAddress').attr('value', data.roadAddress);
            $('#inputDetailAddress').val('');
        }
    }).open();
};

let changeAddress = function () {
    ps.keywordSearch($('#inputAddress').val(), placesSearchCB);
}

$.fn.changeSize = function() {
    $(this).keyup(function(event) {
        if (!(event.keyCode >=37 && event.keyCode<=40)) {
            let inputVal = $(this).val();
            $(this).val(inputVal.replace(/[a-zㄱ-힣,~`!@#$%^&*()_+=<>/]/gi,''));
        }
        let area = parseFloat($('#inputSize').val() * 3.305785).toFixed(2);
        $('#inputArea').val(area);
    });
}

$.fn.changeArea = function() {
    $(this).keyup(function(event) {
        if (!(event.keyCode >=37 && event.keyCode<=40)) {
            let inputVal = $(this).val();
            $(this).val(inputVal.replace(/[a-zㄱ-힣,~`!@#$%^&*()_+=<>/]/gi,''));
        }
    });
    let area = Math.round($('#inputArea').val() / 3.305785);
    $('#inputSize').val(area);
}

/* 버튼 클릭 이벤트 START */
$.fn.clickRoomType = function () {
    $(this).on('click', function () {
        if (selectedRoomType !== 0) {
            $('#btnRoomType-' + selectedRoomType).css('background-color', '');
            $('#btnRoomType-' + selectedRoomType).css('color', '');
        }
        $(this).css('background-color', 'cornflowerblue');
        $(this).css('color', 'white');
        selectedRoomType = $(this).attr('id').split('btnRoomType-')[1];
    })
}
$.fn.clickLease = function () {
    $(this).on('click', function () {
        if (selectedLease !== 0) {
            $('#btnLease-' + selectedLease).css('background-color', '');
            $('#btnLease-' + selectedLease).css('color', '');
        }
        $(this).css('background-color', 'cornflowerblue');
        $(this).css('color', 'white');
        selectedLease = $(this).attr('id').split('btnLease-')[1];

        if (selectedLease == 1) {
            $('#inputLeaseFee').attr("readonly", true);
            $('#inputLeaseDeposit').val("");
            $('#inputLeaseFee').val("0");
        } else {
            $('#inputLeaseFee').removeAttr('readonly');
            $('#inputLeaseDeposit').val("");
            $('#inputLeaseFee').val("");
        }
    });
}
$.fn.clickMaintenanceFee = function () {
    $(this).on('click', function () {
        if (selectedMaintenanceFee !== 0) {
            $('#btnMaintenanceFee-' + selectedMaintenanceFee).css('background-color', '');
            $('#btnMaintenanceFee-' + selectedMaintenanceFee).css('color', '');
        }
        $(this).css('background-color', 'cornflowerblue');
        $(this).css('color', 'white');
        selectedMaintenanceFee = $(this).attr('id').split('btnMaintenanceFee-')[1];

        if ($('#btnMaintenanceFee-' + selectedMaintenanceFee).val() == "Y") {
            $('#inputMaintenanceFee').removeAttr('readonly');
        } else {
            $('#inputMaintenanceFee').val('');
            $('#inputMaintenanceFee').attr('readonly', true);

            $('#btnMaintenanceOptions-1').css('background-color', '');
            $('#btnMaintenanceOptions-2').css('background-color', '');
            $('#btnMaintenanceOptions-3').css('background-color', '');
            $('#btnMaintenanceOptions-4').css('background-color', '');
            $('#btnMaintenanceOptions-5').css('background-color', '');
            $('#btnMaintenanceOptions-6').css('background-color', '');
            $('#btnMaintenanceOptions-1').css('color', '');
            $('#btnMaintenanceOptions-2').css('color', '');
            $('#btnMaintenanceOptions-3').css('color', '');
            $('#btnMaintenanceOptions-4').css('color', '');
            $('#btnMaintenanceOptions-5').css('color', '');
            $('#btnMaintenanceOptions-6').css('color', '');

            maintenanceOptionList = [];
        }
    })
}
$.fn.clickMaintenanceOptions = function () {
    $(this).on('click', function () {
        // MaintenanceFee가 있는 경우에만 버튼 작동
        if ($('#btnMaintenanceFee-' + selectedMaintenanceFee).val() == "Y") {
            // 이미 선택된 버튼인 경우 제외
            if ($(this).css('background-color') === selectedColor) {
                $(this).css('background-color', '');
                $(this).css('color', '');
                for (let i = 0; i < maintenanceOptionList.length; i++) {
                    if (maintenanceOptionList[i] === $(this).val())
                        maintenanceOptionList.splice(i, 1);
                }
            } else {
                $(this).css('background-color', 'cornflowerblue');
                $(this).css('color', 'white');
                maintenanceOptionList.push($(this).val());
            }
        }
    })
}
$.fn.clickHeating = function () {
    $(this).on('click', function () {
        if (selectedHeating !== 0) {
            $('#btnHeating-' + selectedHeating).css('background-color', '');
            $('#btnHeating-' + selectedHeating).css('color', '');
        }
        $(this).css('background-color', 'cornflowerblue');
        $(this).css('color', 'white');
        selectedHeating = $(this).attr('id').split('btnHeating-')[1];
    })
}
$.fn.clickPetAcceptance = function () {
    $(this).on('click', function () {
        if (selectedPetAcceptance !== 0) {
            $('#btnPetAcceptance-' + selectedPetAcceptance).css('background-color', '');
            $('#btnPetAcceptance-' + selectedPetAcceptance).css('color', '');
            for (let i = 0; i < optionList.length; i++) {
                if (optionList[i] === $('#btnPetAcceptance-2').val())
                    optionList.splice(i, 1);
            }
        }
        $(this).css('background-color', 'cornflowerblue');
        $(this).css('color', 'white');
        selectedPetAcceptance = $(this).attr('id').split('btnPetAcceptance-')[1];

        if (selectedPetAcceptance == 2)
            optionList.push($(this).val());
    })
}
$.fn.clickParkingLot = function () {
    $(this).on('click', function () {
        if (selectedParkingLot !== 0) {
            $('#btnParkingLot-' + selectedParkingLot).css('background-color', '');
            $('#btnParkingLot-' + selectedParkingLot).css('color', '');
            for (let i = 0; i < optionList.length; i++) {
                if (optionList[i] === $('#btnParkingLot-2').val()) {
                    optionList.splice(i, 1);
                }
            }
        }
        $(this).css('background-color', 'cornflowerblue');
        $(this).css('color', 'white');
        selectedParkingLot = $(this).attr('id').split('btnParkingLot-')[1];

        if (selectedParkingLot == 2)
            optionList.push($(this).val());
    })
}
$.fn.clickElevator = function () {
    $(this).on('click', function () {
        if (selectedElevator !== 0) {
            $('#btnElevator-' + selectedElevator).css('background-color', '');
            $('#btnElevator-' + selectedElevator).css('color', '');
            for (let i = 0; i < optionList.length; i++) {
                if (optionList[i] === $('#btnElevator-2').val())
                    optionList.splice(i, 1);
            }
        }
        $(this).css('background-color', 'cornflowerblue');
        $(this).css('color', 'white');
        selectedElevator = $(this).attr('id').split('btnElevator-')[1];

        if (selectedElevator == 2)
            optionList.push($(this).val());
    })
}
$.fn.clickOptions = function () {
    $(this).on('click', function () {
        // 이미 선택된 버튼인 경우 제외
        if ($(this).css('background-color') === selectedColor) {
            $(this).css('background-color', '');
            $(this).css('color', '');
            for (let i = 0; i < optionList.length; i++) {
                if (optionList[i] === $(this).val()) {
                    optionList.splice(i, 1);
                }
            }
        } else {
            $(this).css('background-color', 'cornflowerblue');
            $(this).css('color', 'white');
            optionList.push($(this).val());
        }
    })
}
/* 버튼 클릭 이벤트 END */

function placesSearchCB(result, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
        allAddress.latitude = result[0].y;
        allAddress.longitude = result[0].x;
        saveRoom();
    } else {
        swalFocus("", "없는 주소입니다.", "error", "#inputAddress");
    }
}

function pushHashTag() {
    for (let i = 1; i <= 3; i++) {
        if ($('#inputHashTag-' + i).val() !== "")
            hashTagList.push(defend($('#inputHashTag-' + i).val()));
    }
}

function saveRoom() {
    let maintenanceOptions = maintenanceOptionList;
    let options = optionList;
    let hashTags = hashTagList;
    let jibunAddress = allAddress.jibunAddress;
    if(jibunAddress === "") jibunAddress = allAddress.autoJibunAddress;

    let roomInfo = {
        latitude: allAddress.latitude,
        longitude: allAddress.longitude,
        jibunAddress: jibunAddress,
        roadAddress: allAddress.roadAddress,
        detailAddress: defend($('#inputDetailAddress').val()),
        buildingName: allAddress.buildingName,
        area: $('#inputArea').val(),
        floor: $('#inputFloor').val(),
        roomType: $('#btnRoomType-' + selectedRoomType).val(),
        heating: $('#btnHeating-' + selectedHeating).val(),
        options: options,
        title: defend($('#inputTitle').val()),
        content: defend($('#inputContent').val()),
        hashTags: hashTags,
        lease: $('#btnLease-' + selectedLease).val(),
        leaseDeposit: $('#inputLeaseDeposit').val() * 10000,
        leaseFee: $('#inputLeaseFee').val() * 10000,
        leasePeriod: $('#inputLeasePeriod').val(),
        periodUnit: $('#inputPeriodUnit').val(),
        maintenanceFee: $('#inputMaintenanceFee').val() * 10000,
        maintenanceOptions: maintenanceOptions
    };

    if (formData.get("images") == null) {
        return swalWarning('', '사진을 등록해주세요', '#tdImg');
    } else {
        $('#btn-save').attr('disabled', true);
        $('#bat-cancel').attr('disabled', true);

        $.ajax({
            type: 'POST',
            url: '/api/rooms',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(roomInfo)
        }).then(function (data, status) {
            if (status === 'success') {
                switch (data.status) {
                    case "SUCCESS":
                        let message = data.message;
                        let id = data.id;
                        $.ajax({
                            url: '/files/upload/images',
                            processData: false,
                            contentType: false,
                            data: formData,
                            dataType: 'json',
                            type: 'POST'
                        }).then(function (data, status) {
                            if (status === 'success' && data.status === "SUCCESS") {
                                Swal.fire('등록 성공', message, 'success')
                                    .then(function () {
                                        location.href = "/rooms";
                                    });
                            } else {
                                Swal.fire(data.message, data.subMessage, 'error');
                                $.ajax({
                                    type: 'DELETE',
                                    url: '/api/rooms/' + id,
                                    dataType: 'json',
                                    contentType: 'application/json; charset=utf-8',
                                }).fail(function () {
                                    Swal.fire("ERROR", "관리자에게 문의하세요.", 'error');
                                });
                            }
                        });
                        break;
                    case "FAIL":
                        Swal.fire(data.message, data.subMessage, 'error');
                        break;
                }
            } else {
                Swal.fire(data.message, data.subMessage, 'error');
            }
        });
        $('#btn-save').attr('disabled', false);
        $('#btn-cancel').attr('disabled', false);
    }
}

function readURL(input) {
    let td = $('#tdImg');
    let loopCnt = 0;
    if (input.files && input.files[0]) {
        for (let i = 1; i <= input.files.length; i++) {
            loopCnt++;
            let index = i + cnt;
            let imgName = input.files[i - 1].name;

            // 파일 확장자를 잘라내고, 비교를 위해 소문자로 변환
            let fileExt = imgName.slice(imgName.indexOf(".") + 1).toLowerCase();

            if (fileExt != "jpg" && fileExt != "jpeg" && fileExt != "png" && fileExt != "bmp") {
                Swal.fire('', '파일 첨부는 이미지 파일(jpg, jpeg, png, bmp)만 등록이 가능합니다,', 'warning');
                return;
            }

            let reader = new FileReader();
            reader.onload = function (e) {
                let img = $(''
                    + '<div class="img-wrap" id=img-wrap' + index + ' name="' + imgName + '">'
                    + '<span class="close" id=close' + index + '>' + x + '</span>'
                    + '<img class="addImage" data-id=rmimg' + index + ' src=' + e.target.result + ' name="addImage"/>'
                    + '</div>'
                );
                td.append(img);
                $('#close' + index).on('click', function () {
                    let rmDiv = $(this).parent()[0];
                    let num;
                    let imgDiv = $('.img-wrap');
                    for (let i = 0; i < imgDiv.length; i++) {
                        if (imgDiv[i] == rmDiv) {
                            num = i;
                        }
                    }
                    let fileArray = formData.getAll("images");
                    fileArray.splice(num, 1);
                    formData.delete("images");
                    for (let i = 0; i < fileArray.length; i++) {
                        formData.append("images", fileArray[i]);
                    }
                    rmDiv.remove();
                });
            }
            reader.readAsDataURL(input.files[i - 1]);
        }

        let inputFile = $("input[name='uploadFile']");
        let files = inputFile[0].files;
        for (let i = 0; i < files.length; i++) {
            formData.append("images", files[i]);
        }
    }
    cnt += loopCnt;
}

$.checkInput = function() {
    let positive_integer = /^[0-9]*$/;
    let integer = /^[-]?\d*$/g;
    let check = 0;

    for (let i = 1; i <= 5; i++) {
        if ($('#btnRoomType-' + i).css('background-color') === selectedColor) {
            check++;
        }
    }
    if (check == 0) {
        return swalWarning('', '방 형태를 선택하세요.', '#btnRoomType-1');
    }

    check = 0;
    for (let i = 1; i <= 3; i++) {
        if ($('#btnLease-' + i).css('background-color') === selectedColor) {
            check++;
        }
    }
    if (check == 0) {
        return swalWarning('', "금액 유형을 선택하세요.", '#btnLease-2');
    }

    check = 0;
    if ($('#inputLeaseDeposit').val() <= maxMoney && $('#inputLeaseFee').val() <= maxMoney && $('#inputMaintenanceFee').val() <= maxMoney) {
        check++;
    }
    if (check == 0) {
        if ($('#inputLeaseDeposit').val() > maxMoney) {
            return swalWarning("보증금/월세/관리비를 확인하세요.", "210000이하로 입력하세요.", '#inputLeaseDeposit');
        }
        if ($('#inputLeaseFee').val() > maxMoney) {
            return swalWarning("보증금/월세/관리비를 확인하세요.", "210000이하로 입력하세요.", '#inputLeaseFee');
        }
        if ($('#inputMaintenanceFee').val() > maxMoney) {
            return swalWarning("보증금/월세/관리비를 확인하세요.", "210000이하로 입력하세요.", '#inputMaintenanceFee');
        }
    }

    check = 0;
    for (let i = 1; i <= 2; i++) {
        if ($('#btnMaintenanceFee-' + i).css('background-color') === selectedColor) {
            check++;
        }
    }
    if (check == 0) {
        return swalWarning("", "관리비 유무를 선택하세요.", '#btnMaintenanceFee-1');
    }

    if ($('#btnMaintenanceFee-2').css('background-color') === selectedColor) {
        check = 0;
        for (let i = 1; i <= 6; i++) {
            if ($('#btnMaintenanceOptions-' + i).css('background-color') === selectedColor) {
                check++;
            }
        }
        if (check == 0) {
            return swalWarning("", "관리비 포함 항목을 선택하세요.", '#btnMaintenanceOptions-1');
        }
    }

    check = 0;
    for (let i = 1; i <= 3; i++) {
        if ($('#btnHeating-' + i).css('background-color') === selectedColor) {
            check++;
        }
    }
    if (check == 0) {
        return swalWarning("", "난방 종류를 선택하세요.", '#btnHeating-1');
    }

    check = 0;
    for (let i = 1; i <= 2; i++) {
        if ($('#btnPetAcceptance-' + i).css('background-color') === selectedColor) {
            check++;
        }
    }
    if (check == 0) {
        return swalWarning("", "반려동물 수용 여부를 선택하세요.", '#btnPetAcceptance-1');
    }

    check = 0;
    for (let i = 1; i <= 2; i++) {
        if ($('#btnParkingLot-' + i).css('background-color') === selectedColor) {
            check++;
        }
    }
    if (check == 0) {
        return swalWarning("", "주차장 여부를 선택하세요.", '#btnParkingLot-1');
    }

    check = 0;
    for (let i = 1; i <= 2; i++) {
        if ($('#btnElevator-' + i).css('background-color') === selectedColor) {
            check++;
        }
    }
    if (check == 0) {
        return swalWarning("", "엘리베이터 여부를 선택하세요.", '#btnElevator-1');
    }

    check = 0;
    for (let i = 1; i <= 14; i++) {
        if ($('#btnOptions-' + i).css('background-color') === selectedColor) {
            check++;
        }
    }
    if (check == 0) {
        return swalWarning("", "옵션 항목을 선택하세요.", '#btnOptions-1');
    }

    if ($('#inputAddress').val() == "") {
        return swalWarning("", "주소를 입력하세요.", '#inputAddress');
    }
    if ($('#inputDetailAddress').val() == "") {
        return swalWarning("", "상세 주소를 입력하세요.", '#inputDetailAddress');
    }
    if (defend($('#inputDetailAddress').val()).length > 100) {
        return swalWarning("", "상세 주소는 30자를 넘을 수 없습니다.", '#inputDetailAddress');
    }

    if ($('#inputSize').val() <= 0) {
        return swalWarning("잘못된 평 수입니다.", "양수를 입력하세요.", '#inputSize');
    } else if ($('#inputSize').val() == "") {
        return swalWarning("", "평 수를 입력하세요.", '#inputSize');
    }

    if ($('#inputArea').val() <= 0) {
        return swalWarning("잘못된 면적입니다.", "양수를 입력하세요.", '#inputArea');
    } else if ($('#inputArea').val() == "") {
        return swalWarning("", "면적을 입력하세요.", '#inputArea');
    }

    if (!integer.test($('#inputFloor').val())) {
        return swalWarning("잘못된 층수입니다.", "정수를 입력하세요.", '#inputFloor');
    } else if ($('#inputFloor').val() == "") {
        return swalWarning("", "층수를 입력하세요.", '#inputFloor');
    }

    // 전세인 경우
    if (selectedLease == 1 && $('#inputLeaseDeposit').val() == "") {
        return swalWarning("", "보증금을 입력하세요.", '#inputLeaseDeposit');
    } // 월세 단기인 경우
    else if ((selectedLease == 2 || selectedLease == 3) && $('#inputLeaseFee').val() == "") {
        return swalWarning("", "월세 / 단기 금액을 입력하세요.", '#inputLeaseFee');
    }
    if (!positive_integer.test($('#inputLeaseDeposit').val())) {
        return swalWarning("잘못된 금액입니다.", "양의 정수를 입력하세요.", '#inputLeaseDeposit');
    }
    if (!positive_integer.test($('#inputLeaseFee').val())) {
        return swalWarning("잘못된 금액입니다.", "양의 정수를 입력하세요.", '#inputLeaseFee');
    }

    if (!positive_integer.test($('#inputLeasePeriod').val())) {
        return swalWarning("잘못된 임대기간입니다.", "양의 정수를 입력하세요.", '#inputLeasePeriod');
    } else if ($('#inputLeasePeriod').val() == "") {
        return swalWarning("", "임대기간을 입력하세요.", '#inputLeasePeriod');
    }

    if (!positive_integer.test($('#inputMaintenanceFee').val())) {
        return swalWarning("잘못된 관리비입니다.", "양의 정수를 입력하세요.", '#inputMaintenanceFee');
    } else if (selectedMaintenanceFee == 2 && $('#inputMaintenanceFee').val() == "") {
        return swalWarning("", "관리비를 입력하세요.", '#inputMaintenanceFee');
    }

    if ($('#inputTitle').val() == "") {
        return swalWarning("", "제목을 입력하세요.", '#inputTitle');
    }
    if (defend($('#inputTitle').val()).length > 150) {
        return swalWarning("", "제목은 50자를 넘을 수 없습니다.", '#inputTitle');
    }
    if ($('#inputContent').val() == "") {
        return swalWarning("", "상세 설명을 입력하세요.", '#inputContent');
    }

    let hash1 = defend($('#inputHashTag-1').val());
    let hash2 = defend($('#inputHashTag-2').val());
    let hash3 = defend($('#inputHashTag-3').val());

    if (hash1.includes('#') || hash2.includes('#') || hash3.includes('#')) {
        return swalWarning("해시태그를 확인하세요.", "#은 사용할 수 없습니다.", '#inputHashTag-1');
    }
    if (hash1.length > 30 || hash2.length > 30 || hash3.length > 30) {
        return swalWarning("", "해시태그는 10자를 넘을 수 없습니다.", '#inputHashTag-1');
    }

    if ($('.addImage').length < 2 || $('.addImage').length > 10) {
        return swalWarning("", "사진을 2~10장 선택해주세요", '.addImage');
    }

    pushHashTag();

    Swal.fire({
        title: "방 등록",
        text: "방을 등록하시겠습니까?",
        type: "question",
        showCancelButton: true,
        confirmButtonClass: 'btn-success',
        confirmButtonText: '확인',
        cancelButtonClass: 'btn-info',
        cancelButtonText: '취소'
    }).then(result => {
        if (result.value) {
            ps.keywordSearch($('#inputAddress').val(), placesSearchCB);
        }
    })
}

var swalWarning = function (title, content, selector) {
    Swal.fire({
        title: title,
        text: content,
        type: "warning",
        confirmButtonClass: 'btn-info',
        onAfterClose: () => {
            $(selector).focus();
        }
    });
    return false;
}

var swalFocus = function (title, content, type, selector) {
    Swal.fire({
        title: title,
        text: content,
        type: type,
        confirmButtonClass: 'btn-success',
        onAfterClose: () => {
            $(selector).focus();
        }
    });
}

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