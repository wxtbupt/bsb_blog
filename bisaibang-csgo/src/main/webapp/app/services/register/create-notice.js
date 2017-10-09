(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('NoticeCreate', NoticeCreate);

    NoticeCreate.$inject = ['$resource'];

    function NoticeCreate($resource) {
        var service = $resource('/api/ms-notice/create', {}, {
            'save': { method: 'POST'}
        });
        return service;
    }
})();
