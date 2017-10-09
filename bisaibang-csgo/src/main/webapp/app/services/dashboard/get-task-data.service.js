/**
 * Created by gsy on 2017/6/15.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('GetTaskData', GetTaskData);

    GetTaskData.$inject = ['$resource'];

    function GetTaskData ($resource) {
        var service = $resource('/api/ms-task/data', {}, {
            'get': { method: 'GET', params: {}, isArray: false,
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
