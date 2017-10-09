/**
 * Created by HJQ on 2017/7/5.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('ForumGetAllPost', ForumGetAllPost);

    ForumGetAllPost.$inject = ['$resource'];

    function ForumGetAllPost($resource) {
        var service = $resource('api/ms-post/all-post/single-thread/:threadid', {}, {
            'get': { method: 'GET'}
        });
        return service;
    }
})();
