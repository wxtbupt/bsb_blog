(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('CheckBattleId', CheckBattleId);

    CheckBattleId.$inject = ['$resource'];

    function CheckBattleId($resource) {
        var service = $resource('/api/registrations/check/battle-id/:battleid', {}, {
            'get': { method: 'GET', isArray:false  }
        });
        return service;
    }
})();
