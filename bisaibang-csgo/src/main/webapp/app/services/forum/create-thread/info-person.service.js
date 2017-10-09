/**
 * Created by OlyLis on 16-8-1.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('PersonInfo', PersonInfo);

    PersonInfo.$inject = ['$resource'];

    function PersonInfo($resource) {
        var service = $resource('/api/bsb-person-infos', {}, {
            'query': { method: 'GET', isArray: true},
            'save': { method: 'POST' }
        });
        return service;
    }
})();
