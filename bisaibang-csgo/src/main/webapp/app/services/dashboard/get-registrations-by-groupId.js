/**
 * Created by gsy on 2017/4/19.
 */

(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('GetRegistrationGoupId', GetRegistrationGoupId);

    GetRegistrationGoupId.$inject = ['$resource'];

    function GetRegistrationGoupId ($resource) {
        var service = $resource('api/registrations/get-registrations/groupId/:id', {}, {
            'get': { method: 'GET', isArray: true,
                interceptor: {
                    response: function(response) {
                        // expose response
                        return response;
                    }
                }
            }
        });

        return service;
    }
})();
