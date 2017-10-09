(function() {
    'use strict';
    angular
        .module('bsbmsoneApp')
        .factory('ChatLog', ChatLog);

    ChatLog.$inject = ['$resource'];

    function ChatLog ($resource) {
        var resourceUrl =  'api/chat-logs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
