(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('ViewerGetMyTeam', ViewerGetMyTeam);

    ViewerGetMyTeam.$inject = ['$resource'];

    function ViewerGetMyTeam ($resource) {
        var service = $resource('api/ms-team/viewer/get-my-team', {}, {
            'get': { method: 'GET'}
        });

        return service;
    }
})();
