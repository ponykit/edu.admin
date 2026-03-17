$(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    'use strict';
    var url = "/common/file/upload",
        uploadButton = $('<button/>')
            .on('click', function () {
                var $this = $(this),
                    data = $this.data();
                $this
                    .off('click')
                    .text('Abort')
                    .on('click', function () {
                        $this.remove();
                        data.abort();
                    });
                data.submit().always(function () {
                    $this.remove();
                });
            });

    $.ajaxSetup({
        headers: {
            "X-CSRF-TOKEN": token
        }
    });

    $('#courseFile').fileupload({
        url: url,
        method: 'POST',
        dataType: 'json',
        autoUpload: false,
        multipart: true,
        disableImageResize: /Android(?!.*Chrome)|Opera/
            .test(window.navigator.userAgent),
        previewMaxWidth: 200,
        previewMaxHeight: 100,
        previewCrop: true,
        acceptFileTypes:  /(\.|\/)(mp3|mkv|mp4)$/i,
        //showDelete: true,
        uploadTemplate: function (o) {
            var inputSeq = $(".template-upload").length;
            var rows = $();
            $.each(o.files, function (index, file) {
                console.log(file)
                var row = $('<tr class="template-upload fade">' +
                    '<td><input class="form-control" type="text"  id="sort" name="courseDetail[' + inputSeq + '].sort" /></td>' +
                    '<td><span class="preview"></span></td>' +
                    '<td><input multiple class="form-control" type="text" id="unitTitle" name="courseDetail[' + inputSeq + '].unitTitle" />' +
                    '<div class="error"></div>' +
                    '</td>' +
                    '<td><span class="size"></span></td>' +
                    '<td>' +
                    ' <button class="btn btn-warning cancel"> <i class="glyphicon glyphicon-ban-circle"></i> <span>Cancel</span> </button>' + // : '') +
                    '</td>' +
                    '</tr>');
                row.find('#sort').val(inputSeq);
                row.find('#unitTitle').val(file.name);
                row.find('.size').text(o.formatFileSize(file.size));
                if (file.error) {
                    row.find('.error').text(file.error);
                }

                rows = rows.add(row);
            });
            return rows;
        },
        downloadTemplate: function (o) {
            var inputSeq = $(".template-upload").length;
            var rows = $();
            $.each(o.files, function (index, file) {
                console.log(file);
                var row = $('<tr class="template-download fade">' +
                    '<td><input class="form-control" type="text"  id="sort" name="courseDetail[' + inputSeq + '].sort" /></td>' +
                    '<td><span class="preview"></span></td>' +
                    '<td><input multiple class="form-control" type="text" id="unitTitle" name="courseDetail[' + inputSeq + '].unitTitle" />' +
                    (file.error ? '<div class="error"></div>' : '') +
                    '</td>' +
                    '<td><span class="size"></span></td>' +
                    '<td><input  type="hidden"  id="fileName" name="courseDetail[' + inputSeq + '].fileName" />' +
                    '    <input  type="hidden"  id="extension" name="courseDetail[' + inputSeq + '].extension" />' +
                    '    <input  type="hidden"  id="fileSeq"  name="courseDetail[' + inputSeq + '].fileSeq" />' +
                    '    <button class="btn btn-danger delete"> <i class="glyphicon glyphicon-trash"></i><span>Delete</span></button></td>' +
                    '</tr>');
                row.find('#fileSeq').val(file.fileSeq);
                row.find('#sort').val(inputSeq);
                row.find('.size').text(o.formatFileSize(file.fileSize));
                row.find('#unitTitle').val(file.fileName);
                row.find('#fileName').val(file.fileName);
                row.find('#extension').val(file.extension);
            if (file.error) {
                row.find('.error').text(file.error);
            } else {
                if (file.mimeType.indexOf('video') > -1) {
                        row.find('.preview').append(
                            $('<video>').prop('src', file.fileUrl).attr("controls", "controls")
                        );
                    } else if (file.mimeType.indexOf('image') > -1) {
                        row.find('.preview').append(
                            $('<img>').prop('src', file.fileUrl).css({"width": "200", "height": "100"})
                        );
                    }
                }

                row.find('.delete').on('click', function () {
                    fnFileDelete($(this));
                });
                rows = rows.add(row);
            });
            return rows;
        }
    })
        .on('fileuploadadd', function (e, data) {
            data.context = $('<div/>').appendTo('#register-files');
            $.each(data.files, function (index, file) {
                var node = $('<p/>')
                    .append($('<span/>').text(file.name));
                if (!index) {
                    node
                        .append('<br>')
                        .append(uploadButton.clone(true).data(data));
                }
                node.appendTo(data.context);
                $("#allUpload").on('allUpload', function (e) {
                    console.log(data)
                    data.submit();
                });
            });
        })
        .on('fileuploadprocessalways', function (e, data) {
            var index = data.index,
                file = data.files[index],
                node = $(data.context.children()[index]);
            if (file.preview) {
                node
                    .prepend('<br>')
                    .prepend(file.preview);
            }
            if (file.error) {
                node
                    .append('<br>')
                    .append($('<span class="text-danger"/>').text(file.error));
            }
            if (index + 1 === data.files.length) {
                data.context.find('button')
                    .prop('disabled', !!data.files.error);
            }
        })
        .on('fileuploadprogressall', function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('.progress-bar').css(
                'width',
                progress + '%'
            );
            console.log("fileuploadprogressall : ", data.loaded, data.total, data.bitrate);
        })
        .on('fileuploaddone', function (e, data) {
            $.each(data.result.files, function (index, file) {
                if (file.url) {
                    var link = $('<a>')
                        .attr('target', '_blank')
                        .prop('href', file.url);
                    $(data.context.children()[index])
                        .wrap(link);
                } else if (file.error) {
                    var error = $('<span class="text-danger"/>').text(file.error);
                    $(data.context.children()[index])
                        .append('<br>')
                        .append(error);
                }
            });
        })
        .on('fileuploadfail', function (e, data) {
            $.each(data.files, function (index, file) {
                var error = $('<span class="text-danger"/>').text('File upload failed.');
                $(data.context.children()[index])
                    .append('<br>')
                    .append(error);
            });
        })
        .prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');

    $("#allUpload").on('click', function (e) {
        e.preventDefault();
        $("#allUpload").trigger("allUpload");
    });


    var fnFileDelete = function (obj) {
        var delfileVo = $(obj).closest("tr");
        var param = Medea.formobject(delfileVo);
        var params = {
            url: "/common/file/delete",
            type: "POST",
            data:  JSON.stringify(param),
            success: function (data) {
                if (data.code != "200") {
                    alert(data.message);
                } else {
                    alert("정상적으로 삭제 되었습니다.");
                }
            }
        };

        COMMON.ajaxSyncJson(params)
    }

});