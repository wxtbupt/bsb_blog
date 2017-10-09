(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('CheckCityMember', CheckCityMember);

    CheckCityMember.$inject = ['$resource'];

    function CheckCityMember($resource) {
        var service = $resource('api/ms-city/check-city-member', {}, {
            'save': {method: 'POST'}
        });
        return service;
    }
})();
