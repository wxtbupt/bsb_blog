(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('GetHomePage', GetHomePage);

    GetHomePage.$inject = ['$resource'];

    function GetHomePage ($resource) {
        var service = $resource('api/ms-task/get-home-page', {}, {
            'get': { method: 'GET'}
        });
        return service;
    }
})();

