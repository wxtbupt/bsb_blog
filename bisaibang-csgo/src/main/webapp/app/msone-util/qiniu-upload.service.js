/**
 * Created by arslan on 8/23/16.
 */
(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('QiniuUploadService', QiniuUploadService);

    QiniuUploadService.$inject = ['Upload', 'QiniuToken'];

    function QiniuUploadService(Upload, QiniuToken) {
        var service = {
            upload: upload
        };
        return service;
        /////////////////////ng-file-upload solution
        function upload(file,callback) {
            //console.log(file);
            QiniuToken.get({key: file.file.name}, function success(response) {
                Upload.upload({
                    url: 'https://upload-z1.qbox.me/',
                    data: {
                        key: file.file.name,
                        token: response.message,
                        file: file.blob
                    }
                }).then(function (resp) {
                    //console.log(resp);
                    callback(true,resp);
                }, function (resp) {
                    //console.log(resp);
                    callback(false,resp);
                }, function (evt) {
                });
            });
        }
        /////////////XMLHTTPRequest solution
        // function upload(file,callback) {
        //     QiniuToken.get({key: file.file.name}, function (response) {
        //         var xhr = new XMLHttpRequest();
        //         xhr.open('POST', 'https://upload-z1.qbox.me/', true);
        //         var formData;
        //         formData = new FormData();
        //         formData.append('key', file.file.name);
        //         formData.append('token', response.message);
        //         formData.append('file', file.blob);
        //         xhr.onreadystatechange = function (response) {
        //             if (xhr.readyState == 4 && xhr.status == 200 && xhr.responseText != "") {
        //                 var blkRet = JSON.parse(xhr.responseText);
        //                 callback(true,blkRet);
        //             } else if (xhr.status != 200 && xhr.responseText) {
        //                 var blkRet = JSON.parse(xhr.responseText);
        //                 callback(false,blkRet);
        //             }
        //         };
        //         xhr.send(formData);
        //     });
        // }
    }
})();
