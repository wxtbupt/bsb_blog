/**
 * Created by rhd on 2017/8/19.
 */
(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('DeleteTeamApply', DeleteTeamApply);

    DeleteTeamApply.$inject = ['$resource'];

    function DeleteTeamApply($resource) {
        var service = $resource('api/ms-team/delete-apply/team/:teamid', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();

