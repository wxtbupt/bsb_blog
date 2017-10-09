/**
 * Created by gsy on 2017/5/6.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('DeleteCard', DeleteCard);

    DeleteCard.$inject = ['$resource'];

    function DeleteCard($resource) {
        var service = $resource('/api/registrations/admin/delete/card', {}, {
            'save': { method: 'POST'}
        });
        return service;
    }
})();
