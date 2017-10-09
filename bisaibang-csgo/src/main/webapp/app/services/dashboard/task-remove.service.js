/**
 * Created by gsy on 2017/3/24.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('TaskRemove', TaskRemove);

    TaskRemove.$inject = ['$resource'];

    function TaskRemove ($resource) {
        var service = $resource('api/ms-task/remove', {}, {
            'update': { method: 'POST', isArray: false,
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
