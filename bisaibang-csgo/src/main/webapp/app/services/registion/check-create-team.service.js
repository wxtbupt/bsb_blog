(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('CheckCreateTeam', CheckCreateTeam);

    CheckCreateTeam.$inject = ['$resource'];

    function CheckCreateTeam($resource) {
        var service = $resource('/api/ms-team/check-create-team', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();
