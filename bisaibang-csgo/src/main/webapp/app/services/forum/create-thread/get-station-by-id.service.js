/**
 * Created by lth on 2017/2/19.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('GetStationById', GetStationById);

    GetStationById.$inject = ['$resource'];

    function GetStationById($resource) {
        var service = $resource('api/bsb-station/viewer/get-station/station/:id', {}, {
            'get': { method: 'GET' }
        });
        return service;
    }
})();

