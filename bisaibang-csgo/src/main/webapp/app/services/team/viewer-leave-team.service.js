/**
 * Created by arslan on 10/31/16.
 */
(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('ViewerLeaveTeam', ViewerLeaveTeam);

    ViewerLeaveTeam.$inject = ['$resource'];

    function ViewerLeaveTeam($resource) {
        var service = $resource('api/ms-team/viewer/:teamid/leave/myself', {}, {
            'get': {method: 'GET'}
        });
        return service;
    }
})();
