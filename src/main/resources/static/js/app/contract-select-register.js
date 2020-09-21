$(document).ready(function () {
    $('.selectRoom').click(function () {
        let contractUser = $('#roomList').attr('value');
        let roomId = $(this).val();
        location.href = "/contracts/register?contractor=" + contractUser + "&roomId=" + roomId;
    })
});