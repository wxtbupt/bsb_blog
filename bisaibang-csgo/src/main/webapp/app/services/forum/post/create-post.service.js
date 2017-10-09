/**
 * Created by HJQ on 2017/7/5.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('ForumCreatePost', ForumCreatePost);

    ForumCreatePost.$inject = ['$resource'];

    function ForumCreatePost($resource) {
        var service = $resource('api/ms-post/create/single-thread/:threadid', {}, {
            'save': { method: 'POST' }
        });
        return service;
    }
})();
