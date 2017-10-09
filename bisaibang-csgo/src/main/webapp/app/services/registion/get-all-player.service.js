(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('GetAllPlayer', GetAllPlayer);

    GetAllPlayer.$inject = ['$resource'];

    function GetAllPlayer($resource) {
        var service = $resource('/api/ms-team/viewer/get-all/:teamid', {}, {
            'get': {method: 'GET',isArray:true}
        });
        return service;
    }
})();
