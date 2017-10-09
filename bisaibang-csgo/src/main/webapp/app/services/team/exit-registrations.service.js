/**
 * Created by rhd on 2017/8/19.
 */
(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('ExitRegistrations', ExitRegistrations);

    ExitRegistrations.$inject = ['$resource'];

    function ExitRegistrations($resource) {
        var service = $resource('api/ms-registrations/leave/team/:teamid', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();

