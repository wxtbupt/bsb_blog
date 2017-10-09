(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('ReceiverGetAllNotice', ReceiverGetAllNotice);

    ReceiverGetAllNotice.$inject = ['$resource'];

    function ReceiverGetAllNotice($resource) {
        var service = $resource('/api/ms-notice/receiver/get-all-notice', {}, {
            'get': { method: 'GET',isArray:true}
        });
        return service;
    }
})();
