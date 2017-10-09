
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('GetPlayerByUserGame', GetPlayerByUserGame);

    GetPlayerByUserGame.$inject = ['$resource'];

    function GetPlayerByUserGame($resource) {
        var service = $resource('api/bsb-player/admin/player/game/:gameid', {}, {
            'get': { method: 'GET'}
        });
        return service;
    }
})();

