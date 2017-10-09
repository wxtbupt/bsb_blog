/**
 * Created by HJQ on 2017/7/12.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('ThreadDelete', ThreadDelete);

    ThreadDelete.$inject = ['$resource'];

    function ThreadDelete($resource) {
        var service = $resource('api/ms-singlethread/delete/single-thread/:threadid ', {}, {
            'save': { method: 'POST'}
        });
        return service;
    }
})();
