

var pageInit = function () {
    //셀렉트 박스 badge 효과
    $('.select2').select2();


    //내용상세
    ClassicEditor
        .create(document.querySelector('#contentDetail'), {
            toolbar: {
                items: [
                    'heading',
                    '|',
                    'fontBackgroundColor',
                    'fontColor',
                    'fontSize',
                    'fontFamily',
                    'bold',
                    'italic',
                    '|',
                    'insertTable',
                    'removeFormat',
                    'strikethrough',
                    'underline',
                    'blockQuote',
                    '|',
                    'bulletedList',
                    'numberedList',
                    'indent',
                    'outdent',
                    '|',
                    'imageUpload',
                    'mediaEmbed',
                    'link',
                    '|',
                    'undo',
                    'redo',
                    'code',
                    '|'
                ]
            },
            language: 'ko',
            image: {
                toolbar: [
                    'imageTextAlternative',
                    'imageStyle:full',
                    'imageStyle:side'
                ]
            },
            table: {
                contentToolbar: [
                    'tableColumn',
                    'tableRow',
                    'mergeTableCells'
                ]
            },
            licenseKey: '',

        })
        .then(editor => {
            window.contentDetail = editor;
            // After mounting the application change the height
            editor.editing.view.change(writer=>{
                writer.setStyle('height', '250px', editor.editing.view.document.getRoot());
            });
        })
        .catch(error => {
            console.log(error);
        });

    //임시키
    $("#groupKey").val(COMMON.tempKey());

    //영상 상세 사이즈 변환
    $(".table-responsive .size").each(function (idx, obj) {
        $(obj).text(COMMON.util.formatFileSize(parseInt($(obj).text())));
    });
}

/*
강의정보 등록
*/
var courseRegAndMod = function ($form) {

    var param = Medea.formobject($form);
    param.contentDetail =   window.contentDetail.getData();
    param.badge =  $.map($('#badge').select2('data'), function(item) { return item.text; }).join(',');

    console.log(param);
    if (confirm("저장 하시겠습니까?")) {
        var params = {
            url: "/api/course/insertUpdate",
            type: "POST",
            contentType :"application/json",
            data:  JSON.stringify(param),
            success: function (data) {
                console.log(data)
                if (data.code != "200") {
                    alert(data.message);
                } else {
                    alert("정상처리 되었습니다.");
                    location.reload();
                }
            }
        };

        COMMON.ajaxSyncJson(params);
    }
}

