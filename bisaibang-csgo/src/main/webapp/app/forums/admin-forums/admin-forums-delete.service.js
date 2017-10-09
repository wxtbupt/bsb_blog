/**
 * Created by gsy on 2017/3/4.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('deleteForumsThread', deleteForumsThread);

    deleteForumsThread.$inject = ['$resource'];

    function deleteForumsThread ($resource) {
        var service = $resource('api/ms-thread/admin/delete/thread/:id', {}, {
            'update': { method: 'post', params: {}, isArray: false,
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
