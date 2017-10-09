(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('TeamUpdate', TeamUpdate);

    TeamUpdate.$inject = ['$resource'];

    function TeamUpdate($resource) {
        var service = $resource('api/ms-team/captain/update/:teamid', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();
