(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('ChangeteamName', ChangeteamName);

    ChangeteamName.$inject = ['$resource'];

    function ChangeteamName($resource) {
        var service = $resource('api/ms-team/change/team-name/team/:teamid', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();
