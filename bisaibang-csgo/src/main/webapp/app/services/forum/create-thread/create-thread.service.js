
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('ThreadCreate', ThreadCreate);

    ThreadCreate.$inject = ['$resource'];

    function ThreadCreate($resource) {
        var service = $resource('/api/ms-singlethread/create/forum/:forumid', {}, {
            'save': { method: 'POST' }
        });
        return service;
    }
})();
