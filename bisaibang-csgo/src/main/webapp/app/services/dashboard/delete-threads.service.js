/**
 * Created by HJQ on 2017/7/12.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('ThreadsDelete', ThreadsDelete);

    ThreadsDelete.$inject = ['$resource'];

    function ThreadsDelete($resource) {
        var service = $resource('api/ms-singlethread/thread/batch-delete-threads', {}, {
            'save': { method: 'POST'}
        });
        return service;
    }
})();
