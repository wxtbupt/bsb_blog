/**
 * Created by rhd on 2017/8/19.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('TeamRegistrations', TeamRegistrations);

    TeamRegistrations.$inject = ['$resource'];

    function TeamRegistrations($resource) {
        var service = $resource('/api/ms-registrations/create/:teamid', {}, {
            'save': { method: 'POST'}
        });
        return service;
    }
})();

