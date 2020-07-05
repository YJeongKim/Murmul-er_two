var room = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
        $('#btn-cancel').on('click', function () {
            _this.cancel();
        });
        $('#btnImg').on('click', function () {
            $('#uploadImages').trigger('click');
        });
    },
    save : function () {
        var data = {
        };
        $.ajax({
            type: 'POST',
            url: '/api/rooms',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
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

room.init();