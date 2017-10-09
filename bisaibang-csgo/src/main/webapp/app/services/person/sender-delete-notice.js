/**
 * Created by gsy on 2017/6/11.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('SenderDeleteNotice', SenderDeleteNotice);

    SenderDeleteNotice.$inject = ['$resource'];

    function SenderDeleteNotice($resource) {
        var service = $resource('api/ms-notice/delete-notice/notice/:noticeid', {}, {
            'post': { method: 'POST', isArray: false}
        });
        return service;
    }
})();
