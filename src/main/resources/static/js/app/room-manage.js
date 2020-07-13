var room_manage = {
    init: function () {
        let _this = this;

        $('#btnManageRoom').css('border-bottom', '7px solid #93B5F2');

        _this.setButtonPostStatus();
        _this.setImage();

    }, setButtonPostStatus: function () {
        let len = $('.tbList').length;

        for (let i = 1; i <= len; i++) {
            let postStatus = $('#tdPostStatus-' + i).text();

            switch (postStatus) {
                case "게시중":
                    $('#tdButton-' + i + '>#btn-post').text('게시종료');
                    break;
                case "게시종료" :
                    $('#tdButton-' + i + '>#btn-post').text('재게시');
                    break;
                case "게시금지" :
                    $('#tdPostStatus-' + i).css('color', '#ff545b');
                case "거래완료" :
                    room_manage.setButtonDisabled(i);
                    break;
            }
        }
    }, setImage: function () {
        let len = $('.tbList').length;
        for (let i = 1; i <= len; i++) {
            let attrV = $('#uploadValue-' + i).attr('value');
            let value = attrV.split(',');
            let salesPostId = value[0];
            let fileName = value[1];
            let src = '/files/download?id=' + encodeURI(salesPostId) + '&image=' + encodeURI(fileName);
            $('#preview-' + i).attr('src', src);
        }
    }, setButtonDisabled: function (listNum) {
        $('#tdButton-' + listNum + '>.btn-modify').prop('disabled', 'disabled');
        $('#tdButton-' + listNum + '>#btn-post').prop('disabled', 'disabled');
        $('#tdButton-' + listNum + '>#btn-deal').prop('disabled', 'disabled');
        $('#tdButton-' + listNum + '>button').removeAttr('class');
        $('#tdButton-' + listNum + '>button').css('cursor', 'default');
        $('#tdButton-' + listNum + '>button').eq(1).attr('class', 'button btn-delete');
        $('#tdButton-' + listNum + '>button').eq(1).removeAttr('style');
    }, deleteRoom : function (roomId, callback) {
        $.ajax({
            type: 'DELETE',
            url: '/api/rooms/' + roomId,
            contentType: 'application/json; charset=utf-8'
        }).then(function (data, status) {
            if (status === 'success') {
                callback(data);
            } else {
                Swal.fire('연결 실패', '잠시후 다시 시도해주세요.', 'error');
            }
        });
    }
}

room_manage.init();

$(document).ready(function () {
    $('.btn-modify').clickModifyBtn();
    $('.btn-delete').clickDeleteBtn();
    $('.btnPt').clickPostStatBtn();
});

$.fn.clickModifyBtn = function () {
    $(this).on('click', function () {
        let roomId = $(this).val();
        $('#delete' + roomId).attr('disabled', true);
        location.href = "/rooms/update/" + roomId;
    });
}
$.fn.clickDeleteBtn = function () {
    $(this).on('click', function () {
        let listNum = $(this).parent().attr('id').split('tdButtons-')[1];
        Swal.fire({
            title: "방 삭제",
            text: "이 방을 정말로 삭제하시겠습니까?",
            type: "warning",
            showCancelButton: true,
            confirmButtonClass: 'btn-success',
            confirmButtonText: '확인',
            cancelButtonClass: 'btn-info',
            cancelButtonText: '취소'
        }).then(result => {
            if (result.value) {
                let roomId = $(this).val();
                $('#btn-delete-' + listNum).attr('disabled', true);
                $('#btn-modify-' + listNum).attr('disabled', true);

                room_manage.deleteRoom(roomId, function (data) {
                    switch (data.status) {
                        case "SUCCESS" :
                            Swal.fire('삭제 성공', data.message, 'success')
                                .then(function () {
                                    location.reload();
                                });
                            break;
                        case "FAIL" :
                            Swal.fire(data.message, data.subMessage, 'error');
                            break;
                    }
                    $('#btn-delete-' + listNum).attr('disabled', false);
                    $('#btn-modify-' + listNum).attr('disabled', false);
                });
            }
        });
    })
}
$.fn.clickPostStatBtn = function(){
    $(this).on('click', function () {
        let btnText = $(this).text();
        let swalTitle = "";
        let swalText = "";
        let swalType = "";
        switch (btnText) {
            case "게시종료" :
                swalTitle = "게시상태 변경";
                swalText = "게시종료 상태로 바꾸시겠습니까?";
                swalType = "question"; break;
            case "재게시" :
                swalTitle = "게시상태 변경";
                swalText = "이 방을 다시 게시하시겠습니까?";
                swalType = "question"; break;
            case "거래완료" :
                swalTitle = '거래완료';
                swalText = "거래완료 상태로 바꾸면 다시 게시할 수 없습니다.";
                swalType = "warning"; break;
        }
        Swal.fire({
            title: swalTitle,
            text: swalText,
            type: swalType,
            showCancelButton: true,
            confirmButtonClass: 'btn-success',
            confirmButtonText: '확인',
            cancelButtonClass: 'btn-info',
            cancelButtonText: '취소'
        }).then(result => {
            if (result.value) {
                $(this).changePostType(function(data, postType){
                    if (data.status === "FAIL") {
                        Swal.fire(data.message, data.subMessage, 'error');
                    } else {
                        Swal.fire(data.message, postType+' 상태로 변경되었습니다.', 'success');
                    }
                });
            }
        });
    })
}
$.fn.changePostType = function (callback) {
    let listNum = $(this).parent().attr('id').split('tdButton-')[1];
    let btnText = $(this).text();
    let id = $(this).val();
    let postType = (btnText === "재게시") ? "게시중" : btnText;

    $.ajax({
        url: '/api/rooms/' + id + '/post-status',
        type: 'PATCH',
        dataType: 'json',
        contentType: 'application/text; charset=utf-8',
        data: JSON.stringify(postType)
    }).then(function (data, status) {
        if (status === 'success' && data.status === "SUCCESS") {
            switch (data.subMessage) {
                case "POSTING" :
                    $('#tdPostStatus-' + listNum).text('게시중');
                    break;
                case "POSTING_END" :
                    $('#tdPostStatus-' + listNum).text('게시종료');
                    break;
                case "DEAL_COMPLETED" :
                    $('#tdPostStatus-' + listNum).text('거래완료');
                    break;
                case "POSTING_BAN" :
                    $('#tdPostStatus-' + listNum).text('게시금지');
                    break;
            }
            room_manage.setButtonPostStatus(listNum);
            callback(data, $('#tdPostStatus-' + listNum).text());
        } else {
            callback(false);
            return;
        }
    });
}