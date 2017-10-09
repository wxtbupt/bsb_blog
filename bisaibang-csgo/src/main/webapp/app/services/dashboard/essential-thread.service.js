
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('EssentialThread', EssentialThread);

    EssentialThread.$inject = ['$resource'];

    function EssentialThread($resource) {
        var service = $resource('api/ms-singlethread/essential/thread/:threadid', {}, {
            'save': { method: 'POST' }
        });
        return service;
    }
})();
