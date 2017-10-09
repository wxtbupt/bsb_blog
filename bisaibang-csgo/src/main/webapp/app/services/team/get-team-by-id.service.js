/**
 * Created by arslan on 10/31/16.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('GetTeamById', GetTeamById);

    GetTeamById.$inject = ['$resource'];

    function GetTeamById($resource) {
        var service = $resource('api/bsb-team/viewer/get-team/:id', {}, {
            'get': { method: 'GET'}
        });
        return service;
    }
})();
