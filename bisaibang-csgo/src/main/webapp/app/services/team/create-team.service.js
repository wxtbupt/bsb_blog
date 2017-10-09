/**
 * Created by rhd on 2017/8/19.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('CreateTeam', CreateTeam);

    CreateTeam.$inject = ['$resource'];

    function CreateTeam($resource) {
        var service = $resource('api/ms-team/create', {}, {
            'save': { method: 'POST'}
        });
        return service;
    }
})();
