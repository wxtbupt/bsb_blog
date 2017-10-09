/**
 * 用于用户自己解绑id
 * Created by qibo on 2017/8/21.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('DeleteBattleId', DeleteBattleId);

    DeleteBattleId.$inject = ['$resource'];

    function DeleteBattleId($resource) {
        var service = $resource('api/ms-registrations/delete/battle-id', {}, {
            'save': { method: 'POST'}
        });
        return service;
    }
})();
