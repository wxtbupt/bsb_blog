(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('PersonRegister', PersonRegister);

    PersonRegister.$inject = ['$resource'];

    function PersonRegister($resource) {
        var service = $resource('/api/ms-city/enter/city/:cityid', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();
