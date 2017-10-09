/**
 * Created by liujinduo on 2016/10/26.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('ViewerGetAll', ViewerGetAll);

    ViewerGetAll.$inject = ['$resource'];

    function ViewerGetAll($resource) {
        var service = $resource('api/ms-team/viewer/get-all/:teamid', {}, {
            'query': { method: 'GET',isArray:true}
        });
        return service;
    }
})();
