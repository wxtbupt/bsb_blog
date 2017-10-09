/**
 * Created by HJQ on 2017/7/13.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('ForumGetMarkThread', ForumGetMarkThread);

    ForumGetMarkThread.$inject = ['$resource'];

    function ForumGetMarkThread($resource) {
        var service = $resource('/api/ms-singlethread/get-mark-threads/forum/:forumid', {}, {
            'get': { method: 'GET'}
        });
        return service;
    }
})();
