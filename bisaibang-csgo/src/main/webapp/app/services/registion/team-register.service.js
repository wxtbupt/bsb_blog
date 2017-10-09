(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('TeamRegister', TeamRegister);

    TeamRegister.$inject = ['$resource'];

    function TeamRegister($resource) {
        var service = $resource('/api/ms-team/create', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();
