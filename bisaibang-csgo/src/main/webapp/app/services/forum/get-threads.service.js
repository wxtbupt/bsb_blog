/**
 * Created by HJQ on 2017/7/5.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('ForumGetAllThread', ForumGetAllThread);

    ForumGetAllThread.$inject = ['$resource'];

    function ForumGetAllThread($resource) {
        var service = $resource('api/ms-singlethread/viewer/get-threads/forum/:forumid', {}, {
            'get': { method: 'GET'}
        });
        return service;
    }
})();
