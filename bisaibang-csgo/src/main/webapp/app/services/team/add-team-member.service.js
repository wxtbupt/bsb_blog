/**
 * Created by rhd on 2017/8/19.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('AddTeamMember', AddTeamMember);

    AddTeamMember.$inject = ['$resource'];

    function AddTeamMember($resource) {
        var service = $resource('api/ms-team/captain/change-state', {}, {
            'save': { method: 'POST'}
        });
        return service;
    }
})();

