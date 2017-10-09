/**
 * Created by OlyLis on 2017/3/14.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('deleteComment', deleteComment);

    deleteComment.$inject = ['$resource'];

    function deleteComment($resource) {
        var service = $resource('api/ms-comment/admin/delete/comment/:id', {}, {
            'save': { method: 'POST'}
        });
        return service;
    }
})();
