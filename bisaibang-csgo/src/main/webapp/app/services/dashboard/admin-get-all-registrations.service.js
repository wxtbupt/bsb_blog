/**
 * Created by gsy on 2017/3/13.
 */
(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('AdminGetAllRegistrations', AdminGetAllRegistrations);

    AdminGetAllRegistrations.$inject = ['$resource'];

    function AdminGetAllRegistrations($resource) {
        var service = $resource('api/ms-registrations/get-all-registrations', {}, {
            'get': {method: 'GET', isArray: true}
        });

        return service;
    }
})();

