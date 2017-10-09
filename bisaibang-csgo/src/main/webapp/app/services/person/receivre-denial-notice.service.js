/**
 * Created by OlyLis on 2017/5/23.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('ReceiverDenialNotice', ReceiverDenialNotice);

    ReceiverDenialNotice.$inject = ['$resource'];

    function ReceiverDenialNotice($resource) {
        var service = $resource('api/ms-notice/denial-notice/notice/:noticeid', {}, {
            'post': { method: 'POST', isArray: false}
        });
        return service;
    }
})();
