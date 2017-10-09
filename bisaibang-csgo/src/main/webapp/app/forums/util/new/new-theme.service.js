/**
 * Created by OlyLis on 2017/3/3.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('createThread', createThread);

    createThread.$inject = ['$resource'];

    function createThread ($resource) {
        var service = $resource('api/ms-thread/create', {}, {
            'post': { method: 'post', params: {}, isArray: false,
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
