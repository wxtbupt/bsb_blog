/**
 * Created by rhd on 2017/8/19.
 */
(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('getRegistrationsInfo', getRegistrationsInfo);

    getRegistrationsInfo.$inject = ['$resource'];

    function getRegistrationsInfo($resource) {
        var service = $resource('api/ms-registrations/check/registration', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();

