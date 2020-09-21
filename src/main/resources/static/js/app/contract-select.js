$(document).ready(function () {
    $('#btn-cancel').clickCancelButton();
})

$.fn.clickCancelButton = function () {
    $(this).on('click', function () {
        Swal.fire({
            title: "취소",
            text: "정말로 취소하시겠습니까?",
            type: "warning",
            showCancelButton: true,
            confirmButtonClass: 'btn-success',
            confirmButtonText: '확인',
            cancelButtonClass: 'btn-info',
            cancelButtonText: '취소'
        }).then(result => {
            if (result.value) {
                window.close();
            }
        })
    })
}