$(document).ready(function () {
    $('#btnSaveRoom').on('click', function () {
        location.href = "/rooms/register";
    });
    $('#btnManageRoom').on('click', function () {
        location.href = "/rooms";
    });
});