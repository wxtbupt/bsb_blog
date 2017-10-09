(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('CheckUser', CheckUser);

    CheckUser.$inject = ['$resource'];

    function CheckUser($resource) {
        var service = $resource('/api/ms-notice/check-user-reliable', {}, {
            'save': { method: 'POST' }
        });
        return service;
    }
})();
