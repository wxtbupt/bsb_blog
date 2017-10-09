(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('TeamAddNoPlayer', TeamAddNoPlayer);

    TeamAddNoPlayer.$inject = ['$resource'];

    function TeamAddNoPlayer($resource) {
        var service = $resource('api/bsb-team/viewer/enter/:teamid/:playername', {}, {
            'get': { method: 'GET'}
        });
        return service;
    }
})();
