/**
 * Created by gsy on 2017/3/30.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('RemoveTeam', RemoveTeam);

    RemoveTeam.$inject = ['$resource'];

    function RemoveTeam ($resource) {
        var service = $resource('api/registrations/delete/registration-battle-tag', {}, {
            'save': { method: 'POST'}
        });

        return service;
    }
})();
