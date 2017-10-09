/**
 * Created by gsy on 2017/5/6.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('CreateCard', CreateCard);

    CreateCard.$inject = ['$resource'];

    function CreateCard($resource) {
        var service = $resource('/api/registrations/admin/create/card', {}, {
            'save': { method: 'POST'}
        });
        return service;
    }
})();
