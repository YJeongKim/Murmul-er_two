$(document).ready(function () {
    $('.title').html('<h3>계약서 등록</h3><br>계약서를 등록할 방을 선택하세요.');

    $('.selectRoom').click(function () {
        let contractUser = $('.roomList').attr('value');
        let roomId = $(this).val();
        location.href = "/contracts/register?contractor=" + contractUser + "&roomId=" + roomId;
    })
});