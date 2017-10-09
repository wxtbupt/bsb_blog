/**
 * Created by gsy on 2017/5/6.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('GetMyCard', GetMyCard);

    GetMyCard.$inject = ['$resource'];

    function GetMyCard($resource) {
        var service = $resource('/api/registrations/get-my/card', {}, {
            'query': { method: 'POST',isArray:true}
        });
        return service;
    }
})();
