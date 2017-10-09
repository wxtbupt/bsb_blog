(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('UpdateHomePageArticle', UpdateHomePageArticle);

    UpdateHomePageArticle.$inject = ['$resource'];

    function UpdateHomePageArticle ($resource) {
        var service = $resource('api/ms-task/update-home-page/article', {}, {
            'save': { method: 'POST'}
        });

        return service;
    }
})();
