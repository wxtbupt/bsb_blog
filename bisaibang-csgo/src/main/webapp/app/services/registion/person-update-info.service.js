(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('PersonUpdateInfo', PersonUpdateInfo);

    PersonUpdateInfo.$inject = ['$resource'];

    function PersonUpdateInfo($resource) {
        var service = $resource('api/ms-person-info/update', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();
