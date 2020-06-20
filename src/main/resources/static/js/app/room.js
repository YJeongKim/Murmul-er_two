let formData = new FormData();
let x = 'x';
let cnt = 0;

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

    }
}

room.init();

function readURL(input) {
    let td = $('#tdImg');
    let loopCnt = 0;
    if (input.files && input.files[0]) {
        for(let i=1; i<=input.files.length; i++){
            loopCnt++;
            let index = i+cnt;
            let imgName = input.files[i-1].name;
            let fileExt = imgName.slice(imgName.indexOf(".") + 1).toLowerCase(); // 파일 확장자를 잘라내고, 비교를 위해 소문자로

            if(fileExt != "jpg" &&  fileExt != "jpeg" && fileExt != "png" &&  fileExt != "bmp"){
                // Swal.fire('', '파일 첨부는 이미지 파일(jpg, jpeg, png, bmp)만 등록이 가능합니다,', 'warning');
                return;
            }

            var reader = new FileReader();
            reader.onload = function (e) {
                var img = $(''
                    +'<div class="img-wrap" id=img-wrap'+ index +' name="'+imgName+'">'
                    +'<span class="close" id=close'+ index +'>' + x + '</span>'
                    +'<img class="addImage" data-id=rmimg'+ index +' src='+ e.target.result +' name="addImage"/>'
                    +'</div>'
                );
                td.append(img);
                $('#close'+index).click(function () {
                    let rmDiv = $(this).parent()[0];
                    let num;
                    let imgDiv = $('.img-wrap');
                    for(let i = 0; i < imgDiv.length; i++) {
                        if (imgDiv[i] == rmDiv) {
                            num = i;
                        }
                    }
                    var fileArray = formData.getAll("uploadFile");
                    fileArray.splice(num, 1);
                    formData.delete("uploadFile");
                    for(let i = 0;i<fileArray.length; i++){
                        formData.append("uploadFile", fileArray[i]);
                    }
                    rmDiv.remove();

                });
            }
            reader.readAsDataURL(input.files[i-1]);
        }

        var inputFile = $("input[name='uploadFile']");
        var files = inputFile[0].files;
        for(var i = 0; i < files.length; i++){
            formData.append("uploadFile", files[i]);
        }
    }
    cnt += loopCnt;
}