(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('SenderGetAllNotice', SenderGetAllNotice);

    SenderGetAllNotice.$inject = ['$resource'];

    function SenderGetAllNotice($resource) {
        var service = $resource('/api/ms-notice/sender/get-all-notice', {}, {
            'get': { method: 'GET',isArray:true}
        });
        return service;
    }
})();
