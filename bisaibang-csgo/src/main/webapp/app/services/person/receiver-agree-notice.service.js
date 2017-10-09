/**
 * Created by OlyLis on 2017/5/23.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('ReceiverAgreeNotice', ReceiverAgreeNotice);

    ReceiverAgreeNotice.$inject = ['$resource'];

    function ReceiverAgreeNotice($resource) {
        var service = $resource('api/ms-notice/agree-notice/notice/:noticeid', {}, {
            'post': { method: 'POST', isArray: false}
        });
        return service;
    }
})();
