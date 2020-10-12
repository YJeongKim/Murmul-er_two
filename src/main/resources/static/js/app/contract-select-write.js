$(document).ready(function () {
    $('.title').html('<h3>계약서 작성</h3><br>계약할 방을 선택하세요.');

    $('.selectRoom').click(function () {
        let contractUser = $('.roomList').attr('value');
        let roomId = $(this).val();
        location.href = "/contracts/write?contractor=" + contractUser + "&roomId=" + roomId;
    })
});