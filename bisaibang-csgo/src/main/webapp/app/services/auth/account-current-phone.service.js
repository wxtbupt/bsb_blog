(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('AccountCurrentPhone', AccountCurrentPhone);

    AccountCurrentPhone.$inject = ['$resource'];

    function AccountCurrentPhone ($resource) {
        var service = $resource('api/account/current-phone', {}, {
            'get': { method: 'GET'}
        });

        return service;
    }
})();
