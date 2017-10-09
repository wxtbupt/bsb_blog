/**
 * Created by liujinduo on 2016/10/26.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('TeamGetAllPlayer', TeamGetAllPlayer);

    TeamGetAllPlayer.$inject = ['$resource'];

    function TeamGetAllPlayer($resource) {
        var service = $resource('/api/bsb-team/viewer/get-all/:id', {}, {
            'query': { method: 'GET', isArray:true}
        });
        return service;
    }
})();
