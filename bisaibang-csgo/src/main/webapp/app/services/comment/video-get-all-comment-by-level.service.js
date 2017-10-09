/**
 * Created by OlyLis on 2017/4/5.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('VideoGetAllCommentByLevel', VideoGetAllCommentByLevel);

    VideoGetAllCommentByLevel.$inject = ['$resource'];

    function VideoGetAllCommentByLevel($resource) {
        var service = $resource('api/ms-comment/get-comments/video/:id/level/:level', {}, {
            'get': { method: 'GET'}
        });
        return service;
    }
})();
