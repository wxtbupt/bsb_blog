/**
 * Created by OlyLis on 2017/3/25.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('NewsGetCommentAdmin', NewsGetCommentAdmin);

    NewsGetCommentAdmin.$inject = ['$resource'];

    function NewsGetCommentAdmin($resource) {
        var service = $resource('api/ms-comment/admin/get-all-comments/article/:id', {}, {
            'save': { method: 'POST' }
        });
        return service;
    }
})();
