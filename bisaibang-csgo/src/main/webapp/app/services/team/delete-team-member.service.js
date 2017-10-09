/**
 * Created by rhd on 2017/8/19.
 */
(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('DeleteTeamMember', DeleteTeamMember);

    DeleteTeamMember.$inject = ['$resource'];

    function DeleteTeamMember($resource) {
        var service = $resource('api/ms-team/captain/:teamid/leave/:userid', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();

