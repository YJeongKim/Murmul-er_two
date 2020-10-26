var navbar = {
    isLogin : 0,
    init : function () {
        let _this = this;

        _this.isLogin = $(".navbar-buttons").find('ul').length;

        $('#nav-search').on('click', function () {
            _this.moveBeforeLogin('/salesposts');
        });
        $('#nav-register').on('click', function () {
            _this.moveAfterLogin('/rooms/register');
        });
        $('#nav-manage').on('click', function () {
            _this.moveAfterLogin('/rooms');
        });
        $('#nav-contract-write').on('click', function () {
            _this.moveAfterLogin('//contracts/select?contractor=2&type=write');
        });
        $('#nav-contract-register').on('click', function () {
            _this.moveAfterLogin('//contracts/select?contractor=2&type=register');
        });
    }, moveAfterLogin : function (location) {
        if(this.isLogin === 1) {
            window.location.href = location;
        } else {
            swalAlert('warning', '로그인하면 이용할 수 있습니다.');
        }
    }, moveBeforeLogin : function (location) {
        window.location.href = location;
    }
}

navbar.init();

$(document).ready(function () {
    $('#logout').on('click', function () {
        swalAlert('info', '로그아웃을 진행합니다.');
        location.href = '/logout';
    });
    $('#login').on('click', function () {
        swalAlert('info', '로그인을 진행합니다.');
        location.href = '/oauth2/authorization/google';
    });
});

var swalAlert = function (type, title) {
    Swal.fire({
        position: 'center',
        type: type,
        title: title,
        showConfirmButton: false,
        timer: 1500
    });
}