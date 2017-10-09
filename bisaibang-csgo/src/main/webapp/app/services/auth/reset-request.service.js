/**
 * Created by gsy on 2017/4/24.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('ResetRequest', ResetRequest);

    ResetRequest.$inject = ['$resource'];

    function ResetRequest($resource) {
        var service = $resource('api/forget/resetPassword', {}, {
            'save': { method: 'POST' }
        });
        return service;
    }
})();

