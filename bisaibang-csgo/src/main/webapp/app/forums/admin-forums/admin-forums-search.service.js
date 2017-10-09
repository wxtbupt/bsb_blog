/**
 * Created by gsy on 2017/3/8.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('SearchForumsThread', SearchForumsThread);

    SearchForumsThread.$inject = ['$resource'];

    function SearchForumsThread ($resource) {
        var service = $resource('api/ms-thread/find-by-title', {}, {
            'query': { method: 'POST', params: {}, isArray: true,
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
