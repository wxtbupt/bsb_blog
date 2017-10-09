/**
 * Created by OlyLis on 2017/3/14.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('createVideoComment', createVideoComment);

    createVideoComment.$inject = ['$resource'];

    function createVideoComment($resource) {
        var service = $resource('api/ms-comment/create/video', {}, {
            'post': { method: 'POST'}
        });
        return service;
    }
})();
