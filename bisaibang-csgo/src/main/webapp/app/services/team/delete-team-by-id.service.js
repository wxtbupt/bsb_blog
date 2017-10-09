/**
 * Created by rhd on 2017/8/19.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('DeleteTeamById', DeleteTeamById);

    DeleteTeamById.$inject = ['$resource'];

    function DeleteTeamById($resource) {
        var service = $resource('api/ms-team/delete-team/team/:teamid', {}, {
            'save': { method: 'POST'}
        });
        return service;
    }
})();

