let slideMenuFlag = false;
const selectedColor = 'rgb(255, 160, 164)';
let roomTypeList = [];

$(window).resize(function () {
    if ($('#slideMenu').val() === "<") {
        if ($(window).width() < 1400) {
            $('.sub').css('width', 260);
            $('#map').css('width', "calc(100% - 265px)");
        }
    } else {
        if ($(window).width() < 1400) {
            $('.sub').css('width', 520);
            $('#map').css('width', "calc(100% - 525px)");
        }
    }
});

$(document).ready(function () {
    $('#mapInputBox').keypress(function (e) {
        if (e.which === 13) {
            searchPlaces();
        }
    })

    $("#slideMenu").showSlideMenu();

    $.clickEvent();

    $("#btn-filter").on('click', function () {
        if ($('.filterWrap').css('display') == 'none') {
            $('.filterWrap').css('z-index', '10');
            $('.filterWrap').css('display', 'initial');
        } else {
            $('.filterWrap').css('z-index', 'auto');
            $('.filterWrap').css('display', 'none');
        }
    });

    $('#btn-confirm').on('click', function () {
        $('.filterWrap').css('z-index', 'auto');
        $('.filterWrap').css('display', 'none');
        searchRoomFromMap();
    });

    $('#btn-option').on('click', function () {
        if ($('.wrapOption').css('display') == 'none') {
            $('.wrapOption').css('display', 'inline-block');
        } else {
            $('.wrapOption').css('display', 'none');
        }
    });

    $('#btn-apply').on('click', function () {
        $('.wrapOption').css('display', 'none');
    });

    $('#btn-default').on('click', function () {
        $(".optionCheckbox").each(function () {
            this.checked = false;
        });
    });
});

$.clickEvent = function () {
    $('#apartment').setRoomType();
    $('#villa').setRoomType();
    $('#tworoom').setRoomType();
    $('#oneroom').setRoomType();
    $('#officetel').setRoomType();
    let bt = $('.room-type');
    for(let i = 0; i < bt.length; i++){
        roomTypeList.push($('.room-type').eq(i).val());
    }
}

$.fn.setRoomType = function () {
    $(this).on('click', function () {
        if ($(this).css('background-color') === selectedColor) {
            $(this).css('background-color', 'white');
            $(this).css('color', 'black');
            for(let i = 0 ; i < roomTypeList.length; i++){
                if(roomTypeList[i] === $(this).val())
                    roomTypeList.splice(i,1);
            }
        } else {
            $(this).css('background-color', selectedColor);
            $(this).css('color', 'white');
            roomTypeList.push($(this).val());
        }
    })
}

var getAddress = function () {
    new daum.Postcode({
        oncomplete: function (data) {
            $("#tbAddress").attr("value", data.roadAddress);
        }
    }).open();
};

$.fn.showSlideMenu = function () {
    $(this).click(function () {
        if (slideMenuFlag == false) {
            $("#slideMenu").val('>');
            if ($(window).width() < 1400) {
                $('.sub').css('width', 520);
                $('#map').css('width', "calc(100% - 525px)");
            } else {
                $("#map").css('width', '60%');
                $(".sub").css('width', '40%');
            }
            $("#itemsList").css('width', '98%');
            $('.item').css('width', '45%');
            slideMenuFlag = true;

        } else {
            $("#slideMenu").val('<');
            if ($(window).width() < 1400) {
                $('.sub').css('width', 260);
                $('#map').css('width', "calc(100% - 265px)");
            } else {
                $("#map").css('width', '80%');
                $(".sub").css('width', '20%');
            }
            $("#itemsList").css('width', '100%');
            $('.item').css('width', '96%');
            slideMenuFlag = false;
        }
    });
}
